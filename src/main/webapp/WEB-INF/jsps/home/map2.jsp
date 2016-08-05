<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
<head>

<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/highcharts/js/highcharts.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/highcharts/js/modules/data.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/highcharts/js/modules/drilldown.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/highcharts/js/highcharts-3d.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/highcharts/js/modules/exporting.js"></script>
<link type="text/css" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet"/>
  <script>
  $(function () {
	    $('#container').highcharts({
	        chart: {
	            type: 'pie',
	            options3d: {
	                enabled: true,
	                alpha: 45,
	                beta: 0
	            }
	        },
	        title: {
	            text: ''
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                depth: 35,
	                dataLabels: {
	                    enabled: true,
	                    format: '{point.name}'
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: 'Browser share',
	            data: [
						['北京',16.67], 
						['重庆',2.56],
						['广东',18.59],
						['浙江',2.5],
						['天津',0.6], 
						['澳门',0.6],
						['广西',0.6], 
						['江西',3.8], 
						['贵州',1.9], 
						['安徽',3.2], 
						['陕西',2.5], 
						['辽宁',1.9], 
						['山西',1.2],
						['香港',1.2], 
						['四川',5.1], 
						['江苏',3.2], 
						['河北',1.2], 
						['福建',3.2], 
						['吉林',1.2], 
						['海南',3.2], 
						['上海',6.4], 
						['湖北',3.8], 
						['云南',1.2], 
						['湖南',1.9], 
						['河南',2.5], 
						['山东',4.4], 
						['海外',3.2]
	            ]
	        }]
	    });
	});				
  
  </script>
</head>
<body>
<div class="panel panel-default">
	<div class="page-header">
		<a href="<%=request.getContextPath()%>/leader/influence"><button type="button" class="btn btn-default">影响力</button></a>
		<a href="<%=request.getContextPath()%>/leader/activedegree"><button type="button" class="btn btn-default">活跃度</button></a>
		<a href="<%=request.getContextPath()%>/leader/influence2"><button type="button" class="btn btn-default">发布者价值</button></a>
		<a href="<%=request.getContextPath()%>/leader/rank"><button type="button" class="btn btn-default">倾向性分析</button></a>
		<a href="<%=request.getContextPath()%>/home/map"><button type="button" class="btn btn-default active" >地域分布</button></a>
		<h1 align="center">意见领袖地域分布饼状图</h1>
	</div>
	<div class="panel-body">
  	 <div id="container" style="min-width:700px;height:400px"></div>
  	 <div align="center">
   <a href="<%=request.getContextPath()%>/home/map"><button type="button" class="btn btn-default">切换柱状图</button></a>
  </div>
  </div>
  </div>
</body>
</html>