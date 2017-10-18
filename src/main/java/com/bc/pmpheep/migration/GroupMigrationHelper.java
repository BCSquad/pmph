/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.migration;

import com.bc.pmpheep.back.po.PmphGroup;
import com.bc.pmpheep.back.po.PmphGroupMember;
import com.bc.pmpheep.back.po.PmphGroupMessage;
import com.bc.pmpheep.back.service.PmphGroupMemberService;
import com.bc.pmpheep.back.service.PmphGroupMessageService;
import com.bc.pmpheep.back.service.PmphGroupService;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 小组数据迁移工具类，使用本工具类需要先迁移pmph_user和writer_user表
 *
 * @author L.X <gugia@qq.com>
 */
@Component
public class GroupMigrationHelper {

    private final Logger logger = LoggerFactory.getLogger(GroupMigrationHelper.class);
    Map<String, Long> groupIdMap; //pmph_group与bbs_group的主键关系映射
    Map<String, Long> groupMemberIdMap; //pmph_group_member与bbs_groupusers的主键关系映射

    @Resource
    PmphGroupService groupService;
    @Resource
    PmphGroupMemberService groupMemberService;
    @Resource
    PmphGroupMessageService groupMessageService;

    public void group() {
        String sql = "SELECT * FROM bbs_group";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        groupIdMap = new HashMap(maps.size());//初始化
        int count = 0;//迁移成功的条目数
        for (Map<String, Object> map : maps) {
            PmphGroup pmphGroup = new PmphGroup();
            String groupTitle = map.get("groupTitle").toString();
            pmphGroup.setGroupName(groupTitle);
            pmphGroup.setGroupImage("null");//待完成
            pmphGroup.setFounderId(0L);//此处应有中间表，建立旧库sys_user和新库三张用户表的映射关系
            if (null != map.get("bookid")) {
                long bookid = (Long) map.get("bookid");
                pmphGroup.setBookId(bookid);
            }
            String createTime = map.get("createTime").toString();
            pmphGroup.setGmtCreate(Timestamp.valueOf(createTime));
            pmphGroup = groupService.addPmphGroup(pmphGroup);
            String groupID = map.get("groupID").toString();
            groupIdMap.put(groupID, pmphGroup.getId());
            count++;
        }
        logger.info("pmph_user表迁移完成！");
        logger.info("旧库中共 {} 条数据，迁移完成 {} 条", maps.size(), count);
    }

    public void groupMember() {
        /* 取得PmphGroupMember对应旧表的所有数据 */
        String sql = "SELECT * FROM bbs_groupusers";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        groupMemberIdMap = new HashMap(maps.size());//初始化
        int count = 0;//迁移成功的条目数
        /* 以下开始遍历旧表数据，根据相应规则进行数据转换 */
        for (Map<String, Object> map : maps) {
            PmphGroupMember member = new PmphGroupMember();
            String groupID = map.get("groupID").toString();
            long groupId = groupIdMap.get(groupID);//取得旧表groupID对应新表的主键
            member.setGroupId(groupId);
            long userId = 0L;//此处应有中间表，建立旧库sys_user和新库三张用户表的映射关系
            member.setUserId(userId);
            member.setIsWriter(true);//同上，需要先迁移用户表
            /* 是否创建者的判断依据是GUID是否等于groupID */
            String guid = map.get("GUID").toString();
            if (guid.equals(groupID)) {
                member.setIsFounder(true);
            }
            /* 旧表的isManager和新表的is_admin疑似刚好相反 */
            int isManager = (Integer) map.get("isManager");
            member.setIsAdmin(isManager == 0);
            String userName = map.get("userName").toString();
            member.setDisplayName(userName);
            member = groupMemberService.addPmphGroupMember(member);
            groupMemberIdMap.put(guid, member.getId());
            count++;
        }
        logger.info("pmph_user表迁移完成！");
        logger.info("旧库中共 {} 条数据，迁移完成 {} 条", maps.size(), count);
    }

    public void groupMessage() {
        /* 取得PmphGroupMember对应旧表的所有数据 */
        String sql = "SELECT * FROM bbs_discuss";
        String sql_getGUID = "SELECT GUID FROM bbs_groupusers WHERE groupID = ? AND userID = ?";
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0;//迁移成功的条目数
        /* 以下开始遍历旧表数据，根据相应规则进行数据转换 */
        for (Map<String, Object> map : maps) {
            PmphGroupMessage groupMessage = new PmphGroupMessage();
            String groupID = map.get("groupID").toString();
            /* 如果消息没有对应的小组id，则小组可能已被删除，这里应该打印并记录（待完成） */
            if (null == groupIdMap.get(groupID)) {
                logger.warn("未找到消息对应小组，groupID = {}", groupID);
                continue;
            }
            long groupId = groupIdMap.get(groupID);//取得旧表groupID对应新表的主键
            groupMessage.setGroupId(groupId);
            /* 新库member_id是pmph_group_member的主键，这里需要转换 */
            String userID = map.get("userID").toString();
            Map<String, Object> result = JdbcHelper.getJdbcTemplate().queryForMap(sql_getGUID, groupID, userID);
            String guid = result.get("GUID").toString();
            groupMessage.setMemberId(groupMemberIdMap.get(guid));
            String content = map.get("content").toString();
            groupMessage.setMsgContent(content);
            String createtime = map.get("createtime").toString();
            groupMessage.setGmtCreate(Timestamp.valueOf(createtime));
            groupMessageService.addPmphGroupMessage(groupMessage);
            count++;
        }
        logger.info("pmph_user表迁移完成！");
        logger.info("旧库中共 {} 条数据，迁移完成 {} 条", maps.size(), count);
    }
}
