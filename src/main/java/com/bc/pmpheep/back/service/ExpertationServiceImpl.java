package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.ExpertationDao;
import com.bc.pmpheep.back.dao.ProductDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.*;
import com.bc.pmpheep.back.service.common.SystemMessageService;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.*;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Autowired
    private PmphDepartmentService    pmphDepartmentService;

    @Autowired
    private SystemMessageService systemMessageService;

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
        /*Boolean is_admin = false;
        // 系统管理员权限检查
        for (PmphRole pmphRole : pmphRoles) {
            if (null != pmphRole && null != pmphRole.getRoleName() && "系统管理员".equals(pmphRole.getRoleName())) {
                // 我是系统管理原
                is_admin = true;
            }
        }*/

        // 如果是系统管理员，则查询所有，否则查询对应的消息
       /* if (Const.FALSE == pmphUser.getIsAdmin()) {
            List<Long> ids = new ArrayList<Long>();
            // 如果是主任，获取主任所在部门下的所有用户
            if (Const.TRUE == pmphUser.getIsDirector()) {
                // 社内部门父级节点ID
                Long parentId = 1L;
                PmphDepartment pmphDepartment =
                        pmphDepartmentService.getPmphDepartmentById(pmphUser.getDepartmentId());
                // 如果是人卫社主任，则可以查看所有用户发送的消息
                if (parentId.longValue() == pmphDepartment.getId().longValue()) {
                    ids = null;
                } else {
                    // 如果是子级部门主任，则只可以查看子级部门下的用户发送的消息
                    PageParameter<PmphUserManagerVO> parameter = new PageParameter<>(1, 2000);
                    PmphUserManagerVO pmphUserManagerVO = new PmphUserManagerVO();
                    pmphUserManagerVO.setPath(pmphDepartment.getPath());
                    pmphUserManagerVO.setDepartmentId(pmphDepartment.getId());
                    parameter.setParameter(pmphUserManagerVO);
                    PageResult<PmphUserManagerVO> listPageResult =
                            pmphUserService.getListPmphUser(parameter, null);
                    List<PmphUserManagerVO> listPmphUserManagerVOs = listPageResult.getRows();
                    for (PmphUserManagerVO pmManagerVO : listPmphUserManagerVOs) {
                        ids.add(pmManagerVO.getId());
                    }
                }
            } else {
                ids.add(pmphUser.getId());
            }
            pageParameter.getParameter().setFollowingAuditor(ids);
        }*/


        //若管理员或领导登录 则不传入此id 查询全部申报 （但同时也因空id，查出的amIAnAuditor将为否 ，管理员需在前台给审核权限，领导查看即可）
        //否则
       /* if(!is_admin && Const.TRUE != pmphUser.getIsDirector()){
            // 传入登录人id 作为查询条件 审核人id
            pageParameter.getParameter().setAuditor_id(pmphUser.getId());
        }*/

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
        pageParameter.getParameter().put("ptype",ptype);

        /*ProductVO product = productDao.queryProductByProductType(Long.valueOf(String.valueOf(ptype)), "");
        ProductVO product = productDao.queryProductByProductType(Long.valueOf(String.valueOf(ptype)), null,"");
        if(product!=null && product.getId() != null){
            pageParameter.getParameter().put("product_id",product.getId());
        }else{
            pageParameter.getParameter().put("product_id",0);
        }*/
        //ProductVO product =new ProductVO();

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

        //expertationVO.setAmIAnAuditor(expertationDao.queryAmIAnAuditor(pmphUser.getId(),id));
        expertationVO.setAuditorArray(expertationDao.queryAuditorArray(pmphUser.getId(),id));
        expertationVO.setDirector(expertationDao.queryDirector(pmphUser.getId(),id));

        expertationVO.setDecAcadeList(expertationDao.queryDecAcade(id));

        expertationVO.setDecEduExpList(expertationDao.queryDecEduExp(id));

        expertationVO.setDecWorkExpList(expertationDao.queryDecWorkExp(id));

        expertationVO.setDecMonographList(expertationDao.queryDecMonograph(id));

        expertationVO.setDecTextbookPmphList(expertationDao.queryDecTextbookPmph(id));

        expertationVO.setDecEditorBookList(expertationDao.queryDecEditorBook(id));

        expertationVO.setDecArticlePublishedList(expertationDao.queryDecArticlePublished(id));

        expertationVO.setDecProfessionAwardList(expertationDao.queryDecProfessionAward(id));

        expertationVO.setProductProfessionTypeList(expertationDao.queryProfession(id));
        return expertationVO;
    }

    /**
     * 查询临床决策申报详情
     * @param id 申报表主键
     * @return
     */
    @Override
    public ExpertationVO getExpertationById(Long id) {

        //如果是循环遍历此方法 需要一次性把数据拿到再在service拼接，当前以下方法请求次数过多，速度慢，有超时隐患
        //获取list用getExpertationByIdList

        ExpertationVO expertationVO = expertationDao.getExpertationById(id);

        expertationVO.setDecAcadeList(expertationDao.queryDecAcade(id));

        expertationVO.setDecEduExpList(expertationDao.queryDecEduExp(id));

        expertationVO.setDecWorkExpList(expertationDao.queryDecWorkExp(id));

        expertationVO.setDecMonographList(expertationDao.queryDecMonograph(id));

        expertationVO.setDecTextbookPmphList(expertationDao.queryDecTextbookPmph(id));

        expertationVO.setDecEditorBookList(expertationDao.queryDecEditorBook(id));

        expertationVO.setDecArticlePublishedList(expertationDao.queryDecArticlePublished(id));

        expertationVO.setDecProfessionAwardList(expertationDao.queryDecProfessionAward(id));

        expertationVO.setProductProfessionTypeList(expertationDao.queryProfession(id));
        return expertationVO;
    }

    public List<ExpertationVO> getExpertationByIdList(List<Long> queryExpertationIdList){

        List<ExpertationVO> list = expertationDao.getExpertationByIdList(queryExpertationIdList);

        List<DecAcade> DecAcade = expertationDao.queryDecAcadeByIds(queryExpertationIdList);

        List<DecEduExp> decEduExp = expertationDao.queryDecEduExpByIds(queryExpertationIdList);

        List<DecWorkExp> decWorkExp = expertationDao.queryDecWorkExpByIds(queryExpertationIdList);

        List<DecMonograph> decMonograph = expertationDao.queryDecMonographByIds(queryExpertationIdList);

        List<DecTextbookPmph> decTextbookPmph = expertationDao.queryDecTextbookPmphByIds(queryExpertationIdList);

        List<DecEditorBook> decEditorBook = expertationDao.queryDecEditorBookByIds(queryExpertationIdList);

        List<DecArticlePublished> decArticlePublished = expertationDao.queryDecArticlePublishedByIds(queryExpertationIdList);

        List<DecProfessionAward> decProfessionAward = expertationDao.queryDecProfessionAwardByIds(queryExpertationIdList);

        List<ProductProfessionType> profession = expertationDao.queryProfessionByIds(queryExpertationIdList);

        for(ExpertationVO experationV:list){
            //对获取到的数据 进行数据转换
            experationV.setSex("2".equals(experationV.getSex())?"女":"男");
            if(!ObjectUtil.isNull(experationV.getIdtype())){
                switch (experationV.getIdtype()){
                    case 0:experationV.setIdTypeName("身份证");
                    case 1:experationV.setIdTypeName("护照");
                    case 2:experationV.setIdTypeName("军官证");
                    default:
                        experationV.setIdTypeName("");
                }
            }
            if(!ObjectUtil.isNull(experationV.getEducation())){
                switch (experationV.getEducation()){
                    case 0:experationV.setEducationName("专科");
                    case 1:experationV.setEducationName("本科");
                    case 2:experationV.setEducationName("硕士");
                    case 3:experationV.setEducationName("博士后");
                    case 4:experationV.setEducationName("博士");
                    default:
                        experationV.setIdTypeName("");
                }
            }

            experationV.setDecAcadeList(CollectionUtil.isNotEmpty(experationV.getDecAcadeList())?experationV.getDecAcadeList():new ArrayList<com.bc.pmpheep.back.po.DecAcade>());
            experationV.setDecEduExpList(CollectionUtil.isNotEmpty(experationV.getDecEduExpList())?experationV.getDecEduExpList():new ArrayList<DecEduExp>());
            experationV.setDecWorkExpList(CollectionUtil.isNotEmpty(experationV.getDecWorkExpList())?experationV.getDecWorkExpList():new ArrayList<DecWorkExp>());
            experationV.setDecMonographList(CollectionUtil.isNotEmpty(experationV.getDecMonographList())?experationV.getDecMonographList():new ArrayList<DecMonograph>());
            experationV.setDecTextbookPmphList(CollectionUtil.isNotEmpty(experationV.getDecTextbookPmphList())?experationV.getDecTextbookPmphList():new ArrayList<DecTextbookPmph>());
            experationV.setDecEditorBookList(CollectionUtil.isNotEmpty(experationV.getDecEditorBookList())?experationV.getDecEditorBookList():new ArrayList<DecEditorBook>());
            experationV.setDecArticlePublishedList(CollectionUtil.isNotEmpty(experationV.getDecArticlePublishedList())?experationV.getDecArticlePublishedList():new ArrayList<DecArticlePublished>());
            experationV.setDecProfessionAwardList(CollectionUtil.isNotEmpty(experationV.getDecProfessionAwardList())?experationV.getDecProfessionAwardList():new ArrayList<DecProfessionAward>());
            experationV.setProductProfessionTypeList(CollectionUtil.isNotEmpty(experationV.getProductProfessionTypeList())?experationV.getProductProfessionTypeList():new ArrayList<ProductProfessionType>());

            for(DecAcade decAcade :DecAcade){
                if(ObjectUtil.notNull(decAcade) && experationV.getId().equals(decAcade.getExpertationId())){
                    if(!ObjectUtil.isNull(decAcade.getRank())){
                        switch (decAcade.getRank()){
                            case 1:decAcade.setRankName("国际");
                            case 2:decAcade.setRankName("国家");
                            case 3:decAcade.setRankName("省部");
                            case 4:decAcade.setRankName("市级");
                            default:
                                decAcade.setRankName("无");
                        }
                    }
                    experationV.getDecAcadeList().add(decAcade);
                }
            }

            for(DecEduExp d :decEduExp){
                if(ObjectUtil.notNull(d) && experationV.getId().equals(d.getExpertationId())){
                    experationV.getDecEduExpList().add(d);
                }
            }

            for(DecWorkExp d :decWorkExp){
                if(ObjectUtil.notNull(d) && experationV.getId().equals(d.getExpertationId())){
                    experationV.getDecWorkExpList().add(d);
                }
            }

            for(DecMonograph d :decMonograph){
                if(ObjectUtil.notNull(d) && experationV.getId().equals(d.getExpertationId())){
                    experationV.getDecMonographList().add(d);
                }
            }

            for(DecTextbookPmph d :decTextbookPmph){
                if(ObjectUtil.notNull(d) && experationV.getId().equals(d.getExpertationId())){
                    if(!ObjectUtil.isNull(d.getRank())) {
                        switch (d.getRank()) {
                            case 1:
                                d.setRankName("国家");
                            case 2:
                                d.setRankName("省部");
                            case 3:
                                d.setRankName("协编");
                            case 4:
                                d.setRankName("校本");
                            case 5:
                                d.setRankName("其他");
                            case 6:
                                d.setRankName("教育部规划");
                            case 7:
                                d.setRankName("卫计委规划");
                            case 8:
                                d.setRankName("区域规划");
                            case 9:
                                d.setRankName("创新教材");
                            default:
                                d.setRankName("无");
                        }
                    }
                    experationV.getDecTextbookPmphList().add(d);
                }
            }

            for(DecEditorBook d :decEditorBook){
                if(ObjectUtil.notNull(d) && experationV.getId().equals(d.getExpertationId())){
                    experationV.getDecEditorBookList().add(d);
                }
            }


            for(DecArticlePublished d :decArticlePublished){
                if(ObjectUtil.notNull(d) && experationV.getId().equals(d.getExpertationId())){
                    experationV.getDecArticlePublishedList().add(d);
                }
            }

            for(DecProfessionAward d :decProfessionAward){
                if(ObjectUtil.notNull(d) && experationV.getId().equals(d.getExpertationId())){
                    experationV.getDecProfessionAwardList().add(d);
                }
            }

            for(ProductProfessionType d :profession){
                if(ObjectUtil.notNull(d) && experationV.getId().equals(d.getExpertationId())){
                    experationV.getProductProfessionTypeList().add(d);
                }
            }

            //TODONE 将上述各子列表 循环遍历给主列表

        }

        return list;

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

        if (StringUtil.isEmpty(returnCause)&&(4==onlineProgress.intValue()||5==onlineProgress.intValue())) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "退回原因不能为空!");
        }
        try{

            if (StringUtil.strLength(returnCause) > 40) {
                throw new CheckedServiceException(CheckedExceptionBusiness.CLINICAL_DECISION, CheckedExceptionResult.NULL_PARAM,
                        "最多只能输入40个字符，请重新输入!");
            }

            expertationDao.updateOnlineProgress(id,onlineProgress,returnCause);

            if(4==onlineProgress.intValue()||5==onlineProgress.intValue()){ // 退回
                // 产生一条动态消息
            }else if(3==onlineProgress.intValue()){ // 通过
                // 产生一条动态消息
            }

            //发用户消息
            systemMessageService.sendWhenExpertationFormAudit(id,pmphUser);

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

    @Override
    public int changeStatus(Integer status,Long id, String sessionId) {
        if (ObjectUtil.isNull(status)){
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "审核状态不能为空!");
        }
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if(!pmphUser.getIsAdmin()&&!pmphUser.getIsDirector()&&status==5){
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "非法操作!");
        }
        int result = expertationDao.changeStatus(status, id);

        if(status == 4){
            //发用户消息
            try {
                systemMessageService.sendWhenExpertationFormAudit(id,pmphUser);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


}
