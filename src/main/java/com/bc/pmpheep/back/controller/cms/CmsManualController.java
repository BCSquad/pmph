package com.bc.pmpheep.back.controller.cms;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.CmsManual;
import com.bc.pmpheep.back.service.CmsManualService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.CmsManualVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：操作手册邮件控制器
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2018-3-8
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@RestController
@RequestMapping(value = "/cms")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CmsManualController {
    @Autowired
    CmsManualService            cmsManualService;
    // 当前业务类型
    private static final String BUSSINESS_TYPE = "操作手册管理";

    /**
     * 
     * <pre>
     * 功能描述：操作手册列表
     * 使用示范：
     *
     * @param pageNumber 
     * @param pageSize
     * @param cmsContentVO
     * @param request
     * @return
     * </pre>
     */
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询操作手册列表")
    @GetMapping(value = "/manual/list")
    public ResponseBean list(
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @RequestParam(name = "pageSize") Integer pageSize, CmsManualVO cmsManualVO,
    HttpServletRequest request) {
        String title = cmsManualVO.getManualName();
        String userName = cmsManualVO.getUserName();
        if (StringUtil.notEmpty(title)) {
            cmsManualVO.setManualName(StringUtil.toAllCheck(title));
        }
        if (StringUtil.notEmpty(userName)) {
            cmsManualVO.setUserName(StringUtil.toAllCheck(userName));
        }
        PageParameter<CmsManualVO> pageParameter =
        new PageParameter<CmsManualVO>(pageNumber, pageSize, cmsManualVO);
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(cmsManualService.listCmsManual(pageParameter, sessionId));
    }


    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "根据id查询操作手册")
    @GetMapping(value = "/manual/getManual")
    public ResponseBean getManualById(Long id,HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        return new ResponseBean(cmsManualService.cmsManualById(id, sessionId));
    }

    /**
     * 
     * <pre>
     * 功能描述：新增操作手册
     * 使用示范：
     *
     * @param cmsManual CmsManual对象
     * @param request
     * @return
     * </pre>
     */
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增操作手册")
    @PostMapping(value = "/new/manual")
    public ResponseBean manual(CmsManual cmsManual, HttpServletRequest request) {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(cmsManualService.addCmsManual(cmsManual, sessionId));
    }

    /**
     *
     * <pre>
     * 功能描述：新增操作手册
     * 使用示范：
     *
     * @param cmsManual CmsManual对象
     * @param request
     * @return
     * </pre>
     */
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增操作手册")
    @PostMapping(value = "/update/manual")
    public ResponseBean updateManual(CmsManual cmsManual, HttpServletRequest request) {
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(cmsManualService.updateCmsManual(cmsManual, sessionId));
    }
    /**
     * 
     * <pre>
     * 功能描述：删除操作手册
     * 使用示范：
     *
     * @param id 主键ID
     * @return 影响行数
     * </pre>
     */
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除操作手册")
    @DeleteMapping(value = "/manual/{id}/delete")
    public ResponseBean delete(@PathVariable("id") Long id) {
        return new ResponseBean(cmsManualService.deleteCmsManualById(id));
    }
}
