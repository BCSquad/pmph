/**
 * 
 */
package com.bc.pmpheep.back.controller.orgUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.service.OrgUserService;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.OrgUserManagerVO;
import com.bc.pmpheep.controller.bean.ResponseBean;

/**
 * <p>
 * Description:后台机构用户管理控制层
 * <p>
 * 
 * @author lyc
 * @date 2017年9月26日 下午5:40:52
 */
@Controller
@RequestMapping(value = "/users/org")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OrgUserController {

    @Autowired
    private OrgUserService orgUserService;

    /**
     * Description:分页查询机构用户
     * 
     * @author:lyc
     * @date:2017年9月26日下午5:43:59
     * @Param: OrgUserManagerVO
     * @Return:分页数据集
     */
    @RequestMapping(value = "/list/orguser", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean listOrgUser(@RequestParam("pageSize") Integer pageSize,
    @RequestParam("pageNumber") Integer pageNumber, @RequestParam("username") String username,
    @RequestParam("realname") String realname, @RequestParam("orgName") String orgName) {
        OrgUserManagerVO orgUserManagerVO = new OrgUserManagerVO();
        orgUserManagerVO.setUsername(StringUtil.isEmpty(username)?null:username.trim());
        orgUserManagerVO.setRealname(StringUtil.isEmpty(realname)?null:realname.trim());
        orgUserManagerVO.setOrgName(StringUtil.isEmpty(orgName)?null:orgName.trim());
        PageParameter<OrgUserManagerVO> pageParameter =
        new PageParameter<OrgUserManagerVO>(pageNumber, pageSize, orgUserManagerVO);
        return new ResponseBean(orgUserService.getListOrgUser(pageParameter));
    }

    /**
     * 
     * Description:新增一个机构用户
     * 
     * @author:lyc
     * @date:2017年9月26日下午5:50:20
     * @Param: OrgUser
     * @Return:新增的OrgUser
     */
    @RequestMapping(value = "/add/orguser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addOrgUser(OrgUser orgUser) {
        return new ResponseBean(orgUserService.addOrgUser(orgUser));
    }

    /**
     * 
     * Description:更新机构用户信息
     * 
     * @author:lyc
     * @date:2017年9月26日下午5:53:44
     * @Param: OrgUser
     * @Return:更新影响的行数
     */
    @RequestMapping(value = "/update/orguser", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseBean updateOrgUser(OrgUser orgUser) {
        return new ResponseBean(orgUserService.updateOrgUser(orgUser));
    }

    /**
     * 
     * Description:通过id删除一个OrgUser
     * 
     * @author:lyc
     * @date:2017年9月26日下午5:56:38
     * @Param: id
     * @Return:影响的行数
     */
    @RequestMapping(value = "/delete/orguser/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseBean deleteOrgUserById(Long id) {
        return new ResponseBean(orgUserService.deleteOrgUserById(id));
    }

    /**
     * 
     * Description:根据id查询一个机构用户信息
     * 
     * @author:lyc
     * @date:2017年9月26日下午5:58:48
     * @Param: id
     * @Return:OrgUser
     */
    @RequestMapping(value = "/orguser/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean getOrgUserById(Long id) {
        return new ResponseBean(orgUserService.getOrgUserById(id));
    }

    /**
     * 
     * 
     * 功能描述：在机构用户管理页面添加用户
     * 
     * @param orgUser 添加的用户
     * @return 是否成功
     * 
     */
    @RequestMapping(value = "/add/orguserofback", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addOrgUserOfBack(OrgUser orgUser) {
        return new ResponseBean(orgUserService.addOrgUserOfBack(orgUser));
    }

    /**
     * 
     * 
     * 功能描述：在机构用户管理页面修改用户
     * 
     * @param orgUser 修改的用户（必须传入用户id）
     * @return 是否成功
     * 
     */
    @RequestMapping(value = "/update/orguserofback", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseBean updateOrgUserOfBack(OrgUser orgUser) {
        System.out.println(orgUser.toString());
        return new ResponseBean(orgUserService.updateOrgUserOfBack(orgUser));
    }
    
    /**
     * 功能描述：在机构用户页面增加机构用户
     * 
     * @param orgUser org
     * @return 是否成功
     */
    @RequestMapping(value = "/add/orguserandorgofback", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean addOrgUserAndOrgOfBack(OrgUser orgUser,Org org) {
        return new ResponseBean(orgUserService.addOrgUserAndOrgOfBack(orgUser,org));
    }
}
