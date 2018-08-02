package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.ProductDao;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Product;
import com.bc.pmpheep.back.po.ProductAttachment;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.ProductVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.general.po.Content;
import com.bc.pmpheep.general.service.ContentService;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public ResponseBean saveProductVO(ProductVO productVO,String sessionId) {

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
        int mainCount = productDao.saveProduct(productVO);

        //为了保留历史申报里的关联扩展项填写内容，扩展项要伪删除
        productDao.deleteProductExtension(productVO.getId());
        //先按产品分类全部伪删除，再将传入id的insert on duplicate key update
        if(CollectionUtil.isNotEmpty(productVO.getProductExtensionList())){
            int extensionCount = productDao.saveProductExtension(productVO.getProductExtensionList());
        }
        Long productId = productVO.getId();

        productDao.deleteProductAuditorsByProductId(productId);
        if(CollectionUtil.isNotEmpty(productVO.getAuditorList())){
            productDao.saveProductAuditors(productVO.getAuditorList());
        }

        productDao.deleteProductAttachmentByProductId(productId);
        if(CollectionUtil.isNotEmpty(productVO.getProductAttachmentList())){
            productDao.saveProductAttachments(productVO.getProductAttachmentList());
        }

        responseBean.setCode(ResponseBean.SUCCESS);
        responseBean.setData(productVO);
        responseBean.setMsg(productVO.getIs_published()?"发布成功":"暂存成功");

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

        Content noteContent = contentService.add(productVO.getNoteContent());
        Content descriptionContent = contentService.add(productVO.getDescriptionContent());
        productVO.setNoteContent(noteContent);
        productVO.setDescriptionContent(descriptionContent);
        productVO.setNote(noteContent.getId());
        productVO.setDescription(descriptionContent.getId());

    }
}
