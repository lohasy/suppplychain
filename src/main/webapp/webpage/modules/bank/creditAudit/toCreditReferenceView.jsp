<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>银行--授信管理--授信参考</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css" />
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet" />
	<script src="${ctxStatic}/online/lgt-web.js"></script>
	<script src="${ctxStatic}/echarts-2.2.7/build/dist/echarts-all.js" type="text/javascript"></script>
	
	<script>
		var scrollTop = 0;
		var _before_day1 = '';
		var _before_day2 = '';
		var _before_day3 = '';
		var _before_day4 = '';
		var _before_day5 = '';
		var _end_day1 = '';
		var _end_day2 = '';
		var _end_day3 = '';
		var _end_day4 = '';
		var _end_day5 = '';
		
		$(function(){
			$("body").scroll(function(){
				scrollTop = document.documentElement.scrollTop||document.body.scrollTop;
			});
			
	        var start1 = {
	            elem: '#beginDate1', //对应的id
	            format: 'YYYY-MM-DD',
	            start: laydate.now(),//开始日期
	            istime: false,
	            festival: false, //显示节日
	            istoday: false,
	            choose: function (datas) {
	                _before_day1 = datas;
	            }
	        };
	        laydate(start1);
	        $("#beginDate1").click(function(){
	        	var dateTop = $("#laydate_box").css("top").substring(0, $("#laydate_box").css("top").length - 2);
        		$("#laydate_box").css("top", (parseInt(dateTop) - scrollTop) + "px");
	        });
	        
	        var start2 = {
	            elem: '#beginDate2', //对应的id
	            format: 'YYYY-MM-DD',
	            start: laydate.now(),//开始日期
	            istime: false,
	            festival: false, //显示节日
	            istoday: false,
	            choose: function (datas) {
	                _before_day2 = datas;
	            }
	        };
	        laydate(start2);
	        $("#beginDate2").click(function(){
	        	var dateTop = $("#laydate_box").css("top").substring(0, $("#laydate_box").css("top").length - 2);
        		$("#laydate_box").css("top", (parseInt(dateTop) - scrollTop) + "px");
	        });
		        
	        var start3 = {
	            elem: '#beginDate3', //对应的id
	            format: 'YYYY-MM-DD',
	            start: laydate.now(),//开始日期
	            istime: false,
	            festival: false, //显示节日
	            istoday: false,
	            choose: function (datas) {
	                _before_day3 = datas;
	            }
	        };
	        laydate(start3);
	        $("#beginDate3").click(function(){
	        	var dateTop = $("#laydate_box").css("top").substring(0, $("#laydate_box").css("top").length - 2);
        		$("#laydate_box").css("top", (parseInt(dateTop) - scrollTop) + "px");
	        });
	        
	        var start4 = {
	            elem: '#beginDate4', //对应的id
	            format: 'YYYY-MM-DD',
	            start: laydate.now(),//开始日期
	            istime: false,
	            festival: false, //显示节日
	            istoday: false,
	            choose: function (datas) {
	                _before_day4 = datas;
	            }
	        };
	        laydate(start4);
	        $("#beginDate4").click(function(){
	        	var dateTop = $("#laydate_box").css("top").substring(0, $("#laydate_box").css("top").length - 2);
        		$("#laydate_box").css("top", (parseInt(dateTop) - scrollTop) + "px");
	        });
	        
	        var start5 = {
	            elem: '#beginDate5', //对应的id
	            format: 'YYYY-MM-DD',
	            start: laydate.now(),//开始日期
	            istime: false,
	            festival: false, //显示节日
	            istoday: false,
	            choose: function (datas) {
	                _before_day5 = datas;
	            }
	        };
	        laydate(start5);
	        $("#beginDate5").click(function(){
	        	var dateTop = $("#laydate_box").css("top").substring(0, $("#laydate_box").css("top").length - 2);
        		$("#laydate_box").css("top", (parseInt(dateTop) - scrollTop) + "px");
	        });
	        
			var end1 = {
	            elem: '#endDate1', //对应的id
	            format: 'YYYY-MM-DD',
	            start: laydate.now(),//开始日期
	            istime: false,
	            festival: false, //显示节日
	            istoday: false,
	            choose: function (datas) {
	                _end_day1 = datas;
	            }
	        };
	        laydate(end1);
	        $("#endDate1").click(function(){
	        	var dateTop = $("#laydate_box").css("top").substring(0, $("#laydate_box").css("top").length - 2);
        		$("#laydate_box").css("top", (parseInt(dateTop) - scrollTop) + "px");
	        });
	        
	        var end2 = {
	            elem: '#endDate2', //对应的id
	            format: 'YYYY-MM-DD',
	            start: laydate.now(),//开始日期
	            istime: false,
	            festival: false, //显示节日
	            istoday: false,
	            choose: function (datas) {
	                _end_day2 = datas;
	            }
	        };
	        laydate(end2);
	        $("#endDate2").click(function(){
	        	var dateTop = $("#laydate_box").css("top").substring(0, $("#laydate_box").css("top").length - 2);
        		$("#laydate_box").css("top", (parseInt(dateTop) - scrollTop) + "px");
	        });
	        
	        var end3 = {
	            elem: '#endDate3', //对应的id
	            format: 'YYYY-MM-DD',
	            start: laydate.now(),//开始日期
	            istime: false,
	            festival: false, //显示节日
	            istoday: false,
	            choose: function (datas) {
	                _end_day3 = datas;
	            }
	        };
	        laydate(end3);
	        $("#endDate3").click(function(){
	        	var dateTop = $("#laydate_box").css("top").substring(0, $("#laydate_box").css("top").length - 2);
        		$("#laydate_box").css("top", (parseInt(dateTop) - scrollTop) + "px");
	        });
	        
	        var end4 = {
	            elem: '#endDate4', //对应的id
	            format: 'YYYY-MM-DD',
	            start: laydate.now(),//开始日期
	            istime: false,
	            festival: false, //显示节日
	            istoday: false,
	            choose: function (datas) {
	                _end_day4 = datas;
	            }
	        };
	        laydate(end4);
	        $("#endDate4").click(function(){
	        	var dateTop = $("#laydate_box").css("top").substring(0, $("#laydate_box").css("top").length - 2);
        		$("#laydate_box").css("top", (parseInt(dateTop) - scrollTop) + "px");
	        });
	        
	        var end5 = {
	            elem: '#endDate5', //对应的id
	            format: 'YYYY-MM-DD',
	            start: laydate.now(),//开始日期
	            istime: false,
	            festival: false, //显示节日
	            istoday: false,
	            choose: function (datas) {
	                _end_day5 = datas;
	            }
	        };
	        laydate(end5);
	        $("#endDate5").click(function(){
	        	var dateTop = $("#laydate_box").css("top").substring(0, $("#laydate_box").css("top").length - 2);
        		$("#laydate_box").css("top", (parseInt(dateTop) - scrollTop) + "px");
	        });
			
			queryTransactionContractData();
			queryPaymentListData();
			queryYyrkzlysRecordData();
			queryBillInfoData();
			queryCreditHistoryData();
			queryNearThreeBillInfo();
			queryNearThreePaymentInfo();
			queryNearThreeAccountDay();
			queryNearThreeContract();
			queryNearThreeYyrkzlys();
		});
		
		
		//查询近三年订单汇总数据
		function queryNearThreeBillInfo(){
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/BankCreditAudit/getNearlyThreeBillStatistics?id=${supplier_enterprise.id}",
				data : { },
				type: "get",
				dataType: "json",
				success: function(data, status, xhr){
					if(data != null && data.length == 4){
						var amount = 0;
						for(var i = 0; i < data.length; i++){
							if(i != data.length - 1){
								$("#bill-info-table tbody").find("tr:eq("+ i +")").find("td:eq(0)").text(data[i]["year"]);
								$("#bill-info-table tbody").find("tr:eq("+ i +")").find("td:eq(1)").text(data[i]["allVal"]);
								$("#bill-info-table tbody").find("tr:eq("+ i +")").find("td:eq(2)").text(data[i]["averageVal"]);
								$("#bill-info-table tbody").find("tr:eq("+ i +")").find("td:eq(3)").text(data[i]["maxVal"]);
								$("#bill-info-table tbody").find("tr:eq("+ i +")").find("td:eq(4)").text(data[i]["minVal"]);
								amount += parseInt(data[i]["averageVal"]);
							}else{
								$("#bill-info-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(1)").text(data[i]["allVal"]);
								$("#bill-info-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(2)").text(data[i]["averageVal"]);
								$("#bill-info-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(3)").text(data[i]["maxVal"]);
								$("#bill-info-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(4)").text(data[i]["minVal"]);
							}
						}
						$("#bill-info-table tbody").find("tr:eq(3)").find("td:eq(1)").text(data[3]["threeAverageVal"]);
					}
				}
			});
		}
		
		
		//查询近三年付款汇总数据
		function queryNearThreePaymentInfo(){
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/BankCreditAudit/getNearlyThreePaymentStatistics?id=${supplier_enterprise.id}",
				data : { },
				type: "get",
				dataType: "json",
				success: function(data, status, xhr){
					if(data != null && data.length == 4){
						var amount = 0;
						for(var i = 0; i < data.length; i++){
							if(i != data.length - 1){
								$("#payment-info-table tbody").find("tr:eq("+ i +")").find("td:eq(0)").text(data[i]["year"]);
								$("#payment-info-table tbody").find("tr:eq("+ i +")").find("td:eq(1)").text(data[i]["allVal"]);
								$("#payment-info-table tbody").find("tr:eq("+ i +")").find("td:eq(2)").text(data[i]["averageVal"]);
								$("#payment-info-table tbody").find("tr:eq("+ i +")").find("td:eq(3)").text(data[i]["maxVal"]);
								$("#payment-info-table tbody").find("tr:eq("+ i +")").find("td:eq(4)").text(data[i]["minVal"]);
								amount += parseInt(data[i]["averageVal"]);
							}else{
								$("#payment-info-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(1)").text(data[i]["allVal"]);
								$("#payment-info-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(2)").text(data[i]["averageVal"]);
								$("#payment-info-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(3)").text(data[i]["maxVal"]);
								$("#payment-info-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(4)").text(data[i]["minVal"]);
							}
						}
						$("#payment-info-table tbody").find("tr:eq(3)").find("td:eq(1)").text(data[3]["threeAverageVal"]);
					}
				}
			});
		}
		
		
		//查询近三年账期汇总数据
		function queryNearThreeAccountDay(){
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/BankCreditAudit/getNearlyThreeAccountDayStatistics?id=${supplier_enterprise.id}",
				data : { },
				type: "get",
				dataType: "json",
				success: function(data, status, xhr){
					if(data != null && data.length == 4){
						var day = 0;
						for(var i = 0; i < data.length; i++){
							if(i != data.length - 1){
								$("#account-days-table tbody").find("tr:eq("+ i +")").find("td:eq(0)").text(data[i]["year"]);
								$("#account-days-table tbody").find("tr:eq("+ i +")").find("td:eq(1)").text(data[i]["averageVal"]);
								$("#account-days-table tbody").find("tr:eq("+ i +")").find("td:eq(2)").text(data[i]["maxVal"]);
								$("#account-days-table tbody").find("tr:eq("+ i +")").find("td:eq(3)").text(data[i]["minVal"]);
								day += parseInt(data[i]["averageVal"]);
							}else{
								$("#account-days-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(1)").text(data[i]["averageVal"]);
								$("#account-days-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(2)").text(data[i]["maxVal"]);
								$("#account-days-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(3)").text(data[i]["minVal"]);
							}
						}
						$("#account-days-table tbody").find("tr:eq(3)").find("td:eq(1)").text(data[3]["threeAverageVal"]);
					}
				}
			});
		}
		
		
		//查询近三年合同汇总数据
		function queryNearThreeContract(){
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/BankCreditAudit/getNearlyThreeContractStatistics?id=${supplier_enterprise.id}",
				data : { },
				type: "get",
				dataType: "json",
				success: function(data, status, xhr){
					if(data != null && data.length == 4){
						var amount = 0;
						for(var i = 0; i < data.length; i++){
							if(i != data.length - 1){
								$("#contract-info-table tbody").find("tr:eq("+ i +")").find("td:eq(0)").text(data[i]["year"]);
								$("#contract-info-table tbody").find("tr:eq("+ i +")").find("td:eq(1)").text(data[i]["allVal"]);
								$("#contract-info-table tbody").find("tr:eq("+ i +")").find("td:eq(2)").text(data[i]["averageVal"]);
								$("#contract-info-table tbody").find("tr:eq("+ i +")").find("td:eq(3)").text(data[i]["maxVal"]);
								$("#contract-info-table tbody").find("tr:eq("+ i +")").find("td:eq(4)").text(data[i]["minVal"]);
								amount += parseInt(data[i]["averageVal"]);
							}else{
								$("#contract-info-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(1)").text(data[i]["allVal"]);
								$("#contract-info-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(2)").text(data[i]["averageVal"]);
								$("#contract-info-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(3)").text(data[i]["maxVal"]);
								$("#contract-info-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(4)").text(data[i]["minVal"]);
							}
						}
						$("#contract-info-table tbody").find("tr:eq(3)").find("td:eq(1)").text(data[3]["threeAverageVal"]);
					}
				}
			});
		}
		
		
		//查询近三年入库汇总数据
		function queryNearThreeYyrkzlys(){
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/BankCreditAudit/getNearlyThreeYyrkzlysStatistics?id=${supplier_enterprise.id}",
				data : { },
				type: "get",
				dataType: "json",
				success: function(data, status, xhr){
					if(data != null && data.length == 4){
						var amount = 0;
						for(var i = 0; i < data.length; i++){
							if(i != data.length - 1){
								$("#ware-housing-table tbody").find("tr:eq("+ i +")").find("td:eq(0)").text(data[i]["year"]);
								$("#ware-housing-table tbody").find("tr:eq("+ i +")").find("td:eq(1)").text(data[i]["allVal"]);
								$("#ware-housing-table tbody").find("tr:eq("+ i +")").find("td:eq(2)").text(data[i]["averageVal"]);
								$("#ware-housing-table tbody").find("tr:eq("+ i +")").find("td:eq(3)").text(data[i]["maxVal"]);
								$("#ware-housing-table tbody").find("tr:eq("+ i +")").find("td:eq(4)").text(data[i]["minVal"]);
								amount += parseInt(data[i]["averageVal"]);
							}else{
								$("#ware-housing-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(1)").text(data[i]["allVal"]);
								$("#ware-housing-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(2)").text(data[i]["averageVal"]);
								$("#ware-housing-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(3)").text(data[i]["maxVal"]);
								$("#ware-housing-table tbody").find("tr:eq("+ (i + 1) +")").find("td:eq(4)").text(data[i]["minVal"]);
							}
						}
						$("#ware-housing-table tbody").find("tr:eq(3)").find("td:eq(1)").text(data[3]["threeAverageVal"]);
					}
				}
			});
		}
		
		
		//查询交易合同统计数据
		function queryTransactionContractData(){
			var param = {};
			if($.isEmpty($("#beginDate1").val()) && !$.isEmpty($("#endDate1").val())){
				layer.msg("请输入检索起始日期！");
				return;
			}
			if(!$.isEmpty($("#beginDate1").val())){
				param["beginDate"] = $("#beginDate1").val();
			}
			if(!$.isEmpty($("#endDate1").val())){
				param["endDate"] = $("#endDate1").val();
			}
			if(!$.isEmpty($("#beginDate1").val()) && !$.isEmpty($("#endDate1").val())){
				var stDate = new Date($("#beginDate1").val().replace(/-/g,"/"));
				var etDate = new Date($("#endDate1").val().replace(/-/g,"/"));
				if(stDate > etDate){
					layer.msg("起始日期必须小于结束日期！");
					return;
				}
			}
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/BankCreditAudit/getTransactionContractStatistics?id=${supplier_enterprise.id}",
				data : param,
				type: "get",
				dataType: "json",
				success: function(data, status, xhr){
					var xAxiss = [];
					var datas = [];
					if(data !=  null && data.length > 0){
						//处理X轴
						if($.isEmpty($("#beginDate1").val()) && $.isEmpty($("#endDate1").val())){
							var xStart = "";
							var xEnd = "";
							var dt = new Date();
							xEnd = dt.getFullYear() + "-" + (dt.getMonth() + 1 > 9 ? dt.getMonth() + 1 : "0"+ (dt.getMonth() + 1));
							xAxiss.push(xEnd);
							var count = 11;
							while(count > 0){
								var mn = xAxiss[0];
								if(parseInt(mn.substr(5)) > 1){
									xAxiss.unshift(mn.substring(0, 5) + (parseInt(mn.substr(5)) - 1 > 9 ? parseInt(mn.substr(5)) - 1 : "0"+ (parseInt(mn.substr(5)) - 1)));
								}else{
									xAxiss.unshift((parseInt(mn.substring(0, 4)) - 1) + "-12");
								}
								count--;
							}
						}else{
							var xStart = $("#beginDate1").val().substring(0, 7);
							var xEnd = "";
							if($.isEmpty($("#endDate1").val())){
								var dt = new Date();
								xEnd = dt.getFullYear() + "-" + (dt.getMonth() + 1 > 9 ? dt.getMonth() + 1 : "0"+ (dt.getMonth() + 1));
							}else{
								xEnd = $("#endDate1").val().substring(0, 7);
							}
							xAxiss.push(xEnd);
							if(xStart != xEnd){
								var temp = "";
								while(temp != xStart){
									var mn = xAxiss[0];
									if(parseInt(mn.substr(5)) > 1){
										temp = (mn.substring(0, 5) + (parseInt(mn.substr(5)) - 1 > 9 ? parseInt(mn.substr(5)) - 1 : "0"+ (parseInt(mn.substr(5)) - 1)));
									}else{
										temp = ((parseInt(mn.substring(0, 4)) - 1) + "-12");
									}
									xAxiss.unshift(temp);
								}
							}
						}
						for(var i = 0; i < xAxiss.length; i++){
							var month = xAxiss[i];
							var amount = 0;
							$.each(data, function(key, value){
								if(!$.isEmpty(value["startTime"]) && !$.isEmpty(value["amount"])){
									var mth = value["startTime"].substring(0, 7);
									var loan = value["amount"];
									if(!$.isEmpty(mth) && mth == month){
										amount += parseFloat(loan);
									}
								}
							});
							datas.push(amount);
						}
					}
					createLineGraph("graph1", xAxiss, datas, "交易合同统计", "#673ab7");
				},
				error: function(xhr, status, error){
					layer.msg(status);
				}
			});
		}
		
		//查询付款清单统计数据
		function queryPaymentListData(){
			var param = {};
			if($.isEmpty($("#beginDate4").val()) && !$.isEmpty($("#endDate4").val())){
				layer.msg("请输入检索起始日期！");
				return;
			}
			if(!$.isEmpty($("#beginDate4").val())){
				param["beginDate"] = $("#beginDate4").val();
			}
			if(!$.isEmpty($("#endDate4").val())){
				param["endDate"] = $("#endDate4").val();
			}
			if(!$.isEmpty($("#beginDate4").val()) && !$.isEmpty($("#endDate4").val())){
				var stDate = new Date($("#beginDate4").val().replace(/-/g,"/"));
				var etDate = new Date($("#endDate4").val().replace(/-/g,"/"));
				if(stDate > etDate){
					layer.msg("起始日期必须小于结束日期！");
					return;
				}
			}
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/BankCreditAudit/getPaymentListStatistics?id=${supplier_enterprise.id}",
				data : param,
				type: "get",
				dataType: "json",
				success: function(data, status, xhr){
					var xAxiss = [];
					var datas = [];
					if(data !=  null && data.length > 0){
						//处理X轴
						if($.isEmpty($("#beginDate4").val()) && $.isEmpty($("#endDate4").val())){
							var xStart = "";
							var xEnd = "";
							var dt = new Date();
							xEnd = dt.getFullYear() + "-" + (dt.getMonth() + 1 > 9 ? dt.getMonth() + 1 : "0"+ (dt.getMonth() + 1));
							xAxiss.push(xEnd);
							var count = 11;
							while(count > 0){
								var mn = xAxiss[0];
								if(parseInt(mn.substr(5)) > 1){
									xAxiss.unshift(mn.substring(0, 5) + (parseInt(mn.substr(5)) - 1 > 9 ? parseInt(mn.substr(5)) - 1 : "0"+ (parseInt(mn.substr(5)) - 1)));
								}else{
									xAxiss.unshift((parseInt(mn.substring(0, 4)) - 1) + "-12");
								}
								count--;
							}
						}else{
							var xStart = $("#beginDate4").val().substring(0, 7);
							var xEnd = "";
							if($.isEmpty($("#endDate4").val())){
								var dt = new Date();
								xEnd = dt.getFullYear() + "-" + (dt.getMonth() + 1 > 9 ? dt.getMonth() + 1 : "0"+ (dt.getMonth() + 1));
							}else{
								xEnd = $("#endDate4").val().substring(0, 7);
							}
							xAxiss.push(xEnd);
							if(xStart != xEnd){
								var temp = "";
								while(temp != xStart){
									var mn = xAxiss[0];
									if(parseInt(mn.substr(5)) > 1){
										temp = (mn.substring(0, 5) + (parseInt(mn.substr(5)) - 1 > 9 ? parseInt(mn.substr(5)) - 1 : "0"+ (parseInt(mn.substr(5)) - 1)));
									}else{
										temp = ((parseInt(mn.substring(0, 4)) - 1) + "-12");
									}
									xAxiss.unshift(temp);
								}
							}
						}
						for(var i = 0; i < xAxiss.length; i++){
							var month = xAxiss[i];
							var amount = 0;
							$.each(data, function(key, value){
								if(!$.isEmpty(value["invoiceDate"]) && !$.isEmpty(value["amount"])){
									var mth = value["invoiceDate"].substring(0, 7);
									var loan = value["amount"];
									if(!$.isEmpty(mth) && mth == month){
										amount += parseFloat(loan);
									}
								}
							});
							datas.push(amount);
						}
					}
					createLineGraph("graph4", xAxiss, datas, "付款记录统计", "#00ff4e");
				},
				error: function(xhr, status, error){
					layer.msg(status);
				}
			});
		}
		
		//查询入库记录统计数据
		function queryYyrkzlysRecordData(){
			var param = {};
			if($.isEmpty($("#beginDate3").val()) && !$.isEmpty($("#endDate3").val())){
				layer.msg("请输入检索起始日期！");
				return;
			}
			if(!$.isEmpty($("#beginDate3").val())){
				param["beginDate"] = $("#beginDate3").val();
			}
			if(!$.isEmpty($("#endDate3").val())){
				param["endDate"] = $("#endDate3").val();
			}
			if(!$.isEmpty($("#beginDate3").val()) && !$.isEmpty($("#endDate3").val())){
				var stDate = new Date($("#beginDate3").val().replace(/-/g,"/"));
				var etDate = new Date($("#endDate3").val().replace(/-/g,"/"));
				if(stDate > etDate){
					layer.msg("起始日期必须小于结束日期！");
					return;
				}
			}
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/BankCreditAudit/getYyrkzlysRecordStatistics?id=${supplier_enterprise.id}",
				data : param,
				type: "get",
				dataType: "json",
				success: function(data, status, xhr){
					var xAxiss = [];
					var datas = [];
					if(data !=  null && data.length > 0){
						//处理X轴
						if($.isEmpty($("#beginDate3").val()) && $.isEmpty($("#endDate3").val())){
							var xStart = "";
							var xEnd = "";
							var dt = new Date();
							xEnd = dt.getFullYear() + "-" + (dt.getMonth() + 1 > 9 ? dt.getMonth() + 1 : "0"+ (dt.getMonth() + 1));
							xAxiss.push(xEnd);
							var count = 11;
							while(count > 0){
								var mn = xAxiss[0];
								if(parseInt(mn.substr(5)) > 1){
									xAxiss.unshift(mn.substring(0, 5) + (parseInt(mn.substr(5)) - 1 > 9 ? parseInt(mn.substr(5)) - 1 : "0"+ (parseInt(mn.substr(5)) - 1)));
								}else{
									xAxiss.unshift((parseInt(mn.substring(0, 4)) - 1) + "-12");
								}
								count--;
							}
						}else{
							var xStart = $("#beginDate3").val().substring(0, 7);
							var xEnd = "";
							if($.isEmpty($("#endDate3").val())){
								var dt = new Date();
								xEnd = dt.getFullYear() + "-" + (dt.getMonth() + 1 > 9 ? dt.getMonth() + 1 : "0"+ (dt.getMonth() + 1));
							}else{
								xEnd = $("#endDate3").val().substring(0, 7);
							}
							xAxiss.push(xEnd);
							if(xStart != xEnd){
								var temp = "";
								while(temp != xStart){
									var mn = xAxiss[0];
									if(parseInt(mn.substr(5)) > 1){
										temp = (mn.substring(0, 5) + (parseInt(mn.substr(5)) - 1 > 9 ? parseInt(mn.substr(5)) - 1 : "0"+ (parseInt(mn.substr(5)) - 1)));
									}else{
										temp = ((parseInt(mn.substring(0, 4)) - 1) + "-12");
									}
									xAxiss.unshift(temp);
								}
							}
						}
						for(var i = 0; i < xAxiss.length; i++){
							var month = xAxiss[i];
							var amount = 0;
							$.each(data, function(key, value){
								if(!$.isEmpty(value["time"]) && !$.isEmpty(value["count"])){
									var mth = value["time"].substring(0, 7);
									var loan = value["count"];
									if(!$.isEmpty(mth) && mth == month){
										amount += parseFloat(loan);
									}
								}
							});
							datas.push(amount);
						}
					}
					createLineGraph("graph3", xAxiss, datas, "入库记录统计", "#00c4ff");
				},
				error: function(xhr, status, error){
					layer.msg(status);
				}
			});
		}
		
		//查询应收账款统计数据
		function queryBillInfoData(){
			var param = {};
			if($.isEmpty($("#beginDate2").val()) && !$.isEmpty($("#endDate2").val())){
				layer.msg("请输入检索起始日期！");
				return;
			}
			if(!$.isEmpty($("#beginDate2").val())){
				param["beginDate"] = $("#beginDate2").val();
			}
			if(!$.isEmpty($("#endDate2").val())){
				param["endDate"] = $("#endDate2").val();
			}
			if(!$.isEmpty($("#beginDate2").val()) && !$.isEmpty($("#endDate2").val())){
				var stDate = new Date($("#beginDate2").val().replace(/-/g,"/"));
				var etDate = new Date($("#endDate2").val().replace(/-/g,"/"));
				if(stDate > etDate){
					layer.msg("起始日期必须小于结束日期！");
					return;
				}
			}
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/BankCreditAudit/getBillInfoStatistics?id=${supplier_enterprise.id}",
				data : param,
				type: "get",
				dataType: "json",
				success: function(data, status, xhr){
					var xAxiss = [];
					var datas = [];
					if(data !=  null && data.length > 0){
						//处理X轴
						if($.isEmpty($("#beginDate2").val()) && $.isEmpty($("#endDate2").val())){
							var xStart = "";
							var xEnd = "";
							var dt = new Date();
							xEnd = dt.getFullYear() + "-" + (dt.getMonth() + 1 > 9 ? dt.getMonth() + 1 : "0"+ (dt.getMonth() + 1));
							xAxiss.push(xEnd);
							var count = 11;
							while(count > 0){
								var mn = xAxiss[0];
								if(parseInt(mn.substr(5)) > 1){
									xAxiss.unshift(mn.substring(0, 5) + (parseInt(mn.substr(5)) - 1 > 9 ? parseInt(mn.substr(5)) - 1 : "0"+ (parseInt(mn.substr(5)) - 1)));
								}else{
									xAxiss.unshift((parseInt(mn.substring(0, 4)) - 1) + "-12");
								}
								count--;
							}
						}else{
							var xStart = $("#beginDate2").val().substring(0, 7);
							var xEnd = "";
							if($.isEmpty($("#endDate2").val())){
								var dt = new Date();
								xEnd = dt.getFullYear() + "-" + (dt.getMonth() + 1 > 9 ? dt.getMonth() + 1 : "0"+ (dt.getMonth() + 1));
							}else{
								xEnd = $("#endDate2").val().substring(0, 7);
							}
							xAxiss.push(xEnd);
							if(xStart != xEnd){
								var temp = "";
								while(temp != xStart){
									var mn = xAxiss[0];
									if(parseInt(mn.substr(5)) > 1){
										temp = (mn.substring(0, 5) + (parseInt(mn.substr(5)) - 1 > 9 ? parseInt(mn.substr(5)) - 1 : "0"+ (parseInt(mn.substr(5)) - 1)));
									}else{
										temp = ((parseInt(mn.substring(0, 4)) - 1) + "-12");
									}
									xAxiss.unshift(temp);
								}
							}
						}
						for(var i = 0; i < xAxiss.length; i++){
							var month = xAxiss[i];
							var amount = 0;
							$.each(data, function(key, value){
								if(!$.isEmpty(value["startDate"]) && !$.isEmpty(value["amount"])){
									var mth = value["startDate"].substring(0, 7);
									var loan = value["amount"];
									if(!$.isEmpty(mth) && mth == month){
										amount += parseFloat(loan);
									}
								}
							});
							datas.push(amount);
						}
					}
					createLineGraph("graph2", xAxiss, datas, "应收账款统计", "#2700ff");
				},
				error: function(xhr, status, error){
					layer.msg(status);
				}
			});
		}
		
		//查询历史授信统计数据
		function queryCreditHistoryData(){
			var param = {};
			if($.isEmpty($("#beginDate5").val()) && !$.isEmpty($("#endDate5").val())){
				layer.msg("请输入检索起始日期！");
				return;
			}
			if(!$.isEmpty($("#beginDate5").val())){
				param["beginDate"] = $("#beginDate5").val();
			}
			if(!$.isEmpty($("#endDate5").val())){
				param["endDate"] = $("#endDate5").val();
			}
			if(!$.isEmpty($("#beginDate5").val()) && !$.isEmpty($("#endDate5").val())){
				var stDate = new Date($("#beginDate5").val().replace(/-/g,"/"));
				var etDate = new Date($("#endDate5").val().replace(/-/g,"/"));
				if(stDate > etDate){
					layer.msg("起始日期必须小于结束日期！");
					return;
				}
			}
			$.ajax({
				async : true,
				cache : true,
				url: "${ctx}/BankCreditAudit/getCreditDetailedStatistics?id=${supplier_enterprise.id}",
				data : param,
				type: "get",
				dataType: "json",
				success: function(data, status, xhr){
					var xAxiss = [];
					var datas = [];
					if(data !=  null && data.length > 0){
						var month = xAxiss[i];
						var amount = 0;
						for(var i = data.length - 1; i >= 0; i--){
							value = data[i];
							if(!$.isEmpty(value["time"]) && !$.isEmpty(value["allQuota"])){
								var mth = value["time"].substring(0, value["time"].indexOf(" "));
								var loan = value["allQuota"];
								amount = parseFloat(loan);
								xAxiss.push(mth);
								datas.push(amount);
							}
						}
					}
					createBarGraph("graph5", xAxiss, datas);
				},
				error: function(xhr, status, error){
					layer.msg(status);
				}
			});
		}
		
		
		//创建折线图
		function createLineGraph(elementId, xAxiss, datas, graphName, lineColor){
		    // 基于准备好的dom，初始化echarts实例
		    var myChart = echarts.init(document.getElementById(elementId));
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
		        xAxis: {
		            type: 'category',
		            nameRotate: 30,
		            axisLabel: {
		                interval: 0,
		                rotate: 30
		            },
		            data: xAxiss
		        },
		        yAxis: {
		            type: 'value',
		            splitLine: {show: true},
		            show: true,
		            position: 'left'
		        },
		        series: [
		            {
		                name: graphName,
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
								color: lineColor  //连线颜色
							}
						},
		                itemStyle: {
		                    normal: {
		                        color: function (value) {
                                    return lineColor;
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
		
		
		//创建柱状图
		function createBarGraph(elementId, xAxiss, datas){
		    // 基于准备好的dom，初始化echarts实例
		    var myChart = echarts.init(document.getElementById(elementId));

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
		            data: xAxiss
		        },
		        yAxis: {
		            type: 'value',
		            splitLine: {show: true},
		            show: true,
		            position: 'left'
		        },
		        series: [
		            {
		                name: '额度',
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
		                        	return "#be6bff";
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
	<div class="wrapper wrapper-content">
		<div class="ibox">
			<div class="ibox-title">
				<h5><a id="title-a" href="${ctx}/BankCreditAudit/gys-index">授信管理</a></h5>
				<span class="page-title">&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;授信参考</span>
				<div class="ibox-tools">
					<a class="collapse-link">
						<i class="fa fa-chevron-up"></i>
					</a>
					<a class="close-link">
						<i class="fa fa-times"></i>
					</a>
				</div>
			</div>
    		<div class="ibox-content">
				<sys:message content="${message}"/>
				
				<div class="lines">
		   			<ul>
		      			<li><div class="lines_l"><img src="${ctxStatic}/images/lines_01.jpg"></div><div class="lines_r">
		      				<c:if test="${empty supplier_enterprise.paramsId.allQuota}">
		      					<span>0</span>已授信额度（元）
		      				</c:if>
		      				<c:if test="${not empty supplier_enterprise.paramsId.allQuota}">
		      					<span>${supplier_enterprise.paramsId.allQuota}</span>已授信额度（元）
		      				</c:if>
		      			</div>
		      		</li>
		      			<li><div class="lines_l"><img src="${ctxStatic}/images/lines_02.jpg"></div><div class="lines_r">
		      					<c:if test="${empty supplier_enterprise.paramsId.availableQuota}">
		      						<span>0</span>剩余额度（元）
		      					</c:if>
		      					<c:if test="${not empty supplier_enterprise.paramsId.availableQuota}">
		      						<span>${supplier_enterprise.paramsId.availableQuota}</span>剩余额度（元）
		      					</c:if>
		      				</div>
		      			</li>
		    		</ul>
		  		</div>
		  		
		  		<div class="row" style="margin-top: 30px;">
		  			<div class="col-sm-6">
		  				<div class="ibox float-e-margins" style="border: 1px solid #cccccc;">
		                     <div class="ibox-title">
		                        <h5>供应商详情</h5> 
		                        <div class="ibox-tools">
		                        	<a href="javascript: void(0);" onclick="openDialogView('供应商详情', '${ctx}/gys/gys-info?id=${supplier_enterprise.id}', '1200px', '700px')" style="margin-right: 10px;">查看更多</a>
		                            <a class="collapse-link">
		                                <i class="fa fa-chevron-up"></i>
		                            </a>
		                        </div>
		                    </div>
		                    <div class="ibox-content" style="height: 300px;">
		                        <p style="text-indent: 13px;">供应商名称：${supplier_enterprise.name}</p>
		                        <p>组织结构编码：${supplier_enterprise.orgCode}</p>
		                        <p style="text-indent: 13px;">营业期限至：${supplier_enterprise.businessPeriodTo}</p>
		                        <p style="text-indent: 26px;">注册资本：${supplier_enterprise.registeredCapital}</p>
		                        <c:if test="${supplier_enterprise.type == '0'}">
		                        	<p style="text-indent: 26px;">企业类型：股份有限公司</p>
		                        </c:if>
		                        <c:if test="${supplier_enterprise.type == '1'}">
		                        	<p style="text-indent: 26px;">企业类型：有限责任公司</p>
		                        </c:if>
		                        <c:if test="${supplier_enterprise.type == '2'}">
		                        	<p style="text-indent: 26px;">企业类型：合伙企业</p>
		                        </c:if>
		                        <c:if test="${supplier_enterprise.type == '3'}">
		                        	<p style="text-indent: 26px;">企业类型：集体企业</p>
		                        </c:if>
		                        <c:if test="${supplier_enterprise.type == '4'}">
		                        	<p style="text-indent: 26px;">企业类型：国有企业</p>
		                        </c:if>
		                        <p style="text-indent: 26px;">营业地址：${supplier_enterprise.businessAddress}</p>
		                        <c:if test="${supplier_enterprise.state == '0'}">
		                        	<p style="text-indent: 52px;">状态：待提交资料</p>
		                        </c:if>
		                        <c:if test="${supplier_enterprise.state == '1'}">
		                        	<p style="text-indent: 52px;">状态：待平台审核</p>
		                        </c:if>
		                        <c:if test="${supplier_enterprise.state == '2'}">
		                        	<p style="text-indent: 52px;">状态：平台审核不通过</p>
		                        </c:if>
		                        <c:if test="${supplier_enterprise.state == '3'}">
		                        	<p style="text-indent: 52px;">状态：待签约</p>
		                        </c:if>
		                        <c:if test="${supplier_enterprise.state == '4'}">
		                        	<p style="text-indent: 52px;">状态：已签约待银行授信</p>
		                        </c:if>
		                        <c:if test="${supplier_enterprise.state == '5'}">
		                        	<p style="text-indent: 52px;">状态：授信通过</p>
		                        </c:if>
		                        <c:if test="${supplier_enterprise.state == '6'}">
		                        	<p style="text-indent: 52px;">状态：银行授信不通过</p>
		                        </c:if>
		                    </div>
		                </div>
		  			</div>
		  			<div class="col-sm-6">
		  				<div class="ibox float-e-margins" style="border: 1px solid #cccccc;">
		                     <div class="ibox-title">
		                        <h5>合同比例</h5>
		                        <input style="margin-left: 15px; width: 120px; height: 22px; margin-top: -2px;" id="beginDate1" name="beginDate" type="text" placeholder="起始日期" readOnly class="laydate-icon form-control layer-date input-sm" />
								<input style="margin-left: 10px; width: 120px; height: 22px; margin-top: -2px;" id="endDate1" name="endDate" type="text" placeholder="结束日期" readOnly class="laydate-icon form-control layer-date input-sm" />
		                        <a href="javascript: void(0);" onclick="queryTransactionContractData()" style="margin-left:15px;">查询</a>
		                        <div class="ibox-tools">
		                            <a class="collapse-link">
		                                <i class="fa fa-chevron-up"></i>
		                            </a>
		                        </div>
		                    </div>
		                    <div class="ibox-content" style="height: 300px;">
		                        <div id="graph1" style="height: 100%;"></div>
		                    </div>
		                </div>
		  			</div>
		  		</div>
		  		
		  		<div class="row" style="margin-top: 30px;">
		  			<div class="col-sm-6">
		  				<div class="ibox float-e-margins" style="border: 1px solid #cccccc;">
		                     <div class="ibox-title">
		                        <h5>应收账款曲线图</h5> 
		                        <input style="margin-left: 15px; width: 120px; height: 22px; margin-top: -2px;" id="beginDate2" name="beginDate" type="text" placeholder="起始日期" readOnly class="laydate-icon form-control layer-date input-sm" />
								<input style="margin-left: 10px; width: 120px; height: 22px; margin-top: -2px;" id="endDate2" name="endDate" type="text" placeholder="结束日期" readOnly class="laydate-icon form-control layer-date input-sm" />
		                        <a href="javascript: void(0);" onclick="queryBillInfoData()" style="margin-left:15px;">查询</a>
		                        <div class="ibox-tools">
		                            <a class="collapse-link">
		                                <i class="fa fa-chevron-up"></i>
		                            </a>
		                        </div>
		                    </div>
		                    <div class="ibox-content" style="height: 300px;">
		                        <div id="graph2" style="height: 100%;"></div>
		                    </div>
		                </div>
		  			</div>
		  			<div class="col-sm-6">
		  				<div class="ibox float-e-margins" style="border: 1px solid #cccccc;">
		                     <div class="ibox-title">
		                        <h5>验收入库曲线图</h5> 
		                        <input style="margin-left: 15px; width: 120px; height: 22px; margin-top: -2px;" id="beginDate3" name="beginDate" type="text" placeholder="起始日期" readOnly class="laydate-icon form-control layer-date input-sm" />
								<input style="margin-left: 10px; width: 120px; height: 22px; margin-top: -2px;" id="endDate3" name="endDate" type="text" placeholder="结束日期" readOnly class="laydate-icon form-control layer-date input-sm" />
		                        <a href="javascript: void(0);" onclick="queryYyrkzlysRecordData()" style="margin-left:15px;">查询</a>
		                        <div class="ibox-tools">
		                            <a class="collapse-link">
		                                <i class="fa fa-chevron-up"></i>
		                            </a>
		                        </div>
		                    </div>
		                    <div class="ibox-content" style="height: 300px;">
		                        <div id="graph3" style="height: 100%;"></div>
		                    </div>
		                </div>
		  			</div>
		  		</div>
		  		
		  		<div class="row" style="margin-top: 30px;">
		  			<div class="col-sm-6">
		  				<div class="ibox float-e-margins" style="border: 1px solid #cccccc;">
		                     <div class="ibox-title">
		                        <h5>付款流水曲线图</h5> 
		                        <input style="margin-left: 15px; width: 120px; height: 22px; margin-top: -2px;" id="beginDate4" name="beginDate" type="text" placeholder="起始日期" readOnly class="laydate-icon form-control layer-date input-sm" />
								<input style="margin-left: 10px; width: 120px; height: 22px; margin-top: -2px;" id="endDate4" name="endDate" type="text" placeholder="结束日期" readOnly class="laydate-icon form-control layer-date input-sm" />
		                        <a href="javascript: void(0);" onclick="queryPaymentListData()" style="margin-left:15px;">查询</a>
		                        <div class="ibox-tools">
		                            <a class="collapse-link">
		                                <i class="fa fa-chevron-up"></i>
		                            </a>
		                        </div>
		                    </div>
		                    <div class="ibox-content" style="height: 300px;">
		                        <div id="graph4" style="height: 100%;"></div>
		                    </div>
		                </div>
		  			</div>
		  			<div class="col-sm-6">
		  				<div class="ibox float-e-margins" style="border: 1px solid #cccccc;">
		                     <div class="ibox-title">
		                        <h5>授信变化曲线图</h5> 
		                        <input style="margin-left: 15px; width: 120px; height: 22px; margin-top: -2px;" id="beginDate5" name="beginDate" type="text" placeholder="起始日期" readOnly class="laydate-icon form-control layer-date input-sm" />
								<input style="margin-left: 10px; width: 120px; height: 22px; margin-top: -2px;" id="endDate5" name="endDate" type="text" placeholder="结束日期" readOnly class="laydate-icon form-control layer-date input-sm" />
		                        <a href="javascript: void(0);" onclick="queryCreditHistoryData()" style="margin-left:15px;">查询</a>
		                        <div class="ibox-tools">
		                            <a class="collapse-link">
		                                <i class="fa fa-chevron-up"></i>
		                            </a>
		                        </div>
		                    </div>
		                    <div class="ibox-content" style="height: 300px;">
		                        <div id="graph5" style="height: 100%;"></div>
		                    </div>
		                </div>
		  			</div>
		  		</div>
		  		
		  		<div class="row" style="margin-top: 10px; margin-bottom: 10px;">
		  			<div class="col-sm-6">
		  				<table id="bill-info-table" style="width: 100%; text-align: center;" border="1">
		  					<caption style="font-size: 1.2em; text-align: center;">近三年应收账款汇总表</caption>
		  					<thead>
		  						<tr style="height: 30px;">
			  						<th style="text-align: center;">年度</th>
			  						<th style="text-align: center;">总额</th>
			  						<th style="text-align: center;">平均值</th>
			  						<th style="text-align: center;">峰值</th>
			  						<th style="text-align: center;">谷值</th>
			  					</tr>
		  					</thead>
		  					<tbody>
		  						<tr style="height: 25px;">
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td>近三年平均值</td>
			  						<td colspan="4"></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td>近半年</td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
		  					</tbody>
		  				</table>
		  			</div>
		  			<div class="col-sm-6">
		  				<table id="payment-info-table" style="width: 100%; text-align: center;" border="1">
		  					<caption style="font-size: 1.2em; text-align: center;">近三年付款汇总表</caption>
		  					<thead>
		  						<tr style="height: 30px;">
			  						<th style="text-align: center;">年度</th>
			  						<th style="text-align: center;">总额</th>
			  						<th style="text-align: center;">平均值</th>
			  						<th style="text-align: center;">峰值</th>
			  						<th style="text-align: center;">谷值</th>
			  					</tr>
		  					</thead>
		  					<tbody>
		  						<tr style="height: 25px;">
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td>近三年平均值</td>
			  						<td colspan="4"></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td>近半年</td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
		  					</tbody>
		  				</table>
		  			</div>
		  		</div>
		  		
		  		<div class="row" style="margin-top: 10px; margin-bottom: 10px;">
		  			<div class="col-sm-6">
		  				<table id="account-days-table" style="width: 100%; text-align: center;" border="1">
		  					<caption style="font-size: 1.2em; text-align: center;">近三年账期汇总表</caption>
		  					<thead>
		  						<tr style="height: 30px;">
			  						<th style="text-align: center;">年度</th>
			  						<th style="text-align: center;">平均值（天）</th>
			  						<th style="text-align: center;">峰值（天）</th>
			  						<th style="text-align: center;">谷值（天）</th>
			  					</tr>
		  					</thead>
		  					<tbody>
		  						<tr style="height: 25px;">
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td>近三年平均值</td>
			  						<td colspan="3"></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td>近半年</td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
		  					</tbody>
		  				</table>
		  			</div>
		  			<div class="col-sm-6">
		  				<table id="contract-info-table" style="width: 100%; text-align: center;" border="1">
		  					<caption style="font-size: 1.2em; text-align: center;">近三年合同汇总表</caption>
		  					<thead>
		  						<tr style="height: 30px;">
			  						<th style="text-align: center;">年度</th>
			  						<th style="text-align: center;">总额</th>
			  						<th style="text-align: center;">平均值</th>
			  						<th style="text-align: center;">峰值</th>
			  						<th style="text-align: center;">谷值</th>
			  					</tr>
		  					</thead>
		  					<tbody>
		  						<tr style="height: 25px;">
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td>近三年平均值</td>
			  						<td colspan="4"></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td>近半年</td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
		  					</tbody>
		  				</table>
		  			</div>
		  		</div>
		  		
		  		<div class="row" style="margin-top: 10px; margin-bottom: 10px;">
		  			<div class="col-sm-6">
		  				<table id="ware-housing-table" style="width: 100%; text-align: center;" border="1">
		  					<caption style="font-size: 1.2em; text-align: center;">近三年入库汇总表</caption>
		  					<thead>
		  						<tr style="height: 30px;">
			  						<th style="text-align: center;">年度</th>
			  						<th style="text-align: center;">总额</th>
			  						<th style="text-align: center;">平均值</th>
			  						<th style="text-align: center;">峰值</th>
			  						<th style="text-align: center;">谷值</th>
			  					</tr>
		  					</thead>
		  					<tbody>
		  						<tr style="height: 25px;">
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td>近三年平均值</td>
			  						<td colspan="4"></td>
			  					</tr>
			  					<tr style="height: 25px;">
			  						<td>近半年</td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  						<td></td>
			  					</tr>
		  					</tbody>
		  				</table>
		  			</div>
		  		</div>
		  		
			</div>
		</div>
	</div>
</body>
</html>