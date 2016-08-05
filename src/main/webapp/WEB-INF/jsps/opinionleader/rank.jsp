<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link type="text/css" href="<%=request.getContextPath()%>/resources/css/bootstrap.min.css" rel="stylesheet"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/highcharts/js/highcharts.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/highcharts/js/highcharts-3d.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/highcharts/js/modules/exporting.js"></script>
<style type="text/css">
			#container {
				height: 600px; 
				width: 100%;
				margin: 0 auto;
			}
		</style>
<script type="text/javascript">
$(function () {
	 var word= $('#word').text();
    var fre= $('#fre').text();
    var wordObj=eval("("+word+")");//转换为json对象。这是关键
    var freObj=eval("("+fre+")");//转换为json对象。这是关键
    $('#container').highcharts({
        chart: {
            type: 'column',
            margin: 75,
            options3d: {
                enabled: true,
                alpha: 30,
                beta: 15,
                viewDistance: 50,
                depth: 70
            }
        },
        title: {
            text: '意见领袖关键词分布图'
        },
        /* subtitle: {
            text: '意见领袖关键词分布图'
        }, */
        plotOptions: {
            column: {
                depth: 25
            }
        },
        xAxis: {
            categories: wordObj,
        },
        yAxis: {
            title: {
                text: null
            }
        },
        series: [{
            name: '高频词',
            data: freObj
        }]
    });
});
/**
 * Gray theme for Highcharts JS
 * @author Torstein Honsi
 */

Highcharts.theme = {
	colors: ["#DDDF0D", "#7798BF", "#55BF3B", "#DF5353", "#aaeeee", "#ff0066", "#eeaaee",
		"#55BF3B", "#DF5353", "#7798BF", "#aaeeee"],
	chart: {
		backgroundColor: {
			linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
			stops: [
				[0, 'rgb(96, 96, 96)'],
				[1, 'rgb(16, 16, 16)']
			]
		},
		borderWidth: 0,
		borderRadius: 0,
		plotBackgroundColor: null,
		plotShadow: false,
		plotBorderWidth: 0
	},
	title: {
		style: {
			color: '#FFF',
			font: '16px Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif'
		}
	},
	subtitle: {
		style: {
			color: '#DDD',
			font: '12px Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif'
		}
	},
	xAxis: {
		gridLineWidth: 0,
		lineColor: '#999',
		tickColor: '#999',
		labels: {
			style: {
				color: '#999',
				fontWeight: 'bold'
			}
		},
		title: {
			style: {
				color: '#AAA',
				font: 'bold 12px Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif'
			}
		}
	},
	yAxis: {
		alternateGridColor: null,
		minorTickInterval: null,
		gridLineColor: 'rgba(255, 255, 255, .1)',
		minorGridLineColor: 'rgba(255,255,255,0.07)',
		lineWidth: 0,
		tickWidth: 0,
		labels: {
			style: {
				color: '#999',
				fontWeight: 'bold'
			}
		},
		title: {
			style: {
				color: '#AAA',
				font: 'bold 12px Lucida Grande, Lucida Sans Unicode, Verdana, Arial, Helvetica, sans-serif'
			}
		}
	},
	legend: {
		itemStyle: {
			color: '#CCC'
		},
		itemHoverStyle: {
			color: '#FFF'
		},
		itemHiddenStyle: {
			color: '#333'
		}
	},
	labels: {
		style: {
			color: '#CCC'
		}
	},
	tooltip: {
		backgroundColor: {
			linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
			stops: [
				[0, 'rgba(96, 96, 96, .8)'],
				[1, 'rgba(16, 16, 16, .8)']
			]
		},
		borderWidth: 0,
		style: {
			color: '#FFF'
		}
	},


	plotOptions: {
		series: {
			nullColor: '#444444'
		},
		line: {
			dataLabels: {
				color: '#CCC'
			},
			marker: {
				lineColor: '#333'
			}
		},
		spline: {
			marker: {
				lineColor: '#333'
			}
		},
		scatter: {
			marker: {
				lineColor: '#333'
			}
		},
		candlestick: {
			lineColor: 'white'
		}
	},

	toolbar: {
		itemStyle: {
			color: '#CCC'
		}
	},

	navigation: {
		buttonOptions: {
			symbolStroke: '#DDDDDD',
			hoverSymbolStroke: '#FFFFFF',
			theme: {
				fill: {
					linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
					stops: [
						[0.4, '#606060'],
						[0.6, '#333333']
					]
				},
				stroke: '#000000'
			}
		}
	},

	// scroll charts
	rangeSelector: {
		buttonTheme: {
			fill: {
				linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
				stops: [
					[0.4, '#888'],
					[0.6, '#555']
				]
			},
			stroke: '#000000',
			style: {
				color: '#CCC',
				fontWeight: 'bold'
			},
			states: {
				hover: {
					fill: {
						linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
						stops: [
							[0.4, '#BBB'],
							[0.6, '#888']
						]
					},
					stroke: '#000000',
					style: {
						color: 'white'
					}
				},
				select: {
					fill: {
						linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
						stops: [
							[0.1, '#000'],
							[0.3, '#333']
						]
					},
					stroke: '#000000',
					style: {
						color: 'yellow'
					}
				}
			}
		},
		inputStyle: {
			backgroundColor: '#333',
			color: 'silver'
		},
		labelStyle: {
			color: 'silver'
		}
	},

	navigator: {
		handles: {
			backgroundColor: '#666',
			borderColor: '#AAA'
		},
		outlineColor: '#CCC',
		maskFill: 'rgba(16, 16, 16, 0.5)',
		series: {
			color: '#7798BF',
			lineColor: '#A6C7ED'
		}
	},

	scrollbar: {
		barBackgroundColor: {
				linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
				stops: [
					[0.4, '#888'],
					[0.6, '#555']
				]
			},
		barBorderColor: '#CCC',
		buttonArrowColor: '#CCC',
		buttonBackgroundColor: {
				linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
				stops: [
					[0.4, '#888'],
					[0.6, '#555']
				]
			},
		buttonBorderColor: '#CCC',
		rifleColor: '#FFF',
		trackBackgroundColor: {
			linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1 },
			stops: [
				[0, '#000'],
				[1, '#333']
			]
		},
		trackBorderColor: '#666'
	},

	// special colors for some of the demo examples
	legendBackgroundColor: 'rgba(48, 48, 48, 0.8)',
	background2: 'rgb(70, 70, 70)',
	dataLabelsColor: '#444',
	textColor: '#E0E0E0',
	maskColor: 'rgba(255,255,255,0.3)'
};

