<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>核心企业--主页</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/online/jquery.min.js"></script>
	<link href="${ctxStatic}/online/prettyPhoto.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<script src="${ctxStatic}/online/script.js" type="text/javascript"></script>
	<script src="${ctxStatic}/online/jquery.prettyPhoto.js" type="text/javascript"></script>
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">

	<script src="${ctxStatic}/online/lgt-web.js"></script>
	<script src="${ctxStatic}/echarts-2.2.7/build/dist/echarts-all.js" type="text/javascript"></script>

	<script>
		$(function(){
			supplierGraph();
			financingGraph();
		});

		//供应商签约统计图
		function supplierGraph(){
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/CoreEnterpriseCtrl/getSupplierStatistics?id=${fns:getUser().core.id}",
				data : {  },
				type: "get",
				dataType: "json",
				success: function(data, status, xhr){
					var noQianYue = 0;
					var yesQianYue = 0;
					if(data != null && data.length > 0){
						$.each(data, function(key, value){
							if($.isEmpty(value["state"]) || value["state"] == "0" || value["state"] == "1" || value["state"] == "2" || value["state"] == "3"){
								noQianYue++;
							}else{
								yesQianYue++;
							}
						});
					}
					createBieGraph(noQianYue, yesQianYue);
				},
				error: function(xhr, status, error){
					layer.msg(status);
				}
			});
		}

		//供应商融资统计图
		function financingGraph(){
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/CoreEnterpriseCtrl/getFinancingStatistics?id=${fns:getUser().core.id}",
				data : {  },
				type: "get",
				dataType: "json",
				success: function(data, status, xhr){
					var dates = [];
					var datas1 = [];
					var datas2 = [];
					//获取7天日期数组
					for(var i = 0; i < 7; i++){
						var dt = new Date();
						dt.setDate(dt.getDate() - i);
						dates.unshift(dt.format("MM-dd"));
					}
					//处理数据
					if(data != null && data.length > 0){
						$.each(dates, function(key, value){
							var dt = value;
							var amount1 = 0;
							var amount2 = 0;
							$.each(data, function(key, value){
								if(!$.isEmpty(value["generationDate"]) && value["generationDate"].indexOf(dt) != -1){
									if(value["state"] == "9" || value["state"] == "10" || value["state"] == "11"){
										var amount = parseFloat($.isEmpty(value["loanAmount"]) ? value["totalAmount"] : value["loanAmount"]);
										amount1 += amount;
									}else{
										var amount = parseFloat(value["totalAmount"]);
										amount2 += amount;
									}
								}
							});
							datas1.push(amount1);
							datas2.push(amount2);
						});
					}
					createLineGraph(dates, datas1, datas2);
				},
				error: function(xhr, status, error){
					layer.msg(status);
				}
			});
		}

		//创建供应商饼状图
		function createBieGraph(noQianYue, yesQianYue){
		    // 基于准备好的dom，初始化echarts实例
		    var myChart = echarts.init(document.getElementById('graph1'));
		    // 指定图表的配置项和数据
		    var option = {
		        title: {
		            text: '',
		            top: 'top',
		            x: 'center',
		            textStyle: {
		                fontSize: 12
		            }
		        },
		        toolbox: {
		            top: 'top',
		            left: 'left'
		        },
		        tooltip: {
		            trigger: 'item',
		            formatter: "{a}<br/>{b}:{c}({d}%)"
		        },
		        legend: {
		            top: 'vertiacl',
		            x: 'left',
		            data: ['已签约供应商','未签约供应商']
		        },
		        series: [
		            {
		                name: '数量',
		                type: 'pie',
		                radius: '55%',
						center:['50%','60%'],
		                itemStyle: {
		                    normal: {
		                        color: function (value) {
		                            switch (value.data.name) {
		                                case "已签约供应商":
		                                    return "#46d26b";
		                                default:
		                                    return "#5494d8";
		                            }
		                        }
		                    }
		                },
		                data: [{value:yesQianYue, name:'已签约供应商'}, {value:noQianYue, name:'未签约供应商'}]
		            }
		        ]
		    };
		    myChart.setOption(option);
		}

		//创建融资折线图
		function createLineGraph(dates, datas1, datas2){
		    // 基于准备好的dom，初始化echarts实例
		    var myChart = echarts.init(document.getElementById('graph2'));
		    // 指定图表的配置项和数据
		    var option = {
		        title: {
		            text: '',
		            top: 'top',
		            left: 'center',
		            textStyle: {
		                fontSize: 12
		            }
		        },
		        toolbox: {
		            top: 'top',
		            left: 'left'
		        },
		        tooltip: {
		            trigger: 'axis',
		            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
		                type: 'line'        // 默认为直线，可选为：'line' | 'shadow'
		            }
		        },
		        legend: {
		            top: 'top',
		            right: 'right',
		            data: ['通过贷款额','申请融资额']
		        },
		        xAxis: {
		            type: 'category',
		            nameRotate: 30,
		            axisLabel: {
		                interval: 0,
		                rotate: 30
		            },
		            data: dates
		        },
		        yAxis: {
		            type: 'value',
		            splitLine: {show: true},
		            show: true,
		            position: 'left'
		        },
		        series: [
		            {
		                name: '通过贷款额',
		                type: 'line',
		                barGap: '5%',
		                label: {
		                    normal: {
		                        show: true,
		                        position: 'top'
		                    }
		                },
						lineStyle:{
							normal:{
								width:1,  //连线粗细
								color: "#5494d8"  //连线颜色
							}
						},
		                itemStyle: {
		                    normal: {
		                        color: function (value) {
                                    return "#5494d8";
		                        }
		                    }
		                },
		                data: datas1
	            	},
					{
	                	name: '申请融资额',
		                type: 'line',
		                barGap: '5%',
		                label: {
		                    normal: {
		                        show: true,
	                        	position: 'top'
		                    }
		                },
						lineStyle:{
							normal:{
								width:1,  //连线粗细
								color: "#8553f9"  //连线颜色
							}
						},
		                itemStyle: {
		                    normal: {
		                        color: function (value) {
                                    return "#8553f9";
		                        }
		                    }
		                },
		                data: datas2
		            }
		        ]
		    };

		    // 使用刚指定的配置项和数据显示图表。
		    myChart.setOption(option);
		}
	</script>
