$(function()
{
	$('#addRole').linkbutton(
			{
				text : "添加角色",
				iconCls : "icon-add"
			});
	$('#searchRole').linkbutton(
			{
				text : "查询",
				iconCls : "icon-search"
			});
	$('#chongzhiRole').linkbutton(
			{
				text : "重置",
				iconCls : "icon-reset"
			});
	$("#name").autocomplete(
	{
		minLength : 1,
		source : function(request, response)
		{
			var term = $.trim(arguments[0].term);
			if (term != "")
			{
				$.getJSON(getRootPath() + '/manager/role/query',
				{
					"name" : term,
					"random" : Math.random()
				}, function(result)
				{
					response($.map(result, function(item)
					{
						return item.name;
					}));
				});
			}
		}
	});

	$('#roleTable').datagrid(
			{
				remoteSort : true,
				height : /*$('#roleTable').parent().height() - 75*/"auto",
				idField : 'uuid',
				sortName : 'uuid',
				sortOrder : 'desc',
				pagination : true,
				pageNumber : 1,
				rownumbers : true,
				pageList :
				[
						15, 20, 30
				],
				nowrap : true,
				singleSelect : true,
				striped : true,
				// 列设置
				columns :
				[
					[
							{
								field : 'uuid',
								title : 'uuid',
								hidden : true
							},
							{
								field : 'name',
								title : '角色名称',
								width : getWidth(0.3),
								sortable : true,
								resizable : true,
								align : 'left'
							},

							{
								field : 'createdTime',
								title : '创建日期',
								width : getWidth(0.3),
								sortable : true,
								resizable : true,
								align : 'center',
								formatter : function(value)
								{
									return dateOf(value);
								}
							},

							{
								field : 'opt',
								title : '操作',
								width : getWidth(0.35),
								sortable : false,
								resizable : true,
								align : 'center',
								formatter : function(value, item, index)
								{
									if (item.uuid == "child_sys_admin_role")
									{
										return '';
									}
									var edit = "<a href='#' onclick='update("+'"'+ item.id+'"'+','+'"'+ item.name+'"'+")'>编辑</a> ";
									return edit;
								}
							}
					]
				],
				onLoadSuccess : function(row, _cc)
				{
					loadpnt();
				},
				onSortColumn : function(sort, order)
				{
					if ($("#isdirty").val() == "false")
					{
						searchdata();
					}
					else
					{
						getdata();
					}
				}
			});
	// 获取初始化页面的显示数据
	getdata();

});

