<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>平台--企业管理--供应商管理</title>
	<meta name="decorator" content="default"/>
	 <!--右侧主内容 主体-->
    <script type="text/javascript" language="javascript">
    $(function () {
        //上传/重新上传
        $(".btn_edit").bind("click", function () {
            var base_url = "/csplatform/ajax_file_edit.html";
            base_url += "?id=" + $(this).data("pid");
            layer.open({
                type: 2,
                title: '上传文档资料',
                shadeClose: false,
                shade: 0.8,
                area: ['60%', '40%'],
                content: base_url
            });
        });
    });
</script>
<!--主体区-->
<div class="wrapper wrapper-content animated fadeInRight">
  <div class="row">
    <div class="col-lg-12">
      <div class="ibox float-e-margins">
        <!--标题导航区-->
        <div class="ibox-title">
          <h2>平台管理中心</h2>
          <ol class="breadcrumb">
            <li>
              <a href="/csplatform/ops_files_mgmt.html">资料管理</a>
            </li>
            <li>
              <strong>资料列表</strong>
            </li>
          </ol>
        </div>
        <!--标题导航区 END-->

        <div class="ibox-content">
          <form name="mgmtform" id="mgmtform" enctype="multipart/form-data" action="/csplatform/ops_files_mgmt" accept-charset="UTF-8" method="post"><input name="utf8" type="hidden" value="&#x2713;" /><input type="hidden" name="authenticity_token" value="xCsnl3oxgXSEr/pyXGFDPnWaaMdFyizkVg4DNaXqC/8NHyH+A7qFCfEpdB1B5i0wypHH+WJ94Q1E3msq5yWRYA==" />
              <!--搜索区-->
              <div class="row">
                <div class="col-sm-3">
                  <div class="input-group">
                    <input type="text" id="login" name="login" placeholder="资料名称" class="input-sm form-control" value="">
                   <span class="input-group-btn">                  
					<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>                  
                    </span>
                  </div>
                </div>

                <div class="col-md-12">
                  <!--
                  <a href="/csplatform/ops_file_new.html" class="btn btn-primary btn-sm">新增资料</a>
                  -->
                </div>
              </div>
              <!--搜索区 END-->

              <!--表格主内容区-->
              <div class="dataTables_wrapper form-inline">
                <table class="table table-bordered table-hover">
                  <thead>
                  <tr>
                    <th>资料编号</th>
                    <th>资料名称</th>
                    <th>添加时间</th>
                    <th>更新时间</th>
                    <th>操作</th>
                  </tr>
                  </thead>

                  <tbody>
                      <tr>
                        <td>01</td>
                        <td>经办人、经办企业操作授权书</td>
                        <td>2017-12-02 10:30:06</td>
                        <td>2018-08-31 11:58:23</td>
                       		<td>											 
								<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="btn_edit btn btn-primary btn-xs">新增</a>											 									 
								<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="btn_edit btn btn-primary btn-xs">下载</a>	
								<a href="javascript:void(0);" onclick="openDialog('上传凭证', '${ctx}/yfzk/to-hxqyUploadVoucher?id=${bill_info.id}', '800px', '600px');" style="text-align: center;" class="btn btn-primary btn-xs">上传/重新上传</a>	
								<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="btn_edit btn btn-primary btn-xs">删除</a>											 											 
					        </td>
                      </tr>
                      <tr>
                        <td>02</td>
                        <td>交易流水模板</td>
                        <td>2018-02-10 10:51:27</td>
                        <td>2018-02-10 16:24:44</td>
                        	<td>											 
								<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="btn_edit btn btn-primary btn-xs">新增</a>											 									 
								<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="btn_edit btn btn-primary btn-xs">下载</a>	
								<a href="javascript:void(0);" onclick="openDialog('上传凭证', '${ctx}/yfzk/to-hxqyUploadVoucher?id=${bill_info.id}', '800px', '600px');" style="text-align: center;" class="btn btn-primary btn-xs">上传/重新上传</a>	
								<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="btn_edit btn btn-primary btn-xs">删除</a>											 											 
					        </td>
                      </tr>
                      <tr>
                        <td>03</td>
                        <td>供应商模板</td>
                        <td>2018-02-10 10:51:28</td>
                        <td>2018-02-22 17:14:30</td>
                        	<td>											 
								<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="btn_edit btn btn-primary btn-xs">新增</a>											 									 
								<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="btn_edit btn btn-primary btn-xs">下载</a>	
								<a href="javascript:void(0);" onclick="openDialog('上传凭证', '${ctx}/yfzk/to-hxqyUploadVoucher?id=${bill_info.id}', '800px', '600px');" style="text-align: center;" class="btn btn-primary btn-xs">上传/重新上传</a>	
								<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="btn_edit btn btn-primary btn-xs">删除</a>											 											 
					        </td>
                      </tr>
                      <tr>
                        <td>04</td>
                        <td>安心签用户信息变更申请表 </td>
                        <td>2018-02-10 10:51:28</td>
                        <td>2018-02-10 16:25:06</td>
                       	<td>											 
								<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="btn_edit btn btn-primary btn-xs">新增</a>											 									 
								<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="btn_edit btn btn-primary btn-xs">下载</a>	
								<a href="javascript:void(0);" onclick="openDialog('上传凭证', '${ctx}/yfzk/to-hxqyUploadVoucher?id=${bill_info.id}', '800px', '600px');" style="text-align: center;" class="btn btn-primary btn-xs">上传/重新上传</a>	
								<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="btn_edit btn btn-primary btn-xs">删除</a>											 											 
					    </td>
                      </tr>
                      <tr>
                        <td>05</td>
                        <td>安心签变更公司名称授权书</td>
                        <td>2018-02-10 10:51:47</td>
                        <td>2018-02-22 17:09:11</td>
                        	<td>											 
								<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="btn_edit btn btn-primary btn-xs">新增</a>											 									 
								<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="btn_edit btn btn-primary btn-xs">下载</a>	
								<a href="javascript:void(0);" onclick="openDialog('上传凭证', '${ctx}/yfzk/to-hxqyUploadVoucher?id=${bill_info.id}', '800px', '600px');" style="text-align: center;" class="btn btn-primary btn-xs">上传/重新上传</a>	
								<a href="javascript:void(0);" onclick="submitBill('${bill_info.id}')" style="text-align: center;" class="btn_edit btn btn-primary btn-xs">删除</a>											 											 
					    </td>
                      </tr>
                  </tbody>
                </table>

                <!--分页-->

                <!--底部操作区-->
              </div>
              <!--表格主内容区 END-->
          </form> <!--end form-->
          <!--以下基本保持不变，不需要改动-->
        </div>

      </div>
    </div>
  </div>