</head>
<body class="gray-bg">
	<div class="main_body clear" style="margin: 0;">
  		<div class="lines">
   			<ul>
      			<li style="width: 48%; margin-left: 1%; margin-right: 2%;"><div class="lines_l"><img src="${ctxStatic}/images/lines_01.jpg"></div><div class="lines_r" style="width: 82%;">
      					<c:if test="${empty core_enterprise.paramsId.allQuota}">
       	 					<span>
								<fmt:formatNumber type="currency" currencySymbol="￥" value="0" />
							</span>
       	 				</c:if>
       	 				<c:if test="${not empty core_enterprise.paramsId.allQuota}">
       	 					<span>
								<fmt:formatNumber type="currency" currencySymbol="￥" value="${core_enterprise.paramsId.allQuota}" />
							</span>
       	 				</c:if>
					总额度（元）
      				</div>
      			</li>
      			<li style="width: 48%; margin-right: 0;"><div class="lines_l"><img src="${ctxStatic}/images/lines_02.jpg"></div><div class="lines_r" style="width: 82%;">
      					<c:if test="${empty core_enterprise.paramsId.availableQuota}">
          					<span>
								<fmt:formatNumber type="currency" currencySymbol="￥" value="0" />
							</span>
          				</c:if>
          				<c:if test="${not empty core_enterprise.paramsId.availableQuota}">
          					<span>
								<fmt:formatNumber type="currency" currencySymbol="￥" value="${core_enterprise.paramsId.availableQuota}" />
							</span>
          				</c:if>
					可用额度（元）
      				</div>
      			</li>
      			<li style="display: none; margin: 0; width: 32%;">
					<div class="lines_l"><img src="${ctxStatic}/images/lines_03.jpg"></div><div class="lines_r">
						<span>
							<fmt:formatNumber type="currency" currencySymbol="￥" value="${profit}" />
						</span>收益（元）
					</div>
				</li>
    		</ul>
  		</div>
  		<div class="main clear" style="width: 100%;">
    		<div class="curve">
      			<div class="curve_left" style="width: 49%; margin-right: 2%;">
        			<div class="curve_header" style="width: 100%; border: none;"><img src="${ctxStatic}/images/icon_02.jpg">供应商签约一览表</div>
        			<div class="curve_con" style="width: 100%; height: 310px;" id="graph1"></div>
      			</div>
      			<div class="curve_right" style="width: 49%;">
        			<div class="curve_header" style="width: 100%; border: none;"><img src="${ctxStatic}/images/icon_03.jpg">供应商每天贷款情况</div>
        			<div class="curve_con" style="width: 100%; height: 310px;" id="graph2"></div>
      			</div>
      			<div class="clear"></div>
    		</div>
    		<div class="core clear" style="width: 100%; height: 140px;">
      			<ul>
        			<li style="width: 49%; margin-right: 2%;">
          				<div class="core_left" style="width: 110px; height: 110px;"><img style="width: 110px; height: 110px;" src="${ctxStatic}/images/icon_05.png"></div>
          				<div class="core_right" style="width: 75%; height: 110px;">
            				<div class="core_title"><span>逾期还款</span><font>0</font></div>
            				<span class="core_nei" style="height: 25px; line-height: 25px;">供应商融资成功后，核心企业未在截止还款期之前还款。</span>
            				<a href="${ctx}/rzglall/hxqylist?billId.coreEnterpriseId.id=${fns:getUser().core.id}" class="core_an" style="margin-top: 0px;">查看逾期还款</a>
          				</div>
        			</li>
        			<li style="margin:0; width: 49%;">
          				<div class="core_left" style="width: 110px; height: 110px;"><img style="width: 110px; height: 110px;" src="${ctxStatic}/images/icon_06.png"></div>
       					<div class="core_right" style="width: 75%; height: 110px;">
           	 				<div class="core_title"><span>应付账款</span></div>
            				<span class="core_nei" style="height: 25px; line-height: 25px;">上传应付账款（交易流水），供应商根据账款融资。</span>
            				<a href="${ctx}/yfzk/list?coreEnterpriseId.id=${fns:getUser().core.id}" class="core_an" style="margin-top: 0px;">上传应付账款</a>
          				</div>
        			</li>
      			</ul>
    		</div>
   			<div class="clear"></div>
  		</div>
  		<div class="clear"></div>
	</div>

	<script>
		document.onreadystatechange = subSomething;//当页面加载状态改变的时候执行这个方法.
		function subSomething()
		{
			if(document.readyState == "complete") //当页面加载状态
				parent.setIframeHeight($(".main_body").height());
		}
	</script>
</body>
</html>