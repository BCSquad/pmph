package com.bc.pmpheep.back.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphGroupDao;
import com.bc.pmpheep.back.dao.TextbookDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.MessageAttachment;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphGroupFile;
import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.CastUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.CookiesUtil;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.FileUpload;
import com.bc.pmpheep.back.util.FileUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.PmphGroupListVO;
import com.bc.pmpheep.back.vo.PmphGroupMemberVO;
import com.bc.pmpheep.general.bean.FileType;
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
	@Autowired
	private PmphGroupMessageService pmphGroupMessageService;
	@Autowired
	private PmphGroupFileService pmphGroupFileService;
	@Autowired
	private TextbookService textbookService;

	/**
	 *
	 * @param pmphGroup
	 *            实体对象
	 * @return 带主键的PmphGroup
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphGroup addPmphGroup(PmphGroup pmphGroup) throws CheckedServiceException {
		if (ObjectUtil.isNull(pmphGroup)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"参数对象为空");
		}
		if (StringUtil.isEmpty(pmphGroup.getGroupName())) {
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
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (pmphUser.getIsAdmin() || pmphGroupMemberService.isFounder(pmphGroup.getId(), sessionId)) {// 超级管理员与小组创建者才有权利删除小组
			if (null == pmphGroup.getId()) {
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
						"主键为空");
			}
			PmphGroup group = pmphGroupDao.getPmphGroupById(pmphGroup.getId());
			List<PmphGroupFile> pmphGroupFiles = pmphGroupFileService.listPmphGroupFileByGroupId(pmphGroup.getId());// 获取该小组的文件
			Long[] ids = new Long[pmphGroupFiles.size()];
			for (int i = 0; i < pmphGroupFiles.size(); i++) {
				PmphGroupFile pmphGroupFile = pmphGroupFiles.get(i);
				ids[i] = pmphGroupFile.getId();
			}
			int num = pmphGroupDao.deletePmphGroupById(pmphGroup.getId());// 删除小组
			if (num > 0) {
				if (ArrayUtil.isNotEmpty(ids)) {// 判断该小组是否有文件
					pmphGroupFileService.deletePmphGroupFileById(pmphGroup.getId(), ids, sessionId);// 删除小组文件
				}
				if (!RouteUtil.DEFAULT_GROUP_IMAGE.equals(group.getGroupImage())) {// 解散小组时删除小组头像
					fileService.remove(group.getGroupImage());
				}
				pmphGroupMessageService.deletePmphGroupMessageByGroupId(pmphGroup.getId());// 删除小组消息
				result = pmphGroupMemberService.deletePmphGroupMemberOnGroup(pmphGroup.getId());// 删除小组成员
			}
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"该用户没有此操作权限");
		}
		return result;
	}

	@Override
	public void updatePmphGroup(PmphGroup pmphGroup) {
		pmphGroupDao.updatePmphGroup(pmphGroup);
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
			String newGroupImage = fileService.save(file, ImageType.GROUP_AVATAR, pmphGroup.getId());
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
		List<PmphGroupListVO> list = new ArrayList<>();
		if (pmphUser.getIsAdmin()) {
			list = pmphGroupDao.listPmphGroup(pmphGroup.getGroupName());
			for (PmphGroupListVO pmphGroupListVO : list) {
				pmphGroupListVO.setGroupImage(RouteUtil.groupImage(pmphGroupListVO.getGroupImage()));
			}
		} else {
			list = pmphGroupDao.getList(pmphGroup, pmphUser.getId());
			for (PmphGroupListVO pmphGroupListVO : list) {
				pmphGroupListVO.setGroupImage(RouteUtil.groupImage(pmphGroupListVO.getGroupImage()));
			}
		}
		for (PmphGroupListVO pmphGroupListVO : list) {
			PmphGroupMemberVO user = pmphGroupMemberService.getPmphGroupMemberByMemberId(pmphGroupListVO.getId(),
					pmphUser.getId(), false);
			if (ObjectUtil.isNull(user)) {
				pmphGroupListVO.setIsMember(false);
			} else {
				pmphGroupListVO.setIsMember(true);
			}
		}
		return list;
	}

	@Override
	public PmphGroup addPmphGroupOnGroup(String file, PmphGroup pmphGroup, HttpServletRequest request)
			throws CheckedServiceException, IOException {
		String sessionId = CookiesUtil.getSessionId(request);
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (ObjectUtil.notNull(pmphGroupDao.getPmphGroupByGroupName(pmphGroup.getGroupName()))) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"该小组名称已被使用，请重新输入");
		}
		String groupImage = RouteUtil.DEFAULT_GROUP_IMAGE;// 未上传小组头像时，获取默认小组头像路径
		if (!StringUtil.isEmpty(file)) {
			groupImage = saveFileToMongoDB(file, request);
		}
		pmphGroup.setGroupImage(groupImage);
		pmphGroup.setFounderId(pmphUser.getId());
		pmphGroupDao.addPmphGroup(pmphGroup);
		if (null != pmphGroup.getId()) {// 判断是否新增小组成功，如果成功则调用PmphGroupMemberService添加小组成员的方法将创建者添加到小组中
			PmphGroupMember pmphGroupMember = new PmphGroupMember();
			pmphGroupMember.setGroupId(pmphGroup.getId());
			pmphGroupMember.setIsFounder(true);
			pmphGroupMember.setUserId(pmphUser.getId());
			pmphGroupMember.setDisplayName(StringUtil.isEmpty(pmphUser.getRealname())?pmphUser.getUsername():pmphUser.getRealname());
			pmphGroupMemberService.addPmphGroupMember(pmphGroupMember);
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.OBJECT_NOT_FOUND,
					"添加小组失败");
		}
		return pmphGroup;
	}

	@Override
	public PmphGroup updatePmphGroupOnGroup(String file, PmphGroup pmphGroup, HttpServletRequest request)
			throws CheckedServiceException, IOException {
		String sessionId = CookiesUtil.getSessionId(request);
		if (null == pmphGroup) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"参数对象不能为空");
		}
		if (null == pmphGroup.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"小组id不能为空");
		}
		PmphGroup group = pmphGroupDao.getPmphGroupByGroupName(pmphGroup.getGroupName());
		if (ObjectUtil.notNull(group)) {
			if (!pmphGroup.getId().equals(group.getId())) {
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
						"小组名称重复");
			}
		}
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (pmphUser.getIsAdmin() || pmphGroupMemberService.isFounderOrisAdmin(pmphGroup.getId(), sessionId)) {// 超级管理员与小组创建者、管理者才能修改小组信息
			if (!StringUtil.isEmpty(file)) {
				Long id = pmphGroup.getId();
				PmphGroup pmphGroupOld = getPmphGroupById(id);
				if (null != pmphGroupOld && null != pmphGroupOld.getGroupImage()
						&& !"".equals(pmphGroupOld.getGroupImage())
						&& !RouteUtil.DEFAULT_USER_AVATAR.equals(pmphGroupOld.getGroupImage())) {
					fileService.remove(pmphGroupOld.getGroupImage());
				}
				String newGroupImage = saveFileToMongoDB(file, request);
				pmphGroup.setGroupImage(newGroupImage);
			}
			pmphGroupDao.updatePmphGroup(pmphGroup);
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"该用户没有此操作权限");
		}
		return pmphGroup;
	}

	@Override
	public PmphGroup getPmphGroupByTextbookId(Long textbookId) throws CheckedServiceException {
		return pmphGroupDao.getPmphGroupByTextbookId(textbookId);
	}

	@Override
	public PmphGroup addEditorSelcetionGroup(String sessionId, List<PmphGroupMember> list, Long textbookId)
			throws CheckedServiceException {
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (list.size() == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"成员名单为空，更新失败");
		}
		Textbook textbook = textbookService.getTextbookById(textbookId);
		list.get(0).setTextbookId(textbookId);
		list.get(0).setMaterialId(textbook.getMaterialId());
		if(ObjectUtil.isNull(textbook.getPlanningEditor())){
			PmphGroupMember pmphGroupMember = new PmphGroupMember();
			pmphGroupMember.setUserId(textbook.getPlanningEditor());
			pmphGroupMember.setIsWriter(false);
			list.add(pmphGroupMember);
		}
		String groupImage = RouteUtil.DEFAULT_GROUP_IMAGE;// 未上传小组头像时，获取默认小组头像路径
		PmphGroup pmphGroup = new PmphGroup();
		// 查询小组名称是否已存在 不存在直接用书名
		if (ObjectUtil.isNull(pmphGroupDao.getPmphGroupByGroupName(textbook.getTextbookName()))) {
			pmphGroup.setGroupName(textbook.getTextbookName());
		} else {// 存在则用书名加当前小组总数进行区分
			Long count = pmphGroupDao.getPmphGroupCount();
			pmphGroup.setGroupName(textbook.getTextbookName() + count);
		}
		pmphGroup.setGroupImage(groupImage);
		pmphGroup.setBookId(textbookId);
		pmphGroup.setFounderId(pmphUser.getId());
		pmphGroupDao.addPmphGroup(pmphGroup);
		if (null != pmphGroup.getId()) {// 判断是否新增小组成功，如果成功则调用PmphGroupMemberService添加小组成员的方法将创建者添加到小组中
			PmphGroupMember pmphGroupMember = new PmphGroupMember();
			pmphGroupMember.setGroupId(pmphGroup.getId());
			pmphGroupMember.setIsFounder(true);
			pmphGroupMember.setUserId(pmphUser.getId());
			pmphGroupMember.setDisplayName(StringUtil.isEmpty(pmphUser.getRealname())?pmphUser.getUsername():pmphUser.getRealname());
			pmphGroupMemberService.addPmphGroupMember(pmphGroupMember);
			//新增小组成员--策划编辑
			if(!ObjectUtil.isNull(textbook.getPlanningEditor())){
				pmphGroupMember.setGroupId(pmphGroup.getId());
				pmphGroupMember.setUserId(textbook.getPlanningEditor());
				pmphGroupMember.setIsFounder(false);
				pmphGroupMember.setIsWriter(false);
				pmphGroupMember.setDisplayName(textbook.getRealname());
				pmphGroupMemberService.addPmphGroupMember(pmphGroupMember);
			}
			// 批量把前台传入的作家用户添加到该小组
			pmphGroupMemberService.addPmphGroupMemberOnGroup(pmphGroup.getId(), list, sessionId);
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.OBJECT_NOT_FOUND,
					"添加小组和成员失败");
		}
		return pmphGroup;
	}

	@Override
	public List<PmphGroupListVO> listPmphGroupFile(PmphGroup pmphGroup, String sessionId)
			throws CheckedServiceException {
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
		List<PmphGroupListVO> list = new ArrayList<>();
		list = pmphGroupDao.getList(pmphGroup, pmphUser.getId());
		for (PmphGroupListVO pmphGroupListVO : list) {
			pmphGroupListVO.setGroupImage(RouteUtil.groupImage(pmphGroupListVO.getGroupImage()));
		}
		return list;
	}

	@Override
	public PageResult<PmphGroupListVO> getlistPmphGroup(PageParameter<PmphGroupListVO> pageParameter, String sessionId)
			throws CheckedServiceException {
		if (null == pageParameter) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"参数对象为空");
		}
		// session PmphUser用户验证
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		List<PmphGroupListVO> list = new ArrayList<>();
		PageResult<PmphGroupListVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		Integer total = 0;
		if (pmphUser.getIsAdmin()) {
			total = pmphGroupDao.getAdminCount();
			list = pmphGroupDao.getPmphGroupList(pageParameter);
			for (PmphGroupListVO pmphGroupListVO : list) {
				pmphGroupListVO.setGroupImage(RouteUtil.groupImage(pmphGroupListVO.getGroupImage()));
			}
			pageResult.setRows(list);
			pageResult.setTotal(total);

		} else {
			PmphGroup pmphGroup = new PmphGroup();
			List<PmphGroupListVO> groupListVOs = pmphGroupDao.getList(pmphGroup, pmphUser.getId());
			for (PmphGroupListVO pmphGroupListVO : groupListVOs) {
				pmphGroupListVO.setGroupImage(RouteUtil.groupImage(pmphGroupListVO.getGroupImage()));
			}
			if (groupListVOs.size() > 0) {
				pageResult.setRows(groupListVOs);
				pageResult.setTotal(groupListVOs.size());
			}
		}
		return pageResult;
	}

	@Override
	public String msgUploadFiles(MultipartFile file, HttpServletRequest request) throws CheckedServiceException {
		if (file.isEmpty()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
					"附件为空！");
		}
		String filePath = "";
		// 循环获取file数组中得文件
		if (StringUtil.notEmpty(file.getOriginalFilename())) {
			String fullFileName = file.getOriginalFilename();// 完整文件名
			if (fullFileName.length() > 80) {
				throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM,
						"附件名称超出80个字符长度，请修改后上传！");
			}
			String fileName = fullFileName.substring(0, fullFileName.lastIndexOf("."));// 去掉后缀的文件名称
			String beforeDate = DateUtil.date2Str(new Date(), "yyyyMMddHHmmss") + "/";// 获取当前时间拼接路径
			FileUpload.fileUp(file, request.getSession().getServletContext().getRealPath("/") + beforeDate, fileName);// 上传文件
			filePath = beforeDate + fullFileName;
		}
		return filePath;
	}

	/**
	 * 
	 * <pre>
	 * 功能描述：保存文件到MongoDB
	 * 使用示范：
	 *
	 * &#64;param files 临时文件路径
	 * &#64;param msgId messageId
	 * &#64;throws CheckedServiceException
	 * </pre>
	 */
	private String saveFileToMongoDB(String file, HttpServletRequest request) throws IOException {
		String groupImage = RouteUtil.DEFAULT_GROUP_IMAGE;
		// 添加附件到MongoDB表中
		if (!StringUtil.isEmpty(file)) {
			File f = FileUpload.getFileByFilePath(request.getSession().getServletContext().getRealPath("/") + file);
			if (f.isFile()) {
				// 循环获取file数组中得文件
				if (StringUtil.notEmpty(f.getName())) {
					groupImage = fileService.saveLocalFile(f, FileType.GROUP_FILE, 0);// 上传文件到MongoDB
					if (StringUtil.isEmpty(groupImage)) {
						throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE,
								CheckedExceptionResult.FILE_UPLOAD_FAILED, "文件上传失败!");
					}

				}
				FileUtil.delFile(file);// 删除本地临时文件
			}
		}
		return groupImage;
	}
}
