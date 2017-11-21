package com.bc.pmpheep.migration;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.po.Message;
import com.bc.pmpheep.general.service.MessageService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.SQLParameters;
import com.bc.pmpheep.service.exception.CheckedServiceException;
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

    Logger logger = LoggerFactory.getLogger(MigrationStageSeven.class);

    @Resource
    private MessageService messageService;
    @Resource
    private com.bc.pmpheep.general.service.FileService fileService;
    @Resource
    private UserMessageService userMessageSevice;
    @Resource
    private MessageAttachmentService messageAttachmentService;
    @Resource
    private ExcelHelper excelHelper;

    public void start() {
        Date begin = new Date();
        userMessage_messageAttachment();
        logger.info("迁移第七步运行结束，用时：{}", JdbcHelper.getPastTime(begin));
    }

    public void userMessage_messageAttachment() {//case WHEN e.sysflag=0 then 1 when e.sysflag=1 and e.usertype=2 then 3 else 2 end
        String sql = "select "
                + "DISTINCT  "
                + "a.msgid , "
                + "a.msgcontent , "
                + "case WHEN a.msgtype =12  then  1         else 0 end msgtype , "
                + "a.msgtitle title, "
                + "case WHEN a.msgtype =12  then  d.new_pk  else 0   end senderid , "
                + "case WHEN a.msgtype =12 and d.sysflag=0 then 1 when a.msgtype=12 and d.sysflag=1 and d.usertype=2 then 3 when a.msgtype=12 then 2  else 0 end  sendertype ,  "
                + "ifnull(e.new_pk,f.new_pk)  receiverid, "
                + "case WHEN e.new_pk is null then case WHEN f.sysflag=0 then 1 when f.sysflag=1 and f.usertype=2 then 3 else 2 end "
                + "     else                       case WHEN e.sysflag=0 then 1 when e.sysflag=1 and e.usertype=2 then 3 else 2 end "
                + "end   receivertype, "
                + "case WHEN e.new_pk is null then a.recivetype "
                + "     else b.recivetype "
                + "end isread  , "
                + "0   iswithdraw, "
                + "b.isdelete  isdeleted, "
                + "b.senddate gt, "
                + "ifnull(b.recivedate,b.senddate) ut "
                + "from sys_messages a "
                + "LEFT JOIN sys_msgrecive b on b.msgid=a.msgid "
                + "LEFT JOIN (select a.userid,a.new_pk,a.sysflag,b.usertype from sys_user a left join sys_userext b on b.userid=a.userid )  d on  d.userid =a.formuser  "
                + "LEFT JOIN (select a.userid,a.new_pk,a.sysflag,b.usertype from sys_user a left join sys_userext b on b.userid=a.userid )  e on  e.userid =b.touser "
                + "LEFT JOIN (select a.userid,a.new_pk,a.sysflag,b.usertype from sys_user a left join sys_userext b on b.userid=a.userid )  f on  f.userid =a.touser ";
//		String tableName="";
//		JdbcHelper.addColumn(tableName); //增加new_pk字段
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        Map<String, String> msgidOldAndNew = new HashMap<String, String>(maps.size());
        int count = 0;
        for (Map<String, Object> map : maps) {
            StringBuilder exception = new StringBuilder("");
            //先保存这条记录
            String title = (String) map.get("title");
            if (StringUtil.isEmpty(title)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("消息标题查询到为空值。"));
                excel.add(map);
                continue;
            }
            Long senderid = (Long) map.get("senderid");
            if (ObjectUtil.isNull(senderid)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("发送者id查询到为空值。"));
                excel.add(map);
                continue;
            }
            Long receiverid = (Long) map.get("receiverid");
            if (ObjectUtil.isNull(receiverid)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("接收者id查询到为空值。"));
                excel.add(map);
                continue;
            }
            Short msgtype = new Short(String.valueOf(map.get("msgtype")));
            if (ObjectUtil.isNull(msgtype)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("消息类型查询到为空值。"));
                excel.add(map);
                msgtype = 0;
            }
            Short sendertype = new Short(String.valueOf(map.get("sendertype")));
            if (ObjectUtil.isNull(sendertype)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("发送者类型查询到为空值。"));
                excel.add(map);
                sendertype = 0;
            }
            Short receivertype = new Short(String.valueOf((map.get("receivertype"))));
            if (ObjectUtil.isNull(receivertype)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("接收者类型查询到为空值。"));
                excel.add(map);
                receivertype = 0;
            }
            Boolean isread = "1".equals(map.get("isread"));
            Boolean iswithdraw = "1".equals(map.get("iswithdraw"));
            Boolean isdeleted = "1".equals(map.get("isdeleted"));
            Timestamp gt = (Timestamp) map.get("gt");
            Timestamp ut = (Timestamp) map.get("ut");
            UserMessage userMessage = new UserMessage("---------", title, msgtype, senderid, sendertype,
                    receiverid, receivertype, isread, iswithdraw, isdeleted, gt, ut);
            try {
                userMessage = userMessageSevice.addUserMessage(userMessage);
            } catch (CheckedServiceException ex) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append(ex.getMessage()));
                excel.add(map);
                logger.error(ex.getMessage());
                continue;
            }
            count++;
            long pk = userMessage.getId();
            if (count % 1000 == 0) {//打印进度
                logger.info("执行了:" + (count * 100.0) / maps.size() + "%");
            }
            //对html里面内置的图片处理
            String msgid = String.valueOf(map.get("msgid"));
            String msg_id = msgidOldAndNew.get(msgid); //MongoDB对应主键
            if (StringUtil.isEmpty(msg_id)) {//消息主体没有存入
                String msgcontent = (String) map.get("msgcontent");
                if (msgcontent.contains("img src=")) {
                    List<String> srcs = JdbcHelper.getImgSrc(msgcontent);
                    for (String src : srcs) {
                        try {
                            String mongoId = fileService.migrateFile(src.replace("/pmph_imesp", ""), FileType.MSG_IMG, pk);
                            msgcontent = msgcontent.replace(src, "/image/" + mongoId);
                        } catch (IOException ex) {
                            logger.warn("无法根据文章内容中的图片路径找到指定文件{}", ex.getMessage());
                        }
                    }
                }
                Message message = new Message(msgcontent);
                message = messageService.add(message);
                msg_id = message.getId();
                msgidOldAndNew.put(msgid, msg_id);
                //附件处理
                sql = "select c.* from sys_messages a  "
                        + "LEFT JOIN (   "
                        + "sELECT * FROM pub_addfileinfo WHERE tablename ='SYS_MESSAGES' AND childsystemname='sysNotice'   "
                        + ")C ON C.tablekeyid = A.msgid where c.fileid is not null and  a.msgid =?";
                List<Map<String, Object>> message_attachments = JdbcHelper.getJdbcTemplate().queryForList(sql, new Object[]{msgid});
                for (Map<String, Object> message_attachment : message_attachments) {
                    MessageAttachment messageAttachment = new MessageAttachment();
                    messageAttachment.setMsgId(msg_id);
                    messageAttachment.setAttachment("-----------");
                    messageAttachment.setAttachmentName((String) message_attachment.get("filename"));
                    messageAttachment = messageAttachmentService.addMessageAttachment(messageAttachment);
                    String mongoId = null;
                    try {
                        mongoId = fileService.migrateFile((String) message_attachment.get("fildir"), FileType.MSG_FILE, messageAttachment.getId());
                    } catch (Exception e) {

                    }
                    if (null != mongoId) {
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
                excelHelper.exportFromMaps(excel, "消息-用户映射表（多对多）", "user_message");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", "sys_messages", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<>();
        msg.put("result", "sys_messages  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }
}
