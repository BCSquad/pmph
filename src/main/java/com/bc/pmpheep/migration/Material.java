package com.bc.pmpheep.migration;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.migration.common.Until;
/**
 *@author MrYang 
 *@CreateDate 2017年10月23日 下午2:18:10
 **/
@Component
public class Material {
	
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
	} 

}
