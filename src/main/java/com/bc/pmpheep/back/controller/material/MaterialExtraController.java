package com.bc.pmpheep.back.controller.material;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.service.MaterialExtraService;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.vo.MateriaHistorylVO;
import com.bc.pmpheep.back.vo.MaterialExtraVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：教材通知备注 控制器
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-11-22
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Controller
@SuppressWarnings("all")
@RequestMapping(value = "/material/extra")
public class MaterialExtraController {
    @Autowired
    MaterialExtraService        materialExtraService;

    private static final String BUSINESS_TYPE = "教材通知备注";

    /**
     * 
     * <pre>
     * 功能描述：更新教材通知内容，备注信息
     * 使用示范：
     *
     * @param materialExtraVO 
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSINESS_TYPE, logRemark = "更新教材通知内容，备注信息")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseBean update(MaterialExtraVO materialExtraVO) {
        try {
            return new ResponseBean(
                                    materialExtraService.updateMaterialExtraAndNoticeFile(materialExtraVO));
        } catch (IOException e) {
            return new ResponseBean(e);
        }
    }

    /**
     * 
     * <pre>
     * 功能描述：根据教材ID查询教材通知详情及附件
     * 使用示范：
     *
     * @param materialId 教材ID
     * @return Map<String, Object> 集合
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSINESS_TYPE, logRemark = "查看教材通知内容，备注详情")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResponseBean detail(@RequestParam("materialId") Long materialId) {
        return new ResponseBean(materialExtraService.getMaterialExtraAndNoticeDetail(materialId));
    }

    /**
     * 
     * <pre>
     * 功能描述：查询历史教材通知列表
     * 使用示范：
     *
     * @param pageNumber 当前页数
     * @param pageSize 当前页条数
     * @param request
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSINESS_TYPE, logRemark = "查询历史教材通知列表")
    @RequestMapping(value = "/history", method = RequestMethod.GET)
    public ResponseBean material(
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
    HttpServletRequest request) {
        PageParameter<MateriaHistorylVO> pageParameter = new PageParameter<>(pageNumber, pageSize);
        MateriaHistorylVO materiaHistorylVO = new MateriaHistorylVO();
        pageParameter.setParameter(materiaHistorylVO);
        String sessionId = CookiesUtil.getSessionId(request);
        return new ResponseBean(materialExtraService.listMaterialHistory(pageParameter, sessionId));
    }

    /**
     * 
     * <pre>
     * 功能描述：发布教材通知
     * 使用示范：
     *
     * @param request
     * @param materialId 教材ID
     * @param orgIds 机构id集合
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSINESS_TYPE, logRemark = "教材通知发布")
    @RequestMapping(value = "/published", method = RequestMethod.POST)
    public ResponseBean published(HttpServletRequest request,
    @RequestParam("materialId") Long materialId, @RequestParam("orgIds") List<Long> orgIds) {
        String sessionId = CookiesUtil.getSessionId(request);
        try {
            return new ResponseBean(materialExtraService.noticePublished(materialId,
                                                                         orgIds,
                                                                         sessionId));

        } catch (IOException e) {
            return new ResponseBean(e);
        }
    }
}
