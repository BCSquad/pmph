package com.bc.pmpheep.general.runnable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bc.pmpheep.back.bo.DeclarationEtcBO;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.Textbook;
import com.bc.pmpheep.back.service.DeclarationService;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.service.TextbookService;
import com.bc.pmpheep.back.util.CollectionUtil;
import com.bc.pmpheep.back.util.Const;
import com.bc.pmpheep.back.util.DateUtil;
import com.bc.pmpheep.general.bean.ZipDownload;
import com.bc.pmpheep.general.controller.FileDownLoadController;
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
			TextbookService textbookService, DeclarationService declarationService, Long materialId, String textBookids,
			String realname, String position, String title, String orgName, String unitName, Integer positionType,
			Integer onlineProgress, Integer offlineProgress, String id) {
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
	}

	@Override
	public void run() {
		System.out.println("线程开始");
		String src = this.getClass().getResource("/").getPath();
		src = src.substring(1);
		if (!src.endsWith(File.separator)) {
			src += File.separator;
		}
		ZipDownload zipDownload = new ZipDownload();
		Material material = this.materialService.getMaterialById(this.materialId);
		StringBuilder str = new StringBuilder("111111111111111111");
//		str.append(material.getIsEduExpRequired() ? "1" : "0");
//		str.append(material.getIsWorkExpRequired() ? "1" : "0");
//		str.append(material.getIsTeachExpRequired() ? "1" : "0");
//		str.append(material.getIsAcadeRequired() ? "1" : "0");
//		str.append(material.getIsLastPositionRequired() ? "1" : "0");
//		str.append(material.getIsCourseRequired() ? "1" : "0");
//		str.append(material.getIsNationalPlanRequired() ? "1" : "0");
//		str.append(material.getIsTextbookRequired() ? "1" : "0");
//		str.append(material.getIsOtherTextbookRequired() ? "0" : "0");
//		str.append(material.getIsResearchRequired() ? "1" : "0");
//		str.append(material.getIsAchievementRequired() ? "1" : "0");
//		str.append(material.getIsMonographRequired() ? "1" : "0");
//		str.append(material.getIsPublishRewardRequired() ? "1" : "0");
//		str.append(material.getIsSciRequired() ? "1" : "0");
//		str.append(material.getIsClinicalRewardRequired() ? "1" : "0");
//		str.append(material.getIsAcadeRewardRequired() ? "1" : "0");
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
					this.onlineProgress, this.offlineProgress);
		} catch (CheckedServiceException | IllegalArgumentException | IllegalAccessException e) {
			logger.warn("数据表格化的时候失败");
		}
		try {
			List<DeclarationEtcBO> list = new ArrayList<>();
			for (int i = 0; i < textbooks.size(); i++) {
				for (DeclarationEtcBO declarationEtcBO : declarationEtcBOs) {
					if (textbooks.get(i).getTextbookName().equals(declarationEtcBO.getTextbookName())) {
						list.add(declarationEtcBO);
					}
				}
				if (!CollectionUtil.isEmpty(list)) {
					StringBuilder sb = new StringBuilder();
					sb.append(src);
					sb.append(this.id);
					sb.append(File.separator);
					sb.append(material.getMaterialName());
					sb.append(File.separator);
					sb.append((i + 1) + "." + textbooks.get(i).getTextbookName());
					sb.append(File.separator);
					this.wordHelper.export(material.getMaterialName(), sb.toString(), list, filter);
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

}
