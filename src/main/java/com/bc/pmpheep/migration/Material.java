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
		List<Object[]> lst=Until.getListData(sql);
		for(Object[]o:lst){
			String oldMaterid = (String)o[0];
			com.bc.pmpheep.back.po.Material m =new com.bc.pmpheep.back.po.Material();
			m.setMaterialName((String)o[1]);
			m.setMaterialRound((Integer.parseInt(String.valueOf(o[2]))));
			m.setMaterialType(new Long((String) o[3]));
			m.setDeadline((Timestamp)o[4]);
			m.setActualDeadline((Timestamp)o[5]);
			m.setAgeDeadline((Timestamp)o[6]);
			m.setMailAddress((String)o[7]);
			m.setProgress(new Short((String)o[8]));
			m.setDepartmentId((Long)o[9]);
			m.setDirector((Long) o[10]);//director,
			m.setIsMultiBooks("1".equals(String.valueOf(o[11]))); //is_multi_books,
			m.setIsMultiPosition("1".equals(String.valueOf(o[12])));//is_multi_position,
			m.setIsEduExpUsed("1".equals(String.valueOf(o[13])));//is_edu_exp_used,
			m.setIsEduExpRequired("1".equals(String.valueOf(o[14])));//is_edu_exp_required,
			m.setIsWorkExpUsed("1".equals(String.valueOf(o[15])));//is_work_exp_used,
			m.setIsWorkExpRequired("1".equals(String.valueOf(o[16])));//is_work_exp_required,
			m.setIsTeachExpUsed("1".equals(String.valueOf(o[17])));//is_teach_exp_used,
			m.setIsTeachExpRequired("1".equals(String.valueOf(o[18])));//is_teach_exp_required,
			m.setIsAcadeUsed("1".equals(String.valueOf(o[19])));//is_acade_used,
			m.setIsAcadeRequired("1".equals(String.valueOf(o[20])));//is_acade_required,
			m.setIsLastPositionUsed("1".equals(String.valueOf(o[21])));//is_last_position_used,
			m.setIsLastPositionRequired("1".equals(String.valueOf(o[22])));//is_last_position_required,
			m.setIsNationalCourseUsed("1".equals(String.valueOf(o[23])));//is_national_course_used,
			m.setIsNationalCourseRequired("1".equals(String.valueOf(o[24])));//is_national_course_required,
			m.setIsProvincialCourseUsed("1".equals(String.valueOf(o[25])));//is_provincial_course_used,
			m.setIsProvincialCourseRequired("1".equals(String.valueOf(o[26])));//is_provincial_course_required,
			m.setIsSchoolCourseUsed("1".equals(String.valueOf(o[27])));//is_school_course_used,
			m.setIsSchoolCourseRequired("1".equals(String.valueOf(o[28])));//is_school_course_required,
			m.setIsNationalPlanUsed("1".equals(String.valueOf(o[29])));//is_national_plan_used,
			m.setIsNationalPlanRequired("1".equals(String.valueOf(o[30])));//is_national_plan_required,
			m.setIsTextbookWriterUsed("1".equals(String.valueOf(o[31])));//is_textbook_writer_used,
			m.setIsTextbookWriterRequired("1".equals(String.valueOf(o[32])));//is_textbook_writer_required,
			m.setIsOtherTextbookUsed("1".equals(String.valueOf(o[33])));//is_other_textbook_used,
			m.setIsOtherTextbookRequired("1".equals(String.valueOf(o[34])));//is_other_textbook_required,
			m.setIsResearchUsed("1".equals(String.valueOf(o[35])));//is_research_used,
			m.setIsResearchRequired("1".equals(String.valueOf(o[36])));//is_research_required,
			m.setIsPublished("1".equals(String.valueOf(o[37])));//is_published,
			m.setIsDeleted("1".equals(String.valueOf(o[38])));//is_deleted,
			m.setGmtCreate((Timestamp)o[39]);//gmt_create,
			m.setFounderId((Long)o[40]);//founder_id,
			m.setGmtUpdate((Timestamp)o[41]);//gmt_update,
			m.setMenderId((Long)o[42]);//mender_id
			m=materialService.addMaterial(m);
			Until.updateNewPk(oldMaterid, "teach_material", m.getId());
		}
	} 

}
