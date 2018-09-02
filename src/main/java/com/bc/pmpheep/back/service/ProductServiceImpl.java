package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.PmphRoleDao;
import com.bc.pmpheep.back.dao.PmphUserRoleDao;
import com.bc.pmpheep.back.dao.ProductDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.MateriaHistorylVO;
import com.bc.pmpheep.back.vo.MaterialProjectEditorVO;
import com.bc.pmpheep.back.vo.OrgExclVO;
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
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    FileService fileService;

    @Autowired
    ContentService contentService;

    @Autowired
    private PmphUserService pmphUserService;

    @Autowired
    private PmphUserRoleDao pmphUserRoleDao;

    @Autowired
    private PmphRoleDao pmphRoleDao;

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

        Content noteContent = null;
        if(!StringUtil.isEmpty(productVO.getNote())){
            noteContent =  contentService.get(productVO.getNote());
        }
        Content descriptionContent = null;
        if(!StringUtil.isEmpty(productVO.getDescription())){
            descriptionContent = contentService.get(productVO.getDescription());
        }

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
       /* if(productVO.getIs_published()){ // 发布 保存发布人
            productVO.setPublisher_id(pmphUser.getId());
        }*/

        //校验
        validateProductVO(productVO);


        toMakeSureExpertationAuditRoles();


        PmphRole auditorMenuRole = new PmphRole();

        //insert on duplicate key update 主表product 其中 unique键除主键外 还有product_type
        //keyProperty设为id 若插入 则将生成的id传回到productVO的id中 便于附表关联之
        switch (String.valueOf(productVO.getProduct_type())){
            case "1":
                productVO.setProduct_name( "人卫临床助手");
                auditorMenuRole = pmphRoleDao.getByName("临床助手审核人");
                break;
            case "2":
                productVO.setProduct_name( "人卫用药助手");
                auditorMenuRole = pmphRoleDao.getByName("用药助手审核人");
                break;
            case "3":
                productVO.setProduct_name(  "人卫中医助手");
                auditorMenuRole = pmphRoleDao.getByName("中医助手审核人");
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

        // 删除此productId下的所有相关角色pmph_user_role
        //pmphUserRoleDao.deletePmphUserRoleByRoleId(auditorMenuRole.getId());
        if(CollectionUtil.isNotEmpty(productVO.getAuditorList())){
           for(ProductAuditor productAuditor:productVO.getAuditorList()){
                if(ObjectUtil.isNull(productAuditor.getProduct_id()))productAuditor.setProduct_id(productVO.getId());

                //该审核人加角色（菜单权限）
                /* PmphUserRole pmphUserRole = new PmphUserRole(productAuditor.getAuditor_id(),auditorMenuRole.getId());
                pmphUserRoleDao.addPmphUserRole(pmphUserRole);

                List<PmphUser> parentDeptsDirectors =pmphUserService.getSomebodyParentDeptsPmphUserOfSomeRole(productAuditor.getAuditor_id(),null,"主任");
                for (PmphUser PDDirector: parentDeptsDirectors) {
                    // 领导加角色（菜单权限）。 而数据权限（查出来之后是审核还是查看由审核人表控制）
                    PmphUserRole userRole = new PmphUserRole(PDDirector.getId(),auditorMenuRole.getId());
                    pmphUserRoleDao.addPmphUserRole(userRole);
                }*/
            }

            //该审核人加入审核人表（数据权限）
            productDao.saveProductAuditors(productVO.getAuditorList());
        }

        productDao.deleteProductAttachmentByProductId(productId);

        List<ProductAttachment> attachmentList = productVO.getProductAttachmentList();
        List<ProductAttachment> imgList = productVO.getProducntImgList();

        attachmentSaveToMDB(productVO, request, attachmentList, false);
        //attachmentSaveToMDB(productVO, request, imgList, true);

        if(CollectionUtil.isNotEmpty(productVO.getProducntImgList())){
            for(ProductAttachment productAttachment:productVO.getProducntImgList()){
                if(ObjectUtil.isNull(productAttachment.getProduct_id()))productAttachment.setProduct_id(productVO.getId());
                productAttachment.setIs_scan_img(true);
            }
            productDao.saveProductAttachments(productVO.getProducntImgList());
        }

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

    @Override
    public List<OrgExclVO> getOutPutExclOrgByProduct(Long productId) {
        if (null == productId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB,
                    CheckedExceptionResult.NULL_PARAM, "临床id为空");
        }
        return productDao.getOutPutExclOrgByProduct(productId);
    }

    /**
     * 临床发布通知
     * @param productId
     * @param orgIds
     * @param sessionId
     * @return
     */
    @Override
    public Integer noticePublished(Long productId, List<Long> orgIds, String sessionId) {
        if (CollectionUtil.isEmpty(orgIds)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                    CheckedExceptionResult.NULL_PARAM, "机构为空");
        }
        Set<Long> newOrgIdSet = new HashSet<>();// 防止网络延迟重复提交
        newOrgIdSet.addAll(orgIds);
        List<Long> listOrgIds = new ArrayList<Long>(newOrgIdSet.size());
        listOrgIds.addAll(newOrgIdSet);
        // 获取当前用户
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                    CheckedExceptionResult.NULL_PARAM, "请求用户不存在");
        }
        Integer count = 0;
        List<ProductOrg> productOrgList = new ArrayList<ProductOrg>(listOrgIds.size());
        // 根据教材ID查询临床-机构关联表
        List<Long> OrgIds = this.getListProductOrgByProductId(productId);
        if (CollectionUtil.isEmpty(OrgIds)) {// 为空，初次发布
            for (Long orgId : listOrgIds) {
                productOrgList.add(new ProductOrg(productId, orgId));
            }
            count = this.addProductOrgs(productOrgList);

        } else {// 不为空
            List<Long> newOrgIds = new ArrayList<Long>();// 新选中的机构
            for (Long orgId : listOrgIds) {
                if (!OrgIds.contains(orgId)) {
                    newOrgIds.add(orgId);
                    productOrgList.add(new ProductOrg(productId, orgId));
                }
            }
            if (CollectionUtil.isEmpty(productOrgList)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_EXTRA,
                        CheckedExceptionResult.ILLEGAL_PARAM,
                        "当前选中的学校已发送，无需要再次发送");
            }
            count = this.addProductOrgs(productOrgList);

        }

        count = this.updateProduct(new ProductVO(productId, true), sessionId);
        return count;
    }

    @Override
    public Integer addProductOrgs(List<ProductOrg> productOrgList) {
        if (null == productOrgList || productOrgList.size() == 0) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB,
                    CheckedExceptionResult.NULL_PARAM, "参数为空");
        }
        return productDao.addProductOrgs(productOrgList);
    }

    @Override
    public List<Long> getListProductOrgByProductId(Long productId) {
        if (ObjectUtil.isNull(productId)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL_PUB,
                    CheckedExceptionResult.NULL_PARAM, "ID为空");
        }
        return productDao.getListProductOrgByProductId(productId);
    }

    @Override
    public Integer updateProduct(ProductVO productVO, String sessionId) {
        if (null == productVO.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
                    "主键为空");
        }
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
                    "用户为空");
        }
        productVO.setPublisher_id(pmphUser.getId());
        return productDao.updateProduct(productVO);
    }

    @Override
    public PageResult<ProductHistorylVO> listProductHistory(PageParameter<ProductHistorylVO> pageParameter, String sessionId) {
        PageResult<ProductHistorylVO> pageResult = new PageResult<ProductHistorylVO>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        List<ProductHistorylVO> ProductHistorylVOs =
                productDao.listProductHistory(pageParameter);
        if (CollectionUtil.isNotEmpty(ProductHistorylVOs)) {
            Integer count = ProductHistorylVOs.get(0).getCount();
            pageResult.setTotal(count);
            pageResult.setRows(ProductHistorylVOs);
        }
        return pageResult;
    }

    /**
     * 获取临床集合
     * @param productName
     * @return
     */
    @Override
    public List<Product> getListProduct(String productName) {
        return productDao.getListProduct(productName);
    }


    /**
     * 提交校验
     * @param productVO
     */
    private void validateProductVO(ProductVO productVO) {

        Field[] fields = productVO.getClass().getDeclaredFields();

       /* for (Field field:fields) {  //遍历属性，进行非空判断
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
                            ||"is_unit_advise_used".equals(name)
                            ||"is_acade_used".equals(name)
                                ||"is_acade_required".equals(name)
                            ||"is_edu_exp_used".equals(name)
                                ||"is_edu_exp_required".equals(name)
                                ||"is_work_exp_used".equals(name)
                                ||"is_work_exp_required".equals(name)
                                ||"is_pmph_textbook_used".equals(name)
                                ||"is_pmph_textbook_required".equals(name)
                                ||"is_monograph_used".equals(name)
                                ||"is_monograph_required".equals(name)
                                ||"is_edit_book_used".equals(name)
                                ||"is_edit_book_required".equals(name)
                                ||"is_article_published_used".equals(name)
                                ||"is_article_published_required".equals(name)
                                ||"is_subject_type_used".equals(name)
                                ||"is_subject_type_required".equals(name)
                                ||"is_content_type_used".equals(name)

                    )){
                throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                        name+"为空");
            }
        }*/


        if (ObjectUtil.isNull(productVO.getAuditorList())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                    "请选择审核人");
        }

        if (ObjectUtil.isNull(productVO.getProducntImgList())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                    "图片未上传");
        }

        if (ObjectUtil.isNull(productVO.getProductAttachmentList())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                    "附件未上传");
        }

        if (ObjectUtil.isNull(productVO.getDescriptionContent())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                    "产品简介未说明");
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
        }else{
            productVO.setNote("");
        }

        Content descriptionContent = contentService.add(productVO.getDescriptionContent());

        productVO.setDescriptionContent(descriptionContent);

        productVO.setDescription(descriptionContent.getId());

    }

    private void toMakeSureExpertationAuditRoles(){
        //TODO 确保有临床申报审核人相关的角色
    }
}
