$(function() {
	$('#addUser').linkbutton({
		text : "指定权限",
		iconCls : "icon-add"
	});
	$('#search').linkbutton({
		text : "查询",
		iconCls : "icon-search"
	});
	$('#resetUser').linkbutton({
		text : "重置",
		iconCls : "icon-reset"
	});
	$("#loginName").autocomplete({
		minLength : 1,
		source : function(request, response) {
			var term = $.trim(arguments[0].term);
			if (term != "") {
				$.getJSON(getRootPath() + '/manager/user/query', {
					"term" : term
				}, function(result) {
					response($.map(result, function(item) {
						return item.loginName;
					}));
				});
			}
		}
	});

	$("#addUser").click(function() {
		add();
	}),
	// 角色
	$('#role1').combogrid({
		valueField : 'uuid',
		textField : 'name',
		multiple : true,
		editable : false,
		columns : [ [ {
			checkbox : true,
			align : "center",
			field : 'box'
		}, {
			field : 'name',
			title : '全选',
			fitColumns : true
		} ] ],
		onSelect : function(record) {
			change();
		}

	});
	$.getJSON(getRootPath() + '/manager/get/roleAll', {
		"random" : Math.random()
	}, function(result) {
		resultHandler(result, function process(data) {
			$('#role1').combogrid('grid').datagrid('loadData', data);
		});
	});

	$('#positionTable').datagrid(
			{
				rownumbers : true,
				sortName : 'loginName',
				sortOrder : 'asc',
				remoteSort : true,
				pagination : true,
				pageNumber : 1,
				pageSize : 15,
				pageList : [ 15, 20, 30 ],
				idField : 'uuid',
				height : /*$('#gg').parent().height() - 75*/"auto",
				nowrap : true,
				striped : true,
				singleSelect : true,
				remoteSort : true,
				// 列设置
				columns : [ [
						{
							field : 'type',
							title : '类型',
							hidden : true
						},
						{
							field : 'loginName',
							title : '登录名',
							width : getWidth(0.2),
							sortable : true,
							resizable : true,
							align : 'center'
						},
						{
							field : 'roleName',
							title : '角色',
							width : getWidth(0.3),
							resizable : true,
							align : 'center'

						},
						{
							field : 'status',
							title : '状态',
							width : getWidth(0.1),
							resizable : true,
							sortable : true,
							align : 'center',
							formatter : function(value) {
								var values = "";
								if (value == 10) {
									values = "生效";
								} else {
									values = "终止";
								}
								return values;
							}
						},
						{
							field : 'createdTime',
							title : '注册时间',
							width : getWidth(0.2),
							sortable : true,
							align : 'center',
							formatter : function(value) {
								return dateOf(value);
							}
						},
						{
							field : 'opt',
							title : '操作',
							width : getWidth(0.14),
							sortable : false,
							resizable : true,
							align : 'center',
							formatter : function(value, item, index) {
								if (item.uuid == "cheba_admin_uuid") {
									return null;
								}
								var id = item.uuid;
								
								var edit = "<img title='编辑' style='cursor:pointer;' src="+getRootPath()+"/css/lib/jeasyui/themes/icons/pencil.png onclick='edituser("
								+ '"' + id + '"' + ',' + '"'
								+ item.loginName + '"' + ',' + '"'
								+ item.type + '"' + ',' + '"'
								+ item.status + '"' + ");'>";
								return edit;
							}
						} ] ],
				onSortColumn : function(sort, order) {
					$("#sort").val(
							$("#positionTable").datagrid('options').sortName);
					$("#order").val(
							$("#positionTable").datagrid('options').sortOrder);
					search1();
				}
			});
	page();

	$.getJSON(getRootPath() + '/manager/user/search', {
		"random" : Math.random(),
		pageSize : $("#pageSize").val(),
		pageNumber : $("#pageNumber").val(),
		sort : $("#sort").val(),
		order : $("#order").val()
	}, function(result) {
		resultHandler(result, function process(data) {
			$('#positionTable').datagrid('loadData', data);
		});
	});
	$("#search").click(function() {
		search();
	});
	$("#resetUser").click(function() {
		resetUser();
	});
});

