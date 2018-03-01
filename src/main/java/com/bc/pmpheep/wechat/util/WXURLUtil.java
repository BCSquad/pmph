package com.bc.pmpheep.wechat.util;

/**
 * 微信URL常量
 * 
 */
public class WXURLUtil {
    /********* 部门 **********/
    // 创建部门地址
    public static String       CREATE_DEPARTMENT_URL   =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=ACCESS_TOKEN";
    // 更新部门地址
    public static String       UPDATE_DEPARTMENT_URL   =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=ACCESS_TOKEN";
    // 删除部门地址
    public static String       DELETE_DEPARTMENT_URL   =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token=ACCESS_TOKEN&id=DEPTID";
    // 获取部门列表地址
    public static String       GETLIST_DEPARTMENT_URL  =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN";
    public static String       GETLIST_DEPARTMENT_URL2 =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token=ACCESS_TOKEN&id=ID";

    /********* 标签 **********/
    // 创建标签地址
    public static String       CREATE_TAG_URL          =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/tag/create?access_token=ACCESS_TOKEN";
    // 更新标签地址
    public static String       UPDATA_TAG_URL          =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/tag/update?access_token=ACCESS_TOKEN";
    // 删除标签地址
    public static String       DELETE_TAG_URL          =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/tag/delete?access_token=ACCESS_TOKEN&tagid=ID";
    // 获取标签成员地址
    public static String       GET_TAG_PERSON          =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/tag/get?access_token=ACCESS_TOKEN&tagid=ID";
    // 增加标签成员地址
    public static String       ADD_TAG_PERSON          =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/tag/addtagusers?access_token=ACCESS_TOKEN";
    // 删除标签成员地址
    public static String       DELETE_TAG_PERSON       =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/tag/deltagusers?access_token=ACCESS_TOKEN";

    /********* 成员 **********/
    // 创建成员地址
    public static String       CREATE_USER_URL         =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token=ACCESS_TOKEN";
    // 更新成员地址
    public static String       UPDATE_USER_URL         =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token=ACCESS_TOKEN";
    // 删滁成员地址
    public static String       DELETE_USER_URL         =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token=ACCESS_TOKEN&userid=USERID";
    // 获取成员地址
    public static String       GET_USER_PERSON_URL     =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&userid=USERID";
    // 获取部门成员地址
    public static String       GET_USER_GROUP_URL      =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPTID&fetch_child=0&status=0";
    // 邀请成员关注地址
    public static String       INVITE_USER_URL         =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/invite/send?access_token=ACCESS_TOKEN";

    /********* 消息 **********/
    // 发送消息
    public static String       SEND_MSG_URL            =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=ACCESS_TOKEN";

    // 二次验证的URL
    public static String       SECODECHECK_URL         =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/user/authsucc?access_token=ACCESS_TOKEN&userid=USERID";

    // 百度地图搜索网址
    public static String       BAIDUMAP_URL            = "http://api.map.baidu.com/place/v2/search";
    // 获得微信jssdk 票据jsapi_ticket
    public static String       JSAPIURL                =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKEN";

    // 上传媒体文件
    public static String       DOWNLOAD_MEDIA_URL      =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
    // 下载媒体文件
    public static String       UPLOAD_MEDIA_URL        =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    // 凭证获取accessToken（GET）
    public final static String TOKEN_URL               =
                                                       "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=CORPID&corpsecret=CORPSECRET";

}
