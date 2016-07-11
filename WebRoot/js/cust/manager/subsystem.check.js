
$(function()
{
	$('#add').linkbutton(
	{
		text : "添加可登录系统",
		iconCls : "icon-add"
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
						height : "auto",
						nowrap : true,
						singleSelect : true,
						striped : true,
						columns :
						[
							[
									{
										field : 'name',
										title : '可登录系统名称',
										width : getWidth(0.30),
										sortable : true,
										resizable : true,
										align : 'center'
									},
									{
										field : 'key',
										title : '可登录系统key',
										width : getWidth(0.30),
										sortable : true,
										resizable : true,
										align : 'center'
									},

									{
										field : 'opt',
										title : '操作',
										width : getWidth(0.30),
										sortable : false,
										resizable : true,
										align : 'center',
										formatter : function(value, item, index)
										{
											var edit = "<a href='#' onclick='opendialog("
													+ '"update"'
													+ ',"'
													+ item.uuid
													+ '"'
													+ ',"'
													+ item.name
													+ '"'
													+ ',"'
													+ item.key
													+ '"'
													+ ")'>编辑</a> ";
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
								getdata();
						}
					});
	// 获取初始数据
	getdata();
});
function opendialog(type,uuid,name,key)
{
	var dialogDivAdd = $('<div id="dlg" style="width:400px;height:280px;padding:10px 20px">'
			+ '<table height="100%" width="100%">'
			+ '<tr><td align="center" width="100%">'
			+ '<form id="form" method="post">'
			+'<input id="uuid" name="uuid" type="hidden"></input>'
			+'<table align="center">'
			+'<tr><td>可登录系统名称:</td><td><input id="name" name="name"></input></td></tr>'
			+'<tr height="20px"></tr>'
			+'<tr><td>可登录系统key:</td><td><input id="key" name="key"></input></td></tr>'
			+'</table>'
			+ '</form>'
			+ '</td></tr>' 
			+ '</table>' 
			+ '</div>');
	var dialogDivUpdate = $('<div id="dlg" style="width:400px;height:280px;padding:10px 20px">'
			+ '<table height="100%" width="100%">'
			+ '<tr><td align="center" width="100%">'
			+ '<form id="form" method="post">'
			+'<input id="uuid" name="uuid" type="hidden"></input>'
			+'<input id="key" name="key" type="hidden"></input>'
			+'<table align="center">'
			+'<tr><td>可登录系统名称:</td><td><input id="name" name="name"></input></td></tr>'
			+'</table>'
			+ '</form>'
			+ '</td></tr>' 
			+ '</table>' 
			+ '</div>');
	if(type=='update'){
		dialogDivUpdate.appendTo($("#dialogDiv"));
		
	}else{
		dialogDivAdd.appendTo($("#dialogDiv"));
	}
	$('#name').validatebox(
			{
				required : true
			});
	$('#key').validatebox(
			{
				required : true
			});
	
	$('#dlg').dialog(
			{
				modal : true,
				buttons :
				[
						{
							text : '确定',
							iconCls : 'icon-ok',
							handler : function()
							{
								$('#form')
								.ajaxSubmit(
										{
											url : getRootPath() + '/manager/subsystem/'+type,
											dataType : 'json',
											beforeSubmit : function(formData, jqForm, options)
											{
												if (!$('#form').form('validate'))
												{
													return false;
												}
												if (!/^([\u4E00-\u9FA5a-zA-Z0-9._]){0,15}$/.test($(
												'#name').val()))
										{
											$.messager.alert('提示',
													'系统名只能输入1~15个可带中文,字母,数字、“_“、“.“的字符串',
													'warning');
											return false;
										}
												if (!/^([a-zA-Z0-9._]){0,9}$/.test($(
												'#key').val()))
										{
											$.messager.alert('提示',
													'系统key只能输入1~10个可带字母、数字、“_“、“.“的字符串',
													'warning');
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
														$('#dlg').dialog('destroy');
															getdata();
													});

												});
											}
										});
							}
						},
						{
							text : '取消',
							iconCls : 'icon-cancel',
							handler : function()
							{
								$('#dlg').dialog('destroy');
							}
						}
				],
				onClose : function()
				{
					$('#dlg').dialog('destroy');
				}
			});
	$('#dlg').dialog('open');
	if(type=='update'){
		$('#dlg').dialog('setTitle', '编辑可登录系统');
		 $("#uuid").val(uuid);
		 $("#name").val(name);
		 $("#key").val(key);
	}else{
		$('#dlg').dialog('setTitle', '添加可登录系统');
	}

}
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
				getdata();
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

	$.getJSON(getRootPath() + '/manager/subsystem/data',
	new PageOrder(), function(result)
	{
		resultHandler(result, function process(data)
		{
			$('#positionTable').datagrid('loadData', data);
		});
	});
}
