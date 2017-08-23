# bizdata-backstage-mybatis
通用基础后台,基于springMvc+spring+mybatis+shiro构建

## v1.0.4
修复登录页在chrome浏览器下会自动填充用户名密码

修复在chrome中用户新增时自动填充用户名密码

去除掉富文本编辑uedit

移动JsonMessageUtil至commons包下,并删除原先的commons.utils包

## v1.0.5
修改资源中原"登录登出"名称为"登录退出"
修改login_logout_log.jsp页面,为jqgrid添加属性rownumWidth:40，此属性用于显示行数记录列的宽度,默认为25,在行数到大三位数时展示补全。
修改user_list.jsp页面,在edit模式下,加入beforeSubmit进行校验,如果用户提交organizationId为空,则提示必须选择部门。
修改user_list.jsp,增加username与password required=true
