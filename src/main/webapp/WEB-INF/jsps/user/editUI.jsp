<%@ page language="java" contentType="text/html; charset=UTF-8"  
    pageEncoding="UTF-8"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<!DOCTYPE html>  
<html>  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
<title>Hello</title>  
</head>  
<body>  
  
  <form action="edit" method="post" >
  	<input type="hidden" name="id" value="${user.id}">
	标签名称:<input type="text" name="name" value="${user.name}"><br>
	标签說明:<input type="text" name="description" value="${user.description}"><br>
	<input type="submit" value="修改">
	 <a href="javascript:history.go(-1);">返回</a>
	</form>
</body>  
</html>