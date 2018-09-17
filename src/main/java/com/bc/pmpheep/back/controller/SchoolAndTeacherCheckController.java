package com.bc.pmpheep.back.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.OrgService;
import com.bc.pmpheep.back.service.OrgUserService;
import com.bc.pmpheep.back.service.WriterUserCertificationService;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.OrgVO;
import com.bc.pmpheep.back.vo.WriterUserManagerVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：学校/教师审核 控制器
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-10-23
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Controller
@RequestMapping(value = "/auth")
public class SchoolAndTeacherCheckController {
    @Autowired
    OrgService                     orgService;
    @Autowired
    OrgUserService                 orgUserService;
    @Autowired
    WriterUserService              writerUserService;
    @Autowired
    WriterUserCertificationService writerUserCertificationService;
    // 当前业务类型
    private static final String    BUSSINESS_TYPE = "学校/教师审核";

    /**
     * 
     * <pre>
     * 功能描述：获取学校管理员审核列表
     * 使用示范：
     *
     * @param pageNumber 当前页
     * @param pageSize 页面大小
     * @param orgVO orgVO
     * @return 分页数据集
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询系统消息列表")
    @RequestMapping(value = "/orgList", method = RequestMethod.GET)
    public ResponseBean orgList(
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @RequestParam("orgName") String orgName, @RequestParam("realname") String realname,
    @RequestParam("progress") Integer progress, @RequestParam(name = "pageSize") Integer pageSize) {
        PageParameter pageParameter = new PageParameter<>();
        OrgVO orgVO = new OrgVO();
        if (StringUtil.notEmpty(orgName)) {
            orgVO.setOrgName(orgName.replaceAll(" ", ""));
        }
        if (StringUtil.notEmpty(realname)) {
            orgVO.setRealname(realname.replaceAll(" ", ""));
        }
        orgVO.setProgress(progress);
        pageParameter.setPageNumber(pageNumber);
        pageParameter.setPageSize(pageSize);
        pageParameter.setParameter(orgVO);
        return new ResponseBean(orgService.getSchoolAdminCheckList(pageParameter));
    }

    /**
     * 
     * <pre>
     * 功能描述：学校管理员审核、退回操作
     * 使用示范：
     *
     * @param progress 审核状态
     * @param orgUserIds 用户IDs
     * @return
     * </pre>
     * @throws IOException 
     * @throws CheckedServiceException 
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询系统消息列表")
    @RequestMapping(value = "/orgCheck", method = RequestMethod.PUT)
    public ResponseBean orgCheck(@RequestParam(name = "progress") Integer progress,
    @RequestParam(name = "orgUserIds") List<Long> orgUserIds,@RequestParam(name = "backReason") String backReason,HttpSession session) throws CheckedServiceException, IOException {
        if (ObjectUtil.isNull(session)||ObjectUtil.isNull((PmphUser) session.getAttribute(Const.SESSION_PMPH_USER))) {
            throw new CheckedServiceException(CheckedExceptionBusiness.SESSION,
                    CheckedExceptionResult.USER_SESSION,
                    "当前Session会话已过期，请重新登录!");
        }
        PmphUser pmphUser = (PmphUser) session.getAttribute(Const.SESSION_PMPH_USER);
        return new ResponseBean(orgUserService.updateOrgUserProgressById(progress, orgUserIds,backReason,pmphUser));
    }

    /**
     * 
     * <pre>
     * 功能描述：获取教师审核列表
     * 使用示范：
     *
     * @param pageNumber 当前页
     * @param pageSize 页面大小
     * @param writerUserManagerVO 
     * @return 分页数据集
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询系统消息列表")
    @RequestMapping(value = "/writerList", method = RequestMethod.GET)
    public ResponseBean writerList(
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @RequestParam(name = "pageSize") Integer pageSize, @RequestParam("realname") String realname,
    @RequestParam("orgName") String orgName, @RequestParam("progress") Short progress) {
        PageParameter pageParameter = new PageParameter<>();
        WriterUserManagerVO writerUserManagerVO = new WriterUserManagerVO();
        if (StringUtil.notEmpty(orgName)) {
            writerUserManagerVO.setOrgName(orgName.replaceAll(" ", ""));// 去除空格
        }
        if (StringUtil.notEmpty(realname)) {
            writerUserManagerVO.setRealname(realname.replaceAll(" ", ""));
        }
        writerUserManagerVO.setProgress(progress);
        pageParameter.setPageNumber(pageNumber);
        pageParameter.setPageSize(pageSize);
        pageParameter.setParameter(writerUserManagerVO);
        return new ResponseBean(writerUserService.getTeacherCheckList(pageParameter));
    }

    /**
     * 
     * <pre>
     * 功能描述：教师审核、退回操作
     * 使用示范：
     *
     * @param progress 审核状态
     * @param userIds 作家用户Ids
     * @return
     * </pre>
     * @throws Exception 
     * @throws CheckedServiceException 
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询系统消息列表")
    @RequestMapping(value = "/writerCheck", method = RequestMethod.PUT)
    public ResponseBean writerCheck(@RequestParam("progress") Short progress,
    @RequestParam("userIds") Long[] userIds,@RequestParam("backReason") String backReason,HttpServletRequest request) throws CheckedServiceException, Exception {
        return new ResponseBean(
                                writerUserCertificationService.updateWriterUserCertificationProgressByUserId(progress,
                                                                                                             userIds,backReason,request));
    }
}
