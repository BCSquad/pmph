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
        	alert('${UserId}');
        	if(0 == '${isLogin}'){
        		alert(1);
        		window.location.href='http://192.168.100.109:8089/#/login?wechatUserId='+'${UserId}';
        		//window.location.href='http://120.76.221.250/#/login';
        	}else{
        		window.location.href='http://192.168.100.109:8089/#/login?username='+'${username}'+'&password='+'${password}'+'&wechatUserId='+'${UserId}'+'&token='+'${token}';
        	}
		</script>
    </head>
</html>

