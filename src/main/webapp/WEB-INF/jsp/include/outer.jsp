<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath() + "/";
	request.setAttribute("ctx", path);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<!-- logo ico -->
<link rel="shortcut icon" type="image/x-icon"
	href="${ctx }assets/images/logo.ico" media="screen" />
<title>欢迎页</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<!--[if !IE]> -->
<link rel="stylesheet" href="${ctx }assets/css/pace.css" />

<script
	data-pace-options='{ "ajax": true, "document": true, "eventLag": false, "elements": false }'
	src="${ctx }assets/js/pace.js"></script>

<!-- <![endif]-->

<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="${ctx }assets/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx }assets/css/font-awesome.css" />

<!-- text fonts -->
<link rel="stylesheet" href="${ctx }assets/css/ace-fonts.css" />

<!-- ace styles -->
<link rel="stylesheet" href="${ctx }assets/css/ace.css"
	class="ace-main-stylesheet" id="main-ace-style" />

<!--[if lte IE 9]>
		<link rel="stylesheet" href="${ctx }assets/css/ace-part2.css" class="ace-main-stylesheet" />
	<![endif]-->

<!--[if lte IE 9]>
	  <link rel="stylesheet" href="${ctx }assets/css/ace-ie.css" />
	<![endif]-->

<!-- ace settings handler -->
<script src="${ctx }assets/js/ace-extra.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

<!--[if lte IE 8]>
		<script src="${ctx }assets/js/html5shiv.js"></script>
		<script src="${ctx }assets/js/respond.js"></script>
	<![endif]-->
