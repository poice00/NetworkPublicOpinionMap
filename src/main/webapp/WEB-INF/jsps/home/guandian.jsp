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
            text: '观点分类饼状图'
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
            name: "观点分类",
            colorByPoint: true,
            data: [{ name: "市场", y: 28,drilldown: "市场" },{ name: "房价", y: 29,drilldown: "房价" },{ name: "房产税", y: 27,drilldown: "房产税" },{ name: "其他", y: 16,drilldown: "其他" }]
        }],
        drilldown: {
            series: [{
                name: "市场",
                id: "市场",
                data: [["市场需求",41],["市场政策",51],["其他",8]]
            },
            {
                name: "房价",
                id: "房价",
                data: [["房价要跌",61],["房价要涨",32],["其他",7]]
            },
            {
                name: "房产税",
                id: "房产税",
                data: [["是否征收",55],["征收标准",40],["其他",5]]
            }
            ]
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


	</body>
</html>
