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
import com.bc.pmpheep.general.bean.ZipDownload;
import com.bc.pmpheep.service.exception.CheckedServiceException;
import com.bc.pmpheep.utils.WordHelper;
import com.bc.pmpheep.utils.ZipHelper;

public class SpringThread implements Runnable {

	Logger logger = LoggerFactory.getLogger(SpringThread.class);
	ZipHelper zipHelper;
	WordHelper wordHelper;
	MaterialService materialService;
	TextbookService textbookService;
	DeclarationService declarationService;
	MaterialExtensionService materialExtensionService;
	private Long materialId;
	private String textBookids;
	private String realname;
	private String position;
	private String title;
	private String orgName;
	private String unitName;
	private Integer positionType;
	private Integer onlineProgress;
	private Integer offlineProgress;
	private String id;
	private Boolean isSelect;

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getTextBookids() {
		return textBookids;
	}

	public void setTextBookids(String textBookids) {
		this.textBookids = textBookids;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getPositionType() {
		return positionType;
	}

	public void setPositionType(Integer positionType) {
		this.positionType = positionType;
	}

	public Integer getOnlineProgress() {
		return onlineProgress;
	}

	public void setOnlineProgress(Integer onlineProgress) {
		this.onlineProgress = onlineProgress;
	}

	public Integer getOfflineProgress() {
		return offlineProgress;
	}

	public void setOfflineProgress(Integer offlineProgress) {
		this.offlineProgress = offlineProgress;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public SpringThread(ZipHelper zipHelper, WordHelper wordHelper, MaterialService materialService,
			TextbookService textbookService, DeclarationService declarationService,Boolean isSelect, Long materialId, String textBookids,
			String realname, String position, String title, String orgName, String unitName, Integer positionType,
			Integer onlineProgress, Integer offlineProgress, String id,
			MaterialExtensionService materialExtensionService) {
		super();
		this.zipHelper = zipHelper;
		this.wordHelper = wordHelper;
		this.materialService = materialService;
		this.textbookService = textbookService;
		this.declarationService = declarationService;
		this.materialId = materialId;
		this.textBookids = textBookids;
		this.realname = realname;
		this.position = position;
		this.title = title;
		this.orgName = orgName;
		this.unitName = unitName;
		this.positionType = positionType;
		this.onlineProgress = onlineProgress;
		this.offlineProgress = offlineProgress;
		this.id = id;
		this.materialExtensionService = materialExtensionService;
		this.isSelect = isSelect;
	}

	@Override
	public synchronized void run() {
//		System.out.println("线程开始");
		String src = this.getClass().getResource("/").getPath();
		src = src.substring(1);
		if (!src.endsWith(File.separator)) {
			src += File.separator;
		}
		ZipDownload zipDownload = new ZipDownload();
		Material material = this.materialService.getMaterialById(this.materialId);
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
		List<Textbook> textbooks = this.textbookService.getTextbookByMaterialId(this.materialId);
		List<DeclarationEtcBO> declarationEtcBOs = new ArrayList<>();
		String dest = src + this.id;
		zipDownload.setId(this.id);
		zipDownload.setMaterialName(material.getMaterialName());
		zipDownload.setState(0);
		zipDownload.setDetail("loading...");
		zipDownload.setCreateTime(DateUtil.getCurrentTime());
		Const.WORD_EXPORT_MAP.put(this.id, zipDownload);
		try {
			declarationEtcBOs = this.declarationService.declarationEtcBO(this.materialId, this.textBookids,
					this.realname, this.position, this.title, this.orgName, this.unitName, this.positionType,
					this.onlineProgress, this.offlineProgress,this.isSelect);
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		try {
			List<DeclarationEtcBO> list = new ArrayList<>();
			for (int i = 0; i < textbooks.size(); i++) {
				String textbookName = textbooks.get(i).getTextbookName() + "第" + textbooks.get(i).getTextbookRound()
						+ "版";
				for (DeclarationEtcBO declarationEtcBO : declarationEtcBOs) {
					for (DeclarationEtcBO etcBO : declarationEtcBOs) {
						if (declarationEtcBO.getRealname().equals(etcBO.getRealname())
								&& !declarationEtcBO.getTextbookName().contains(etcBO.getTextbookName().get(0))) {
							declarationEtcBO.getTextbookName().add(etcBO.getTextbookName().get(0));
							declarationEtcBO.getPresetPosition().add(etcBO.getPresetPosition().get(0));
						}
					}
					if (textbookName.equals(declarationEtcBO.getTextbookName().get(0))) {
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
					sb.append((i + 1) + "." + FileUtil.replaceIllegalCharForFileName(textbookName));
					sb.append(File.separator);
					this.wordHelper.export(material.getMaterialName(), sb.toString(), list, str.toString(),
							this.materialExtensionService.getMaterialExtensionByMaterialId(this.materialId));
					list.removeAll(list);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		new Thread(zipDownload).start();
		try {
			this.zipHelper.zip(dest + File.separator + material.getMaterialName(), dest + File.separator, true, null);
		} catch (Exception e) {
			e.getMessage();
		}
		zipDownload.setState(1);
		zipDownload.setDetail("/zip/download?id=" + this.id);
		Const.WORD_EXPORT_MAP.put(this.id, zipDownload);
	}




	public Boolean getSelect() {
		return isSelect;
	}

	public void setSelect(Boolean select) {
		isSelect = select;
	}
}