// Apply the theme
var highchartsOptions = Highcharts.setOptions(Highcharts.theme);
		</script>
</head>
<body>
<div class="panel panel-default">
	<div class="page-header">
		<a href="<%=request.getContextPath()%>/leader/influence"><button type="button" class="btn btn-default">影响力</button></a>
		<a href="<%=request.getContextPath()%>/leader/activedegree"><button type="button" class="btn btn-default">活跃度</button></a>
		<a href="<%=request.getContextPath()%>/leader/influence2"><button type="button" class="btn btn-default">发布者价值</button></a>
		<a href="<%=request.getContextPath()%>/leader/rank"><button type="button" class="btn btn-default active">倾向性分析</button></a>
		<a href="<%=request.getContextPath()%>/home/map"><button type="button" class="btn btn-default">地域分布</button></a>
		<h1 align="center">意见领袖倾向性分析</h1>
	</div>
	<div class="panel-body">
	<div id="container"></div>
	<div id="word" style="display: none">${word }</div>	
	<div id="fre" style="display: none">${fre }</div>	
	<hr>
	<div align="center"">图：年度网络舆情关键词分析</div>	
		
		<div align="center">
		选取影响力和活跃度最高的10个人<br>
		统计每个“意见领袖”使用这些关键词的次数<br>
		提及次数最多的25个词汇,依次是经济、美元、市场、房价、人民币、投资、发展、社会、房地产、货币、股市、资本、企业、人口、危机、金融、资源、国企、战略、腐败、改革、贷款、政治、成本、开发<br>
		其中，提及“经济”的微博占样本总量的10.8％<br>
		提及“美元”的微博占8.11％<br>
		提及“市场”的微博占7.58％<br>
		提及“房价”和“人民币”的微博分别占5.82％和5.48％<br>
		</div>
	</div>
	</div>
	</body>
</body>
</html>