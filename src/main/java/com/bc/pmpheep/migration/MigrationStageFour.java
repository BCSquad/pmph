package com.bc.pmpheep.migration;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.MaterialContact;
import com.bc.pmpheep.back.po.MaterialExtension;
import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.back.po.MaterialNoteAttachment;
import com.bc.pmpheep.back.po.MaterialNoticeAttachment;
import com.bc.pmpheep.back.po.MaterialOrg;
import com.bc.pmpheep.back.po.MaterialProjectEditor;
import com.bc.pmpheep.back.po.MaterialType;
import com.bc.pmpheep.back.service.MaterialContactService;
import com.bc.pmpheep.back.service.MaterialExtensionService;
import com.bc.pmpheep.back.service.MaterialExtraService;
import com.bc.pmpheep.back.service.MaterialNoteAttachmentService;
import com.bc.pmpheep.back.service.MaterialNoticeAttachmentService;
import com.bc.pmpheep.back.service.MaterialOrgService;
import com.bc.pmpheep.back.service.MaterialProjectEditorService;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.service.MaterialTypeService;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.SQLParameters;
import com.bc.pmpheep.utils.ExcelHelper;

/**
 * @author Mryang
 *
 * @createDate 2017年11月2日 下午12:05:57
 *
 */
@Component
public class MigrationStageFour {

    Logger logger = LoggerFactory.getLogger(MigrationStageFour.class);
    //用来储存MaterialExtra返回的主键，供后面使用 Map<教材id,教材通知备注表id> 
    public static Map<Long, Long> mps = new HashMap<Long, Long>(12);

    @Resource
    private ExcelHelper excelHelper;

    @Autowired
    private MaterialTypeService materialTypeService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private MaterialExtensionService materialExtensionService;
    @Autowired
    private MaterialExtraService materialExtraService;
    @Autowired
    private MaterialNoticeAttachmentService materialNoticeAttachmentService;
    @Autowired
    private FileService fileService;
    @Autowired
    private MaterialNoteAttachmentService materialNoteAttachmentService;
    @Autowired
    private MaterialContactService materialContactService;
    @Autowired
    private MaterialOrgService materialOrgService;
    @Autowired
    private MaterialProjectEditorService materialProjectEditorService;

    public void start() {
        Date begin = new Date();
        materialType();
        material();
        materialExtension();
        materialExtra();
        transferMaterialNoticeAttachment();
        transferMaterialNoteAttachment();
        transferMaterialContact();
        materialOrg();
        materialPprojectEeditor();
        logger.info("迁移第四步运行结束，用时：{}", JdbcHelper.getPastTime(begin));
    }

