package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.ExpertationDao;
import com.bc.pmpheep.back.dao.ProductDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.ExpertationCountnessVO;
import com.bc.pmpheep.back.vo.ExpertationVO;
import com.bc.pmpheep.back.vo.ProductType;
import com.bc.pmpheep.back.vo.ProductVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpertationServiceImpl implements ExpertationService{

    @Autowired
    ExpertationDao expertationDao;

    @Autowired
    PmphUserService pmphUserService;

    @Autowired
    ProductDao productDao;

    /**
     * 查找临床决策申报列表
     * @param pageParameter
     * @param sessionId
     * @return
     */
    @Override
    public PageResult<ExpertationVO> list4Audit(PageParameter<ExpertationVO> pageParameter,String sessionId) {

        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                    "用户为空");
        }
        if (ObjectUtil.isNull(pageParameter)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                    "参数为空");
        }
        if(ObjectUtil.isNull(pageParameter.getParameter().getExpert_type())){
            throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                    "申报类型为空");
        }




        List<PmphRole> pmphRoles = pmphUserService.getListUserRole(pmphUser.getId());
        // 下面进行授权
        Boolean is_admin = false;
        // 系统管理员权限检查
        for (PmphRole pmphRole : pmphRoles) {
            if (null != pmphRole && null != pmphRole.getRoleName() && "系统管理员".equals(pmphRole.getRoleName())) {
                // 我是系统管理原
                is_admin = true;
            }
        }

        //若管理员或领导登录 则不传入此id 查询全部申报 （但同时也因空id，查出的amIAnAuditor将为否 ，管理员需在前台给审核权限，领导查看即可）
        //否则
        if(!is_admin){
            //TODO 传入登录人id 作为查询条件 审核人id
            pageParameter.getParameter().setAuditor_id(pmphUser.getId());
        }

        PageResult pageResult = new PageResult();
        int totalCount = expertationDao.queryExpertationCount(pageParameter);
        List<ExpertationVO> list = new ArrayList<>();
        if(totalCount>0){
            list = expertationDao.queryExpertation(pageParameter);
            /*for (ExpertationVO e: list) {
                List<ProductType> clist = expertationDao.queryProductContentTypeListByExpertationId(e.getId());
                List<ProductType> slist = expertationDao.queryProductSubjectTypeListByExpertationId(e.getId());
                e.setProductSubjectTypeList(slist);
                e.setProductContentTypeList(clist);
                e.setExcelTypeStr();
            }*/
        }

        pageResult.setPageNumber(pageParameter.getPageNumber());

        pageResult.setTotal(totalCount);
        pageResult.setRows(list);

        return pageResult;
    }

    /**
     * 查询 临床决策申报-结果统计
     * @param pageParameter
     * @param sessionId
     * @return
     */
    @Override
    public PageResult getCountListGroupByType(PageParameter<Map<String, Object>> pageParameter, String sessionId) {

        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                    "用户为空");
        }

        int ttype = (int)pageParameter.getParameter().get("ttype");
        int ptype = (int)pageParameter.getParameter().get("ptype");

        ProductVO product = productDao.queryProductByProductType(Long.valueOf(String.valueOf(ptype)), "");
        if(product!=null && product.getId() != null){
            pageParameter.getParameter().put("product_id",product.getId());
        }else{
            pageParameter.getParameter().put("product_id",0);
        }
        List<ExpertationCountnessVO> list = new ArrayList<>();
        int totalCount = 0;
        if(ttype == 2){ //2.内容分类
            totalCount = expertationDao.getCountListGroupByContentTypeCount(pageParameter);
            list = expertationDao.getCountListGroupByContentType(pageParameter);
        }else if(ttype == 3){ //3.专业分类
            totalCount = expertationDao.getCountListGroupByProfessionTypeCount(pageParameter);
            list = expertationDao.getCountListGroupByProfessionType(pageParameter);
        }else{ //1.学科分类
            totalCount = expertationDao.getCountListGroupBySubjectTypeCount(pageParameter);
            list = expertationDao.getCountListGroupBySubjectType(pageParameter);
        }



        PageResult pageResult = new PageResult();
        pageResult.setTotal(totalCount);
        pageResult.setRows(list);

        return pageResult;
    }

    /**
     * 查询临床决策申报详情
     * @param id 申报表主键
     * @param sessionId
     * @return
     */
    @Override
    public ExpertationVO getExpertationById(Long id, String sessionId) {

        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                    "用户为空");
        }

        ExpertationVO expertationVO = expertationDao.getExpertationById(id);

        expertationVO.setDecAcadeList(expertationDao.queryDecAcade(id));

        expertationVO.setDecEduExpList(expertationDao.queryDecEduExp(id));

        expertationVO.setDecWorkExpList(expertationDao.queryDecWorkExp(id));

        expertationVO.setDecMonographList(expertationDao.queryDecMonograph(id));

        expertationVO.setDecTextbookPmphList(expertationDao.queryDecTextbookPmph(id));

        expertationVO.setDecArticlePublishedList(expertationDao.queryDecArticlePublished(id));

        expertationVO.setDecProfessionAwardList(expertationDao.queryDecProfessionAward(id));
        return expertationVO;
    }

    @Override
    public Boolean onlineProgress(Long id, Integer onlineProgress, String returnCause, PmphUser pmphUser) {
        Boolean flag = false;
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "主键不能为空!");
        }
        if (ObjectUtil.isNull(onlineProgress)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "审核进度不能为空!");
        }
        try{
            //获取申报信息
            ExpertationVO expertationVO  = expertationDao.getExpertationById(id);
            if(4==onlineProgress.intValue()||5==onlineProgress.intValue()){ // 退回
                if (StringUtil.strLength(returnCause) > 40) {
                    throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
                            "最多只能输入40个字符，请重新输入!");
                }

                // 产生一条动态消息
            }else if(3==onlineProgress.intValue()){ // 通过
                // 产生一条动态消息
            }
            expertationDao.updateOnlineProgress(id,onlineProgress,returnCause);
            flag = true;
        }catch (Exception e){
         e.printStackTrace();
        }

        return flag;
    }

    @Override
    public Map showTabs(Long productType) {
        List showTabList = new ArrayList();
        List notShowTabList = new ArrayList();
        Map map =  expertationDao.showTabs(productType);
        if((Boolean)map.get("is_subject_type_used")){
            showTabList.add("subject");
        }else{
            notShowTabList.add("subject");
        }
        if((Boolean)map.get("is_content_type_used")){
            showTabList.add("content");
        }else{
            notShowTabList.add("content");
        }
        if((Boolean)map.get("is_profession_type_used")){
            showTabList.add("profession");
        }else{
            notShowTabList.add("profession");
        }
        Map resultMap = new HashMap();
        resultMap.put("showTabList",showTabList);
        resultMap.put("notShowTabList",notShowTabList);
        return resultMap;
    }



}
