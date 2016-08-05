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
	            type: 'column'
	        },
	        title: {
	            text: ''
	        },
	        xAxis: {
	            categories: [
					'北京',
					'重庆',
					'广东',
					'天津',
					'浙江',
					'澳门',
					'广西',
					'江西',
					'安徽',
					'贵州',
					'陕西',
					'辽宁',
					'山西',
					'香港',
					'四川',
					'江苏',
					'河北',
					'福建',
					'吉林',
					'海南',
					'上海',
					'湖北',
					'云南',
					'湖南',
					'山东',
					'河南',
					'海外',
	            ]
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: '人数(人)'
	            }
	        },
	        tooltip: {
	            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
	            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	                '<td style="padding:0"><b>{point.y:.1f} 人</b></td></tr>',
	            footerFormat: '</table>',
	            shared: true,
	            useHTML: true
	        },
	        plotOptions: {
	            column: {
	                pointPadding: 0.2,
	                borderWidth: 10
	            }
	        },
	        series: [{
	            name: '人数',
	            data: [26, 4, 29, 1, 4, 1, 1, 6, 5, 3, 4, 3, 2, 2, 8,5, 2, 5, 2, 5,10, 6, 2, 3, 7, 4, 5]

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
		<h1 align="center">意见领袖地域分布柱状图</h1>
	</div>
	<div class="panel-body">
 	 <div id="container" style="min-width:700px;height:400px"></div>
 	 <div align="center">
		选取300个意见领袖<br>
		统计每个“意见领袖”的所在地域<br>
		北京、广州、上海占比最多，分别是：17%、18.9%、6.5%<br>
		表明经济领域话题的发起的主要在一线城市<br>
		</div>
 	 <div align="center">
 	 <a href="<%=request.getContextPath()%>/home/map2"><button type="button" class="btn btn-default">切换饼状图</button></a>
 	 </div>
  </div>
 </div>
</body>
</html>