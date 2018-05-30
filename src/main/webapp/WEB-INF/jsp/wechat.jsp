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

        </script>
        <script type="text/javascript">
        	//alert('${UserId}');
                  if(0 == '${isLogin}'){
                //alert(1);
                window.location.href='http://192.168.100.109:8089/#/login?wechatUserId='+'${UserId}';
                //window.location.href='http://120.76.221.250/#/login';
            }else if(1=='${isLogin}'){
                window.location.href='http://192.168.100.109:8089/#/login?username='+'${username}'+'&password='+'${password}'+'&wechatUserId='+'${UserId}'+'&token='+'${token}';
            }else if(2=='${isLogin}'){
                //和vue 同步 解决单点登录一闪的问题 、sessionStorage是Html5的特性,IE7以下浏览器不支持
              var userData={
                  userSessionId:'${userSessionId}' ,
                  sessionPmphUserToken:'${sessionPmphUserToken}',
                  sessionPmphUser:${sessionPmphUser} ,
                  pmphUserPermissionIds:${pmphUserPermissionIds}
              }
              mySessionStorage.set('currentUser',userData,'json');
              Cookie.set('sessionId','${userSessionId}',2)
              Cookie.set('token','${sessionPmphUserToken}',2)
                //window.location.href='http://192.168.100.135/#/login?username='+'${username}'+'&password='+'${password}'+'&token='+'${token}'+'&wechatUserId='+'${UserId}';
                window.location.href='http://192.168.100.109:8089/#/index?sessionId=${userSessionId}'+'&token=${sessionPmphUserToken}'+'&currentUser='+JSON.stringify(userData)+'&permissionIds=${pmphUserPermissionIds}';
            }

		</script>
    </head>
</html>

