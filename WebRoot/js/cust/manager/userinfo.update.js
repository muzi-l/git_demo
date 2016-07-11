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
	$("#loginName").each(function()
	{
		this.disabled = true;
	});

	$('#type').combobox(
	{
		valueField : 'key',
		textField : 'value',
		multiple : false,
		editable : false,
		panelHeight : "auto",
		width : 155,
		listHeight : 50,
		hasDownArrow : false
	});
	$.getJSON(getRootPath() + '/manager/userinfotype/getall', function(result)
	{
		resultHandler(result, function process(data)
		{
			$("#type").combobox('loadData', data);
		});
	});
	$("#type").combobox('disable');
	$
			.getJSON(
					getRootPath() + '/manager/subsystem/getall',
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
										var opt = new Option(data[i].name,
												data[i].uuid);
										document
												.getElementById("subsystemUuid").options
												.add(opt);
									}
									// $('#subsystemUuid').combobox('loadData',
									// data);
									// $('#subsystemUuid').combobox('setValues',
									// $("#subsystemValue").val().split(","));
									var selectvalue = $("#subsystemValue")
											.val().split(",");
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
								});
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
	$.getJSON(getRootPath() + '/common/unifiedstatus/all', function(result)
			{
				resultHandler(result, function process(data)
				{
					$('#status').combobox('loadData', data);
				});
			});
	// $('#subsystemUuid').combobox(
	// {
	// valueField : 'uuid',
	// textField : 'name',
	// editable : false,
	// width:150,
	// multiple : true,
	// required : true
	// });
	// $('#subsystemUuid').combobox("showPanel");
});
function roback()
{
	loadUrl(getRootPath() + "/manager/userinfo/list");
};
function updatebutton()
{
	$('#form')
			.ajaxSubmit(
					{
						url : getRootPath() + '/manager/userinfoedit/submit?random' + Math.random(),
						dataType : 'json',
						beforeSubmit : function(formData, jqForm, options)
						{
							if ($('#userName').val() == null
									|| $('#userName').val() == "")
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
							resultHandler(result, function process(data)
							{
								$.messager.alert('提示', data, 'info', function()
								{
									if (data == '更新成功')
									{
										loadUrl(getRootPath()
												+ '/manager/userinfo/list');
									}
								});
							});
						}
					});
}