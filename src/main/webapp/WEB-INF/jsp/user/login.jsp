<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
<form action="/user/doLogin.do" method="post">
用户名：<input name="username" type="text"/>
密码：<input name="password" type="text"/>
<input type="submit" value="登录"/>
</form>
</body>
</html>