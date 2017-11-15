package com.bc.pmpheep.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.OrgService;
import com.bc.pmpheep.back.service.OrgUserService;
import com.bc.pmpheep.back.service.WriterUserCertificationService;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.back.vo.OrgVO;
import com.bc.pmpheep.back.vo.WriterUserManagerVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

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
    @RequestMapping(value = "/orgs/list", method = RequestMethod.GET)
    public ResponseBean listSchoolAdminCheck(
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @RequestParam(name = "pageSize") Integer pageSize, OrgVO orgVO) {
        PageParameter<OrgVO> pageParameter = new PageParameter<OrgVO>(pageNumber, pageSize, orgVO);
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
     */
    @ResponseBody
    @RequestMapping(value = "/orgs/check", method = RequestMethod.PUT)
    public ResponseBean updateSchoolAdminCheck(@RequestParam(name = "progress") Integer progress,
    @RequestParam(name = "orgUserIds") List<Long> orgUserIds) {
        return new ResponseBean(orgUserService.updateOrgUserProgressById(progress, orgUserIds));
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
    @RequestMapping(value = "/writers/list", method = RequestMethod.GET)
    public ResponseBean listTeacherCheck(
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @RequestParam(name = "pageSize") Integer pageSize, WriterUserManagerVO writerUserManagerVO) {
        PageParameter<WriterUserManagerVO> pageParameter =
        new PageParameter<WriterUserManagerVO>(pageNumber, pageSize, writerUserManagerVO);
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
     */
    @ResponseBody
    @RequestMapping(value = "/writers/check", method = RequestMethod.PUT)
    public ResponseBean updateTeacherCheck(@RequestParam(name = "progress") Short progress,
    @RequestParam(name = "userIds") String[] userIds) {
        return new ResponseBean(
                                writerUserCertificationService.updateWriterUserCertificationProgressByUserId(progress,
                                                                                                             userIds));
    }
}
