<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>首页</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
     		if($("#user-info").val().indexOf("核心企业") != -1){
     			location.href = "${ctx}/CoreEnterpriseCtrl/hxqy-home?id=${fns:getUser().core.id}";
     		}else if($("#user-info").val().indexOf("供应商") != -1){
     			location.href = "${ctx}/gys/gys-home?id=${fns:getUser().supplier.id}";
     		}else if($("#user-info").val().indexOf("银行") != -1){
     			location.href = "${ctx}/bank/bankList";
     		}else if($("#user-info").val().indexOf("平台") != -1){
     			location.href = "${ctx}/rzglall/list";
     		}else{
     			location.href = "${ctx}/sys/log";
     		}
		});
	</script>
</head>
<body class="gray-bg">
	<input type="hidden" id="user-info" value="${fns:getUser().roleNames}" />
   <!-- <div class="row  border-bottom white-bg dashboard-header">
        <div class="col-sm-12">
            <blockquote class="text-info" style="font-size:14px">
    			<p>简介描述</p>
            </blockquote>

            <hr>
        </div>
    </div>
      
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="col-sm-4">

                <div class="ibox float-e-margins">
                     <div class="ibox-title">
                        <h5>相关描述一</h5> 
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="index.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                <li><a href="index.html#">选项1</a>
                                </li>
                                <li><a href="index.html#">选项2</a>
                                </li>
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        
                    </div>
                </div>
              
            </div>
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                     <div class="ibox-title">
                        <h5>相关描述二</h5> <span class="label label-primary">K+</span>
                        <div class="ibox-tools">
                            <a class="collapse-link">
                                <i class="fa fa-chevron-up"></i>
                            </a>
                            <a class="dropdown-toggle" data-toggle="dropdown" href="index.html#">
                                <i class="fa fa-wrench"></i>
                            </a>
                            <ul class="dropdown-menu dropdown-user">
                                <li><a href="index.html#">选项1</a>
                                </li>
                                <li><a href="index.html#">选项2</a>
                                </li>
                            </ul>
                            <a class="close-link">
                                <i class="fa fa-times"></i>
                            </a>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="panel-body">
                            <div class="panel-group" id="version">
                            
                            	<div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h5 class="panel-title">
                                            <a data-toggle="collapse" data-parent="#version" href="#">标题一</a><code class="pull-right"></code>
                                        </h5>
                                    </div>
                                    <div id="v2.4" class="panel-collapse collapse in">
                                        <div class="panel-body">
                                            
                                        </div>
                                    </div>
                                </div>   
                			</div>
            			</div>
            		</div>
            	</div>
            </div>
            <div class="col-sm-4">
                <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>相关描述三</h5>
                    </div>
                    <div class="ibox-content">
                        
                    </div>
                </div>
                  <div class="ibox float-e-margins">
                    <div class="ibox-title">
                        <h5>联系信息</h5>

                    </div>
                    <div class="ibox-content">
                        <p><i class="fa fa-send-o"></i> 网址：<a href="#" target="_blank">http://www.*****.com</a>
                        </p>
                        <p><i class="fa fa-qq"></i> QQ：<a href="http://wpa.qq.com/msgrd?v=3&uin=00000000&site=qq&menu=yes" target="_blank">0000000</a>
                        </p>
                        <p><i class="fa fa-weixin"></i> 微信：<a href="javascript:;">00000000</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div> -->

</body>
</html>