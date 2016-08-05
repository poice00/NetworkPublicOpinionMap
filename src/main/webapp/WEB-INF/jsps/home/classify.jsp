<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>领域分类</title>

		<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
		<style type="text/css">
${demo.css}
		</style>
		<script type="text/javascript">
$(function () {
    // Create the chart
    var classify= $('#classify').text();
    var detail= $('#detail').text();
    var dataObj=eval("("+classify+")");//转换为json对象。这是关键
    var detailObj=eval("("+detail+")");//转换为json对象。这是关键
    $('#container').highcharts({
    	 chart: {
             type: 'pie',
             options3d: {
                 enabled: false,
                 alpha: 45,
                 beta: 0
             }
         },
        title: {
            text: '领域分类饼状图'
        },
        /* subtitle: {
            text: '点击查看领域详细信息'
        }, */
        plotOptions: {
        	 pie: {
                 allowPointSelect: true,
                 cursor: 'pointer',
                 depth: 35,
                 dataLabels: {
                     enabled: true,
                     format: '{point.name}: {point.y:.1f}%'
                 }
             }
        },

        tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> of total<br/>'
        },
        series: [{
        	type: 'pie',
            name: "领域",
            colorByPoint: true,
            data: dataObj
        }],
        drilldown: {
            series: [{
                name: "经济",
                id: "经济",
                data: detailObj
            }]
        }
    });
});
		</script>
	</head>
	<body>
<script src="<%=request.getContextPath()%>/resources/js/highcharts/js/highcharts.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/highcharts/js/modules/data.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/highcharts/js/modules/drilldown.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/highcharts/js/highcharts-3d.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/highcharts/js/modules/exporting.js"></script>

<div id="container" style="min-width: 310px; max-width: 600px; height: 800px; margin: 0 auto"></div>
<div id="classify" style="display: none">${classify }</div>	
<div id="detail" style="display: none">${detail }</div>	

	</body>
</html>
