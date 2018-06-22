package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bc.pmpheep.back.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.common.service.BaseService;
import com.bc.pmpheep.back.dao.PmphGroupMemberDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.MaterialProjectEditor;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.service.common.SystemMessageService;
import com.bc.pmpheep.back.vo.PmphGroupListVO;
import com.bc.pmpheep.back.vo.PmphGroupMemberManagerVO;
import com.bc.pmpheep.back.vo.PmphGroupMemberVO;
import com.bc.pmpheep.back.vo.TextbookDecVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * PmphGroupMemberService 接口实现
 * 
 * @author Mryang
 * 
 */
@Service
public class PmphGroupMemberServiceImpl extends BaseService implements PmphGroupMemberService {
    @Autowired
    private PmphGroupMemberDao     pmphGroupMemberDao;
    @Autowired
    private PmphGroupService       pmphGroupService;
    @Autowired
    private PmphUserService        pmphUserService;
    @Autowired
    private WriterUserService      writerUserService;
    @Autowired
    private DecPositionService     decPositionService;
    @Autowired
    private PmphGroupMemberService pmphGroupMemberService;
    @Autowired
    SystemMessageService           systemMessageService;
	@Autowired
	private MaterialService 	   materialService;
	@Autowired
	private TextbookService 	   textbookService;
	@Autowired
	private MaterialProjectEditorService materialProjectEditorService;

