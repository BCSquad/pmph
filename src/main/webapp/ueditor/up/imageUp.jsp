    <%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
             pageEncoding="utf-8"%>
        <%@ page import="java.util.Properties" %>
        <%@ page import="java.util.List" %>
        <%@ page import="java.util.Iterator" %>
        <%@ page import="java.util.Arrays" %>
        <%@ page import="java.io.FileInputStream" %>
        <%@ page import="com.bc.pmpheep.back.controller.ueditor.Uploader" %>
        <%@ page import="java.io.File" %>
        <%@ page import="java.util.Map" %>
        <%@ page import="com.bc.pmpheep.general.service.*" %>
        <%@ page import="com.bc.pmpheep.general.bean.*" %>
        <%@ page import="com.bc.pmpheep.back.util.*" %>
        <%@ page import="org.springframework.context.ApplicationContext" %>
		<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
 <%
           ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext()); 
		   FileService  fileService = (FileService)ctx.getBean("fileService");//如是注解的Service取注解
	
request.setCharacterEncoding( Uploader.ENCODEING );
response.setCharacterEncoding( Uploader.ENCODEING );
String currentPath = request.getRequestURI().replace( request.getContextPath(), "" );
File currentFile = new File( currentPath );
currentPath = currentFile.getParent() + File.separator;
//System.out.println("=======currentPath==========="+currentPath);
//加载配置文件
//String propertiesPath = request.getSession().getServletContext().getRealPath( currentPath + "config.properties" );
String propertiesPath = request.getRealPath("/ueditor/up/config.properties");
//System.out.println("========propertiesPath=========="+propertiesPath);
Properties properties = new Properties();
try {
    properties.load( new FileInputStream( propertiesPath ) );
} catch ( Exception e ) {
    //加载失败的处理
    e.printStackTrace();
}
List<String> savePath = Arrays.asList( properties.getProperty( "savePath" ).split( "," ) );
//获取存储目录结构
if ( request.getParameter( "fetch" ) != null ) {
    response.setHeader( "Content-Type", "text/javascript" );
    //构造json数据
    Iterator<String> iterator = savePath.iterator();
    String dirs = "[";
    while ( iterator.hasNext() ) {
        dirs += "'" + iterator.next() +"'";
        if ( iterator.hasNext() ) {
            dirs += ",";
        }
    }
    dirs += "]";
    response.getWriter().print( "updateSavePath( "+ dirs +" );" );
    return;
}
Uploader up = new Uploader(request);
// 获取前端提交的path路径
String dir = request.getParameter( "dir" );
//System.out.println("========dir=========="+dir);
//普通请求中拿不到参数， 则从上传表单中拿
if ( dir == null ) {
	dir = up.getParameter("dir");
}
if ( dir == null || "".equals( dir ) ) {
    //赋予默认值
    dir = savePath.get( 0 );
    //安全验证
} else if ( !savePath.contains( dir ) ) {
    response.getWriter().print( "{'state':'\\u975e\\u6cd5\\u4e0a\\u4f20\\u76ee\\u5f55'}" );
    return;
}
up.setSavePath( dir );
String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
up.setAllowFiles(fileType);
up.setMaxSize(500 * 1024); //单位KB
up.upload();
String serverProjectPath = System.getProperty("user.dir").replace("bin", "webapps").toString();
System.out.println(serverProjectPath);
File file = FileUpload.getFileByFilePath(serverProjectPath+"/pmpheep/ueditor/up/"+up.getUrl());
String filePath=fileService.saveLocalFile(file,ImageType.SYS_MESSAGE,0L);
response.getWriter().print("{'original':'"+up.getOriginalName()+"','url':'/image/"+filePath+"','title':'"+up.getTitle()+"','state':'"+up.getState()+"'}");
System.out.println("========dir=========="+"{'original':'"+up.getOriginalName()+"','url':'"+up.getUrl()+"','title':'"+up.getTitle()+"','state':'"+up.getState()+"'}");
%>
