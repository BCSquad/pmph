package com.bc.pmpheep.back.service.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.MaterialProjectEditor;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.po.OrgUser;
import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.service.CmsContentService;
import com.bc.pmpheep.back.service.DecPositionService;
import com.bc.pmpheep.back.service.DeclarationService;
import com.bc.pmpheep.back.service.MaterialProjectEditorService;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.service.OrgService;
import com.bc.pmpheep.back.service.OrgUserService;
import com.bc.pmpheep.back.service.PmphGroupMemberService;
import com.bc.pmpheep.back.service.PmphGroupService;
import com.bc.pmpheep.back.service.TextbookService;
import com.bc.pmpheep.back.service.UserMessageService;
import com.bc.pmpheep.back.service.WriterUserService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.back.vo.PmphGroupMemberVO;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.general.service.MessageService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.websocket.MyWebSocketHandler;
import com.bc.pmpheep.websocket.WebScocketMessage;

/**
 *发送系统消息公共服务类
 *以下方法推荐放在操作完成后
 *@author MrYang 
 *@CreateDate 2017年11月17日 上午8:55:49
 *
 **/

@Service
public final class SystemMessageService {
	
	  private final String messageTitle = "系统消息";
	  
	  @Autowired
	  private UserMessageService userMessageService;

	  @Autowired
	  private MessageService messageService;
	  
	  @Autowired
	  private OrgUserService orgUserService;
	  
	  @Autowired
	  private WriterUserService writerUserService;
	  
	  @Autowired
	  private MyWebSocketHandler       myWebSocketHandler;
	  
	  @Autowired
	  private MaterialService      materialService;
	  
	  @Autowired
	  private TextbookService      textbookService;
	  
	  @Autowired
	  private DecPositionService decPositionService;
	  
	  @Autowired
	  private DeclarationService declarationService;
	  
	  @Autowired
	  private PmphGroupService pmphGroupService;
	  
	  @Autowired
	  private PmphGroupMemberService pmphGroupMemberService;
	  
	  @Autowired 
	  private MaterialProjectEditorService materialProjectEditorService;
	  
	  @Autowired
	  private  CmsContentService cmsContentService;
	  
	  @Autowired
	  private OrgService orgService;
	  
	  
	  
	  /**
	   * 遴选公告发布时，给学校管理员和学校教师发送消息，通知他们留意报名情况或者是参加报名
	   * @author Mryang
	   * @createDate 2017年11月17日 上午10:52:13
	   * @param materialName  教材名称
	   * @param ids           发送的机构id集合
	   * @throws CheckedServiceException
	   * @throws IOException
	   */
	  public  void materialSend(String materialName,List<Long> ids) throws CheckedServiceException, IOException{
		  this.materialSend(materialName,ids,false);
	  }
	  
