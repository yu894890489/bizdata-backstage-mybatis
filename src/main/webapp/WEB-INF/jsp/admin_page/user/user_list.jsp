<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath() + "/";
	request.setAttribute("ctx", path);
%>
<title>用户管理</title>
<link rel="stylesheet" href="${ctx }assets/css/jquery-ui.css" />
<link rel="stylesheet" href="${ctx }assets/css/ui.jqgrid.css" />
<style type="text/css">
</style>
<!-- ajax layout which only needs content area -->
<div class="page-header">
	<h1>用户管理</h1>
</div>
<!-- /.page-header -->

<div class="row">
	<div class="col-xs-12">
		<!-- jqgrid表单 -->
		<table id="grid-table"></table>
		<!-- jqgrid分页元素 -->
		<div id="grid-pager"></div>

		<!-- PAGE CONTENT ENDS -->

		<!-- 弹出树,模态框 -->
		<div id="dialog-message" class="hide">
			<div class="widget-box widget-color-blue2">
				<div class="widget-body">
					<div class="widget-main padding-8">
						<ul id="tree"></ul>
					</div>
				</div>
			</div>
		</div>
		<!-- #diai18nsage -->
	</div>
	<!-- /.col -->
</div>
<!-- /.row -->

<input id="shiro_create" type="hidden"
	value="<shiro:hasPermission name='sys:user:create'>true</shiro:hasPermission>">
<input id="shiro_update" type="hidden"
	value="<shiro:hasPermission name='sys:user:update'>true</shiro:hasPermission>">
<input id="shiro_delete" type="hidden"
	value="<shiro:hasPermission name='sys:user:delete'>true</shiro:hasPermission>">

