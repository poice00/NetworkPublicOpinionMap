<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>情感分类</title>

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
								type : 'column'
							},
							title : {
								text : '舆情预警柱状图'
							},
							xAxis : {
								categories : [ '2014-01', '2014-02', '2014-03',
										'2014-04', '2014-05', '2014-06',
										'2014-07', '2014-08', '2014-09',
										'2014-10', '2014-11', '2014-12',
										'2015-01', '2015-02', '2015-03' ]
							},
							yAxis : {
								min : 0,
								title : {
									text : '分类情感人数（人）'
								},
								stackLabels : {
									enabled : true,
									style : {
										fontWeight : 'bold',
										color : (Highcharts.theme && Highcharts.theme.textColor)
												|| 'gray'
									}
								}
							},
							legend : {
								align : 'right',
								x : -30,
								verticalAlign : 'top',
								y : 25,
								floating : true,
								backgroundColor : (Highcharts.theme && Highcharts.theme.background2)
										|| 'white',
								borderColor : '#CCC',
								borderWidth : 1,
								shadow : false
							},
							tooltip : {
								formatter : function() {
									return '<b>' + this.x + '</b><br/>'
											+ this.series.name + ': ' + this.y
											+ '<br/>' + 'Total: '
											+ this.point.stackTotal;
								}
							},
							plotOptions : {
								column : {
									stacking : 'normal',
									dataLabels : {
										enabled : true,
										color : (Highcharts.theme && Highcharts.theme.dataLabelsColor)
												|| 'white',
										style : {
											textShadow : '0 0 3px black'
										}
									}
								}
							},
							series : [ {
								name : 'positive',
								data : [<c:forEach items="${positive}" var="p">
								${p}, 
								</c:forEach>]
							}, {
								name : 'normal',
								data : [<c:forEach items="${normal}" var="p">
								${p}, 
								</c:forEach>]
							}, {
								name : 'negative',
								data : [<c:forEach items="${negative}" var="p">
								${p}, 
								</c:forEach>]
							} ]
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
		style="min-width: 310px; height: 800px; margin: 0 auto"></div>
</body>
</html>
