<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath() + "/";
	request.setAttribute("ctx", path);
%>
<title>角色管理</title>
<link rel="stylesheet" href="${ctx }assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${ctx }assets/css/ui.jqgrid.css" />
<style type="text/css">
</style>
<!-- ajax layout which only needs content area -->
<div class="page-header">
	<h1>角色管理</h1>
</div>
<!-- /.page-header -->

<div class="row">
	<div class="col-xs-6">
		<!-- jqgrid表单 -->
		<table id="grid-table-role"></table>
		<!-- jqgrid分页元素 -->
		<div id="grid-pager-role"></div>
		<!-- PAGE CONTENT ENDS -->
	</div>
	<div class="col-xs-6">
		<!-- jqgrid表单 -->
		<table id="grid-table-resource"></table>
		<!-- PAGE CONTENT ENDS -->
	</div>
	<!-- /.col -->
</div>
<!-- /.row -->

<input id="shiro_create" type="hidden"
	value="<shiro:hasPermission name='sys:role:create'>true</shiro:hasPermission>">
<input id="shiro_update" type="hidden"
	value="<shiro:hasPermission name='sys:role:update'>true</shiro:hasPermission>">
<input id="shiro_delete" type="hidden"
	value="<shiro:hasPermission name='sys:role:delete'>true</shiro:hasPermission>">