    protected void materialType() {
        String tableName = "sys_booktypes";
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        List<Map<String, Object>> maps = JdbcHelper.queryForList(tableName);//取得该表中所有数据
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        //插入除parentid和path的字段；
        for (Map<String, Object> map : maps) {
            /*因此表有主要级字段和次要级字段，次要级字段插入新表同时也需导出Excel，因此异常信息不止一条，
			 * 用StringBulider进行拼接成最终的异常信息
             */
            StringBuilder exception = new StringBuilder();
            BigDecimal bookTypesID = (BigDecimal) map.get("BookTypesID");
            BigDecimal parentTypesId = (BigDecimal) map.get("ParentTypesID");
            String typeName = (String) map.get("TypeName");
            if (StringUtil.isEmpty(typeName)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("类型名称为空。"));
                excel.add(map);
                continue;
            }
            /*
			 * 原数据库系统表的数据是按照父-子顺序排列的，因此不许循环可以直接调用获取父节点和path的方法
             */
            Long parentId = 0L;
            if (parentTypesId.intValue() != 0) {
                parentId = JdbcHelper.getPrimaryKey(tableName, "BookTypesID", parentTypesId);
            }
            String path = JdbcHelper.getPath(tableName, "BookTypesID", "ParentTypesID", parentTypesId);
            Integer sort = (Integer) map.get("Sortno");
            if (ObjectUtil.notNull(sort) && sort < 0) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("排序为负数。"));
                sort = 999;
                excel.add(map);
            }
            String note = (String) map.get("Remark");
            //书籍分类备注信息比较重要，虽然有默认值，但仍认为是次要级字段
            if (StringUtil.isEmpty(note)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("备注为空。"));
                note = typeName;
                excel.add(map);
            }
            MaterialType materialType = new MaterialType();
            materialType.setParentId(parentId);
            materialType.setPath(path);
            materialType.setTypeName(typeName);
            materialType.setSort(sort);
            materialType.setNote(note);
            try {
                materialType = materialTypeService.addMaterialType(materialType);
            } catch (Exception e) {
                excel.add(map);
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append(e.getMessage() + "。"));
                continue;
            }
            count++;
            long pk = materialType.getId();
            //更新旧表中new_pk字段
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "BookTypesID", bookTypesID);
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "教材类型表", "material_type");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", tableName, excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + " 表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    protected void material() {
        String tableName = "teach_material";
        String sql = "select "
                + "a.materid, "
                + "a.matername, "
                + "a.teachround round, "
                + "i.new_pk  booktypesid, "
                + "a.showenddate, "
                + "a.enddate, "
                + "a.agedeaddate, "
                + "a.mailaddress, "
                + "CASE WHEN a.flowtype=10 THEN '0' ELSE '1' END is_published,"
                + "CASE WHEN a.flowtype=12 AND k.materid IS NOT NULL THEN '1' ELSE '0' "
                + "END is_all_textbook_published, "
                + "j.new_pk DepartmentId , "
                + "d.newuserid director, "
                + "a.isbookmulti, "
                + "a.ispositionmulti, "
                + "a.isuselearnexp, "
                + "a.isfilllearnexp, "
                + "a.isuseworkexp, "
                + "a.isfillworkexp, "
                + "a.isuseteachexp, "
                + "a.isfillteachexp, "
                + "a.isuseacadeexp, "
                + "a.isfillacadeexp, "
                + "a.isusematerpartexp, "
                + "a.isfillmaterpartexp, "
                + "a.isusecountry, "
                + "a.isfillcountry, "
                + "a.isuseprovexce, "
                + "a.isfillprovexce, "
                + "a.isuseschoolconstr, "
                + "a.isfillschoolconstr, "
                + "a.isuseeditormater, "
                + "a.isfilleditormater, "
                + "a.isusematerwrite, "
                + "a.isfillmaterwrite, "
                + "a.isuseothermaterwrite, "
                + "a.isfillothermaterwrite, "
                + "a.isusescientresearch, "
                + "a.isfillscientresearch, "
                + "if(a.isdelete =1 ,true,false) isdelete, "
                + "a.createdate, "
                + "g.new_pk founder_id, "
                + "a.updatedate, "
                + "ifnull(h.new_pk,g.new_pk) mender_id  "
                +//如果更新者为空那么默认创建者
                "from teach_material a  "
                + "LEFT JOIN ( "
                + "select DISTINCT e.materid,d.rolename,a.userid,a.new_pk newuserid,f.new_pk neworgid from sys_user  a "
                + "LEFT JOIN  sys_userorganize b on b.userid=a.userid "
                + "LEFT JOIN  sys_userrole   c on   c.userid =a.userid  "
                + "LEFT JOIN  sys_role   d  on d.roleid =c.roleid "
                + "LEFT JOIN teach_material e on e.createorgid = b.orgid  "
                + "LEFT JOIN ba_organize f on f.orgid = b.orgid "
                + "where d.rolename like '%主任%' and e.materid is not null  GROUP BY e.materid "
                + ") d on d.materid = a.materid "
                + "LEFT JOIN sys_user g on g.userid = a.createuserid "
                + "LEFT JOIN sys_user h on h.userid = a.updateuserid  "
                + "LEFT JOIN sys_booktypes i on i.BookTypesID = a. booktypesid  "
                + "LEFT JOIN ba_organize j on a.createorgid = j.orgid "
                + "LEFT JOIN site_article k ON a.materid = k.materid "
                + "WHERE true GROUP BY a.materid ;";
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> oldMaterial : maps) {
            StringBuilder exception = new StringBuilder();
            String materialId = String.valueOf(oldMaterial.get("materid"));
            String matername = (String) oldMaterial.get("matername");
            if (StringUtil.isEmpty(matername)) {
                oldMaterial.put(SQLParameters.EXCEL_EX_HEADER, exception.append("教材名称为空。"));
                excel.add(oldMaterial);
                continue;
            }
            String mailaddress = (String) oldMaterial.get("mailaddress");
            if (StringUtil.isEmpty(mailaddress)) {
                oldMaterial.put(SQLParameters.EXCEL_EX_HEADER, exception.append("邮寄地址为空。"));
                excel.add(oldMaterial);
                continue;
            }
            Long DepartmentId = (Long) oldMaterial.get("DepartmentId");
            if (ObjectUtil.isNull(DepartmentId)) {
                oldMaterial.put(SQLParameters.EXCEL_EX_HEADER, exception.append("创建部门为空。"));
                excel.add(oldMaterial);
                continue;
            }
            Long director = (Long) oldMaterial.get("director");
            if (ObjectUtil.isNull(director)) {
                oldMaterial.put(SQLParameters.EXCEL_EX_HEADER, exception.append("主任为空。"));
                excel.add(oldMaterial);
                continue;
            }
            Long founder_id = (Long) oldMaterial.get("founder_id");
            if (ObjectUtil.isNull(founder_id)) {
                oldMaterial.put(SQLParameters.EXCEL_EX_HEADER, exception.append("创建人为空。"));
                excel.add(oldMaterial);
                continue;
            }
            Integer round = (Integer) oldMaterial.get("round");
            if (ObjectUtil.isNull(round)) {
                oldMaterial.put(SQLParameters.EXCEL_EX_HEADER, exception.append("轮次为空,设默认值1。"));
                excel.add(oldMaterial);
                round = 1;
            }
            Long booktypesid = (Long) oldMaterial.get("booktypesid");
            if (ObjectUtil.isNull(booktypesid)) {
                oldMaterial.put(SQLParameters.EXCEL_EX_HEADER, exception.append("架构为空，设为默认0。"));
                excel.add(oldMaterial);
                booktypesid = 0L;
            }
            Long mender_id = (Long) oldMaterial.get("mender_id");
            if (ObjectUtil.isNull(mender_id)) {
                oldMaterial.put(SQLParameters.EXCEL_EX_HEADER, exception.append("修改人id为空,设置默认为创建者。"));
                excel.add(oldMaterial);
                mender_id = founder_id;
            }
            Material material = new Material();
            material.setMaterialName(matername);
            material.setMaterialRound(round);
            material.setMaterialType(booktypesid);
            material.setDeadline((Timestamp) oldMaterial.get("showenddate"));
            material.setActualDeadline((Timestamp) oldMaterial.get("enddate"));
            material.setAgeDeadline((Timestamp) oldMaterial.get("agedeaddate"));
            material.setMailAddress(mailaddress);
            material.setDepartmentId(DepartmentId);
            material.setDirector(director);//director,
            material.setIsMultiBooks("1".equals(String.valueOf(oldMaterial.get("isbookmulti")))); //is_multi_books,
            material.setIsMultiPosition("1".equals(String.valueOf(oldMaterial.get("ispositionmulti"))));//is_multi_position,
            material.setIsEduExpUsed("1".equals(String.valueOf(oldMaterial.get("isuselearnexp"))));//is_edu_exp_used,
            material.setIsEduExpRequired("1".equals(String.valueOf(oldMaterial.get("isfilllearnexp"))));//is_edu_exp_required,
            material.setIsWorkExpUsed("1".equals(String.valueOf(oldMaterial.get("isuseworkexp"))));//is_work_exp_used,
            material.setIsWorkExpRequired("1".equals(String.valueOf(oldMaterial.get("isfillworkexp"))));//is_work_exp_required,
            material.setIsTeachExpUsed("1".equals(String.valueOf(oldMaterial.get("isuseteachexp"))));//is_teach_exp_used,
            material.setIsTeachExpRequired("1".equals(String.valueOf(oldMaterial.get("isfillteachexp"))));//is_teach_exp_required,
            material.setIsAcadeUsed("1".equals(String.valueOf(oldMaterial.get("isuseacadeexp"))));//is_acade_used,
            material.setIsAcadeRequired("1".equals(String.valueOf(oldMaterial.get("isfillacadeexp"))));//is_acade_required,
            material.setIsLastPositionUsed("1".equals(String.valueOf(oldMaterial.get("isusematerpartexp"))));//is_last_position_used,
            material.setIsLastPositionRequired("1".equals(String.valueOf(oldMaterial.get("isfillmaterpartexp"))));//is_last_position_required,
            material.setIsNationalCourseUsed("1".equals(String.valueOf(oldMaterial.get("isusecountry"))));//is_national_course_used,
            material.setIsNationalCourseRequired("1".equals(String.valueOf(oldMaterial.get("isfillcountry"))));//is_national_course_required,
            material.setIsProvincialCourseUsed("1".equals(String.valueOf(oldMaterial.get("isuseprovexce"))));//is_provincial_course_used,
            material.setIsProvincialCourseRequired("1".equals(String.valueOf(oldMaterial.get("isfillprovexce"))));//is_provincial_course_required,
            material.setIsSchoolCourseUsed("1".equals(String.valueOf(oldMaterial.get("isuseschoolconstr"))));//is_school_course_used,
            material.setIsSchoolCourseRequired("1".equals(String.valueOf(oldMaterial.get("isfillschoolconstr"))));//is_school_course_required,
            material.setIsNationalPlanUsed("1".equals(String.valueOf(oldMaterial.get("isuseeditormater"))));//is_national_plan_used,
            material.setIsNationalPlanRequired("1".equals(String.valueOf(oldMaterial.get("isfilleditormater"))));//is_national_plan_required,
            material.setIsTextbookUsed("1".equals(String.valueOf(oldMaterial.get("isusematerwrite"))));//is_textbook_writer_used,
            material.setIsTextbookRequired("1".equals(String.valueOf(oldMaterial.get("isfillmaterwrite"))));//is_textbook_writer_required,
            material.setIsOtherTextbookUsed("1".equals(String.valueOf(oldMaterial.get("isuseothermaterwrite"))));//is_other_textbook_used,
            material.setIsOtherTextbookRequired("1".equals(String.valueOf(oldMaterial.get("isfillothermaterwrite"))));//is_other_textbook_required,
            material.setIsResearchUsed("1".equals(String.valueOf(oldMaterial.get("isusescientresearch"))));//is_research_used,
            material.setIsResearchRequired("1".equals(String.valueOf(oldMaterial.get("isfillscientresearch"))));//is_research_required,
            material.setIsPublished("1".equals(String.valueOf(oldMaterial.get("is_published"))));//is_published,
            material.setIsPublic(false);//is_public
            material.setIsAllTextbookPublished("1".equals(String.valueOf(oldMaterial.get("is_all_textbook_published"))));//is_all_textbook_published
            material.setIsForceEnd(false);//is_force_end
            material.setIsDeleted("1".equals(String.valueOf(oldMaterial.get("isdelete"))));//is_deleted,
            material.setGmtCreate((Timestamp) oldMaterial.get("createdate"));//gmt_create,			
            material.setFounderId(founder_id);//founder_id,
            material.setGmtUpdate((Timestamp) oldMaterial.get("updatedate"));//gmt_update,			
            material.setMenderId((Long) oldMaterial.get("mender_id"));//mender_id
            try {
                material = materialService.addMaterial(material);
                count++;
            } catch (Exception e) {
                oldMaterial.put(SQLParameters.EXCEL_EX_HEADER, exception.append(e.getMessage()) + "。");
                excel.add(oldMaterial);
                logger.error(e.getMessage());
                continue;
            }
            long pk = material.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "materid", materialId);//更新旧表中new_pk字段
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "教材信息表", "material");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", tableName, excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + " 表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    protected void materialExtension() {
        String tableName = "teach_material_extend";
        String sql = "select "
                + "a.expendid, "
                + "b.new_pk materid, "
                + "a.expendname, "
                + "a.isfill from  "
                + "teach_material_extend a  "
                + "LEFT JOIN teach_material b on b.materid=a.materid  "
                + "where b.materid is not null ";
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        List<Map<String, Object>> maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> materialExtension : maps) {
            String oldExpendid = (String) materialExtension.get("expendid");
            StringBuilder exception = new StringBuilder();
            Long materid = (Long) materialExtension.get("materid");
            if (ObjectUtil.isNull(materid)) {
                materialExtension.put(SQLParameters.EXCEL_EX_HEADER, exception.append("教材id为空。"));
                excel.add(materialExtension);
                continue;
            }
            String expendname = (String) materialExtension.get("expendname");
            if (StringUtil.isEmpty(expendname)) {
                materialExtension.put(SQLParameters.EXCEL_EX_HEADER, exception.append("扩展名称为空。"));
                excel.add(materialExtension);
                continue;
            }
            MaterialExtension newMaterialExtension = new MaterialExtension();
            newMaterialExtension.setMaterialId(materid);
            newMaterialExtension.setExtensionName(expendname);
            newMaterialExtension.setIsRequired("1".equals(String.valueOf(materialExtension.get("isfill"))));
            try {
                newMaterialExtension = materialExtensionService.addMaterialExtension(newMaterialExtension);
                count++;
            } catch (Exception e) {
                materialExtension.put(SQLParameters.EXCEL_EX_HEADER, exception.append(e.getMessage()) + "。");
                excel.add(materialExtension);
                logger.error(e.getMessage());
            }
            long pk = newMaterialExtension.getId();
            JdbcHelper.updateNewPrimaryKey(tableName, pk, "expendid", oldExpendid);//更新旧表中new_pk字段
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "教材信息扩展项表", "material_extension");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", tableName, excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + " 表迁移完成" + count + "/" + maps.size());
        SQLParameters.STATISTICS.add(msg);
    }

    protected void materialExtra() {
        String sql = "select "
                + "new_pk, "
                + "introduction, "
                + "remark "
                + "from teach_material  ";
        //获取到所有数据表
        List<Map<String, Object>> materialExtraList = JdbcHelper.getJdbcTemplate().queryForList(sql);
        int count = 0;
        List<Map<String, Object>> excel = new LinkedList<>();
        for (Map<String, Object> object : materialExtraList) {
            StringBuilder exception = new StringBuilder();
            Long materid = (Long) object.get("new_pk");
            if (ObjectUtil.isNull(materid)) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append("教材id为空。"));
                excel.add(object);
                continue;
            }
            String notice = (String) object.get("introduction");
            if (StringUtil.isEmpty(notice)) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append("通知内容为空。"));
                excel.add(object);
                continue;
            }
            String note = (String) object.get("remark");
            if (StringUtil.isEmpty(note)) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append("备注。"));
                excel.add(object);
                continue;
            }
            MaterialExtra materialExtra = new MaterialExtra();
            materialExtra.setMaterialId(materid);
            materialExtra.setNotice(notice);
            materialExtra.setNote(note);
            try {
                materialExtra = materialExtraService.addMaterialExtra(materialExtra);
                count++;
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            mps.put(materialExtra.getMaterialId(), materialExtra.getId());
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "教材通知备注表", "material_extra");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", "material_extra", count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "material_extra 表迁移完成" + count + "/" + materialExtraList.size());
        SQLParameters.STATISTICS.add(msg);
    }

    protected void transferMaterialNoticeAttachment() {
        String sql = "select "
                + "a.new_pk, "
                + "b.filedir, "
                + "b.filename, "
                + "1 "
                + "from teach_material a "
                + "LEFT JOIN pub_addfileinfo b on b.tablekeyid = a.materid "
                + "where   b.childsystemname='notice_introduction' and tablename='TEACH_MATERIAL_INTRODUCTION' ";
        List<Map<String, Object>> materialNoticeAttachmentList = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> map : materialNoticeAttachmentList) {
            StringBuilder exception = new StringBuilder();
            //文件名
            String fileName = (String) map.get("filename");
            if (StringUtil.isEmpty(fileName)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("文件名称为空。"));
                excel.add(map);
                continue;
            }
            Long newMaterid = (Long) map.get("new_pk");
            if (ObjectUtil.isNull(newMaterid)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("教材id为空。"));
                excel.add(map);
                continue;
            }
            Long materialExtraId = mps.get(newMaterid);
            if (ObjectUtil.isNull(materialExtraId)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("教材通知备注id。"));
                excel.add(map);
                continue;
            }
            MaterialNoticeAttachment materialNoticeAttachment = new MaterialNoticeAttachment();
            materialNoticeAttachment.setMaterialExtraId(materialExtraId);
            //先用一个临时的"-"占位，不然会报错;
            materialNoticeAttachment.setAttachment("-");
            materialNoticeAttachment.setAttachmentName(fileName);
            materialNoticeAttachment.setDownload(1L);
            try {
                materialNoticeAttachment = materialNoticeAttachmentService.addMaterialNoticeAttachment(materialNoticeAttachment);
            } catch (Exception e) {
                continue;
            }
            if (ObjectUtil.notNull(materialNoticeAttachment.getId())) {
                String mongoId;
                try {
                    mongoId = fileService.migrateFile((String) map.get("filedir"), FileType.MATERIAL_NOTICE_ATTACHMENT, materialNoticeAttachment.getId());
                } catch (IOException ex) {
                    logger.error("文件读取异常，路径<{}>，异常信息：{}", (String) map.get("filedir"), ex.getMessage());
                    map.put(SQLParameters.EXCEL_EX_HEADER, "文件读取异常。");
                    excel.add(map);
                    continue;
                } catch (Exception e) {
                    map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("未知异常：" + e.getMessage() + "。"));
                    excel.add(map);
                    continue;
                }
                if (StringUtil.notEmpty(mongoId)) {
                    materialNoticeAttachment.setAttachment(mongoId);
                    materialNoticeAttachmentService.updateMaterialNoticeAttachment(materialNoticeAttachment);
                    count++;
                } else {
                    map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("文件保存失败或者文件不存在。"));
                    excel.add(map);
                    continue;
                }
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "教材通知附件表", "material_notice_attachment");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("教材通知附件表迁移了{}条数据", count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "教材通知附件  表迁移完成" + count + "/" + materialNoticeAttachmentList.size());
        SQLParameters.STATISTICS.add(msg);
    }

    protected void transferMaterialNoteAttachment() {
        String sql = "select "
                + "a.new_pk materid, "
                + "b.filedir, "
                + "b.filename "
                + "from teach_material a  "
                + "LEFT JOIN pub_addfileinfo b on b.tablekeyid = a.materid "
                + "where b.childsystemname='notice' and tablename='TEACH_MATERIAL' ";
        List<Map<String, Object>> materialMaterialNoteAttachmentList = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> map : materialMaterialNoteAttachmentList) {
            StringBuilder exception = new StringBuilder();
            //文件名
            String fileName = (String) map.get("filename");
            if (StringUtil.isEmpty(fileName)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("文件名称为空。"));
                excel.add(map);
                continue;
            }
            Long newMaterid = (Long) map.get("materid");
            if (ObjectUtil.isNull(newMaterid)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("教材id为空。"));
                excel.add(map);
                continue;
            }
            Long materialExtraId = mps.get(newMaterid);
            if (ObjectUtil.isNull(materialExtraId)) {
                map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("教材通知备注id。"));
                excel.add(map);
                continue;
            }
            MaterialNoteAttachment materialNoteAttachment = new MaterialNoteAttachment();
            materialNoteAttachment.setMaterialExtraId(materialExtraId);
            //先用一个临时的"-"占位，不然会报错;
            materialNoteAttachment.setAttachment("-");
            materialNoteAttachment.setAttachmentName(fileName);
            materialNoteAttachment.setDownload(1L);
            try {
                materialNoteAttachment = materialNoteAttachmentService.addMaterialNoteAttachment(materialNoteAttachment);
                count++;
            } catch (Exception e) {
                continue;
            }
            if (ObjectUtil.notNull(materialNoteAttachment.getId())) {
                String mongoId;
                try {
                    mongoId = fileService.migrateFile((String) map.get("filedir"), FileType.MATERIAL_NOTE_ATTACHMENT, materialNoteAttachment.getId());
                } catch (IOException ex) {
                    logger.error("文件读取异常，路径<{}>，异常信息：{}", (String) map.get("filedir"), ex.getMessage());
                    map.put(SQLParameters.EXCEL_EX_HEADER, "文件读取异常。");
                    excel.add(map);
                    continue;
                } catch (Exception e) {
                    map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("未知异常：" + e.getMessage() + "。"));
                    excel.add(map);
                    continue;
                }
                if (StringUtil.notEmpty(mongoId)) {
                    materialNoteAttachment.setAttachment(mongoId);
                    materialNoteAttachmentService.updateMaterialNoteAttachment(materialNoteAttachment);
                } else {
                    map.put(SQLParameters.EXCEL_EX_HEADER, exception.append("文件保存失败或者文件不存在。"));
                    excel.add(map);
                    continue;
                }
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "教材备注附件表", "material_note_attachment");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("教材通知附件表迁移了{}条数据", count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "教材备注附件表  表迁移完成" + count + "/" + materialMaterialNoteAttachmentList.size());
        SQLParameters.STATISTICS.add(msg);
    }

    protected void transferMaterialContact() {
        String sql = "select "
                + "a.linkerid linkerid, "
                + "b.new_pk   materid, "
                + "c.new_pk   userid, "
                + "c.username username, "
                + "a.linkphone linkphone, "
                + "a.email    email, "
                + "a.orderno  orderno "
                + "from teach_material_linker  a  "
                + "LEFT JOIN teach_material  b on b.materid = a.materid "
                + "LEFT JOIN sys_user  c on c.userid = a.userid "
                + "where 1=1 ";
        //获取到所有数据表
        String tableName = "teach_material_linker";
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        List<Map<String, Object>> materialContactList = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> object : materialContactList) {
            String linkId = (String) object.get("linkerid");
            StringBuilder exception = new StringBuilder();
            Long materid = (Long) object.get("materid");
            if (ObjectUtil.isNull(materid)) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append("教材id为空。"));
                excel.add(object);
                continue;
            }
            Long userid = (Long) object.get("userid");
            if (ObjectUtil.isNull(userid)) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append("联系人id为空。"));
                excel.add(object);
                continue;
            }
            String username = (String) object.get("username");
            if (StringUtil.isEmpty(username)) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append("联系人姓名为空。"));
                excel.add(object);
                continue;
            }
            String linkphone = (String) object.get("linkphone");
            if (StringUtil.isEmpty(linkphone)) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append("联系人电话为空。"));
                excel.add(object);
                continue;
            }
            String email = (String) object.get("email");
            if (StringUtil.isEmpty(email)) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append("联系人邮箱为空。"));
                excel.add(object);
                continue;
            }
            Integer orderno = null;
            orderno = (Integer) object.get("orderno");
            if (ObjectUtil.notNull(orderno) && orderno < 0) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append("联系人排序不符合规范,默认999。"));
                orderno = 999;
                excel.add(object);
            }
            MaterialContact materialContact = new MaterialContact();
            materialContact.setMaterialId(materid);
            materialContact.setContactUserId(userid);
            materialContact.setContactUserName(username);
            materialContact.setContactPhone(linkphone);
            materialContact.setContactEmail(email);
            materialContact.setSort(orderno);
            try {
                materialContact = materialContactService.addMaterialContact(materialContact);
                count++;
            } catch (Exception e) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append(e.getMessage()) + "。");
                excel.add(object);
                logger.error(e.getMessage());
            }
            long pk = materialContact.getId();
            if (ObjectUtil.notNull(pk)) {
                JdbcHelper.updateNewPrimaryKey(tableName, pk, "linkerid", linkId);//更新旧表中new_pk字段
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "教材联系人表（一对多）", "material_contact");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", tableName, excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", materialContactList.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + materialContactList.size());
        SQLParameters.STATISTICS.add(msg);
    }

    protected void materialOrg() {
        String sql = "select  "
                + "a.pushschoolid , "
                + "b.new_pk materid, "
                + "c.new_pk orgid "
                + "from teach_pushschool  a  "
                + "LEFT JOIN teach_material  b on b.materid = a.materid "
                + "LEFT JOIN ba_organize c on c.orgid = a.orgid "
                + "where 1=1 ";
        //获取到所有数据表
        String tableName = "teach_pushschool";
        JdbcHelper.addColumn(tableName); //增加new_pk字段
        List<Map<String, Object>> pubList = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> object : pubList) {
            String pushschoolId = (String) object.get("pushschoolid");
            StringBuilder exception = new StringBuilder();
            Long materid = (Long) object.get("materid");
            if (ObjectUtil.isNull(materid)) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append("教材id为空。"));
                excel.add(object);
                continue;
            }
            Long orgid = (Long) object.get("orgid");
            if (ObjectUtil.isNull(orgid)) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append("结构id为空。"));
                excel.add(object);
                continue;
            }
            MaterialOrg materialOrg = new MaterialOrg();
            materialOrg.setMaterialId(materid);
            materialOrg.setOrgId(orgid);
            try {
                materialOrg = materialOrgService.addMaterialOrg(materialOrg);
                count++;
            } catch (Exception e) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append(e.getMessage() + "。"));
                excel.add(object);
                logger.error(e.getMessage());
            }
            long pk = materialOrg.getId();
            if (ObjectUtil.notNull(pk)) {
                JdbcHelper.updateNewPrimaryKey(tableName, pk, "pushschoolid", pushschoolId);//更新旧表中new_pk字段
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "教材-机构关联表", "material_org");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", tableName, excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", pubList.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "" + tableName + "  表迁移完成" + count + "/" + pubList.size());
        SQLParameters.STATISTICS.add(msg);
    }

    protected void materialPprojectEeditor() {
        String sql = "select DISTINCT * from( "
                + "select "
                + "b.new_pk   materid, "
                + "c.new_pk   userid  "
                + "from teach_material_linker  a  "
                + "LEFT JOIN teach_material  b on b.materid = a.materid "
                + "LEFT JOIN sys_user  c on c.userid = a.userid "
                + "where true  "
                + "UNION select DISTINCT e.new_pk materid ,a.new_pk userid from sys_user  a  "
                + "LEFT JOIN  sys_userorganize b on b.userid=a.userid  "
                + "LEFT JOIN  sys_userrole   c on   c.userid =a.userid   "
                + "lEFT JOIN  sys_role   d  on d.roleid =c.roleid    "
                + "LEFT JOIN teach_material e on e.createorgid = b.orgid   "
                + "where d.rolename like '%主任%' and e.materid is not null   GROUP BY e.materid "
                + ")temp ";
        List<Map<String, Object>> materialProjectEditorList = JdbcHelper.getJdbcTemplate().queryForList(sql);
        List<Map<String, Object>> excel = new LinkedList<>();
        int count = 0;
        for (Map<String, Object> object : materialProjectEditorList) {
            StringBuilder exception = new StringBuilder();
            Long materid = (Long) object.get("materid");
            if (ObjectUtil.isNull(materid)) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append("教材id为空。"));
                excel.add(object);
                continue;
            }
            Long userid = (Long) object.get("userid");
            if (ObjectUtil.isNull(userid)) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append("用户id为空。"));
                excel.add(object);
                continue;
            }
            MaterialProjectEditor materialProjectEditor = new MaterialProjectEditor();
            materialProjectEditor.setMaterialId(materid);
            materialProjectEditor.setEditorId(userid);
            try {
                materialProjectEditor = materialProjectEditorService.addMaterialProjectEditor(materialProjectEditor);
                count++;
            } catch (Exception e) {
                object.put(SQLParameters.EXCEL_EX_HEADER, exception.append(e.getMessage() + "。"));
                excel.add(object);
                logger.error(e.getMessage());
            }
        }
        if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, "教材-项目编辑关联表（多对多）", "MaterialProjectEditor");
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", "MaterialProjectEditor", excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", materialProjectEditorList.size(), count);
        //记录信息
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("result", "MaterialProjectEditor  表迁移完成" + count + "/" + materialProjectEditorList.size());
        SQLParameters.STATISTICS.add(msg);
    }

}
