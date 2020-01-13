<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>核心企业--材料管理--医院入库质量验收记录</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<script>
		$(function(){
			$("#importFile").change(function(){
				$("#importExcel").submit();
			});
		});
		
		//导入文件
		function importFile(){
			$("#importFile").click();
		}
		
		function refresh(){
			location.href = "${ctx}/fdCtrl/findYyrkzlysListByPage?coreEnterpriseId.id=${fns:getUser().core.id}&billId.id=${bill_info.id}";
		}
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="rong_nav">
			<ul>
				<li><a href="${ctx}/fdCtrl/findPaymentListByPage?coreEnterpriseId.id=${fns:getUser().core.id}&billId.id=${bill_info.id}" class="nav_b">付款清单</a></li>
		       	<li><a href="${ctx}/fdCtrl/findTransactionListByPage?coreEnterpriseId.id=${fns:getUser().core.id}&billId.id=${bill_info.id}" class="nav_b">交易合同</a></li>
		       	<li style="display: none;"><a href="${ctx}/fdCtrl/findYyrkzlysListByPage?coreEnterpriseId.id=${fns:getUser().core.id}&billId.id=${bill_info.id}" class="nav_a">医院入库质量验收记录</a></li>
		   	</ul>
		</div>
		<div class="ibox">
			<div class="ibox-title">
				<h5><a href="${ctx}/fdCtrl/findYyrkzlysListByPage?coreEnterpriseId.id=${fns:getUser().core.id}&billId.id=${bill_info.id}">医院入库质量验收记录管理</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;医院入库质量验收记录列表</span>
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
	
				<!-- 查询条件 -->
				<div class="row">
					<form:form style="padding-left: 15px; margin-bottom: 5px;" id="searchForm" action="${ctx}/fdCtrl/findYyrkzlysListByPage?coreEnterpriseId.id=${fns:getUser().core.id}&billId.id=${bill_info.id}" method="post" class="form-inline">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					</form:form>
			        <div class="col-sm-12" style="margin-top: 10px; margin-bottom: 10px;">
			        	<button class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" style="float: left; margin-right: 20px;"><i class="fa fa-search"></i> 查询</button>
			        	<table:delRow url="${ctx}/fdCtrl/deleteYyrkzlysLists" id="contentTable"></table:delRow>
      					<form style="display: inline;" id="importExcel" name="importExcel" action="${ctx}/fdCtrl/importYyrkzlysExcelData" accept-charset="UTF-8" method="post" enctype="multipart/form-data">
      						<input type="file" style="display: none;" name="file" id="importFile" />
      						<input name="coreEnterpriseId.id" type="hidden" value="${fns:getUser().core.id}" />
      						<input name="bill_id" type="hidden" value="${bill_info.id}" />
      						<a href="javascript: void(0);" onclick="importFile()" class="button1" id="importDatas" style="margin-top: -5px;">批量导入数据</a>
      					</form>
      					<a href="${ctx}/fdCtrl/download-template?type=0" class="button1" id="down-templete" style="margin-left: 20px; margin-top: -5px;">下载Excel模板</a>
					</div>
					<div class="col-sm-12">
						<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
							<thead>
								<tr>
									<th><input type="checkbox" class="i-checks"></th>
									<th>品名</th>
									<th>单价</th>
									<th>总价</th>
									<th>规格</th>
									<th>单位</th>
									<th>数量</th>
									<th>生产企业</th>
									<th>生产批号</th>
									<th>灭菌批号/设备编号</th>
									<th>有效期</th>
									<th>质量状况</th>
									<th>验收结论</th>
									<th>记录时间</th>
									<th>验收员</th>
									<th>供应商名称</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.list}" var="item">
									<tr>
										<td><input type="checkbox" id="${item.id}" class="i-checks"></td>
										<td>${item.projectName}</td>
										<td>${item.unitPrice}</td>
										<td>${item.allPrice}</td>
										<td>${item.specifications}</td>
										<td>${item.unit}</td>
										<td>${item.count}</td>
										<td>${item.produceCompany}</td>
										<td>${item.produceNum}</td>
										<td>${item.deviceNum}</td>
										<td>${item.validityTime}</td>
										<td>${item.qualityCondition}</td>
										<td>${item.acceptConclusion}</td>
										<td><fmt:formatDate value="${item.time}" type="both"/></td>
										<td>${item.inspector}</td>
										<td>${item.supplierEnterpriseId.name}</td>
										<td>
											<a href="${ctx}/fdCtrl/deleteYyrkzlysLists?ids=${item.id}&bill_id=${bill_info.id}" style="color: #ffffff;" class="btn btn-primary btn-xs">删 除</a>
					                    </td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<!-- 分页代码 -->
						<table:page page="${page}"></table:page>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<!-- <script>
		document.onreadystatechange = subSomething;//当页面加载状态改变的时候执行这个方法. 
		function subSomething() 
		{ 
			if(document.readyState == "complete") //当页面加载状态
			{
				setTimeout(function(){
					$(".pace-progress").css("top", "52px");
				}, 200);
				parent.setIframeHeight($(".wrapper").height() + 55);
			}
		} 
	</script> -->
</body>
</html>