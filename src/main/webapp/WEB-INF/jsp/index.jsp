<%-- 
    Document   : index
    Created on : 2017-9-10, 17:48:18
    Author     : L.X <gugia@qq.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>PMPH E-education Platform</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    </head>
    <body>
        <div>
            <h2>Welcome to PMPH E-education Platform</h2>
            <form action="file/upload" method="post" enctype="multipart/form-data">
                <input name="file" type="file" class="opt_input" />
                <input type="submit" />
            </form>
            <a href="file/download/59cbd0da588e8a8aa8bb6925">下载文件</a>
            <img src="avatar/59cbd0da588e8a8aa8bb6925" />
            <img src="avatar/59cbd0da588e8a8aa8bb6925" />
            <img src="avatar/59cbd0da588e8a8aa8bb6925" />
            <img src="avatar/59cbd0da588e8a8aa8bb6925" />
            <img src="avatar/59cbd0da588e8a8aa8bb6924" />
            <br>
            <form class="form-inline" action="cms/wechat/article" method="post">
                <div class="form-group">
                    <label for="exampleInputName2">微信公众号文章地址</label>
                    <input type="url" class="form-control" name="url" placeholder="请输入正确的网址">
                </div>
                <button type="submit" class="btn btn-default">获取id</button>
            </form>
        </div>
        <script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    </body>
</html>
