<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link type="text/css" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<div class="panel panel-default">
	<div class="page-header">
		<h1 align="center">房地产专题热帖</h1>
	</div>
	<div class="panel-body">
		<table class="table table-hover">
			<tr>
			    <td>标题</td>
			    <td>发帖人</td>
				<td>浏览量</td>
				<td>回复数</td>
				<td>热度</td>
			</tr>
			<c:forEach items="${topicHot}" var="item">
				<tr>
				        <td>${item.title }</td>
				        <td>${item.writer.writerName }</td>
						<td>${item.visitNum}</td>
						<td>${item.replyNum}</td>
						<td>${item.hotNum}</td>
				</tr>
			</c:forEach>
		
		</table>
	</div>
	</div>
</body>
</html>