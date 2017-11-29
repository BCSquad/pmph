/**
 * 
 */
package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.DecAcadeDao;
import com.bc.pmpheep.back.dao.DecCourseConstructionDao;
import com.bc.pmpheep.back.dao.DecEduExpDao;
import com.bc.pmpheep.back.dao.DecExtensionDao;
import com.bc.pmpheep.back.dao.DecLastPositionDao;
import com.bc.pmpheep.back.dao.DecNationalPlanDao;
import com.bc.pmpheep.back.dao.DecPositionDao;
import com.bc.pmpheep.back.dao.DecResearchDao;
import com.bc.pmpheep.back.dao.DecTeachExpDao;
import com.bc.pmpheep.back.dao.DecTextbookDao;
import com.bc.pmpheep.back.dao.DecWorkExpDao;
import com.bc.pmpheep.back.dao.DeclarationDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DecAcade;
import com.bc.pmpheep.back.po.DecCourseConstruction;
import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.back.po.DecExtension;
import com.bc.pmpheep.back.po.DecLastPosition;
import com.bc.pmpheep.back.po.DecNationalPlan;
import com.bc.pmpheep.back.po.DecResearch;
import com.bc.pmpheep.back.po.DecTeachExp;
import com.bc.pmpheep.back.po.DecTextbook;
import com.bc.pmpheep.back.po.DecWorkExp;
import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.service.common.SystemMessageService;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.RouteUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.vo.ApplicationVO;
import com.bc.pmpheep.back.vo.DecPositionDisplayVO;
import com.bc.pmpheep.back.vo.DeclarationListVO;
import com.bc.pmpheep.back.vo.DeclarationOrDisplayVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * <p>
 * Title:Declaration业务层实现类
 * <p>
 * 
 * @author lyc
 * @date 2017年9月24日 下午3:52:18
 */
@Service
public class DeclarationServiceImpl implements DeclarationService {

    @Autowired
    private DeclarationDao           declarationDao;
    @Autowired
    private SystemMessageService     systemMessageService;
    @Autowired
    private DecPositionDao           decPositionDao;
    @Autowired
    private DecEduExpDao             decEduExpDao;
    @Autowired
    private DecWorkExpDao            decWorkExpDao;
    @Autowired
    private DecTeachExpDao           decTeachExpDao;
    @Autowired
    private DecAcadeDao              decAcadeDao;
    @Autowired
    private DecLastPositionDao       decLastPositionDao;
    @Autowired
    private DecCourseConstructionDao decCourseConstructionDao;
    @Autowired
    private DecNationalPlanDao       decNationalPlanDao;
    @Autowired
    private DecTextbookDao           decTextbookDao;
    @Autowired
    private DecResearchDao           decResearchDao;
    @Autowired
    private DecExtensionDao          decExtensionDao;