<!-- inline scripts related to this page -->
<script type="text/javascript">
  var scripts = [
      null, "${ctx }assets/js/jqGrid/jquery.jqGrid.src.js", "${ctx }assets/js/jqGrid/i18n/grid.locale-cn.js", null
  ]
  $('.page-content-area').ace_ajax('loadScripts', scripts, function(){
    /* 选择器 */
    var grid_selector_role = "#grid-table-role";
    var pager_selector_role = "#grid-pager-role";
    var grid_selector_resource = "#grid-table-resource";
    
    //调整大小为page-content宽度
    $(window).on('resize.jqGrid', function(){
      $(grid_selector_role).jqGrid('setGridWidth', $(".page-content .col-xs-6").width());
      $(grid_selector_resource).jqGrid('setGridWidth', $(".page-content .col-xs-6").width());
    })
    //resize on sidebar collapse/expand
    var parent_column_role = $(grid_selector_role).closest('[class*="col-"]');
    var parent_column_resource = $(grid_selector_resource).closest('[class*="col-"]');
    $(document).on('settings.ace.jqGrid', function(ev, event_name, collapsed){
      if (event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed') {
        //setTimeout is for webkit only to give time for DOM changes and then redraw!!!
        setTimeout(function(){
          $(grid_selector).jqGrid('setGridWidth', parent_column.width());
          $(grid_selector_resource).jqGrid('setGridWidth', parent_column_resource.width());
        }, 0);
      }
    })

    var roleData = null
    var roleObg = null
    /* jqgrid定义 */
    jQuery(grid_selector_role).jqGrid({
      //解析从Server返回的json数据
      jsonReader : {
        root : "rows", // json中每行的记录
        page : "currentPage", //json中代表当前页码的数据  
        total : "totalPageSize", //json中代表页码总数的数据
        records : "totalRecords",//总记录数
        repeatitems : false,//通常设置为false
        id : "id",//解析rows中的id值,作为该行的id(默认就是id)
      },
      caption : "",//表格的名称
      cellEdit : false,//设置单元格是否可点击修改,false为不可以
      url : "role/roleList",//请求路径
      datatype : "json",//数据类型
      mtype : "GET",//提交方式
      height : 300,//jqgrid高度
      hoverrows : true,//当为false时mouse hovering会被禁用,true时鼠标移动到行时高亮显示
      rownumbers : true,//添加行号
      viewrecords : true,//显示总记录数
      altRows : true,//设置为交替表格,交替颜色区分
      altclass : "",//当altRows为true,交替行的间隔样式,此处是class类,我们可以设置背景等
      multiselect : false,//启用多行选择,出现多选的checkbox
      multiboxonly : true,//当multiselect=true起作用,为true时,点击checkbox时该行被选中，点击其他列，将清除当前行的选中
      autoencode : false,//是否编码服务器返回内容或者提交到服务器的内容中包含的html代码，设置为true时，<会被编码为&lt;
      rowNum : 10,//一页显示记录条数
      rowList : [
          10, 20, 30
      ],//可选的一页显示记录数
      autowidth : true,//true，重新计算表格相对于父元素的宽度。在表格建立时执行。如果建立之后表格父元素宽度改变，需要自动调整宽度，使用setGridWidth方法来实现
      emptyrecords : "当前获取数据为空...",//当表格没有数据时显示的提示信息，仅当viewrecords配置为true时有效。
      loadtext : "数据加载中...",
      editurl : "role/update",//对单元格进行编辑时的url
      pager : pager_selector_role,//分页元素
      pagerpos : false,//定义分页栏位置,left、center、right
      pgbuttons : true,//显示分页按钮
      pginput : true,//是否显示跳转页面输入框
      pgtext : "{0} 共 {1} 页",//显示页数与总页数信息
      recordpos : false,//定义了记录信息的位置： left, center, right
      // sortname : "locked",//默认排序的列名称提交至后台,url中&sidx="xxx"
      // sortorder : "asc",
      colNames : [
          "角色名", '角色描述', '操作'
      ],//定义表头内容显示的字符串数组。注意此数组长度要和colModel配置的数组长度一致
      /* JSON数组对象描述列的参数。 */
      colModel : [
          {
            name : 'role',
            edittype : 'text',
            width : 80,
            editable : true,
            sortable : false,
            jsonmap : "role",
            editrules : {
              required : true
            }
          }, {
            name : 'description',
            edittype : "text",
            width : 80,
            sortable : false,
            editable : true,
            jsonmap : "description",
            editrules : {
              required : true
            }
          }, {
            name : 'option',
            index : '',
            width : 80,//宽度
            fixed : true,//宽度固定不变
            sortable : false,//不作为排序条件
            search : false,//不作为搜索条件
            resize : false,
            viewable : false,//显示详情时不显示
            formatter : 'actions',
            formatoptions : {
              keys : true,//启用回车生效
              
              editbutton : $("#shiro_update").val() == "true",//编辑按钮开关
              editformbutton : false,//是否打开form编辑
              onEdit : function(){//编辑时触发
              },
              
              delbutton : $("#shiro_delete").val() == "true",//删除键开关
              delOptions : {
                url : "role/delete",
                recreateForm : true,
                //提交后触发,用于返回执行状态
                afterSubmit : function(response, postdata){
                  var json = JSON.parse(response.responseText);
                  if (!json.success) {
                    return [
                        false, json.message, null
                    ]
                  } else {
                    return [
                        true, null, null
                    ]
                  }
                },
              },
            }
          }
      ],
      onSelectRow : function(id){
        $(grid_selector_resource).trigger("reloadGrid")
        setTimeout(function(){
          var reObg = null
          for (var i = 0, l = roleData.rows.length; i < l; i++) {
            var obj = roleData.rows[i]
            if (obj.id == id) {
              roleObg = reObg = obj
              break
            }
          }
          for (var l = reObg.resourceList.length; l > 0; l--) {
            var obj = reObg.resourceList[l - 1]
            $(grid_selector_resource).setSelection(obj.id)
          }
          $(grid_selector_resource).setSelection(1)
        }, 500)
      },
      
      //在所有数据加载进入表格和所有处理已经完成后触发。在排序，分页等操作后也会触发。
      loadComplete : function(data){
        roleData = data
        var table = this;
        setTimeout(function(){
          updatePagerIcons(table);
          enableTooltips(table);
        }, 0);
      },
    });
    
    //replace icons with FontAwesome icons like above
    function updatePagerIcons(table){
      var replacement = {
        'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
        'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
        'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
        'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
      };
      $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
        var icon = $(this);
        var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
        
        if ($class in replacement)
          icon.attr('class', 'ui-icon ' + replacement[$class]);
      })
    }
    
    function enableTooltips(table){
      $('.navtable .ui-pg-button').tooltip({
        container : 'body'
      });
      $(table).find('.ui-pg-div').tooltip({
        container : 'body'
      });
    }
    
    //触发window的resize.jqGrid事件
    $(window).triggerHandler('resize.jqGrid');
    
    //底部操作栏事件,包括新增,查询,等...
    jQuery(grid_selector_role).jqGrid('navGrid', pager_selector_role, { //设置事件
      edit : $("#shiro_update").val() == "true",//编辑
      editicon : 'ace-icon fa fa-pencil blue',
      add : $("#shiro_create").val() == "true",//新增
      addicon : 'ace-icon fa fa-plus-circle purple',
      del : $("#shiro_delete").val() == "true",//删除
      delicon : 'ace-icon fa fa-trash-o red',
      search : false,//搜索
      searchicon : 'ace-icon fa fa-search orange',
      refresh : true,//刷新
      refreshicon : 'ace-icon fa fa-refresh green',
      view : true,//查看
      viewicon : 'ace-icon fa fa-search-plus grey',
    }, {
      //编辑操作
      closeAfterEdit : true,
      recreateForm : true,
      viewPagerButtons : false,
      beforeShowForm : function(e){
        var form = $(e[0]);
        form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
        style_edit_form(form);
      },
      //gjf add
      afterSubmit : function(response, postdate){
        var json = JSON.parse(response.responseText);
        if (!json.success) {
          return [
              false, json.message, null
          ]
        } else {
          return [
              true, null, null
          ]
        }
      }
    }, {
      //新增操作
      url : "role/create",
      closeAfterAdd : true,
      recreateForm : true,
      viewPagerButtons : false,
      serializeEditData : function(data){
        return $.param($.extend({}, data, {
          id : ""
        }));
      },
      beforeShowForm : function(e){
        var form = $(e[0]);
        form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
        style_edit_form(form);
      },
      //gjf add
      afterSubmit : function(response, postdate){
        var json = JSON.parse(response.responseText);
        if (!json.success) {
          return [
              false, json.message, null
          ]
        } else {
          return [
              true, null, null
          ]
        }
      }
    }, {
      //删除操作
      url : "role/delete",
      recreateForm : true,
      //提交后触发,用于返回执行状态
      afterSubmit : function(response, postdata){
        var json = JSON.parse(response.responseText);
        if (!json.success) {
          return [
              false, json.message, null
          ]
        } else {
          return [
              true, null, null
          ]
        }
      },
      beforeShowForm : function(e){
        var form = $(e[0]);
        if (form.data('styled'))
          return false;
        
        form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
        style_delete_form(form);
        
        form.data('styled', true);
      },
      afterComplete : function(){
        $(grid_selector_resource).trigger("reloadGrid")
      }
    }, null,//search 表单
    {
      //展示每条记录view 
      recreateForm : true,
      beforeShowForm : function(e){
        var form = $(e[0]);
        form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
      }
    })

    function style_edit_form(form){
      //update buttons classes
      var buttons = form.next().find('.EditButton .fm-button');
      buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();//ui-icon, s-icon
      buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
      buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')

      buttons = form.next().find('.navButton a');
      buttons.find('.ui-icon').hide();
      buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
      buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');
    }
    
    function style_delete_form(form){
      var buttons = form.next().find('.EditButton .fm-button');
      buttons.addClass('btn btn-sm btn-white btn-round').find('[class*="-icon"]').hide();//ui-icon, s-icon
      buttons.eq(0).addClass('btn-danger').prepend('<i class="ace-icon fa fa-trash-o"></i>');
      buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>')
    }
    
    var resourceData = null
    /* jqgrid定义 */
    jQuery(grid_selector_resource).jqGrid({
      treeGrid : true,
      treeGridModel : 'adjacency',
      ExpandColumn : 'name',
      //解析从Server返回的json数据
      jsonReader : {
        root : "rows",
        repeatitems : false
      },
      treeReader : {
        level_field : "level",
        parent_id_field : "parent",
        leaf_field : "isleaf",
        expanded_field : "expanded",
        loaded : "loaded",
        icon_field : null
      },
      datatype : "json", //数据类型
      url : "resource/resourceList",//请求路径
      treeIcons : {
        plus : 'glyphicon glyphicon-plus',
        minus : 'glyphicon glyphicon-minus',
        leaf : 'glyphicon glyphicon-menu-right'
      },
      mtype : "GET",//提交方式
      height : 400,//jqgrid高度
      hoverrows : true,//当为false时mouse hovering会被禁用,true时鼠标移动到行时高亮显示
      altRows : true,//设置为交替表格,交替颜色区分
      altclass : "",//当altRows为true,交替行的间隔样式,此处是class类,我们可以设置背景等
      autoencode : false,//是否编码服务器返回内容或者提交到服务器的内容中包含的html代码，设置为true时，<会被编码为&lt;
      autowidth : true,//true，重新计算表格相对于父元素的宽度。在表格建立时执行。如果建立之后表格父元素宽度改变，需要自动调整宽度，使用setGridWidth方法来实现
      emptyrecords : "当前获取数据为空...",//当表格没有数据时显示的提示信息，仅当viewrecords配置为true时有效。
      loadtext : "数据加载中...",
      pager : false,//分页元素
      pagerpos : false,//定义分页栏位置,left、center、right
      pgbuttons : false,//显示分页按钮
      pginput : false,//是否显示跳转页面输入框
      recordpos : false,//定义了记录信息的位置： left, center, right
      colNames : [
          "资源名称", "资源类型", "父级"
      ],//定义表头内容显示的字符串数组。注意此数组长度要和colModel配置的数组长度一致
      /* JSON数组对象描述列的参数。 */
      scrollrows : true,
      multiselect : true,
      multiboxonly : false,
      colModel : [
          {
            name : 'name',
            edittype : 'text',
            width : 80,
            editable : true,
            sortable : false,
            jsonmap : "name",
          }, {
            name : 'type',
            edittype : 'text',
            width : 80,
            editable : false,
            sortable : false,
            formatter : function(data){
              var val = "";
              switch (data) {
                case "column":
                  val = "栏目";
                  break;
                case "menu":
                  val = "菜单";
                  break;
                case "action":
                  val = "动作";
                  break;
              }
              return val;
            }
          }, {
            name : 'parent',
            editable : false,
            width : 80,
            hidden : true
          }
      ],
      onSelectRow : function(id, flag, dom){
        if (!!dom) {
          if (flag) {
            $.post("role/relation/role/" + roleObg.id + "/resource/" + id, {}, function(data){
              for (var i = 0, l = roleData.rows.length; i < l; i++) {
                var obj = roleData.rows[i]
                if (obj.id == roleObg.id) {
                  obj.resourceList.push({
                    id : id
                  })
                  break
                }
              }
              var parentid = $(grid_selector_resource).jqGrid('getRowData', id).parent
              var list = $(grid_selector_resource).jqGrid('getGridParam', 'selarrrow')
              if (!(list.indexOf("" + parentid) > -1)) {
                $(grid_selector_resource).setSelection(parentid, true, true)
              }
            })
          } else {
            $.post("role/disassociate/role/" + roleObg.id + "/resource/" + id, {}, function(data){
              for (var i = 0, l = roleData.rows.length; i < l; i++) {
                var obj = roleData.rows[i]
                if (obj.id == roleObg.id) {
                  for (var j = 0, ll = obj.resourceList.length; j < ll; j++) {
                    if (obj.resourceList[j].id == id) {
                      obj.resourceList.splice(j, 1)
                      break
                    }
                  }
                  break
                }
              }
              for (var i = 0, l = resourceDate.rows.length; i < l; i++) {
                var obj = resourceDate.rows[i]
                var list = $(grid_selector_resource).jqGrid('getGridParam', 'selarrrow')
                if (obj.parent == id && list.indexOf("" + obj.id) > -1) {
                  $(grid_selector_resource).setSelection(obj.id, true, true)
                }
              }
            })
          }
        }
      },
      //在所有数据加载进入表格和所有处理已经完成后触发。在排序，分页等操作后也会触发。
      loadComplete : function(data){
        resourceDate = data
      },
    });
    
    // 必须添加
    $(document).one('ajaxloadstart.page', function(e){
      $(grid_selector_role).jqGrid('GridUnload');
      $(grid_selector_resource).jqGrid('GridUnload');
      $('.ui-jqdialog').remove();
    });
  })
</script>
