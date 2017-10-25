package com.bc.pmpheep.migration;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.back.po.MaterialExtension;
import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.back.po.MaterialNoticeAttachment;
import com.bc.pmpheep.back.service.MaterialExtensionService;
import com.bc.pmpheep.back.service.MaterialExtraService;
import com.bc.pmpheep.back.service.MaterialNoticeAttachmentService;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.OtherParamaters;
import com.bc.pmpheep.migration.common.Until;
/**
 *@author MrYang 
 *@CreateDate 2017年10月23日 下午2:18:10
 **/
@Component
public class Material {
	
	Logger  logger = LoggerFactory.getLogger(Material.class);
	
	@Autowired
	private MaterialService materialService;
	
	public  void transferMaterial() throws Exception{
		String sql="select "+
					"a.materid, "+
					"a.matername, "+
					"if(a.teachround  is null or a.teachround  ='',1,a.teachround), "+
					"if(a.booktypesid is null or a.booktypesid='',1,a.booktypesid), "+
					"a.showenddate, "+
					"a.enddate, "+
					"a.agedeaddate, "+
					"a.mailaddress, "+
					"a.flowtype, "+
					"ifnull(b.new_org_id,0), "+
					"ifnull(d.new_user_id,t.new_user_id) , "+
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
					"if(a.ispublishfront =1,true,false), "+
					"if(a.isdelete =1 ,true,false), "+
					"a.createdate, "+
					"IFNULL(g.new_user_id,t.new_user_id), "+
					"a.updatedate, "+
					"h.new_user_id "+
					"from teach_material a  "+
					"LEFT JOIN ba_organize b on b.orgid=a.createorgid  "+
					"LEFT JOIN sys_userorganize c on c.orgid=b.orgid  "+
					"LEFT JOIN sys_user  d on d.userid =c.userid "+
					"LEFT JOIN sys_userrole e on e.userid = d.userid  "+
					"LEFT JOIN sys_role f on f.roleid = e.roleid "+
					"LEFT JOIN sys_user g on g.userid = a.createuserid "+
					"LEFT JOIN sys_user h on h.userid = a.updateuserid,(select new_user_id from sys_user where usercode='admin' )t  "+
					"WHERE a.isdelete = 0 GROUP BY A.new_materid ";
		//获取到所有数据表
		List<Object[]> materialList=Until.getListData(sql);
		for(Object[] oldMaterial:materialList){
			String oldMaterid = (String)oldMaterial[0];
			com.bc.pmpheep.back.po.Material material =new com.bc.pmpheep.back.po.Material();
			material.setMaterialName((String)oldMaterial[1]);
			material.setMaterialRound((Integer.parseInt(String.valueOf(oldMaterial[2]))));
			material.setMaterialType(new Long((String) oldMaterial[3]));
			material.setDeadline((Timestamp)oldMaterial[4]);
			material.setActualDeadline((Timestamp)oldMaterial[5]);
			material.setAgeDeadline((Timestamp)oldMaterial[6]);
			material.setMailAddress((String)oldMaterial[7]);
			material.setProgress(new Short((String)oldMaterial[8]));
			material.setDepartmentId((Long)oldMaterial[9]);
			material.setDirector((Long) oldMaterial[10]);//director,
			material.setIsMultiBooks("1".equals(String.valueOf(oldMaterial[11]))); //is_multi_books,
			material.setIsMultiPosition("1".equals(String.valueOf(oldMaterial[12])));//is_multi_position,
			material.setIsEduExpUsed("1".equals(String.valueOf(oldMaterial[13])));//is_edu_exp_used,
			material.setIsEduExpRequired("1".equals(String.valueOf(oldMaterial[14])));//is_edu_exp_required,
			material.setIsWorkExpUsed("1".equals(String.valueOf(oldMaterial[15])));//is_work_exp_used,
			material.setIsWorkExpRequired("1".equals(String.valueOf(oldMaterial[16])));//is_work_exp_required,
			material.setIsTeachExpUsed("1".equals(String.valueOf(oldMaterial[17])));//is_teach_exp_used,
			material.setIsTeachExpRequired("1".equals(String.valueOf(oldMaterial[18])));//is_teach_exp_required,
			material.setIsAcadeUsed("1".equals(String.valueOf(oldMaterial[19])));//is_acade_used,
			material.setIsAcadeRequired("1".equals(String.valueOf(oldMaterial[20])));//is_acade_required,
			material.setIsLastPositionUsed("1".equals(String.valueOf(oldMaterial[21])));//is_last_position_used,
			material.setIsLastPositionRequired("1".equals(String.valueOf(oldMaterial[22])));//is_last_position_required,
			material.setIsNationalCourseUsed("1".equals(String.valueOf(oldMaterial[23])));//is_national_course_used,
			material.setIsNationalCourseRequired("1".equals(String.valueOf(oldMaterial[24])));//is_national_course_required,
			material.setIsProvincialCourseUsed("1".equals(String.valueOf(oldMaterial[25])));//is_provincial_course_used,
			material.setIsProvincialCourseRequired("1".equals(String.valueOf(oldMaterial[26])));//is_provincial_course_required,
			material.setIsSchoolCourseUsed("1".equals(String.valueOf(oldMaterial[27])));//is_school_course_used,
			material.setIsSchoolCourseRequired("1".equals(String.valueOf(oldMaterial[28])));//is_school_course_required,
			material.setIsNationalPlanUsed("1".equals(String.valueOf(oldMaterial[29])));//is_national_plan_used,
			material.setIsNationalPlanRequired("1".equals(String.valueOf(oldMaterial[30])));//is_national_plan_required,
			material.setIsTextbookWriterUsed("1".equals(String.valueOf(oldMaterial[31])));//is_textbook_writer_used,
			material.setIsTextbookWriterRequired("1".equals(String.valueOf(oldMaterial[32])));//is_textbook_writer_required,
			material.setIsOtherTextbookUsed("1".equals(String.valueOf(oldMaterial[33])));//is_other_textbook_used,
			material.setIsOtherTextbookRequired("1".equals(String.valueOf(oldMaterial[34])));//is_other_textbook_required,
			material.setIsResearchUsed("1".equals(String.valueOf(oldMaterial[35])));//is_research_used,
			material.setIsResearchRequired("1".equals(String.valueOf(oldMaterial[36])));//is_research_required,
			material.setIsPublished("1".equals(String.valueOf(oldMaterial[37])));//is_published,
			material.setIsDeleted("1".equals(String.valueOf(oldMaterial[38])));//is_deleted,
			material.setGmtCreate((Timestamp)oldMaterial[39]);//gmt_create,
			material.setFounderId((Long)oldMaterial[40]);//founder_id,
			material.setGmtUpdate((Timestamp)oldMaterial[41]);//gmt_update,
			material.setMenderId((Long)oldMaterial[42]);//mender_id
			material=materialService.addMaterial(material);
			Until.updateNewPk(oldMaterid, "teach_material", material.getId());
		}
		logger.info("教材主表导入完毕！");
	} 
	
