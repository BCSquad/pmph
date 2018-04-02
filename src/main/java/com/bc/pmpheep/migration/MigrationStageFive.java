package com.bc.pmpheep.migration;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.service.TextbookService;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.SQLParameters;
import com.bc.pmpheep.utils.ExcelHelper;

/**
 * 图五 教材书籍迁移工具类
 *
 * @author Mr
 *
 */
@Component
public class MigrationStageFive {

    private final Logger logger = LoggerFactory.getLogger(MigrationStageFive.class);

    @Resource
    TextbookService textbookService;
    @Resource
    ExcelHelper excelHelper;

    public void start() {
        Date begin = new Date();
        textbook();
        logger.info("迁移第五步运行结束，用时：{}", JdbcHelper.getPastTime(begin));
    }

    /**
     * 教材书籍表
     */
    private void textbook() {
        String sql = "select DISTINCT a.*,b.new_pk newmaterid,c.new_pk bookcreateuserid,b.createdate newcreatedate,"
                + " e.new_pk editroid,d.new_pk newcreateuserid,b.createuserid oldcreateuserid"
                + " from teach_bookinfo a "
                + " left join teach_material b on b.materid=a.materid "
                + " left join sys_user c on c.userid=a.createuserid "
                + " left join sys_user d on d.userid=b.createuserid "
                + " left join sys_user e on e.userid=a.userid "
                + "	where a.bookname not like '%测%' and b.matername not like '%测%'"
                + " and b.matername not like '%sd%' "
                + "	and b.matername not like '%7%' and b.matername not like '%1%'"; 
        String tableName = "teach_bookinfo";// 要迁移的旧表名称
        JdbcHelper.addColumn(tableName); // 增加new_pk字段
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        Map<String, Object> result = new LinkedHashMap<>();
        int count = 0;//迁移成功的条目数
        int correctCount = 0;
        int[] state = {0,0,0,0,0};
        StringBuilder reason = new StringBuilder();
        StringBuilder dealWith = new StringBuilder();
        //默认值
        Long constant=0L;
        /* 开始遍历查询结果 */
        for (Map<String, Object> map : maps) {
        	StringBuilder exception = new StringBuilder();
            /* 根据MySQL字段类型进行类型转换 */
            String id = (String) map.get("bookid");// 旧表主键

            Integer revision = (Integer) map.get("revision");
            if (ObjectUtil.isNull(revision)) {
                revision = 1;//没有值，则书籍轮次默认为1
            }
            Long c = (Long) map.get("newmaterid");
            if (ObjectUtil.isNull(c)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("该教材id为空。"));
                //因教材id不能为空，默认为0
                c=constant;
                excel.add(map);
                logger.error("该教材id为空，此结果将将被记录在Excel中");
                if (state[0] == 0){
                	reason.append("找不到教材的唯一标识。");
                	dealWith.append("放弃迁移。");
                	state[0] = 1;
                }
                continue;
            }
            Long createuserid = (Long) map.get("bookcreateuserid");
            Long newcreateuseid = (Long) map.get("newcreateuserid");
            if (ObjectUtil.isNull(createuserid) && ObjectUtil.isNull(newcreateuseid)) {
                /*记录教材书籍表没有的创建者id为空*/
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("该教材的创建者id为空。"));
                newcreateuseid=constant;
                excel.add(map);
                logger.error("该教材的创建者id为空，此结果将将被记录在Excel中");
                if (state[1] == 0){
                	reason.append("找不到教材的创建者。");
                	dealWith.append("设为默认值迁入数据库。");
                	state[1] = 1;
                }
                //continue;
            }
            String bookname = (String) map.get("bookname");
            if (StringUtil.isEmpty(bookname)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("该书籍名称为空。"));
                excel.add(map);
                logger.error("该书籍名称为空，此结果将将被记录在Excel中");
                if (state[2] == 0){
                	reason.append("找不到书籍的名称。");
                	dealWith.append("放弃迁移。");
                	state[2] = 1;
                }
                continue;
            }
            java.util.Date ceDate = (java.util.Date) map.get("createdate");
            /*教材表对应书籍创建时间*/
            java.util.Date createdate = (java.util.Date) map.get("newcreatedate");
            if (ObjectUtil.isNull(ceDate) && ObjectUtil.isNull(createdate)) {//如果没有创建时间 就查找关联教材创建时间
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("创建时间为空。"));
                excel.add(map);
                logger.error("创建时间为空，因新库不能插入null，去教材表找对应创建时间，此结果将将被记录在Excel中");
                if (state[3] ==0){
                	reason.append("找不到书籍的创建时间。");
                	dealWith.append("放弃迁移。");
                	state[3] = 1;
                }
                continue;
            }
            Integer xnumber = (Integer) map.get("xnumber");
            if (ObjectUtil.isNull(xnumber)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("图书序号为空。"));
                xnumber=(int) constant.longValue();
                excel.add(map);
                logger.error("图书序号为空，此结果将将被记录在Excel中");
                if (state[4] == 0){
                	reason.append("找不到书籍的序号。");
                	dealWith.append("设为默认值迁入数据库。");
                	state[4] = 1;
                }
                //continue;
            }
            Textbook textbook = new Textbook();
            textbook.setTextbookRound(revision); //书籍轮次
            textbook.setMaterialId(c);// 教材id
            if (createuserid == newcreateuseid) {// 如果没有创建者id 就找教材创建者id
                textbook.setFounderId(createuserid); // 创建者id
            } else {
                textbook.setFounderId(newcreateuseid); // 创建者id	
            }
            if (ceDate == createdate) {
                textbook.setGmtCreate((Timestamp) ceDate);// 创建时间
            } else {
                textbook.setGmtCreate((Timestamp) createdate);
            }
            textbook.setSort(xnumber);// 图书序号
            textbook.setPlanningEditor((Long) map.get("editroid"));// 策划编辑id
            textbook.setTextbookName(bookname);// 书籍名称
            textbook.setGmtPublished((Timestamp) map.get("resultpublishdate"));// 公布时间
            textbook.setIsbn((String) map.get("isbn"));// ISBN号
            textbook.setIsLocked(false);// 是否锁定（通过）旧平台无该状态 默认0
            textbook.setRevisionTimes(0);// 公布后再次修改次数  旧平台无该状态 默认0
            textbook.setRepublishTimes(0);// 公布后再次公布次数 旧平台无该状态 默认0
            textbook.setGmtUpdate((Timestamp) map.get("resultpublishdate"));// 修改时间 旧平台无 默认为公布时间
            /**
             * 旧表处理状态 10：待选主编/副主编；11：已分配主编/副主编；12：已确定主编/副主编 20：已分配策划编辑；30：策划编辑已预选编委；
             * 40：第一主编已选编委；50：策划编辑审核通过编委；60：项目编辑审核通过编委；70：主任审核通过；80：已发布
             */
            Integer dealtype = Integer.parseInt((String) map.get("dealtype"));
            if (dealtype >= 11) {
                textbook.setIsChiefChosen(true);// 是否已选定第一主编
            }
            if (dealtype >= 12) {
                textbook.setIsChiefPublished(true);// 是否已预选编委
            }
            if (dealtype >= 40) {
                textbook.setIsListSelected(true);// 主编是否选定编委
            }
            if (dealtype >= 50) {
                textbook.setIsPlanningEditorConfirm(true);// 策划编辑是否确定名单
            }
            if (dealtype >= 60) {
                textbook.setIsProjectEditorConfirm(true);// 项目编辑是否确定名单
            }
            if (dealtype >= 80) {
                textbook.setIsPublished(true);// 是否已公布
                textbook.setIsLocked(true);//当公布后为锁定
            }
            /* 开始新增新表对象，并设置属性值 */
            textbook = textbookService.addTextbook(textbook);
            long pk = textbook.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "bookid", id); // 更新旧表中new_pk字段
            count++;
            if (null == map.get("exception")){
            	correctCount++;
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "教材书籍表", "textbook");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        if (correctCount != maps.size()){
        	result.put(SQLParameters.EXCEL_HEADER_TABLENAME, "textbook");
        	result.put(SQLParameters.EXCEL_HEADER_DESCRIPTION, "教材书籍表");
        	result.put(SQLParameters.EXCEL_HEADER_SUM_DATA, maps.size());
        	result.put(SQLParameters.EXCEL_HEADER_MIGRATED_DATA, count);
        	result.put(SQLParameters.EXCEL_HEADER_CORECT_DATA, correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_TRANSFERED_DATA, count - correctCount);
        	result.put(SQLParameters.EXCEL_HEADER_NO_MIGRATED_DATA, maps.size() - count);
        	result.put(SQLParameters.EXCEL_HEADER_EXCEPTION_REASON, reason.toString());
        	result.put(SQLParameters.EXCEL_HEADER_DEAL_WITH, dealWith.toString());
        	SQLParameters.STATISTICS_RESULT.add(result);
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", tableName, excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

}
