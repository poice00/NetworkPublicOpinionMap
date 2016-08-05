<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		<h1 align="center">房地产专题传播网络</h1>
	</div>
	<div class="panel-body">
		<img alt="" src="<%=request.getContextPath()%>/resources/img/uniqueNetwork.png" />
		<table class="table table-hover">
		<tr>
			<td>平均度</td>
			<td>图密度</td>
			<td>网络直径</td>
			<td>模块化指标</td>
			<td>平均路径长度</td>
			<td>平均聚类系数</td>
		</tr>
		<tr>
			<td>1.905</td>
			<td>0.013</td>
			<td>8</td>
			<td>0.716</td>
			<td>2.465</td>
			<td>0.063</td>
		</tr>
	
	</table>
		<div align="center">
			模块度指标：划分出16个社区，其中3 个社区规模远远大于其他社区。
		</div>
	</div>
</div>
</body>
</html>