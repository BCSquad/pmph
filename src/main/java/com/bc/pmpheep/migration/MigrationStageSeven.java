package com.bc.pmpheep.migration;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.MessageAttachment;
import com.bc.pmpheep.back.po.UserMessage;
import com.bc.pmpheep.back.service.MessageAttachmentService;
import com.bc.pmpheep.back.service.UserMessageService;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.general.service.MessageService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.SQLParameters;
import com.bc.pmpheep.utils.ExcelHelper;

/**
 * 
 * @author Mryang
 *
 * @createDate 2017年11月2日 下午12:06:10
 *
 */
@Component
public class MigrationStageSeven {
	
	Logger  logger = LoggerFactory.getLogger(MigrationStageSeven.class);
	
	@Resource
    private MessageService           messageService;
	@Resource
	private com.bc.pmpheep.general.service.FileService fileService;
	@Resource
	private UserMessageService userMessageSevice;
	@Resource 
	private MessageAttachmentService messageAttachmentService;
	@Resource
    private ExcelHelper excelHelper;
	
	@SuppressWarnings("all")
	public void userMessage_messageAttachment (){
		String sql="select "+
						 "DISTINCT  "+
						 "a.msgid , "+
						 "a.msgcontent , "+
						 "case WHEN a.msgtype =12  then  1         else 0 end msgtype , "+
						 "a.msgtitle title, "+
						 "case WHEN a.msgtype =12  then  d.new_pk  else 0   end senderid , "+
						 "case WHEN a.msgtype =12 and d.sysflag=0 then 1 when a.msgtype=12 and d.sysflag=1 and d.usertype=2 then 3 when a.msgtype=12 then 2  else 0 end  sendertype ,  "+ 
						 "e.new_pk  receiverid, "+
						 "case WHEN e.sysflag=0 then 1 when e.sysflag=1 and e.usertype=2 then 3 else 2 end   receivertype, "+
						 "b.recivetype isread  , "+
						 "0   iswithdraw, "+
						 "b.isdelete  isdeleted, "+
						 "b.senddate gt, "+
						 "ifnull(b.recivedate,b.senddate) ut "+
					"from sys_messages a "+
                		"LEFT JOIN sys_msgrecive b on b.msgid=a.msgid "+
                		"LEFT JOIN (select a.userid,a.new_pk,a.sysflag,b.usertype from sys_user a left join sys_userext b on b.userid=a.userid )  d on  d.userid =a.formuser  "+
                	    "LEFT JOIN (select a.userid,a.new_pk,a.sysflag,b.usertype from sys_user a left join sys_userext b on b.userid=a.userid )  e on  e.userid =b.touser ";
//		String tableName="";
//		JdbcHelper.addColumn(tableName); //增加new_pk字段
		List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		List<Map<String, Object>> excel = new LinkedList<>();
		Map<String, String>       msgidOldAndNew = new HashMap<String, String>(maps.size());
		int count =0; 
		for (Map<String, Object> map : maps) {
			StringBuilder  exception=  new StringBuilder("");
			//先保存这条记录
			Short msgtype    = new Short (String.valueOf(map.get("msgtype")));
			if(null == msgtype ){
				map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("消息类型为空,设置默认 0").toString());
				excel.add(map);
				msgtype =0;
			}
			String title     =(String)map.get("title");
			if(StringUtil.isEmpty(title)){
				map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("消息标题为空").toString());
				excel.add(map);
				continue;
			}
			Long senderid    =(Long)  map.get("senderid");
			if(null == senderid ){
				map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("发送者id为空").toString());
				excel.add(map);
				continue;
			}
			Short sendertype =new Short(String.valueOf(map.get("sendertype")));
			if(null == sendertype ){
				map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("发送者类型为空,设置默认 0").toString());
				excel.add(map);
				sendertype =0;
			}
			Long receiverid  =(Long ) map.get("receiverid");
			if(null == receiverid ){
				map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("接收者id为空").toString());
				excel.add(map);
				continue;
			}
			Short receivertype=new Short (String.valueOf((map.get("receivertype"))));
			if(null == receivertype ){
				map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("接收者类型为空,设置默认 0").toString());
				excel.add(map);
				receivertype =0;
			}
			Boolean isread    ="1".equals(map.get("isread"));
			Boolean iswithdraw="1".equals(map.get("iswithdraw"));
			Boolean isdeleted ="1".equals(map.get("isdeleted"));
			Timestamp gt      =(Timestamp)map.get("gt");
			Timestamp ut      =(Timestamp)map.get("ut");
			UserMessage  userMessage = new UserMessage("---------",title,msgtype,senderid,sendertype,
					receiverid, receivertype, isread, iswithdraw, isdeleted,gt, ut);
			try {
				userMessage=userMessageSevice.addUserMessage(userMessage);
			} catch (Exception e) {
				map.put(SQLParameters.EXCEL_EX_HEADER, exception.append(e.getMessage().toString()));
				excel.add(map);
				logger.error( e.getMessage());
				continue;
			}
			count++;
			if(count%1000==0){//打印进度
				logger.info("执行了:"+(count*100.0)/maps.size()+"%");
			}
			//对html里面内置的图片处理
			String msgid=      String.valueOf(map.get("msgid"));
			String msg_id = msgidOldAndNew.get(msgid); //MongoDB对应主键
			if(null == msg_id || "".equals(msg_id)){//消息主体没有存入
				String msgcontent =(String) map.get("msgcontent");
				List<String>srcs  =getImgSrc(msgcontent);
				if(null != srcs && srcs.size()>0){//内容里面包含图片
					for(String src :srcs){
						String mongoId = null;
						try {
							mongoId =fileService.migrateFile(src.replace("/pmph_imesp",""), FileType.MSG_PIC,userMessage.getId());
						} catch (Exception e) {
							
						}
						if(null != mongoId ){
							msgcontent = msgcontent.replace(src, SQLParameters.severPath+"/image/"+mongoId);
						}
					}
				}
				Message message = new Message (msgcontent);
				message = messageService.add(message);
				msg_id  = message.getId();
				msgidOldAndNew.put(msgid, msg_id);
				//附件处理
				sql="select c.* from sys_messages a  "+
                		"LEFT JOIN (   "+
                			 "sELECT * FROM pub_addfileinfo WHERE tablename ='SYS_MESSAGES' AND childsystemname='sysNotice'   "+
                		")C ON C.tablekeyid = A.msgid where c.fileid is not null and  a.msgid =?";
				List<Map<String, Object>> message_attachments = JdbcHelper.getJdbcTemplate().queryForList(sql,new Object[]{msgid});
				for(Map<String, Object> message_attachment :message_attachments ){
					MessageAttachment messageAttachment = new MessageAttachment();
					messageAttachment.setMsgId(msgid);
					messageAttachment.setAttachment("-----------");
					messageAttachment.setAttachmentName((String)message_attachment.get("filename"));
					messageAttachment=messageAttachmentService.addMessageAttachment(messageAttachment);
					String mongoId = null;
					try {
						mongoId =fileService.migrateFile((String)message_attachment.get("fildir"), FileType.MSG_FILE,messageAttachment.getId());
					} catch (Exception e) {
						
					}
					if(null != mongoId){
						messageAttachment.setAttachment(mongoId);
						messageAttachmentService.updateMessageAttachment(messageAttachment);
					}
				}
			}
			userMessage.setMsgId(msg_id);
			userMessageSevice.updateUserMessage(userMessage);
		}
		if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "sys_messages", "sys_messages");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", "sys_messages", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
      //记录信息
        Map<String,Object> msg= new HashMap<String,Object>();
        msg.put("result","sys_messages  表迁移完成"+count+"/"+ maps.size());
        SQLParameters.msg.add(msg);
	}
	
	
	//抓取HTML中src地址;
	public  static List<String> getImgSrc(String htmlStr) {  
        String img = "";  
        Pattern p_image;  
        Matcher m_image;  
        List<String> pics = new ArrayList<String>();  
      //String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址  
        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";  
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);  
        m_image = p_image.matcher(htmlStr);  
        while (m_image.find()) {  
            img = img + "," + m_image.group();  
            // Matcher m =  
            // Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src  
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);  
            while (m.find()) {  
                pics.add(m.group(1));  
            }  
        }  
        return pics;  
    }  
	
	public void start(){
		userMessage_messageAttachment ();
	}
	
	
}