</head>
<body class="no-skin">
	<!-- #section:basics/navbar.layout -->
	<div class="navbar navbar-default">
		<script type="text/javascript">
      try {
        ace.settings.check('navbar', 'fixed')
      } catch (e) {
      }
    </script>

		<div class="navbar-container" id="navbar-container">
			<!-- /section:basics/sidebar.mobile.toggle -->
			<div class="navbar-header pull-left">
				<!-- #section:basics/navbar.layout.brand -->
				<a href="#" class="navbar-brand"> <small> <i
						class="fa fa-desktop"></i> 后台管理
				</small>
				</a>

				<!-- /section:basics/navbar.layout.brand -->

				<!-- #section:basics/navbar.toggle -->

				<!-- /section:basics/navbar.toggle -->
			</div>

			<!-- #section:basics/navbar.dropdown -->
			<div class="navbar-buttons navbar-header pull-right"
				role="navigation">
				<ul class="nav ace-nav">
					<li class="light-blue"><a data-toggle="dropdown" href="#"
						class="dropdown-toggle"> <span class="user-info"> <small>Welcome,</small>
								<shiro:principal />
						</span> <i class="ace-icon fa fa-caret-down"></i>
					</a>
						<ul
							class="user-menu dropdown-menu-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
							<li><a href="${ctx }/admin/logout"> <i
									class="ace-icon fa fa-power-off"></i> 安全退出
							</a></li>
						</ul></li>
				</ul>
			</div>
			<!-- /section:basics/navbar.dropdown -->
		</div>
		<!-- /.navbar-container -->
	</div>

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
      try {
        ace.settings.check('main-container', 'fixed')
      } catch (e) {
      }
    </script>

		<!-- #section:basics/sidebar -->
		<div id="sidebar" class="sidebar responsive">
			<script type="text/javascript">
        try {
          ace.settings.check('sidebar', 'fixed')
        } catch (e) {
        }
      </script>

			<div class="sidebar-shortcuts" id="sidebar-shortcuts">
				<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
					<button class="btn btn-success">
						<i class="ace-icon fa fa-signal"></i>
					</button>

					<button class="btn btn-info">
						<i class="ace-icon fa fa-pencil"></i>
					</button>

					<!-- #section:basics/sidebar.layout.shortcuts -->
					<button class="btn btn-warning">
						<i class="ace-icon fa fa-users"></i>
					</button>

					<button class="btn btn-danger">
						<i class="ace-icon fa fa-cogs"></i>
					</button>

					<!-- /section:basics/sidebar.layout.shortcuts -->
				</div>

				<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
					<span class="btn btn-success"></span> <span class="btn btn-info"></span>

					<span class="btn btn-warning"></span> <span class="btn btn-danger"></span>
				</div>
			</div>
			<!-- /.sidebar-shortcuts -->

			<ul class="nav nav-list">
			</ul>
			<!-- /.nav-list -->

			<!-- #section:basics/sidebar.layout.minimize -->
			<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
				<i class="ace-icon fa fa-angle-double-left"
					data-icon1="ace-icon fa fa-angle-double-left"
					data-icon2="ace-icon fa fa-angle-double-right"></i>
			</div>

			<!-- /section:basics/sidebar.layout.minimize -->
			<script type="text/javascript">
        try {
          ace.settings.check('sidebar', 'collapsed')
        } catch (e) {
        }
      </script>
		</div>
		<!-- /section:basics/sidebar -->

		<div class="main-content">
			<div class="main-content-inner">
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
            try {
              ace.settings.check('breadcrumbs', 'fixed')
            } catch (e) {
            }
          </script>

					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a
							href="javascript:void(0);">Home</a></li>
					</ul>
					<!-- /.breadcrumb -->
				</div>
				<!-- /section:basics/content.breadcrumbs -->

				<div class="page-content">
					<!-- #section:settings.box -->
					<div class="ace-settings-container" id="ace-settings-container">
						<div class="btn btn-app btn-xs btn-warning ace-settings-btn"
							id="ace-settings-btn">
							<i class="ace-icon fa fa-cog bigger-130"></i>
						</div>

						<div class="ace-settings-box clearfix" id="ace-settings-box">
							<div class="pull-left width-50">
								<!-- #section:settings.skins -->
								<div class="ace-settings-item">
									<div class="pull-left">
										<select id="skin-colorpicker" class="hide">
											<option data-skin="no-skin" value="#438EB9">#438EB9</option>
											<option data-skin="skin-1" value="#222A2D">#222A2D</option>
											<option data-skin="skin-2" value="#C6487E">#C6487E</option>
											<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
										</select>
									</div>
									<span>&nbsp; Choose Skin</span>
								</div>

								<!-- /section:settings.skins -->

								<!-- #section:settings.navbar -->
								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-navbar" /> <label class="lbl"
										for="ace-settings-navbar"> Fixed Navbar</label>
								</div>

								<!-- /section:settings.navbar -->

								<!-- #section:settings.sidebar -->
								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-sidebar" /> <label class="lbl"
										for="ace-settings-sidebar"> Fixed Sidebar</label>
								</div>

								<!-- /section:settings.sidebar -->

								<!-- #section:settings.breadcrumbs -->
								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-breadcrumbs" /> <label class="lbl"
										for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
								</div>

								<!-- /section:settings.breadcrumbs -->

								<!-- #section:settings.rtl -->
								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-rtl" /> <label class="lbl"
										for="ace-settings-rtl"> Right To Left (rtl)</label>
								</div>

								<!-- /section:settings.rtl -->

								<!-- #section:settings.container -->
								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-add-container" /> <label class="lbl"
										for="ace-settings-add-container"> Inside <b>.container</b></label>
								</div>

								<!-- /section:settings.container -->
							</div>
							<!-- /.pull-left -->

							<div class="pull-left width-50">
								<!-- #section:basics/sidebar.options -->
								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-hover" /> <label class="lbl"
										for="ace-settings-hover"> Submenu on Hover</label>
								</div>

								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-compact" /> <label class="lbl"
										for="ace-settings-compact"> Compact Sidebar</label>
								</div>

								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-highlight" /> <label class="lbl"
										for="ace-settings-highlight"> Alt. Active Item</label>
								</div>

								<!-- /section:basics/sidebar.options -->
							</div>
							<!-- /.pull-left -->
						</div>
						<!-- /.ace-settings-box -->
					</div>
					<!-- /.ace-settings-container -->
					<!-- /section:settings.box -->

					<div class="page-content-area" data-ajax-content="true">
						<!-- ajax content goes here -->
					</div>
					<!-- /.page-content-area -->
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->

		<div class="footer">
			<div class="footer-inner">
				<!-- #section:basics/footer -->
				<div class="footer-content">
					<a href="http://www.bizdata-info.com/" target="_blank">南京商数信息技术有限公司</a>
					<span class="bigger-120"> &copy; 2015-2016</span>
				</div>
				<!-- /section:basics/footer -->
			</div>
		</div>

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>

	<!-- basic scripts -->

	<!--[if !IE]> -->
	<script type="text/javascript">
    window.jQuery || document.write("<script src='${ctx }assets/js/jquery.js'>" + "<"+"/script>");
  </script>
	<!-- <![endif]-->

	<!--[if IE]>
	<script type="text/javascript">
		window.jQuery || document.write("<script src='${ctx }assets/js/jquery1x.js'>"+"<"+"/script>");
	</script>
	<![endif]-->
	<script type="text/javascript">
    if ('ontouchstart' in document.documentElement)
      document.write("<script src='${ctx }assets/js/jquery.mobile.custom.js'>" + "<"+"/script>");
  </script>
	<script src="${ctx }assets/js/bootstrap.js"></script>

	<!-- gijianfeng 2015-12-20 add 根据后台返回的全局量，401值进行页面的跳转，此处用于超时跳转到登录页 -->
	<script>
    $.ajaxSetup({
      complete : function(XMLHttpRequest, textStatus){
        if ("401" == XMLHttpRequest.status) {
          alert("你已被系统强制退出!原因可能如下:\n\n1.登录超过\n2.你的账号在别处登录\n3.账户已被管理员禁用");
          window.location.href = "${ctx}/admin/";
        }
      }
    })
  </script>
	<script>
    $.ajax({
      url : "getAllUserMenus",
      async : false,
      dataType : "JSON",
      success : function(mes){
        var data = mes
        var html = ""

        html += '<li class="active">'
        html += '<a data-url="page/index" href="#page/index">'
        html += '<i class="menu-icon fa fa-tachometer"></i>'
        html += '<span class="menu-text">首页</span>'
        html += '</a>'
        html += '<b class="arrow"></b>'
        html += '</li>'

        for (var i = 0, l = data.menus.length; i < l; i++) {
          var f = data.menus[i]
          if (f.menus.length > 0) {
            html += '<li class="">'
            html += '<a href="#" class="dropdown-toggle">'
            html += '<i class="menu-icon '+f.icon+'"></i>'
            html += '<span class="menu-text">' + f.name + '</span>'
            html += '<b class="arrow fa fa-angle-down"></b>'
            html += '</a>'
            html += '<b class="arrow"></b>'
            html += '<ul class="submenu">'
            for (var j = 0, ll = f.menus.length; j < ll; j++) {
              var c = f.menus[j]
              html += '<li class="">'
              html += '<a data-url="page'+c.url+'" href="#page'+c.url+'">'
              html += '<i class="menu-icon fa fa-caret-right"></i>'
              html += c.name
              html += '</a>'
              html += '<b class="arrow"></b>'
              html += '</li>'
            }
            html += '</ul>'
            html += '</li>'
          } else {
            //当只有一级菜单时，展示菜单
            html += '<li class="">'
            html += '<a data-url="page'+f.url+'" href="#page'+f.url+'">'
            html += '<i class="menu-icon '+f.icon+'"></i>'
            html += '<span class="menu-text"> ' + f.name + ' </span>'
            html += '</a>'
            html += '<b class="arrow"></b>'
            html += '</li>'
          }
        }
        $("#sidebar	.nav-list").html(html)
        var index = location.href.split("#")[1]
        $("a[data-url='" + index + "']").addClass("active")
      }
    })
  </script>

	<!-- ace scripts -->
	<script src="${ctx }assets/js/ace/elements.scroller.js"></script>
	<script src="${ctx }assets/js/ace/elements.colorpicker.js"></script>
	<script src="${ctx }assets/js/ace/elements.fileinput.js"></script>
	<script src="${ctx }assets/js/ace/elements.typeahead.js"></script>
	<script src="${ctx }assets/js/ace/elements.wysiwyg.js"></script>
	<script src="${ctx }assets/js/ace/elements.spinner.js"></script>
	<script src="${ctx }assets/js/ace/elements.treeview.js"></script>
	<script src="${ctx }assets/js/ace/elements.wizard.js"></script>
	<script src="${ctx }assets/js/ace/elements.aside.js"></script>
	<script src="${ctx }assets/js/ace/ace.js"></script>
	<script src="${ctx }assets/js/ace/ace.ajax-content.js"></script>
	<script src="${ctx }assets/js/ace/ace.touch-drag.js"></script>
	<script src="${ctx }assets/js/ace/ace.sidebar.js"></script>
	<script src="${ctx }assets/js/ace/ace.sidebar-scroll-1.js"></script>
	<script src="${ctx }assets/js/ace/ace.submenu-hover.js"></script>
	<script src="${ctx }assets/js/ace/ace.widget-box.js"></script>
	<script src="${ctx }assets/js/ace/ace.settings.js"></script>
	<script src="${ctx }assets/js/ace/ace.settings-rtl.js"></script>
	<script src="${ctx }assets/js/ace/ace.settings-skin.js"></script>
	<script src="${ctx }assets/js/ace/ace.widget-on-reload.js"></script>
	<script src="${ctx }assets/js/ace/ace.searchbox-autocomplete.js"></script>
</body>
</html>