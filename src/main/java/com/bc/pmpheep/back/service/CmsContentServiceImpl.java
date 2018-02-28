package com.bc.pmpheep.back.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bc.pmpheep.back.dao.CmsContentDao;
import com.bc.pmpheep.back.plugin.PageParameter;
import com.bc.pmpheep.back.plugin.PageResult;
import com.bc.pmpheep.back.po.CmsContent;
import com.bc.pmpheep.back.po.CmsExtra;
import com.bc.pmpheep.back.po.MaterialExtra;
import com.bc.pmpheep.back.po.MaterialNoteAttachment;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.WriterUserTrendst;
import com.bc.pmpheep.back.util.ArrayUtil;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.util.FileUpload;
import com.bc.pmpheep.back.util.FileUtil;
import com.bc.pmpheep.back.util.ObjectUtil;
import com.bc.pmpheep.back.util.PageParameterUitl;
import com.bc.pmpheep.back.util.SessionUtil;
import com.bc.pmpheep.back.util.StringUtil;
import com.bc.pmpheep.back.util.SummaryUtil;
import com.bc.pmpheep.back.vo.CmsContentVO;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.po.Content;
import com.bc.pmpheep.general.service.ContentService;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.service.exception.CheckedExceptionBusiness;
import com.bc.pmpheep.service.exception.CheckedExceptionResult;
import com.bc.pmpheep.service.exception.CheckedServiceException;

/**
 * 
 * <pre>
 * 功能描述：CmsContentService 接口实现
 * 使用示范：
 * 
 * 
 * &#64;author (作者) nyz
 * 
 * &#64;since (该版本支持的JDK版本) ：JDK 1.6或以上
 * &#64;version (版本) 1.0
 * &#64;date (开发日期) 2017-10-25
 * &#64;modify (最后修改时间) 
 * &#64;修改人 ：nyz 
 * &#64;审核人 ：
 * </pre>
 */
@Service
public class CmsContentServiceImpl implements CmsContentService {
	@Autowired
	CmsContentDao cmsContentDao;
	@Autowired
	ContentService contentService;
	@Autowired
	CmsScheduleService cmsScheduleService;
	@Autowired
	private FileService fileService;
	@Autowired
	CmsExtraService cmsExtraService;
	@Autowired
	MaterialExtraService materialExtraService;
	@Autowired
	MaterialNoticeAttachmentService materialNoticeAttachmentService;
	@Autowired
	MaterialNoteAttachmentService materialNoteAttachmentService;
	@Autowired
	WriterUserTrendstService writerUserTrendstService;

