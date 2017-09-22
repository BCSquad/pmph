package com.bc.pmpheep.back.controller.group;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.service.PmphGroupService;
import com.bc.pmpheep.back.vo.PmphGroupListVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * @author MrYang
 * @CreateDate 2017年9月21日 下午3:59:34
 *
 *
 */
@Controller
@RequestMapping(value = "/groups")
public class GroupController {

    @Autowired
    private PmphGroupService pmphGroupService;

    /**
     * 根据小组名称模糊查询获取当前用户的小组
     *
     * @author Mryang
     * @param pmphGroup
     * @return
     * @createDate 2017年9月21日 下午4:02:57
     */
    @RequestMapping(value = "/getList")
    @ResponseBody
    public ResponseBean getList(PmphGroup pmphGroup) {
        /*
        ResponseBean<List<PmphGroupListVO>> responseBean = new ResponseBean<>();
        try {
            List<PmphGroupListVO> pmphGroupList = pmphGroupService.getList(pmphGroup);
            responseBean.setData(pmphGroupList);
        } catch (CheckedServiceException e) {
            responseBean.setCode(ResponseBean.FAILURE);
            responseBean.setMsg(e.toString());
        } catch (Exception e) {
            responseBean.setCode(ResponseBean.FAILURE);
            responseBean.setMsg("未知错误,请联系管理员！");
        }
        return responseBean;
        */
        
        /* --------- 以下是正确的示例 ---------
         *
         * 在ResponseBean初始化时，通过ResponseBeanAop对其构造函数进行切面编程，
         * 因此返回时<务必>要使用ResponseBean的构造函数即 new ResponseBean(anything)
        */
        return new ResponseBean(pmphGroupService.getList(pmphGroup));
    }
}
