$(function()
{
	$('#p').panel({
		  title:"密码修改",
		  width:320
	});
	$(".panel-title").attr("align","left");
	$('#update').linkbutton(
			{
				text : "提交",
				iconCls : "icon-ok"
			});
	$('#resets').linkbutton(
			{
				text : "重置",
				iconCls : "icon-reset"
			});
	$('#originalPassword').validatebox(
			{
				required : true
			});
	$('#newPassword').validatebox(
			{
				required : true
			});
	$('#newPasswords').validatebox(
			{
				required : true
			});
});

function resets(){
	$("#originalPassword").val("");
	$("#newPassword").val("");
	$("#newPasswords").val("");
}
function updatePassword()
{
	var loginName =$("#loginName").val();
	$('#passwordForm').ajaxSubmit(
			{
				url : getRootPath() + "/manager/updatepassword/submit?loginName=" + loginName+"&random="+ Math.random(),
				dataType:'json',
				beforeSubmit:function(formData, jqForm, options){
					if($('#passwordForm').form("validate")){					
						var value = $('#newPassword').val();
					    var reg = /^[x00-x7f]+$/;
					    if (! reg.test(value)){
					    	$.messager.alert('提示', "密码格式不正确", 'warning');
					     return false;
					    }
						if ($('#originalPassword').val()==$('#newPassword').val())
						{
							$.messager.alert('提示', '新密码不能与原始密码一样','warning');
							return false;
						}
						if ($('#newPassword').val()!=$('#newPasswords').val())
						{
							$.messager.alert('提示', '两次新密码输入不一致，请确认','warning');
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
						if (data=='success')
						{
							$.messager.alert('提示', '修改成功,请重新登录', 'info', function()
							{
//								loadUrl(getRootPath()+"/js/cust/account/update_password_success.jsp");
//								window.location.assign(getRootPath()+"/login.html"); 
								signout();
							});
						}
						else if(data=='passworderror')
						{
							$.messager.alert('提示', '原始密码错误！');
						}
						else{
							$.messager.alert('提示',"修改失败！");
						}
					});

				}
			});
}
