<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>核心企业--供应商管理--邀请供应商</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
	<script>
		$(function(){
			$("#invitationGys").click(function(){
				var str="";
		 	 	var ids="";
		 	 	var count = 0;
			  	$("#contentTable tbody tr td input.i-checks:checkbox").each(function(){
			    	if(true == $(this).is(':checked')){
			    		count++;
	     	 			str += $(this).attr("id") + ",";
			    	}
			  	});
			  	if(str.substr(str.length - 1) == ','){
			    	ids = str.substr(0 , str.length-1);
			  	}
			  	if(ids == ""){
					top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
					return;
			  	}
			  	top.layer.confirm('您已勾选了'+ count +'位供应商，是否邀请？', {icon: 3, title:'邀请供应商'}, function(index){
			  		location.href = "${ctx}/coreSupplierCtrl/batch-invitationGys?coreEnterpriseId.id=${fns:getUser().core.id}&ids=" + ids;
				    top.layer.close(index);
				});
			});
			
			if($("#invitationState").attr("val") != undefined && $("#invitationState").attr("val") != null && $("#invitationState").attr("val") != ""){
      			$("#invitationState option[value="+ $("#invitationState").attr("val") +"]")[0].selected = true;
      		}
		});
		
		function invitation(id){
			top.layer.confirm('是否邀请该供应商?', {icon: 3, title:'邀请供应商'}, function(index){
				location.href = "${ctx}/coreSupplierCtrl/batch-invitationGys?coreEnterpriseId.id=${fns:getUser().core.id}&ids=" + id;
			    top.layer.close(index);
			});
		}
		
		function refresh(){
			location.href = "${ctx}/coreSupplierCtrl/invitation-gys?coreEnterpriseId.id=${fns:getUser().core.id}";
		}
	</script>
</head>
<body class="gray-bg">
	<div class="rong_nav">
		<ul>
			<li><a href="${ctx}/coreSupplierCtrl/invitation-gys?coreEnterpriseId.id=${fns:getUser().core.id}" class="nav_a">邀请供应商</a></li>
	       	<li><a href="${ctx}/coreSupplierCtrl/hxqyGys-list?coreEnterpriseId.id=${fns:getUser().core.id}" class="nav_b">供应商列表</a></li>
	   	</ul>
	</div>
	<div class="wrapper wrapper-content" style="padding: 0;">
		<div class="ibox" style="margin-bottom: 0;">
			<div class="ibox-title">
				<h5><a href="${ctx}/coreSupplierCtrl/invitation-gys?coreEnterpriseId.id=${fns:getUser().core.id}">邀请供应商</a></h5>
				<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;供应商列表</span>
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
					<form:form style="padding-left: 15px; margin-bottom: 5px;" id="searchForm" modelAttribute="core_supplier" action="${ctx}/coreSupplierCtrl/invitation-gys?coreEnterpriseId.id=${fns:getUser().core.id}" method="post" class="form-inline">
						<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
						<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
						<div class="form-group">
							<input name="supplierEnterpriseId.name" value="${core_supplier.supplierEnterpriseId.name}" placeholder="供应商名称" class="input-sm form-control" />
							<input style="margin-left: 10px;" name="supplierEnterpriseId.agencyName" value="${core_supplier.supplierEnterpriseId.agencyName}" placeholder="联系人姓名" class="input-sm form-control" />
							<input style="margin-left: 10px;" name="supplierEnterpriseId.agencyPhone" value="${core_supplier.supplierEnterpriseId.agencyPhone}" placeholder="手机号" class="input-sm form-control" />
						</div>
						<div class="form-group" style="display: block; margin-top: 10px;">
							<input name="supplierEnterpriseId.agencyEmail" value="${core_supplier.supplierEnterpriseId.agencyEmail}" placeholder="邮箱" class="input-sm form-control" />
							<select style="margin-left: 10px; padding:0 12px;" id="invitationState" name="state" val="${core_supplier.state}" class="input-sm form-control input-s-sm inline">
                      			<option value="">邀请状态</option>
                      			<option value="0">未邀请</option>
                      			<option value="1">已邀请</option>
								<option value="2">已注册</option>
                    		</select>
						</div>
					</form:form>
			        <div class="col-sm-12" style="margin-top: 10px; margin-bottom: 10px;">
			        	<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			        	<button style="margin-left: 10px; margin-right: 10px;" class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
			        	<table:delRow url="${ctx}/gys/deleteBatch" id="contentTable"></table:delRow>
						<a href="javascript:void(0);" onclick="openDialog('导入供应商', '${ctx}/gys/exportGys', '700px', '450px', 'content-frame');" class="button" id="importdata">批量导入供应商</a>
      					<a href="javascript:void(0);" class="button1 pinvite" id="invitationGys">批量邀请供应商</a>
      					<span style="margin-left: 5px; font-size: 1.1em; line-height: 40px;">（已导入供应商${page.count}个，邀请供应商${invitationCount}个，已注册供应商${registerCount}个。）</span>
      					<a href="javascript:void(0);" onclick="openDialog('添加供应商', '${ctx}/coreSupplierCtrl/hxqy-addGys', '700px', '450px', 'content-frame');" class="button2" id="newcompany">添加供应商</a>
					</div>
					<div class="col-sm-12">
						<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
							<thead>
								<tr>
									<th><input type="checkbox" class="i-checks"></th>
									<th>供应商名称</th>
									<th>联系人姓名</th>
									<th>手机号</th>
									<th>邮箱</th>
									<th>导入时间</th>
									<th>邀请状态</th>
									<th>邀请时间</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${page.list}" var="core_supplier">
									<tr>
										<td><input type="checkbox" id="${core_supplier.supplierEnterpriseId.id}" class="i-checks"></td>
										<td>${core_supplier.supplierEnterpriseId.name}</td>
										<td>${core_supplier.supplierEnterpriseId.agencyName}</td>
										<td>${core_supplier.supplierEnterpriseId.agencyPhone}</td>
										<td>${core_supplier.supplierEnterpriseId.agencyEmail}</td>
										<td><fmt:formatDate value="${core_supplier.exportTime}" type="both"/></td>
										<td>
											<c:if test="${core_supplier.state == 0}">
												未邀请
											</c:if>
											<c:if test="${core_supplier.state == 1}">
												已邀请
											</c:if>
											<c:if test="${core_supplier.state == 2}">
												已注册
											</c:if>
										</td>
										<td><fmt:formatDate value="${core_supplier.invitationTime}" type="both"/></td>
										<td>
											<c:if test="${core_supplier.state == 0}">
												<a href="javascript:void(0);" onclick="invitation('${core_supplier.supplierEnterpriseId.id}')" style="color: #ffffff;" class="btn btn-primary btn-xs">邀请</a>
											</c:if>
			                            	<c:if test="${core_supplier.state == 1}">
												<a href="javascript:void(0);" onclick="invitation('${core_supplier.supplierEnterpriseId.id}')" style="color: #ffffff;" class="btn btn-primary btn-xs">再次邀请</a>
											</c:if>
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
	
	<script>
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
	</script>
</body>
</html>