function getWidth(percent) {
	return $('#positionTable').parent().width() * percent;
}
function resetUser() {
	$('#loginName').val("");
	$('#role1').combogrid('clear');
	$('#role').val("");
	change();
	var checked = $("#gg td[field='cks'] .datagrid-header-check input").attr(
			"checked");
	$(".panel .datagrid-header-check input").attr("checked", false);
	$("#gg .datagrid-header-check input").attr("checked", checked);

}
function search() {
	$("#positionTable").datagrid({
		sortName : 'loginName',
		sortOrder : 'asc',
		pageNumber : 1,
		pageSize : 15,
		pageList : [ 15, 20, 30 ]
	});
	page();
	tiaojians();
	var pageSize = $('#positionTable').datagrid('options').pageSize;
	var pageNumber = $('#positionTable').datagrid('options').pageNumber;
	var sort = $('#positionTable').datagrid('options').sortName;
	var order = $('#positionTable').datagrid('options').sortOrder;

	$('#ff')
			.ajaxSubmit(
					{
						url : getRootPath() + '/manager/user/search?pageSize='
								+ pageSize + "&pageNumber=" + pageNumber
								+ "&sort=" + sort + "&order=" + order
								+ "&random=" + Math.random(),

						success : function(result) {
							resultHandler(result, function process(data) {
								$("#isdirty").val("true");
								$('#positionTable').datagrid("loadData", data);
							});
						}
					});
}
function search1() {
	var pageSize = $('#positionTable').datagrid('options').pageSize;
	var pageNumber = $('#positionTable').datagrid('options').pageNumber;
	var sort = $('#positionTable').datagrid('options').sortName;
	var order = $('#positionTable').datagrid('options').sortOrder;

	// 角色条件
	tiaojians();
	$('#ff')
			.ajaxSubmit(
					{
						url : getRootPath() + '/manager/user/search?pageSize='
								+ pageSize + "&pageNumber=" + pageNumber
								+ "&sort=" + sort + "&order=" + order
								+ "&random=" + Math.random(),

						success : function(result) {
							resultHandler(result, function process(data) {
								$("#isdirty").val("true");
								$('#positionTable').datagrid("loadData", data);
							});
						}
					});
}