	@Override
	public CmsContent addCmsContent(CmsContent cmsContent) throws CheckedServiceException {
		if (ObjectUtil.isNull(cmsContent)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
					"CmsContent对象参数为空");
		}
		if (ObjectUtil.isNull(cmsContent.getParentId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
					"上级id参数为空");
		}
		if (StringUtil.isEmpty(cmsContent.getPath())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
					"根节点路径参数为空");
		}
		if (StringUtil.isEmpty(cmsContent.getMid())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
					"内容id参数为空");
		}
		if (ObjectUtil.isNull(cmsContent.getCategoryId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
					"内容类型参数为空");
		}
		if (StringUtil.isEmpty(cmsContent.getTitle())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
					"内容标题参数为空");
		}
		if (ObjectUtil.isNull(cmsContent.getAuthorType())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
					"作者类型参数为空");
		}
		if (cmsContent.getTitle().length() > 50) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.ILLEGAL_PARAM,
					"消息标题不能超过50个字！");
		}
		cmsContentDao.addCmsContent(cmsContent);
		return cmsContent;
	}

	@Override
	public CmsContent addCmsContent(CmsContent cmsContent, String[] files, String content, String scheduledTime,
			String sessionId) throws CheckedServiceException, IOException {
		// 获取当前登陆用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "用户为空");
		}
		if (StringUtil.isEmpty(content)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
					"内容参数为空");
		}
		if (ObjectUtil.isNull(cmsContent.getCategoryId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
					"所属栏目不能为空");

		}
		if (ObjectUtil.isNull(cmsContent.getMaterialId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
					"教材ID不能为空");

		}
		// MongoDB 内容插入
		Content contentObj = contentService.add(new Content(content));
		if (StringUtil.isEmpty(contentObj.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.PO_ADD_FAILED,
					"Content对象内容保存失败");
		}
		// 内容保存
		cmsContent.setParentId(cmsContent.getCategoryId());// 上级id
		// cmsContent.setPath(cmsContent.getPath());// 根节点路径
		cmsContent.setMid(contentObj.getId());// 内容id
		cmsContent.setAuthorType(Const.CMS_AUTHOR_TYPE_1);// 作者类型
		cmsContent.setAuthorId(pmphUser.getId());// 作者id
		// cmsContent.setMaterialId(cmsContent.getMaterialId());// 教材ID，为0表示未选择教材
		String summary = SummaryUtil.htmlToText(content);
		summary = summary.substring(0, Math.min(summary.length(), 60));
		cmsContent.setSummary(summary);
		if (Const.TRUE == cmsContent.getIsPublished()) {
			cmsContent.setAuthStatus(Const.CMS_AUTHOR_STATUS_2);
		}
		// 信息快报/公告管理(发布)，审核时间就为当前时间
		if (ObjectUtil.notNull(cmsContent.getAuthStatus())) {
			if (Const.CMS_AUTHOR_STATUS_2.shortValue() == cmsContent.getAuthStatus()) {
				cmsContent.setAuthUserId(pmphUser.getId());
				cmsContent.setAuthStatus(Const.CMS_AUTHOR_STATUS_2);
				cmsContent.setAuthDate(DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss", DateUtil.getCurrentTime()));
				cmsContent.setIsPublished(true);
			}
		}
		if (ObjectUtil.notNull(cmsContent.getIsStaging())) {
			if (Const.TRUE.booleanValue() == cmsContent.getIsStaging().booleanValue()) {
				// 信息快报/公告管理(暂存)
				cmsContent.setAuthUserId(pmphUser.getId());
				cmsContent.setAuthStatus(null);
				cmsContent.setAuthDate(null);
				cmsContent.setIsPublished(false);
			}
		}
		Long contentId = this.addCmsContent(cmsContent).getId();// 获取新增后的主键ID
		if (ObjectUtil.isNull(contentId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.PO_ADD_FAILED,
					"CmsContent添加内容失败");
		}
		// 定时发布
		// if (Const.TRUE.booleanValue() == cmsContent.getIsScheduled().booleanValue())
		// {
		// if (StringUtil.isEmpty(scheduledTime)) {
		// throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
		// CheckedExceptionResult.NULL_PARAM, "定时发布时间参数为空");
		// }
		// cmsScheduleService.addCmsSchedule(new CmsSchedule(contentId,
		// DateUtil.str2Timestam(scheduledTime)));
		// }
		// 保存附件到MongoDB
		this.saveFileToMongoDB(files, contentId);
		return cmsContent;
	}

	@Override
	public Integer updateCmsContent(CmsContent cmsContent, String[] files, String content, String[] attachment,
			String scheduledTime, String sessionId) throws CheckedServiceException, IOException {
		Integer count = 0;
		// 获取当前登陆用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "用户为空");
		}
		if (StringUtil.notEmpty(content)) {
			// 更新MongoDB 内容
			contentService.update(new Content(cmsContent.getMid(), content));
		}
		if (ObjectUtil.isNull(cmsContent)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");

		}
		if (ObjectUtil.isNull(cmsContent.getMaterialId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
					"教材ID为空");
		}
		if (Const.TRUE == cmsContent.getIsPublished()) {
			cmsContent.setAuthStatus(Const.CMS_AUTHOR_STATUS_2);
		}
		// 信息快报/公告管理(发布)，审核时间就为当前时间
		if (Const.CMS_AUTHOR_STATUS_2 == cmsContent.getAuthStatus().shortValue()) {
			cmsContent.setAuthUserId(pmphUser.getId());
			cmsContent.setAuthStatus(Const.CMS_AUTHOR_STATUS_2);
			cmsContent.setIsStaging(Const.FALSE);
			cmsContent.setAuthDate(DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss", DateUtil.getCurrentTime()));
			cmsContent.setIsPublished(true);
		} else if (cmsContent.getCategoryId() == Const.CMS_CATEGORY_ID_2
				|| cmsContent.getCategoryId() == Const.CMS_CATEGORY_ID_3) {
			if (Const.TRUE == cmsContent.getIsStaging()) {
				// 信息快报/公告管理(暂存)
				cmsContent.setAuthUserId(pmphUser.getId());
				cmsContent.setAuthStatus(Const.CMS_AUTHOR_STATUS_0);
				cmsContent.setAuthDate(null);
				cmsContent.setIsPublished(false);
			}
		} else {
			// 文章管理,退回
			if (Const.CMS_AUTHOR_STATUS_1.shortValue() == cmsContent.getAuthStatus().shortValue()) {
				cmsContent.setAuthUserId(pmphUser.getId());
				cmsContent.setAuthStatus(Const.CMS_AUTHOR_STATUS_1);
				cmsContent.setAuthDate(DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss", DateUtil.getCurrentTime()));
				cmsContent.setIsDeleted(false);
				cmsContent.setIsPublished(false);
			} else if (Const.CMS_AUTHOR_STATUS_2.shortValue() == cmsContent.getAuthStatus().shortValue()) {
				// 文章管理,通过
				cmsContent.setAuthUserId(pmphUser.getId());
				cmsContent.setAuthStatus(Const.CMS_AUTHOR_STATUS_2);
				cmsContent.setIsStaging(Const.FALSE);
				cmsContent.setAuthDate(DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss", DateUtil.getCurrentTime()));
				cmsContent.setIsPublished(true);
			}
		}
		if (cmsContent.getCategoryId() == Const.CMS_CATEGORY_ID_1
				&& cmsContent.getAuthorType() == Const.CMS_AUTHOR_TYPE_2
				&& cmsContent.getAuthStatus() == Const.CMS_AUTHOR_STATUS_0 && Const.TRUE == cmsContent.getIsStaging()) {
			cmsContent.setIsStaging(false);
		}
		// 再次编辑时间
		cmsContent.setGmtReedit(DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss", DateUtil.getCurrentTime()));
		count = cmsContentDao.updateCmsContent(cmsContent);
		// 是否定时发布
		// CmsSchedule csmSchedule =
		// cmsScheduleService.getCmsScheduleByContentId(cmsContent.getId());
		// 1.修改时如果不选择定时发布，则查询该数据之前是否有选择定时发布，如果有则删除
		// if (Const.FALSE.booleanValue() == cmsContent.getIsScheduled().booleanValue())
		// {
		// if (ObjectUtil.notNull(csmSchedule)) {
		// cmsScheduleService.deleteCmsScheduleByContentId(cmsContent.getId());
		// }
		// }
		// 2.修改时如果选择定时发布，则查询该数据之前是否有选择定时发布，如果有则更新，没有则新增
		// if (Const.TRUE.booleanValue() == cmsContent.getIsScheduled().booleanValue())
		// {
		// if (StringUtil.isEmpty(scheduledTime)) {
		// throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
		// CheckedExceptionResult.NULL_PARAM, "定时发布时间参数为空");
		// }
		// if (ObjectUtil.notNull(csmSchedule)) {
		// cmsScheduleService.updateCmsSchedule(new CmsSchedule(
		// csmSchedule.getId(),
		// cmsContent.getId(),
		// DateUtil.str2Timestam(scheduledTime)));
		// } else {
		// cmsScheduleService.addCmsSchedule(new CmsSchedule(
		// cmsContent.getId(),
		// DateUtil.str2Timestam(scheduledTime)));
		// }
		// }
		// 删除附件
		if (ArrayUtil.isNotEmpty(attachment)) {
			// 删除CmsExtra 表
			cmsExtraService.deleteCmsExtraByAttachment(attachment);
			// 删除MongoDB对应的文件
			for (int i = 0; i < attachment.length; i++) {
				fileService.remove(attachment[i]);
			}
		}
		// 保存附件到MongoDB
		this.saveFileToMongoDB(files, cmsContent.getId());
		return count;
	}

	@Override
	public Integer publishCmsContentById(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		Integer count = 0;
		count = cmsContentDao.publishCmsContentById(id);
		CmsContent cmsContent = this.getCmsContentById(id);
		writerUserTrendstService.addWriterUserTrendst(
				new WriterUserTrendst(cmsContent.getAuthorId(), Const.WRITER_USER_TRENDST_TYPE_1, id));
		return count;
	}

	@Override
	public Integer hideCmsContentById(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return cmsContentDao.hideCmsContentById(id);
	}

	@Override
	public Integer checkContentById(Long id, Short authStatus, Long categoryId, String sessionId)
			throws CheckedServiceException {
		// 获取当前登陆用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "用户为空");
		}
		if (ObjectUtil.isNull(id) || ObjectUtil.isNull(authStatus)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		Boolean isPublished = false;
		if (Const.CMS_AUTHOR_STATUS_2 == authStatus) {
			isPublished = true;
		}
		Integer count = 0;
		count = cmsContentDao.checkContentById(new CmsContent(id, authStatus, pmphUser.getId(),
				DateUtil.formatTimeStamp("yyyy-MM-dd HH:mm:ss", DateUtil.getCurrentTime()), isPublished, false,
				Const.MATERIAL_TYPE_ID));
		// 评论审核通过，评论数加1
		if (Const.CMS_CATEGORY_ID_0.longValue() == categoryId.longValue()) {
			this.updatCmsContentCommentsById(id);
		}
		CmsContent cmsContent = this.getCmsContentById(id);
		Integer type = 0;
		if (Const.CMS_CATEGORY_ID_0.longValue() == categoryId.longValue()) {
			type = Const.WRITER_USER_TRENDST_TYPE_2;
		} else {
			type = Const.WRITER_USER_TRENDST_TYPE_1;
		}
		writerUserTrendstService.addWriterUserTrendst(new WriterUserTrendst(cmsContent.getAuthorId(), type, id));
		return count;
	}

	@Override
	public PageResult<CmsContentVO> listCmsContent(PageParameter<CmsContentVO> pageParameter, String sessionId)
			throws CheckedServiceException {
		// 获取当前登陆用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "用户为空");
		}
		pageParameter.getParameter().setIsAdmin(pmphUser.getIsAdmin());
		pageParameter.getParameter().setAuthorId(pmphUser.getId());
		PageResult<CmsContentVO> pageResult = new PageResult<CmsContentVO>();
		// 将页面大小和页面页码拷贝
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		// if(cmsContentDao.getCmsContentByAuthorId(pageParameter.getParameter().getAuthorId()).size()>0){
		// 包含数据总条数的数据集
		List<CmsContentVO> cmsContentList = cmsContentDao.listCmsContent(pageParameter);
		if (CollectionUtil.isNotEmpty(cmsContentList)) {
			Integer count = cmsContentList.get(0).getCount();
			pageResult.setTotal(count);
			pageResult.setRows(cmsContentList);
		}
		// }
		return pageResult;
	}

	@Override
	public List<CmsContent> getCmsContentList(CmsContent cmsContent) throws CheckedServiceException {
		if (ObjectUtil.isNull(cmsContent)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");

		}
		return cmsContentDao.getCmsContentList(cmsContent);
	}

	@Override
	public Map<String, Object> getCmsContentAndContentAndAttachmentById(Long id) throws CheckedServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");

		}
		// 按id 获取CmsContent对象
		CmsContent cmsContent = cmsContentDao.getCmsContentById(id);
		String authDate = cmsContent.getAuthDate();
		if (StringUtil.notEmpty(authDate)) {
			cmsContent.setAuthDate(DateUtil.date2Str(DateUtil.str2Date(authDate)));
		}
		String deadlinePromote = cmsContent.getDeadlinePromote();
		if (StringUtil.notEmpty(deadlinePromote)) {
			cmsContent.setDeadlinePromote(DateUtil.date2Str(DateUtil.str2Date(deadlinePromote)));
		}
		String deadlineStick = cmsContent.getDeadlineStick();
		if (StringUtil.notEmpty(deadlineStick)) {
			cmsContent.setDeadlineStick(DateUtil.date2Str(DateUtil.str2Date(deadlineStick)));
		}
		String deadlineHot = cmsContent.getDeadlineHot();
		if (StringUtil.notEmpty(deadlineHot)) {
			cmsContent.setDeadlineHot(DateUtil.date2Str(DateUtil.str2Date(deadlineHot)));
		}
		resultMap.put("cmsContent", cmsContent);
		// 判断内容是否已经发布或审核通过
		String fileDownLoadType = null;
		if (Const.TRUE.booleanValue() == cmsContent.getIsPublished().booleanValue()
				|| Const.CMS_AUTHOR_STATUS_2.shortValue() == cmsContent.getAuthStatus().shortValue()) {
			fileDownLoadType = Const.CMS_FILE_DOWNLOAD;
		} else {
			fileDownLoadType = Const.FILE_DOWNLOAD;
		}
		// 按mid 获取Content对象
		Content content = contentService.get(cmsContent.getMid());
		resultMap.put("content", content);
		// 按contentId 获取CMS内容附件
		List<CmsExtra> cmsExtras = cmsExtraService.getCmsExtraByContentId(id);
		for (CmsExtra cmsExtra : cmsExtras) {
			String attachment = cmsExtra.getAttachment();
			cmsExtra.setAttachment(fileDownLoadType + attachment);// 拼接附件下载路径
		}
		resultMap.put("cmsExtras", cmsExtras);
		// 根据MaterialId 获取教材备注附件
		List<MaterialNoteAttachment> materialNoteAttachments = null;
		if (Const.TRUE == cmsContent.getIsMaterialEntry()) {
			MaterialExtra materialExtra = materialExtraService.getMaterialExtraByMaterialId(cmsContent.getMaterialId());
			if (ObjectUtil.notNull(materialExtra)) {
				// 教材备注附件
				materialNoteAttachments = materialNoteAttachmentService
						.getMaterialNoteAttachmentByMaterialExtraId(materialExtra.getId());
			}
		}
		resultMap.put("MaterialNoteAttachment", materialNoteAttachments);
		return resultMap;
	}

	@Override
	public CmsContent getCmsContentById(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return cmsContentDao.getCmsContentById(id);
	}

	@Override
	public Integer getCmsContentCount(Long categoryId) throws CheckedServiceException {
		return cmsContentDao.getCmsContentCount(categoryId);
	}

	@Override
	public CmsContent getCmsContentByMaterialId(Long materialId) throws CheckedServiceException {
		if (ObjectUtil.isNull(materialId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
					"教材ID参数为空!");
		}
		return cmsContentDao.getCmsContentByMaterialId(materialId);
	}

	@Override
	public Integer deleteCmsContentById(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return cmsContentDao.deleteCmsContentById(id);
	}

	@Override
	public Integer deleteCmsContentByIds(List<Long> ids) throws CheckedServiceException {
		if (CollectionUtil.isEmpty(ids)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");

		}
		return cmsContentDao.deleteCmsContentByIds(ids);
	}

	@Override
	public Integer updateCmsContent(CmsContent cmsContent) throws CheckedServiceException {
		if (ObjectUtil.isNull(cmsContent)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return cmsContentDao.updateCmsContent(cmsContent);
	}

	@Override
	public Integer updateCmsContentByIds(List<CmsContent> csmContents) throws CheckedServiceException {
		if (CollectionUtil.isEmpty(csmContents)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "参数为空");
		}
		return cmsContentDao.updateCmsContentByIds(csmContents);
	}

	/**
	 * 
	 * <pre>
	 * 功能描述：保存文件到MongoDB
	 * 使用示范：
	 *
	 * &#64;param files 临时文件路径
	 * &#64;param contentId cms内容ID
	 * &#64;throws CheckedServiceException
	 * </pre>
	 */
	private void saveFileToMongoDB(String[] files, Long contentId) throws CheckedServiceException, IOException {
		// 保存附件到MongoDB
		if (ArrayUtil.isNotEmpty(files)) {
			for (int i = 0; i < files.length; i++) {
				File file = FileUpload.getFileByFilePath(files[i]);
				if (file.isFile()) {
					// 循环获取file数组中得文件
					if (StringUtil.notEmpty(file.getName())) {
						System.out.println(file.getName());
						String gridFSFileId = fileService.saveLocalFile(file, FileType.CMS_ATTACHMENT, contentId);
						if (StringUtil.isEmpty(gridFSFileId)) {
							throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
									CheckedExceptionResult.FILE_UPLOAD_FAILED, "文件上传失败!");
						}
						// 保存对应数据
						CmsExtra cmsExtra = cmsExtraService
								.addCmsExtra(new CmsExtra(contentId, gridFSFileId, file.getName(), null));
						if (ObjectUtil.isNull(cmsExtra.getId())) {
							throw new CheckedServiceException(CheckedExceptionBusiness.CMS,
									CheckedExceptionResult.PO_ADD_FAILED, "CmsExtra对象新增失败");
						}
					}
					// FileUtil.delFile(files[i]);// 删除本地临时文件
					String localFile = files[i];
					String fileDirectory = localFile.substring(0, localFile.lastIndexOf(File.separatorChar));
					FileUtil.delete(fileDirectory);// 删除本地临时文件
				}
			}
		}
	}

	@Override
	public Integer updateCmsContentByMaterialId(Long MaterialId) throws CheckedServiceException {
		if (ObjectUtil.isNull(MaterialId)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM,
					"教材id为空");
		}
		return cmsContentDao.updateCmsContentByMaterialId(MaterialId);
	}

	@Override
	public PageResult<CmsContentVO> listCmsComment(PageParameter<CmsContentVO> pageParameter, String sessionId)
			throws CheckedServiceException {
		// 获取当前登陆用户
		PmphUser pmphUser = SessionUtil.getPmphUserBySessionId(sessionId);
		if (ObjectUtil.isNull(pmphUser) || ObjectUtil.isNull(pmphUser.getId())) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "用户为空");
		}
		pageParameter.getParameter().setIsAdmin(pmphUser.getIsAdmin());
		pageParameter.getParameter().setAuthorId(pmphUser.getId());
		PageResult<CmsContentVO> pageResult = new PageResult<CmsContentVO>();
		// 将页面大小和页面页码拷贝
		PageParameterUitl.CopyPageParameter(pageParameter, pageResult);
		// 包含数据总条数的数据集
		List<CmsContentVO> cmsContentList = cmsContentDao.listCmsComment(pageParameter);
		if (CollectionUtil.isNotEmpty(cmsContentList)) {
			Integer count = cmsContentList.get(0).getCount();
			pageResult.setTotal(count);
			pageResult.setRows(cmsContentList);
		}
		return pageResult;
	}

	@Override
	public Integer updatCmsContentCommentsById(Long id) throws CheckedServiceException {
		if (ObjectUtil.isNull(id)) {
			throw new CheckedServiceException(CheckedExceptionBusiness.CMS, CheckedExceptionResult.NULL_PARAM, "主键为空");
		}
		return cmsContentDao.updatCmsContentCommentsById(id);
	}
}
