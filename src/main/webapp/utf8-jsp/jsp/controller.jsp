<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.bc.pmpheep.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
    response.setHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin,Access-Control-Allow-Credentials");
	response.setHeader("Access-Control-Allow-Headers", "Accept,Origin,X-Requested-With,X_Requested_With,Content-Type,Last-Modified,Access-Control-Allow-Origin,x-auth-token");
	response.setHeader("Access-Control-Max-Age","3600");
	response.setHeader("Content-Type" , "text/html");
	
	
	String rootPath = application.getRealPath( "/" );
	
	out.write( new ActionEnter( request, rootPath,rootPath+"utf8-jsp\\jsp\\config.json" ).exec() );
	
%>