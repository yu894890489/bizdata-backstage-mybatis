<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<title>Welcome</title>

<!-- ajax layout which only needs content area -->
<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
		Welcome欢迎页面
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