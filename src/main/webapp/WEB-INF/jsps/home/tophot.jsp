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
		<h1 align="center">经济领域相关的十大热点子专题</h1>
	</div>
	<div class="panel-body">
		<table class="table table-hover">
			<tr>
			    <td>专题名称</td>
				<td>帖子量</td>
				<td>总浏览量</td>
				<td>总回复数</td>
				<td>参与人数</td>
				<td>专题热度</td>
			</tr>
			<c:forEach items="${themes}" var="item">
				<tr>
				        <td>${item.themeIntro }</td>
						<td>${item.totalTopicNum }</td>
						<td>${item.totalVisit}</td>
						<td>${item.totalReply}</td>
						<td>${item.totalPart}</td>
						<td>${item.theme_hot}</td>
				</tr>
			</c:forEach>
		
		</table>
	</div>
	</div>
</body>
</html>