function page() {
	var pager = $('#positionTable').datagrid('getPager'); // get the pager of
	pager.pagination({
		showPageList : true,
		showRefresh : false,
		onSelectPage : function(pageNumber, pageSize) {
			if ($("#isdirty").val() == "false") {
				search();
			} else {
				search1();
			}
		},
		onRefresh : function(pageNumber, pageSize) {

		},
		onChangePageSize : function(pageSize) {
			if ($("#isdirty").val() == "false") {
				search();
			} else {
				search1();
			}
		}
	});
}
function change() {
	$("#isdirty").val("false");
}
function tiaojians() {
	var rows = $('#role1').combogrid('grid').datagrid('getSelections');
	var role = [];
	for (var i = 0; i < rows.length; i++) {
		role.push(rows[i].uuid);
	}
	$("#role").val(role);
}
function add() {
	$('<div id="dd" title="指定权限" style="width: 550px; height: 560px;">'
					+ '<div>'
					+ '<table style="padding: 5px;">'
					+ '<tr>'
					+ '<td>'
					+ '<form id="addUserForm" method="post" name="addUserForm">'
					+ '<div><div><table id="getAllUsers"></table></div><div>'
					+ '<div style="float: left;">' + '<table>' + '<tr>'
					+ '<td align=right>用户名：</td>' + '<td>'
					+ '<input id="loginNameAdd" name="loginNameAdd" disabled="disabled" style="width: 145px;" />'
					+ '</td>'
					+ '<td>'
					+ '<label style="color: red">*</label>'
					+ '</td>'
					+ '</tr>'
					+ '<tr>'
					+ '<td align=right>用户状态：</td>'
					+ '<td><input id="status" name="status"/></td>'
					+ '<td><label style="color: red">*</label></td>'
					+ '</tr><tr>'
					+ '<td align=right>用户类型：</td>'
					+ '<td><input id="type" name="type" style="width: 145px;"/></td>'
					+ '<td><label style="color: red">*</label></td>'
					+ '</tr>'
					+ '<tr>'
					+ '<td valign="top" align=right>角色：<br>'
					+ '<label style="color: red"><按Ctrl多选></label></td>'
					+ '<td><select multiple="multiple" id="roleAdd" onblur="onblurRoleChange(this)" style="width: 150px; height: 220px;"></select></td>'
					+ '</tr>'
					+ '</table>'
					+ '</div>'
					+ '<div style="float: left; height: 310px; overflow: auto;width: 245px;">'
					+ '<table>'
					+ '<tr><td valign="top" align=right>可用权限：</td><td><ul id="tree" class="ztree"></ul></td></tr>'
					+ '</table>'
					+ '</div>'
					+ '</div>'
					+ '</div>'
					+ '</form>'
					+ '</td></tr>' + '</table>').appendTo($("#dialogDiv"));
	$('#getAllUsers').datagrid(
			{
				noHeader : true,
				height : 160,
				width : 490,
				singleSelect : true,
				idField : 'uuid',
				nowrap : true,
				striped : false,
				rownumbers : false,
				columns :
				[
					[
							{
								field : 'loginName',
								title : '登录名',
								width : 100,
//								sortable : true,
								resizable : true,
								align : 'left'
							},
							{
								field : 'userName',
								title : '用户名',
								width : 100,
//								sortable : true,
								resizable : true,
								align : 'center'

							},
							{
								field : 'email',
								title : '邮箱',
								width : 100,
//								sortable : true,
								resizable : true,
								align : 'center'
							},
							{
								field : 'createdTime',
								title : '注册时间',
								width : 100,
								align : 'center',
								formatter : function(value)
								{
									if (value)
									{
										var date = new Date(value);
										return date.getFullYear() + '-'
												+ (date.getMonth() + 1) + '-'
												+ date.getDate();
									}

								}

							},
							{
								field : 'opt',
								title : '操作',
								width : 64,
								sortable : false,
								resizable : true,
								align : 'left',
								formatter : function(value, item, index)
								{
									var edit = '<a href="#onClickRow()">选择</a>';
									return edit;
								}
							}
					]
				],
				onClickRow : function(rowIndex, rowData)
				{
					 $("#loginNameAdd").val(rowData.loginName);
					 $('#status').combobox("clear");
					 $("#type").combobox("setValue",rowData.type);
				}
			});
	$.getJSON(getRootPath() +'/manager/loadAllUserInfo/data',{"random" : Math.random(),type:"20"}, function(result)
			{
				resultHandler(result, function process(data)
				{
					$('#getAllUsers').datagrid('loadData', data);
				});
			});
	$('#tijiao').linkbutton({
		text : "提交",
		iconCls : "icon-ok"
	});
	$('#status').combobox({
		valueField : 'key',
		textField : 'value',
		multiple : false,
		editable : false,
		panelHeight : "auto",
		width : 150,
		listHeight : 50
	});
	$.getJSON(getRootPath() + '/common/userState/all', function(result) {
		resultHandler(result, function process(data) {
			$('#status').combobox('loadData', data);
		});
	});
	$('#type').combobox({
		valueField : 'key',
		textField : 'value',
		multiple : false,
		editable : false,
		panelHeight:"auto",
		width : 150,
		listHeight : 50,
		hasDownArrow:false
	});
	$.getJSON(getRootPath()+'/common/userType/all', function(result)
			{
				resultHandler(result, function process(data)
				{
					$("#type").combobox('loadData', data);
				});
	});
	$.getJSON(getRootPath() + '/manager/user/menus', {
		"random" : Math.random()
	}, function(result) {
		resultHandler(result, function process(data) {
			tree(data);
		});
	});
	$.getJSON(getRootPath() + '/manager/get/roleAll',
	{
		"random" : Math.random()
	},function(result){
		resultHandler(result,function process(data) {
		// 先加载已拥有权限
			for ( var i = 0; i < data.length; i++)
			{
				$("#roleAdd").append("<option value='"+data[i].uuid+"'>"+data[i].name +"</option>");
			}
		});
	});
	$("#roleAdd").change(function()
			{
				var d = $("#roleAdd").val();
				onRoleChange(d, "");
			});
	$('#dd').dialog({
		modal : true,
		buttons : [ {
			text : '提交',
			iconCls : 'icon-ok',
			handler : function() {
				var loginNameAdd = $("#loginNameAdd").val();
				var roleValue = $("#roleAdd").val();
				if(roleValue!=null){
					var r = '';
					for ( var i = 0; i < roleValue.length; i++)
					{
						if (r != '')
							r += ',';
						r += roleValue[i].uuid;
					}
				}
				$('#addUserForm').ajaxSubmit(
						{
							dataType:'json',
							url : getRootPath() + "/manager/useradd/submit?loginName="
									+loginNameAdd+ "&roleUuid=" + roleValue+"&random="+ Math.random(),
							beforeSubmit:function(){
								if($('#addUserForm').form("validate")){
									if ($("#loginNameAdd").val()=="")
									{
										$.messager.alert('提示', '请选择用户','warning');
										return false;
									}
									if ($('#status').combobox('getValues')=="")
									{
										$.messager.alert('提示', '请选择用户状态','warning');
										return false;
									}
									if ($('#type').combobox('getValues')=="")
									{
										$.messager.alert('提示', '请选择用户类型','warning');
										return false;
									}
									if ($("#roleAdd").val()==""||$("#roleAdd").val()==null)
									{
										$.messager.alert('提示', '请选择角色','warning');
										return false;
									}
									return true;
								}else{
									
									return false;
								}
							},		
							success : function(result)
							{
								resultHandler(result, function(data)
								{
									if (data == '添加成功')
									{
										$.messager.alert('提示', data, 'info', function()
										{
											closeFunction();
										});
										$("a.panel-tool-close").attr("onClick","closeFunction()");
									}
									else
									{
										$.messager.alert('提示', data, 'info', function(){});
									}
								});
							}
						});
			}
		}, {
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#dd').dialog('destroy');
			}
		} ],
		onClose : function() {
			$('#dd').dialog('destroy');
		}
	});
	$(".dialog-button").css("border-top-color","#C6E2FF");
	$('#dd').dialog('open');
}
function closeFunction(){
	$('#dd').dialog('destroy');
	search1();
}
function edituser(uuid, name, type, status) {
	edit(uuid, name, type, status);
}
function edit(uuid, loginName, type, status) {
	$(
			'<div id="dd" title="编辑权限" style="width: 550px; height: 410px;">'
					+ '<div>'
					+ '<table style="padding: 5px;">'
					+ '<tr>'
					+ '<td>'
					+ '<form id="updateUserForm" method="post" name="updateUserForm">'
					+ '<div><div></div><div>'
					+ '<div style="float: left;">' + '<table>' + '<tr>'
					+ '<td align=right>用户名：</td>' + '<td>'
					+ '<input id="loginName" name="loginName" value="'
					+ loginName
					+ '" disabled="disabled" style="width: 145px;" />'
					+ '</td>'
					+ '<td>'
					+ '<label style="color: red">*</label>'
					+ '</td>'
					+ '</tr>'
					+ '<tr>'
					+ '<td align=right>用户状态：</td>'
					+ '<td><input id="status" name="status" value="'
					+ status
					+ '" /></td>'
					+ '<td><label style="color: red">*</label></td>'
					+ '</tr><tr>'
					+ '<td align=right>用户类型：</td>'
					+ '<td><input id="type" name="type" type="hidden" value="'+ type +'" style="width: 145px;"/>'
					+'<input id="types" name="types" disabled="disabled" style="width: 145px;"/></td>'
					+ '<td><label style="color: red">*</label></td>'
					+ '</tr>'
					+ '<tr>'
					+ '<td valign="top" align=right>角色：<br>'
					+ '<label style="color: red"><按Ctrl多选></label></td>'
					+ '<td><select multiple="multiple" id="roleUpdate" onblur="onblurRoleChange(this)" style="width: 150px; height: 220px;"></select></td>'
					+ '</tr>'
					+ '</table>'
					+ '</div>'
					+ '<div style="float: left; height: 310px; overflow: auto;width: 245px;">'
					+ '<table>'
					+ '<tr><td valign="top" align=right>可用权限：</td><td><ul id="tree" class="ztree"></ul></td></tr>'
					+ '</table>'
					+ '</div>'
					+ '</div>'
					+ '</div>'
					+ '</form>'
					+ '</td></tr>' + '</table>').appendTo($("#dialogDiv"));
	$('#status').combobox({
		valueField : 'key',
		textField : 'value',
		multiple : false,
		editable : false,
		panelHeight : "auto",
		width : 150,
		listHeight : 50
	});
	$.getJSON(getRootPath() + '/common/userState/all', function(result) {
		resultHandler(result, function process(data) {
			$('#status').combobox('loadData', data);
		});
	});
	if (type != "10") {
		$("#types").val("内部员工");
	} else {
		$("#types").val("CP用户");
	}
	$.getJSON(getRootPath() + '/manager/user/menus', {
		"random" : Math.random()
	}, function(result) {
		resultHandler(result, function process(data) {

			tree(data);
		});
	});
	$.getJSON(getRootPath() + '/manager/get/roleAll',
	{
		"random" : Math.random()
	},function(result){
		resultHandler(result,function process(data) {
		// 先加载已拥有权限
			for ( var i = 0; i < data.length; i++)
			{
				$("#roleUpdate").append("<option value='"+data[i].uuid+"'>"+data[i].name +"</option>");
			}
		// 权限赋到树上面去
		$.getJSON(getRootPath()+ '/manager/user/roles',{"random" : Math.random(),userUuid : uuid
		},function(result) {resultHandler(result,function process(data) {if (data != null&& data != '') {
				var roleUuid = [];
				var count = $("#roleUpdate option").length;
				for ( var i = 0; i < count; i++) {

					for ( var j = 0; j < data.length; j++) {
						if ($("#roleUpdate").get(0).options[i].value == data[j].uuid) {
							$("#roleUpdate").get(0).options[i].selected = true;
							roleUuid.push($("#roleUpdate").get(0).options[i].value);
						}
					}
				}
				onRoleChange(roleUuid);
			}
			});
			});
		});
	});
	$("#roleUpdate").change(function()
			{
				var d = $("#roleUpdate").val();
				onRoleChange(d, "");
			});
	$('#dd').dialog({
		modal : true,
		buttons : [ {
			text : '提交',
			iconCls : 'icon-ok',
			handler : function() {
				var roleValue = $("#roleUpdate").val();
				if(roleValue!=null){
					var r = '';
					for ( var i = 0; i < roleValue.length; i++)
					{
						if (r != '')
							r += ',';
						r += roleValue[i].uuid;
					}
				}
				$('#updateUserForm').ajaxSubmit(
						{
							dataType:'json',
							url : getRootPath() + "/manager/useredit/submit?uuid="
									+ uuid + "&loginName="+loginName+ "&roleUuid=" + roleValue+"&random="+ Math.random(),
							beforeSubmit:function(){
								if($('#updateUserForm').form("validate")){
									if ($("#roleUpdate").val()==""||$("#roleUpdate").val()==null)
									{
										$.messager.alert('提示', '请选择角色','warning');
										return false;
									}
									return true;
								}else{
									
									return false;
								}
							},		
							success : function(result)
							{
								resultHandler(result, function(data)
								{
									if (data == '修改成功')
									{
										$.messager.alert('提示', '编辑成功', 'info', function()
										{
											closeFunction();
										});
										$("a.panel-tool-close").attr("onClick","closeFunction()");
									}
									else
									{
										$.messager.alert('提示', '编辑失败，请重新操作！');
									}
								});
							}
						});
			}
		}, {
			text : '关闭',
			iconCls : 'icon-cancel',
			handler : function() {
				$('#dd').dialog('destroy');
			}
		} ],
		onClose : function() {
			$('#dd').dialog('destroy');
		}
	});
	$(".dialog-button").css("border-top-color","#C6E2FF");
	$('#dd').dialog('open');
}
function onRoleChange(newValue, oldValue)
{
	var zTree = $.fn.zTree.getZTreeObj("tree");
	nodes = zTree.getNodes();
	for ( var i = 0, l = nodes.length; i < l; i++)
	{
		zTree.setChkDisabled(nodes[i], false);
		zTree.checkNode(nodes[i], false, true);
	}
	if(newValue==null){
		return
	}
	$.getJSON(getRootPath() + '/manager/user/permiKeys',
	{
		"random" : Math.random(),
		roleUuids : newValue.join(",")

	}, function(result)
	{
		resultHandler(result, function process(data)
		{
			for ( var i = 0; i < newValue.length; i++)
			{
				for ( var j = 0; j < data.length; j++)
				{
					if (newValue[i] == data[j].roleUuid)
					{
						var node = zTree.getNodeByParam("id",
								data[j].permiUuid, null);
						zTree.checkNode(node, true, false);
					}
				}
			}
			for ( var i = 0, l = nodes.length; i < l; i++)
			{
				zTree.setChkDisabled(nodes[i], true);
			}
		});
	});
}
function tree(data)
{
	var menus = data[0].children;
	for ( var i = 0; i < menus.length; i++)
	{
		menus[i].open = true;
	}
	var setting =
	{
		check :
		{
			enable : true
		},
		data :
		{
			key :
			{
				name : "text"
			},
			simpleData :
			{
				enable : true,
				idKey : "id",
				pIdKey : "pid",
				rootPid : null
			}
		},
		view :
		{
			showIcon : false,
			dblClickExpand : false,
			showLine : false
		}
	};
	$.fn.zTree.init($("#tree"), setting, menus);
	var zTree = $.fn.zTree.getZTreeObj("tree");
	nodes = zTree.getNodes();
	for ( var i = 0, l = nodes.length; i < l; i++)
	{
		zTree.setChkDisabled(nodes[i], true);
	}
}
function onblurRoleChange(opt){
	onRoleChange($("#"+opt.id+"").val(),"");
}