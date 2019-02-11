package com.bc.pmpheep.back.controller.org;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bc.pmpheep.annotation.LogDetail;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.po.Org;
import com.bc.pmpheep.back.service.OrgService;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.OrgVO;
import com.bc.pmpheep.controller.bean.ResponseBean;
import com.google.gson.Gson;

/**
 * @author MrYang
 * @CreateDate 2017年9月26日 上午9:44:18
 *
 **/
@Controller
@RequestMapping(value = "/orgs")
@SuppressWarnings( "all")
public class OrgController {

	@Autowired
	private OrgService orgService;
	// 当前业务类型
	private static final String BUSSINESS_TYPE = "机构";

	/**
	 * 分页查询org
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 上午9:46:19
	 * @param orgVO
	 * @return 分页数据集
	 */
	@RequestMapping(value = "/list/org", method = RequestMethod.GET)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "查询机构列表")
	@ResponseBody
	public ResponseBean listOrg(@RequestParam(name = "pageNumber", defaultValue = "1") Integer pageNumber,
			@RequestParam(name = "pageSize") Integer pageSize, OrgVO orgVO) {
		PageParameter<OrgVO> pageParameter = new PageParameter<OrgVO>(pageNumber, pageSize, orgVO);
		return new ResponseBean(orgService.listOrg(pageParameter));
	}

	/**
	 * 新增一个org
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 下午1:48:59
	 * @param org
	 * @return 新增后的org
	 */
	@RequestMapping(value = "/add/org", method = RequestMethod.POST)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "新增机构")
	@ResponseBody
	public ResponseBean addOrg(Org org) {
		return new ResponseBean(orgService.addOrg(org));
	}

	/**
	 * 更新 org
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 下午1:57:33
	 * @param org
	 * @return 更新影响的行数
	 */
	@RequestMapping(value = "/update/org", method = RequestMethod.PUT)
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "更新机构")
	@ResponseBody
	public ResponseBean updateOrg(Org org) {
		return new ResponseBean(orgService.updateOrg(org));
	}

	/**
	 * 通过id删除 一个org
	 * 
	 * @author Mryang
	 * @createDate 2017年9月26日 下午1:58:42
	 * @param id
	 * @return 影响的行数
	 */
    @LogDetail(businessType = BUSSINESS_TYPE, logRemark = "在新增或者修改用户页面查询机构")
	@RequestMapping(value = "/delete/org/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseBean deleteOrgById(@PathVariable Long id) {
		return new ResponseBean(orgService.deleteOrgById(id));
	}

	/**
	 * 
	 * 
	 * 功能描述：在新增或者修改用户页面查询机构
	 *
	 * @param orgName
	 *            机构名称
	 * @return 模糊查询出来的机构
	 *
	 */
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "在新增或者修改用户页面查询机构")
	@RequestMapping(value = "/list/orgByOrgName", method = RequestMethod.GET)
	public ResponseBean orgByOrgName(@RequestParam("orgName") String orgName) {
		return new ResponseBean(orgService.listOrgByOrgName(orgName));
	}
	
	@ResponseBody
	@LogDetail(businessType = BUSSINESS_TYPE, logRemark = "解析批量导入的发布学校数据")
    @RequestMapping(value = "/orgExport", method = RequestMethod.POST)
    public ResponseBean excel(MultipartFile file,HttpServletRequest req){
 		if(null == file || file.isEmpty()){
 			return new ResponseBean("没有文件");
 		}
 		//文件名称
        String name =file.getOriginalFilename();
    	//文件类型
        String fileType = name.substring(name.lastIndexOf("."));
        
		InputStream in = null;
		try {
			in = file.getInputStream();
		} catch (FileNotFoundException e) {
			if(null != in ){
				try {
					in.close();
				} catch (Exception ee) {
					
				}finally{
					in = null;
				}
			}
			return new ResponseBean("未获取到文件");
		} catch (Exception e) {
			if(null != in ){
				try {
					in.close();
				} catch (Exception ee) {
					
				}finally{
					in = null;
				}
			}
			return new ResponseBean("未知异常");
		} 
		Workbook workbook = null;
		try {
			if ((".xls").equals(fileType)){
    			workbook = new HSSFWorkbook(in);
    		} else if ((".xlsx").equals(fileType)){
    			workbook = new XSSFWorkbook(in);
    		} else{
    			if(null != in ){
    				try {
    					in.close();
    				} catch (Exception ee) {
    					
    				}finally{
    					in = null;
    				}
    			}
    			return new ResponseBean("读取的不是Excel文件");
    		}
		} catch (IOException e) {
			if(null != workbook){
				try {
					workbook.close();
				} catch (Exception ee) {
					
				}finally{
					workbook = null;
				}
			}
			if(null != in ){
				try {
					in.close();
				} catch (Exception ee) {
					
				}finally{
					in = null;
				}
			}
			return new ResponseBean("读取文件异常");
		} catch(Exception e){
			if(null != workbook){
				try {
					workbook.close();
				} catch (Exception ee) {
					
				}finally{
					workbook = null;
				}
			}
			if(null != in ){
				try {
					in.close();
				} catch (Exception ee) {
					
				}finally{
					in = null;
				}
			}
			return new ResponseBean("未知异常");
		} 
		
		//sheet数目
		//int sheetTotal = workbook.getNumberOfSheets() ;
		Sheet sheet = workbook.getSheetAt(0);
		List<Org>    orgs  = new ArrayList<Org>(sheet.getLastRowNum());
		List<String> erros = new ArrayList<String>(sheet.getLastRowNum());
		for (int rowNum = 1 ; rowNum <= sheet.getLastRowNum();rowNum ++){
			Row row = sheet.getRow(rowNum);
			if (null == row){
				continue;
			}
			Cell cell1 = row.getCell(0);
			Cell cell2 = row.getCell(1);
			Cell cell3 = row.getCell(2);
			String value1 = StringUtil.getCellValue(cell1);
			String value2 = StringUtil.getCellValue(cell2);
			String value3 = StringUtil.getCellValue(cell3);
			if(StringUtil.notEmpty(value2)&&StringUtil.notEmpty(value3)){
				Org org=orgService.getOrgByNameAndUserName(value2, value3);
				if(null != org){
					orgs.add(org);
				}else{
					erros.add("系统找不到机构名称为\""+value2+"\",机构代码为\""+value3+"\"的机构");
				}
			}else {
				if(StringUtil.isEmpty(value1)&&StringUtil.isEmpty(value2)&&StringUtil.isEmpty(value3)){
					
				}else{
					erros.add("第"+rowNum+"条数据填写不完整 ");
				}
			}			
		}
		if(null != workbook){
			try {
				workbook.close();
			} catch (Exception e) {
				
			}finally{
				workbook = null;
			}
		}
		if(null != in ){
			try {
				in.close();
			} catch (Exception e) {
				
			}finally{
				in = null;
			}
		}
		Map<String,Object> res=  new HashMap<String,Object>();
		res.put("orgs", orgs);
		res.put("erros", erros);
		return new ResponseBean(res);
	}
}
