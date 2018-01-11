package com.bc.pmpheep.migration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bc.pmpheep.back.dao.CmsAdvertisementDao;
import com.bc.pmpheep.back.dao.CmsAdvertisementImageDao;
import com.bc.pmpheep.back.po.CmsAdvertisement;
import com.bc.pmpheep.back.po.CmsAdvertisementImage;
import com.bc.pmpheep.back.po.PmphUser;
import com.bc.pmpheep.back.po.Survey;
import com.bc.pmpheep.back.po.SurveyQuestion;
import com.bc.pmpheep.back.po.SurveyQuestionOption;
import com.bc.pmpheep.back.po.SurveyTemplate;
import com.bc.pmpheep.back.po.SurveyTemplateQuestion;
import com.bc.pmpheep.back.po.SurveyType;
import com.bc.pmpheep.back.service.PmphUserService;
import com.bc.pmpheep.back.service.SurveyQuestionAnswerService;
import com.bc.pmpheep.back.service.SurveyQuestionCategoryService;
import com.bc.pmpheep.back.service.SurveyQuestionOptionService;
import com.bc.pmpheep.back.service.SurveyQuestionService;
import com.bc.pmpheep.back.service.SurveyService;
import com.bc.pmpheep.back.service.SurveyTargetService;
import com.bc.pmpheep.back.service.SurveyTemplateQuestionService;
import com.bc.pmpheep.back.service.SurveyTemplateService;
import com.bc.pmpheep.back.service.SurveyTypeService;
import com.bc.pmpheep.back.vo.CmsAdvertisementOrImageVO;
import com.bc.pmpheep.general.bean.FileType;
import com.bc.pmpheep.general.service.FileService;
import com.bc.pmpheep.migration.common.JdbcHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 数据填充工具类
 * 
 * Description:数据填充工具类
 * @author tyc
 *
 */
@Component
public class MigrationPlus {
	
	 private final Logger logger = LoggerFactory.getLogger(MigrationPlus.class);
	 
	 @Resource
	 SurveyQuestionAnswerService surveyQuestionAnswerService;
	 @Resource
	 SurveyQuestionCategoryService surveyQuestionCategoryService;
	 @Resource
	 SurveyQuestionOptionService surveyQuestionOptionService;
	 @Resource
	 SurveyQuestionService surveyQuestionService;
	 @Resource
	 SurveyService surveyService;
	 @Resource
	 SurveyTargetService surveyTargetService;
	 @Resource
	 SurveyTemplateQuestionService surveyTemplateQuestionService;
	 @Resource
	 SurveyTemplateService surveyTemplateService;
	 @Resource
	 SurveyTypeService surveyTypeService;
	 @Resource
	 PmphUserService pmphUserService;
	 @Resource
	 CmsAdvertisementDao cmsAdvertisementDao;
	 @Resource
	 CmsAdvertisementImageDao cmsAdvertisementImageDao;
	 @Resource
	 FileService fileService;
	 
	 public void start() {
		 Date begin = new Date();
		 //initCmsAdvertisementData();
		 survey();
		 logger.info("数据填充运行结束，用时：{}", JdbcHelper.getPastTime(begin));
	 }
	 
