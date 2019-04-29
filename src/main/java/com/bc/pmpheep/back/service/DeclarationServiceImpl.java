/**
 *
 */
package com.bc.pmpheep.back.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bc.pmpheep.back.dao.*;
import com.bc.pmpheep.back.util.*;
import com.mchange.v2.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.DecAcade;
import com.bc.pmpheep.back.po.DecAcadeReward;
import com.bc.pmpheep.back.po.DecAchievement;
import com.bc.pmpheep.back.po.DecClinicalReward;
import com.bc.pmpheep.back.po.DecCourseConstruction;
import com.bc.pmpheep.back.po.DecEduExp;
import com.bc.pmpheep.back.po.DecIntention;
import com.bc.pmpheep.back.po.DecLastPosition;
import com.bc.pmpheep.back.po.DecMonograph;
import com.bc.pmpheep.back.po.DecMoocDigital;
import com.bc.pmpheep.back.po.DecNationalPlan;
import com.bc.pmpheep.back.po.DecPosition;
import com.bc.pmpheep.back.po.DecPublishReward;
import com.bc.pmpheep.back.po.DecResearch;
import com.bc.pmpheep.back.po.DecSci;
import com.bc.pmpheep.back.po.DecTeachExp;
import com.bc.pmpheep.back.po.DecTextbook;
import com.bc.pmpheep.back.po.DecTextbookPmph;
import com.bc.pmpheep.back.po.DecWorkExp;
import com.bc.pmpheep.back.po.Declaration;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.MaterialExtension;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.WriterUser;
import com.bc.pmpheep.back.po.WriterUserTrendst;
import com.bc.pmpheep.back.service.common.SystemMessageService;
import com.bc.pmpheep.back.vo.ApplicationVO;
import com.bc.pmpheep.back.vo.DecExtensionVO;
import com.bc.pmpheep.back.vo.DecPositionDisplayVO;
import com.bc.pmpheep.back.vo.DecPositionPublishedVO;
import com.bc.pmpheep.back.vo.DeclarationListVO;
import com.bc.pmpheep.back.vo.DeclarationOrDisplayVO;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.servlet.http.HttpServletRequest;

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
    private DeclarationDao declarationDao;
    @Autowired
    private SystemMessageService systemMessageService;
    @Autowired
    private DecPositionDao decPositionDao;
    @Autowired
    private DecPositionPublishedDao decPositionPublishedDao;
    @Autowired
    private DecEduExpDao decEduExpDao;
    @Autowired
    private DecWorkExpDao decWorkExpDao;
    @Autowired
    private DecTeachExpDao decTeachExpDao;
    @Autowired
    private DecAchievementDao decAchievementDao;
    @Autowired
    private DecAcadeDao decAcadeDao;
    @Autowired
    private DecLastPositionDao decLastPositionDao;
    @Autowired
    private DecCourseConstructionDao decCourseConstructionDao;
    @Autowired
    private DecNationalPlanDao decNationalPlanDao;
    @Autowired
    private DecTextbookDao decTextbookDao;
    @Autowired
    private DecResearchDao decResearchDao;
    @Autowired
    private DecExtensionDao decExtensionDao;
    @Autowired
    private DecMonographDao decMonographDao;
    @Autowired
    private DecPublishRewardDao decPublishRewardDao;
    @Autowired
    private DecSciDao decSciDao;
    @Autowired
    private DecClinicalRewardDao decClinicalRewardDao;
    @Autowired
    private DecAcadeRewardDao decAcadeRewardDao;
    @Autowired
    private DecTextbookPmphDao decTextbookPmphDao;
    @Autowired
    private DecMoocDigitalDao decMoocDigitalDao;
    @Autowired
    private DecIntentionDao decIntentionDao;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private MaterialExtensionService materialExtensionService;
    @Autowired
    private WriterUserTrendstService writerUserTrendstService;
    @Autowired
    private WriterUserService writerUserService;
    @Autowired
    private DataDictionaryDao dataDictionaryDao;

    @Override
    public Declaration addDeclaration(Declaration declaration) throws CheckedServiceException {
        if (null == declaration) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "参数为空");
        }
        if (null == declaration.getMaterialId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "教材id不能为空");
        }
        if (null == declaration.getUserId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "作家id不能为空");
        }
        declarationDao.addDeclaration(declaration);
        return declaration;
    }

    @Override
    public Integer deleteDeclarationById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "主键为空");
        }
        return declarationDao.deleteDeclarationById(id);
    }

    @Override
    public Integer updateDeclaration(Declaration declaration) throws CheckedServiceException {
        if (null == declaration.getId()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "主键为空");
        }
        return declarationDao.updateDeclaration(declaration);
    }

    @Override
    public Declaration getDeclarationById(Long id) throws CheckedServiceException {
        if (null == id) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "主键为空");
        }
        return declarationDao.getDeclarationById(id);
    }

    @Override
    public List<Declaration> getDeclarationByMaterialId(Long materialId) throws CheckedServiceException {
        if (null == materialId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "教材id为空");
        }
        return declarationDao.getDeclarationByMaterialId(materialId);
    }

    @Override
    public PageResult<DeclarationListVO> pageDeclaration(Integer pageNumber, Integer pageSize, Long materialId,
                                                         String textBookids, String realname, String position, String title, String orgName, Long orgId,
                                                         String unitName, Integer positionType, Integer onlineProgress, Integer offlineProgress, Boolean haveFile,Boolean isSelected,String startCommitDate,String endCommitDate,  String tag,
                                                         HttpServletRequest request)
            throws CheckedServiceException {
        if (null == request.getSession(false)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
                    "会话过期");
        }
        // 获取当前用户
        String sessionId = CookiesUtil.getSessionId(request);
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (null == pmphUser) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
                    "请求用户不存在");
        }


        if (null == materialId && tag==null) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
                    "教材为空");
        }
        Gson gson = new Gson();
        List<Long> bookIds = gson.fromJson(textBookids, new TypeToken<ArrayList<Long>>() {
        }.getType());
        // 拼装复合参数
        Map<String, Object> map = new HashMap<String, Object>();
        if(tag!=null&& tag.equals("WX")){
            map.put("userId",pmphUser.getId());
            Map<String,Object> ma=declarationDao.getMaterialForResolve(map);
            map.put("materialId", ma.get("mymaterials"));
        }else{
            map.put("materialId", "("+materialId+")");
        }
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
        if (null != orgId) {
            map.put("orgId", orgId); // 申报单位
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
        if (null != offlineProgress) { // 0 未收到 // 2 已收到
            map.put("offlineProgress", offlineProgress); // 纸质表进度
        }
        if (null != haveFile) {
            map.put("haveFile", haveFile); // 有无教材大纲
        }
        if (null != isSelected) {
            map.put("isSelected", isSelected); // 是否被遴选中
        }
        if (null != startCommitDate) {
            map.put("startCommitDate", startCommitDate); // 是否被遴选中
        }
        if (null != endCommitDate) {
            map.put("endCommitDate", endCommitDate); // 是否被遴选中
        }
        // 包装参数实体
        PageParameter<Map<String, Object>> pageParameter = new PageParameter<Map<String, Object>>(pageNumber, pageSize,
                map);
        // 返回实体
        PageResult<DeclarationListVO> pageResult = new PageResult<>();
        PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
        // 获取总数
        Integer total = declarationDao.listDeclarationTotal(pageParameter);

        if (null != total && total > 0) {
            List<DeclarationListVO> rows = declarationDao.listDeclaration(pageParameter);
                for (DeclarationListVO row : rows) {
                    HashMap<String, Object> paraMap = new HashMap<>();
                    paraMap.put("declarationId",row.getId());
                    String declarationlCreateDate = declarationDao.findDeclarationCreateDate(paraMap);
                    Date date1 = DateUtil.fomatDate(declarationlCreateDate);
                    Date date = DateUtil.fomatDate("2019-04-12 12:00");
                    if(date1.getTime()>date.getTime()) {
                    String post = row.getPresetPosition().toString();

                    if (post != null) {
                        if (ObjectUtil.isNumber(post)) {
                            post = dataDictionaryDao.getDataDictionaryItemNameByCode(Const.PMPH_POSITION, row.getPresetPosition().toString());
                        }
                    }

                    row.setChooseBooksAndPostions(row.getTextbookName() + "-" + post);

                }
                    String tit = row.getTitle();
                    if (tit != null) {

                        if (ObjectUtil.isNumber(tit)) {
                            tit = dataDictionaryDao.getDataDictionaryItemNameByCode(Const.WRITER_USER_TITLE, row.getTitle().toString());
                        }
                    }
                    row.setTitle(tit);
            }






            pageResult.setRows(rows);
        }
        pageResult.setTotal(total);
        return pageResult;
    }

    @Override
    public Declaration confirmPaperList(Long id, Integer offlineProgress, String sessionId)
            throws CheckedServiceException, IOException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "主键不能为空!");
        }
        if (ObjectUtil.isNull(offlineProgress)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "确认收到纸质表不能为空!");
        }
        // 纸质表审核人id
        PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
        if (ObjectUtil.isNull(pmphUser)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.OBJECT_NOT_FOUND, "审核人为空!");
        }
        Long authUserId = pmphUser.getId();// 纸质表审核人Id为登陆用户ID
        // 获取当前作家用户申报信息
        Declaration declarationCon = declarationDao.getDeclarationById(id);
        // 获取教材
        Material material = materialService.getMaterialById(declarationCon.getMaterialId());
        if (ObjectUtil.isNull(declarationCon)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
                    CheckedExceptionResult.OBJECT_NOT_FOUND, "查询结果为空!");
        }
        declarationCon.setAuthUserId(authUserId); // 纸质表审核人id
        Date date = new Date();
        declarationCon.setPaperDate(new Timestamp(date.getTime())); // 纸质表收到时间
        declarationCon.setOfflineProgress(offlineProgress);
        declarationDao.updateDeclaration(declarationCon);
        WriterUserTrendst writerUserTrendst = new WriterUserTrendst();
        writerUserTrendst.setUserId(declarationCon.getUserId());
        writerUserTrendst.setIsPublic(false);// 自己可见
        writerUserTrendst.setType(8);
        String detail = "";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("title", CheckedExceptionBusiness.MATERIAL);
        if (0 == offlineProgress) {
            map.put("content", "抱歉，您在《" + material.getMaterialName() + "》提交的申报纸质表被退回，请您核对后重新提交。");
        } else {
            map.put("content", "您好，人民卫生出版社已收到您提交《" + material.getMaterialName() + "》的纸质申报表，感谢您的参与，请耐心等待遴选结果。");
        }
        map.put("img", 1);
        detail = new Gson().toJson(map);
        writerUserTrendst.setDetail(detail);
        writerUserTrendst.setCmsContentId(null);
        writerUserTrendst.setBookId(declarationCon.getMaterialId());
        writerUserTrendst.setBookCommentId(null);
        writerUserTrendstService.addWriterUserTrendst(writerUserTrendst);
        Boolean isPass = true;
        if(0 == offlineProgress){
            isPass = false;
        }
        systemMessageService.sendWhenReceiptAudit(declarationCon.getId(), isPass,pmphUser); // 发送系统消息
        return declarationCon;
    }

    @Override
    public Declaration onlineProgress(Long id, Integer onlineProgress, String returnCause,PmphUser pmphUser)
            throws CheckedServiceException, IOException {
        if (ObjectUtil.isNull(id)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "主键不能为空!");
        }
        if (ObjectUtil.isNull(onlineProgress)) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "审核进度不能为空!");
        }
        // 获取当前作家用户申报信息
        Declaration declarationCon = declarationDao.getDeclarationById(id);
        // 获取教材
        Material material = materialService.getMaterialById(declarationCon.getMaterialId());
        // 获取审核进度是3并且已提交但是待审核并且是提交到出版社0
        // 提交出版社，出版社点击通过
        if (3 == onlineProgress.intValue() && 1 == declarationCon.getOnlineProgress()
                && 0 == declarationCon.getOrgId()) {
            declarationCon.setOnlineProgress(onlineProgress);
            declarationDao.updateDeclaration(declarationCon);
            // 添加动态信息
            WriterUserTrendst writerUserTrendst = new WriterUserTrendst();
            writerUserTrendst.setUserId(declarationCon.getUserId());
            writerUserTrendst.setIsPublic(false);// 自己可见
            writerUserTrendst.setType(8);
            String detail = "";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", CheckedExceptionBusiness.MATERIAL);
            map.put("content", "恭喜！您提交的《" + material.getMaterialName() + "》申报表已通过出版社审核。");
            map.put("img", 1);
            detail = new Gson().toJson(map);
            writerUserTrendst.setDetail(detail);
            writerUserTrendst.setCmsContentId(null);
            writerUserTrendst.setBookId(declarationCon.getMaterialId());
            writerUserTrendst.setBookCommentId(null);
            writerUserTrendstService.addWriterUserTrendst(writerUserTrendst);
            // 发送系统消息
            systemMessageService.sendWhenDeclarationFormAudit(declarationCon.getId(), true, returnCause,pmphUser);
            // 获取审核进度是4并且通过或者不通过审核单位并且不是提交到出版社0则被退回给申报单位
            // 提交审核单位，审核单位通过或者不通过，出版社都退回给申报单位操作
        } else if (4 == onlineProgress.intValue() && 0 != declarationCon.getOrgId()) {
			/*List<DecPosition> decPosition = decPositionDao.listDecPositions(id);
			for (DecPosition decPositions : decPosition) {
				Integer chosenPosition = decPositions.getChosenPosition();
				if (null != chosenPosition && chosenPosition.intValue() > 0) {
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
							CheckedExceptionResult.NULL_PARAM, "已遴选职务，不可退回给申报单位!");
				}
			}*/
            declarationCon.setOnlineProgress(onlineProgress);
            if (StringUtil.strLength(returnCause) > 100) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
                        "最多只能输入100个字符，请重新输入!");
            }
            declarationCon.setReturnCause(returnCause);
            declarationDao.updateDeclaration(declarationCon);
            // 添加动态信息
			/*WriterUserTrendst writerUserTrendst = new WriterUserTrendst();
			writerUserTrendst.setUserId(declarationCon.getUserId());
			writerUserTrendst.setIsPublic(false);// 自己可见
			writerUserTrendst.setType(8);
			String detail = "";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", CheckedExceptionBusiness.MATERIAL);
			map.put("content", "抱歉，贵校老师" + declarationCon.getRealname() + "提交的《" + material.getMaterialName()
					+ "》申报表被出版社退回，退回原因：" + returnCause + "，请贵校核对后重试。");
			map.put("img", 2);
			detail = new Gson().toJson(map);
			writerUserTrendst.setDetail(detail);
			writerUserTrendst.setCmsContentId(null);
			writerUserTrendst.setBookId(declarationCon.getMaterialId());
			writerUserTrendst.setBookCommentId(null);
			writerUserTrendstService.addWriterUserTrendst(writerUserTrendst);*/
            // 发送系统消息
            systemMessageService.sendWhenDeclarationFormAuditToOrgUser(declarationCon.getId(), false, returnCause, onlineProgress,pmphUser);
            // 获取审核进度是5并且通过或者不通过审核单位并且不是提交到出版社0则被退回给个人
            // 提交审核单位，审核单位通过或者不通过，出版社都可以退回给个人操作
        } else if (5 == onlineProgress.intValue() && 0 != declarationCon.getOrgId()) {
			/*List<DecPosition> decPosition = decPositionDao.listDecPositions(id);
			for (DecPosition decPositions : decPosition) {
				Integer chosenPosition = decPositions.getChosenPosition();
				if (null != chosenPosition && chosenPosition.intValue() > 0) {
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
							CheckedExceptionResult.NULL_PARAM, "已遴选职务，不可退回给个人!");
				}
			}*/
            declarationCon.setOnlineProgress(onlineProgress);
            if (StringUtil.strLength(returnCause) > 100) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
                        "最多只能输入100个字符，请重新输入!");
            }
            declarationCon.setReturnCause(returnCause);
            declarationDao.updateDeclaration(declarationCon);
            // 添加动态信息
            WriterUserTrendst writerUserTrendst = new WriterUserTrendst();
            writerUserTrendst.setUserId(declarationCon.getUserId());
            writerUserTrendst.setIsPublic(false);// 自己可见
            writerUserTrendst.setType(8);
            String detail = "";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", CheckedExceptionBusiness.MATERIAL);
            map.put("content", "抱歉，您提交的《" + material.getMaterialName() + "》申报表被出版社退回，"
                    + "退回原因：" + returnCause + "，请您核对后重新提交。");
            map.put("img", 2);
            detail = new Gson().toJson(map);
            writerUserTrendst.setDetail(detail);
            writerUserTrendst.setCmsContentId(null);
            writerUserTrendst.setBookId(declarationCon.getMaterialId());
            writerUserTrendst.setBookCommentId(null);
            writerUserTrendstService.addWriterUserTrendst(writerUserTrendst);
            // 发送系统消息
            systemMessageService.sendWhenDeclarationFormAuditToOrgUser(declarationCon.getId(), false, returnCause, onlineProgress,pmphUser);
            // 获取审核进度是5并且机构id为出版社0则被退回给个人
            // 提交到出版社，出版社退回给个人操作
        } else if (5 == onlineProgress.intValue() && 0 == declarationCon.getOrgId()) {
			/*List<DecPosition> decPosition = decPositionDao.listDecPositions(id);
			for (DecPosition decPositions : decPosition) {
				Integer chosenPosition = decPositions.getChosenPosition();
				if (null != chosenPosition && chosenPosition.intValue() > 0) {
					throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL,
							CheckedExceptionResult.NULL_PARAM, "已遴选职务，不可退回给个人!");
				}
			}*/
            declarationCon.setOnlineProgress(onlineProgress);
            if (StringUtil.strLength(returnCause) > 100) {
                throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
                        "最多只能输入100个字符，请重新输入!");
            }
            declarationCon.setReturnCause(returnCause);
            declarationDao.updateDeclaration(declarationCon);
            // 添加动态信息
            WriterUserTrendst writerUserTrendst = new WriterUserTrendst();
            writerUserTrendst.setUserId(declarationCon.getUserId());
            writerUserTrendst.setIsPublic(false);// 自己可见
            writerUserTrendst.setType(8);
            String detail = "";
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", CheckedExceptionBusiness.MATERIAL);
            map.put("content", "抱歉，您提交的《" + material.getMaterialName() + "》申报表被出版社退回，"
                    + "退回原因：" + returnCause + "，请您核对后重新提交。");
            map.put("img", 2);
            detail = new Gson().toJson(map);
            writerUserTrendst.setDetail(detail);
            writerUserTrendst.setCmsContentId(null);
            writerUserTrendst.setBookId(declarationCon.getMaterialId());
            writerUserTrendst.setBookCommentId(null);
            writerUserTrendstService.addWriterUserTrendst(writerUserTrendst);
            // 发送系统消息
            systemMessageService.sendWhenDeclarationFormAudit(declarationCon.getId(), false, returnCause,pmphUser);
        }

        //撤销某(人)申报表的所有遴选，dec_positiond的choosePosition设为0，dec_pisition_published相应删除
        if(5 == onlineProgress.intValue()||4 == onlineProgress.intValue()||2 == onlineProgress.intValue()){
            List<DecPosition> decPosition = decPositionDao.listDecPositions(id);
            int dismissCount = decPositionDao.dismissPositionByDeclarationId(id);
        }


        return declarationCon;
    }

    @Override
    public ApplicationVO exportExcel(Long declarationId) {
        ApplicationVO applicationVO = new ApplicationVO(); // 专家信息显示实体
        // 作家申报
        Map<String, Object> params = new HashMap<>();
        params.put("declarationId",declarationId);
        List<DecPositionDisplayVO> decPositionList = decPositionDao.listDecPositionsOrBook(declarationId);
        for (DecPositionDisplayVO decPositions : decPositionList) {
            String syllabusId = decPositions.getSyllabusId();
            if (StringUtil.notEmpty(syllabusId)) {
                String syllabusIds = RouteUtil.MONGODB_FILE + syllabusId; // 下载路径
                decPositions.setSyllabusId(syllabusIds);
            }

            String declarationlCreateDate = declarationDao.findDeclarationCreateDate(params);
            Date date1 = DateUtil.fomatDate(declarationlCreateDate);
            Date date = DateUtil.fomatDate("2019-04-12 12:00");

            if(date1.getTime()>date.getTime()) {

            String dataDictionaryItemNameByCode = dataDictionaryDao.getDataDictionaryItemNameByCode(Const.PMPH_POSITION, decPositions.getPresetPosition().toString());
            decPositions.setShowPosition(dataDictionaryItemNameByCode);
            if (decPositions.getChosenPosition() != 0) {
                String dataDictionaryItemNameByCode2 = dataDictionaryDao.getDataDictionaryItemNameByCode(Const.PMPH_POSITION, decPositions.getChosenPosition().toString());
                decPositions.setShowChosenPosition(dataDictionaryItemNameByCode2);
            }

            }else{

                switch (decPositions.getPresetPosition()) {
                    case 1:
                        decPositions.setShowPosition("编委");
                        break;
                    case 2:
                        decPositions.setShowPosition("副主编");
                        break;
                    case 3:
                        decPositions.setShowPosition("副主编,编委");
                        break;
                    case 4:
                        decPositions.setShowPosition("主编");
                        break;
                    case 5:
                        decPositions.setShowPosition("主编,编委");
                        break;
                    case 6:
                        decPositions.setShowPosition("主编,副主编");
                        break;
                    case 7:
                        decPositions.setShowPosition("主编,副主编,编委");
                        break;
                    case 8:
                        decPositions.setShowPosition("数字编委");
                        break;
                    case 9:
                        decPositions.setShowPosition("编委,数字编委");
                        break;
                    case 10:
                        decPositions.setShowPosition("副主编,数字编委");
                        break;
                    case 11:
                        decPositions.setShowPosition("副主编,编委,数字编委");
                        break;
                    case 12:
                        decPositions.setShowPosition("主编,数字编委");
                        break;
                    case 13:
                        decPositions.setShowPosition("主编,编委,数字编委");
                        break;
                    case 14:
                        decPositions.setShowPosition("主编,副主编,数字编委");
                        break;
                    case 15:
                        decPositions.setShowPosition("主编,副主编,编委,数字编委");
                        break;
                    default:
                        break;
                }
                if (decPositions.getChosenPosition() != 0) {
                    switch (decPositions.getChosenPosition()) {
                        case 1:
                            decPositions.setShowChosenPosition("编委");
                            break;
                        case 2:
                            decPositions.setShowChosenPosition("副主编");
                            break;
                        case 3:
                            decPositions.setShowChosenPosition("副主编,编委");
                            break;
                        case 4:
                            decPositions.setShowChosenPosition("主编");
                            break;
                        case 5:
                            decPositions.setShowChosenPosition("主编,编委");
                            break;
                        case 6:
                            decPositions.setShowChosenPosition("主编,副主编");
                            break;
                        case 7:
                            decPositions.setShowChosenPosition("主编,副主编,编委");
                            break;
                        case 8:
                            decPositions.setShowChosenPosition("数字编委");
                            break;
                        case 9:
                            decPositions.setShowChosenPosition("编委,数字编委");
                            break;
                        case 10:
                            decPositions.setShowChosenPosition("副主编,数字编委");
                            break;
                        case 11:
                            decPositions.setShowChosenPosition("副主编,编委,数字编委");
                            break;
                        case 12:
                            decPositions.setShowChosenPosition("主编,数字编委");
                            break;
                        case 13:
                            decPositions.setShowChosenPosition("主编,编委,数字编委");
                            break;
                        case 14:
                            decPositions.setShowChosenPosition("主编,副主编,数字编委");
                            break;
                        case 15:
                            decPositions.setShowChosenPosition("主编,副主编,编委,数字编委");
                            break;
                        default:
                            break;
                    }
                }


            }
        }
        // 作家遴选
        List<DecPositionPublishedVO> decPositionPublishedVOs = decPositionPublishedDao
                .listDecPositionDisplayOrPosition(declarationId);

        for (DecPositionPublishedVO decPositionPublished : decPositionPublishedVOs) {

            String materialCreateDate = declarationDao.findDeclarationCreateDate(params);
            Date date1 = DateUtil.fomatDate(materialCreateDate);
            Date date = DateUtil.fomatDate("2019-04-12");

            if(date1.getTime()>date.getTime()) {

                if (decPositionPublished.getChosenPosition() != 0) {
                    String dataDictionaryItemNameByCode2 = dataDictionaryDao.getDataDictionaryItemNameByCode(Const.PMPH_POSITION, decPositionPublished.getChosenPosition().toString());
                    decPositionPublished.setShowChosenPosition(dataDictionaryItemNameByCode2);
                } else {
                switch (decPositionPublished.getChosenPosition()) {
                    case 1:
                        decPositionPublished.setShowChosenPosition("编委");
                        break;
                    case 2:
                        decPositionPublished.setShowChosenPosition("副主编");
                        break;
                    case 3:
                        decPositionPublished.setShowChosenPosition("副主编,编委");
                        break;
                    case 4:
                        decPositionPublished.setShowChosenPosition("主编");
                        break;
                    case 5:
                        decPositionPublished.setShowChosenPosition("主编,编委");
                        break;
                    case 6:
                        decPositionPublished.setShowChosenPosition("主编,副主编");
                        break;
                    case 7:
                        decPositionPublished.setShowChosenPosition("主编,副主编,编委");
                        break;
                    case 8:
                        decPositionPublished.setShowChosenPosition("数字编委");
                        break;
                    case 9:
                        decPositionPublished.setShowChosenPosition("编委,数字编委");
                        break;
                    case 10:
                        decPositionPublished.setShowChosenPosition("副主编,数字编委");
                        break;
                    case 11:
                        decPositionPublished.setShowChosenPosition("副主编,编委,数字编委");
                        break;
                    case 12:
                        decPositionPublished.setShowChosenPosition("主编,数字编委");
                        break;
                    case 13:
                        decPositionPublished.setShowChosenPosition("主编,编委,数字编委");
                        break;
                    case 14:
                        decPositionPublished.setShowChosenPosition("主编,副主编,数字编委");
                        break;
                    case 15:
                        decPositionPublished.setShowChosenPosition("主编,副主编,编委,数字编委");
                        break;
                    default:
                        break;
                }
            }
            }
        }
        // 作家申报表
        DeclarationOrDisplayVO declaration = declarationDao.getDeclarationByIdOrOrgName(declarationId);
        WriterUser user = writerUserService.get(declaration.getUserId());
        String title = dataDictionaryDao.getDataDictionaryItemNameByCode(Const.WRITER_USER_TITLE, declaration.getTitle().toString());
        declaration.setTitle(title);
        if (user != null) {
            declaration.setUsername(user.getUsername());

        }
        // 作家学习经历
        List<DecEduExp> decEduExpList = decEduExpDao.getListDecEduExpByDeclarationId(declarationId);
        // 作家工作经历
        List<DecWorkExp> decWorkExpList = decWorkExpDao.getListDecWorkExpByDeclarationId(declarationId);
        // 作家教学经历
        List<DecTeachExp> decTeachExpList = decTeachExpDao.getListDecTeachExpByDeclarationId(declarationId);
        // 作家个人成就
        DecAchievement decAchievement = decAchievementDao.getDecAchievementByDeclarationId(declarationId);
        // 作家兼职学术
        List<DecAcade> decAcadeList = decAcadeDao.getListDecAcadeByDeclarationId(declarationId);
        // 作家本套上版教材参编情况
        List<DecLastPosition> decLastPositionList = decLastPositionDao
                .getListDecLastPositionByDeclarationId(declarationId);
        // 作家主编国家级规划教材情况
        List<DecNationalPlan> decNationalPlanList = decNationalPlanDao
                .getListDecNationalPlanByDeclarationId(declarationId);
        // 人卫社教材编写情况表
        List<DecTextbookPmph> decTextbookPmphList = decTextbookPmphDao
                .getListDecTextbookPmphByDeclarationId(declarationId);
        // 其他社教材编写情况
        List<DecTextbook> decTextbookList = decTextbookDao.getListDecTextbookByDeclarationId(declarationId);
        // 参加人卫慕课、数字教材编写情况表
        DecMoocDigital decMoocDigital = decMoocDigitalDao.getDecMoocDigitalByDeclarationId(declarationId);
        // 作家精品课程建设情况
        List<DecCourseConstruction> decCourseConstruction = decCourseConstructionDao
                .getDecCourseConstructionByDeclarationId(declarationId);
        // 作家科研情况
        List<DecResearch> decResearchList = decResearchDao.getListDecResearchByDeclarationId(declarationId);
        // 主编学术专著情况
        List<DecMonograph> decMonographList = decMonographDao.getListDecMonographByDeclarationId(declarationId);
        // 出版行业获奖情况
        List<DecPublishReward> decPublishRewardList = decPublishRewardDao
                .getListDecPublishRewardByDeclarationId(declarationId);
        // SCI论文投稿及影响因子情况
        List<DecSci> decSciList = decSciDao.getListDecSciByDeclarationId(declarationId);
        // 临床医学获奖情况
        List<DecClinicalReward> decClinicalRewardList = decClinicalRewardDao
                .getListDecClinicalRewardByDeclarationId(declarationId);
        // 学术荣誉授予情况
        List<DecAcadeReward> decAcadeRewardList = decAcadeRewardDao.getListDecAcadeRewardByDeclarationId(declarationId);
        // 作家扩展项
        List<DecExtensionVO> decExtensionList = decExtensionDao.getListDecExtensionByDeclarationId(declarationId);
        // 编写内容意向表
        DecIntention decIntention = decIntentionDao.getDecIntentionByDeclarationId(declarationId);
        // 是否选择必填
        Material material = materialService.getMaterialById(declaration.getMaterialId());
        // 把查询出来的信息添加进applicationVO
        applicationVO.setDecPositionList(decPositionList);
        applicationVO.setDecPositionPublishedVOs(decPositionPublishedVOs);
        applicationVO.setDeclaration(declaration);
        applicationVO.setDecEduExpList(decEduExpList);
        applicationVO.setDecWorkExpList(decWorkExpList);
        applicationVO.setDecTeachExpList(decTeachExpList);
        applicationVO.setDecAchievement(decAchievement);
        applicationVO.setDecAcadeList(decAcadeList);
        applicationVO.setDecLastPositionList(decLastPositionList);
        applicationVO.setDecNationalPlanList(decNationalPlanList);
        applicationVO.setDecTextbookPmphList(decTextbookPmphList);
        applicationVO.setDecTextbookList(decTextbookList);
        applicationVO.setDecMoocDigital(decMoocDigital);
        applicationVO.setDecCourseConstruction(decCourseConstruction);
        applicationVO.setDecResearchList(decResearchList);
        applicationVO.setDecMonographList(decMonographList);
        applicationVO.setDecPublishRewardList(decPublishRewardList);
        applicationVO.setDecSciList(decSciList);
        applicationVO.setDecClinicalRewardList(decClinicalRewardList);
        applicationVO.setDecAcadeRewardList(decAcadeRewardList);
        applicationVO.setDecExtensionList(decExtensionList);
        applicationVO.setDecIntention(decIntention);
        applicationVO.setMaterial(material);
        return applicationVO;
    }

    @Override
    public List<DeclarationEtcBO> getDeclarationEtcBOs(Long materialId) {
        List<DeclarationEtcBO> declarationEtcBOs = new ArrayList<>(20);
        List<Declaration> declarations = declarationDao.getDeclarationByMaterialId(materialId);
        if (CollectionUtil.isEmpty(declarations)) {
            return null;
        }
        int count = 1;
        for (Declaration declaration : declarations) {
            // 学习经历
            ArrayList<DecEduExp> decEduExps = (ArrayList<DecEduExp>) decEduExpDao
                    .getListDecEduExpByDeclarationId(declaration.getId());
            // 工作经历
            ArrayList<DecWorkExp> decWorkExps = (ArrayList<DecWorkExp>) decWorkExpDao
                    .getListDecWorkExpByDeclarationId(declaration.getId());
            // 教学经历
            ArrayList<DecTeachExp> decTeachExps = (ArrayList<DecTeachExp>) decTeachExpDao
                    .getListDecTeachExpByDeclarationId(declaration.getId());
            // 兼职学术
            ArrayList<DecAcade> decAcades = (ArrayList<DecAcade>) decAcadeDao
                    .getListDecAcadeByDeclarationId(declaration.getId());
            // 上套教材
            ArrayList<DecLastPosition> decLastPositions = (ArrayList<DecLastPosition>) decLastPositionDao
                    .getListDecLastPositionByDeclarationId(declaration.getId());
            // 精品课程建设情况
            ArrayList<DecCourseConstruction> decCourseConstructions = (ArrayList<DecCourseConstruction>) decCourseConstructionDao
                    .getDecCourseConstructionByDeclarationId(declaration.getId());
            // 主编国家级规划
            ArrayList<DecNationalPlan> decNationalPlans = (ArrayList<DecNationalPlan>) decNationalPlanDao
                    .getListDecNationalPlanByDeclarationId(declaration.getId());
            // 教材编写
            ArrayList<DecTextbook> decTextbooks = (ArrayList<DecTextbook>) decTextbookDao
                    .getListDecTextbookByDeclarationId(declaration.getId());
            // 作家科研
            ArrayList<DecResearch> decResearchs = (ArrayList<DecResearch>) decResearchDao
                    .getListDecResearchByDeclarationId(declaration.getId());
            /*
             * ArrayList<DecAchievement> decAchievements = (ArrayList<DecAchievement>)
             * decAchievementDao .getDecAchievementByDeclarationId(declaration.getId());
             */
            DeclarationEtcBO declarationEtcBO = new DeclarationEtcBO();
            declarationEtcBO.setRealname("欧阳望月".concat(String.valueOf(count)));
            declarationEtcBO.setUsername("Smith");
            // declarationEtcBO.setTextbookName("人体解剖学与组织胚胎学");
            // declarationEtcBO.setPresetPosition("副主编");
            declarationEtcBO.setChosenOrgName("人民卫生出版社");
            declarationEtcBO.setSex("女");
            declarationEtcBO.setBirthday("1975年11月22日");
            declarationEtcBO.setAddress("浙江省金华市婺城区婺州街1188号");
            declarationEtcBO.setExperience(23);
            declarationEtcBO.setOrgName("金华职业技术学院医学院");
            declarationEtcBO.setPosition("教师");
            declarationEtcBO.setTitle("教授");
            declarationEtcBO.setPostcode("321017");
            declarationEtcBO.setHandphone("13857989881");
            declarationEtcBO.setEmail("test10001test@163.com");
            declarationEtcBO.setFax("01065930013");
            declarationEtcBO.setTelephone("010-65930013");
            declarationEtcBO.setDecEduExps(decEduExps);
            declarationEtcBO.setDecWorkExps(decWorkExps);
            declarationEtcBO.setDecTeachExps(decTeachExps);
            declarationEtcBO.setDecAcades(decAcades);
            declarationEtcBO.setDecLastPositions(decLastPositions);
            declarationEtcBO.setDecCourseConstructions(decCourseConstructions);
            declarationEtcBO.setDecNationalPlans(decNationalPlans);
            declarationEtcBO.setDecTextbooks(decTextbooks);
            declarationEtcBO.setDecResearchs(decResearchs);
            // declarationEtcBO.setDecAchievement(decAchievements.get(0));
            declarationEtcBOs.add(declarationEtcBO);
            count++;
            if (count > 21) {
                break;
            }
        }
        return declarationEtcBOs;
    }

    @Override
    public List<DeclarationEtcBO> declarationEtcBO(Long materialId, String textBookids, String realname,
                                                   String position, String title, String orgName, String unitName, Integer positionType,
                                                   Integer onlineProgress, Integer offlineProgress,Boolean isSelect)
            throws CheckedServiceException, IllegalArgumentException, IllegalAccessException {
        List<DeclarationEtcBO> declarationEtcBOs = new ArrayList<>();
        Gson gson = new Gson();

        List<Long> bookIds = gson.fromJson("["+textBookids+"]", new TypeToken<ArrayList<Long>>() {
        }.getType());
        List<DeclarationOrDisplayVO> declarationOrDisplayVOs = declarationDao.getDeclarationOrDisplayVOByMaterialId(
                materialId, bookIds, realname, position, title, orgName, unitName, positionType, onlineProgress,
                offlineProgress,isSelect);
        List<Long> decIds = new ArrayList<>();
        try {
            for (DeclarationOrDisplayVO declarationOrDisplayVO : declarationOrDisplayVOs) {

                HashMap<String, Object> paraMap = new HashMap<>();
                paraMap.put("declarationId",declarationOrDisplayVO.getId());
                String declarationlCreateDate = declarationDao.findDeclarationCreateDate(paraMap);
                Date date1 = DateUtil.fomatDate(declarationlCreateDate);
                Date date = DateUtil.fomatDate("2019-04-12 12:00");
                if(date1.getTime()>date.getTime()) {
                    String post = dataDictionaryDao.getDataDictionaryItemNameByCode(Const.PMPH_POSITION, declarationOrDisplayVO.getPresetPosition().toString());
                    declarationOrDisplayVO.setPresetPosition(post);

                }else{
                    switch (Integer.parseInt(declarationOrDisplayVO.getPresetPosition())) {
                        case 1:
                            declarationOrDisplayVO.setPresetPosition("编委");
                            break;
                        case 2:
                            declarationOrDisplayVO.setPresetPosition("副主编");
                            break;
                        case 3:
                            declarationOrDisplayVO.setPresetPosition("副主编,编委");
                            break;
                        case 4:
                            declarationOrDisplayVO.setPresetPosition("主编");
                            break;
                        case 5:
                            declarationOrDisplayVO.setPresetPosition("主编,编委");
                            break;
                        case 6:
                            declarationOrDisplayVO.setPresetPosition("主编,副主编");
                            break;
                        case 7:
                            declarationOrDisplayVO.setPresetPosition("主编,副主编,编委");
                            break;
                        case 8:
                            declarationOrDisplayVO.setPresetPosition("数字编委");
                            break;
                        case 9:
                            declarationOrDisplayVO.setPresetPosition("编委,数字编委");
                            break;
                        case 10:
                            declarationOrDisplayVO.setPresetPosition("副主编,数字编委");
                            break;
                        case 11:
                            declarationOrDisplayVO.setPresetPosition("副主编,编委,数字编委");
                            break;
                        case 12:
                            declarationOrDisplayVO.setPresetPosition("主编,数字编委");
                            break;
                        case 13:
                            declarationOrDisplayVO.setPresetPosition("主编,编委,数字编委");
                            break;
                        case 14:
                            declarationOrDisplayVO.setPresetPosition("主编,副主编,数字编委");
                            break;
                        case 15:
                            declarationOrDisplayVO.setPresetPosition("主编,副主编,编委,数字编委");
                            break;
                        default:
                            break;
                    }
                }

                decIds.add(declarationOrDisplayVO.getId());
                String gtitle = dataDictionaryDao.getDataDictionaryItemNameByCode(Const.WRITER_USER_TITLE, declarationOrDisplayVO.getTitle().toString());
                declarationOrDisplayVO.setTitle(gtitle);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        Material material = materialService.getMaterialById(materialId);
        // 学习经历
        ArrayList<DecEduExp> decEduExps = (ArrayList<DecEduExp>) decEduExpDao.getListDecEduExpByDeclarationIds(decIds);
        // 工作经历
        ArrayList<DecWorkExp> decWorkExps = (ArrayList<DecWorkExp>) decWorkExpDao
                .getListDecWorkExpByDeclarationIds(decIds);
        // 教学经历
        ArrayList<DecTeachExp> decTeachExps = (ArrayList<DecTeachExp>) decTeachExpDao
                .getListDecTeachExpByDeclarationIds(decIds);
        // 兼职学术
        ArrayList<DecAcade> decAcades = (ArrayList<DecAcade>) decAcadeDao.getListDecAcadeByDeclarationIds(decIds);
        // 个人成就
        ArrayList<DecAchievement> decAchievements = (ArrayList<DecAchievement>) decAchievementDao
                .getDecAchievementByDeclarationIds(decIds);
        // 本套上版教材参编情况
        ArrayList<DecLastPosition> decLastPositions = (ArrayList<DecLastPosition>) decLastPositionDao
                .getListDecLastPositionByDeclarationIds(decIds);
        // 精品课程建设情况
        ArrayList<DecCourseConstruction> decCourseConstructions = (ArrayList<DecCourseConstruction>) decCourseConstructionDao
                .getDecCourseConstructionByDeclarationIds(decIds);
        // 主编国家级规划
        ArrayList<DecNationalPlan> decNationalPlans = (ArrayList<DecNationalPlan>) decNationalPlanDao
                .getListDecNationalPlanByDeclarationIds(decIds);
        // 人卫社教材编写情况表
        ArrayList<DecTextbookPmph> decTextbookPmphs = (ArrayList<DecTextbookPmph>) decTextbookPmphDao
                .getListDecTextbookPmphByDeclarationIds(decIds);
        // 其他社教材编写情况
        ArrayList<DecTextbook> decTextbooks = (ArrayList<DecTextbook>) decTextbookDao
                .getListDecTextbookByDeclarationIds(decIds);
        // 参加人卫慕课、数字教材编写情况表
        ArrayList<DecMoocDigital> decMoocDigitals = (ArrayList<DecMoocDigital>) decMoocDigitalDao
                .getDecMoocDigitalByDeclarationIds(decIds);
        // 作家科研
        ArrayList<DecResearch> decResearchs = (ArrayList<DecResearch>) decResearchDao
                .getListDecResearchByDeclarationIds(decIds);
        // 主编学术专著情况
        ArrayList<DecMonograph> decMonographList = (ArrayList<DecMonograph>) decMonographDao
                .getListDecMonographByDeclarationIds(decIds);
        // 出版行业获奖情况
        ArrayList<DecPublishReward> decPublishRewardList = (ArrayList<DecPublishReward>) decPublishRewardDao
                .getListDecPublishRewardByDeclarationIds(decIds);
        // SCI论文投稿及影响因子情况
        ArrayList<DecSci> decSciList = (ArrayList<DecSci>) decSciDao.getListDecSciByDeclarationIds(decIds);
        // 临床医学获奖情况
        ArrayList<DecClinicalReward> decClinicalRewardList = (ArrayList<DecClinicalReward>) decClinicalRewardDao
                .getListDecClinicalRewardByDeclarationIds(decIds);
        // 学术荣誉授予情况
        ArrayList<DecAcadeReward> decAcadeRewardList = (ArrayList<DecAcadeReward>) decAcadeRewardDao
                .getListDecAcadeRewardByDeclarationIds(decIds);
        // 作家扩展项
        ArrayList<DecExtensionVO> decExtensionVOs = (ArrayList<DecExtensionVO>) decExtensionDao
                .getListDecExtensionVOByDeclarationIds(decIds);
        // 编写内容意向表
        ArrayList<DecIntention> decIntentions = (ArrayList<DecIntention>) decIntentionDao
                .getDecIntentionByDeclarationIds(decIds);
        try{

            for (DeclarationOrDisplayVO declarationOrDisplayVO : declarationOrDisplayVOs) {
                if(StringUtil.isEmpty(declarationOrDisplayVO.getTextbookName())){
                    throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
                            "书籍名称为空");
                }
                String strOnlineProgress = "";// 审核进度
                String strOfflineProgress = "";// 纸质表进度
                String sex = "";// 性别
                String idtype = "";// 证件类别
                String degree = "";// 学历
                switch (declarationOrDisplayVO.getIdtype()) {
                    case 0:
                        idtype = "身份证";
                        break;
                    case 1:
                        idtype = "护照";
                        break;
                    case 2:
                        idtype = "军官证";
                        break;
                    default:
                        idtype = "出现错误";
                        break;
                }
                switch (declarationOrDisplayVO.getDegree()) {
                    case 0:
                        degree = "无";
                        break;
                    case 1:
                        degree = "专科";
                        break;
                    case 2:
                        degree = "本科";
                        break;
                    case 3:
                        degree = "硕士";
                        break;
                    case 4:
                        degree = "博士";
                        break;
                    default:
                        degree = "出现错误";
                        break;
                }
                switch (declarationOrDisplayVO.getOnlineProgress()) {
                    case 0:
                        strOnlineProgress = "未提交";
                        break;
                    case 1:
                        strOnlineProgress = "已提交";
                        break;
                    case 2:
                        strOnlineProgress = "被退回";
                        break;
                    case 3:
                        strOnlineProgress = "已通过";
                        break;
                    default:
                        strOnlineProgress = "出现错误";
                        break;
                }
                switch (declarationOrDisplayVO.getSex()) {
                    case 0:
                        sex = "保密";
                        break;
                    case 1:
                        sex = "男";
                        break;
                    case 2:
                        sex = "女";
                        break;
                    default:
                        sex = "出现错误";
                        break;
                }
                switch (declarationOrDisplayVO.getOfflineProgress()) {
                    case 0:
                        strOfflineProgress = "未收到";
                        break;
                    case 1:
                        strOfflineProgress = "被退回";
                        break;
                    case 2:
                        strOfflineProgress = "已收到";
                        break;
                    default:
                        strOfflineProgress = "出现错误";
                        break;
                }
                String birthday = "";
                if (null != declarationOrDisplayVO.getBirthday()) {
                    birthday = DateUtil.date2Str(declarationOrDisplayVO.getBirthday(), "yyyy-MM-dd");
                }

                if (null == declarationOrDisplayVO.getPosition() || "".equals(declarationOrDisplayVO.getPosition())) {
                    declarationOrDisplayVO.setPosition("无");
                }
                if (StringUtil.isEmpty(declarationOrDisplayVO.getTextbookName())) {
                    declarationOrDisplayVO.setTextbookName("");
                }
                if (StringUtil.isEmpty(declarationOrDisplayVO.getPresetPosition())) {
                    declarationOrDisplayVO.setPresetPosition("");
                }
                // 学习经历
                List<DecEduExp> decEduExp = new ArrayList<>();
                for (DecEduExp exp : decEduExps) {
                    if (exp.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        decEduExp.add(exp);
                    }
                }
                // 工作经历
                List<DecWorkExp> decWorkExp = new ArrayList<>();
                for (DecWorkExp workExp : decWorkExps) {
                    if (workExp.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        decWorkExp.add(workExp);
                    }
                }
                // 教学经历
                List<DecTeachExp> decTeachExp = new ArrayList<>();
                for (DecTeachExp teachExp : decTeachExps) {
                    if (teachExp.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        decTeachExp.add(teachExp);
                    }
                }
                // 兼职学术
                List<DecAcade> decAcade = new ArrayList<>();
                for (DecAcade acade : decAcades) {
                    if (acade.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        decAcade.add(acade);
                    }
                }
                // 个人成就
                DecAchievement decAchievement = new DecAchievement();
                for (DecAchievement achievement : decAchievements) {
                    if (achievement.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        decAchievement = achievement;
                        break;
                    }
                }
                // 上套教材
                List<DecLastPosition> decLastPosition = new ArrayList<>();
                for (DecLastPosition lastPosition : decLastPositions) {
                    if (lastPosition.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        decLastPosition.add(lastPosition);
                    }
                }
                // 精品课程建设情况
                List<DecCourseConstruction> decCourseConstruction = new ArrayList<>();
                for (DecCourseConstruction construction : decCourseConstructions) {
                    if (construction.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        decCourseConstruction.add(construction);
                    }
                }
                // 主编国家级规划
                List<DecNationalPlan> decNationalPlan = new ArrayList<>();
                for (DecNationalPlan plan : decNationalPlans) {
                    if (plan.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        decNationalPlan.add(plan);
                    }
                }
                // 人卫社教材编写
                List<DecTextbookPmph> decTextbookPmph = new ArrayList<>();
                for (DecTextbookPmph textbookPmph : decTextbookPmphs) {
                    if (textbookPmph.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        decTextbookPmph.add(textbookPmph);
                    }
                }
                // 其他社教材编写
                List<DecTextbook> decTextbook = new ArrayList<>();
                for (DecTextbook textbook : decTextbooks) {
                    if (textbook.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        decTextbook.add(textbook);
                    }
                }
                // 参加人卫慕课、数字教材编写情况表
                DecMoocDigital decMoocDigital = new DecMoocDigital();
                for (DecMoocDigital moocDigital : decMoocDigitals) {
                    if (moocDigital.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        decMoocDigital = moocDigital;
                        break;
                    }
                }
                // 作家科研
                List<DecResearch> decResearch = new ArrayList<>();
                for (DecResearch research : decResearchs) {
                    if (research.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        decResearch.add(research);
                    }
                }

                // 主编学术专著情况
                List<DecMonograph> monographs = new ArrayList<>();
                for (DecMonograph monograph : decMonographList) {
                    if (monograph.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        monographs.add(monograph);
                    }
                }
                // 出版行业获奖情况
                List<DecPublishReward> publishRewards = new ArrayList<>();
                for (DecPublishReward publishReward : decPublishRewardList) {
                    if (publishReward.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        publishRewards.add(publishReward);
                    }
                }
                // SCI论文投稿及影响因子情况
                List<DecSci> scis = new ArrayList<>();
                for (DecSci sci : decSciList) {
                    if (sci.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        scis.add(sci);
                    }
                }
                // 临床医学获奖情况
                List<DecClinicalReward> clinicalRewards = new ArrayList<>();
                for (DecClinicalReward clinicalReward : decClinicalRewardList) {
                    if (clinicalReward.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        clinicalRewards.add(clinicalReward);
                    }
                }
                // 学术荣誉授予情况
                List<DecAcadeReward> acadeRewards = new ArrayList<>();
                for (DecAcadeReward acadeReward : decAcadeRewardList) {
                    if (acadeReward.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        acadeRewards.add(acadeReward);
                    }
                }
                // 编写内容意向表
                DecIntention decIntention = new DecIntention();
                for (DecIntention intention : decIntentions) {
                    if (intention.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        decIntention = intention;
                        break;
                    }
                }
                // 作家扩展项
                List<DecExtensionVO> extensionVOs = new ArrayList<>();
                for (DecExtensionVO extensionVO : decExtensionVOs) {
                    if (extensionVO.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                        extensionVOs.add(extensionVO);
                    }
                }
                String isDispensed = declarationOrDisplayVO.getIsDispensed() ? "是" : "否";
                String isUtec = declarationOrDisplayVO.getIsUtec() ? "是" : "否";
                String textbookName = declarationOrDisplayVO.getTextbookName() + "第"
                        + declarationOrDisplayVO.getTextbookRound() + "版";
                DeclarationEtcBO declarationEtcBO = new DeclarationEtcBO(declarationOrDisplayVO.getRealname(),
                        declarationOrDisplayVO.getUsername(), sex, birthday, declarationOrDisplayVO.getExperience(),DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss",declarationOrDisplayVO.getCommitDate()),
                        declarationOrDisplayVO.getOrgName(), declarationOrDisplayVO.getPosition(),
                        declarationOrDisplayVO.getTitle(), declarationOrDisplayVO.getAddress(),
                        declarationOrDisplayVO.getPostcode(), declarationOrDisplayVO.getTelephone(),
                        declarationOrDisplayVO.getFax(), declarationOrDisplayVO.getHandphone(), degree,
                        declarationOrDisplayVO.getEmail(), idtype, declarationOrDisplayVO.getIdcard(),
                        declarationOrDisplayVO.getExpertise(), isDispensed, isUtec, strOnlineProgress, strOfflineProgress,
                        declarationOrDisplayVO.getOrgNameOne(), (ArrayList<DecEduExp>) decEduExp,
                        (ArrayList<DecWorkExp>) decWorkExp, (ArrayList<DecTeachExp>) decTeachExp, decAchievement,
                        (ArrayList<DecAcade>) decAcade, (ArrayList<DecLastPosition>) decLastPosition,
                        (ArrayList<DecCourseConstruction>) decCourseConstruction,
                        (ArrayList<DecNationalPlan>) decNationalPlan, (ArrayList<DecTextbookPmph>) decTextbookPmph,
                        decMoocDigital, (ArrayList<DecTextbook>) decTextbook, (ArrayList<DecResearch>) decResearch,
                        (ArrayList<DecMonograph>) monographs, (ArrayList<DecPublishReward>) publishRewards,
                        (ArrayList<DecSci>) scis, (ArrayList<DecClinicalReward>) clinicalRewards,
                        (ArrayList<DecAcadeReward>) acadeRewards, (ArrayList<DecExtensionVO>) extensionVOs, decIntention);
                List<String> list = new ArrayList<>();
                list.add(textbookName);
                declarationEtcBO.setTextbookName(list);
                List<String> presetPosition = new ArrayList<>();
                presetPosition.add(declarationOrDisplayVO.getPresetPosition());
                declarationEtcBO.setPresetPosition(presetPosition);
                declarationEtcBOs.add(declarationEtcBO);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return declarationEtcBOs;
    }

    @Override
    public List<Declaration> getDeclarationByIds(List<Long> ids) throws CheckedServiceException {
        if (null == ids || ids.isEmpty()) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.NULL_PARAM,
                    "参数为空!");
        }
        List<Declaration> declarations = declarationDao.getDeclarationByIds(ids);
        return declarations;
    }

    @Override
    public Declaration getDeclarationByMaterialIdAndUserId(Long materialId, Long userId)
            throws CheckedServiceException {
        if (null == materialId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "教材id为空");
        }
        if (null == userId) {
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "作家id为空");
        }
        return declarationDao.getDeclarationByMaterialIdAndUserId(materialId, userId);
    }

    @Override
    public List<DeclarationOrDisplayVO> getDeclarationOrDisplayVOByRealname(List<Long> id)
            throws CheckedServiceException {
        List<DeclarationOrDisplayVO> declarationOrDisplayVOs = declarationDao
                .getDeclarationOrDisplayVOByIdOrRealname(id);
        return declarationOrDisplayVOs;
    }
    @Override
    public List<DeclarationOrDisplayVO> getDeclarationOrDisplayVOByRealname2(List<Long> id)
            throws CheckedServiceException {
        List<DeclarationOrDisplayVO> declarationOrDisplayVOs = declarationDao
                .getDeclarationOrDisplayVOByIdOrRealname2(id);
        for(DeclarationOrDisplayVO dv:declarationOrDisplayVOs){
                String title = dataDictionaryDao.getDataDictionaryItemNameByCode(Const.PMPH_POSITION, dv.getTitle().toString());
                String presetPosition = dv.getPresetPosition();
                dv.setTitle(title);
        }
        return declarationOrDisplayVOs;
    }

    @Override
    public List<DeclarationEtcBO> getDeclarationOrDisplayVOByIdOrRealname(List<Long> decIds)
            throws CheckedServiceException, IllegalArgumentException, IllegalAccessException {
        List<DeclarationEtcBO> declarationEtcBOs = new ArrayList<>();


        Map<String, Object> params = new HashMap<>();
        params.put("declarationId",decIds.get(0));
        Long materialBydeclarationId = declarationDao.getMaterialBydeclarationId(params);
        params.put("material_id",materialBydeclarationId);
        String materialCreateDate = declarationDao.findMaterialCreateDate(params);

        Date date1 = DateUtil.fomatDate(materialCreateDate);
        Date date = DateUtil.fomatDate("2019-2-12 12:00");
        List<DeclarationOrDisplayVO> declarationOrDisplayVOs;
        if(date1.getTime()>date.getTime()) {
            declarationOrDisplayVOs= declarationDao.getDeclarationOrDisplayVOByIdOrRealname2(decIds);
            for(DeclarationOrDisplayVO dv: declarationOrDisplayVOs){
                String post = dv.getPresetPosition();
                if (post != null) {
                    if (ObjectUtil.isNumber(post)) {
                        post = dataDictionaryDao.getDataDictionaryItemNameByCode(Const.PMPH_POSITION, dv.getPresetPosition().toString());
                    }
                }
                dv.setPresetPosition(post);
            }


        }else{
            declarationOrDisplayVOs= declarationDao.getDeclarationOrDisplayVOByIdOrRealname(decIds);
        }


            // 学习经历
        ArrayList<DecEduExp> decEduExps = (ArrayList<DecEduExp>) decEduExpDao.getListDecEduExpByDeclarationIds(decIds);
        // 工作经历
        ArrayList<DecWorkExp> decWorkExps = (ArrayList<DecWorkExp>) decWorkExpDao
                .getListDecWorkExpByDeclarationIds(decIds);
        // 教学经历
        ArrayList<DecTeachExp> decTeachExps = (ArrayList<DecTeachExp>) decTeachExpDao
                .getListDecTeachExpByDeclarationIds(decIds);
        // 兼职学术
        ArrayList<DecAcade> decAcades = (ArrayList<DecAcade>) decAcadeDao.getListDecAcadeByDeclarationIds(decIds);
        // 个人成就
        ArrayList<DecAchievement> decAchievements = (ArrayList<DecAchievement>) decAchievementDao
                .getDecAchievementByDeclarationIds(decIds);
        // 本套上版教材参编情况
        ArrayList<DecLastPosition> decLastPositions = (ArrayList<DecLastPosition>) decLastPositionDao
                .getListDecLastPositionByDeclarationIds(decIds);
        // 精品课程建设情况
        ArrayList<DecCourseConstruction> decCourseConstructions = (ArrayList<DecCourseConstruction>) decCourseConstructionDao
                .getDecCourseConstructionByDeclarationIds(decIds);
        // 主编国家级规划
        ArrayList<DecNationalPlan> decNationalPlans = (ArrayList<DecNationalPlan>) decNationalPlanDao
                .getListDecNationalPlanByDeclarationIds(decIds);
        // 人卫社教材编写情况表
        ArrayList<DecTextbookPmph> decTextbookPmphs = (ArrayList<DecTextbookPmph>) decTextbookPmphDao
                .getListDecTextbookPmphByDeclarationIds(decIds);
        // 其他社教材编写情况
        ArrayList<DecTextbook> decTextbooks = (ArrayList<DecTextbook>) decTextbookDao
                .getListDecTextbookByDeclarationIds(decIds);
        // 参加人卫慕课、数字教材编写情况表
        ArrayList<DecMoocDigital> decMoocDigitals = (ArrayList<DecMoocDigital>) decMoocDigitalDao
                .getDecMoocDigitalByDeclarationIds(decIds);
        // 作家科研
        ArrayList<DecResearch> decResearchs = (ArrayList<DecResearch>) decResearchDao
                .getListDecResearchByDeclarationIds(decIds);
        // 主编学术专著情况
        ArrayList<DecMonograph> decMonographList = (ArrayList<DecMonograph>) decMonographDao
                .getListDecMonographByDeclarationIds(decIds);
        // 出版行业获奖情况
        ArrayList<DecPublishReward> decPublishRewardList = (ArrayList<DecPublishReward>) decPublishRewardDao
                .getListDecPublishRewardByDeclarationIds(decIds);
        // SCI论文投稿及影响因子情况
        ArrayList<DecSci> decSciList = (ArrayList<DecSci>) decSciDao.getListDecSciByDeclarationIds(decIds);
        // 临床医学获奖情况
        ArrayList<DecClinicalReward> decClinicalRewardList = (ArrayList<DecClinicalReward>) decClinicalRewardDao
                .getListDecClinicalRewardByDeclarationIds(decIds);
        // 学术荣誉授予情况
        ArrayList<DecAcadeReward> decAcadeRewardList = (ArrayList<DecAcadeReward>) decAcadeRewardDao
                .getListDecAcadeRewardByDeclarationIds(decIds);
        // 作家扩展项
        ArrayList<DecExtensionVO> decExtensionVOs = (ArrayList<DecExtensionVO>) decExtensionDao
                .getListDecExtensionVOByDeclarationIds(decIds);
        // 编写内容意向表
        ArrayList<DecIntention> decIntentions = (ArrayList<DecIntention>) decIntentionDao
                .getDecIntentionByDeclarationIds(decIds);
        // 教材扩展项
        // List<MaterialExtension> extensions =
        // materialExtensionService.getMaterialExtensionByMaterialId(materialId);
        for (DeclarationOrDisplayVO declarationOrDisplayVO : declarationOrDisplayVOs) {
            String strOnlineProgress = "";// 审核进度
            String strOfflineProgress = "";// 纸质表进度
            String sex = "";// 性别
            String idtype = "";// 证件类别
            String degree = "";// 学历
            switch (declarationOrDisplayVO.getIdtype()) {
                case 0:
                    idtype = "身份证";
                    break;
                case 1:
                    idtype = "护照";
                    break;
                case 2:
                    idtype = "军官证";
                    break;
                default:
                    idtype = "出现错误";
                    break;
            }
            switch (declarationOrDisplayVO.getDegree()) {
                case 0:
                    degree = "无";
                    break;
                case 1:
                    degree = "专科";
                    break;
                case 2:
                    degree = "本科";
                    break;
                case 3:
                    degree = "硕士";
                    break;
                case 4:
                    degree = "博士";
                    break;
                default:
                    degree = "出现错误";
                    break;
            }
            switch (declarationOrDisplayVO.getOnlineProgress()) {
                case 0:
                    strOnlineProgress = "未提交";
                    break;
                case 1:
                    strOnlineProgress = "已提交";
                    break;
                case 2:
                    strOnlineProgress = "被退回";
                    break;
                case 3:
                    strOnlineProgress = "已通过";
                    break;
                default:
                    strOnlineProgress = "出现错误";
                    break;
            }
            switch (declarationOrDisplayVO.getSex()) {
                case 0:
                    sex = "保密";
                    break;
                case 1:
                    sex = "男";
                    break;
                case 2:
                    sex = "女";
                    break;
                default:
                    sex = "出现错误";
                    break;
            }
            switch (declarationOrDisplayVO.getOfflineProgress()) {
                case 0:
                    strOfflineProgress = "未收到";
                    break;
                case 1:
                    strOfflineProgress = "被退回";
                    break;
                case 2:
                    strOfflineProgress = "已收到";
                    break;
                default:
                    strOfflineProgress = "出现错误";
                    break;
            }
            String birthday = "";
            if (null != declarationOrDisplayVO.getBirthday()) {
                birthday = DateUtil.date2Str(declarationOrDisplayVO.getBirthday(), "yyyy-MM-dd");
            }
            if (null == declarationOrDisplayVO.getPosition() || "".equals(declarationOrDisplayVO.getPosition())) {
                declarationOrDisplayVO.setPosition("无");
            }
            if (StringUtil.isEmpty(declarationOrDisplayVO.getTextbookName())) {
                declarationOrDisplayVO.setTextbookName("");
            }
            if (StringUtil.isEmpty(declarationOrDisplayVO.getPresetPosition())) {
                declarationOrDisplayVO.setPresetPosition("");
            }
            // 学习经历
            List<DecEduExp> decEduExp = new ArrayList<>();
            for (DecEduExp exp : decEduExps) {
                if (exp.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decEduExp.add(exp);
                }
            }
            // 工作经历
            List<DecWorkExp> decWorkExp = new ArrayList<>();
            for (DecWorkExp workExp : decWorkExps) {
                if (workExp.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decWorkExp.add(workExp);
                }
            }
            // 教学经历
            List<DecTeachExp> decTeachExp = new ArrayList<>();
            for (DecTeachExp teachExp : decTeachExps) {
                if (teachExp.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decTeachExp.add(teachExp);
                }
            }
            // 兼职学术
            List<DecAcade> decAcade = new ArrayList<>();
            for (DecAcade acade : decAcades) {
                if (acade.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decAcade.add(acade);
                }
            }
            // 个人成就
            DecAchievement decAchievement = new DecAchievement();
            for (DecAchievement achievement : decAchievements) {
                if (achievement.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decAchievement = achievement;
                    break;
                }
            }
            // 上套教材
            List<DecLastPosition> decLastPosition = new ArrayList<>();
            for (DecLastPosition lastPosition : decLastPositions) {
                if (lastPosition.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decLastPosition.add(lastPosition);
                }
            }
            // 精品课程建设情况
            List<DecCourseConstruction> decCourseConstruction = new ArrayList<>();
            for (DecCourseConstruction construction : decCourseConstructions) {
                if (construction.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decCourseConstruction.add(construction);
                }
            }
            // 主编国家级规划
            List<DecNationalPlan> decNationalPlan = new ArrayList<>();
            for (DecNationalPlan plan : decNationalPlans) {
                if (plan.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decNationalPlan.add(plan);
                }
            }
            // 人卫社教材编写
            List<DecTextbookPmph> decTextbookPmph = new ArrayList<>();
            for (DecTextbookPmph textbookPmph : decTextbookPmphs) {
                if (textbookPmph.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decTextbookPmph.add(textbookPmph);
                }
            }
            // 其他社教材编写
            List<DecTextbook> decTextbook = new ArrayList<>();
            for (DecTextbook textbook : decTextbooks) {
                if (textbook.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decTextbook.add(textbook);
                }
            }
            // 参加人卫慕课、数字教材编写情况表
            DecMoocDigital decMoocDigital = new DecMoocDigital();
            for (DecMoocDigital moocDigital : decMoocDigitals) {
                if (moocDigital.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decMoocDigital = moocDigital;
                    break;
                }
            }
            // 作家科研
            List<DecResearch> decResearch = new ArrayList<>();
            for (DecResearch research : decResearchs) {
                if (research.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decResearch.add(research);
                }
            }

            // 主编学术专著情况
            List<DecMonograph> monographs = new ArrayList<>();
            for (DecMonograph monograph : decMonographList) {
                if (monograph.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    monographs.add(monograph);
                }
            }
            // 出版行业获奖情况
            List<DecPublishReward> publishRewards = new ArrayList<>();
            for (DecPublishReward publishReward : decPublishRewardList) {
                if (publishReward.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    publishRewards.add(publishReward);
                }
            }
            // SCI论文投稿及影响因子情况
            List<DecSci> scis = new ArrayList<>();
            for (DecSci sci : decSciList) {
                if (sci.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    scis.add(sci);
                }
            }
            // 临床医学获奖情况
            List<DecClinicalReward> clinicalRewards = new ArrayList<>();
            for (DecClinicalReward clinicalReward : decClinicalRewardList) {
                if (clinicalReward.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    clinicalRewards.add(clinicalReward);
                }
            }
            // 学术荣誉授予情况
            List<DecAcadeReward> acadeRewards = new ArrayList<>();
            for (DecAcadeReward acadeReward : decAcadeRewardList) {
                if (acadeReward.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    acadeRewards.add(acadeReward);
                }
            }
            // 编写内容意向表
            DecIntention decIntention = new DecIntention();
            for (DecIntention intention : decIntentions) {
                if (intention.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    decIntention = intention;
                    break;
                }
            }
            // 作家扩展项
            List<DecExtensionVO> extensionVOs = new ArrayList<>();
            for (DecExtensionVO extensionVO : decExtensionVOs) {
                if (extensionVO.getDeclarationId().equals(declarationOrDisplayVO.getId())) {
                    extensionVOs.add(extensionVO);
                }
            }
            String isDispensed = declarationOrDisplayVO.getIsDispensed() ? "是" : "否";
            String isUtec = declarationOrDisplayVO.getIsUtec() ? "是" : "否";
            String textbookName = declarationOrDisplayVO.getTextbookName() + "第"
                    + declarationOrDisplayVO.getTextbookRound() + "版";
            DeclarationEtcBO declarationEtcBO = new DeclarationEtcBO(declarationOrDisplayVO.getRealname(),
                    declarationOrDisplayVO.getUsername(), sex, birthday, declarationOrDisplayVO.getExperience(),DateUtil.formatTimeStamp("yyyy-MM-dd",declarationOrDisplayVO.getCommitDate()),
                    declarationOrDisplayVO.getOrgName(), declarationOrDisplayVO.getPosition(),
                    declarationOrDisplayVO.getTitle(), declarationOrDisplayVO.getAddress(),
                    declarationOrDisplayVO.getPostcode(), declarationOrDisplayVO.getTelephone(),
                    declarationOrDisplayVO.getFax(), declarationOrDisplayVO.getHandphone(), degree,
                    declarationOrDisplayVO.getEmail(), idtype, declarationOrDisplayVO.getIdcard(),
                    declarationOrDisplayVO.getExpertise(), isDispensed, isUtec, strOnlineProgress, strOfflineProgress,
                    declarationOrDisplayVO.getOrgNameOne(), (ArrayList<DecEduExp>) decEduExp,
                    (ArrayList<DecWorkExp>) decWorkExp, (ArrayList<DecTeachExp>) decTeachExp, decAchievement,
                    (ArrayList<DecAcade>) decAcade, (ArrayList<DecLastPosition>) decLastPosition,
                    (ArrayList<DecCourseConstruction>) decCourseConstruction,
                    (ArrayList<DecNationalPlan>) decNationalPlan, (ArrayList<DecTextbookPmph>) decTextbookPmph,
                    decMoocDigital, (ArrayList<DecTextbook>) decTextbook, (ArrayList<DecResearch>) decResearch,
                    (ArrayList<DecMonograph>) monographs, (ArrayList<DecPublishReward>) publishRewards,
                    (ArrayList<DecSci>) scis, (ArrayList<DecClinicalReward>) clinicalRewards,
                    (ArrayList<DecAcadeReward>) acadeRewards, (ArrayList<DecExtensionVO>) extensionVOs, decIntention);
            List<String> list = new ArrayList<>();
            list.add(textbookName);
            declarationEtcBO.setTextbookName(list);
            List<String> presetPosition = new ArrayList<>();
            presetPosition.add(declarationOrDisplayVO.getPresetPosition());
            declarationEtcBO.setPresetPosition(presetPosition);
            declarationEtcBO.setMaterialId(declarationOrDisplayVO.getMaterialId());
            declarationEtcBOs.add(declarationEtcBO);
        }
        return declarationEtcBOs;
    }

    @Override
    public List<Declaration> getPositionChooseLossByMaterialId(Long materialId) throws CheckedServiceException {
        if(null==materialId){
            throw new CheckedServiceException(CheckedExceptionBusiness.MATERIAL, CheckedExceptionResult.ILLEGAL_PARAM,
                    "教材id为空");
        }
        return declarationDao.getPositionChooseLossByMaterialId(materialId);
    }

    @Override
    public Long getMaterialBydeclarationId(Map<String, Object> params) {
        return declarationDao.getMaterialBydeclarationId(params);
    }

    @Override
    public String findMaterialCreateDate(Map<String, Object> paraMap) {
        return declarationDao.findMaterialCreateDate(paraMap);
    }

}