function getWidth(percent)
{
	return $('#roleTable').parent().width() * percent;
}
function add()
{
	add();
}
function chongzhi()
{
	$('#name').val("");
}
function getdata()
{
	$.ajax({
		type : "post",
		dataType : 'json',
		url : getRootPath() + '/manager/role/search',
		data : {
			name : $("#name").val(),
			pageSize : $('#roleTable').datagrid('options').pageSize,
			pageNumber : $('#roleTable').datagrid('options').pageNumber,
			sort : $('#roleTable').datagrid('options').sortName,
			order: $('#roleTable').datagrid('options').sortOrder,
			"random" : Math.random()
		},
		success : function(result) {
			resultHandler(result, function process(data) {
				$('#roleTable').datagrid('loadData', data);
			});
		}
	});
}
function loadpnt()
{
	// 分页查看
	var pager = $('#roleTable').datagrid('getPager');
	pager.pagination(
	{
		showPageList : true,
		showRefresh : false,
		onSelectPage : function(pageNumber, pageSize)
		{
			if ($("#isdirty").val() == "false")
			{
				searchdata();
			}
			else
			{
				getdata();
			}
		}
	});
}
function searchdata()
{
	$('#roleTable').datagrid(
	{
		pageNumber : 1,
		onLoadSuccess : function(row, _cc)
		{
			loadpnt();
		}
	});
	$("#isdirty").val("true");
	getdata();
}
function aa()
{
	$("#isdirty").val("false");
}
function add() {
	$('<div id="dd" title="添加角色" style="width: 260px; height: 450px;">'
					+ '<form id="addRoleForm" method="post" name="addRoleForm">'
					+ '<table style="padding: 5px;">'
					+ '<tr>'
					+ '<td>角色名称:'
					+'<input type="text" maxlength="10" id="nameAdd" onkeydown="if(event.keyCode==13) return false;" />'
					+'&nbsp;'
					+'<font color="#FF0000">*</font>'
					+ '</td>'
					+ '</tr>'
					+ '<tr>'
					+ '<td align="left">权限:</td>'
					+ '</tr>'
					+'<tr>'
					+ '<td align="left"><ul id="tt2"></ul></td>'
					+ '</tr>'
					+ '</table>'
					+ '</form>'
					+ '</div>').appendTo($("#dialogDiv"));
	$('#nameAdd').validatebox({
		required : true,
		validType : "name"
	});
	$.getJSON(getRootPath() + '/manager/role/menus', function(result) {
		resultHandler(result, function process(data) {
			$('#tt2').tree('loadData', data);
		});
	});
	$('#tt2').tree({
		checkbox : true,
		onClick : function(node) {
			$(this).tree('toggle', node.target);
		},
		onBeforeExpand : function(node) {
			return false;
		},
		onBeforeCollapse : function(node) {
			return false;
		},
		onContextMenu : function(e, node) {
			e.preventDefault();
			$('#tt2').tree('select', node.target);
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}
	});
	$('#dd').dialog({
		modal : true,
		buttons : [ {
			text : '提交',
			iconCls : 'icon-ok',
			handler : function() {
				if ($('#addRoleForm').form("validate")) {
					var nodes = $('#tt2').tree('getChecked');
					var s = '';
					for ( var i = 0; i < nodes.length; i++) {
						if (s != '')
							s += ',';
						s += nodes[i].attributes.key;
					}
					if (s == '') {
						$.messager.alert('提示', '权限不可为空');
						return false;
					}
					$.ajax({
						type : "post",
						dataType : 'json',
						url : getRootPath() + '/manager/roleadd/submit',
						data : {
							name : $("#nameAdd").val(),
							ss : s,
							"random" : Math.random()
						},
						success : function(result) {
							resultHandler(result, function process(data) {
								if (data == '添加角色成功'){
									$.messager.alert('提示', data, 'info', function() {
										 {
											closeFunction();
										}
									});
									$("a.panel-tool-close").attr("onClick","closeFunction()");
								}
							});
						}
					});
				}
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
function update(uuid,name) {
	$('<div id="dd" title="编辑角色" style="width: 260px; height: 450px;">'
					+ '<form id="updateRoleForm" method="post" name="updateRoleForm">'
					+ '<table style="padding: 5px;">'
					+ '<tr>'
					+ '<td>角色名称:'
					+'<input type="text" maxlength="10" id="nameUpdate" value="'+ name +'" onkeydown="if(event.keyCode==13) return false;" />'
					+'&nbsp;'
					+'<font color="#FF0000">*</font>'
					+ '</td>'
					+ '</tr>'
					+ '<tr>'
					+ '<td align="left">权限:</td>'
					+ '</tr>'
					+'<tr>'
					+ '<td align="left"><ul id="tt2"></ul></td>'
					+ '</tr>'
					+ '</table>'
					+ '</form>'
					+ '</div>').appendTo($("#dialogDiv"));
	$('#nameUpdate').validatebox({
		required : true,
		validType : "name"
	});
	$.getJSON(getRootPath() + '/manager/role/menus', function(result) {
		resultHandler(result, function process(data) {
			// 加载树控件数据
			$('#tt2').tree('loadData', data);

			// 标记该角色拥有的权限
			$.post(getRootPath() + '/manager/role/permiKeys', {
				roleUuid : uuid
			}, function(result) {
				resultHandler(result, function process(data) {
					// 获得根节点
					var root = $('#tt2').tree('getRoot');
					mark(root, data);
				});
			});
		});
	});
	$("#tt2").tree({
		checkbox : true,
		onBeforeExpand : function(node) {
			return false;
		},
		onBeforeCollapse : function(node) {
			return false;
		}
	});
	$('#tt2').tree({
		checkbox : true,
		onClick : function(node) {
			$(this).tree('toggle', node.target);
		},
		onBeforeExpand : function(node) {
			return false;
		},
		onBeforeCollapse : function(node) {
			return false;
		},
		onContextMenu : function(e, node) {
			e.preventDefault();
			$('#tt2').tree('select', node.target);
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}
	});
	$('#dd').dialog({
		modal : true,
		buttons : [ {
			text : '提交',
			iconCls : 'icon-ok',
			handler : function() {
				if ($('#updateRoleForm').form("validate")) {
					var nodes = $('#tt2').tree('getChecked');
					var s = '';
					for ( var i = 0; i < nodes.length; i++) {
						if (s != '')
							s += ',';
						s += nodes[i].attributes.key;
					}
					if (s == '') {
						$.messager.alert('提示', '权限不可为空');
						return false;
					}
					$.ajax({
						type : "post",
						dataType : 'json',
						url : getRootPath() + '/manager/roleedit/submit',
						data : {
							uuid : uuid,
							name : $("#nameUpdate").val(),
							fdsa : s,
							"random" : Math.random()
						},
						success : function(result) {
							resultHandler(result, function process(data) {
								if (data == '更新成功'){
									$.messager.alert('提示', data, 'info', function() {
										 {
											closeFunction();
										}
									});
									$("a.panel-tool-close").attr("onClick","closeFunction()");
								}
							});
						}
					});
				}
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
	getdata();
}
function mark(treeNode, keys) {
	var children = $('#tt2').tree('getChildren', treeNode.target);
	// 有子节点
	if (children) {
		for ( var i = 0; i < children.length; i++) {
			if (contains(keys, children[i].attributes.key)) {
				$("#tt2").tree('check', children[i].target);
			}
		}
	}
}
function contains(array, ele) {
	if (array) {
		for ( var j = 0; j < array.length; j++) {
			if (array[j] == ele) {
				return true;
			}
		}
		return false;
	}
	return false;
}
$.extend($.fn.validatebox.defaults.rules,{
	name : {
		validator : function(value, param) {
			var patrn = /^[\u4E00-\u9FA5a-zA-Z]{1}([\u4E00-\u9FA5a-zA-Z0-9._]){0,9}$/;
			return patrn.test(value);
		},
		message : '角色名只能输入1-10个以字母开头、可带数字、“_“、“.“的字符串'
	}
});