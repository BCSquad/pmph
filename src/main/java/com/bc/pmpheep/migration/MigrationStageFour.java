package com.bc.pmpheep.migration;

import java.io.IOException;
import java.sql.Timestamp;
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
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.bc.pmpheep.migration.common.SQLParameters;
import com.bc.pmpheep.utils.ExcelHelper;

/**
 *@author MrYang 
 *@CreateDate 2017年10月23日 下午2:18:10
 **/
@Component
public class MigrationStageFour {
	
	Logger  logger = LoggerFactory.getLogger(MigrationStageFour.class);
	
	@Resource
    private ExcelHelper excelHelper;
	
	@Autowired
	private MaterialTypeService materialTypeService;
	public void materialType(){
		String tableName="sys_booktypes";
		JdbcHelper.addColumn(tableName); //增加new_pk字段
		List<Map<String, Object>> maps = JdbcHelper.queryForList(tableName);//取得该表中所有数据
		List<Map<String, Object>> excel = new LinkedList<>();
		int count =0; 
		Integer maxLevel=0;
		//插入除parentid和path的字段；
		for (Map<String, Object> map : maps) {
			Integer tempLevel=Integer.parseInt(String.valueOf(map.get("level")));
			maxLevel = tempLevel>maxLevel?tempLevel:maxLevel;
			MaterialType materialType=null;
			try {
				materialType = new MaterialType(0L,"0",(String)map.get("TypeName"), (Integer) map.get("Sortno"),(String)map.get("Remark"));
				materialType=materialTypeService.addMaterialType(materialType);
				count++;
			} catch (Exception e) {
				excel.add(map);
				logger.error( e.getMessage());
			}
			if(null != materialType.getId()){
				JdbcHelper.updateNewPrimaryKey(tableName, materialType.getId(), "BookTypesID", map.get("BookTypesID"));//更新旧表中new_pk字段
			}
		}
		//插入parentId和path
//		String sql="select a1.NEW_BOOKTYPESID id,"+
//					"ifnull(a2.NEW_BOOKTYPESID,0) parentid,"+
//					"CONCAT('0-',ifnull(a5.NEW_BOOKTYPESID,0),'-',ifnull(a4.NEW_BOOKTYPESID,0),'-',ifnull(a3.NEW_BOOKTYPESID,0),'-',ifnull(a2.NEW_BOOKTYPESID,0)) "+
//					"from sys_booktypes  a1 "+
//					"LEFT JOIN sys_booktypes  a2 on a2.BookTypesID=a1.ParentTypesID "+
//					"LEFT JOIN sys_booktypes  a3 on a3.BookTypesID=a2.ParentTypesID "+
//					"LEFT JOIN sys_booktypes  a4 on a4.BookTypesID=a3.ParentTypesID "+
//					"LEFT JOIN sys_booktypes  a5 on a5.BookTypesID=a4.ParentTypesID ";
		String sql="select  a1.new_pk id,"+
					"ifnull(a2.new_pk,0) parentid,"+
				    "CONCAT('0'";
		for(int i=maxLevel;true;i--){
			 sql += ",'-',ifnull(a"+i+".new_pk,0)";
			 if(i==2){
				 sql +=") path from sys_booktypes  a1 ";
				 break;
			 }
		}
		for(int i=2;i<=maxLevel;i++){
			 sql += "LEFT JOIN sys_booktypes  a"+i+" on a"+i+".BookTypesID=a"+(i-1)+".ParentTypesID ";
		}
		maps = JdbcHelper.getJdbcTemplate().queryForList(sql);
		for (Map<String, Object> map : maps) {
			String path  =(String) map.get("path");
			Long id      =(Long)map.get("id");
			Long parentId=(Long)map.get("parentid");
			String temp="0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0";
			for(int i=maxLevel;2*i-1>0;i--){
				path = path.replace(temp.substring(0, 2*i-1), "0");
			}
			MaterialType materialType=new MaterialType();
			materialType.setId(id);
			materialType.setParentId(parentId);
			materialType.setPath(path);
			materialTypeService.updateMaterialType(materialType);
		}
		if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, tableName, tableName);
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", tableName, excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", maps.size(), count);
	}
	
	
	
	@Autowired
	private MaterialService materialService;
	
	public  void material() throws Exception{
		
		
		
		String sql="select "+
					"a.materid, "+
					"a.matername, "+
					"if(a.teachround  is null or a.teachround  ='',1,a.teachround) round, "+
					"if(i.new_pk is   null or    i.new_pk ='',0,i.new_pk) booktypesid, "+
					"a.showenddate, "+
					"a.enddate, "+
					"a.agedeaddate, "+
					"a.mailaddress, "+
					"a.flowtype, "+
					"ifnull(d.neworgid,0) DepartmentId , "+
					"d.newuserid director, "+
					"a.isbookmulti, "+
					"a.ispositionmulti, "+
					"a.isuselearnexp, "+
					"a.isfilllearnexp, "+
					"a.isuseworkexp, "+
					"a.isfillworkexp, "+
					"a.isuseteachexp, "+
					"a.isfillteachexp, "+
					"a.isuseacadeexp, "+
					"a.isfillacadeexp, "+
					"a.isusematerpartexp, "+
					"a.isfillmaterpartexp, "+
					"a.isusecountry, "+
					"a.isfillcountry, "+
					"a.isuseprovexce, "+
					"a.isfillprovexce, "+
					"a.isuseschoolconstr, "+
					"a.isfillschoolconstr, "+
					"a.isuseeditormater, "+
					"a.isfilleditormater, "+
					"a.isusematerwrite, "+
					"a.isfillmaterwrite, "+
					"a.isuseothermaterwrite, "+
					"a.isfillothermaterwrite, "+
					"a.isusescientresearch, "+
					"a.isfillscientresearch, "+
					"if(a.ispublishfront =1,true,false) ispublishfront, "+
					"if(a.isdelete =1 ,true,false) isdelete, "+
					"a.createdate, "+
					"g.new_pk founder_id, "+
					"a.updatedate, "+
					"ifnull(h.new_pk,g.new_pk) mender_id  "+//如果更新者为空那么默认创建者
					"from teach_material a  "+
					"LEFT JOIN ( "+
					 	"select DISTINCT e.materid,d.rolename,a.userid,a.new_pk newuserid,b.new_pk neworgid from sys_user  a "+
						"LEFT JOIN  sys_userorganize b on b.userid=a.userid "+
						"LEFT JOIN  sys_userrole   c on   c.userid =a.userid  "+
						"LEFT JOIN  sys_role   d  on d.roleid =c.roleid "+
						"LEFT JOIN teach_material e on e.createorgid = b.orgid  "+
						"where d.rolename like '%主任%' and e.materid is not null  GROUP BY e.materid "+
					") d on d.materid = a.materid "+
					"LEFT JOIN sys_user g on g.userid = a.createuserid "+
					"LEFT JOIN sys_user h on h.userid = a.updateuserid  "+
					"LEFT JOIN sys_booktypes i on i.BookTypesID = a. booktypesid  "+
					"WHERE true GROUP BY a.materid  ";
		//获取到所有数据表
		String tableName ="teach_material";
		JdbcHelper.addColumn(tableName); //增加new_pk字段
		List<Map<String, Object>> materialList=JdbcHelper.getJdbcTemplate().queryForList(sql);
		List<Map<String, Object>> excel = new LinkedList<>();
		int count =0;
		for(Map<String, Object> oldMaterial:materialList){
			Material material =new Material();
			material.setMaterialName((String)oldMaterial.get("matername"));
			material.setMaterialRound((Integer.parseInt(String.valueOf(oldMaterial.get("round")))));
			material.setMaterialType((Long) oldMaterial.get("booktypesid"));
			material.setDeadline((Timestamp)oldMaterial.get("showenddate"));
			material.setActualDeadline((Timestamp)oldMaterial.get("enddate"));
			material.setAgeDeadline((Timestamp)oldMaterial.get("agedeaddate"));
			material.setMailAddress((String)oldMaterial.get("mailaddress"));
			material.setProgress(new Short((String)oldMaterial.get("flowtype")));
			material.setDepartmentId((Long)oldMaterial.get("DepartmentId"));
			material.setDirector((Long) oldMaterial.get("director"));//director,
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
			material.setIsTextbookWriterUsed("1".equals(String.valueOf(oldMaterial.get("isusematerwrite"))));//is_textbook_writer_used,
			material.setIsTextbookWriterRequired("1".equals(String.valueOf(oldMaterial.get("isfillmaterwrite"))));//is_textbook_writer_required,
			material.setIsOtherTextbookUsed("1".equals(String.valueOf(oldMaterial.get("isuseothermaterwrite"))));//is_other_textbook_used,
			material.setIsOtherTextbookRequired("1".equals(String.valueOf(oldMaterial.get("isfillothermaterwrite"))));//is_other_textbook_required,
			material.setIsResearchUsed("1".equals(String.valueOf(oldMaterial.get("isusescientresearch"))));//is_research_used,
			material.setIsResearchRequired("1".equals(String.valueOf(oldMaterial.get("isfillscientresearch"))));//is_research_required,
			material.setIsPublished("1".equals(String.valueOf(oldMaterial.get("ispublishfront"))));//is_published,
			material.setIsDeleted("1".equals(String.valueOf(oldMaterial.get("isdelete"))));//is_deleted,
			material.setGmtCreate((Timestamp)oldMaterial.get("createdate"));//gmt_create,
			material.setFounderId((Long)oldMaterial.get("founder_id"));//founder_id,
			material.setGmtUpdate((Timestamp)oldMaterial.get("updatedate"));//gmt_update,
			material.setMenderId((Long)oldMaterial.get("mender_id"));//mender_id
			try {
				material=materialService.addMaterial(material);
				count++;
			} catch (Exception e) {
				excel.add(oldMaterial);
				logger.error( e.getMessage());
			}
			if(null != material.getId()){
				JdbcHelper.updateNewPrimaryKey(tableName, material.getId(), "materid",oldMaterial.get("materid"));//更新旧表中new_pk字段
			}
		}
		if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, tableName, tableName);
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", tableName, excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", materialList.size(), count);
	} 
	
	@Autowired
	private MaterialExtensionService materialExtensionService;
	
	public void materialExtension() throws Exception{
		String sql = "select "+
						"a.expendid, "+
						"b.new_pk materid, "+
						"a.expendname, "+
						"a.isfill from  "+
						"teach_material_extend a  "+
						"LEFT JOIN teach_material b on b.materid=a.materid  "+
						"where b.materid is not null ";
		//获取到所有数据表
		String tableName ="teach_material_extend";
		JdbcHelper.addColumn(tableName); //增加new_pk字段
		List<Map<String, Object>> materialExtensionList=JdbcHelper.getJdbcTemplate().queryForList(sql);
		List<Map<String, Object>> excel = new LinkedList<>();
		int count =0;
		for(Map<String, Object> materialExtension:materialExtensionList){
			String oldExpendid = (String)materialExtension.get("expendid");
			MaterialExtension newMaterialExtension=new MaterialExtension();
			newMaterialExtension.setMaterialId((Long)materialExtension.get("materid"));
			newMaterialExtension.setExtensionName((String)materialExtension.get("expendname"));
			newMaterialExtension.setIsRequired("1".equals(String.valueOf(materialExtension.get("isfill"))));
			try {
				newMaterialExtension=materialExtensionService.addMaterialExtension(newMaterialExtension);
				count++;
			} catch (Exception e) {
				excel.add(materialExtension);
				logger.error( e.getMessage());
			}
			if(null != newMaterialExtension.getId()){
				JdbcHelper.updateNewPrimaryKey(tableName, newMaterialExtension.getId(), "expendid",oldExpendid);//更新旧表中new_pk字段
			}
		}
		if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, tableName, tableName);
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", tableName, excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", materialExtensionList.size(), count);
	}
	
	@Autowired
	private MaterialExtraService materialExtraService;
	
	//用来储存MaterialExtra返回的主键，供后面使用 Map<教材id,教材通知备注表id> 
	public static Map<Long,Long> mps = new HashMap<Long,Long>(12);
	
	public void materialExtra() throws Exception{
		String sql = "select "+
						"new_pk, "+
						"introduction, "+
						"remark "+
						"from teach_material  ";
		//获取到所有数据表
		List<Map<String, Object>> materialExtraList=JdbcHelper.getJdbcTemplate().queryForList(sql);
		int count =0;
		for(Map<String, Object> object:materialExtraList){
			MaterialExtra materialExtra = new MaterialExtra();
			materialExtra.setMaterialId((Long)object.get("new_pk"));
			materialExtra.setNotice((String)object.get("introduction"));
			materialExtra.setNote((String)object.get("remark"));
			try {
				materialExtra=materialExtraService.addMaterialExtra(materialExtra);
				count++;
			} catch (Exception e) {
				logger.error( e.getMessage());
			}
			mps.put(materialExtra.getMaterialId(), materialExtra.getId());
		}
		logger.info("'{}'表迁移完成，异常条目数量：{}", "material_extra",count);
    }
	
	
	@Autowired
	private MaterialNoticeAttachmentService materialNoticeAttachmentService;
	
	@Autowired
	private FileService fileService;
	
	public void transferMaterialNoticeAttachment() throws Exception{
		String sql = "select "+
						"a.new_pk, "+
						"b.filedir, "+
						"b.filename, "+
						"1 "+
						"from teach_material a "+
						"LEFT JOIN pub_addfileinfo b on b.tablekeyid = a.materid "+
						"where   b.childsystemname='notice_introduction' and tablename='TEACH_MATERIAL_INTRODUCTION' ";
		List<Map<String, Object>> materialNoticeAttachmentList=JdbcHelper.getJdbcTemplate().queryForList(sql);
		List<Map<String, Object>> excel = new LinkedList<>();
		int count =0;
		for(Map<String, Object> map:materialNoticeAttachmentList){
			//文件名
			String fileName = (String)map.get("filename") ;
			Long newMaterid = (Long)  map.get("new_pk");
			Long materialExtraId = mps.get(newMaterid);
			MaterialNoticeAttachment materialNoticeAttachment =new MaterialNoticeAttachment();
			materialNoticeAttachment.setMaterialExtraId(materialExtraId);
			//先用一个临时的"-"占位，不然会报错;
			materialNoticeAttachment.setAttachment("-");
			materialNoticeAttachment.setAttachmentName(fileName);
			materialNoticeAttachment.setDownload(1L);
			try {
				materialNoticeAttachment=materialNoticeAttachmentService.addMaterialNoticeAttachment(materialNoticeAttachment);
			} catch (Exception e) {
				continue;
			}
			if(null != materialNoticeAttachment.getId()){
				String mongoId;
	            try {
	                mongoId = fileService.migrateFile((String)map.get("filedir"), FileType.MATERIAL_NOTICE_ATTACHMENT,materialNoticeAttachment.getId());
	            } catch (IOException ex) {
	            	logger.error("文件读取异常，路径<{}>，异常信息：{}", (String)map.get("filedir"), ex.getMessage());
	                map.put(SQLParameters.EXCEL_EX_HEADER, "文件读取异常");
	                excel.add(map);
	                continue;
	            }
	            if(null != mongoId){
	            	materialNoticeAttachment.setAttachment(mongoId);
	                materialNoticeAttachmentService.updateMaterialNoticeAttachment(materialNoticeAttachment);
	                count++;
	            }
	        }
		}
		logger.info("教材通知附件表迁移了{}条数据",count);
	}
	
	
	@Autowired
	private MaterialNoteAttachmentService materialNoteAttachmentService;
	
	public void transferMaterialNoteAttachment() throws Exception{
		String sql = "select "+
						"a.new_pk materid, "+
						"b.filedir, "+
						"b.filename "+
						"from teach_material a  "+
						"LEFT JOIN pub_addfileinfo b on b.tablekeyid = a.materid "+
						"where b.childsystemname='notice' and tablename='TEACH_MATERIAL' ";
		List<Map<String, Object>> materialMaterialNoteAttachmentList=JdbcHelper.getJdbcTemplate().queryForList(sql);
		List<Map<String, Object>> excel = new LinkedList<>();
		int count =0;
		for(Map<String, Object> map:materialMaterialNoteAttachmentList){
			//文件名
			String fileName =(String) map.get("filename");
			Long newMaterid =(Long)   map.get("materid");
			Long materialExtraId = mps.get(newMaterid);
			MaterialNoteAttachment materialNoteAttachment =new MaterialNoteAttachment();
			materialNoteAttachment.setMaterialExtraId(materialExtraId);
			//先用一个临时的"-"占位，不然会报错;
			materialNoteAttachment.setAttachment("-");
			materialNoteAttachment.setAttachmentName(fileName);
			materialNoteAttachment.setDownload(1L);
			try {
				materialNoteAttachment=materialNoteAttachmentService.addMaterialNoteAttachment(materialNoteAttachment);
			} catch (Exception e) {
				continue;
			}
			if(null != materialNoteAttachment.getId()){
				String mongoId;
	            try {
	                mongoId = fileService.migrateFile((String)map.get("filedir"),FileType.MATERIAL_NOTE_ATTACHMENT,materialNoteAttachment.getId());
	            } catch (IOException ex) {
	            	logger.error("文件读取异常，路径<{}>，异常信息：{}", (String)map.get("filedir"), ex.getMessage());
	                map.put(SQLParameters.EXCEL_EX_HEADER, "文件读取异常");
	                excel.add(map);
	                continue;
	            }
	            if(null != mongoId){
	            	materialNoteAttachment.setAttachment(mongoId);
	            	materialNoteAttachmentService.updateMaterialNoteAttachment(materialNoteAttachment);
	                count++;
	            }
	        }
		}
		logger.info("教材通知附件表迁移了{}条数据",count);
	}
	
	@Autowired
	private MaterialContactService materialContactService;
	
	public void transferMaterialContact() throws Exception{
		String sql ="select "+
						"a.linkerid linkerid, "+
						"b.new_pk   materid, "+
						"c.new_pk   userid, "+
						"c.username username, "+
						"a.linkphone linkphone, "+
						"a.email    email, "+
						"a.orderno  orderno "+
						"from teach_material_linker  a  "+
						"LEFT JOIN teach_material  b on b.materid = a.materid "+
						"LEFT JOIN sys_user  c on c.userid = a.userid "+
						"where 1=1 ";
		//获取到所有数据表
		String tableName ="teach_material_linker";
		JdbcHelper.addColumn(tableName); //增加new_pk字段
		List<Map<String, Object>> materialContactList=JdbcHelper.getJdbcTemplate().queryForList(sql);
		List<Map<String, Object>> excel = new LinkedList<>();
		int count =0;
		for(Map<String, Object> object:materialContactList){
			MaterialContact materialContact= new MaterialContact(
					(Long) object.get("materid"),
					(Long) object.get("userid"),
					(String) object.get("username"),
					(String)object.get("linkphone"), 
					(String)object.get("email"),
					(Integer) object.get("orderno"));
			try {
				materialContact=materialContactService.addMaterialContact(materialContact);
				count++;
			} catch (Exception e) {
				excel.add(object);
				logger.error( e.getMessage());
			}
			if(null != materialContact.getId()){
				JdbcHelper.updateNewPrimaryKey(tableName, materialContact.getId(), "linkerid",object.get("linkerid"));//更新旧表中new_pk字段
			}
		}
		if (excel.size() > 0) {
            try {
                excelHelper.exportFromMaps(excel, tableName, tableName);
            } catch (IOException ex) {
                logger.error("异常数据导出到Excel失败", ex);
            }
        }
        logger.info("'{}'表迁移完成，异常条目数量：{}", tableName, excel.size());
        logger.info("原数据库中共有{}条数据，迁移了{}条数据", materialContactList.size(), count);
	}
	
	@Autowired
	private MaterialOrgService materialOrgService;
	public void materialOrg(){
		String sql ="select  "+
					"a.pushschoolid , "+
					"b.new_pk materid, "+
					"c.new_pk orgid "+
					"from teach_pushschool  a  "+
					"LEFT JOIN teach_material  b on b.materid = a.materid "+
					"LEFT JOIN ba_organize c on c.orgid = a.orgid "+
					"where 1=1 ";
		//获取到所有数据表
		String tableName ="teach_pushschool";
		JdbcHelper.addColumn(tableName); //增加new_pk字段
		List<Map<String, Object>> pubList=JdbcHelper.getJdbcTemplate().queryForList(sql);
		List<Map<String, Object>> excel = new LinkedList<>();
		int count =0;
		for(Map<String, Object> object:pubList){
			MaterialOrg materialOrg= new MaterialOrg((Long) object.get("materid"), (Long) object.get("orgid"));
			try {
				materialOrg=materialOrgService.addMaterialOrg(materialOrg);
				count++;
			} catch (Exception e) {
				excel.add(object);
				logger.error( e.getMessage());
			}
			if(null != materialOrg.getId()){
				JdbcHelper.updateNewPrimaryKey(tableName, materialOrg.getId(), "pushschoolid",object.get("pushschoolid"));//更新旧表中new_pk字段
			}
		}
		if (excel.size() > 0) {
		    try {
		        excelHelper.exportFromMaps(excel, tableName, tableName);
		    } catch (IOException ex) {
		        logger.error("异常数据导出到Excel失败", ex);
		    }
		}
		logger.info("'{}'表迁移完成，异常条目数量：{}", tableName, excel.size());
		logger.info("原数据库中共有{}条数据，迁移了{}条数据", pubList.size(), count);
	}
	
	@Autowired
	private MaterialProjectEditorService materialProjectEditorService;
	public void materialPprojectEeditor(){
		String sql ="select DISTINCT * from( "+
				"select "+
				"b.new_pk   materid, "+
				"c.new_pk   userid  "+
				"from teach_material_linker  a  "+
				"LEFT JOIN teach_material  b on b.materid = a.materid "+
				"LEFT JOIN sys_user  c on c.userid = a.userid "+
				"where true  "+
				"UNION select DISTINCT e.new_pk materid ,a.new_pk userid from sys_user  a  "+
				"LEFT JOIN  sys_userorganize b on b.userid=a.userid  "+
				"LEFT JOIN  sys_userrole   c on   c.userid =a.userid   "+
				"lEFT JOIN  sys_role   d  on d.roleid =c.roleid    "+
				"LEFT JOIN teach_material e on e.createorgid = b.orgid   "+
				"where d.rolename like '%主任%' and e.materid is not null   GROUP BY e.materid "+
				")temp ";
		List<Map<String, Object>> materialProjectEditorList=JdbcHelper.getJdbcTemplate().queryForList(sql);
		List<Map<String, Object>> excel = new LinkedList<>();
		int count =0;
		for(Map<String, Object> object:materialProjectEditorList){
			MaterialProjectEditor materialProjectEditor= new MaterialProjectEditor((Long) object.get("materid"), (Long) object.get("userid"));
			try {
				materialProjectEditor=materialProjectEditorService.addMaterialProjectEditor(materialProjectEditor);
				count++;
			} catch (Exception e) {
				excel.add(object);
				logger.error( e.getMessage());
			}
		}
		if (excel.size() > 0) {
		    try {
		        excelHelper.exportFromMaps(excel, "MaterialProjectEditor", "MaterialProjectEditor");
		    } catch (IOException ex) {
		        logger.error("异常数据导出到Excel失败", ex);
		    }
		}
		logger.info("'{}'表迁移完成，异常条目数量：{}", "MaterialProjectEditor", excel.size());
		logger.info("原数据库中共有{}条数据，迁移了{}条数据", materialProjectEditorList.size(), count);
	}
	
	public void start() throws Exception {
		//materialType();
//		material();
//		materialExtension();
//		materialExtra();
//		transferMaterialNoticeAttachment();
//		transferMaterialNoteAttachment();
//		transferMaterialContact();
		materialOrg();
		materialPprojectEeditor();
	}
	
}
