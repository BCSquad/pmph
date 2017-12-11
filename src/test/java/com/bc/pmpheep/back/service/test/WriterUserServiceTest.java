package com.bc.pmpheep.back.service.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.dao.WriterUserDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.WriterPermission;
import com.bc.pmpheep.back.po.WriterRole;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.po.WriterUserRole;
import com.bc.pmpheep.back.service.WriterPermissionService;
import com.bc.pmpheep.back.service.WriterRoleService;
import com.bc.pmpheep.back.service.WriterUserRoleService;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.vo.WriterUserManagerVO;
import com.bc.pmpheep.test.BaseTest;

/**
 * 
 * @author 曾庆峰 <791038935@qq.com>
 * 
 */
@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class WriterUserServiceTest extends BaseTest {

    Logger                  logger = LoggerFactory.getLogger(WriterUserServiceTest.class);

    @Resource
    WriterUserService       writerUserService;
    @Autowired
    WriterUserDao           writerUserDao;
    @Resource
    WriterUserRoleService   writerUserRoleService;
    @Resource
    WriterRoleService       writerRoleService;
    @Resource
    WriterPermissionService writerPermissionService;

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testAddWriterUserService() {
        WriterUser writerUser = this.addWriterUser();
        Assert.assertNotNull("不否保存成功", writerUser.getId());
        // logger.info("添加了{}", writerUser.toString());
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testDeleteWriterUserServiceById() {
        WriterUser writerUser = this.addWriterUser();
        Assert.assertNotNull("删除失败", writerUserService.delete(writerUser.getId()));
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testUpdateWriterUserById() {
        WriterUser writerUser = this.addWriterUser();
        writerUser.setPassword("1111");
        writerUser = writerUserService.update(writerUser);
        Assert.assertNotNull("是否更新成功", writerUser);
    }

    @Test
    public void testGet() {
        WriterUser writerUser = this.addWriterUser();
        WriterUser writerUser1 = new WriterUser();
        writerUser1.setRealname("姓名");
        writerUser1 = writerUserService.get(writerUser.getId());
        Assert.assertNotNull("是否查询成功", writerUser1);
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetListWriterUserVO() {
        WriterUser writerUser = this.addWriterUser();
        PageParameter pageParameter = new PageParameter<>();
        PageResult pageResult = new PageResult<>();
        WriterUserManagerVO managerVO = new WriterUserManagerVO();
        pageParameter.setParameter(managerVO);
        pageParameter.setPageSize(15);
        pageResult = writerUserService.getListWriterUser(pageParameter);
        Assert.assertNotNull("是否查询成功", pageResult);
        // logger.info("查找成功{}", pageResult);
    }

    /**
     * PmphUser 添加Test
     */
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testAddPmphUserTest() {
        List<Long> roleIdList = new ArrayList<Long>();
        roleIdList.add(1L);
        roleIdList.add(2L);
        roleIdList.add(3L);
        WriterUser ps = writerUserService.add(new WriterUser("test1", "123"), roleIdList);// 给单用户添加多个角色
        Assert.assertNotNull(ps);
    }

    /**
     * PmphUser 添加删除
     */
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testDeletePmphUserTest() {
        WriterUser writerUser = this.addWriterUser();
        Assert.assertTrue("删除失败", writerUserService.delete(writerUser.getId()) > 0);
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testDelete() {
        WriterRole WriterRole = this.addWriterRole();
        WriterUser writerUser = writerUserService.add(new WriterUser("test1", "123"));
        WriterUserRole writerUserRole =
        writerUserRoleService.addWriterUserRole(new WriterUserRole(writerUser.getId(),
                                                                   WriterRole.getId()));
        List<Long> userIdList = new ArrayList<Long>();
        userIdList.add(writerUserRole.getId());
        Assert.assertTrue("是否删除成功",
                          writerUserService.deleteUserAndRole(userIdList) > 0 ? true : false);
    }

    /**
     * 查询
     */
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetListsTest() {
        WriterUser writerUser = this.addWriterUser();
        // WriterUser wtUser;
        // List<WriterUser> pmUsers;
        // List<WriterPermission> listPermissions;
        // wtUser = writerUserService.login("test1", "123");
        List<WriterRole> pr = writerUserService.getListUserRole(writerUser.getId());
        Assert.assertNotNull("获取失败", pr);
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetListRoleSnByUser() {
        WriterUser writerUser = this.addWriterUser();
        List<String> listRoleNameList = writerUserService.getListRoleSnByUser(writerUser.getId());
        Assert.assertNotNull("查询失败", listRoleNameList);// 查询所有
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetListAllResource() {
        WriterUser writerUser = this.addWriterUser();
        Integer writerPermission =
        writerPermissionService.addWriterPermission(new WriterPermission(2L, "PATH", "name",
                                                                         "menu_name", "url", false,
                                                                         null, null, null, null));
        Assert.assertNotNull("查询失败", writerUserService.getListAllResource(writerUser.getId()));// 查询所有
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetListByRole() {
        WriterUser writerUser = this.addWriterUser();
        Assert.assertNotNull("查询失败", writerUserService.getListByRole(writerUser.getId()));// 查询所有
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetList() {
        WriterUser writerUser = this.addWriterUser();
        Assert.assertNotNull("查询失败", writerUserService.getList());
        ;// 查询所有
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetId() {
        WriterUser writerUser = this.addWriterUser();
        Assert.assertNotNull("查询失败", writerUserService.get(writerUser.getId()));// 查询所有
    }

    // @Test
    // @Rollback(Const.ISROLLBACK)
    // public void testLogin(){
    // WriterUser writerUser=this.addWriterUser();
    // Assert.assertNotNull("获取失败",writerUserService.login("user1","123"));// 查询所有
    // }
    // @Test
    // @Rollback(Const.ISROLLBACK)
    // public void testGetByUsernameAndPassword() {
    // WriterUser writerUser=this.addWriterUser();
    // writerUser = writerUserService.getByUsernameAndPassword(writerUser.getUsername(),
    // writerUser.getPassword());// 按UserName
    // Assert.assertNotNull("获取失败",writerUser);
    // }

    /**
     * PmphUser 更新方法
     */
    @Test
    @Rollback(Const.ISROLLBACK)
    public void testUpdatePmphUserTest() {
        WriterUser writerUser = this.addWriterUser();
        List<Long> userIdList = new ArrayList<Long>();
        userIdList.add(1L);
        userIdList.add(2L);
        writerUser.setRealname("姓名");
        writerUser.setPassword("1234");
        WriterUser pu = writerUserService.update(writerUser);
        Assert.assertNotNull("是否更新成功", pu);
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testUpdate() {
        WriterUser writerUser = this.addWriterUser();
        List<Long> userIdList = new ArrayList<Long>();
        userIdList.add(1L);
        userIdList.add(2L);
        writerUser.setRealname("姓名");
        writerUser.setPassword("1234");
        WriterUser pu1 = writerUserService.update(writerUser, userIdList);
        Assert.assertNotNull("是否更新成功", pu1);
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetWriterUserListByOrgIds() {
        WriterUser writerUser =
        writerUserService.add(new WriterUser("test00", "123", false, 1L, null, null, null, null,
                                             null,null, null, null, null, null, null, null, null, null,
                                             null, null, false, null, null, null, null, false,
                                             false, null, null, null, null, false, null, null));
        List<Long> orgIds = new ArrayList<Long>();
        orgIds.add(writerUser.getOrgId());
        List<WriterUser> list = writerUserService.getWriterUserListByOrgIds(orgIds);
        Assert.assertTrue("获取数据失败", list.size() > 0);
    }

    @Test
    public void testAddWriterUserOfBack() {
        WriterUser writerUser = new WriterUser();
        writerUser.setUsername("username");
        writerUser.setRealname("用户名");
        writerUser.setNickname("昵称");
        writerUser.setOrgId(727L);
        String result = writerUserService.addWriterUserOfBack(writerUser);
        Assert.assertTrue("添加失败", result.equals("SUCCESS"));
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testUpdateWriterUserOfBack() {
        WriterUser writerUser = this.addWriterUser();
        writerUser.setUsername(writerUser.getUsername());
        writerUser.setPassword("789");
        writerUser.setRealname("ZZZ");
        writerUser.setNickname("QQQ");
        writerUser.setAvatar("---");
        writerUser.setRealname("UUU");
        String result = writerUserService.updateWriterUserOfBack(writerUser);
        Assert.assertTrue("更新失败", result.equals("SUCCESS"));
    }

    @Test
    @Rollback(Const.ISROLLBACK)
    public void testGetTeacherCheckList() {
        WriterUserManagerVO writerUserManagerVO = new WriterUserManagerVO();
        writerUserManagerVO.setRealname(null);
        PageParameter<WriterUserManagerVO> pageParameter =
        new PageParameter<WriterUserManagerVO>(1, 15, writerUserManagerVO);
        PageResult<WriterUserManagerVO> pageResult = new PageResult<WriterUserManagerVO>();
        pageResult = writerUserService.getTeacherCheckList(pageParameter);
        Assert.assertNotNull("获取失败", pageResult);
    }

    private WriterUser addWriterUser() {
        WriterUser writerUser = writerUserService.add(new WriterUser("user001", "123"));
        return writerUser;
    }

    private WriterRole addWriterRole() {
        WriterRole writerRole =
        writerRoleService.add(new WriterRole("角色", false, null, null, null, null));
        return writerRole;
    }

    private WriterUserRole addWriterUserRole() {
        WriterUserRole writerUserRolew =
        writerUserRoleService.addWriterUserRole(new WriterUserRole(1L, 2L));
        return writerUserRolew;
    }
}
