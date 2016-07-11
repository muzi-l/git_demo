function resultHandler(result, process, callback)
{
	if (result.level == 'OnlyMessage')
	{
		switch (result.message.level)
		{
			case "Info":
				$.messager.alert("Info", result.message.content, "info",
						callback);
				break;

			case "Valid":
				$.messager.show(
				{
					title : 'Warn',
					msg : result.message.content,
					timeout : 0,
					showType : 'show'
				});
				break;

			case "LogicEx":
				$.messager.alert("Warn", result.message.content, "warning",
						callback);
				break;

			case "SysEx":
				window.location.href=getRootPath() +"/error";
//				$.messager.alert("Error", result.message.content, "error",
//						callback);
				break;

			default:
				$.messager.alert("Info", result.message.content, "info",
						callback);
		}
	}
	else if (result.level == 'DataMessage')
	{
		$.messager.alert("Info", result.message.content, "info", callback);
		process(result.data);
	}
	else if (result.level == 'OnlyData')
	{
		process(result.data);
	}
}