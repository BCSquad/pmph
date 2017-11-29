package com.bc.pmpheep.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.SysOperation;
import com.bc.pmpheep.back.service.SysOperationService;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * 
 * <pre>
 * 功能描述：系统操作日志
 * 使用示范：
 * 
 * 
 * @author (作者) nyz
 * 
 * @since (该版本支持的JDK版本) ：JDK 1.6或以上
 * @version (版本) 1.0
 * @date (开发日期) 2017-11-15
 * @modify (最后修改时间) 
 * @修改人 ：nyz 
 * @审核人 ：
 * </pre>
 */
@Controller
@RequestMapping(value = "/sys/operation")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SysOperationController {
    @Autowired
    SysOperationService sysOperationService;

    /**
     * 
     * <pre>
     * 功能描述：SysOperation-添加
     * 使用示范：
     *
     * @param sysOperation SysOperation 对象
     * @return SysOperation
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public ResponseBean saveSysOperation(SysOperation sysOperation) {
        return new ResponseBean(sysOperationService.addSysOperation(sysOperation));
    }

    /**
     * 
     * <pre>
     * 功能描述：分页查询条件查询SysOperation 系统日志列表
     * 使用示范：
     *
     * @param pageNumber 当前页
     * @param pageSize 页面数据条数
     * @param sysOperation
     * @return 分页数据集
     * </pre>
     */
    @ResponseBody
    @RequestMapping(value = "/operations", method = RequestMethod.GET)
    public ResponseBean listSysOperation(
    @RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
    @RequestParam(name = "pageSize") Integer pageSize, SysOperation sysOperation) {
        PageParameter<SysOperation> pageParameter =
        new PageParameter<SysOperation>(pageNumber, pageSize, sysOperation);
        return new ResponseBean(sysOperationService.getListSysOperation(pageParameter));
    }
}