	@Autowired
	private MaterialExtensionService materialExtensionService;
	
	public void transferMaterialExtension() throws Exception{
		String sql = "select "+
						"a.expendid,"+
						"b.new_materid,"+
						"a.expendname,"+
						"a.isfill from "+
						"teach_material_extend a "+
						"LEFT JOIN teach_material b on b.materid=a.materid "+
						"where b.materid is not null and b.isdelete =0 ";
		List<Object[]> materialExtensionList=Until.getListData(sql);
		for(Object[] materialExtension:materialExtensionList){
			String oldExpendid = (String)materialExtension[0];
			MaterialExtension newMaterialExtension=new MaterialExtension();
			newMaterialExtension.setMaterialId((Long)materialExtension[1]);
			newMaterialExtension.setExtensionName((String)materialExtension[2]);
			newMaterialExtension.setIsRequired("1".equals(String.valueOf(materialExtension[3])));
			newMaterialExtension=materialExtensionService.addMaterialExtension(newMaterialExtension);
			Until.updateNewPk(oldExpendid, "teach_material_extend", newMaterialExtension.getId());
		}
	}
	
	@Autowired
	private MaterialExtraService materialExtraService;
	
	//用来储存MaterialExtra返回的主键，供后面使用 Map<教材id,教材通知备注表id> 
	public static Map<Long,Long> mps = new HashMap<Long,Long>(12);
	
	public void transferMaterialExtra() throws Exception{
		String sql = "select "+
						"new_materid,"+
						"new_materid,"+
						"introduction,"+
						"remark "+
						"from teach_material where isdelete =0 ";
		List<Object[]> materialExtraList=Until.getListData(sql);
		for(Object[] object:materialExtraList){
			MaterialExtra materialExtra = new MaterialExtra();
			materialExtra.setMaterialId((Long)object[1]);
			materialExtra.setNotice((String)object[2]);
			materialExtra.setNote((String)object[3]);
			materialExtra=materialExtraService.addMaterialExtra(materialExtra);
			mps.put(materialExtra.getMaterialId(), materialExtra.getId());
		}
	}
	
	
	@Autowired
	private MaterialNoticeAttachmentService materialNoticeAttachmentService;
	
	@Autowired
	private FileService fileService;
	
	public void transferMaterialNoticeAttachment() throws Exception{
		String sql = "select "+
						"a.new_materid, "+
						"b.filedir, "+
						"b.filename, "+
						"1 "+
						"from teach_material a "+
						"LEFT JOIN pub_addfileinfo b on b.tablekeyid = a.materid "+
						"where a.isdelete =0 and b.childsystemname='notice_introduction' and tablename='TEACH_MATERIAL_INTRODUCTION' ";
		List<Object[]> materialNoticeAttachmentList=Until.getListData(sql);
		for(Object[] object:materialNoticeAttachmentList){
			//根据文件路径获取File文件
			File oldFile = new File(OtherParamaters.FILEPATH+(String)object[1]);
			//文件存在并且不是文件夹
			if(oldFile.exists() && !oldFile.isDirectory()){
				//文件名
				String fileName = oldFile.getName();
				Long newMaterid =(Long)object[0];
				Long materialExtraId = mps.get(newMaterid);
				MaterialNoticeAttachment materialNoticeAttachment =new MaterialNoticeAttachment();
				materialNoticeAttachment.setMaterialExtraId(materialExtraId);
				//先用一个临时的"-"占位，不然会报错;
				materialNoticeAttachment.setAttachment("-");
				materialNoticeAttachment.setAttachmentName(fileName);
				materialNoticeAttachment.setDownload(1L);
				materialNoticeAttachment=materialNoticeAttachmentService.addMaterialNoticeAttachment(materialNoticeAttachment);
				MultipartFile file= new MockMultipartFile(fileName,new FileInputStream(oldFile));
				String fileId=fileService.save(file, FileType.MATERIAL_NOTICE_ATTACHMENT, materialNoticeAttachment.getId());
				materialNoticeAttachment.setAttachment(fileId);
				//更新附件id
				materialNoticeAttachmentService.updateMaterialNoticeAttachment(materialNoticeAttachment);
			}
		}
	}

}
