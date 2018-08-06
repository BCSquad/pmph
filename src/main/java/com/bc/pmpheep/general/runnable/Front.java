package com.bc.pmpheep.general.runnable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bc.pmpheep.back.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.service.DeclarationService;
import com.bc.pmpheep.back.service.MaterialExtensionService;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.service.TextbookService;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.back.vo.DeclarationOrDisplayVO;
import com.bc.pmpheep.general.bean.ZipDownload;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.utils.WordHelper;
import com.bc.pmpheep.utils.ZipHelper;

public class Front implements Runnable {

	Logger logger = LoggerFactory.getLogger(SpringThread.class);
	ZipHelper zipHelper;
	WordHelper wordHelper;
	MaterialService materialService;
	TextbookService textbookService;
	DeclarationService declarationService;
	MaterialExtensionService materialExtensionService;
	private List<Long> decIds;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public ZipHelper getZipHelper() {
		return zipHelper;
	}

	public void setZipHelper(ZipHelper zipHelper) {
		this.zipHelper = zipHelper;
	}

	public WordHelper getWordHelper() {
		return wordHelper;
	}

	public void setWordHelper(WordHelper wordHelper) {
		this.wordHelper = wordHelper;
	}

	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}

	public TextbookService getTextbookService() {
		return textbookService;
	}

	public void setTextbookService(TextbookService textbookService) {
		this.textbookService = textbookService;
	}

	public DeclarationService getDeclarationService() {
		return declarationService;
	}

	public void setDeclarationService(DeclarationService declarationService) {
		this.declarationService = declarationService;
	}

	public MaterialExtensionService getMaterialExtensionService() {
		return materialExtensionService;
	}

	public void setMaterialExtensionService(MaterialExtensionService materialExtensionService) {
		this.materialExtensionService = materialExtensionService;
	}

	public List<Long> getDecIds() {
		return decIds;
	}

	public void setDecIds(List<Long> decIds) {
		this.decIds = decIds;
	}

	public Front(Logger logger, ZipHelper zipHelper, WordHelper wordHelper, MaterialService materialService,
			TextbookService textbookService, DeclarationService declarationService,
			MaterialExtensionService materialExtensionService, List<Long> decIds, String id) {
		super();
		this.logger = logger;
		this.zipHelper = zipHelper;
		this.wordHelper = wordHelper;
		this.materialService = materialService;
		this.textbookService = textbookService;
		this.declarationService = declarationService;
		this.materialExtensionService = materialExtensionService;
		this.decIds = decIds;
		this.id = id;
	}

	@Override
	public synchronized void run() {
		System.out.println("线程开始");
		String src = this.getClass().getResource("/").getPath();
		src = src.substring(1);
		if (!src.endsWith(File.separator)) {
			src += File.separator;
		}
		ZipDownload zipDownload = new ZipDownload();
		List<DeclarationEtcBO> declarationEtcBOs = new ArrayList<>();
		List<DeclarationOrDisplayVO> declarationOrDisplayVOs = this.declarationService
				.getDeclarationOrDisplayVOByRealname(this.decIds);
		Material material = this.materialService
				.getMaterialById(declarationOrDisplayVOs.get(0).getMaterialId());
		StringBuilder str = new StringBuilder();
		str.append(material.getIsEduExpUsed() ? "1" : "0");
		str.append(material.getIsWorkExpUsed() ? "1" : "0");
		str.append(material.getIsTeachExpUsed() ? "1" : "0");
		str.append(material.getIsAchievementUsed() ? "1" : "0");
		str.append(material.getIsAcadeUsed() ? "1" : "0");
		str.append(material.getIsLastPositionUsed() ? "1" : "0");
		str.append(material.getIsNationalPlanUsed() ? "1" : "0");
		str.append(material.getIsPmphTextbookUsed() ? "1" : "0");
		str.append(material.getIsTextbookUsed() ? "1" : "0");
		str.append(material.getIsMoocDigitalUsed() ? "1" : "0");
		str.append(material.getIsCourseUsed() ? "1" : "0");
		str.append(material.getIsResearchUsed() ? "1" : "0");
		str.append(material.getIsMonographUsed() ? "1" : "0");
		str.append(material.getIsPublishRewardUsed() ? "1" : "0");
		str.append(material.getIsSciUsed() ? "1" : "0");
		str.append(material.getIsClinicalRewardUsed() ? "1" : "0");
		str.append(material.getIsAcadeRewardUsed() ? "1" : "0");
		str.append(material.getIsIntentionUsed() ? "1" : "0");
		Integer filter = Integer.parseInt(str.toString(), 2);
		String dest = src + this.id;
		zipDownload.setId(this.id);
		zipDownload.setState(0);
		zipDownload.setMaterialName(material.getMaterialName());
		zipDownload.setDetail("loading...");
		zipDownload.setCreateTime(DateUtil.getCurrentTime());
		Const.WORD_EXPORT_MAP.put(this.id, zipDownload);
		try {
			declarationEtcBOs = this.declarationService.getDeclarationOrDisplayVOByIdOrRealname(this.decIds);
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		try {
			List<DeclarationEtcBO> list = new ArrayList<>();
			for (int i = 0; i < declarationOrDisplayVOs.size(); i++) {
				List<Textbook> textbooks = this.textbookService.getTextbookByMaterialId(material.getId());
				String name = declarationOrDisplayVOs.get(i).getRealname() + "-" + material.getMaterialName();
				for (DeclarationEtcBO declarationEtcBO : declarationEtcBOs) {
					for (DeclarationEtcBO etcBO : declarationEtcBOs) {
						if (declarationEtcBO.getRealname().equals(etcBO.getRealname())
								&& !declarationEtcBO.getTextbookName().contains(etcBO.getTextbookName().get(0))) {
							declarationEtcBO.getTextbookName().add(etcBO.getTextbookName().get(0));
							declarationEtcBO.getPresetPosition().add(etcBO.getPresetPosition().get(0));
						}
					}
					if (declarationOrDisplayVOs.get(i).getRealname().equals(declarationEtcBO.getRealname())) {
						list.add(declarationEtcBO);
					}
				}
				if (!CollectionUtil.isEmpty(list)) {

					StringBuilder sb = new StringBuilder();
					sb.append(src);
					sb.append(this.id);
					sb.append(File.separator);
					sb.append(FileUtil.replaceIllegalCharForFileName(material.getMaterialName()));
					sb.append(File.separator);
					sb.append((i + 1) + "." + FileUtil.replaceIllegalCharForFileName(name));
					sb.append(File.separator);

					/*sb.append(src);
					sb.append(this.id);
					sb.append(File.separator);
					sb.append(material.getMaterialName());
					sb.append(File.separator);
					sb.append(name);
					sb.append(File.separator);*/

					this.wordHelper.export(material.getMaterialName(), sb.toString(), list, str.toString(),
							this.materialExtensionService.getMaterialExtensionByMaterialId(material.getId()),(i + 1));
					list.removeAll(list);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Thread(zipDownload).start();
		try {
			this.zipHelper.zip(dest + File.separator + material.getMaterialName(),
					dest + File.separator, true, null);
		} catch (Exception e) {
			e.getMessage();
		}
		zipDownload.setState(1);
		zipDownload.setDetail("/zip/download?id=" + this.id);
		Const.WORD_EXPORT_MAP.put(this.id, zipDownload);
	}

}
