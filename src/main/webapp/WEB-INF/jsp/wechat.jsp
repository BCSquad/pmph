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
        	//alert('${UserId}');
                  if(0 == '${isLogin}'){
                //alert(1);
                window.location.href='http://192.168.100.109:8089/#/login?wechatUserId='+'${UserId}';
                //window.location.href='http://120.76.221.250/#/login';
            }else if(1=='${isLogin}'){
                window.location.href='http://192.168.100.109:8089/#/login?username='+'${username}'+'&password='+'${password}'+'&wechatUserId='+'${UserId}'+'&token='+'${token}';
            }else if(2=='${isLogin}'){
                window.location.href='http://192.168.100.135/#/login?username='+'${username}'+'&password='+'${password}'+'&token='+'${token}'+'&wechatUserId='+'${UserId}';
            }
            <%--var userData={--%>
                <%--token:${sessionPmphUserToken} ,--%>
                <%--sessionId:${userSessionId} ,--%>
                <%--userInfo:${sessionPmphUser} ,--%>
                <%--permissionIds:${pmphUserPermissionIds}--%>
            <%--}--%>


            <%--var Cookie ={--%>
                <%--/**--%>
                 <%--* getCookie--%>
                 <%--* @param name--%>
                 <%--* @returns {*}--%>
                 <%--*/--%>
                <%--get:function (name) {--%>
                    <%--var strCookie = document.cookie;--%>
                    <%--var arrCookie = strCookie.split("; ");--%>
                    <%--for (var i = 0; i < arrCookie.length; i++) {--%>
                        <%--var arr = arrCookie[i].split("=");--%>
                        <%--if (arr[0] == name) return arr[1];--%>
                    <%--}--%>
                    <%--return "";--%>
                <%--},--%>


                <%--/**--%>
                 <%--* 添加cookie--%>
                 <%--* @param name--%>
                 <%--* @param value--%>
                 <%--* @param expiresHours--%>
                 <%--* @param domain--%>
                 <%--*/--%>
                <%--set:function(name, value, expiresHours, domain) {--%>
                    <%--var cookieString = name + "=" + (!!value ? value : '');--%>
                    <%--var date = new Date();--%>

                    <%--if (domain != undefined)--%>
                        <%--domain = ";domain=" + domain;--%>
                    <%--else--%>
                        <%--domain = '';--%>

                    <%--date.setTime(date.getTime() + expiresHours * 3600 * 1000);--%>
                    <%--cookieString = cookieString + domain + "; path=/; expires=" + date.toGMTString();--%>

                    <%--document.cookie = cookieString;--%>
                <%--}--%>
            <%--}--%>
		</script>
    </head>
</html>