<!-- inline scripts related to this page -->
<script type="text/javascript">
  var scripts = [
      null, "${ctx }assets/js/jqGrid/jquery.jqGrid.src.js", "${ctx }assets/js/jqGrid/i18n/grid.locale-cn.js",
      "${ctx }assets/js/jquery-ui.js", "${ctx }assets/js/fuelux/fuelux.tree.js", null
  ]
  $('.page-content-area').ace_ajax(
      'loadScripts',
      scripts,
      function(){
        /* 选择器 */
        var grid_selector = "#grid-table";
        var pager_selector = "#grid-pager";
        
        //调整大小为page-content宽度
        $(window).on('resize.jqGrid', function(){
          $(grid_selector).jqGrid('setGridWidth', $(".page-content").width());
        })
        //resize on sidebar collapse/expand
        var parent_column = $(grid_selector).closest('[class*="col-"]');
        $(document).on('settings.ace.jqGrid', function(ev, event_name, collapsed){
          if (event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed') {
            //setTimeout is for webkit only to give time for DOM changes and then redraw!!!
            setTimeout(function(){
              $(grid_selector).jqGrid('setGridWidth', parent_column.width());
            }, 0);
          }
        })

        var organizationList = {}
        var organizationMap = {}

        $.ajax({
          url : "organization/organizationList",
          async : false,
          dataType : "JSON",
          success : function(data){
            var temp = []
            var parentList = []
            for (var i = 0, l = data.rows.length; i < l; i++) {
              var obj = data.rows[i]
              temp.push({
                id : obj.id,
                text : obj.name,
                type : "item"
              })
              organizationMap[obj.id] = obj.name
              parentList.push(obj.id)
            }
            for (var i = 0, l = data.rows.length; i < l; i++) {
              var obj = data.rows[i]
              var index = parentList.indexOf(obj.parent)
              if (index > -1) {
                if (!temp[index].additionalParameters)
                  temp[index].additionalParameters = {}
                if (!temp[index].additionalParameters.children)
                  temp[index].additionalParameters.children = {}
                obj.type = obj.state == "closed" ? 'folder' : 'item'
                temp[index].type = "folder"
                temp[index].additionalParameters.children[obj.id] = temp[i]
              }
            }
            organizationList = temp[0].additionalParameters.children
          }
        })

        function formatterOrganizationList(options, callback){
          var $data = null
          if (!("text" in options) && !("type" in options)) {
            $data = organizationList;//the root tree
            callback({
              data : $data
            });
            return;
          } else if ("type" in options && options.type == "folder") {
            if ("additionalParameters" in options && "children" in options.additionalParameters)
              $data = options.additionalParameters.children || {};
            else
              $data = {}//no data
          }
          
          if ($data != null)//this setTimeout is only for mimicking some random delay
            setTimeout(function(){
              callback({
                data : $data
              });
            }, parseInt(Math.random() * 500) + 200);
        }
        
        //重载 对话框的标题函数支持HTML标签title
        $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
          _title : function(title){
            var $title = this.options.title || '&nbsp;'
            if (("title_html" in this.options) && this.options.title_html == true)
              title.html($title);
            else
              title.text($title);
          }
        }));
        
        /* jqgrid定义 */
        jQuery(grid_selector).jqGrid({
          prmNames : {
            search : "search"//search参数
          },
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
          url : "user/read",//请求路径
          datatype : "json",//数据类型
          mtype : "GET",//提交方式
          height : 300,//jqgrid高度
          hoverrows : true,//当为false时mouse hovering会被禁用,true时鼠标移动到行时高亮显示
          rownumbers : true,//添加行号
          rownumWidth : 40,//设置行数列的宽度
          viewrecords : true,//显示总记录数
          altRows : true,//设置为交替表格,交替颜色区分
          altclass : "",//当altRows为true,交替行的间隔样式,此处是class类,我们可以设置背景等
          multiselect : false,//启用多行选择,出现多选的checkbox
          multiboxonly : false,//当multiselect=true起作用,为true时,点击checkbox时该行被选中，点击其他列，将清除当前行的选中
          autoencode : false,//是否编码服务器返回内容或者提交到服务器的内容中包含的html代码，设置为true时，<会被编码为&lt;
          rowNum : 10,//一页显示记录条数
          rowList : [
              10, 20, 30
          ],//可选的一页显示记录数
          autowidth : true,//true，重新计算表格相对于父元素的宽度。在表格建立时执行。如果建立之后表格父元素宽度改变，需要自动调整宽度，使用setGridWidth方法来实现
          emptyrecords : "当前获取数据为空...",//当表格没有数据时显示的提示信息，仅当viewrecords配置为true时有效。
          loadtext : "数据加载中...",
          editurl : "user/update",//对单元格进行编辑时的url
          pager : pager_selector,//分页元素
          pagerpos : "center",//定义分页栏位置,left、center、right
          pgbuttons : true,//显示分页按钮
          pginput : true,//是否显示跳转页面输入框
          pgtext : "{0} 共 {1} 页",//显示页数与总页数信息
          recordpos : "right",//定义了记录信息的位置： left, center, right
          sortname : "id,locked",//默认排序的列名称提交至后台,url中&sidx="xxx"
          sortorder : "asc",
          colNames : [
              "登录名", '所属机构名', '拥有角色', '用户状态', '密码',
          ],//定义表头内容显示的字符串数组。注意此数组长度要和colModel配置的数组长度一致
          /* JSON数组对象描述列的参数。 */
          colModel : [
              {
                align : 'left',//文字居左显示
                name : 'username',
                edittype : 'text',
                width : 80,
                editable : true,
                sortable : false,
                jsonmap : "username",
                editrules : {
                  required : true
                },
                search : true,//作为搜索项,
                searchoptions : {//搜索参数
                  sopt : [
                    'eq'
                  ],//设置等于
                }
              }, {
                //组织机构
                name : 'organizationId',
                index : 'organizationId',
                width : 80,
                sortable : false,
                editable : true,
                edittype : "custom",
                jsonmap : "organizationId",
                search : false,
                formatter : function(data){
                  var organization_name = organizationMap[data];
                  if (organization_name === undefined) {
                    //如果不存在该部门,则设置为名称组织机构
                    organization_name = '组织机构'
                  }
                  return organization_name
                },
                editoptions : {
                  custom_element : function(text){
                    var id = ""
                    for ( var key in organizationMap) {
                      if (organizationMap[key] == text) {
                        id = key
                        break
                      }
                    }
                    if (id == "") {
                      text = "组织机构"
                    }
                    return $('<a href="javascript:void(0);" data-id="' + id + '">' + text + '</a>');
                  },
                  custom_value : function(dom){
                    return $(dom).data("id")
                  },
                  dataEvents : [
                    {
                      type : "click",
                      fn : function(e){
                        var dialog = $("#dialog-i18n").removeClass('hide').dialog({
                          modal : true,
                          title : "<div class='widget-header widget-header-small'><h4 class='smaller'>组织机构</h4></div>",
                          title_html : true,
                          buttons : [
                            {
                              text : "取消",
                              "class" : "btn btn-minier",
                              click : function(){
                                $(this).dialog("close");
                              }
                            }
                          ]
                        })
                      }
                    }
                  ]
                }
              }, {
                name : 'roles',
                index : 'roles',
                width : 150,
                sortable : false,
                editable : true,
                edittype : "select",
                search : false,
                editoptions : {
                  dataUrl : "role/roleList",
                  buildSelect : get_role
                },
                jsonmap : "roleList",
                formatter : function(data){
                  if (typeof data == "string")
                    return data
                  return data[0].role
                }
              }, {
                name : 'locked',//表格列的名称
                index : 'locked',//索引，其和后台交互的参数为sidx
                align : "left",//显示位置
                width : 70,//默认列的宽度，只能是象素值，不能是百分比
                editable : true,//单元格是否可编辑
                edittype : "checkbox",//可编辑的类型
                editoptions : {
                  value : '0:1'
                },
                editrules : {//编辑的规则
                
                },
                formatter : locked_format_status,//格式化数据
                unformat : un_locked_format_status,//反格式化数据
                jsonmap : "locked",//Server返回json字段映射
                resizable : true,//可以被缩放
                sortable : true,//是否可排序
                search : true,//作为搜索项,
                stype : "select",//select类型
                searchoptions : {//搜索参数
                  sopt : [
                    'eq'
                  ],//设置等于
                  value : "0:启用;1:禁用"
                }
              }, {
                name : 'password',
                edittype : "text",
                editoptions : {//此处添加事件，数组
                  dataEvents : [
                    {
                      type : 'focus',
                      fn : function(e){
                        this.type = 'password'
                      }
                    }
                  ]
                },
                editable : true,
                hidden : true,
                viewable : false,//
                editrules : {
                  //gjf add
                  edithidden : true,
                  custom : true,
                  custom_func : checkPassword
                },
                hidedlg : true
              }, 
              /* {
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
                    url : "user/delete",
                    recreateForm : true,
                    //提交后触发,用于返回执行状态
                    afterSubmit : function(response, postdata){
                      var json = JSON.parse(response.responseText);
                      if (!json.success) {
                        return [
                            false, json.i18n, null
                        ]
                      } else {
                        return [
                            true, null, null
                        ]
                      }
                    },
                  }
                }
              } */
          ],
          
          //在所有数据加载进入表格和所有处理已经完成后触发。在排序，分页等操作后也会触发。
          loadComplete : function(){
            var table = this;
            setTimeout(function(){
              styleCheckbox(table);
              
              updateActionIcons(table);
              updatePagerIcons(table);
              enableTooltips(table);
            }, 0);
          },
        });
        
        function get_role(data){
          data = JSON.parse(data)
          var html = ""
          html += "<select>"
          for (var i = 0, l = data.rows.length; i < l; i++) {
            var obj = data.rows[i]
            html += "<option value=\""+obj.id+"\">" + obj.role + "</option>"
          }
          html += "</select>"
          return html
        }
        
        //locked状态反格式化
        function un_locked_format_status(cellvalue, options, cell){
          setTimeout(function(){
            $(cell).find('input[type=checkbox]').addClass('ace ace-switch ace-switch-8').after(
                '<span class="lbl"></span>');
          }, 0);
          if (cellvalue == '启用') {
            return "0"
          } else {
            return "1"
          }
        }
        
        //是否锁定格式化
        function locked_format_status(cellvalue, options, cell){
          var temp = ""
          if (cellvalue == 0) {
            temp = '<span class="text-success">启用</span>'
          } else {
            temp = '<span class="text-danger">禁用</span>'
          }
          return temp
        }
        
        //it causes some flicker when reloading or navigating grid
        //it may be possible to have some custom formatter to do this as the grid is being created to prevent this
        //or go back to default browser checkbox styles for the grid
        function styleCheckbox(table){
          /**
          	$(table).find('input:checkbox').addClass('ace')
          	.wrap('<label />')
          	.after('<span class="lbl align-top" />')
          
          
          	$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
          	.find('input.cbox[type=checkbox]').addClass('ace')
          	.wrap('<label />').after('<span class="lbl align-top" />');
           */
        }
        
        //unlike navButtons icons, action icons in rows seem to be hard-coded
        //you can change them like this in here if you want
        function updateActionIcons(table){
          /**
          var replacement = 
          {
          	'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue',
          	'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red',
          	'ui-icon-disk' : 'ace-icon fa fa-check green',
          	'ui-icon-cancel' : 'ace-icon fa fa-times red'
          };
          $(table).find('.ui-pg-div span.ui-icon').each(function(){
          	var icon = $(this);
          	var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
          	if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
          })
           */
        }
        
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
        
        $('#tree').ace_tree({
          dataSource : remoteDateSource, // formatterOrganizationList
          loadingHTML : '<div class="tree-loading"><i class="ace-icon fa fa-refresh fa-spin blue"></i></div>',
          'open-icon' : 'ace-icon fa fa-folder-open',
          'close-icon' : 'ace-icon fa fa-folder',
          'selectable' : true,
          multiSelect : false,
          'selected-icon' : null,
          'unselected-icon' : null,
          cacheItems : true,
          cancelSelect : false
        });
        
        function remoteDateSource(options, callback){
          var id = null
          if (!('text' in options || 'type' in options)) {
            id = 1
          } else if ('type' in options && options['type'] == 'folder') {
            id = options.id
          }
          
          if (id !== null) {
            $.ajax({
              url : "organization/brotherOrganization/" + id,
              type : 'GET',
              dataType : 'json',
              success : function(data){
                var return_data = {}
                for (var i = 0, l = data.length; i < l; i++) {
                  var obj = data[i]
                  return_data[obj.id] = obj
                }
                callback({
                  data : return_data
                })
              },
              error : function(response){
              }
            })
          }
        }
        
        $("body").on("click", ".tree-item", function(e){
          var obj = $('#tree').tree('selectedItems')[0]
          $("a[name='organizationId']").data("id", obj.id)
          $("a[name='organizationId']").html(obj.text)
          $("#dialog-i18n").dialog("close")
        })

        //底部操作栏事件,包括新增,查询,等...
        jQuery(grid_selector).jqGrid('navGrid', pager_selector, { //设置事件
          edit : $("#shiro_update").val() == "true",//编辑
          editicon : 'ace-icon fa fa-pencil blue',
          add : $("#shiro_create").val() == "true",//新增
          addicon : 'ace-icon fa fa-plus-circle purple',
          del : $("#shiro_delete").val() == "true",//删除
          delicon : 'ace-icon fa fa-trash-o red',
          search : true,//搜索
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
          beforeSubmit : function(postdata, formid){
            //提交之前进行相应判断,organizationId是否为空
            return checkOrganizationId(postdata)
          },
          beforeShowForm : function(e){
            var form = $(e[0]);
            form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
            style_edit_form(form);
            //设置密码框从text为password
            var password_input = form.find('#password')
            password_input.attr("type", "password")
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
          url : "user/create",
          closeAfterAdd : true,
          recreateForm : true,
          viewPagerButtons : false,
          serializeEditData : function(data){
            return $.param($.extend({}, data, {
              id : ""
            }));
          },
          beforeSubmit : function(postdata, formid){
            //提交之前进行相应判断,organizationId是否为空
            return checkOrganizationId(postdata)
          },
          beforeShowForm : function(e){
            var form = $(e[0]);
            form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
            style_edit_form(form);
            //
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
          url : "user/delete",
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
          }
        }, {
          //search 表单
          recreateForm : true,
          afterShowSearch : function(e){
            var form = $(e[0]);
            form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
            style_search_form(form);
          },
          afterRedraw : function(){
            style_search_filters($(this));
          },
          multipleSearch : false,
        /**
        multipleGroup:true,
        showQuery: true
         */
        }, {
          //展示每条记录view 
          recreateForm : true,
          beforeShowForm : function(e){
            var form = $(e[0]);
            form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
          }
        })

        function style_edit_form(form){
          //enable datepicker on "sdate" field and switches for "stock" field
          //form.find('input[name=sdate]').datepicker({format:'yyyy-mm-dd' , autoclose:true})
          form.find('input[name=locked]').addClass('ace ace-switch ace-switch-8').after('<span class="lbl"></span>');
          //don't wrap inside a label element, the checkbox value won't be submitted (POST'ed)
          //.addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');
          
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
        
        function style_search_filters(form){
          form.find('.delete-rule').val('X');
          form.find('.add-rule').addClass('btn btn-xs btn-primary');
          form.find('.add-group').addClass('btn btn-xs btn-success');
          form.find('.delete-group').addClass('btn btn-xs btn-danger');
        }
        function style_search_form(form){
          var dialog = form.closest('.ui-jqdialog');
          var buttons = dialog.find('.EditTable')
          buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class',
              'ace-icon fa fa-retweet');
          buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class',
              'ace-icon fa fa-comment-o');
          buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class',
              'ace-icon fa fa-search');
        }
        
        //gjf add
        //设置弹出层form居中
        function centerForm(){
          
        }
        
        //gjf add
        //检查密码栏不为空并且为0到18位
        function checkPassword(val, colname){
          var length = $.trim(val).length
          if (length >= 6) {
            return [
                true, ""
            ]
          } else {
            return [
                false, "密码必须为6位以上英文数字"
            ]
          }
        }
        
        //检查组织机构是否选择
        function checkOrganizationId(postdata){
          //提交之前进行相应判断,organizationId是否为空
          var organizationId = postdata.organizationId;
          var message = "";
          var state = false;
          if ('' == organizationId) {
            message = "必须为用户分配所属部门"
            state = false;
          } else {
            state = true;
          }
          return [
              state, message
          ]
        }
        
        // 必须添加
        $(document).one('ajaxloadstart.page', function(e){
          $(grid_selector).jqGrid('GridUnload');
          $('.ui-jqdialog').remove();
        });
      })
</script>
