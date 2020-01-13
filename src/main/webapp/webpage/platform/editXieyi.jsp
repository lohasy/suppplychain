<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台--协议管理--编辑协议</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/summernote/summernote.css" rel="stylesheet">
	<link href="${ctxStatic}/summernote/summernote-bs3.css" rel="stylesheet">
	<script src="${ctxStatic}/summernote/summernote.min.js"></script>
	<script src="${ctxStatic}/summernote/summernote-zh-CN.js"></script>
</head>
<body class="pace-done">
<!--主体区-->
<div class="wrapper wrapper-content animated fadeInRight">
	<div class="row">
    	<div class="col-lg-12">
      		<div class="ibox float-e-margins">
        		<!--标题导航区-->
        		<div class="ibox-title">
					<h5><a href="${ctx}/xieyi/list">协议管理</a></h5>
					<c:if test="${type == 1}">
						<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;查看协议</span>
					</c:if>
					<c:if test="${type == 2}">
						<span>&nbsp;&nbsp;&nbsp;&nbsp;/&nbsp;&nbsp;&nbsp;&nbsp;编辑协议</span>
					</c:if>
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
	      			<form name="mgmtform" id="mgmtform" enctype="multipart/form-data" action="${ctx}/xieyi/save" accept-charset="UTF-8" method="post">
	           			<div class="row">
	                 		<dl class="dl-horizontal">
	                    		<c:if test="${type==1}">
	                    			<dt>协议编号：</dt>
	                    			<dd>${protocol_info.id}</dd>
	                    			<dt>协议名称：</dt>
	                    			<dd>${protocol_info.name}</dd>
	                    			<dt>协议内容：</dt>
	                    			<dd>
	                      				<div class="hetong">
	       									<input type="hidden"  id="tmp" value="${protocol_info.template}"/>
	      									<div id="xieyi" class="hetong_nei"></div>
										</div>
					 				</dd>
	         						<div class="clear"></div>
	         					</c:if>
	         					<c:if test="${type==2  or type==3}">
			      					<input type="hidden" name="id"  value="${protocol_info.id}" />
			                    	<dt>协议名称：</dt>
			                    	<dd><input type="text" id="name" name="name" value="${protocol_info.name}" /></dd>
			                    	<dt>协议内容：</dt>
			                    	<dd>
			                    		<div class="hetong">
			      							<div class="hetong_nei">
				       							<div class="mail-text h-200">
				       								<input type="hidden" name="template" id="template" value="${protocol_info.template}"/>
				         							<div class="summernote" id="rich_contents"></div>
				        							<div class="clearfix"></div>
				       							</div>
				       							<input type="button" value="保存"  id="sub"/>
				       						</div>
				       					</div>
				       				</dd>
			      				</c:if>
	         				</dl>
	      				</div>
	          		</form>
        		</div>
        	
   			</div>
    	</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function () {
	    $('.i-checks').iCheck({
	        checkboxClass: 'icheckbox_square-green',
	        radioClass: 'iradio_square-green',
	    });
	    $('.summernote').summernote({
	        lang: 'zh-CN'
	    });
	    $("#rich_contents").next().find(".note-editable").html($("#template").val())
	    $("#xieyi").html($("#tmp").val());
	});
	
	$("#sub").click(function(){
		$("#template").val($(".summernote").next().find(".note-editable").html());
		 $("#mgmtform").submit();
		  return true;
	});

	var edit = function () {
	    $('.click2edit').summernote({
	        focus: true
	    });
	};
	var save = function () {
	    var aHTML = $('.click2edit').code();
	    $('.click2edit').destroy();
	};
</script>

</body>
</html>