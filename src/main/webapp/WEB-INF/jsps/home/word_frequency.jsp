<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<title>词频演化</title>
	<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/word.css" />
</head>
<body>
	<div id="box-3" class="g2-1">
		<img class="biaoti" src="<%=request.getContextPath()%>/resources/img/biaoti03.png" />
		
		<a href="javascript:void(0);" style="display:table;">
			<img id="tb3" class="wendang00" src="<%=request.getContextPath()%>/resources/img/tubiaoxg.png">
		</a>
		<a href="javascript:void(0);" style="display:table;">
			<img id="wb3" class="wendang02" src="<%=request.getContextPath()%>/resources/img/wenbenxg.png">
		</a>
		<a href="javascript:void(0);" target="_black" style="display:table;">
			<img class="wendang04" src="<%=request.getContextPath()%>/resources/img/wendang.png" >
		</a>
		
		<div id="wordfreq1" class="shurukuang00">
			<div id="main3a" style="height:330px; width:303px; float:left;"></div>
			<div id="main3b" style="height:330px; width:303px; float:left;"></div>
			<div id="main3c" style="height:330px; width:303px; float:left;"></div>
		</div>
	</div>
	
	<div id="box-6" class="g2-1">
		<img class="biaoti" src="<%=request.getContextPath()%>/resources/img/biaoti06.png" />
		
		<a href="javascript:void(0);" style="display:table;">
			<img id="tb3" class="wendang00" src="<%=request.getContextPath()%>/resources/img/tubiaoxg.png">
		</a>
		<a href="javascript:void(0);" style="display:table;">
			<img id="wb3" class="wendang02" src="<%=request.getContextPath()%>/resources/img/wenbenxg.png">
		</a>
		<a href="javascript:void(0);" target="_black" style="display:table;">
			<img class="wendang04" src="<%=request.getContextPath()%>/resources/img/wendang.png" >
		</a>
		
		<div id="keyws1" class="shurukuang00" style="text-align:center;">
			<div id="cloud"></div>
		</div>
	</div>
	
	<div id="box-2" class="g2-1">
		<img class="biaoti" src="<%=request.getContextPath()%>/resources/img/biaoti02.png" />
		
		<a href="javascript:void(0);" style="display:table;">
			<img id="tb3" class="wendang00" src="<%=request.getContextPath()%>/resources/img/tubiaoxg.png">
		</a>
		<a href="javascript:void(0);" style="display:table;">
			<img id="wb3" class="wendang02" src="<%=request.getContextPath()%>/resources/img/wenbenxg.png">
		</a>
		<a href="javascript:void(0);" target="_black" style="display:table;">
			<img class="wendang04" src="<%=request.getContextPath()%>/resources/img/wendang.png" >
		</a>
		
		<div id="stcq1" class="shurukuang01">
			<div id="main2" style="height:430px;"></div>
		</div>
		
	</div>
	
	<script src="<%=request.getContextPath()%>/resources/js/dist/echarts.js"></script>
	<%-- <!--注音-->
	<script src="<%=request.getContextPath()%>/resources/js/jquery-1.4.4.min.js"></script>
	
	<!--词云图-->
	<script src="<%=request.getContextPath()%>/resources/js/d3.js"></script>
	<script src="<%=request.getContextPath()%>/resources/js/d3.layout.cloud.js"></script>
	
	<!--词云球Flash-->
	<script src="<%=request.getContextPath()%>/resources/js/flash/flex/swfobject.js"></script>
	
	<!--首页js-->
	<script src="<%=request.getContextPath()%>/resources/js/indexnew.js"></script> --%>
	
	<script type="text/javascript">
        // 路径配置
        require.config({
            paths: {
                echarts: '/NetworkPublicOpinionMap/resources/js/dist'
            }
        });
        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar', // 使用柱状图就加载bar模块，按需加载
                'echarts/chart/force',
				'echarts/chart/chord',
                'echarts/chart/wordCloud'
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main3a')); 
                
                var option = {
                		title : {
                	        text: '名词',
                	    },
                	    tooltip : {
                	        trigger: 'axis'
                	    },
                	    /* legend: {
                	        data:['2011年', '2012年']
                	    }, */
                	    toolbox: {
                	        show : true,
                	        feature : {
                	            mark : {show: true},
                	            dataView : {show: true, readOnly: false},
                	            magicType: {show: true, type: ['line', 'bar']},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
                	    xAxis : [
                	        {
                	            type : 'value',
                	            boundaryGap : [0, 0.01]
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'category',
                	            data : ${nounKey}
                	        }
                	    ],
                	    series : [
                	        {
                	            type:'bar',
                	            data:${nounValue}
                	        }
                	    ]
                };
        
                // 为echarts对象加载数据 
                myChart.setOption(option); 
           
                var myChartVerb = ec.init(document.getElementById('main3b')); 
                var option = {
                		title : {
                	        text: '动词',
                	    },
                	    tooltip : {
                	        trigger: 'axis'
                	    },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	            mark : {show: true},
                	            dataView : {show: true, readOnly: false},
                	            magicType: {show: true, type: ['line', 'bar']},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
                	    xAxis : [
                	        {
                	            type : 'value',
                	            boundaryGap : [0, 0.01]
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'category',
                	            data : ${verbKey}
                	        }
                	    ],
                	    series : [
                	        {
                	            type:'bar',
                	            data:${verbValue}
                	        }
                	    ]
                };
        
                // 为echarts对象加载数据 
                myChartVerb.setOption(option); 
            
                var myChartAdjective = ec.init(document.getElementById('main3c')); 
                var option = {
                		title : {
                	        text: '形容词',
                	    },
                	    tooltip : {
                	        trigger: 'axis'
                	    },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	            mark : {show: true},
                	            dataView : {show: true, readOnly: false},
                	            magicType: {show: true, type: ['line', 'bar']},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
                	    xAxis : [
                	        {
                	            type : 'value',
                	            boundaryGap : [0, 0.01]
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'category',
                	            data : ${adjectiveKey}
                	        }
                	    ],
                	    series : [
                	        {
                	            type:'bar',
                	            data:${adjectiveValue}
                	        }
                	    ]
                };
        
                // 为echarts对象加载数据 
                myChartAdjective.setOption(option); 
                
             	/* 关键词的字符云 */
                function createRandomItemStyle() {
                    return {
                        normal: {
                            color: 'rgb(' + [
                                Math.round(Math.random() * 160),
                                Math.round(Math.random() * 160),
                                Math.round(Math.random() * 160)
                            ].join(',') + ')'
                        }
                    };
                }

                option = {
                    title: {
                        text: 'KeyWords',
                        link: 'http://www.google.com/trends/hottrends'
                    },
                    tooltip: {
                        show: true
                    },
                    series: [{
                        name: '关键词提取',
                        type: 'wordCloud',
                        size: ['80%', '80%'],
                        textRotation : [0, 45, 90, -45],
                        textPadding: 0,
                        autoSize: {
                            enable: true,
                            minSize: 14
                        },
                        data: [${keyWords}]
                    }]
                };
                
                var cloud = ec.init(document.getElementById('keyws1')); 
                cloud.setOption(option); 
                
                /*================================= 实体抽取 =================================*/
                function setDoc(doc){
                	var nodesArr = new Array();
                	var linksArr = new Array();
                	var resultArr = new Array();
                	
                	nodesArr.push({category : 0, name : '文本', value : 6, lable : '文本\n(ROOT)'});
                	nodesArr.push({category : 1, name : '人名', value : 5});
                	nodesArr.push({category : 1, name : '地名', value : 5});
                	nodesArr.push({category : 1, name : '机构名', value : 5});
                	nodesArr.push({category : 1, name : '关键词', value : 5});
                	nodesArr.push({category : 1, name : '作者', value : 5});
                	nodesArr.push({category : 1, name : '媒体', value : 5});
                	
                	linksArr.push({source : '人名', target : '文本', weight : 1, name : '实体'});
                	linksArr.push({source : '地名', target : '文本', weight : 1, name : '实体'});
                	linksArr.push({source : '机构名', target : '文本', weight : 1, name : '实体'});
                	linksArr.push({source : '关键词', target : '文本', weight : 1, name : '实体'});
                	linksArr.push({source : '作者', target : '文本', weight : 1, name : '实体'});
                	linksArr.push({source : '媒体', target : '文本', weight : 1, name : '实体'});
                	
                	for(var i = 0; i < 6; i++){
                		var docArr = doc[i].toString().split("#");
                		
                		var stType = '人名';
                		if(i == 1){
                			stType = '地名';
                		}else if(i == 2){
                			stType = '机构名';
                		}else if(i == 3){
                			stType = '关键词';
                		}else if(i == 4){
                			stType = '作者';
                		}else if(i == 5){
                			stType = '媒体';
                		}
                		
                		for(var j = 0; j < docArr.length; j++){
                			if(docArr[j] != ''){
                				nodesArr.push({category : 2, name : docArr[j], value : 5});
                				linksArr.push({source : docArr[j], target : stType, weight : 1, name: '内容'});
                			}
                		}
                	}
                	
                	resultArr.push(nodesArr);
                	resultArr.push(linksArr);
                	return resultArr;
                }
                
                var doc = new Array('朱娟娟#李彦宏#','汉正街#武汉#中国#武汉市#河南#西安#上海#','清华大学#西安交通大学#北京大学#中国科学院#','介先生#城管#临时工#协管队员#队员#','朱娟娟#','');
				var docArr = new Array();
				docArr = setDoc(doc);
                
                var option2 = {
    					title : {
    						text : '',
    						subtext : '',
    						x : 'right',
    						y : 'bottom'
    					},
    					tooltip : {
    						trigger : 'item',
    						formatter : '{a} : {b}'
    					},
    					toolbox : {
    						show : true,
    						feature : {
    							restore : { show : true },
    							magicType : { show : true, type : ['force', 'chord'] },
    							saveAsImage : { show : true }
    						}
    					},
    					legend : {
    						x : 'left',
    						data : ['实体类型', '实体内容']
    					},
    					series : [{
    						type : 'force',
    						name : "内容",
    						ribbonType : false,
    						categories : [
    							{
    								name : '文本'
    							},
    							{
    								name : '实体类型'
    							},
    							{
    								name : '实体内容'
    							}
    						],
    						itemStyle : {
    							normal : {
    								label : {
    									show : true,
    									textStyle : {
    										color : '#333'
    									}
    								},
    								nodeStyle : {
    									brushType : 'both',
    									borderColor : 'rgba(255,215,0,0.4)',
    									borderWidth : 1
    								},
    								linkStyle : {
    									type : 'curve'
    								}
    							},
    							emphasis : {
    								label : { show : false },
    								nodeStyle : {},
    								linkStyle : {}
    							}
    						},
    						useWorker : false,
    						minRadius : 15,
    						maxRadius : 25,
    						gravity : 1.1,
    						scaling : 1.1,
    						roam : 'move',
    						nodes : docArr[0],
    						links : docArr[1]
    					}]
    				};//配置结束
    				
                var forceChart2 = ec.init(document.getElementById('main2'));
    			forceChart2.setOption(option2);
            }
        );
        
             
       </script>
</body>
</html>