	/**
	 * 
	 * @param pmphGroupMember
	 *            实体对象
	 * @return 带主键的 PmphGroupMember
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphGroupMember addPmphGroupMember(PmphGroupMember pmphGroupMember) throws CheckedServiceException {
		if (null == pmphGroupMember.getDisplayName()) {
//			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
//					"小组内显示名称为空");
			pmphGroupMember.setDisplayName("");
		}
		pmphGroupMemberDao.addPmphGroupMember(pmphGroupMember);
		return pmphGroupMember;
	}

	/**
	 * 
	 * @param
	 * @return PmphGroupMember
	 * @throws CheckedServiceException
	 */
	@Override
	public PmphGroupMember getPmphGroupMemberById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		PmphGroupMember pmphGroupMemberVO = pmphGroupMemberDao.getPmphGroupMemberById(id);
		return pmphGroupMemberVO;
	}

	/**
	 * 
	 * @param
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer deletePmphGroupMemberById(Long id) throws CheckedServiceException {
		if (null == id) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return pmphGroupMemberDao.deletePmphGroupMemberById(id);
	}

	/**
	 * @param pmphGroupMember
	 * @return 影响行数
	 * @throws CheckedServiceException
	 */
	@Override
	public Integer updatePmphGroupMember(PmphGroupMember pmphGroupMember) throws CheckedServiceException {
		if (null == pmphGroupMember.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键为空");
		}
		return pmphGroupMemberDao.updatePmphGroupMember(pmphGroupMember);
	}

	@Override
	public List<PmphGroupMemberVO> listPmphGroupMember(Long groupId, String sessionId) throws CheckedServiceException {

		if (null == groupId || groupId == 0) {
			List<PmphGroupListVO> myPmphGroupListVOList = pmphGroupService.listPmphGroup(new PmphGroup(), sessionId);
			if (null == myPmphGroupListVOList || myPmphGroupListVOList.size() == 0) {
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
						"你没有小组！");
			}
			groupId = myPmphGroupListVOList.get(0).getId();// 初始化页面时没有参数传入则直接调用初始化时小组排序的第一个小组id
		}
		List<PmphGroupMemberVO> list = pmphGroupMemberDao.listPmphGroupMember(groupId);
		for (PmphGroupMemberVO pmphGroupMemberVO : list) {
			if (pmphGroupMemberVO.getIsWriter()) {
				WriterUser writerUser = writerUserService.get(pmphGroupMemberVO.getUserId());
				pmphGroupMemberVO.setAvatar(
						RouteUtil.userAvatar(writerUser.getAvatar()));
				pmphGroupMemberVO.setAvatarInfo("用户名: "+(StringUtil.isEmpty(writerUser.getUsername())?"-":writerUser.getUsername())+"<br/>"+"电话: "+(StringUtil.isEmpty(writerUser.getHandphone())?"-":writerUser.getHandphone())+"<br/>"+"邮箱: "+(StringUtil.isEmpty(writerUser.getEmail())?"-":writerUser.getEmail()));
				pmphGroupMemberVO.setUserType(Const.SENDER_TYPE_2);
			} else {
				PmphUser pmphUser = pmphUserService.get(pmphGroupMemberVO.getUserId());
				pmphGroupMemberVO.setAvatar(
						RouteUtil.userAvatar(pmphUser.getAvatar()));
				pmphGroupMemberVO.setUserType(Const.SENDER_TYPE_1);
				pmphGroupMemberVO.setAvatarInfo("用户名: "+(StringUtil.isEmpty(pmphUser.getUsername())?"-":pmphUser.getUsername())+"<br/>"+"电话: "+(StringUtil.isEmpty(pmphUser.getHandphone())?"-":pmphUser.getHandphone())+"<br/>"+"邮箱: "+(StringUtil.isEmpty(pmphUser.getEmail())?"-":pmphUser.getEmail()));
			}
		}
		return list;
	}

    @Override
    public String addPmphGroupMemberOnGroup(Long groupId, List<PmphGroupMember> pmphGroupMembers,
    String sessionId) throws CheckedServiceException {
        String result = "FAIL";
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (null == pmphUser || null == pmphUser.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        /**
        *
        Textbook textbook=new Textbook();
        if(null!=pmphGroupMembers.get(0).getTextbookId()){
        	//查询书籍信息
        	textbook=textbookService.getTextbookById(pmphGroupMembers.get(0).getTextbookId());
        }
        Material material=new Material();
        if(null!=pmphGroupMembers.get(0).getMaterialId()){
        	 //查询教材信息
            material=materialService.getMaterialById(pmphGroupMembers.get(0).getMaterialId());
        }
        //查询该教材是否存在项目编辑
        MaterialProjectEditor materialProjectEditor=materialProjectEditorService.getMaterialProjectEditorByMaterialIdAndUserId(material.getId(), pmphUser.getId());
        ////判断当前教材是否有创建小组的权限
        if (!material.getDirector().equals(pmphUser.getId()) && !textbook.getPlanningEditor().equals(pmphUser.getId())
                && null == materialProjectEditor && !pmphUser.getIsAdmin()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                    CheckedExceptionResult.ILLEGAL_PARAM, "该用户没有此操作权限");

        }
        if (null != material.getPlanPermission()) {
            if (!BinaryUtil.getBit(material.getPlanPermission(), 7)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                        CheckedExceptionResult.ILLEGAL_PARAM, "该用户没有此操作权限");
            }
        }
        if (null != material.getProjectPermission()) {
            if (!BinaryUtil.getBit(material.getProjectPermission(), 7)) {
                throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                        CheckedExceptionResult.ILLEGAL_PARAM, "该用户没有此操作权限");
            }
        }
        */
        //小组权限的判断 
        Long materialId = pmphGroupMembers.get(0).getMaterialId() ;
        Long textBookId = pmphGroupMembers.get(0).getTextbookId();
        String myPower  = textbookService.listBookPosition(1,9999,null,"["+textBookId+"]",null,materialId,sessionId)
        								  .getRows()
        								  .get(0)
        								  .getMyPower();
        String groupPower = myPower.substring(6,7);
        
        /**
         * 
        if (pmphUser.getIsAdmin() || isFounderOrisAdmin(groupId, sessionId)
        		||material.getDirector()==pmphUser.getId()||textbook.getPlanningEditor()==pmphUser.getId()
        		||pmphUser.getId()==materialProjectEditor.getEditorId()) {// 是超级管理员或者该小组的创建人和管理员才可以添加成员
        */
        if("1".equals(groupPower)) { //小组权限的判断  end
        	if (pmphGroupMembers.size() > 0) {
                List<Long> writers = new ArrayList<>();
                List<Long> pmphs = new ArrayList<>();
                for (PmphGroupMember pmphGroupMember : pmphGroupMembers) {
                    PmphGroupMemberVO groupMember =
                    pmphGroupMemberDao.getPmphGroupMemberByMemberId(groupId,
                                                                    pmphGroupMember.getUserId(),
                                                                    pmphGroupMember.getIsWriter());// 查看当前添加人员是否以前在小组中
                    if (ObjectUtil.isNull(groupMember)) {// 当前人员以前不在小组中
                        if (null == pmphGroupMember.getUserId()) {
                            throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                                                              CheckedExceptionResult.ILLEGAL_PARAM,
                                                              "成员id为空");
                        }
                        if (pmphGroupMember.getIsWriter()) {
                            WriterUser writerUser =
                            writerUserService.get(pmphGroupMember.getUserId());
                            pmphGroupMember.setDisplayName(writerUser.getRealname());
                            writers.add(pmphGroupMember.getUserId());
                        } else {
                            PmphUser user = pmphUserService.get(pmphGroupMember.getUserId());
                            pmphGroupMember.setDisplayName(user.getRealname());
                            pmphs.add(pmphGroupMember.getUserId());
                        }
                        pmphGroupMember.setGroupId(groupId);
                        pmphGroupMemberDao.addPmphGroupMember(pmphGroupMember);
					} else {
						if (groupMember.getIsDeleted()) {
							pmphGroupMember.setGroupId(groupId);
							pmphGroupMember.setIsDeleted(false);
							pmphGroupMemberDao.update(pmphGroupMember);
							if (groupMember.getIsWriter()) {
								writers.add(pmphGroupMember.getUserId());
							} else {
								pmphs.add(pmphGroupMember.getUserId());
							}
						} else {
							throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
									CheckedExceptionResult.ILLEGAL_PARAM, "该用户已在小组中，请勿重复添加");
						}
					}
				}
				// 向添加的小组成员发送消息
				try {
					if (!writers.isEmpty()) {
						// 向作家用户发送消息
						systemMessageService.sendWhenInviteJoinGroup(pmphUser.getRealname(), groupId, writers,
								(short) 2);
					}
					if (!pmphs.isEmpty()) {// 向社内用户发送消息
						systemMessageService.sendWhenInviteJoinGroup(pmphUser.getRealname(), groupId, pmphs, (short) 1);
					}
				} catch (IOException e) {
					throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
							CheckedExceptionResult.ILLEGAL_PARAM, "发送消息失败" + e.getMessage());
				}
				result = "SUCCESS";
			} else {
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
						"参数为空");
			}
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"该用户没有此操作权限");
		}
		return result;
	}

	@Override
	public String deletePmphGroupMemberOnGroup(Long groupId) throws CheckedServiceException {
		String result = "FAIL";
		if (null == groupId) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"小组id为空");
		}
		int num = pmphGroupMemberDao.deletePmphGroupMemberOnGroup(groupId);
		if (num > 0) {
			result = "SUCCESS";
		}

		return result;
	}

    @Override
    public Boolean isFounderOrisAdmin(Long groupId, String sessionId)
    throws CheckedServiceException {
        boolean flag = false;
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (null == pmphUser || null == pmphUser.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                                              CheckedExceptionResult.NULL_PARAM, "用户为空");
        }
        Long memberId = pmphUser.getId();
        PmphGroupMemberVO currentUser = pmphGroupMemberDao.getPmphGroupMemberByMemberId(groupId, memberId, false);
        if(null!=currentUser){
        	 if (currentUser.getIsFounder() || currentUser.getIsAdmin()) {
                 flag = true;
             }
        }
        return flag;
    }

	@Override
	public Boolean isFounder(Long groupId, String sessionId) throws CheckedServiceException {
		boolean flag = false;
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		Long memberId = pmphUser.getId();
		PmphGroupMemberVO currentUser = pmphGroupMemberDao.getPmphGroupMemberByMemberId(groupId, memberId, false);
		if (currentUser.getIsFounder()) {
			flag = true;
		}
		return flag;
	}

	@Override
	public PageResult<PmphGroupMemberManagerVO> listGroupMemberManagerVOs(
			PageParameter<PmphGroupMemberManagerVO> pageParameter) throws CheckedServiceException {
		if (null == pageParameter.getParameter().getGroupId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"小组id不能为空");
		}
		String name = pageParameter.getParameter().getName();
		if (StringUtil.notEmpty(name)) {
			pageParameter.getParameter().setName(name);
		}
		PageResult<PmphGroupMemberManagerVO> pageResult = new PageResult<>();
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		int total = pmphGroupMemberDao.groupMemberTotal(pageParameter);
		if (total > 0) {
			List<PmphGroupMemberManagerVO> list = pmphGroupMemberDao.listGroupMemberManagerVOs(pageParameter);
			for (PmphGroupMemberManagerVO pmphGroupMemberManagerVO : list) {
				pmphGroupMemberManagerVO.setAvatar(RouteUtil.userAvatar(pmphGroupMemberManagerVO.getAvatar()));
				if (pmphGroupMemberManagerVO.getIsWriter()) {
					pmphGroupMemberManagerVO.setUserType(Const.SENDER_TYPE_2);
				} else {
					pmphGroupMemberManagerVO.setUserType(Const.SENDER_TYPE_1);
				}
				// 处理position
				String position = pmphGroupMemberManagerVO.getPosition();
				if (null != position && position.contains(",")) {
					position = position.replace("无,", "").replace(",无", "");
					String[] positions = position.split(",");
					String tempPosition = positions[0];
					for (int i = 1; i < positions.length; i++) {
						if (!("," + tempPosition + ",").contains("," + positions[i] + ",")) {
							tempPosition += "," + positions[i];
						}
					}
					pmphGroupMemberManagerVO.setPosition(tempPosition);
				}
				// 处理position end
			}
			pageResult.setRows(list);
		}
		pageResult.setTotal(total);
		return pageResult;
	}

	@Override
	public String deletePmphGroupMemberByIds(Long groupId, Long[] ids, String sessionId)
			throws CheckedServiceException {
		String result = "FAIL";
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"该用户为空");
		}
		if (!pmphUser.getIsAdmin()) {
			if (!isFounderOrisAdmin(groupId, sessionId)) {
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
						"该用户没有操作权限");
			}
		}
		Long userid = pmphUser.getId();
		PmphGroupMemberVO currentUser = pmphGroupMemberDao.getPmphGroupMemberByMemberId(groupId, userid, false);
		if (null == ids || ids.length == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键不能为空");
		} else {
			for (Long id : ids) {
				PmphGroupMember pmphGroupMember = pmphGroupMemberDao.getPmphGroupMemberById(id);
				if (userid == pmphGroupMember.getUserId() && !pmphGroupMember.getIsWriter()) {
					throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
							CheckedExceptionResult.ILLEGAL_PARAM, "您无权限删除管理员，请重新选择");
				}
				if (pmphUser.getIsAdmin() || currentUser.getIsFounder()) {// 只有小组创建者和超级管理员可以删除小组成员
					if (pmphGroupMemberDao.getPmphGroupMemberById(id).getIsFounder()) {
						throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
								CheckedExceptionResult.ILLEGAL_PARAM, "小组创建者不能删除，请重新选择");
					}
					pmphGroupMemberDao.deletePmphGroupMemberById(id);
				} else {// 管理员进入的方法
					if (currentUser.getIsAdmin() && (pmphGroupMember.getIsFounder() || pmphGroupMember.getIsAdmin())) {
						throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
								CheckedExceptionResult.ILLEGAL_PARAM, "您无权限删除管理员，请重新选择");
					} else {
						pmphGroupMemberDao.deletePmphGroupMemberById(id);
					}
				}
			}
			result = "SUCCESS";
		}
		return result;
	}

	@Override
	public String updateGroupMemberByIds(Long groupId, Long[] ids, String sessionId) throws CheckedServiceException {
		String result = "FAIL";
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"该用户为空");
		}
		if (!pmphUser.getIsAdmin()) {
			if (!isFounderOrisAdmin(groupId, sessionId)) {
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
						"该用户没有操作权限");
			}
		}
		Long userid = pmphUser.getId();
		PmphGroupMemberVO currentUser = pmphGroupMemberDao.getPmphGroupMemberByMemberId(groupId, userid, false);
		if (null == ids || ids.length == 0) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"主键不能为空");
		} else {
			for (Long id : ids) {
				PmphGroupMember pmphGroupMember = pmphGroupMemberDao.getPmphGroupMemberById(id);
				// if (userid == pmphGroupMember.getUserId() && !pmphGroupMember.getIsWriter())
				// {
				// throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
				// CheckedExceptionResult.ILLEGAL_PARAM, "您无权限删除管理员，请重新选择");
				// }
				if (pmphUser.getIsAdmin() || currentUser.getIsFounder()) {// 只有小组创建者和超级管理员可以删除小组成员
					if (pmphGroupMemberDao.getPmphGroupMemberById(id).getIsFounder()) {
						throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
								CheckedExceptionResult.ILLEGAL_PARAM, "小组创建者不能删除，请重新选择");
					}
					pmphGroupMemberDao.updateGroupMemberById(id);
				} else {// 管理员进入的方法
					if (currentUser.getIsAdmin() && (pmphGroupMember.getIsFounder() || pmphGroupMember.getIsAdmin())) {
						throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
								CheckedExceptionResult.ILLEGAL_PARAM, "您无权限删除管理员，请重新选择");
					} else {
						pmphGroupMemberDao.updateGroupMemberById(id);
					}
				}
			}
			result = "SUCCESS";
		}
		return result;
	}

	@Override
	public PmphGroupMemberVO getPmphGroupMemberByMemberId(Long groupId, Long userId, Boolean isWriter)
			throws CheckedServiceException {
		if (null == userId) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"组员id不能为空");
		}
		PmphGroupMemberVO pmphGroupMemberVO = pmphGroupMemberDao.getPmphGroupMemberByMemberId(groupId, userId,
				isWriter);
		if (ObjectUtil.isNull(pmphGroupMemberVO)) {
			return null;
//			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
//					"你不是该小组的组员");
		}
		pmphGroupMemberVO.setAvatar(
				isWriter ? writerUserService.get(userId).getAvatar() : pmphUserService.get(userId).getAvatar());
		return pmphGroupMemberVO;
	}

	@Override
	public String updateMemberIdentity(Long groupId, List<PmphGroupMember> members, String sessionId)
			throws CheckedServiceException {
		String result = "FAIL";
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
					"用户为空");
		}
		if (pmphUser.getIsAdmin() || isFounder(groupId, sessionId)) {// 只有小组创建者与超级管理员可以修改小组人员权限
			if (null == members || members.size() == 0) {
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM,
						"参数不能为空");
			} else {
				for (PmphGroupMember pmphGroupMember : members) {
					PmphGroupMember member = pmphGroupMemberDao.getPmphGroupMemberById(pmphGroupMember.getId());
					if (member.getIsFounder() && !pmphGroupMember.getIsAdmin()) {
						throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
								CheckedExceptionResult.NULL_PARAM, "小组创建者不能取消管理员身份");
					}
					if (pmphGroupMember.getIsAdmin()) {
						if (member.getIsAdmin() || member.getIsFounder()) {
							throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
									CheckedExceptionResult.NULL_PARAM, "该用户已是小组管理员了");
						} else {
							pmphGroupMember.setIsAdmin(true);
						}
					} else {
						if (member.getIsAdmin()) {
							pmphGroupMember.setIsAdmin(false);
						} else {
							throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
									CheckedExceptionResult.NULL_PARAM, "该用户不是小组管理员");
						}

					}
					pmphGroupMemberDao.updatePmphGroupMember(pmphGroupMember);
				}
				result = "SUCCESS";
			}
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.ILLEGAL_PARAM,
					"只有创建者有此权限操作");
		}

		return result;

	}

    @Override
    public String addEditorBookGroup(Long textbookId, String sessionId)
    throws CheckedServiceException {
        String result = "FAIL";
        // 获取用户信息 进行判断用户是否有权限操作
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (null == pmphUser || null == pmphUser.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
                                              CheckedExceptionResult.NULL_PARAM, "该用户为空");
        }
        //查询书籍信息
        Textbook textbook=textbookService.getTextbookById(textbookId);
        //查询教材信息
        Material material=materialService.getMaterialById(textbook.getMaterialId());
        //查询该教材是否存在项目编辑
        MaterialProjectEditor materialProjectEditor=materialProjectEditorService.getMaterialProjectEditorByMaterialIdAndUserId(material.getId(), pmphUser.getId());
        // 通过书籍id查询所有主编、副主编、编委
        List<TextbookDecVO> textbookDecVOs = decPositionService.getTextbookEditorList(textbookId);
        List<PmphGroupMember> list = new ArrayList<PmphGroupMember>(textbookDecVOs.size());
        // 通过书籍id查询小组
        PmphGroup pmphGroup = pmphGroupService.getPmphGroupByTextbookId(textbookId);
        //判断当前教材是否有更新小组的权限
        //小组权限的判断 
        Long materialId = textbook.getMaterialId() ;
        String myPower  = textbookService.listBookPosition(1,9999,null,"["+textbookId+"]",null,materialId,sessionId)
        								  .getRows()
        								  .get(0)
        								  .getMyPower();
        String groupPower = myPower.substring(6,7);
        if("1".equals(groupPower)){
//        	if(null!=material.getPlanPermission()||null!=material.getProjectPermission()){
//        		if(!BinaryUtil.getBit(material.getPlanPermission(), 7)||!BinaryUtil.getBit(material.getProjectPermission(), 7)){
//            		throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
//            				CheckedExceptionResult.ILLEGAL_PARAM, "该用户没有更新成员权限 ");
//            	}
//        	}
		    // 通过小组id查询小组现有成员
		    List<PmphGroupMember> pmphGroupMembers =
		    pmphGroupMemberDao.listPmphGroupMembers(pmphGroup.getId());
		    List<Long> groupUserIdList = new ArrayList<Long>(pmphGroupMembers.size());
			List<Long> groupUserIdNotWriterList = new ArrayList<Long>(pmphGroupMembers.size());
		    for (PmphGroupMember pmphGroupMember : pmphGroupMembers) {
		    	if(pmphGroupMember.getIsWriter()){
					groupUserIdList.add(pmphGroupMember.getUserId());
				}else{
					groupUserIdNotWriterList.add(pmphGroupMember.getUserId());
				}
		    }
		    // 通过遍历把不存在的成员添加到list中
		    for (TextbookDecVO textbookDecVO : textbookDecVOs) {
		        Long userId = textbookDecVO.getUserId();
		        if (!groupUserIdList.contains(userId)) {
		            list.add(new PmphGroupMember(userId, Const.TRUE,textbook.getMaterialId(),textbookId,textbookDecVO.getRealname()));
		        }
		    }
		    if(!ObjectUtil.isNull(textbook.getPlanningEditor())&&!groupUserIdNotWriterList.contains(textbook.getPlanningEditor())){
				list.add(new PmphGroupMember(textbook.getPlanningEditor(), Const.FALSE,textbook.getMaterialId(),textbookId,textbook.getRealname()));
			}
		    if (CollectionUtil.isEmpty(list)) {
		        throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
		                                          CheckedExceptionResult.SUCCESS, "小组成员已是最新");
		    }
		    pmphGroupMemberService.addPmphGroupMemberOnGroup(pmphGroup.getId(), list, sessionId);
		    result = "SUCCESS";
        }else{
        	throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
    				CheckedExceptionResult.ILLEGAL_PARAM, "该用户没有更新成员权限 ");
        }
        return result;
    }

	@Override
	public Integer addPmphGroupMembers(Long groupId, List<PmphGroupMember> pmphGroupMembers, String sessionId) throws CheckedServiceException {
		if(null == pmphGroupMembers || pmphGroupMembers.size() == 0 ) {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
    				CheckedExceptionResult.ILLEGAL_PARAM, "请选择用户");
		}
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		PmphGroupMemberVO currentUser = new PmphGroupMemberVO();

		if (!pmphUser.getIsAdmin()) {
			Long userId = pmphUser.getId();
			currentUser = pmphGroupMemberService.getPmphGroupMemberByMemberId(groupId, userId, false);
		}

		if (pmphUser.getIsAdmin() || currentUser.getIsFounder()
				|| currentUser.getIsAdmin()) {// 超级管理员、小组创建者、小组管理者才可以邀请新成员
			for(PmphGroupMember pmphGroupMember: pmphGroupMembers) {
				Boolean isWriter = pmphGroupMember.getIsWriter();
				isWriter = (isWriter == null || !isWriter) ? false:true;
				PmphGroupMemberVO member = getPmphGroupMemberByMemberId(groupId,pmphGroupMember.getUserId(),isWriter);
				if(null== member || member.getId() == null ) {
					if(StringUtil.isEmpty(pmphGroupMember.getDisplayName())) {
						if(isWriter) {
							pmphGroupMember.setDisplayName(StringUtil.isEmpty(writerUserService.get(pmphGroupMember.getUserId()).getRealname())?"":writerUserService.get(pmphGroupMember.getUserId()).getUsername());
						}else {
							pmphGroupMember.setDisplayName(StringUtil.isEmpty(pmphUserService.get(pmphGroupMember.getUserId()).getRealname())?"":pmphUserService.get(pmphGroupMember.getUserId()).getUsername());
						}
					}
					pmphGroupMember.setGroupId(groupId);
					this.addPmphGroupMember(pmphGroupMember);
				}else{
					PmphGroupMember reUseMember = new PmphGroupMember ();
					reUseMember.setId(member.getId());
					reUseMember.setIsDeleted(false);
					pmphGroupMemberDao.updatePmphGroupMember(reUseMember);
				}
			}
		} else {
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
					CheckedExceptionResult.ILLEGAL_PARAM, "该用户没有此操作权限");
		}

		return pmphGroupMembers.size();
	}

	@Override
	public String updatePmphGroupMemberDisplayName(Long groupId,Long userId, Long id, String displayName,
			String sessionId) throws CheckedServiceException {
		String result = "FAIL";
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (null == pmphUser || null == pmphUser.getId()){
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
					CheckedExceptionResult.NULL_PARAM, "该用户为空");
		}
		if (ObjectUtil.isNull(id)){
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
					CheckedExceptionResult.NULL_PARAM, "未选择小组成员");
		}
		if (StringUtil.isEmpty(displayName)){
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
					CheckedExceptionResult.NULL_PARAM, "成员昵称不能为空");
		}
			PmphGroupMember member = new PmphGroupMember();
			member.setId(id);
			member.setDisplayName(displayName);
			member.setUserId(userId);
			member.setGroupId(groupId);
		if (pmphUser.getIsAdmin() || isFounder(groupId, sessionId)){
			pmphGroupMemberDao.update(member);
			result = "SUCCESS";
		} else if (isFounderOrisAdmin(groupId, sessionId)){
			PmphGroupMember pmphGroupMember = pmphGroupMemberDao.getPmphGroupMemberById(id);
			if (pmphGroupMember.getIsFounder() || pmphGroupMember.getIsAdmin()){
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
						CheckedExceptionResult.ILLEGAL_PARAM, "管理员只能修改自己或普通成员的小组内昵称");
			}else{
				pmphGroupMemberDao.update(member);
				result = "SUCCESS";
			}
		}else {
			PmphGroupMember pmphGroupMember = pmphGroupMemberDao.getPmphGroupMemberById(id);
			if (pmphUser.getId().equals(pmphGroupMember.getUserId()) && !pmphGroupMember.getIsWriter()){
				pmphGroupMemberDao.update(member);
				result = "SUCCESS";
			} else {
				throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
						CheckedExceptionResult.ILLEGAL_PARAM, "普通成员只能修改自己的昵称");
			}
		}
		return result;
	}

	@Override
	public Map<String,Object> queryMaterialMembers(Long groupId, Integer pageNumber, Integer pageSize, String searchParam) throws CheckedServiceException {
		Map<String,Object> params=new HashMap<>();
		if (ObjectUtil.isNull(groupId)){
			throw new CheckedServiceException(CheckedExceptionBusiness.GROUP,
					CheckedExceptionResult.NULL_PARAM, "小组未选择");
		}
		params.put("groupId",groupId);
		params.put("searchParam",StringUtil.toAllCheck(searchParam));
		int total=pmphGroupMemberDao.queryMaterialMembersTotal(params);
		Map<String,Object> resultMap = new HashMap<String,Object>();
		List<Map<String,Object >> memberlist=new ArrayList<>();
		if(total>0){
			params.put("index",(pageNumber-1)*pageSize);
			params.put("size",pageSize);
			memberlist=pmphGroupMemberDao.queryMaterialMembers(params);
		}
        resultMap.put("memberlist",memberlist);
        resultMap.put("total",total);
		return resultMap;
	}

}
