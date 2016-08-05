<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0038)http://www.soften.cn/introduction.html -->
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<title>舆情地图</title>
<link type="text/css" href="<%=request.getContextPath()%>/resources/css/home_files/product.css" rel="stylesheet"/>
<link type="text/css" href="<%=request.getContextPath()%>/resources/css/home_files/style.css" rel="stylesheet"/>
<link type="text/css" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<!-- <div>
	<h1 align="center" style="font-family: '黑体'">网络舆情分析</h1>
</div> -->
<div class="top_wrap">

  <div class="top_center">
  	<h2 align="center" style="font-family: '黑体'">舆情地图</h2>
  </div>
  
  <div class="nav ">
  </div>
	<div class="content clearfix">
		<div class="side sideCore">
			<div class="sCon01">
				<a target="ifrm" href="<%=request.getContextPath()%>/home/classify">数据分类</a>
				<a target="ifrm" href="<%=request.getContextPath()%>/leader/influence">意见领袖</a>
				<a target="ifrm" href="<%=request.getContextPath()%>/home/wordFre/2014/12">词频演化</a>
				<a target="ifrm" href="<%=request.getContextPath()%>/home/networkAnalysis">整体网络图</a>
				<a target="ifrm" href="<%=request.getContextPath()%>/theme/tophot">领域热点舆情</a>
				<a target="ifrm" href="<%=request.getContextPath()%>/home/topicHot">专题热帖</a>
				<a target="ifrm" href="<%=request.getContextPath()%>/theme/guandian">专题观点分析</a>
				<a target="ifrm" href="<%=request.getContextPath()%>/home/uniqueNetworkAnalysis">专题传播网络</a>
				<%-- <a target="ifrm" href="<%=request.getContextPath()%>/home/spread">专题传播趋势</a> --%>
				<a target="ifrm" href="<%=request.getContextPath()%>/emotion/calEmotion">专题情感分析</a>
				<a target="ifrm" href="<%=request.getContextPath()%>/emotion/caution">专题舆情预警</a>
		
			</div>
			
		</div><!--end side-->
		<div class="main">
			<iframe src="<%=request.getContextPath()%>/index.jsp" name="ifrm" frameborder=0 width=100% height=100%>
			</iframe>
		</div><!-- end main -->
	</div>


	<!--footer star-->
	<div class="foot">
		&nbsp;
		<p>
		<span>舆情地图 </span>Copyright &#169; <a href="#"><span style="color:#696969;">sicd</span></a><span style="color:#696969;"> 2015 All Rights Reserved</span>
		</p>
		&nbsp;
	</div>
</div>