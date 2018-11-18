package com.bc.pmpheep.general.runnable;

import com.bc.pmpheep.back.dao.MaterialSurveyDao;
import com.bc.pmpheep.back.po.Material;
import com.bc.pmpheep.back.po.SurveyWordDetailVO;
import com.bc.pmpheep.back.po.SurveyWordMainVO;
import com.bc.pmpheep.back.service.MaterialService;
import com.bc.pmpheep.back.util.*;
import com.bc.pmpheep.back.vo.SurveyVO;
import com.bc.pmpheep.general.bean.ZipDownload;
import com.bc.pmpheep.utils.MaterialSurveyWordHelper;
import com.bc.pmpheep.utils.ZipHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.List;

public class MaterialSurveySpringThread implements Runnable {

    Logger logger = LoggerFactory.getLogger(MaterialSurveySpringThread.class);
    ZipHelper zipHelper;
    MaterialSurveyWordHelper materialSurveyWordHelper;
    MaterialSurveyDao materialSurveyDao;
    SurveyVO surveyVO;
    String id;
    MaterialService materialService;

    public MaterialSurveySpringThread(ZipHelper zipHelper, MaterialSurveyWordHelper materialSurveyWordHelper, MaterialSurveyDao materialSurveyDao,MaterialService materialService, SurveyVO surveyVO, String id) {
        super();
        this.zipHelper = zipHelper;
        this.materialSurveyWordHelper = materialSurveyWordHelper;
        this.materialSurveyDao = materialSurveyDao;
        this.surveyVO = surveyVO;
        this.materialService = materialService;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void run(){
        System.out.println("线程开始");
        String src = this.getClass().getResource("/").getPath();
        src = src.substring(1);
        if (!src.endsWith(File.separator)) {
            src += File.separator;
        }
        ZipDownload zipDownload = new ZipDownload();

            //业务逻辑获取数据
            List<SurveyWordMainVO> mainList = materialSurveyDao.wordExMainList(surveyVO);

            List<SurveyWordDetailVO> detailList = materialSurveyDao.wordExDetailList();

            Long materialId = surveyVO.getMaterialId();
            Material material = null;

            if (materialId != null && materialId > 0) {
                material = materialService.getMaterialById(materialId);
            }

            String dest = src + this.id;
            zipDownload.setId(this.id);
            zipDownload.setMaterialName(ObjectUtil.notNull(material) ? material.getMaterialName() : "非教材相关调研表"); // 由于历史原因 这里导出的bean 的名称 用materialName
            zipDownload.setState(0);
            zipDownload.setDetail("loading...");
            zipDownload.setCreateTime(DateUtil.getCurrentTime());
            Const.WORD_EXPORT_MAP.put(this.id, zipDownload);

            String groupBy = "user"; //用户 另一种选择是调研表 survey

            String materialName = FileUtil.replaceIllegalCharForFileName(ObjectUtil.notNull(material) ? material.getMaterialName() : "非教材相关调研表");

        try {
            //获取
            if (!CollectionUtil.isEmpty(mainList)) {
                StringBuilder filePath = new StringBuilder();
                filePath.append(src);
                filePath.append(this.id);
                filePath.append(File.separator);
                filePath.append(materialName);
                filePath.append(File.separator);
                //
                this.materialSurveyWordHelper.export(filePath.toString(), mainList, detailList, groupBy);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        new Thread(zipDownload).start();
        try {
            this.zipHelper.zip(dest + File.separator + materialName, dest + File.separator, true, null);
        } catch (Exception e) {
            e.getMessage();
        }
        zipDownload.setState(1);
        zipDownload.setDetail("/zip/download?id=" + this.id);
        Const.WORD_EXPORT_MAP.put(this.id, zipDownload);

    }


}
