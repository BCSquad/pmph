package com.bc.pmpheep.back.util;

import org.springframework.context.ApplicationContext;

/**
 * 项目公用常量类
 * 
 * @author:admin
 * 
 */
public class Const {
    public static final String       DEFAULT_PASSWORD           = "123456";
    public static final Long         PMPHDEPARTMENTROOTID       = 0L;                                                              // 社内部门根节点id
    public static final Integer      PAGETOTAL                  = 0;                                                               // 默认
    public static final Integer      PAGENUMBER                 = 1;                                                               // 默认
    public static final Integer      PAGESIZE                   = 5;                                                               // 默认
    public static final boolean      ISROLLBACK                 = true;                                                            // 单元测试数据是否回滚
    public static final String       WEB_PROJECT_NAME           = "PMPH_IMESP";
    public static final String       SEESION_PMPH_USER_TOKEN    = "sessionPmphUserToken";
    public static final String       SEESION_WRITER_USER_TOKEN  = "sessionWriterUserToken";
    public static final String       SESSION_SECURITY_CODE      = "sessionSecCode";
    public static final String       SESSION_PMPH_USER          = "sessionPmphUser";
    public static final String       SESSION_WRITER_USER        = "sessionWriterUser";
    public static final String       SESSION_ROLE_RIGHTS        = "sessionRoleRights";
    public static final String       SESSION_MENU_LIST          = "menuList";                                                      // 当前菜单
    public static final String       SESSION_ALL_MENU_LIST      = "allMenuList";                                                   // 全部菜单
    public static final String       SESSION_userpds            = "userpds";
    public static final String       SESSION_USERROL            = "USERROL";                                                       // 用户对象
    public static final String       SESSION_USERNAME           = "USERNAME";                                                      // 用户名
    public static final String       TRUE                       = "T";
    public static final String       FALSE                      = "F";
    public static final String       LOGIN                      = "/login_toLogin.do";                                             // 登录地址
    public static final String       FILEPATHIMG                = "uploadFiles/uploadImgs/";                                       // 图片上传路径
    public static final String       FILEPATHFILE               = "uploadFiles/file/";                                             // 文件上传路径
    public static final String       NO_INTERCEPTOR_PATH        =
                                                                ".*/((login)|(logout)|(code)|(app)|(static)|(main)|(websocket)).*"; // 不对匹配该值的访问路径拦截（正则）

    public static ApplicationContext WEB_APP_CONTEXT            = null;                                                            // 该值会在web容器启动时由WebAppContextListener初始化

    /**
     * APP Constants
     */
    // app注册接口_请求协议参数)
    public static final String[]     APP_REGISTERED_PARAM_ARRAY = new String[] { "countries",
        "uname", "passwd", "title", "full_name", "company_name", "countries_code", "area_code",
        "telephone", "mobile"                                  };
    public static final String[]     APP_REGISTERED_VALUE_ARRAY = new String[] { "国籍", "邮箱帐号",
        "密码", "称谓", "名称", "公司名称", "国家编号", "区号", "电话", "手机号"    };

    // app根据用户名获取会员信息接口_请求协议中的参数
    public static final String[]     APP_GETAPPUSER_PARAM_ARRAY = new String[] { "USERNAME" };
    public static final String[]     APP_GETAPPUSER_VALUE_ARRAY = new String[] { "用户名" };

}
