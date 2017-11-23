package com.bc.pmpheep.back.controller.material;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.service.MaterialExtraService;
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
    @LogDetail(businessType = BUSINESS_TYPE, logRemark = "查看教材通知内容，备注详情")
    @RequestMapping(value = "/deatil", method = RequestMethod.GET)
    public ResponseBean deatil(@RequestParam("materialId") Long materialId) {
        return new ResponseBean(materialExtraService.getMaterialExtraAndNoticeDetail(materialId));
    }

}