	 //初始化广告数据
	 @SuppressWarnings("unchecked")
	 public void initCmsAdvertisementData() {
	        //初始化的数据
	        String dataJson
	                = "["
	                + "{adname:'首页轮播',         type:1,autoPlay:true, animationInterval:3000,image:[{image:'/upload/site/24e8c65f-f513-4bee-9e20-bdcc9f97e3a1.jpg'},{image:'/upload/site/24e8c65f-f513-4bee-9e20-bdcc9f97e3a1.jpg'},{image:'/upload/site/24e8c65f-f513-4bee-9e20-bdcc9f97e3a1.jpg'}]} ,"
	                + "{adname:'首页中部',         type:0,autoPlay:false,animationInterval:0,   image:[{image:'/upload/site/2670f031-35da-4dd6-b079-8f295c51a339.png'},{image:'/upload/site/af598f9e-ae9e-48a0-a3e4-17acc363051a.png'},{image:'/upload/site/a4160c1e-8beb-4530-9f2b-df022a6f751d.png'},{image:'/upload/site/a69b782d-f1ad-42e6-a91a-08432963b54a.png'}]} ,"
	                + "{adname:'信息快报和遴选公告列表',type:0,autoPlay:false,animationInterval:0,   image:[{image:'/upload/article/20170328/wenzhang10.jpg'}]} ,"
	                + "{adname:'读书首页轮播 ',      type:1,autoPlay:true ,animationInterval:3000,image:[{image:'/upload/article/20170328/xiaoxi1.jpg'},{image:'/upload/article/20170328/xiaoxi2.jpg'},{image:'/upload/article/20170328/xiaoxi3.jpg'}]} ,"
	                + "]";
	        Gson gson = new Gson();
	        List<CmsAdvertisementOrImageVO> lst = gson.fromJson(dataJson, new TypeToken<ArrayList<CmsAdvertisementOrImageVO>>() {
	        }.getType());
	        for (CmsAdvertisementOrImageVO cmsAdvertisementAndImages : lst) {
	            CmsAdvertisement cmsAdvertisement = new CmsAdvertisement();
	            //广告名称
	            cmsAdvertisement.setAdname(cmsAdvertisementAndImages.getAdname());
	            //是否自动播放
	            cmsAdvertisement.setAutoPlay(cmsAdvertisementAndImages.getAutoPlay());
	            //循环间隔时间
	            cmsAdvertisement.setAnimationInterval(cmsAdvertisementAndImages.getAnimationInterval());
	            //类型    0 普通  1 轮播
	            cmsAdvertisement.setType(cmsAdvertisementAndImages.getType());
	            //保存广告
	            cmsAdvertisementDao.addCmsAdvertisement(cmsAdvertisement);
	            List<CmsAdvertisementImage> images = (List<CmsAdvertisementImage>) (cmsAdvertisementAndImages.getImage());
	            for (CmsAdvertisementImage image : images) {
	                String filePath = image.getImage();
	                image.setAdvertId(cmsAdvertisement.getId());
	                image.setImage("----");
	                //保存图片文件 
	                cmsAdvertisementImageDao.addCmsAdvertisementImage(image);
	                String mongoId = null;
	                try {
	                    //保存图片至mongo
	                    mongoId = fileService.migrateFile(filePath, FileType.CMS_ADVERTISEMENT, image.getId());
	                } catch (Exception ex) {
	                    logger.warn("文件上传失败 :{}", ex.getMessage());
	                    //文件保存失败删除这条记录
	                    cmsAdvertisementImageDao.deleteCmsAdvertisementByImages(image.getId());
	                    continue;
	                }
	                image.setImage(mongoId);
	                //修改图片文件地址
	                cmsAdvertisementImageDao.updateCmsAdvertisementImage(image);
	            }
	        }
	    }
	 
