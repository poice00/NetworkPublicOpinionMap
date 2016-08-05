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
		<a href="<%=request.getContextPath()%>/leader/influence"><button type="button" class="btn btn-default">影响力</button></a>
		<a href="<%=request.getContextPath()%>/leader/activedegree"><button type="button" class="btn btn-default">活跃度</button></a>
		<a href="<%=request.getContextPath()%>/leader/influence2"><button type="button" class="btn btn-default active">发布者价值</button></a>
		<a href="<%=request.getContextPath()%>/leader/rank"><button type="button" class="btn btn-default">倾向性分析</button></a>
		<a href="<%=request.getContextPath()%>/home/map"><button type="button" class="btn btn-default">地域分布</button></a>
		<h1 align="center">发布者情感倾向</h1>
	</div>
	<div class="panel-body">
		<table class="table table-hover">
			<tr>
				<td>用户</td>
				<td>情感倾向</td>
			</tr>
			<c:forEach items="${resultList}" var="item">
				<tr>
						<td>${item.writer.writerName }</td>
						<td>${item.authorInfluence2}</td>
				</tr>
			</c:forEach>
		
		</table>
	</div>
	<hr>
	<div align="center">
		发布者的情感倾向是舆情监测的重点<br>
		选择排名前10 的负向情绪用户<br>
	</div>
	</div>
</body>
</html>