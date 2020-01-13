<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>供应商--主页</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/prettyPhoto.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	
	<script src="${ctxStatic}/online/lgt-web.js"></script>
	<script src="${ctxStatic}/echarts-2.2.7/build/dist/echarts-all.js" type="text/javascript"></script>
	
	<script>
		$(function(){
			queryNoreadMsg();
			financingGraph();
		});
		
		//获取未读消息
		function queryNoreadMsg(){
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/iim/mailBox/queryNoReadMailByAnsyc?receiver.id=${fns:getUser().id}&readstatus=0",
				data : {  },
				type: "get",
				dataType: "json",
				success: function(data, status, xhr){
					$("#no-read-msg-li").empty();
					if(data != null && data.length > 0){
						$.each(data, function(key, value){
							$("#no-read-msg-li").append(
								'<li style="width: 100%; height: 66px; overflow: hidden;" title="'+ value["mail"]["title"] +'"><span class="news_day" style="width: 145px; height: 22px; margin-right: 5px;">'+ value["remarks"] +'</span><br /><a href="${ctx}/iim/mailBox/list?orderBy=sendtime desc">'+ value["mail"]["title"] +'</a></li>'
							);
						});
					}
				},
				error: function(xhr, status, error){
					layer.msg(status);
				}
			});
		}
		
		//融资统计
		function financingGraph(){
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/gys/getFinancingStatistics?id=${fns:getUser().supplier.id}",
				data : {  },
				type: "get",
				dataType: "json",
				success: function(data, status, xhr){
					var datas = [];
					if(data != null && data.length > 0){
						for(var i = 1; i < 13; i++){
							var month = (i + "").length == 1? ("0" + i) : (i + "");
							var amount = 0;
							$.each(data, function(key, value){
								if(!$.isEmpty(value["generationDate"]) && !$.isEmpty(value["totalAmount"])){
									var mth = value["generationDate"].substring(5, 7);
									var loan = value["loanAmount"];
									if(!$.isEmpty(mth) && mth == month){
										if(!$.isEmpty(loan)){
											amount += parseFloat(loan);
										}else{
											amount += parseFloat(value["totalAmount"]);
										}
									}
								}
							});
							datas.push(amount);
						}
					}
					createBar(datas);
				},
				error: function(xhr, status, error){
					layer.msg(status);
				}
			});
		}
		
		//创建柱状图
		function createBar(datas){
		    // 基于准备好的dom，初始化echarts实例
		    var myChart = echarts.init(document.getElementById('graph'));

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
		                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		            }
		        },
		        legend: {
		            top: 'top',
		            right: 'right',
		            data: ['']
		        },
		        grid: {
		            show: true,
		            left: '1%',
		            right: '0px',
		            bottom: '5%',
		            containLabel: true
		        },
		        xAxis: {
		            type: 'category',
		            nameRotate: 30,
		            axisLabel: {
		                interval: 0,
		                rotate: 30
		            },
		            data: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"]
		        },
		        yAxis: {
		            type: 'value',
		            splitLine: {show: true},
		            show: true,
		            position: 'left'
		        },
		        series: [
		            {
		                name: '金额',
		                type: 'bar',
		                barGap: '5%',
		                label: {
		                    normal: {
		                        show: true,
		                        position: 'top'
		                    }
		                },
		                itemStyle: {
		                    normal: {
		                        color: function (value) {
		                        	return "#426786";
		                        }
		                    }
		                },
		                data: datas
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
  		<div class="gong_left" style="width: 330px; margin-right: 20px;">
    		<div class="gong_he" style="width: 100%;">
      			<div class="gong_he_top"><b>${supplier_enterprise.name}</b><br>您好，${fns:getUser().loginName}</div>
      			<div class="gong_he_du" style="width: 100%;">
        			<div class="gong_he_zong" style="width: 49%; margin-right: 2%;"><span class="gong_he_title">总额度（元）</span>
        				<c:if test="${empty supplier_enterprise.paramsId.allQuota}">
       	 					<span class="gong_he_money"><fmt:formatNumber type="currency" currencySymbol="￥" value="0" /></span>
       	 				</c:if>
       	 				<c:if test="${not empty supplier_enterprise.paramsId.allQuota}">
       	 					<span class="gong_he_money"><fmt:formatNumber type="currency" currencySymbol="￥" value="${supplier_enterprise.paramsId.allQuota}" /></span>
       	 				</c:if>
        			</div>
        			<div class="gong_he_ke" style="width: 49%;"><span class="gong_he_title1">可用额度（元）</span>
        				<c:if test="${empty supplier_enterprise.paramsId.availableQuota}">
          					<span class="gong_he_money"><fmt:formatNumber type="currency" currencySymbol="￥" value="0" /></span>
          				</c:if>
          				<c:if test="${not empty supplier_enterprise.paramsId.availableQuota}">
          					<span class="gong_he_money"><fmt:formatNumber type="currency" currencySymbol="￥" value="${supplier_enterprise.paramsId.availableQuota}" /></span>
          				</c:if>
        			</div>
      			</div>
      			<div class="gong_he_tong clear"><a href="${ctx}/gys/gys-mycompany-downloadContract?financingId.billId.supplierEnterpriseId.id=${fns:getUser().supplier.id}" style="width: auto; padding-left: 0; text-align: center;">我的合同</a></div>
      			<div class="clear"></div>
    		</div>
    		<div class="news clear" style="width: 100%;">
      			<div class="news_title"><a href="${ctx}/iim/mailBox/list?orderBy=sendtime desc">更多 <font style="font-family:宋体">&gt;&gt;</font></a><span>最新消息</span></div>
      			<div class="news_list" style="width: 100%;">
        			<ul id="no-read-msg-li"></ul>
        			<div class="clear"></div>
      			</div>
      			<div class="clear"></div>
    		</div>
  		</div>
  		<div class="gong_right" style="width: 850px;">
    		<div class="gong_adv"><img src="${ctxStatic}/images/banner.jpg" height="250px"></div>
    		<div class="gong_e clear">
      			<div class="gong_e_con" style="width: 415px;">
      				<a href="${ctx}/yfzk/gyslist?supplierEnterpriseId.id=${fns:getUser().supplier.id}">
        				<div class="gong_e_l" style="width: 45px; height: 150px;">我要融资</div>
                        <div class="gong_e_r">应收账款${waitFinancingAmount.waitFinancingCount}笔，可立即申请融资<br><b><fmt:formatNumber type="currency" currencySymbol="￥" value="${waitFinancingAmount.waitFinancingAmount}" />元</b></div>
      				</a>
      			</div>
      			<div class="gong_e_con" style="margin:0; width: 415px;">
      				<a href="${ctx}/rzglall/gyslist?billId.supplierEnterpriseId.id=${fns:getUser().supplier.id}">
        				<div class="gong_e_l1" style="width: 45px; height: 150px;">融资余额</div>
        				<div class="gong_e_r">融资余额<br><b><fmt:formatNumber type="currency" currencySymbol="￥" value="${waitFinancingAmount.havingFinancingAmount}" />元</b></div>
      				</a>
  				</div>
      			<div class="clear"></div>
    		</div>
    		<div class="gong_biao clear" style="width: 100%;">
    			<div id="graph" style="height: 240px; margin: 0;"></div>
    		</div>
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