	 protected void survey() {
		 SurveyType surveyType = new SurveyType("学生", 1);
		 surveyTypeService.addSurveyType(surveyType);
		 SurveyType surveyType2 = new SurveyType("教师", 2);
		 surveyTypeService.addSurveyType(surveyType2);
		 SurveyType surveyType3 = new SurveyType("医生", 3);
		 surveyTypeService.addSurveyType(surveyType3);
		 /*PmphUser pmphUser = pmphUserService.getPmphUser("admin");
		 SurveyTemplate surveyTemplate = new SurveyTemplate("人卫e教平台满意度问卷模板", 999, pmphUser.getId(), 
				 "该问卷只针对教师", surveyType2.getId(), false);*/
		 SurveyTemplate surveyTemplate = new SurveyTemplate("人卫e教平台满意度问卷模板", 999, 610L, 
				 "该问卷只针对教师", surveyType2.getId(), false);
		 surveyTemplateService.addSurveyTemplate(surveyTemplate);
		 Survey survey = new Survey("人卫e教平台满意度问卷模板", 
				 "希望您能抽出几分钟时间，将您的感受和建议告诉我们，我们非常重视每位教师的宝贵意见，期待您的参与！", 
				 "该问卷只针对教师", surveyTemplate.getId(), surveyType2.getId(), 
				 610L, null, null, 999, (short) 0);
		 surveyService.addSurvey(survey);
		 SurveyQuestion surveyQuestion = new SurveyQuestion(0L, "您是否使用过人卫e教平台网站？", (short) 1,
				 1, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion);
		 SurveyQuestionOption surveyQuestionOption = new SurveyQuestionOption(surveyQuestion.getId(), 
				 "是", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption);
		 SurveyQuestionOption surveyQuestionOption2 = new SurveyQuestionOption(surveyQuestion.getId(), 
				 "否", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption2);
		 SurveyQuestion surveyQuestion2 = new SurveyQuestion(0L, "您使用人卫e教平台网站的目的是？", 
				 (short) 2, 2, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion2);
		 SurveyQuestionOption  surveyQuestionOption3 = new SurveyQuestionOption(surveyQuestion2.getId(), 
				 "工作需要", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption3);
		 SurveyQuestionOption  surveyQuestionOption4 = new SurveyQuestionOption(surveyQuestion2.getId(), 
				 "获取生活信息，扩展视野受", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption4);
		 SurveyQuestionOption  surveyQuestionOption5 = new SurveyQuestionOption(surveyQuestion2.getId(), 
				 "关注名人动态", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption5);
		 SurveyQuestionOption  surveyQuestionOption6 = new SurveyQuestionOption(surveyQuestion2.getId(), 
				 "寻找知趣相投的群体", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption6);
		 SurveyQuestionOption  surveyQuestionOption7 = new SurveyQuestionOption(surveyQuestion2.getId(), 
				 "展现自己多彩的生活", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption7);
		 SurveyQuestionOption  surveyQuestionOption8 = new SurveyQuestionOption(surveyQuestion2.getId(), 
				 "其他", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption8);
		 SurveyQuestion surveyQuestion3 = new SurveyQuestion(0L, "您在人卫e教平台网站上主要做什么？", 
				 (short) 2, 3, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion3);
		 SurveyQuestionOption  surveyQuestionOption9 = new SurveyQuestionOption(surveyQuestion3.getId(), 
				 "教材申报", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption9);
		 SurveyQuestionOption  surveyQuestionOption10 = new SurveyQuestionOption(surveyQuestion3.getId(), 
				 "查看图书", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption10);
		 SurveyQuestionOption  surveyQuestionOption11 = new SurveyQuestionOption(surveyQuestion3.getId(), 
				 "图书纠错", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption11);
		 SurveyQuestionOption  surveyQuestionOption12 = new SurveyQuestionOption(surveyQuestion3.getId(), 
				 "选题申报", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption12);
		 SurveyQuestionOption  surveyQuestionOption13 = new SurveyQuestionOption(surveyQuestion3.getId(), 
				 "评论图书", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption13);
		 SurveyQuestionOption  surveyQuestionOption14 = new SurveyQuestionOption(surveyQuestion3.getId(), 
				 "其他", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption14);
		 SurveyQuestion surveyQuestion4 = new SurveyQuestion(0L, "您使用人卫e教平台网站的频率是？", 
				 (short) 1, 4, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion4);
		 SurveyQuestionOption surveyQuestionOption15 = new SurveyQuestionOption(surveyQuestion4.getId(), 
				 "几乎每天都会使用", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption15);
		 SurveyQuestionOption surveyQuestionOption16 = new SurveyQuestionOption(surveyQuestion4.getId(), 
				 "每个星期都会使用", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption16);
		 SurveyQuestionOption surveyQuestionOption17 = new SurveyQuestionOption(surveyQuestion4.getId(), 
				 "每个月都会使用", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption17);
		 SurveyQuestionOption surveyQuestionOption18 = new SurveyQuestionOption(surveyQuestion4.getId(), 
				 "很少使用", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption18);
		 SurveyQuestion surveyQuestion5 = new SurveyQuestion(0L, "到目前为止，您使用人卫e教平台网站有多久了？", 
				 (short) 1, 5, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion5);
		 SurveyQuestionOption surveyQuestionOption19 = new SurveyQuestionOption(surveyQuestion5.getId(), 
				 "不到1个月", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption19);
		 SurveyQuestionOption surveyQuestionOption20 = new SurveyQuestionOption(surveyQuestion5.getId(), 
				 "1-3个月（含3个月）", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption20);
		 SurveyQuestionOption surveyQuestionOption21 = new SurveyQuestionOption(surveyQuestion5.getId(), 
				 "3-6个月（含6个月）", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption21);
		 SurveyQuestionOption surveyQuestionOption22 = new SurveyQuestionOption(surveyQuestion5.getId(), 
				 "6-12个月（含12个月）", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption22);
		 SurveyQuestionOption surveyQuestionOption23 = new SurveyQuestionOption(surveyQuestion5.getId(), 
				 "1-2年（含2年）", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption23);
		 SurveyQuestionOption surveyQuestionOption24 = new SurveyQuestionOption(surveyQuestion5.getId(), 
				 "2年以上", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption24);
		 SurveyQuestion surveyQuestion6 = new SurveyQuestion(0L, "您的性别是", (short) 1,
				 6, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion6);
		 SurveyQuestionOption surveyQuestionOption25 = new SurveyQuestionOption(surveyQuestion6.getId(), 
				 "男", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption25);
		 SurveyQuestionOption surveyQuestionOption26 = new SurveyQuestionOption(surveyQuestion6.getId(), 
				 "女", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption26);
		 SurveyQuestion surveyQuestion7 = new SurveyQuestion(0L, "您的学历是", (short) 1,
				 7, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion7);
		 SurveyQuestionOption surveyQuestionOption27 = new SurveyQuestionOption(surveyQuestion7.getId(), 
				 "本科", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption27);
		 SurveyQuestionOption surveyQuestionOption28 = new SurveyQuestionOption(surveyQuestion7.getId(), 
				 "硕士及以上", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption28);
		 SurveyQuestion surveyQuestion8 = new SurveyQuestion(0L, "您对人卫e教平台网站的发展还有哪些期待", 
				 (short) 5, 8, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion8);
		 SurveyQuestionOption surveyQuestionOption29 = new SurveyQuestionOption(surveyQuestion8.getId(), 
				 "期待", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption29);
		 SurveyQuestion surveyQuestion9 = new SurveyQuestion(0L, "您对人卫e教平台网站的发展还有哪些建议", 
				 (short) 5, 9, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion9);
		 SurveyQuestionOption surveyQuestionOption30 = new SurveyQuestionOption(surveyQuestion9.getId(), 
				 "建议", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption30);
		 SurveyQuestion surveyQuestion10 = new SurveyQuestion(0L, "您对人卫e教平台网站的态度是", 
				 (short) 4, 10, null, true);
		 surveyQuestionService.addSurveyQuestion(surveyQuestion10);
		 SurveyQuestionOption surveyQuestionOption31 = new SurveyQuestionOption(surveyQuestion10.getId(), 
				 "态度", false, null);
		 surveyQuestionOptionService.addSurveyQuestionOption(surveyQuestionOption31);
		 SurveyTemplateQuestion surveyTemplateQuestion = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion);
		 SurveyTemplateQuestion surveyTemplateQuestion2 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion2.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion2);
		 SurveyTemplateQuestion surveyTemplateQuestion3 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion3.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion3);
		 SurveyTemplateQuestion surveyTemplateQuestion4 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion4.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion4);
		 SurveyTemplateQuestion surveyTemplateQuestion5 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion5.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion5);
		 SurveyTemplateQuestion surveyTemplateQuestion6 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion6.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion6);
		 SurveyTemplateQuestion surveyTemplateQuestion7 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion7.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion7);
		 SurveyTemplateQuestion surveyTemplateQuestion8 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion8.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion8);
		 SurveyTemplateQuestion surveyTemplateQuestion9 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion9.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion9);
		 SurveyTemplateQuestion surveyTemplateQuestion10 = new SurveyTemplateQuestion(surveyTemplate.getId(), 
				 surveyQuestion10.getId());
		 surveyTemplateQuestionService.addSurveyTemplateQuestion(surveyTemplateQuestion10);
	 }
}