	  /**
	   * 遴选公告发布时，给学校管理员和学校教师发送消息，通知他们留意报名情况或者是参加报名
	   * @author Mryang
	   * @createDate 2017年11月17日 上午10:52:13
	   * @param materialId    教材id
	   * @param ids           发送的机构id集合
	   * @throws CheckedServiceException
	   * @throws IOException
	   */
	  public  void materialSend(Long materialId,List<Long> ids) throws CheckedServiceException, IOException{
		  Material material =materialService.getMaterialById(materialId);
		  if(null == material){
			  throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "没有找到对应的教材");
		  }
		  this.materialSend(material.getMaterialName(),ids,false);
	  }
	  
	  /**
	   * 遴选公告发布时，给学校管理员和学校教师发送消息，通知他们留意报名情况或者是参加报名
	   * @author Mryang
	   * @createDate 2017年11月17日 上午9:28:13
	   * @param materialName  教材名称
	   * @param ids           发送的机构id集合
	   * @param isOnlyManager 是否只发给管理员
	   * @throws CheckedServiceException
	   * @throws IOException
	   */
	  public  void materialSend(String materialName,List<Long> ids,boolean isOnlyManager) throws CheckedServiceException, IOException{
		  	if(StringUtils.isEmpty(materialName)){
		  		throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "消息体为空");
		  	}
		  	if(null == ids || ids.size() == 0){
		  		throw new CheckedServiceException(CheckedExceptionBusiness.MESSAGE, CheckedExceptionResult.NULL_PARAM, "消息接收对象为空");
		  	}
		  	//向教师发送消息
		  	if(!isOnlyManager){
		  		String tercherMsg="《<font color='red'>"+materialName+"</font>》已经开始申报，请您留意";
		  		//mogodb保存消息体
			  	Message message = new Message (tercherMsg);
				message = messageService.add(message);
				String msg_id  = message.getId();
				//获取这些机构启用的作家用户
				List<WriterUser> writerUserList = writerUserService.getWriterUserListByOrgIds(ids); 
				if(null != writerUserList && writerUserList.size()  > 0){
					List<UserMessage> userMessageList = new ArrayList<UserMessage>(writerUserList.size());
					List<String> userIds =   new ArrayList<String>(writerUserList.size());
					for(WriterUser writerUser :writerUserList){
						 //信息是由系统发出
						 UserMessage userMessage = new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),writerUser.getId(), new Short("2"));
						 userMessageList.add(userMessage);
						 userIds.add("2_"+writerUser.getId());
					}
					//批量插入消息
					userMessageService.addUserMessageBatch(userMessageList) ;
					//websocket推送页面消息
					WebScocketMessage webScocketMessage =
				            new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
				                                  "系统", Const.SENDER_TYPE_1,
				                                  Const.SEND_MSG_TYPE_0, 
				                                  RouteUtil.DEFAULT_USER_AVATAR,
				                                  messageTitle,
				                                  tercherMsg, DateUtil.getCurrentTime());
				  	myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
			    }
			}
		  	//向管理员发送消息
		  	String managerMsg="《<font color='red'>"+materialName+"</font>》已经开始申报，请您留意教职工的报名情况";
		  	Message message = new Message (managerMsg);
			message = messageService.add(message);
			String msg_id  = message.getId();
			//获取这些机构的管理员
			List<OrgUser> orgUserList = orgUserService.getOrgUserListByOrgIds(ids);
			List<UserMessage> userMessageList = new ArrayList<UserMessage>(orgUserList.size());
			List<String> userIds =   new ArrayList<String>(orgUserList.size());
		  	for(OrgUser orgUser :orgUserList ){
		  		 UserMessage userMessage = new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),orgUser.getId(), new Short("3"));
		  		 userMessageList.add(userMessage);
				 userIds.add("3_"+orgUser.getId());
		  	}
		  	//批量插入消息
			userMessageService.addUserMessageBatch(userMessageList) ;
			//websocket推送页面消息
			WebScocketMessage webScocketMessage =
		            new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
		                                  "系统", Const.SENDER_TYPE_1,
		                                  Const.SEND_MSG_TYPE_0, 
		                                  RouteUtil.DEFAULT_USER_AVATAR,
		                                  messageTitle,
		                                  managerMsg, DateUtil.getCurrentTime());
		  	myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	 }
	  
	  /**
	   * 遴选公告发布时，给学校管理员学校教师发送消息，通知他们留意报名情况或者是参加报名
	   * @author Mryang
	   * @createDate 2017年11月17日 上午10:52:13
	   * @param materialId    教材id
	   * @param ids           发送的机构id集合
	   * @param isOnlyManager 是否只发给管理员
	   * @throws CheckedServiceException
	   * @throws IOException
	   */
	  public  void materialSend(Long materialId,List<Long> ids,boolean isOnlyManager) throws CheckedServiceException, IOException{
		  Material material =materialService.getMaterialById(materialId);
		  if(null == material){
			  throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "没有找到对应的教材");
		  }
		  this.materialSend(material.getMaterialName(),ids,isOnlyManager);
	  }
	  
	  /**
	   * 遴选主编副主编时点击确认按钮之后向第一主编发送消息 
	   * @author Mryang
	   * @createDate 2017年11月17日 下午1:57:04
	   * @param bookId 教材书籍id
	   * @throws CheckedServiceException
	   * @throws IOException
	   */
	  public  void sendWhenConfirmFirstEditor(Long bookId) throws CheckedServiceException, IOException{
		  //获取教材书籍
		  Textbook textbook =textbookService.getTextbookById(bookId);
		  //获取教材
		  Material material =materialService.getMaterialById(textbook.getMaterialId());
		  if(null == material){
			  throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "该书籍没有找到对应的教材");
		  }
		  //获取这本书的申报遴选列表
		  List<DecPosition> decPositionLst=decPositionService.listDecPositionsByTextbookId(bookId);
		  for(DecPosition decPosition : decPositionLst ){
			  if(null != decPosition && null != decPosition.getChosenPosition() && null !=decPosition.getRank() 
					  && decPosition.getChosenPosition() ==1 && decPosition.getRank() == 1){//筛选出主编
				Declaration declaration=declarationService.getDeclarationById(decPosition.getDeclarationId());
				//消息内容
				String msgContent="恭喜您被选为《<font color='red'>"+material.getMaterialName()+"</font>》<font color='red'>["+textbook.getTextbookName()+"</font>]的第一主编，您现在可以开始遴选编委了";
				Message message = new Message (msgContent);
				message = messageService.add(message);
				String msg_id  = message.getId();
				UserMessage userMessage = new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),declaration.getUserId(), new Short("2"));
				userMessageService.addUserMessage(userMessage);
				//websocket推送页面消息
				WebScocketMessage webScocketMessage =
			            new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
			                                  "系统", Const.SENDER_TYPE_1,
			                                  Const.SEND_MSG_TYPE_0, 
			                                  RouteUtil.DEFAULT_USER_AVATAR,
			                                  messageTitle,
			                                  msgContent, DateUtil.getCurrentTime());
				List<String> userIds = new ArrayList<String>(1);
				userIds.add("2_"+declaration.getUserId());
			  	myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
			  }
		  }
	  }
	  
	  /**
	   * 任意用户被邀请进入小组  向被邀请人发出
	   * @author Mryang
	   * @createDate 2017年11月17日 下午2:16:36
	   * @param inviterName  邀请人名字
	   * @param groupId      小组id
	   * @param invitedPersonIds  被邀请人ids
	   * @param invitedPersonType 被邀请人类型: 1=社内用户/2=作家/3=机构用户
	   * @throws CheckedServiceException
	   * @throws IOException
	   */
	  public void sendWhenInviteJoinGroup(String inviterName,Long groupId,List<Long> invitedPersonIds,short invitedPersonType) throws CheckedServiceException, IOException{
		  if(StringUtils.isEmpty(inviterName)){
			  throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM, "邀请人为空"); 
		  }
		  if(null == invitedPersonIds || invitedPersonIds.size() == 0){
			  throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM, "被邀请人为空"); 
		  }
		  //获取小组
		  PmphGroup pmphGroup =pmphGroupService.getPmphGroupById(groupId);
		  if(null == pmphGroup){
			  throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM, "小组不存在"); 
		  }
		  String msgContent="您被[<font color='red'>"+inviterName+"</font>]邀请加入[<font color='red'>"+pmphGroup.getGroupName()+"]</font>小组";
		  //存入消息主体
		  Message message = new Message (msgContent);
		  message = messageService.add(message);
		  String msg_id  = message.getId();
		  //组装消息和消息对象
		  List<UserMessage> userMessageList = new ArrayList<UserMessage>(invitedPersonIds.size());
		  List<String> userIds =   new ArrayList<String>(invitedPersonIds.size());
		  for(Long id :invitedPersonIds){
			  UserMessage userMessage = new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),id, invitedPersonType);
			  userMessageList.add(userMessage);
			  userIds.add(invitedPersonType+"_"+id);
		  }
		  //发送消息
		  userMessageService.addUserMessageBatch(userMessageList);
		  //websocket推送页面消息
		  WebScocketMessage webScocketMessage =
	            new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
	                                  "系统", Const.SENDER_TYPE_1,
	                                  Const.SEND_MSG_TYPE_0, 
	                                  RouteUtil.DEFAULT_USER_AVATAR,
	                                  messageTitle,
	                                  msgContent, DateUtil.getCurrentTime());
		  myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	  }
	  
	  /**
	   * 任意用户退出小组 给小组创建者和管理员发送消息 
	   * @author Mryang
	   * @createDate 2017年11月17日 下午2:41:26
	   * @param exitPersonName 退出人姓名
	   * @param groupId        退出的小组id
	   * @throws CheckedServiceException
	   * @throws IOException
	   */
	  public void sendWhenQuitGroup (String exitPersonName,Long groupId) throws CheckedServiceException, IOException{
		  if(StringUtils.isEmpty(exitPersonName)){
			  throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM, "退出人为空"); 
		  }
		  //获取小组
		  PmphGroup pmphGroup =pmphGroupService.getPmphGroupById(groupId);
		  if(null == pmphGroup){
			  throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM, "小组不存在"); 
		  }
		  List<PmphGroupMemberVO> PmphGroupMemberVOList =pmphGroupMemberService.listPmphGroupMember(groupId, null);
		  if(null == PmphGroupMemberVOList || PmphGroupMemberVOList.size()==0){
			  throw new CheckedServiceException(CheckedExceptionBusiness.GROUP, CheckedExceptionResult.NULL_PARAM, "小组没有成员");
		  }
		  //存入消息主体
		  String msgContent="[<font color='red'>"+exitPersonName+"</font>]退出了["+pmphGroup.getGroupName()+"]小组";
		  Message message = new Message (msgContent);
		  message = messageService.add(message);
		  String msg_id  = message.getId();
		  //组装消息和消息对象
		  List<UserMessage> userMessageList = new ArrayList<UserMessage>(PmphGroupMemberVOList.size());
		  List<String> userIds =   new ArrayList<String>(PmphGroupMemberVOList.size());
		  for(PmphGroupMemberVO pmphGroupMemberVO :PmphGroupMemberVOList){
			  if(pmphGroupMemberVO.getIsFounder()||pmphGroupMemberVO.getIsAdmin()){//给创建者和管理员发送
				  UserMessage userMessage = new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),pmphGroupMemberVO.getUserId(),
						  pmphGroupMemberVO.getUserType());
				  userMessageList.add(userMessage);
				  userIds.add(pmphGroupMemberVO.getUserType()+"_"+pmphGroupMemberVO.getUserId()); 
			  }
		  }
		  //发送消息
		  userMessageService.addUserMessageBatch(userMessageList);
		  //websocket推送页面消息
		  WebScocketMessage webScocketMessage =
	            new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
	                                  "系统", Const.SENDER_TYPE_1,
	                                  Const.SEND_MSG_TYPE_0, 
	                                  RouteUtil.DEFAULT_USER_AVATAR,
	                                  messageTitle,
	                                  msgContent, DateUtil.getCurrentTime());
		  myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	  }
	  
	  /**
	   * 
	   * 教师认证审  向教师用户发送消息
	   * @author Mryang
	   * @createDate 2017年11月17日 下午2:52:53
	   * @param auditorOrgName 认证机构名称，如：首都医科大学,如是人卫社认证的审核，则为：人民卫生出版社
	   * @param teacherIds     教师ids
	   * @param isPass         true 通过/false 退回
	   * @throws CheckedServiceException
	   * @throws IOException
	   */
	  public void sendWhenTeacherCertificationAudit(String auditorOrgName ,List<Long> teacherIds,boolean isPass) throws CheckedServiceException, IOException{
		  if(StringUtils.isEmpty(auditorOrgName)){
			  throw new CheckedServiceException(CheckedExceptionBusiness.TEACHER_CHECK, CheckedExceptionResult.NULL_PARAM, "审核机构为空"); 
		  }
		  if(null == teacherIds ||teacherIds.size() ==0){
			  throw new CheckedServiceException(CheckedExceptionBusiness.TEACHER_CHECK, CheckedExceptionResult.NULL_PARAM, "认证的教师为空"); 
		  }
		  //存入消息主体
		  String msgContent="抱歉，您提交的教师认证资料已被[<font color='red'>"+auditorOrgName+"</font>]管理员退回，请您核对后重试";//退回
		  if(isPass){//通过
			  msgContent="恭喜！您提交的教师认证资料已通过[color='red'>"+auditorOrgName+"</font>]管理员审核";
		  }
		  //存入消息主体
		  Message message = new Message (msgContent);
		  message = messageService.add(message);
		  String msg_id  = message.getId();
		  //组装消息和消息对象
		  List<UserMessage> userMessageList = new ArrayList<UserMessage>(teacherIds.size());
		  List<String> userIds =   new ArrayList<String>(teacherIds.size());
		  //发送消息
		  for(Long  id:teacherIds){
			  UserMessage userMessage = new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),id,new Short("2"));
			  userMessageList.add(userMessage);
			  userIds.add("2_"+id);
		  }
		  
		  userMessageService.addUserMessageBatch(userMessageList);
		  //websocket推送页面消息
		  WebScocketMessage webScocketMessage =
	            new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
	                                  "系统", Const.SENDER_TYPE_1,
	                                  Const.SEND_MSG_TYPE_0, 
	                                  RouteUtil.DEFAULT_USER_AVATAR,
	                                  messageTitle,
	                                  msgContent, DateUtil.getCurrentTime());
		  myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	  }
	  
	  /**
	   * 学校管理员认证  向机构用户发送消息
	   * @author Mryang
	   * @createDate 2017年11月17日 下午3:01:42
	   * @param orguserIds 机构用户ids
	   * @param isPass     true 通过/false 退回
	   * @throws CheckedServiceException
	   * @throws IOException
	   */
	  public void sendWhenManagerCertificationAudit(List<Long> orguserIds,boolean isPass) throws CheckedServiceException, IOException{
		  if(null == orguserIds ||orguserIds.size() ==0){
			  throw new CheckedServiceException(CheckedExceptionBusiness.SCHOOL_ADMIN_CHECK, CheckedExceptionResult.NULL_PARAM, "认证的管理员为空"); 
		  }
		  //存入消息主体
		  String msgContent="抱歉，您提交的管理员认证资料已被退回，请您修改后重试";//退回
		  if(isPass){//通过
			  msgContent="恭喜！您提交的管理员认证资料已通过审核";
		  }
		  //存入消息主体
		  Message message = new Message (msgContent);
		  message = messageService.add(message);
		  String msg_id  = message.getId();
		  //组装消息和消息对象
		  List<UserMessage> userMessageList = new ArrayList<UserMessage>(orguserIds.size());
		  List<String> userIds =   new ArrayList<String>(orguserIds.size());
		  //发送消息
		  for(Long  id:orguserIds){
			  UserMessage userMessage = new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),id,new Short("3"));
			  userMessageList.add(userMessage);
			  userIds.add("3_"+id);
		  }
		  
		  userMessageService.addUserMessageBatch(userMessageList);
		  //websocket推送页面消息
		  WebScocketMessage webScocketMessage =
	            new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
	                                  "系统", Const.SENDER_TYPE_1,
	                                  Const.SEND_MSG_TYPE_0, 
	                                  RouteUtil.DEFAULT_USER_AVATAR,
	                                  messageTitle,
	                                  msgContent, DateUtil.getCurrentTime());
		  myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	  }
	  
	  /**
	   * 作家教材申报表 给学校管理员发送或者人卫教材相关人员发送消息
	   * @author Mryang
	   * @createDate 2017年11月17日 下午4:31:19
	   * @param declarationId  申报id
	   * @throws CheckedServiceException
	   * @throws IOException
	   */
	  public void sendWhenSubmitDeclarationForm(Long declarationId) throws CheckedServiceException, IOException{ 
		  //获取申报表
		  Declaration declaration=declarationService.getDeclarationById(declarationId);
		  if(null == declaration){
			  throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "申报表不存在");
		  }
		  if(null == declaration.getOrgId()){
			  throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "认证的管理员为空");
		  }
		  Material material = materialService.getMaterialById(declaration.getMaterialId());
		  if(declaration.getOrgId() == 0){//提交的人卫社
			  //项目编辑  策划编辑  主任 id
			  String msgContent="[<font color='red'>"+declaration.getRealname()+"</font>]提交了《<font color='red'>"+material.getMaterialName()+"</font>》申报表，请及时进行资料审核";
			  //存入消息主体
			  Message message = new Message (msgContent);
			  message = messageService.add(message);
			  String msg_id  = message.getId();
			  //主任id
			  Long  directorId=material.getDirector();
			  //项目编辑列表
			  List<MaterialProjectEditor> materialProjectEditorList=materialProjectEditorService.listMaterialProjectEditors(material.getId());
			  //批量保存的消息集合
			  List<UserMessage> userMessageList = new ArrayList<UserMessage>(materialProjectEditorList.size()+1);
			  List<String> userIds =   new ArrayList<String>(materialProjectEditorList.size()+1);
			  userMessageList.add(new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),directorId,new Short("1")));
			  userIds.add("1_"+directorId);
			  for(MaterialProjectEditor materialProjectEditor: materialProjectEditorList ){
				  UserMessage userMessage = new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),materialProjectEditor.getEditorId(),new Short("1"));
				  userMessageList.add(userMessage);
				  userIds.add("1_"+materialProjectEditor.getEditorId());
			  }
			  //策划编辑
			  List<DecPosition> decPositionList=decPositionService.listDecPositions(declarationId);//我申报的书籍
			  for(DecPosition  decPosition: decPositionList){
				  Textbook textbook=textbookService.getTextbookById(decPosition.getTextbookId());
				  if(null != textbook && null != textbook.getPlanningEditor()){
					  UserMessage userMessage = new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),textbook.getPlanningEditor(),new Short("1"));
					  userMessageList.add(userMessage);
					  userIds.add("1_"+textbook.getPlanningEditor());
				  }
			  }
			  //发送消息
			  userMessageService.addUserMessageBatch(userMessageList);
			  //websocket推送页面消息
			  WebScocketMessage webScocketMessage =
		            new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
		                                  "系统", Const.SENDER_TYPE_1,
		                                  Const.SEND_MSG_TYPE_0, 
		                                  RouteUtil.DEFAULT_USER_AVATAR,
		                                  messageTitle,
		                                  msgContent, DateUtil.getCurrentTime());
			  myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
		}else{//提交的机构
			  String msgContent="贵校老师[<font color='red'>"+declaration.getRealname()+"</font>]提交了《<font color='red'>"+material.getMaterialName()+"</font>》申报表，请及时进行资料审核、打印并快递申报纸质表";
			  //存入消息主体
			  Message message = new Message (msgContent);
			  message = messageService.add(message);
			  String msg_id  = message.getId();
			  //获取机构用户
			  List<Long> orgIds = new ArrayList<Long>(1);
			  orgIds.add(declaration.getOrgId());
			  List<OrgUser>orgUserList =orgUserService.getOrgUserListByOrgIds(orgIds) ;
			  List<UserMessage> userMessageList = new ArrayList<UserMessage>(orgUserList.size());
			  List<String> userIds =   new ArrayList<String>(orgUserList.size());
			  for(OrgUser orgUser:orgUserList){
				  UserMessage userMessage = new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),orgUser.getId(),new Short("3"));
				  userMessageList.add(userMessage);
				  userIds.add("3_"+orgUser.getId());
			  }
			  //发送消息
			  userMessageService.addUserMessageBatch(userMessageList);
			  //websocket推送页面消息
			  WebScocketMessage webScocketMessage =
		            new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
		                                  "系统", Const.SENDER_TYPE_1,
		                                  Const.SEND_MSG_TYPE_0, 
		                                  RouteUtil.DEFAULT_USER_AVATAR,
		                                  messageTitle,
		                                  msgContent, DateUtil.getCurrentTime());
			  myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
		}
	}
	  
	  /**
	   * 教材申报表审核  向作家用户发送消息
	   * @author Mryang
	   * @createDate 2017年11月17日 下午4:42:14
	   * @param declarationId 申报id
	   * @param isPass  true 通过/false 退回
	   * @throws CheckedServiceException
	   * @throws IOException
	   */
	  public void sendWhenDeclarationFormAudit(Long declarationId,boolean isPass) throws CheckedServiceException, IOException{
		  //获取申报表
		  Declaration declaration=declarationService.getDeclarationById(declarationId);
		  if(null == declaration){
			  throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "申报表不存在");
		  }
		  if(null == declaration.getOrgId()){
			  throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "认证的管理员为空");
		  }
		  //获取教材
		  Material material = materialService.getMaterialById(declaration.getMaterialId());
		  String  msgContent="";
		  if(declaration.getOrgId() == 0){//提交的人卫社
			  msgContent="抱歉，您提交的《<font color='red'>"+material.getMaterialName()+"</font>》申报表被[<font color='red'>出版社</font>]退回，请您核对后重试";
			  if(isPass){//通过
				  msgContent="恭喜！您提交的《<font color='red'>"+material.getMaterialName()+"</font>》申报表已通过[<font color='red'>出版社</font>]审核";
			  }
		  }else{//提交的机构
			  msgContent="抱歉，您提交的《<font color='red'>"+material.getMaterialName()+"</font>》申报表被[<font color='red'>学校管理员</font>]退回，请您核对后重试";
			  if(isPass){//通过
				  msgContent="恭喜！您提交的《<font color='red'>"+material.getMaterialName()+"</font>》申报表已通过[<font color='red'>学校管理员</font>]审核";
			  }
		  }
		  //存入消息主体
		  Message message = new Message (msgContent);
		  message = messageService.add(message);
		  String msg_id  = message.getId();
		  //发送消息
		  userMessageService.addUserMessage(new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),declaration.getUserId(),new Short("2")));
		  //websocket推送页面消息
		  WebScocketMessage webScocketMessage =
	            new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
	                                  "系统", Const.SENDER_TYPE_1,
	                                  Const.SEND_MSG_TYPE_0, 
	                                  RouteUtil.DEFAULT_USER_AVATAR,
	                                  messageTitle,
	                                  msgContent, DateUtil.getCurrentTime());
		  List<String> userIds =   new ArrayList<String>(1);
		  userIds.add("2_"+declaration.getUserId());
		  myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	  }
	  
	  /**
	   * 出版社收到纸质表 审核 
	   * @author Mryang
	   * @createDate 2017年11月17日 下午5:08:49
	   * @param declarationId
	   * @param isPass  true收到资质表 / false退回资质表
	   * @throws CheckedServiceException
	   * @throws IOException
	   */
	  public void sendWhenReceiptAudit (Long declarationId,boolean isPass )  throws CheckedServiceException, IOException{
		  Declaration declaration=declarationService.getDeclarationById(declarationId);
		  if(null == declaration){
			  throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "申报表不存在");
		  }
		  if(null == declaration.getOrgId()){
			  throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM, "管理员为空");
		  }
		  Material material = materialService.getMaterialById(declaration.getMaterialId());
		  String  orgMsgContent="抱歉，贵校老师[<font color='red'>"+declaration.getRealname()+"</font>]在《<font color='red'>"+material.getMaterialName()+"</font>》提交的申报纸质表被退回";
		  String  writerMsgContent="抱歉，您在《<font color='red'>"+material.getMaterialName()+"</font>》提交的申报纸质表被退回，请您核对后重试";
		  if(isPass){//收到
			  orgMsgContent="您好，人民卫生出版社已收到贵校老师[<font color='red'>"+declaration.getRealname()+"</font>]在《<font color='red'>"+material.getMaterialName()+"</font>》提交的申报纸质表";
			  writerMsgContent="您好，人民卫生出版社已收到您在《<font color='red'>"+material.getMaterialName()+"</font>》提交的申报纸质表，感谢您的参与，请耐心等待遴选结果";
		  }
		  //存入消息主体
		  Message orgUserMessage = new Message (orgMsgContent);
		  orgUserMessage = messageService.add(orgUserMessage);
		  String orgMsg_id  = orgUserMessage.getId();
		  List<Long> orgIds = new ArrayList<Long>(1);
		  orgIds.add(declaration.getOrgId());
		  List<OrgUser> OrgUserList=orgUserService.getOrgUserListByOrgIds(orgIds);
		  List<UserMessage> userMessageList = new ArrayList<UserMessage>(OrgUserList.size());
		  List<String> userIds =   new ArrayList<String>(1);
		  for(OrgUser orgUser: OrgUserList){
			  userMessageList.add(new UserMessage(orgMsg_id, messageTitle,new Short("0"),0L, new Short("0"),orgUser.getId(),new Short("3")));
			  userIds.add("3_"+orgUser.getId());
		  }
		  //发送消息给管理员
		  userMessageService.addUserMessageBatch(userMessageList);
		  //websocket推送页面消息
		  WebScocketMessage webScocketMessage =
	            new WebScocketMessage(orgMsg_id, Const.MSG_TYPE_0,0L ,
	                                  "系统", Const.SENDER_TYPE_1,
	                                  Const.SEND_MSG_TYPE_0, 
	                                  RouteUtil.DEFAULT_USER_AVATAR,
	                                  messageTitle,
	                                  orgMsgContent, DateUtil.getCurrentTime());
		 myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
		  //------------------------给申报者发送消息--------------------
		  //存入消息主体
		  Message writerUserMessage = new Message (writerMsgContent);
		  writerUserMessage = messageService.add(writerUserMessage);
		  String writerMsg_id  = writerUserMessage.getId();
		  //发送消息给申报者
		  userMessageService.addUserMessage(new UserMessage(writerMsg_id, messageTitle,new Short("0"),0L, new Short("0"),declaration.getUserId(),new Short("2")));
		  //websocket推送页面消息
		  webScocketMessage =new WebScocketMessage(writerMsg_id, Const.MSG_TYPE_0,0L ,
	                                  "系统", Const.SENDER_TYPE_1,
	                                  Const.SEND_MSG_TYPE_0, 
	                                  RouteUtil.DEFAULT_USER_AVATAR,
	                                  messageTitle,
	                                  writerMsgContent, DateUtil.getCurrentTime());
		  userIds =   new ArrayList<String>(1);
		  userIds.add("2_"+declaration.getUserId());
		  myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
		  
	  }
	  
	  /**
	   * 随笔文章审核 向作家用户发送消息
	   * @author Mryang
	   * @createDate 2017年11月17日 下午5:23:16
	   * @param cmsContentId
	   * @param isPass true通过/false 不通过
	   * @throws CheckedServiceException
	   * @throws IOException
	   */
	  public void sendWhenInformalEssayAudit(Long cmsContentId,boolean isPass)  throws CheckedServiceException, IOException{
		 CmsContent cmsContent =cmsContentService.getCmsContentById(cmsContentId);
		 if(null == cmsContent){
			 throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "文章不存在");
		 }
		 String msgContent = "抱歉，您的文章《<font color='red'>"+cmsContent.getTitle()+"</font>》没有通过审核，请您修改后重试 ";
		 if(isPass){
			 msgContent = "恭喜！您的文章《<font color='red'>"+cmsContent.getTitle()+"</font>》已通过审核 ";
		 }
		  //存入消息主体
		  Message message = new Message (msgContent);
		  message = messageService.add(message);
		  String msg_id  = message.getId();
		  //发送消息给申报者
		  userMessageService.addUserMessage(new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),cmsContent.getAuthorId(),new Short("2")));
		  //websocket推送页面消息
		  WebScocketMessage webScocketMessage =new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
	                                  "系统", Const.SENDER_TYPE_1,
	                                  Const.SEND_MSG_TYPE_0, 
	                                  RouteUtil.DEFAULT_USER_AVATAR,
	                                  messageTitle,
	                                  msgContent, DateUtil.getCurrentTime());
		  List<String> userIds =   new ArrayList<String>(1);
		  userIds.add("2_"+cmsContent.getAuthorId());
		  myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
	}
	  
	  /**
	   * 某一本书的最终结果公布 或者 整套教材全部公布时 向当选者和学校管理员发送消息
	   * @author Mryang
	   * @createDate 2017年11月19日 上午11:24:31
	   * @param textBookId
	   * @throws CheckedServiceException
	   * @throws IOException
	   */
	  public void sendWhenPubfinalResult(Long textBookId) throws CheckedServiceException, IOException{
		  //获取这书根据书籍id获取申报职位
		  List<DecPosition> decPositionList = decPositionService.listDecPositionsByTextbookId(textBookId) ;
		  Textbook textbook = textbookService.getTextbookById(textBookId);
		  Material material = materialService.getMaterialById(textbook.getMaterialId());
		  //给主编发送
		  for(DecPosition decPosition:decPositionList){
			  if(null != decPosition.getChosenPosition() && null != decPosition.getRank()  && decPosition.getChosenPosition() ==1  ){
				  String editorMsg="《<font color='red'>"+material.getMaterialName()+"</font>》[<font color='red'>"+textbook.getTextbookName()+"</font>]的最终结果已公布，恭喜您当选[<font color='red'>"+textbook.getTextbookName()+"</font>]的";
				  editorMsg += "的第"+rank(decPosition.getRank())+"主编";
				  //获取申报表
				  Declaration declaration =declarationService.getDeclarationById(decPosition.getDeclarationId());
				  //存入消息主体
				  Message message = new Message (editorMsg);
				  message = messageService.add(message);
				  String msg_id  = message.getId();
				  //发送消息给申报者
				  userMessageService.addUserMessage(new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),declaration.getUserId(),new Short("2")));
				  //websocket推送页面消息
				  WebScocketMessage webScocketMessage =new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
			                                  "系统", Const.SENDER_TYPE_1,
			                                  Const.SEND_MSG_TYPE_0, 
			                                  RouteUtil.DEFAULT_USER_AVATAR,
			                                  messageTitle,
			                                  editorMsg, DateUtil.getCurrentTime());
				  List<String> userIds =   new ArrayList<String>(1);
				  userIds.add("2_"+declaration.getUserId());
				  myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
			  }
		  }
		  //给副主编发送
		  String associateEditor ="《<font color='red'>"+material.getMaterialName()+"</font>》[<font color='red'>"+textbook.getTextbookName()+"</font>]的最终结果已公布，恭喜您当选[<font color='red'>"+textbook.getTextbookName()+"</font>]的副主编";
		  Message message = new Message (associateEditor);
		  for(DecPosition decPosition:decPositionList){
			  if(null != decPosition.getChosenPosition() && null != decPosition.getRank()  && decPosition.getChosenPosition() ==2  ){
				  //获取申报表
				  Declaration declaration =declarationService.getDeclarationById(decPosition.getDeclarationId());
				  //存入消息主体
				  String msg_id  = message.getId();
				  if(null == msg_id){
					  message = messageService.add(message); 
				  }
				  msg_id  = message.getId();
				  //发送消息给申报者
				  userMessageService.addUserMessage(new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),declaration.getUserId(),new Short("2")));
				  //websocket推送页面消息
				  WebScocketMessage webScocketMessage =new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
			                                  "系统", Const.SENDER_TYPE_1,
			                                  Const.SEND_MSG_TYPE_0, 
			                                  RouteUtil.DEFAULT_USER_AVATAR,
			                                  messageTitle,
			                                  associateEditor, DateUtil.getCurrentTime());
				  List<String> userIds =   new ArrayList<String>(1);
				  userIds.add("2_"+declaration.getUserId());
				  myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
			  }
		  }
		  //给编委发送
		  String bianWei ="《<font color='red'>"+material.getMaterialName()+"</font>》[<font color='red'>"+textbook.getTextbookName()+"</font>]的最终结果已公布，恭喜您当选[<font color='red'>"+textbook.getTextbookName()+"</font>]的编委";
		  message = new Message (bianWei);
		  for(DecPosition decPosition:decPositionList){
			  if(null != decPosition.getChosenPosition()  && decPosition.getChosenPosition() ==3  ){
				  //获取申报表
				  Declaration declaration =declarationService.getDeclarationById(decPosition.getDeclarationId());
				  //存入消息主体
				  String msg_id  = message.getId();
				  if(null == msg_id){
					  message = messageService.add(message); 
				  }
				  msg_id  = message.getId();
				  //发送消息给申报者
				  userMessageService.addUserMessage(new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),declaration.getUserId(),new Short("2")));
				  //websocket推送页面消息
				  WebScocketMessage webScocketMessage =new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
			                                  "系统", Const.SENDER_TYPE_1,
			                                  Const.SEND_MSG_TYPE_0, 
			                                  RouteUtil.DEFAULT_USER_AVATAR,
			                                  messageTitle,
			                                  bianWei, DateUtil.getCurrentTime());
				  List<String> userIds =   new ArrayList<String>(1);
				  userIds.add("2_"+declaration.getUserId());
				  myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
			  }
		  }
		  //给学校管理员发送消息
		  if(material.getIsAllTextbookPublished()){//所有都发布了
			  String orgMsg="《<font color='red'>"+material.getMaterialName()+"</font>》的编写团队遴选已结束，贵校共[{sum}]位老师当选，名单如下：";
			  //根据教材Id查询对应的书籍集合
			  List<Textbook> textbooks=textbookService.getTextbookByMaterialId(material.getId());
			  List<Long> bookIds= new ArrayList<Long>();
			  for(Textbook book:textbooks){
				  bookIds.add(book.getId());
			  }
			  //根据书籍获取当选了该书籍的人员所属机构
			  List<Org> orgs=orgService.listBeElectedOrgByBookIds(bookIds);
			  for(Org org:orgs){
				  //根据orgid和bookid获取该机构某些已公布的书的申报职位
				  List<DecPosition> decPositions = decPositionService.listDecPositionsByTextbookIdAndOrgid(bookIds, org.getId());
				  if(null !=decPositions && decPositions.size() > 0){
					  String msgContent=orgMsg;
					  int sum=0;
					  for(int i=0;i<decPositions.size();i++){
						  DecPosition decPosition =decPositions.get(i);
						  Declaration declaration =declarationService.getDeclarationById(decPosition.getDeclarationId());
						  msgContent += "</br>"+"[<font color='red'>"+declaration.getRealname()+"</font>]";
						  msgContent += " - "+textbookService.getTextbookById(decPosition.getTextbookId()).getTextbookName()+" - ";
						  if(null != decPosition.getChosenPosition() && null != decPosition.getRank()  && decPosition.getChosenPosition() ==1  ){
							  msgContent += "第"+rank(decPosition.getRank())+"主编"; 
						  }else if(null != decPosition.getChosenPosition() && null != decPosition.getRank()  && decPosition.getChosenPosition() ==2){
							  msgContent += "副主编"; 
						  }else if(null != decPosition.getChosenPosition()  && decPosition.getChosenPosition() ==3){
							  msgContent += "编委";
						  }else{
							  continue;
						  }
						  sum++;
					  }
					  msgContent.replace("{sum}", String.valueOf(sum));
					  //存入消息主体
					  Message msg = new Message (msgContent);
					  msg = messageService.add(msg); 
					  String msg_id  = msg.getId();
					  //发送消息给申报者
					  userMessageService.addUserMessage(new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),org.getId(),new Short("3")));
					  //websocket推送页面消息
					  WebScocketMessage webScocketMessage =new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
				                                  "系统", Const.SENDER_TYPE_1,
				                                  Const.SEND_MSG_TYPE_0, 
				                                  RouteUtil.DEFAULT_USER_AVATAR,
				                                  messageTitle,
				                                  msgContent, DateUtil.getCurrentTime());
					  List<String> userIds =   new ArrayList<String>(1);
					  userIds.add("3_"+org.getId());
					  myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);

				  }
			  }
			  
