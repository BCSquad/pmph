package com.bc.pmpheep.back.controller.shiro;

import java.util.List;

import com.bc.pmpheep.annotation.LogDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.service.WriterPermissionService;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：PmphPermissionController
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-9-20
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@SuppressWarnings("all")
@RequestMapping("/permission/writer")
@Controller
public class WriterPermissionController {
    Logger                  logger = LoggerFactory.getLogger(WriterPermissionController.class);
    @Autowired
    WriterPermissionService writerPermissionService;

    // 当前业务类型
    private static final String BUSSINESS_TYPE = "个人用户权限";

    /**
     * 
     * <pre>
     * 功能描述：返回到列表显示页面
     * 使用示范：
     *
     * @param model
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseBean list() {
        // 查询到所有的权限列表
        List<WriterPermission> resourceList = writerPermissionService.getListResource();
        return new ResponseBean(resourceList);
    }

    /**
     * 
     * <pre>
     * 功能描述：跳转到添加权限的页面
     * 使用示范：
     *
     * @param model
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "跳转到添加权限的页面")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ResponseBean add() {
        return new ResponseBean(new WriterPermission());
    }

    /**
     * 
     * <pre>
     * 功能描述：跳转到添加权限的页面
     * 使用示范：
     *
     * @param pmphPermission
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "添加")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseBean add(WriterPermission writerPermission) {
        logger.debug(writerPermission.toString());
        return new ResponseBean(writerPermissionService.addWriterPermission(writerPermission));
    }

    /**
     * 
     * <pre>
     * 功能描述：跳转到更新权限的页面
     * 使用示范：
     *
     * @param id
     * @param model
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "跳转到更新权限的页面")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseBean update(@PathVariable("id") Long id) {
        WriterPermission resource = writerPermissionService.get(id);
        return new ResponseBean(resource);
    }

    /**
     * 
     * <pre>
     * 功能描述：更新权限的方法
     * 使用示范：
     *
     * @param pmphPermission
     * @return
     * </pre>
     */
    @ResponseBody
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "更新")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseBean update(WriterPermission writerPermission) {
        logger.debug(writerPermission.toString());
        Integer count = writerPermissionService.updateWriterPermissionById(writerPermission);
        return new ResponseBean(count);
    }
}
