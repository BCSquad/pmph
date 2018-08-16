package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.ProductDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.ProductVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.po.Content;
import com.bc.pmpheep.general.service.ContentService;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    FileService fileService;

    @Autowired
    ContentService contentService;

    @Override
    public ProductVO getProductByType(Long product_type, String sessionId) {

        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                    "用户为空");
        }

        ProductVO productVO = productDao.queryProductByProductType(product_type, Const.CLINICAL_DECISION_FILE_DOWNLOAD);
        if(productVO==null){
            productVO = new ProductVO(product_type);
            productVO.setProduct_type(product_type);
        }

        Content noteContent = contentService.get(productVO.getNote());
        Content descriptionContent = contentService.get(productVO.getDescription());

        productVO.setNoteContent(noteContent);
        productVO.setDescriptionContent(descriptionContent);

        return productVO;
    }

    @Override
    public int updateProductAttachmenDownLoadCountsByAttachment(String id) {
        int count = productDao.updateProductAttachmenDownLoadCountsByAttachment(id);
        return count;
    }

    @Override
    public ResponseBean saveProductVO(ProductVO productVO,String sessionId, HttpServletRequest request) throws IOException {

        ResponseBean responseBean = new ResponseBean();

        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                    "用户为空");
        }

        //if(productVO.getId()==null){ //新建 保存创建人
            productVO.setFounder_id(pmphUser.getId());
        //}
        if(productVO.getIs_published()){ // 发布 保存发布人
            productVO.setPublisher_id(pmphUser.getId());
        }

        //校验
        validateProductVO(productVO);

        //insert on duplicate key update 主表product 其中 unique键除主键外 还有product_type
        //keyProperty设为id 若插入 则将生成的id传回到productVO的id中 便于附表关联之
        switch (String.valueOf(productVO.getProduct_type())){
            case "1":
                productVO.setProduct_name( "人卫临床助手");
                break;
            case "2":
                productVO.setProduct_name( "人卫用药助手");
                break;
            case "3":
                productVO.setProduct_name(  "人卫中医助手");
                break;
            default:
                productVO.setProduct_name(  "未命名");
        }

        //将传回的附件和图片mongodb地址过滤，去掉最后一个/及之前的字符串
        attachmentRegexGetMongoId(productVO);

        int mainCount = productDao.saveProduct(productVO);

        //为了保留历史申报里的关联扩展项填写内容，扩展项要伪删除
        productDao.deleteProductExtension(productVO.getId());
        //先按产品分类全部伪删除，再将传入id的insert on duplicate key update
        if(CollectionUtil.isNotEmpty(productVO.getProductExtensionList())){
            for(ProductExtension productExtension:productVO.getProductExtensionList()){
                if(ObjectUtil.isNull(productExtension.getProductId()))productExtension.setProductId(productVO.getId());
            }
            int extensionCount = productDao.saveProductExtension(productVO.getProductExtensionList());
        }
        Long productId = productVO.getId();

        productDao.deleteProductAuditorsByProductId(productId);
        if(CollectionUtil.isNotEmpty(productVO.getAuditorList())){
            for(ProductAuditor productAuditor:productVO.getAuditorList()){
                if(ObjectUtil.isNull(productAuditor.getProduct_id()))productAuditor.setProduct_id(productVO.getId());
            }
            productDao.saveProductAuditors(productVO.getAuditorList());
        }

        productDao.deleteProductAttachmentByProductId(productId);

        List<ProductAttachment> attachmentList = productVO.getProductAttachmentList();
        List<ProductAttachment> imgList = productVO.getProducntImgList();

        attachmentSaveToMDB(productVO, request, attachmentList, false);
        attachmentSaveToMDB(productVO, request, imgList, true);

        /*if(CollectionUtil.isNotEmpty(productVO.getProducntImgList())){
            for(ProductAttachment productAttachment:productVO.getProducntImgList()){
                if(ObjectUtil.isNull(productAttachment.getProduct_id()))productAttachment.setProduct_id(productVO.getId());
                productAttachment.setIs_scan_img(true);
            }

            productDao.saveProductAttachments(productVO.getProducntImgList());
        }*/

        responseBean.setCode(ResponseBean.SUCCESS);
        responseBean.setData(productVO);
        responseBean.setMsg(productVO.getIs_published()?"发布成功":"暂存成功");

        return responseBean;
    }

    private void attachmentSaveToMDB(ProductVO productVO, HttpServletRequest request, List<ProductAttachment> attachmentList, Boolean is_scan_img) throws IOException {
        if(CollectionUtil.isNotEmpty(attachmentList)){
            for(ProductAttachment productAttachment:attachmentList){
                if(ObjectUtil.isNull(productAttachment.getProduct_id()))productAttachment.setProduct_id(productVO.getId());
                if(productAttachment.getId() == null){
                    String tempFileId = productAttachment.getAttachment();
                    byte[] file = (byte[]) request.getSession(false).getAttribute(tempFileId);
                    String fileName = (String) request.getSession(false).getAttribute("fileName_" + tempFileId);
                    InputStream sbs = new ByteArrayInputStream(file);
                    //初次生成，产生主键id
                    productDao.createAttachment(productAttachment);
                    String mdbId ;
                    mdbId = fileService.save(sbs, fileName, FileType.CLINIC_DECISION_ATTACHMENT,
                    						productAttachment.getId());
                    productAttachment.setAttachment(mdbId);
                    //附件保存到mdb后，产生mdb的id，再次保存（主键冲突会执行更新）
                }


                productAttachment.setIs_scan_img(is_scan_img);
            }
            productDao.saveProductAttachments(attachmentList);
        }
    }

    private void attachmentRegexGetMongoId(ProductVO productVO) {
        List<ProductAttachment> alist = productVO.getProductAttachmentList();
        List<ProductAttachment> mlist = productVO.getProducntImgList();
        for (ProductAttachment a: alist) {
            String aid = a.getAttachment();
            if(StringUtil.notEmpty(aid)){
                aid = aid.replaceAll("^.*/","");
                a.setAttachment(aid);
            }
        }
        for (ProductAttachment a: mlist) {
            String aid = a.getAttachment();
            if(StringUtil.notEmpty(aid)){
                aid = aid.replaceAll("^.*/","");
                a.setAttachment(aid);
            }
        }
    }

    @Override
    public ResponseBean list(PageParameter<ProductVO> pageParameter) {
        ResponseBean responseBean = new ResponseBean();
        PageResult<ProductVO> pageResult = new PageResult<>();
        pageResult.setPageSize(pageParameter.getPageSize());
        pageResult.setPageNumber(pageParameter.getPageNumber());
        int count = productDao.queryListCount(pageParameter);
        List<ProductVO> list = productDao.queryList(pageParameter);
        pageResult.setTotal(count);
        pageResult.setRows(list);
        responseBean.setData(pageResult);
        return responseBean;
    }

    /**
     * 提交校验
     * @param productVO
     */
    private void validateProductVO(ProductVO productVO) {

        Field[] fields = productVO.getClass().getDeclaredFields();

        for (Field field:fields) {  //遍历属性，进行非空判断
            Object fieldValue = null;
            String type = null;
            String name = null;
            try {
                name = field.getName();
                //type = field.getGenericType().toString();
                Method getter = productVO.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
                fieldValue = getter.invoke(productVO);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (fieldValue == null &&
                    !(
                        "publisher_id".equals(name)     //不需要非空校验的属性
                        ||"id".equals(name)
                        ||"gmt_update".equals(name)
                        ||"gmt_publish".equals(name)
                        ||"ProductExtensionList".equals(name)
                        ||"ProductAttachmentList".equals(name)
                        ||"ProducntImgList".equals(name)
                            ||"product_name".equals(name)
                                ||"gmt_create".equals(name)
                            ||"gmt_publish".equals(name)
                                ||"note".equals(name)
                                ||"description".equals(name)
                                ||"publisher".equals(name)
                                ||"founder".equals(name)
                                || "is_unit_advise_required".equals(name)
                                || "is_deleted".equals(name)

                    )){
                throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                        name+"为空");
            }
        }

        if(productVO.getIs_published() ){ //若发布
            if(productVO.getAuditorList()==null || productVO.getAuditorList().size() < 1 ){
                throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION,CheckedExceptionResult.NULL_PARAM,
                        "发布时必须选择审核人");
            }
            productVO.setGmt_publish(new Timestamp(new Date().getTime())); //设定发布时间
        }
        if(!StringUtil.isEmpty(productVO.getNoteContent().getContent())){
            Content noteContent = contentService.add(productVO.getNoteContent());
            productVO.setNoteContent(noteContent);
            productVO.setNote(noteContent.getId());
        }

        Content descriptionContent = contentService.add(productVO.getDescriptionContent());

        productVO.setDescriptionContent(descriptionContent);

        productVO.setDescription(descriptionContent.getId());

    }
}
