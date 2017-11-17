package com.bc.pmpheep.back.controller.cms;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.CmsContentService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.vo.CmsContentVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：社外内容审核 控制器
 * 使用示范：
 * 
 * 
 * &#64;author (作者) nyz
 * 
 * &#64;since (该版本支持的JDK版本) ：JDK 1.6或以上
 * &#64;version (版本) 1.0
 * &#64;date (开发日期) 2017-11-2
 * &#64;modify (最后修改时间) 
 * &#64;修改人 ：nyz 
 * &#64;审核人 ：
 * </pre>
 */
@Controller
@RequestMapping(value = "/cms")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CmsContentCheckController {
	@Autowired
	CmsContentService cmsContentService;
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "公告管理";

	/**
	 * 
	 * <pre>
	 * 功能描述：分页查询条件查询《社外内容审核》列表
	 * 使用示范：
	 *
	 * &#64;param pageNumber 当前页
	 * &#64;param pageSize 页面数据条数
	 * &#64;param cmsContentVO
	 * &#64;param sessionId
	 * &#64;return 分页数据集
	 * </pre>
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询公告管理列表")
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public ResponseBean check(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize") Integer pageSize, CmsContentVO cmsContentVO, HttpServletRequest request) {
		PageParameter<CmsContentVO> pageParameter = new PageParameter<CmsContentVO>(pageNumber, pageSize, cmsContentVO);
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(cmsContentService.listContentCheck(pageParameter, sessionId));
	}

	/**
	 * 
	 * <pre>
	 * 功能描述：社外内容审核操作(通过/拒绝)
	 * 使用示范：
	 *
	 * &#64;param id 主键ID
	 * &#64;param authStatus 审核状态
	 * &#64;return 影响行数
	 * </pre>
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询系统消息列表")
	@RequestMapping(value = "/check/content", method = RequestMethod.PUT)
	public ResponseBean content(@RequestParam("id") Long id, @RequestParam("authStatus") Short authStatus,
			HttpServletRequest request) {
		String sessionId = CookiesUtil.getSessionId(request);
		return new ResponseBean(cmsContentService.checkContentById(id, authStatus, sessionId));
	}


    /**
     * 
     * <pre>
     * 功能描述：社外内容审核操作(逻辑删除)
     * 使用示范：
     *
     * @param ids 主键ID集合
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "删除一条公告管理数据")
    @RequestMapping(value = "/check/{id}/update", method = RequestMethod.DELETE)
    public ResponseBean update(@PathVariable("id") Long id) {
        return new ResponseBean(cmsContentService.deleteCmsContentById(id));
    }
    /**
     * 
     * <pre>
     * 功能描述：社外内容审核操作(批量逻辑删除)
     * 使用示范：
     *
     * @param ids 主键ID集合
     * @return 影响行数
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "批量删除公告管理数据")
    @RequestMapping(value = "/check/delete", method = RequestMethod.DELETE)
    public ResponseBean delete(@RequestParam("ids") List<Long> ids) {
        return new ResponseBean(cmsContentService.deleteCmsContentByIds(ids));
    }
}