//			  《全国高等学校五年制临床医学专业第九轮规划教材》的编写团队遴选已结束，贵校共[5]位老师当选，名单如下：
//			  [丁志国] - 局部解剖学 - 第一主编
//			  [王海滨] - 医学影像学 - 第二主编
//			  [雷国华] - 医学计算机应用 - 副主编
//			  [孙风梅] - 医学文献检索与论文写作 - 编委
//			  [宋守君] - 医患沟通 - 编委
		  }else{
			  String orgMsg="《<font color='red'>"+material.getMaterialName()+"</font>》[<font color='red'>"+textbook.getTextbookName()+"</font>]的最终结果已公布，贵校老师";
			  //《全国高等学校五年制临床医学专业第九轮规划教材》[传染病学]的最终结果已公布，贵校老师[丁志国]当选第一主编,[丁志国]当选第一主编,[丁志国]当选第一主编
			  //先要将学校分队
			  List<Long> bookIds= new ArrayList<Long>();
			  bookIds.add(textBookId);
			  //根据教材Id查询对应的书籍集合
			  List<Org> orgs=orgService.listBeElectedOrgByBookIds(bookIds);
			  for(Org org:orgs){
				  //根据orgid和bookid获取该机构某些已公布的书的申报职位
				  List<DecPosition> decPositions = decPositionService.listDecPositionsByTextbookIdAndOrgid(bookIds, org.getId());
				  if(null !=decPositions && decPositions.size() > 0){
					  //拼装消息
					  String msgContent=orgMsg;
					  for(int i=0;i<decPositions.size();i++){
						  DecPosition decPosition =decPositions.get(i);
						  Declaration declaration =declarationService.getDeclarationById(decPosition.getDeclarationId());
						  if(i>0){
							  msgContent += ",";  
						  }
						  msgContent += "[<font color='red'>"+declaration.getRealname()+"</font>]当选";
						  if(null != decPosition.getChosenPosition() && null != decPosition.getRank()  && decPosition.getChosenPosition() ==1  ){
							  msgContent += "第"+rank(decPosition.getRank())+"主编"; 
						  }else if(null != decPosition.getChosenPosition() && null != decPosition.getRank()  && decPosition.getChosenPosition() ==2){
							  msgContent += "副主编"; 
						  }else if(null != decPosition.getChosenPosition()  && decPosition.getChosenPosition() ==3){
							  msgContent += "编委";
						  }else{
							  continue;
						  }
					  }
					  //存入消息主体
					  Message msg = new Message (msgContent);
					  msg = messageService.add(msg); 
					  String msg_id  = msg.getId();
					  //发送消息给申报者
					  userMessageService.addUserMessage(new UserMessage(msg_id, messageTitle,new Short("0"),0L, new Short("0"),org.getId(),new Short("3")));
					  //websocket推送页面消息
					  WebScocketMessage webScocketMessage =new WebScocketMessage(msg_id, Const.MSG_TYPE_0,0L ,
				                                  "系统", Const.SENDER_TYPE_1,
				                                  Const.SEND_MSG_TYPE_0, 
				                                  RouteUtil.DEFAULT_USER_AVATAR,
				                                  messageTitle,
				                                  msgContent, DateUtil.getCurrentTime());
					  List<String> userIds =   new ArrayList<String>(1);
					  userIds.add("3_"+org.getId());
					  myWebSocketHandler.sendWebSocketMessageToUser(userIds, webScocketMessage);
				  }
			  }			  
		  }
	  }
	  
	  /**
	   * 
	   * 主编排位数字转成中文
	   * @author Mryang
	   * @createDate 2017年11月20日 下午3:23:59
	   * @param rank 大于0的正整数
	   * @return 
	   */
	  private String rank(int rank){
		  String res="";
		  switch (rank) {
			  case 1:
					res="一";
					break;
			  case 2:
					res="二";
					break;
			  case 3:
					res="三";
					break;
			  case 4:
					res="四";
					break;
			  case 5:
					res="五";
					break;
			  case 6:
					res="六";
					break;
			  case 7:
					res="七";
					break;
			  case 8:
					res="八";
					break;
			  case 9:
					res="九";
					break;
			  case 10:
					res="十";
					break;
			  default:
					res=String.valueOf(rank);
					break;
		 } 
		 return res;
	  }
}

















