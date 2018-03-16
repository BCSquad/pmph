package com.bc.pmpheep.back.service.test;

import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;

import com.bc.pmpheep.back.po.PmphDepartment;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.test.BaseTest;
import com.bc.pmpheep.back.service.PmphDepartmentService;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.vo.PmphUserDepartmentVO;

/**
 * AreaDao 单元测试
 *
 * @author mryang
 */
public class PmphDepartmentSeviceTest extends BaseTest {
	Logger logger = LoggerFactory.getLogger(PmphDepartmentSeviceTest.class);

	@Resource
	private PmphDepartmentService pmphDepartmentService;
	
	@Resource
	private PmphUserService pmphUserService;
	
	Random random = new Random();
	PmphDepartment pmphDepartment = new PmphDepartment(5L, "String path", "String dpName", random.nextInt(1000000),
			"String note");

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddPmphDepartment() {
		pmphDepartmentService.addPmphDepartment(pmphDepartment);
		Assert.assertTrue("添加失败", pmphDepartment.getId() > 0);
	}

	@Test
	@Rollback(Const.ISROLLBACK)
	public void testAddDepartment() {
		pmphDepartmentService.add(pmphDepartment);
		Assert.assertTrue("添加失败", pmphDepartment.getId() > 0);
	}

	@Test
	public void testUpdatePmphDepartment() {
		pmphDepartmentService.addPmphDepartment(pmphDepartment);
		pmphDepartment.setDpName(String.valueOf(random.nextLong()));
		Assert.assertTrue("更新失败", pmphDepartmentService.updatePmphDepartment(pmphDepartment) > 0);
	}

	@Test
	public void testDeletePmphDepartment() {
		pmphDepartmentService.addPmphDepartment(pmphDepartment);
		Assert.assertTrue("删除失败", pmphDepartmentService.deletePmphDepartmentById(pmphDepartment.getId()) >= 0);
	}

	@Test
	public void testDeletePmphDepartmentBatch() {
		pmphDepartmentService.addPmphDepartment(pmphDepartment);
		Assert.assertTrue("删除失败", pmphDepartmentService.deletePmphDepartmentBatch(pmphDepartment.getId()) >= 0);
	}

	@Test
	public void testGetPmphDepartment() {
		pmphDepartmentService.addPmphDepartment(pmphDepartment);
		Assert.assertNotNull("获取数据失败", pmphDepartmentService.getPmphDepartmentById(pmphDepartment.getId()));
	}

	@Test
	public void testListPmphDepartment() {
//		pmphDepartmentService.addPmphDepartment(pmphDepartment);
		PmphUserDepartmentVO departmentVO = pmphDepartmentService.listPmphDepartment(null);
		Assert.assertNotNull("获取数据失败", departmentVO);
	}

	@Test
	public void testListPmphUserDepartmentByDpName() {
		pmphDepartmentService.addPmphDepartment(pmphDepartment);
		List<PmphUserDepartmentVO> list = pmphDepartmentService
				.listPmphUserDepartmentByDpName(pmphDepartment.getDpName());
		Assert.assertNotNull("获取数据失败", list);
	}
	 //	部门对比
	@Test
	@Rollback(Const.ISROLLBACK)
	public void department(){
		//查询现在所有父级部门，
		PmphUserDepartmentVO departmentVO = pmphDepartmentService.listPmphDepartment(null);
		//部门总数为28，超过则是多余部门
		if(ObjectUtil.notNull(departmentVO)&&departmentVO.getSonDepartment().size()>28){
			for (PmphUserDepartmentVO pmphDepartment : departmentVO.getSonDepartment()) {
				//查询该部门下的所有成员
				List<PmphUser> pmphUsers=pmphUserService.listPmphUserByDepartmentId(pmphDepartment.getId());
				switch (pmphDepartment.getDpName()) {
				case "出版社科室1":
					if(ObjectUtil.notNull(pmphUsers)){
						for (PmphUser pmphUser : pmphUsers) {//把该部门人员移到人民卫生出版社部门下
							pmphUser.setDepartmentId(0L);
							pmphUserService.updateUser(pmphUser);
						}
					}
					//删除多余的部门
					pmphDepartmentService.deletePmphDepartmentBatch(pmphDepartment.getId());
					break;
				case "公司领导":
					//查询该部门下的所有成员
					if(ObjectUtil.notNull(pmphUsers)){
						for (PmphUser pmphUser : pmphUsers) {//把该部门人员移到人民卫生出版社部门下
							pmphUser.setDepartmentId(0L);
							pmphUserService.updateUser(pmphUser);
						}
					}
					//删除多余的部门
					pmphDepartmentService.deletePmphDepartmentBatch(pmphDepartment.getId());
					break;
				case "其他":
					//查询该部门下的所有成员
					if(ObjectUtil.notNull(pmphUsers)){
						for (PmphUser pmphUser : pmphUsers) {//把该部门人员移到人民卫生出版社部门下
							pmphUser.setDepartmentId(0L);
							pmphUserService.updateUser(pmphUser);
						}
					}
					//删除多余的部门
					pmphDepartmentService.deletePmphDepartmentBatch(pmphDepartment.getId());
					break;
				case "农协":
					//查询该部门下的所有成员
					if(ObjectUtil.notNull(pmphUsers)){
						for (PmphUser pmphUser : pmphUsers) {//把该部门人员移到人民卫生出版社部门下
							pmphUser.setDepartmentId(0L);
							pmphUserService.updateUser(pmphUser);
						}
					}
					//删除多余的部门
					pmphDepartmentService.deletePmphDepartmentBatch(pmphDepartment.getId());
					break;
				default:
					break;
				}
			}
		}
	 }
}
