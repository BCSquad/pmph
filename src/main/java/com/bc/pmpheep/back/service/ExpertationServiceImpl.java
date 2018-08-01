package com.bc.pmpheep.back.service;

import com.bc.pmpheep.back.dao.ExpertationDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.PmphRole;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.vo.ExpertationCountnessVO;
import com.bc.pmpheep.back.vo.ExpertationVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExpertationServiceImpl implements ExpertationService{

    @Autowired
    ExpertationDao expertationDao;

    @Autowired
    PmphUserService pmphUserService;

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

        //若管理员登录 则不传入此id 查询全部申报
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
        }

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
        List<ExpertationCountnessVO> list = new ArrayList<>();
        int totalCount = 0;
        if(ttype == 2){ //2.内容分类
            totalCount = expertationDao.getCountListGroupByContentTypeCount(pageParameter);
            list = expertationDao.getCountListGroupByContentType(pageParameter);
        }else{ //1.学科分类
            totalCount = expertationDao.getCountListGroupBySubjectTypeCount(pageParameter);
            list = expertationDao.getCountListGroupBySubjectType(pageParameter);
        }



        PageResult pageResult = new PageResult();
        pageResult.setTotal(totalCount);
        pageResult.setRows(list);

        return pageResult;
    }
}
