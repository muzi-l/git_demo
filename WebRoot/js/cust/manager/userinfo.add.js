$(function()
{
	$('#tijiao').linkbutton(
	{
		text : "提交",
		iconCls : "icon-ok"
	});
	$('#roback').linkbutton(
	{
		text : "返回",
		iconCls : "icon-back"
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

	$('#loginName').validatebox(
	{
		required : true
	});
	$('#password').validatebox(
	{
		required : true
	});
	$('#userName').validatebox(
	{
		required : true
	});
	$('#email').validatebox(
	{
		required : true,
		validType : 'email'
	});
	// $('#subsystemUuid').combobox(
	// {
	// valueField : 'uuid',
	// textField : 'name',
	// multiple : true,
	// width:170,
	// editable : false,
	// required : true
	// });
	// $('#subsystemUuid').combobox('showPanel');
	$("#roback").click(function()
	{
		loadUrl(getRootPath() + "/manager/userinfo/list");
	});
});

function addbutton()
{
	$('#form')
			.ajaxSubmit(
					{
						url : getRootPath() + '/manager/userinfoadd/submit?random'+ Math.random(),
						dataType : 'json',
						beforeSubmit : function(formData, jqForm, options)
						{
							if (!$('#form').form('validate'))
							{
								return false;
							}
							if ($('#subsystemUuid').val() == null)
							{
								$.messager.alert('提示', "登录系统不可为空", 'warning');
								return false;
							}
							if (!/^[a-zA-Z]{1}([a-zA-Z0-9._]){0,29}$/.test($(
									'#loginName').val()))
							{
								$.messager.alert('提示',
										'登录名只能输入1~30个以字母开头、可带数字、“_“、“.“的字符串',
										'warning');
								return false;
							}

							if (!/^([\u4E00-\u9FA5a-zA-Z0-9._]){0,30}$/.test($(
									'#userName').val()))
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
										loadUrl(getRootPath()
												+ '/manager/userinfo/list');
									}
								});

							});
						}
					});
}