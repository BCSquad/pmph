package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphGroupDao;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.Tools;
import com.bc.pmpheep.back.vo.PmphGroupListVO;
import com.bc.pmpheep.general.bean.ImageType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphGroupService 接口实现
 * 
 * @author Mryang
 * 
 */
@Service
public class PmphGroupServiceImpl extends BaseService implements PmphGroupService {
	@Autowired
	private PmphGroupDao pmphGroupDao;
	@Autowired
	private FileService fileService;
	@Autowired
	private PmphGroupMemberService pmphGroupMemberService;

	/**
	 * 
	 * @param pmphGroup
	 *            实体对象
	 * @return 带主键的PmphGroup
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphGroup addPmphGroup(PmphGroup pmphGroup) throws CheckedServiceException {
		if (null == pmphGroup) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"参数对象为空");
		}
		if (Tools.isEmpty(pmphGroup.getGroupName())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"小组名称为空不允许新增");
		}
		pmphGroupDao.addPmphGroup(pmphGroup);
		return pmphGroup;
	}

	/**
	 * 
	 * @param id
	 *            主键ID
	 * @return PmphGroup
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphGroup getPmphGroupById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return pmphGroupDao.getPmphGroupById(id);
	}

	/**
	 * 
	 * @param PmphGroup
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public String deletePmphGroupById(PmphGroup pmphGroup, String sessionId) throws CheckedServiceException {
		String result = "FAIL";
		if (pmphGroupMemberService.isFounder(pmphGroup.getId(), sessionId)) {
			if (null == pmphGroup.getId()) {
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
						"主键为空");
			}
			int num = pmphGroupDao.deletePmphGroupById(pmphGroup.getId());
			if (num > 0) {
				result = pmphGroupMemberService.deletePmphGroupMemberOnGroup(pmphGroup.getId());
			}
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"该用户没有此操作权限");
		}
		return result;
	}

	/**
	 * @param PmphGroup
	 * @return 影响行数
	 * @throws CheckedServiceException
	 * @throws IOException
	 * @update Mryang 2017.10.13 15:20
	 */
	@Override
	public Integer updatePmphGroup(MultipartFile file, PmphGroup pmphGroup)
			throws CheckedServiceException, IOException {
		if (null == pmphGroup) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"参数对象为空");
		}
		if (null == pmphGroup.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		// 修改小组图片
		if (null != file) {
			Long id = pmphGroup.getId();
			PmphGroup pmphGroupOld = getPmphGroupById(id);
			if (null != pmphGroupOld && null != pmphGroupOld.getGroupImage()
					&& !"".equals(pmphGroupOld.getGroupImage())) {
				fileService.remove(pmphGroupOld.getGroupImage());
			}
			String newGroupImage = fileService.save(file, ImageType.GROUP_AVATAR, 0);
			pmphGroup.setGroupImage(newGroupImage);
		}
		return pmphGroupDao.updatePmphGroup(pmphGroup);
	}

	/**
	 * 
	 * @introduction 根据小组名称模糊查询获取当前用户的小组
	 * @author Mryang
	 * @createDate 2017年9月20日 下午4:45:48
	 * @param pmphGroup
	 * @return List<PmphGroupListVO>
	 * @throws CheckedServiceException
	 */
	@Override
	public List<PmphGroupListVO> listPmphGroup(PmphGroup pmphGroup, String sessionId) throws CheckedServiceException {
		if (null == pmphGroup) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"参数对象为空");
		}
		// session PmphUser用户验证
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		return pmphGroupDao.getList(pmphGroup, pmphUser.getId());
	}

	@Override
	public PmphGroup addPmphGroupOnGroup(MultipartFile file, PmphGroup pmphGroup, String sessionId)
			throws CheckedServiceException, IOException {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		String groupImage = Const.DEFAULT_GROUP_IMAGE;// 未上传小组头像时，获取默认小组头像路径
		if (null != file) {
			groupImage = fileService.save(file, ImageType.GROUP_AVATAR, 0);
		}
		pmphGroup.setGroupImage(groupImage);
		pmphGroup.setFounderId(pmphUser.getId());
		pmphGroupDao.addPmphGroup(pmphGroup);
		if (null != pmphGroup.getId()) {// 判断是否新增小组成功，如果成功则调用PmphGroupMemberService添加小组成员的方法将创建者添加到小组中
			PmphGroupMember pmphGroupMember = new PmphGroupMember();
			pmphGroupMember.setGroupId(pmphGroup.getId());
			pmphGroupMember.setIsFounder(true);
			pmphGroupMember.setUserId(pmphUser.getId());
			pmphGroupMember.setDisplayName(pmphUser.getRealname());
			pmphGroupMemberService.addPmphGroupMember(pmphGroupMember);
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.OBJECT_NOT_FOUND,
					"添加小组失败");
		}
		return pmphGroup;
	}

	@Override
	public PmphGroup updatePmphGroupOnGroup(MultipartFile file, PmphGroup pmphGroup, String sessionId)
			throws CheckedServiceException, IOException {
		if (null == pmphGroup){
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"参数对象不能为空");
		}
		if (null == pmphGroup.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"小组id不能为空");
		}
		if (pmphGroupMemberService.isFounderOrisAdmin(pmphGroup.getId(), sessionId)) {
			if (null != file) {
				Long id = pmphGroup.getId();
				PmphGroup pmphGroupOld = getPmphGroupById(id);
				if (null != pmphGroupOld && null != pmphGroupOld.getGroupImage()
						&& !"".equals(pmphGroupOld.getGroupImage())) {
					fileService.remove(pmphGroupOld.getGroupImage());
				}
				String newGroupImage = fileService.save(file, ImageType.GROUP_AVATAR, 0);
				pmphGroup.setGroupImage(newGroupImage);
			}
			pmphGroupDao.updatePmphGroup(pmphGroup);
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"该用户没有此操作权限");
		}
		return pmphGroup;
	}

}
