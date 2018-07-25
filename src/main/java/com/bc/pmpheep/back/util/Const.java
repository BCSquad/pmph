package com.bc.pmpheep.back.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.bc.pmpheep.general.bean.ZipDownload;
import com.bc.pmpheep.general.runnable.WechatArticle;

/**
 * 
 * <pre>
 * 功能描述：常量工具类
 * 使用示范：
 * 
 * 
 * &#64;author (作者) nyz
 * 
 * &#64;since (该版本支持的JDK版本) ：JDK 1.6或以上
 * &#64;version (版本) 1.0
 * &#64;date (开发日期) 2017-9-20
 * &#64;modify (最后修改时间) 
 * &#64;修改人 ：nyz 
 * &#64;审核人 ：
 * </pre>
 */
public class Const {
	// word导出内存
	public static Map<String, ZipDownload> WORD_EXPORT_MAP = new HashMap<>(16);
	// 微信公众号爬虫任务内存
	public static Map<String, WechatArticle> WACT_MAP = new HashMap<>(16);
	// 判断图书是否同步完成
	public static Integer AllSYNCHRONIZATION = 0;
	// 临时文件上传的目录
	public static final String FILE_TEMP_PATH = "/upload/temp";

	public static final String DEFAULT_PASSWORD = "123456";
	public static final String WEB_PROJECT_NAME = "PMPH_PROJECT";
	// 社内部门根节点id
	public static final Long PMPH_DEPARTMENT_ROOT_ID = 0L;
	// 书籍类别根节点id
	public static final Long MATERIAL_TYPE_ID = 0L;
	// 默认
	public static final Integer PAGE_TOTAL = 0;
	// 默认
	public static final Integer PAGE_NUMBER = 1;
	// 默认
	public static final Integer PAGE_SIZE = 5;
	// 单元测试数据是否回滚
	public static final boolean ISROLLBACK = true;
	// 用户SessionId
	public static final String USER_SEESION_ID = "userSessionId";
	// 服务器上
	 public static final String SESSION_ID = "sessionId";

//	public static final String SESSION_ID = "JSESSIONID";
	// PMPH_WECHAT_USER_TOKEN
	public static final String PMPH_WECHAT_USER_TOKEN = "token";
	// PMPH_USER_TOKEN
	public static final String SEESION_PMPH_USER_TOKEN = "sessionPmphUserToken";
	// WRITER_USER_TOKEN
	public static final String SEESION_WRITER_USER_TOKEN = "sessionWriterUserToken";
	// PMPH_USER_TOKEN
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	// PMPH_USER
	public static final String SESSION_PMPH_USER = "sessionPmphUser";
	// WRITER_USER
	public static final String SESSION_WRITER_USER = "sessionWriterUser";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	// 当前菜单
	public static final String SESSION_MENU_LIST = "menuList";
	// 全部菜单
	public static final String SESSION_ALL_MENU_LIST = "allMenuList";
	public static final String SESSION_USERPDS = "userpds";
	// 用户对象
	public static final String SESSION_USERROL = "USERROL";
	// 用户名
	public static final String SESSION_USERNAME = "USERNAME";
	public static final Boolean TRUE = true;
	public static final Boolean FALSE = false;
	// 登录地址
	public static final String LOGIN = "/pmph/login";
	// 图片上传路径
	public static final String FILE_PATH_IMG = "uploadFiles/uploadImgs/";
	// 文件上传路径
	public static final String MSG_FILE_PATH_FILE = "d:/msg/uploadFiles/file/";
	// 文件上传路径
	public static final String FILE_PATH_FILE = "uploadFiles/file/";
	// 不对匹配该值的访问路径拦截（正则）
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(statics)|(websocket)|(operation/*))";
	// 该值会在web容器启动时由WebAppContextListener初始化
	public static ApplicationContext WEB_APP_CONTEXT = null;
	// 普通文件下载控制器方法
	public static final String FILE_DOWNLOAD = "/pmpheep/file/download/";
	/**
	 * 系统消息类型
	 */
	// 系统消息
	public static final Short MSG_TYPE_0 = 0;
	// 站内群发
	public static final Short MSG_TYPE_1 = 1;
	// 站内私信(作家和机构用户不能群发)
	public static final Short MSG_TYPE_2 = 2;
	// 小组互动
	public static final Short MSG_TYPE_3 = 3;
	/**
	 * 发送者类型
	 */
	// 系统
	public static final Short SENDER_TYPE_0 = 0;
	// 社内用户
	public static final Short SENDER_TYPE_1 = 1;
	// 作家用户
	public static final Short SENDER_TYPE_2 = 2;
	// 机构用户
	public static final Short SENDER_TYPE_3 = 3;
	/**
	 * 接收者类型
	 */
	// 社内用户
	public static final Short RECEIVER_TYPE_1 = 1;
	// 作家用户
	public static final Short RECEIVER_TYPE_2 = 2;
	// 机构用户
	public static final Short RECEIVER_TYPE_3 = 3;
	/**
	 * 发送消息类型
	 */
	// 新增
	public static final Short SEND_MSG_TYPE_0 = 0;
	// 撤回
	public static final Short SEND_MSG_TYPE_1 = 1;
	// 删除
	public static final Short SEND_MSG_TYPE_2 = 2;
	/**
	 * 发送消息页面（发送对象）
	 */
	// 学校管理员
	public static final Integer SEND_OBJECT_1 = 1;
	// 所有人
	public static final Integer SEND_OBJECT_2 = 2;
	// 特定对象
	public static final Integer SEND_OBJECT_3 = 3;
	// 教材报名者
	public static final Integer SEND_OBJECT_4 = 4;
	/**
	 * 用户登录类型
	 */
	// 社内用户
	public static final Short LOGIN_TYPE_PMPH = 1;
	// 作家用户
	public static final Short LOGIN_TYPE_WRITER = 2;
	// 机构用户
	public static final Short LOGIN_TYPE_ORG = 3;
	public static final String LOGIN_USER_IS_ADMINS = "超级管理员";
	public static final String LOGIN_USER_IS_ADMIN = "管理员";
	public static final String LOGIN_SYS_USER_IS_ADMIN = "系统管理员";
	/**
	 * 学校审核
	 * 
	 * 0=待审核/1=通过/2=退回
	 */
	public static final Integer ORG_USER_PROGRESS_0 = 0;
	public static final Integer ORG_USER_PROGRESS_1 = 1;
	public static final Integer ORG_USER_PROGRESS_2 = 2;
	/**
	 * 教师审核
	 * 
	 * 0=未提交/1=已提交/2=被退回/3=通过
	 */
	public static final Short WRITER_PROGRESS_0 = 0;
	public static final Short WRITER_PROGRESS_1 = 1;
	public static final Short WRITER_PROGRESS_2 = 2;
	public static final Short WRITER_PROGRESS_3 = 3;
	/**
	 * CMS
	 */
	// CMS 类型
	public static final String CMS_TYPE = "cms";
	// material 教材类型
	public static final String MATERIAL_NOTICE_TYPE = "notice";
	public static final String MATERIAL_NOTE_TYPE = "note";
	// product 临床决断产品类型
	public static final String CLINICAL_DECISION = "clinic";
	// CMS 附件下载Controller方法
	public static final String CMS_FILE_DOWNLOAD = "/pmpheep/file/cms/download/";
	// material教材通知附件下载Controller方法
	public static final String MATERIAL_NOTICE_FILE_DOWNLOAD = "/pmpheep/file/notice/download/";
	public static final String MATERIAL_NOTE_FILE_DOWNLOAD = "/pmpheep/file/note/download/";
	// product 临床决断产品 附件下载Controller方法
	public static final String CLINICAL_DECISION_FILE_DOWNLOAD = "/pmpheep/file/clinic/download/";
	// 作者类型--系统
	public static final Short CMS_AUTHOR_TYPE_0 = 0;
	// 作者类型--编辑
	public static final Short CMS_AUTHOR_TYPE_1 = 1;
	// 作者类型--作家
	public static final Short CMS_AUTHOR_TYPE_2 = 2;
	// CMS 页面搜索状态(0:是否发布,1:是否审核,2:是否置顶,3:是否热门,4:是否推荐,5:是否隐藏)
	public static final Integer CMS_PAGE_SEARCH_0 = 0;
	public static final Integer CMS_PAGE_SEARCH_1 = 1;
	public static final Integer CMS_PAGE_SEARCH_2 = 2;
	public static final Integer CMS_PAGE_SEARCH_3 = 3;
	public static final Integer CMS_PAGE_SEARCH_4 = 4;
	public static final Integer CMS_PAGE_SEARCH_5 = 5;
	// CMS 审核状态(0=待审核/1=审核未通过/2=通过)
	public static final Short CMS_AUTHOR_STATUS_0 = 0;
	public static final Short CMS_AUTHOR_STATUS_1 = 1;
	public static final Short CMS_AUTHOR_STATUS_2 = 2;

	// Cms_Category_Role permissionType字段(1:后台操作权限,2:审核权限)
	public static final Short CMS_CATEGORY_PERMISSSION_1 = 1;
	public static final Short CMS_CATEGORY_PERMISSSION_2 = 2;
	public static final Long CMS_MATERIAL_ID = 0L;
	/**
	 * CMS栏目类型（0：评论，1：内容管理，2：信息快报管理，3：公告管理，4：帮助管理）
	 */
	public static final Long CMS_CATEGORY_ID_0 = 0L;
	public static final Long CMS_CATEGORY_ID_1 = 1L;
	public static final Long CMS_CATEGORY_ID_2 = 2L;
	public static final Long CMS_CATEGORY_ID_3 = 3L;
	public static final Long CMS_CATEGORY_ID_4 = 4L;

	/**
	 * 问卷状态(0=未发送/1=发送/2=回收)
	 */
	public static final Short SURVEY_STATUS_0 = 0;
	public static final Short SURVEY_STATUS_1 = 1;
	public static final Short SURVEY_STATUS_2 = 2;
	/**
	 * 问卷问题类型(1:单选,2:多选,3:下拉,4:单行文本,5:多行文本,6:附件)
	 */
	public static final Short SURVEY_QUESTION_TYPE_1 = 1;
	public static final Short SURVEY_QUESTION_TYPE_2 = 2;
	public static final Short SURVEY_QUESTION_TYPE_3 = 3;
	public static final Short SURVEY_QUESTION_TYPE_4 = 4;
	public static final Short SURVEY_QUESTION_TYPE_5 = 5;
	public static final Short SURVEY_QUESTION_TYPE_6 = 6;
	/**
	 * 用户动态表
	 * 
	 * *注1：0=其他(教材申报、我要出书、图书纠错、问卷调查)/1=发表文章/2=文章评论/3=文章收藏/4=文章点赞/5=发表书评/6=图书收藏/7=图书点赞
	 */
	public static final Integer WRITER_USER_TRENDST_TYPE_0 = 0;
	public static final Integer WRITER_USER_TRENDST_TYPE_1 = 1;
	public static final Integer WRITER_USER_TRENDST_TYPE_2 = 2;
	public static final Integer WRITER_USER_TRENDST_TYPE_3 = 3;
	public static final Integer WRITER_USER_TRENDST_TYPE_4 = 4;
	public static final Integer WRITER_USER_TRENDST_TYPE_5 = 5;
	public static final Integer WRITER_USER_TRENDST_TYPE_6 = 6;
	public static final Integer WRITER_USER_TRENDST_TYPE_7 = 7;
}