</div>
<!--主体区 END-->



    

    <!--Footer区-->
    <div class="footer">
      <div class="pull-right">

      </div>
      <div>
        <strong>Copyright</strong> 供应链金融管理平台 © 2018
      </div>
    </div>
    <!--Footer区 END-->

  </div>

</div>

<script>
    $(document).ready(function () {
//        setTimeout(function () {
//            $.gritter.add({
//                title: '您有2条未读信息',
//                text: '请前往<a href="mailbox.html" class="text-warning">收件箱</a>查看今日任务',
//                time: 10000
//            });
//        }, 2000);
    });
</script>
<script>
    $(document).ready(function () {
        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });

        $(".checkall").on("ifChecked ",function (){
            //全选
            $(".table").iCheck("check");
        });
        $(".checkall").on("ifUnchecked ",function (){
            //全选
            $(".table").iCheck("uncheck");
        });
    });
</script>
<script type="text/javascript" language="javascript">
    $(function () {
        $("#province_id").bind("change", function () {
            var base_url = "/commons/ajax_city_search?province_id=" + $("#province_id").val();
            $.ajax({
                type: "POST",
                url: base_url,
                //data: $('#buyform').serialize(),
                timeout: 5000,
                error: function (request) {
                },
                success: function (data) {
                    $("#city_id").html(data);
                }
            });
        });
    });
</script>
</body>
</html>