<%-- 
    Document   : wechat
    Created on : 2018-1-26, 1:50:45
    Author     : L.X <gugia@qq.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>PMPH E-education Platform</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript">
            var appLogin = "120.76.221.250";//app 测试微信访问
           // var appLogin = "192.168.100.109"; //正式
           // var appLogin = "127.0.0.1:8088"; // app开发
            var pcLogin = "120.76.221.250";//测试
            //var pcLogin = "192.168.100.135"; //正式
            //var pcLogin = "127.0.0.1:8089"; //开发
            var Cookie ={
                /**
                 * getCookie
                 * @param name
                 * @returns {*}
                 */
                get:function (name) {
                    var strCookie = document.cookie;
                    var arrCookie = strCookie.split("; ");
                    for (var i = 0; i < arrCookie.length; i++) {
                        var arr = arrCookie[i].split("=");
                        if (arr[0] == name) return arr[1];
                    }
                    return "";
                },


                /**
                 * 添加cookie
                 * @param name
                 * @param value
                 * @param expiresHours
                 * @param domain
                 */
                set:function(name, value, expiresHours, domain) {
                    var cookieString = name + "=" + (!!value ? value : '');
                    var date = new Date();

                    if (domain != undefined)
                        domain = ";domain=" + domain;
                    else
                        domain = '';

                    date.setTime(date.getTime() + expiresHours * 3600 * 1000);
                    cookieString = cookieString + domain + "; path=/; expires=" + date.toGMTString();

                    document.cookie = cookieString;
                }
            }

            //获取sessionStorage
            function getSessionStorage(key, format) {
                var data;
                if (sessionStorage.getItem(key)) {
                    if (format == 'json') {
                        data = JSON.parse(sessionStorage.getItem(key));
                    } else {
                        data = sessionStorage.getItem(key);
                    }
                } else {
                    data = false
                }
                return data;
            }
            //写入sessionStorage
            function setSessionStorage(key, content, format) {
                var data;
                if (format == 'json') {
                    data = JSON.stringify(content);
                } else {
                    data = content;
                }
                sessionStorage.setItem(key, data);
            }
             var mySessionStorage = {
                get: getSessionStorage,
                set: setSessionStorage
            }
            function Empty(v) {
                switch (typeof v) {
                    case 'undefined':
                        return true;
                        break;
                    case 'string':
                        if (v.length == 0)
                            return true;
                        break;
                    case 'boolean':
                        if (!v)
                            return true;
                        break;
                    case 'number':
                        if (0 === v)
                            return true;
                        break;
                    case 'object':
                        if (null == v)
                            return true;
                        if (undefined !== v.length && v.length == 0)
                            return true;
                        for (var k in v) {
                            return false;
                        }
                        return false;
                        break;
                }
                return false;
            }

        </script>
        <script type="text/javascript">
        	//alert('${UserId}');
            var userData={
                userSessionId:'${userSessionId}' ,
                sessionPmphUserToken:'${sessionPmphUserToken}',
                sessionPmphUser:${sessionPmphUser} ,
                pmphUserPermissionIds:${pmphUserPermissionIds}
            }
            mySessionStorage.set('currentUser',userData,'json');
            Cookie.set('sessionId','${userSessionId}',2)
            Cookie.set('token','${sessionPmphUserToken}',2)
            if((3 == '${isLogin}'||4 == '${isLogin}') &&
                ((1=='${appType}' && !Empty('${materialId}' &&!Empty('${declarationId}'))
                    ||(2=='${appType}')
                    ||(3=='${appType}'&&!Empty('${bookName}')&&!Empty('${type}')&&!Empty('${id}'))))){
                if(1=='${appType}'){
                    window.location.href= 'http://'+appLogin+'/wx/#/material/${materialId}/expert?declarationId=${declarationId}&sessionId=${userSessionId}'+'&token=${sessionPmphUserToken}'+'&currentUser='+JSON.stringify(userData)+'&permissionIds=${pmphUserPermissionIds}';
                }else if(3=='${appType}'){
                    <%--console.log('http://'+appLogin+'/wx/#/checkbook?bookName=${bookName}&type=${type}&id=${id}&sessionId=${userSessionId}'+'&token=${sessionPmphUserToken}'+'&currentUser='+JSON.stringify(userData)+'&permissionIds=${pmphUserPermissionIds}'--%>
                <%--)--%>
                    window.location.href= 'http://'+appLogin+'/wx/#/checkbook?bookName=${bookName}&type=${type}&id=${id}&sessionId=${userSessionId}'+'&token=${sessionPmphUserToken}'+'&currentUser='+JSON.stringify(userData)+'&permissionIds=${pmphUserPermissionIds}';
                }else{
                    window.location.href= 'http://'+appLogin+'/wx/#/topic/list?sessionId=${userSessionId}'+'&token=${sessionPmphUserToken}'+'&currentUser='+JSON.stringify(userData)+'&permissionIds=${pmphUserPermissionIds}';
                }

            }else if((3 == '${isLogin}'||4 == '${isLogin}') && !Empty('${appType}')){ //从企业微信登录 app vue 如果找不到对应的参数则进入首页
                      window.location.href='http://'+appLogin+'/wx/#/index?sessionId=${userSessionId}'+'&token=${sessionPmphUserToken}'+'&currentUser='+JSON.stringify(userData)+'&permissionIds=${pmphUserPermissionIds}';
            }else if(0 == '${isLogin}'){
                //alert(1);
                //window.location.href='http://'+pcLogin+'/#/login?wechatUserId='+'${UserId}';
                //window.location.href='http://120.76.221.250/#/login';
                window.location.href='http://'+appLogin+'/wx/#/login?wechatUserId='+'${UserId}'+'&isIndexOrCommission='+${isIndexOrCommission};  //app 登录
            }else if(1=='${isLogin}'){
                //window.location.href='http://'+pcLogin+'/#/login?username='+'${username}'+'&password='+'${password}'+'&wechatUserId='+'${UserId}'+'&token='+'${token}';
                window.location.href='http://'+appLogin+'/wx/#/index?sessionId=${userSessionId}'+'&token=${sessionPmphUserToken}'+'&currentUser='+JSON.stringify(userData)+'&permissionIds=${pmphUserPermissionIds}';// 这个地方应该直接跳转到app 首页
            }else if(2=='${isLogin}'){
                //和vue 同步 解决单点登录一闪的问题 、sessionStorage是Html5的特性,IE7以下浏览器不支持
                //window.location.href='http://192.168.100.135/#/login?username='+'${username}'+'&password='+'${password}'+'&token='+'${token}'+'&wechatUserId='+'${UserId}';
                window.location.href='http://'+pcLogin+'/#/index?sessionId=${userSessionId}'+'&token=${sessionPmphUserToken}'+'&currentUser='+JSON.stringify(userData)+'&permissionIds=${pmphUserPermissionIds}';
            }else if(5 == '${isLogin}'){ //代办页面
                window.location.href='http://'+appLogin+'/wx/#/wxMessageList?sessionId=${userSessionId}'+'&token=${sessionPmphUserToken}'+'&currentUser='+JSON.stringify(userData)+'&permissionIds=${pmphUserPermissionIds}';
            }

		</script>
    </head>
</html>

