$(function()
{
	$('#add').linkbutton(
	{
		text : "添加用户认证",
		iconCls : "icon-add"
	});
	$('#search').linkbutton(
	{
		text : "查询",
		iconCls : "icon-search"
	});
	$('#chongzhi').linkbutton(
	{
		text : "重置",
		iconCls : "icon-reset"
	});
	$("#loginName").autocomplete(
	{
		minLength : 1,
		source : function(request, response)
		{
			var term = $.trim(arguments[0].term);
			if (term != "")
			{
				$.getJSON(getRootPath() + '/manager/userinfo/query',
				{
					"loginName" :term,
					"random" : Math.random()
				}, function(result)
				{
					response($.map(result, function(item)
					{
						return item.loginName;
					}));
				});
			}
		}
	});
	$("#userName").autocomplete(
	{
		minLength : 1,
		source : function(request, response)
		{
			var term = $.trim(arguments[0].term);
			if (term != "")
			{
				$.getJSON(getRootPath() + '/manager/userinfo/query',
				{
					"userName" : term,
					"random" : Math.random()
				}, function(result)
				{
					response($.map(result, function(item)
					{
						return item.userName;
					}));
				});
			}
		}
	});

	$('#positionTable')
			.datagrid(
					{
						remoteSort : true,
						sortName : 'createdTime',
						sortOrder : 'desc',
						pagination : true,
						pageNumber : 1,
						rownumbers : true,
						pageSize : 10,
						pageList :
						[
								15, 20, 30
						],
						idField : 'id',
						height : /*$('#positionTable').parent().height() - 80*/"auto",
						nowrap : true,
						singleSelect : true,
						striped : true,
						columns :
						[
							[
									{
										field : 'type',
										title : 'type',
										hidden : true
									},
									{
										field : 'id',
										title : 'ID',
										width : getWidth(0.03),
										sortable : true,
										align : 'left'
									},
									{
										field : 'loginName',
										title : '登录名',
										width : getWidth(0.09),
										sortable : true,
										resizable : true,
										align : 'center'
									},
									{
										field : 'userName',
										title : '昵称',
										width : getWidth(0.09),
										sortable : true,
										resizable : true,
										align : 'center'
									},
									{
										field : 'email',
										title : '认证邮箱',
										width : getWidth(0.10),
										sortable : true,
										resizable : true,
										align : 'center'
									},
									{
										field : 'typeValue',
										title : '用户类型',
										width : getWidth(0.08),
										sortable : false,
										resizable : true,
										align : 'center'
									},
									{
										field : 'status',
										title : '用户状态',
										width : getWidth(0.07),
										sortable : false,
										resizable : true,
										align : 'center',
										formatter : function(value){
											if(value == 10){
												return "生效";
											}
											return "终止";
										}
									},
									{
										field : 'system',
										title : '可登录系统',
										width : getWidth(0.28),
										sortable : false,
										resizable : true,
										align : 'center',
										formatter : formatOpt
									},
									{
										field : 'createdTime',
										title : '注册时间',
										width : getWidth(0.10),
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
										width : getWidth(0.1),
										sortable : false,
										resizable : true,
										align : 'center',
										formatter : function(value, item, index)
										{
											// var edit = '<a href="' +
											// getRootPath()+
											// '/manager/userinfoedit/view?id='+
											// item.id + '">编辑</a> ';
											var edit = "<a href='#' onclick='edituserinfo("
												+ '"' + item.id + '"' + ',' + '"'
												+ item.loginName + '"' + ',' + '"'
												+ item.userName + '"' + ',' + '"'
												+ item.type + '"'+ ',' + '"'
												+ item.email + '"' + ',' + '"'
												+ item.status + '"' + ")'>编辑</a> ";
											if (item.id == 10000)
											{
												return '';
											}
											var edit1 = "<a href='#' onclick='resetpassword("
													+ '"'
													+ item.id
													+ '"'
													+ ")'>重置密码</a> ";
											if (item.id == 10000)
											{
												return '';
											}
											return edit + edit1;
										}
									}
							]
						],
						// onClickRow : function(rowIndex, rowData)
						// {
						// $('#positionTable').datagrid('unselectRow',
						// rowIndex);
						// },
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
	// 获取初始数据
	getdata();
});

function getWidth(percent)
{
	return $('#positionTable').parent().width() * percent;
}
function loadpnt()
{
	var pager = $('#positionTable').datagrid('getPager');
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
function PageOrder()
{
	this.random = Math.random();
	this.pageSize = $('#positionTable').datagrid('options').pageSize;
	this.pageNumber = $('#positionTable').datagrid('options').pageNumber;
	this.sort = $('#positionTable').datagrid('options').sortName;
	this.order = $('#positionTable').datagrid('options').sortOrder;
}
function getdata()
{

	$.getJSON(getRootPath() + '/manager/userinfo/pagination',
	{
		"pageSize" : $('#positionTable').datagrid('options').pageSize,
		"pageNumber" : $('#positionTable').datagrid('options').pageNumber,
		"sort" : $('#positionTable').datagrid('options').sortName,
		"order" : $('#positionTable').datagrid('options').sortOrder,
		"loginName" : $("#loginName").val(),
		"userName" : $("#userName").val(),
		"random" : Math.random()
	}, function(result)
	{
		resultHandler(result, function process(data)
		{
			$('#positionTable').datagrid('loadData', data);
		});
	});
}
function add()
{
//	loadUrl(getRootPath() + '/manager/userinfoadd/view');
	adduserinfo();
}
function chongzhi()
{
	$("#loginName").val("");
	$("#userName").val("");
	aa();
}
function aa()
{
	$("#isdirty").val("false");
}
function searchdata()
{

	$('#positionTable').datagrid(
	{
		pageNumber : 1,
		onLoadSuccess : function(row, _cc)
		{
			loadpnt();
		}
	});
	$.post(getRootPath() + '/manager/userinfo/search',
	{
		"pageSize" : $('#positionTable').datagrid('options').pageSize,
		"pageNumber" : $('#positionTable').datagrid('options').pageNumber,
		"sort" : $('#positionTable').datagrid('options').sortName,
		"order" : $('#positionTable').datagrid('options').sortOrder,
		"loginName" : $("#loginName").val(),
		"userName" : $("#userName").val(),
		"random" : Math.random()
	}, function(result)
	{
		resultHandler(result, function process(data)
		{
			$("#isdirty").val("true");
			$('#positionTable').datagrid('loadData', data);
		});
	});

}
//function edituserinfo(id)
//{
//	loadUrl(getRootPath() + "/manager/userinfoedit/view?id=" + id + "&random="
//			+ Math.random());
//}
function adduserinfo() {
		$('<div id="dd" title="添加用户认证" style="width: 515px; height: 300px;">'
				+ '<div>'
				+ '<table style="padding: 5px;">'
				+ '<tr>'
				+ '<td>'
				+ '<form id="addForm" method="post" name="addForm">'
				+ '<div style="float: left;">' + '<table>'
				+ '<tr>'
				+ '<td align=right>登录名称：</td>' + '<td>'
				+ '<input id="addloginName" name="addloginName" style="width: 150px;" maxlength="30"/>'
				+ '</td>'
				+ '<td>'
				+ '<label style="color: red">*</label>'
				+ '</td>'
				+ '</tr>'
				+ '<tr>'
				+ '<td align=right>登录密码：</td>'
				+ '<td><input id="password" name="password" type="password" maxlength="16" style="width: 150px;" onpaste="return false"/></td>'
				+ '<td><label style="color: red">*</label></td>'
				+ '</tr>'
				+ '<tr>'
				+ '<td align=right>昵称：</td>'
				+ '<td><input id="adduserName" name="adduserName" style="width: 150px;" maxlength="30"/></td>'
				+ '<td><label style="color: red">*</label></td>'
				+ '</tr>'
				+ '<tr>'
				+ '<td align=right>认证邮箱：</td>'
				+ '<td><input id="email" name="email" style="width: 150px;" maxlength="60"/></td>'
				+ '<td><label style="color: red">*</label></td>'
				+ '</tr>'
				+ '<tr>'
				+ '<td align=right>用户类型：</td>'
				+ '<td><input id="type" name="type"/>'
				+'</td>'
				+ '<td><label style="color: red">*</label></td>'
				+ '</tr>'
				+ '<tr>'
				+ '<td align=right>用户状态：</td>'
				+ '<td><input id="status" name="status" value=""/></td>'
				+ '<td><label style="color: red">*</label></td>'
				+ '</tr>'
				+ '</table>'
				+ '</div>'
				+ '<div style="float: left; height:170px; overflow: auto;width: 245px;">'
				+ '<table>'
				+ '<tr><td valign=top align=left>可登录系统：<br>'
				+ '<label style="color: red"><按Ctrl多选></label></td>'
				+ '<td align=right><select multiple="multiple" id="subsystemUuid" name="subsystemUuid" style="width: 160px; height: 155px;"></select>'
				+ '<input type="hidden" id="subsystemValue"></td>'
				+ '</tr>'
				+ '</table>'
				+ '</div>'
				+ '</div>'
				+ '</div>'
				+ '</form>'
				+ '</td></tr>' + '</table>').appendTo($("#dialogDiv"));
		$('#type').combobox(
				{
					valueField : 'key',
					textField : 'value',
					multiple : false,
					editable : false,
					required : true,
					panelHeight : "auto",
					width : 155,
					listHeight : 50
				});
		$('#status').combobox(
		{
			valueField : 'key',
			textField : 'value',
			multiple : false,
			editable : false,
			required : true,
			panelHeight : "auto",
			width : 155,
			listHeight : 50
		});
		$.getJSON(getRootPath() + '/manager/userinfotype/getall', function(result)
		{
			resultHandler(result, function process(data)
			{
				$("#type").combobox('loadData', data);
			});
		});
		$.getJSON(getRootPath() + '/common/unifiedstatus/all', function(result)
		{
			resultHandler(result, function process(data)
			{
				$("#status").combobox('loadData', data);
			});
		});
		$.getJSON(getRootPath() + '/manager/subsystem/getall',{"random" : Math.random()},  function(result)
				{
					resultHandler(result, function process(data)
					{
						// $('#subsystemUuid').combobox('loadData',data);
						for ( var i = 0; i < data.length; i++)
						{
							var opt = new Option(data[i].name, data[i].uuid);
							document.getElementById("subsystemUuid").options.add(opt);
						}

					});
		});
		$('#addloginName').validatebox(
				{
					required : true
				});
		$('#password').validatebox(
		{
			required : true
		});
		$('#adduserName').validatebox(
		{
			required : true
		});
		$('#email').validatebox(
		{
			required : true,
			validType : 'email'
		});
	$('#dd').dialog({
	modal : true,
	buttons : [ {
		text : '提交',
		iconCls : 'icon-ok',
		handler : function() {
			var adduserName = $("#adduserName").val();
			var addloginName = $("#addloginName").val();
			$('#addForm')
			.ajaxSubmit(
					{
						url : getRootPath() + '/manager/userinfoadd/submit?random'+ Math.random()+"&userName="+ adduserName+"&LoginName="+ addloginName,
						dataType : 'json',
						beforeSubmit : function(formData, jqForm, options)
						{
							if (!$('#addForm').form('validate'))
							{
								return false;
							}
							if ($('#subsystemUuid').val() == null)
							{
								$.messager.alert('提示', "登录系统不可为空", 'warning');
								return false;
							}
							if (!/^[a-zA-Z]{1}([a-zA-Z0-9._]){0,29}$/.test($(
									'#addloginName').val()))
							{
								$.messager.alert('提示',
										'登录名只能输入1~30个以字母开头、可带数字、“_“、“.“的字符串',
										'warning');
								return false;
							}

							if (!/^([\u4E00-\u9FA5a-zA-Z0-9._]){0,30}$/.test($(
									'#adduserName').val()))
							{
								$.messager.alert('提示',
										'昵称只能输入1~30个可带中文,字母,数字、“_“、“.“的字符串',
										'warning');
								return false;
							}

							var ee = $('#email').val();
							if (ee
									.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) == -1)
							{
								$.messager.alert('提示', "邮箱格式不正确", 'warning');
								return false;
							}

							return true;
						},
						success : function(result)
						{
							resultHandler(result, function process(data)
							{
								$.messager.alert('提示', data, 'info', function()
								{
									if (data == '添加成功')
									{
										$('#dd').dialog('destroy');
										searchdata();
									}
								});

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
	$('#dd').dialog('open');	
}
function edituserinfo(uuid,loginName, userName,type,email, status) {
	edit(uuid,loginName, userName,type,email, status);
}
function edit(uuid,loginName,userName,type,email, status) {
	$('<div id="dd" title="编辑用户认证" style="width: 510px; height: 300px;">'
					+ '<div>'
					+ '<table style="padding: 5px;">'
					+ '<tr>'
					+ '<td>'
					+ '<form id="userinfoForm" method="post" name="userinfoForm">'
					+ '<input type=hidden id="id" name="id" style="width: 145px;" value="'
					+ uuid
					+ '" />'
					+ '<div style="float: left;">' + '<table>'
					+ '<tr>'
					+ '<td align=right>登录名称：</td>' + '<td>'
					+ '<input id="updateloginName" name="updateloginName" maxlength="30" value="'
					+ loginName
					+ '" style="width: 145px;" />'
					+ '</td>'
					+ '<td>'
					+ '<label style="color: red">*</label>'
					+ '</td>'
					+ '</tr>'
					+ '<tr>'
					+ '<td align=right>昵称：</td>'
					+ '<td><input id="updateuserName" name="updateuserName" maxlength="30" style="width: 145px;" value="'
					+ userName
					+ '" /></td>'
					+ '<td><label style="color: red">*</label></td>'
					+ '</tr>'
					+ '<tr>'
					+ '<td align=right>认证邮箱：</td>'
					+ '<td><input id="email" name="email" maxlength="60" onpaste="return false" style="width: 145px;ime-mode: disabled" value="'
					+ email
					+ '" /></td>'
					+ '<td><label style="color: red">*</label></td>'
					+ '</tr>'
					+ '<tr>'
					+ '<td align=right>用户类型：</td>'
					+ '<td><input id="type" name="type" type="hidden" value="'+ type +'" style="width: 145px;"/>'
					+'<input id="types" name="types" disabled="disabled" style="width: 145px;"/></td>'
					+ '<td><label style="color: red">*</label></td>'
					+ '</tr>'
					+ '<tr>'
					+ '<td align=right>用户状态：</td>'
					+ '<td><input id="status" name="status" value="'
					+ status
					+ '" /></td>'
					+ '<td><label style="color: red">*</label></td>'
					+ '</tr>'
					+ '</table>'
					+ '</div>'
					+ '<div style="float: left; height: 150px; overflow: auto;width: 245px;">'
					+ '<table>'
					+ '<tr><td valign=top align=left>可登录系统：<br>'
					+ '<label style="color: red"><按Ctrl多选></label></td>'
					+ '<td align=right><select multiple="multiple" id="subsystemUuid" name="subsystemUuid" style="width: 160px; height: 130px;"></select>'
					+ '<input type="hidden" id="subsystemValue"></td>'
					+ '</tr>'
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
	$.getJSON(getRootPath() + '/manager/subsystem/getall',
			{
				"random" : Math.random()
			},
			function(result)
			{
				resultHandler(
						result,
						function process(data)
						{
							for ( var i = 0; i < data.length; i++)
							{
								var opt = new Option(data[i].name,data[i].uuid);
								document.getElementById("subsystemUuid").options.add(opt);
							}
							$.getJSON(getRootPath() + '/manager/userinfoedit/have',{
								"random" : Math.random(),
								id : uuid
							}, function(result)
									{
										resultHandler(result, function process(data)
										{
										if(data!=null&&data!=""){
												$("#subsystemValue").val(data);
												
												var selectvalue = $("#subsystemValue").val().split(",");
												var count = $("#subsystemUuid option").length;
												for ( var i = 0; i < count; i++)
												{
													for ( var j = 0; j < selectvalue.length; j++)
													{
														if ($("#subsystemUuid").get(0).options[i].value == selectvalue[j])
														{
															$("#subsystemUuid").get(0).options[i].selected = true;
														}
													}
												}
											}
										});
									});	
						});
			});
	$('#dd').dialog({
		modal : true,
		buttons : [ {
			text : '提交',
			iconCls : 'icon-ok',
			handler : function() {
				var updateuserName = $("#updateuserName").val();
				var updateloginName = $("#updateloginName").val();
				$('#userinfoForm').ajaxSubmit(
						{
							url : getRootPath() + '/manager/userinfoedit/submit?random' + Math.random()+"&userName="+ updateuserName+"&LoginName="+ updateloginName,
							dataType : 'json',
							beforeSubmit : function(formData, jqForm, options)
							{
								if ($('#updateuserName').val() == null
										|| $('#updateuserName').val() == "")
								{
									$.messager.alert('提示', "昵称不可为空", 'warning');
									return false;
								}
								if ($('#email').val() == null
										|| $('#email').val() == "")
								{
									$.messager.alert('提示', "邮箱不可为空", 'warning');
									return false;
								}
								if ($('#email').val() != null
										|| $('#email').val() != "")
								{
									var ee = $('#email').val();
									if (ee.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) == -1)
									{
										$.messager
										.alert('提示', "邮箱格式不正确", 'warning');
										return false;
									}
								}
								if (!/^([\u4E00-\u9FA5a-zA-Z0-9._]){0,30}$/.test($(
										'#userName').val()))
								{
									$.messager.alert('提示',
											'昵称只能输入1~30个可带中文,字母,数字、“_“、“.“的字符串',
											'warning');
									return false;
								}
								if ($('#subsystemUuid').val() == null)
								{
									$.messager.alert('提示', "登录系统不可为空", 'warning');
									return false;
								}
								return true;
							},
							success : function(result)
							{
								resultHandler(result, function(data)
								{
									if (data == '更新成功')
									{
										$.messager.alert('提示', data, 'info', function()
										{
											$('#dd').dialog('destroy');
											searchdata();
										});
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
	$('#dd').dialog('open');
}
function resetpassword(id)
{

	$.messager.confirm('提示', '确定重置?', function(ok)
	{
		if (ok)
		{
			if (id != "")
			{
				$.getJSON(getRootPath() + "/manager/userinfo/resetpassword",
				{
					"id" : id,
					random : Math.random()
				}, function(result)
				{
					resultHandler(result, function process(data)
					{
						$.messager.alert('提示', data, 'info');
					});
				});

			}
		}
	});
}
function formatOpt(value, rowData, rowIndex) {
	var text = [];
	text.push("<div class='dis' title='\ ");
	text.push(value);
	text.push("\' ");
	text.push(">");
	text.push(value);
	text.push("</div>");
	return text.join('');
}