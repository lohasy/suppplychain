<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script>
		$(function(){
			loadCompanySelectList();
		});
		
		//加载公司下拉列表数据
		function loadCompanySelectList(){
			$.ajax({
				async : true,
				cache : true,
				url : "${ctx}/CoreEnterpriseCtrl/get-hxqy-list",
				data : {  },
				type: "post",
				dataType: "json",
				success: function(data, status, xhr){
					if(data == undefined || data == null || data.length == 0) return;
					$.each(data, function(key, value){
						$("#hxqy-company").append('<option value="'+ value["id"] +'" title="组织机构：'+ value["orgCode"] +'">'+ value["name"] +'</option>');
					});
					if($("#hxqy-company").attr("val") != undefined && $("#hxqy-company").attr("val") != null && $("#hxqy-company").attr("val") != ""){
		      			$("#hxqy-company option[value="+ $("#hxqy-company").attr("val") +"]")[0].selected = true;
		      		}
				},
				error: function(xhr, status, error){
					console.log(xhr);
				}
			});
			$.ajax({
				async : true,
				cache : true,
				url : "${ctx}/gys/get-gys-list",
				data : {  },
				type: "post",
				dataType: "json",
				success: function(data, status, xhr){
					if(data == undefined || data == null || data.length == 0) return;
					$.each(data, function(key, value){
						$("#gys-company").append('<option value="'+ value["id"] +'" title="组织机构：'+ value["orgCode"] +'">'+ value["name"] +'</option>');
					});
					if($("#gys-company").attr("val") != undefined && $("#gys-company").attr("val") != null && $("#gys-company").attr("val") != ""){
		      			$("#gys-company option[value="+ $("#gys-company").attr("val") +"]")[0].selected = true;
		      		}
				},
				error: function(xhr, status, error){
					console.log(xhr);
				}
			});
		}
	</script>
</head>
<body>
<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
			<h5>用户列表 </h5>
			<div class="ibox-tools">
				<a class="collapse-link">
					<i class="fa fa-chevron-up"></i>
				</a>
				<a class="dropdown-toggle" data-toggle="dropdown" href="form_basic.html#">
					<i class="fa fa-wrench"></i>
				</a>
				<ul class="dropdown-menu dropdown-user">
					<li><a href="#">选项1</a>
					</li>
					<li><a href="#">选项2</a>
					</li>
				</ul>
				<a class="close-link">
					<i class="fa fa-times"></i>
				</a>
			</div>
	</div>
    <div class="ibox-content">
    <sys:message content="${message}"/>
		<!-- 查询条件 -->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/list" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>机构类型：</span>
				<sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" 
				title="机构类型" url="/sys/office/treeData?type=1" cssClass=" form-control input-sm" allowClear="true" />
			<span style="margin-left: 202px;">登录名：</span>
				<form:input path="loginName" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
		 </div>
		 <div class="form-group" style="margin-top: 10px;">
		 	<span>归属公司：</span>
				<%-- <sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}" 
				title="部门" url="/sys/office/treeData?type=2" cssClass=" form-control input-sm" allowClear="true" notAllowSelectParent="true"/> --%>
				<select style="padding:0 12px; width: 200px;" id="hxqy-company" name="core.id" val="${user.core.id}" class="input-sm form-control input-s-sm inline">
           			<option value="">请选择核心企业</option>
         		</select>
         		<select style="margin-left: 5px;padding:0 12px; width: 200px;" id="gys-company" name="supplier.id" val="${user.supplier.id}" class="input-sm form-control input-s-sm inline">
           			<option value="">请选择供应商</option>
         		</select>
			<span style="margin-left: 40px;">姓名：</span>
				<form:input path="name" htmlEscape="false" maxlength="50" class=" form-control input-sm"/>
		 </div>
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="sys:user:add">
				<table:addRow url="${ctx}/sys/user/form" title="用户" width="800px" height="620px"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:edit">
			    <table:editRow url="${ctx}/sys/user/form" id="contentTable"  title="用户" width="800px" height="680px"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:del">
				<table:delRow url="${ctx}/sys/user/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:import">
				<table:importExcel url="${ctx}/sys/user/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:user:export">
	       		<table:exportExcel url="${ctx}/sys/user/export"></table:exportExcel><!-- 导出按钮 -->
	       </shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th><input type="checkbox" class="i-checks"></th>
				<th class="sort-column login_name">登录名</th>
				<th class="sort-column name">姓名</th>
				<th class="sort-column phone">电话</th>
				<th class="sort-column mobile">手机</th>
				<th class="sort-column c.name">机构类型</th>
				<th class="sort-column o.name">所属公司</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td> <input type="checkbox" id="${user.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看用户', '${ctx}/sys/user/form?id=${user.id}','800px', '680px')">${user.loginName}</a></td>
				<td>${user.name}</td>
				<td>${user.phone}</td>
				<td>${user.mobile}</td>
				<td>${user.company.name}</td>
				<td>
					<c:if test="${not empty user.core.name}">
						${user.core.name}
					</c:if>
					<c:if test="${not empty user.supplier.name}">
						${user.supplier.name}
					</c:if>
				</td>
				<td>
					<shiro:hasPermission name="sys:user:view">
						<a href="#" onclick="openDialogView('查看用户', '${ctx}/sys/user/form?id=${user.id}','800px', '680px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:user:edit">
						<a href="#" onclick="openDialog('修改用户', '${ctx}/sys/user/form?id=${user.id}','800px', '700px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:user:del">
						<a href="${ctx}/sys/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table:page page="${page}"></table:page>
		</div>
	</div>
	</div>
</body>
</html>