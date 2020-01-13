<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台--企业管理--供应商合同列表</title>
	<meta name="decorator" content="default"/>
</head>
<body style="background-color: #fff; ">
	<div>	
		<!--主体区-->
		<div class="wrapper wrapper-content fadeInRight">
			<form name="mgmtform" id="mgmtform" class="form-horizontal" action="" accept-charset="UTF-8" method="post">
      			<!--表格主内容区-->
      			<div class="dataTables_wrapper form-inline">
        			<table class="table table-bordered table-hover">
		          		<thead>
			          		<tr>
			            		<th>合同名称</th>
			            		<th>合同起始日期</th>
			            		<th>合同截止日期</th>
			            		<th>合同状态</th>
			            		<th>操作</th>
			          		</tr>
		          		</thead>

			          	<tbody>
		              		<tr>
			                	<td>上海长然实业有限公司征信报告查询授权书</td>
			                	<td>
			                  		<input type="text" id="start_date_567" name="start_date[567]" class="input-sm form-control" style="width:120px;" value="" placeholder="YYYY-MM-DD">
			                	</td>
			                	<td>
			                  		<input type="text" id="end_date_567" name="end_date[567]" class="input-sm form-control" style="width:120px;" value="" placeholder="YYYY-MM-DD">
			                	</td>
			                	<td></td>
			                	<td>
			                  		<a href="#" class="btn btn-info btn-xs" target="_blank">查看合同</a>
			                	</td>
			              	</tr>
	          			</tbody>
        			</table>
        		</div>
			</form>
		</div>
	</div>
</body>
</html>