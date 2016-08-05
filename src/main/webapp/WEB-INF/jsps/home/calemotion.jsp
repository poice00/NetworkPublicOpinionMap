<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>情感分析</title>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
<style type="text/css">
	${demo.css}
</style>
<script type="text/javascript">
	$(function() {
		$('#container')
				.highcharts(
						{
							chart : {
								type : 'scatter',
								zoomType : 'xy'
							},
							title : {
								text : '情感分析散点图'
							},
							subtitle : {
								text : '--基于用户评论'
							},
							xAxis : {
								title : {
									enabled : true,
									text : '活跃度'
								},
								startOnTick : true,
								endOnTick : true,
								showLastLabel : true
							},
							yAxis : {
								title : {
									text : '情感值'
								}
							},
							legend : {
								layout : 'vertical',
								align : 'left',
								verticalAlign : 'top',
								x : 100,
								y : 70,
								floating : true,
								backgroundColor : (Highcharts.theme && Highcharts.theme.legendBackgroundColor)
										|| '#FFFFFF',
								borderWidth : 1
							},
							plotOptions : {
								scatter : {
									marker : {
										radius : 5,
										states : {
											hover : {
												enabled : true,
												lineColor : 'rgb(100,100,100)'
											}
										}
									},
									states : {
										hover : {
											marker : {
												enabled : false
											}
										}
									},
									tooltip : {
										headerFormat : '<b></b><br>',
										pointFormat : '{point.x} cm, {point.y} kg'
									}
								}
							},
							series : [ {
								name : '',
								color : 'rgba(223, 83, 83, .5)', 
								data : [ 
								         <c:forEach items="${eaList}" var="ea">
								         [ ${ea.authorActiveDegree}, ${ea.emotionAvg} ], 
								         </c:forEach>
								         ]

							} /* , {
							            name: 'Male',
							            color: 'rgba(119, 152, 191, .5)',
							            data: [180.3, 83.2]
							        } */]
						});
	});
</script>
</head>
<body>
	<script
		src="<%=request.getContextPath()%>/resources/js/highcharts/js/highcharts.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/highcharts/js/modules/data.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/highcharts/js/modules/drilldown.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/highcharts/js/highcharts-3d.js"></script>
	<script
		src="<%=request.getContextPath()%>/resources/js/highcharts/js/modules/exporting.js"></script>


	<div id="container"
		style="min-width: 310px; height: 800px; max-width: 800px; margin: 0 auto"></div>

</body>
</html>
