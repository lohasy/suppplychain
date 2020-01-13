<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp" %>
<html>
<head>
    <title>供应商--融资管理--应收账款管理</title>
    <meta name="decorator" content="default"/>
    <link href="${ctxStatic}/online/style.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic}/online/financing.css?20180203" rel="stylesheet">
    <script>
        $(function () {
            //外部js调用
            laydate({
                elem: '#beginDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
            laydate({
                elem: '#endDate', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
                event: 'focus' //响应事件。如果没有传入event，则按照默认的click
            });
        });
    </script>
</head>
<body class="gray-bg">
<div class="rong_nav">
    <ul>
        <li><a href="${ctx}/yfzk/gyslist?supplierEnterpriseId.id=${fns:getUser().supplier.id}" class="nav_a">应收账款管理</a>
        </li>
        <li><a href="${ctx}/rzglall/gyslist?billId.supplierEnterpriseId.id=${fns:getUser().supplier.id}" class="nav_b">融资管理</a>
        </li>
        <li style="display:none;"><a href="${ctx}/yfzk/gysYfzkList?supplierParentId.id=${fns:getUser().supplier.id}"
                                     class="nav_b">应付账款管理（下级供应商）</a></li>
        <li style="display:none;"><a
                href="${ctx}/rzglall/gysHxqylist?billId.supplierParentId.id=${fns:getUser().supplier.id}" class="nav_b">融资管理（下级供应商）</a>
        </li>
    </ul>
</div>
<div class="wrapper wrapper-content" style="padding: 0;">
    <div class="ibox" style="margin-bottom: 0;">
        <div class="ibox-title">
            <h5><a href="${ctx}/yfzk/gyslist?supplierEnterpriseId.id=${fns:getUser().supplier.id}">应收账款管理</a></h5>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;单据列表</span>
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

            <div class="lines" style="margin-top: 5px; margin-bottom: 20px;">
                <ul>
                    <li style="width: 32%; margin-right: 2%;">
                        <div class="lines_l"><img src="${ctxStatic}/images/lines_01.jpg"></div>
                        <div class="lines_r" style="width: auto; float: none; margin-left: 103px;">
                            <c:if test="${empty supplier_enterprise.paramsId.allQuota}">
                                <span>
                                    <fmt:formatNumber type="currency" currencySymbol="￥" value="0" />
                                </span>
                            </c:if>
                            <c:if test="${not empty supplier_enterprise.paramsId.allQuota}">
	         	 					<span>
										<fmt:formatNumber type="currency" currencySymbol="￥" value="${supplier_enterprise.paramsId.allQuota}"/>
									</span>
                            </c:if>
                            总额度（元）
                        </div>
                    </li>
                    <li style="width: 32%; margin-right: 2%;">
                        <div class="lines_l"><img src="${ctxStatic}/images/lines_02.jpg"></div>
                        <div class="lines_r" style="width: auto; float: none; margin-left: 103px;">
                            <c:if test="${empty supplier_enterprise.paramsId.availableQuota}">
		          					<span>
										<fmt:formatNumber type="currency" currencySymbol="￥" value="0" />
									</span>可用额度（元）
                            </c:if>
                            <c:if test="${not empty supplier_enterprise.paramsId.availableQuota}">
		          					<span>
                                        <fmt:formatNumber type="currency" currencySymbol="￥" value="${supplier_enterprise.paramsId.availableQuota}" />
									</span>可用额度（元）
                            </c:if>
                        </div>
                    </li>
                    <li style="width: 32%; margin:0;">
                        <div class="lines_l"><img src="${ctxStatic}/images/lines_03.jpg"></div>
                        <div class="lines_r" style="width: auto; float: none; margin-left: 103px;">
                            <span><fmt:formatNumber type="currency" currencySymbol="￥" value="${ableFinancingQuota}" /></span>可融资总额（元）
                        </div>
                    </li>
                </ul>
            </div>

            <!-- 查询条件 -->
            <div class="row">
                <form:form style="padding-left: 15px; margin-bottom: 5px;" id="searchForm" modelAttribute="bill_info"
                           action="${ctx}/yfzk/gyslist?supplierEnterpriseId.id=${fns:getUser().supplier.id}"
                           method="post" class="form-inline">
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                    <table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}"
                                      callback="sortOrRefresh();"/><!-- 支持排序 -->
                    <div class="form-group">
                        <input name="num" value="${bill_info.num}" placeholder="单据号" class="input-sm form-control"/>
                        <input style="margin-left: 10px;" name="contractNum" value="${bill_info.contractNum}"
                               placeholder="合同号" class="input-sm form-control"/>
                        <input style="margin-left: 10px; width: 200px; height: 29px;" id="beginDate"
                               name="searchStartDate" value="${bill_info.searchStartDate}" type="text"
                               placeholder="起始日期" readOnly class="laydate-icon form-control layer-date input-sm"/>
                        <input style="margin-left: 10px; width: 200px; height: 29px;" id="endDate" name="searchEndDate"
                               value="${bill_info.searchEndDate}" type="text" placeholder="结束日期" readOnly
                               class="laydate-icon form-control layer-date input-sm"/>
                    </div>
                </form:form>
                <div class="col-sm-12" style="margin-top: 15px; margin-bottom: 10px;">
                    友情提醒：申请融资需要财务账号才能申请，申请完毕，需要负责人账号审核才能提交给银行审核。
                    <div class="pull-right">
                        <button class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()"><i
                                class="fa fa-search"></i> 查询
                        </button>
                        <button style="margin-left: 10px;" class="btn btn-primary btn-rounded btn-outline btn-sm "
                                onclick="reset()"><i class="fa fa-refresh"></i> 重置
                        </button>
                    </div>
                </div>
                <div class="col-sm-12" style="margin-bottom: 15px;">
                    <table id="contentTable"
                           class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
                        <thead>
                        <tr>
                            <th><input type="checkbox" class="i-checks"></th>
                            <th>单据号</th>
                            <th>合同号</th>
                            <th>单据金额（元）</th>
                            <th>单据起始日期</th>
                            <th>单据截止日期</th>
                            <th>剩余融资天数</th>
                            <th>单据状态</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${page.list}" var="bill_info">
                            <tr>
                                <td><input type="checkbox" id="${bill_info.id}" class="i-checks"></td>
                                <td>${bill_info.num}</td>
                                <td>${bill_info.contractNum}</td>
                                <td><fmt:formatNumber type="currency" currencySymbol="￥" value="${bill_info.amount}" /></td>
                                <td><fmt:formatDate value="${bill_info.startDate}" pattern="yyyy-MM-dd"/></td>
                                <td><fmt:formatDate value="${bill_info.endDate}" pattern="yyyy-MM-dd"/></td>
                                <td>${bill_info.dayDiffValue}</td>
                                <td>
                                    <c:if test="${bill_info.state == 0}">
                                        待提交
                                    </c:if>
                                    <c:if test="${bill_info.state == 1}">
                                        待供应商融资
                                    </c:if>
                                    <c:if test="${bill_info.state == 2}">
                                        待供应商审核
                                    </c:if>
                                    <c:if test="${bill_info.state == 3}">
                                        待签约
                                    </c:if>
                                    <c:if test="${bill_info.state == 4}">
                                        供应商审核不通过
                                    </c:if>
                                    <c:if test="${bill_info.state == 5}">
                                        待银行配置
                                    </c:if>
                                    <c:if test="${bill_info.state == 6}">
                                        待银行审核
                                    </c:if>
                                    <c:if test="${bill_info.state == 7}">
                                        银行审核不通过
                                    </c:if>
                                    <c:if test="${bill_info.state == 8}">
                                        待银行复核
                                    </c:if>
                                    <c:if test="${bill_info.state == 9}">
                                        银行复核不通过
                                    </c:if>
                                    <c:if test="${bill_info.state == 10}">
                                        待银行放款
                                    </c:if>
                                    <c:if test="${bill_info.state == 11}">
                                        已放款
                                    </c:if>
                                    <c:if test="${bill_info.state == 12}">
                                        待银行清分
                                    </c:if>
                                    <c:if test="${bill_info.state == 13}">
                                        已完成
                                    </c:if>
                                </td>
                                <td>
                                    <a href="javascript:void(0);"
                                       onclick="openDialogView('单据详情', '${ctx}/yfzk/redirect-info?id=${bill_info.id}', '1000px', '600px')"
                                       style="text-align: center;" class="yao1 bill_details">单据详情</a>
                                    <c:if test="${bill_info.state == 1 && not fn:contains(fns:getUser().roleNames, '供应商负责人') && bill_info.supplierEnterpriseId.state == '5'}">
                                        <a href="${ctx}/yfzk/to-financingApply?id=${bill_info.id}"
                                           style="text-align: center; margin-left: 5px;" class="yao2 confirm_single">融资申请</a>
                                    </c:if>
                                    <a href="javascript:void(0);"
                                       onclick="openDialog('信链付拆分', '${ctx}/financingSplitCtrl/toFinancingSplitPage?billId=${bill_info.id}', '800px', '600px', 'content-frame')"
                                       style="text-align: center; margin-left: 5px; width: 70px;"
                                       class="yao2 confirm_single">信链付拆分</a>
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
    function subSomething() {
        if (document.readyState == "complete") //当页面加载状态
        {
            setTimeout(function () {
                $(".pace-progress").css("top", "52px");
            }, 200);
            parent.setIframeHeight($(".wrapper").height() + 55);
        }
    }
</script>
</body>
</html>