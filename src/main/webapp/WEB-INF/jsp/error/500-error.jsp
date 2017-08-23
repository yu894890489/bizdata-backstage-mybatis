<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<title>500-系统错误</title>

<!-- ajax layout which only needs content area -->
<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->

		<!-- #section:pages/error -->
		<div class="error-container">
			<div class="well">
				<h1 class="grey lighter smaller">
					<span class="blue bigger-125"> <i
						class="ace-icon fa fa-random"></i> 500
					</span> 系统错误
				</h1>

				<hr />
				<h3 class="lighter smaller">系统发生运行错误，我们会尽快修复！！！</h3>

				<div class="space"></div>

				<hr />
				<div class="space"></div>

				<div class="center">
					<a href="javascript:history.back()" class="btn btn-grey"> <i
						class="ace-icon fa fa-arrow-left"></i> 返回
					</a>
				</div>
			</div>
		</div>

		<!-- /section:pages/error -->

		<!-- PAGE CONTENT ENDS -->
	</div>
	<!-- /.col -->
</div>
<!-- /.row -->

<!-- page specific plugin scripts -->
<script type="text/javascript">
  var scripts = [
      null, null
  ]
  $('.page-content-area').ace_ajax('loadScripts', scripts, function(){
    //inline scripts related to this page
  });
</script>