    @Override
    public Declaration addDeclaration(Declaration declaration) throws CheckedServiceException {
        if (null == declaration) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "参数为空");
        }
        if (null == declaration.getMaterialId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "教材id不能为空");
        }
        if (null == declaration.getUserId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "作家id不能为空");
        }
        declarationDao.addDeclaration(declaration);
        return declaration;
    }

    @Override
    public Integer deleteDeclarationById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
        }
        return declarationDao.deleteDeclarationById(id);
    }

    @Override
    public Integer updateDeclaration(Declaration declaration) throws CheckedServiceException {
        if (null == declaration.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
        }
        return declarationDao.updateDeclaration(declaration);
    }

    @Override
    public Declaration getDeclarationById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "主键为空");
        }
        return declarationDao.getDeclarationById(id);
    }

    @Override
    public List<Declaration> getDeclarationByMaterialId(Long materialId)
    throws CheckedServiceException {
        if (null == materialId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "教材id为空");
        }
        return declarationDao.getDeclarationByMaterialId(materialId);
    }

    @Override
    public PageResult<DeclarationListVO> pageDeclaration(Integer pageNumber, Integer pageSize,
    Long materialId, String textBookids, String realname, String position, String title,
    String orgName, String unitName, Integer positionType, Integer onlineProgress,
    Integer offlineProgress) throws CheckedServiceException {
        if (null == materialId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.NULL_PARAM, "教材为空");
        }
        Gson gson = new Gson();
        List<Long> bookIds = gson.fromJson(textBookids, new TypeToken<ArrayList<Long>>() {
        }.getType());
        // 拼装复合参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("materialId", materialId);
        if (null != bookIds && bookIds.size() > 0) {
            map.put("bookIds", bookIds); // 书籍ids
        }
        if (StringUtil.notEmpty(realname)) {
            map.put("realname", StringUtil.toAllCheck(realname)); // 账号或者姓名
        }
        if (StringUtil.notEmpty(position)) {
            map.put("position", StringUtil.toAllCheck(position)); // 职务
        }
        if (StringUtil.notEmpty(title)) {
            map.put("title", StringUtil.toAllCheck(title)); // 职称
        }
        if (StringUtil.notEmpty(orgName)) {
            map.put("orgName", StringUtil.toAllCheck(orgName)); // 工作单位
        }
        if (StringUtil.notEmpty(unitName)) {
            map.put("unitName", StringUtil.toAllCheck(unitName)); // 申报单位
        }
        if (null != positionType && positionType != 0) {
            map.put("positionType", positionType); // 申报职位
        }
        if (null != onlineProgress && onlineProgress != 0) {
            map.put("onlineProgress", onlineProgress); // 学校审核进度
        }
        if (null != offlineProgress && offlineProgress != 0) {
            map.put("offlineProgress", offlineProgress); // 纸质表进度
        }
        // 包装参数实体
        PageParameter<Map<String, Object>> pageParameter =
        new PageParameter<Map<String, Object>>(pageNumber, pageSize, map);
        // 返回实体
        PageResult<DeclarationListVO> pageResult = new PageResult<>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 获取总数
        Integer total = declarationDao.listDeclarationTotal(pageParameter);
        if (null != total && total > 0) {
            List<DeclarationListVO> rows = declarationDao.listDeclaration(pageParameter);
            pageResult.setRows(rows);
        }
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    public Declaration confirmPaperList(Long id, Integer offlineProgress, Long materialId)
    throws CheckedServiceException, IOException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "主键不能为空!");
        }
        if (ObjectUtil.isNull(offlineProgress)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "确认收到纸质表不能为空!");
        }
        // 获取当前作家用户申报信息
        Declaration declarationCon = declarationDao.getDeclarationById(id);
        declarationCon.setMaterialId(materialId);
        declarationCon.setOfflineProgress(offlineProgress);
        declarationDao.updateDeclaration(declarationCon);
        systemMessageService.sendWhenReceiptAudit(declarationCon.getId(), true); // 发送系统消息
        return declarationCon;
    }

    @Override
    public Declaration onlineProgress(Long id, Integer onlineProgress, Long materialId)
    throws CheckedServiceException, IOException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "主键不能为空!");
        }
        if (ObjectUtil.isNull(onlineProgress)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                                              CheckedExceptionResult.ILLEGAL_PARAM, "审核进度不能为空!");
        }
        // 获取当前作家用户申报信息
        Declaration declarationCon = declarationDao.getDeclarationById(id);
        declarationCon.setOnlineProgress(onlineProgress);
        Integer onlineOne = 2;
        Integer onlineTwo = 3;
        if (onlineOne.equals(onlineProgress)) { // 获取审核进度是2则被退回
            declarationDao.updateDeclaration(declarationCon);
            systemMessageService.sendWhenDeclarationFormAudit(declarationCon.getId(), false); // 发送系统消息
        } else if (onlineTwo.equals(onlineProgress)) { // 获取审核进度是3则通过
            declarationDao.updateDeclaration(declarationCon);
            systemMessageService.sendWhenDeclarationFormAudit(declarationCon.getId(), true); // 发送系统消息
        }
        return declarationCon;
    }

	@Override
	public ApplicationVO exportExcel(Long declarationId) {
		ApplicationVO applicationVO = new ApplicationVO();
		List<DecPositionDisplayVO> decPositionList = decPositionDao.listDecPositionsOrBook(declarationId);
		for (DecPositionDisplayVO decPositions : decPositionList) {
			String syllabusId = decPositions.getSyllabusId();
			if (StringUtil.notEmpty(syllabusId)) {
				String syllabusIds = RouteUtil.MONGODB_FILE + syllabusId;
				decPositions.setSyllabusId(syllabusIds);
			}
		}
		// 专家信息
		DeclarationOrDisplayVO declaration = declarationDao.getDeclarationByIdOrOrgName(declarationId);
		String orgNameOne = declaration.getOrgNameOne();
		declaration.setOrgName(orgNameOne);
		// 学习经历
		List<DecEduExp> decEduExpList = decEduExpDao.getListDecEduExpByDeclarationId(declarationId);
		// 工作经历
		List<DecWorkExp> decWorkExpList = decWorkExpDao.getListDecWorkExpByDeclarationId(declarationId);
		// 教学经历
		List<DecTeachExp> decTeachExpList = decTeachExpDao.getListDecTeachExpByDeclarationId(declarationId);
		// 兼职学术
		List<DecAcade> decAcadeList = decAcadeDao.getListDecAcadeByDeclarationId(declarationId);
		// 上套教材
		List<DecLastPosition> decLastPositionList = decLastPositionDao.getListDecLastPositionByDeclarationId(declarationId);
		// 国家级精品课程建设情况 //type 1=国家
		List<DecCourseConstruction> decNationalCourseConstructionList = decCourseConstructionDao.decNationalCourseConstructionList(declarationId);
		// 省部级精品课程建设情况//type 2=省部
		List<DecCourseConstruction> decProvinceCourseConstructionList = decCourseConstructionDao.decProvinceCourseConstructionList(declarationId);
		// 学校精品课程建设情况   //type 3=学校
		List<DecCourseConstruction> decSchoolCourseConstructionList = decCourseConstructionDao.decSchoolCourseConstructionList(declarationId);
		// 主编国家级规划
		List<DecNationalPlan> decNationalPlanList = decNationalPlanDao.getListDecNationalPlanByDeclarationId(declarationId);
		// 教材编写
		List<DecTextbook> decTextbookList = decTextbookDao.getListDecTextbookByDeclarationId(declarationId);
		// 作家科研
		List<DecResearch> decResearchList = decResearchDao.getListDecResearchByDeclarationId(declarationId);
		// 作家扩展项
		List<DecExtension> decExtensionList = decExtensionDao.getListDecExtensionsByDeclarationId(declarationId);
		// 把查询出来的信息添加进applicationVO
		applicationVO.setDecPositionList(decPositionList);
		applicationVO.setDeclaration(declaration);
		applicationVO.setDecEduExpList(decEduExpList);
		applicationVO.setDecWorkExpList(decWorkExpList);
		applicationVO.setDecTeachExpList(decTeachExpList);
		applicationVO.setDecAcadeList(decAcadeList);
		applicationVO.setDecLastPositionList(decLastPositionList);
		applicationVO.setDecNationalCourseConstructionList(decNationalCourseConstructionList);
		applicationVO.setDecProvinceCourseConstructionList(decProvinceCourseConstructionList);;
		applicationVO.setDecSchoolCourseConstructionList(decSchoolCourseConstructionList);
		applicationVO.setDecNationalPlanList(decNationalPlanList);
		applicationVO.setDecTextbookList(decTextbookList);
		applicationVO.setDecResearchList(decResearchList);
		applicationVO.setDecExtensionList(decExtensionList);
		return applicationVO;
	}
}
