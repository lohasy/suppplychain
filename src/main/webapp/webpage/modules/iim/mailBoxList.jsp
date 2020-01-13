<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>消息列表</title>
   	<meta name="decorator" content="default"/>
</head>
<body class="gray-bg">
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-12 animated fadeInRight">
                <div class="mail-box-header">
                    <h2>
               			消息总计 (总计${mailBoxCount}条，未读${noReadCount}条)
                	</h2>
                    <div class="mail-tools tooltip-demo m-t-md">
              			<form:form  id="searchForm" modelAttribute="mailBox" action="${ctx}/iim/mailBox/" method="post" class="pull-right mail-search">
                   			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
							<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
							<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"></table:sortColumn><!-- 支持排序 -->
                        	<div class="input-group">
                        		<form:input path="mail.title" htmlEscape="false" maxlength="128" class=" form-control input-sm" placeholder="搜索消息" />
                            	<div class="input-group-btn">
                                	<button id="btnSubmit" type="submit" class="btn btn-sm btn-primary">搜索</button>
                            	</div>
                        	</div>
                    	</form:form>
                        <button style="margin-right: 15px;" class="btn btn-white btn-sm" data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()"><i class="fa fa-refresh"></i> 刷新</button>
						<table:delRow url="${ctx}/iim/mailBox/deleteAll" id="contentTable"></table:delRow>
                    </div>
                </div>
                <div class="mail-box">
                    <table id="contentTable" class="table table-hover table-mail">
                    	<thead> 
                   			<tr>
                    			<th class="check-mail">
                               		<input type="checkbox" class="i-checks">
                           		</th>
                    			<th>状态</th>
                    			<th>消息来源</th>
                    			<th>标题</th>
                    			<th>内容</th>
                    			<th>时间</th>
                    			<th>操作</th>
                    		</tr>
                    	</thead>
                        <tbody>
                       		<c:forEach items="${page.list}" var="mailBox">
								<tr>
									<td class="check-mail">
                                   		<input type="checkbox" id="${mailBox.id}" class="i-checks">
	                                </td>
	                                <td>
		                                <a href="${ctx}/iim/mailBox/detail?id=${mailBox.id}">
		                                	<c:if test="${mailBox.readstatus =='0'}">
		                                  		<i class=" fa fa-envelope"> 未读</i>
			                                </c:if>
	                                 		<c:if test="${mailBox.readstatus =='1'}">
			                                	<i class="fa fa fa-envelope-o btn-white "> 已读 </i>
		                                	</c:if>
			                            </a>
	                                </td>
	                                <td>
	                                	<a href="${ctx}/iim/mailBox/detail?id=${mailBox.id}">
                                			${(fns:getUserById(mailBox.sender)).name}
										</a>
									</td>
                                	<td class="mail-ontact">
                                		<a href="${ctx}/iim/mailBox/detail?id=${mailBox.id}">
											${mailBox.mail.title}
										</a>
									</td>
									<td class="mail-subject">
										<a href="${ctx}/iim/mailBox/detail?id=${mailBox.id}">
											${mailBox.mail.overview}
										</a>
	                                </td>
	                                <td class="mail-date">${fns:formatDateTime(mailBox.sendtime)}</td>
									<td>
										<a href="${ctx}/iim/mailBox/delete?id=${mailBox.id}" onclick="return confirmx('确认要删除该消息吗？', this.href)" class="btn btn-info btn-xs btn-danger"><i class="fa fa-trash"></i> 删除</a>
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
</body>

</html>