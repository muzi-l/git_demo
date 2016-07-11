//var menuId = null;
$(function() {
	$.getJSON(getRootPath() + '/menuData', {
		"random" : Math.random()
	}, function(result) {
		resultHandler(result, function process(data) {
			initMenu(data[0].children);
		});
	});
});

function initMenu(data) {
	$('#menu').tree({
		data : data,
		onClick : function(node) {
			if (node.attributes.url != null) {
				var url = getRootPath() + node.attributes.url;
				var title = node.text;
				// loadUrl(url);
				open1(title, url);
			}
			// window.location.href = url;
		},
		onBeforeExpand : function(node) {
			return false;
		},
		onBeforeCollapse : function(node) {
			return false;
		}

	});
}

function signout() {
	$.getJSON(getRootPath() + '/signout', {
		"random" : Math.random()
	}, function(result) {
		resultHandler(result, function process(data) {
			// 将名为“menuId”的cookie删除
			// $.cookie("menuId",null,{ expires: 0, path: '/' });
			window.location.href = getRootPath() + "/login.html";
		});
	});
}

// 关于系统
function systeminformation() {
	var dialogDivAdd = $('<div id="dlg" style="width:600px;height:330px;padding:10px 20px">'
			+ '<table style="height: 95%; width: 95%;">'
			+ '<tr style="width: 100%; height: 100%">'
			+ '<td >'
			+ '<h1 align="center">'
			+ $('#systemName').val()
			+ '</h1>'
			+ '<h4 align="center">'
			+ '版本:'
			+ $('#systemversion').val()
			+ '</h4>'
			+ '<center><img align="middle" src="./img/title.png"></center>'
			+ '</td>' + '</tr>' + '</table>' + '</div>');
	dialogDivAdd.appendTo($("#dialogDiv"));
	$('#dlg').dialog({
		modal : true,
		buttons : [],
		onClose : function() {
			$('#dlg').dialog('destroy');
		}
	});
	$('#dlg').dialog('setTitle', '关于');
	$('#dlg').dialog('open');
}

// 参数是控件的id
function comboboxNullable(comboBoxId) {
	var panel = $('#' + comboBoxId).combobox('panel');
	panel
			.prepend("<div align='center' style='cursor: pointer;'  onclick='new valueClear()'>--- 清空 ---</div>");

	valueClear = function() {
		$('#' + comboBoxId).combobox('clear');
		$('#' + comboBoxId).combobox('hidePanel');
	};
}

// 获取url参数
(function($) {
	$.getUrlParam = function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	};
})(jQuery);

// js获取项目根路径，如： http://localhost:8080/cnrvoice-fenzhang
function getRootPath() {
	// 获取当前网址，如： http://localhost:8080/cnrvoice-fenzhang/login.html
	var wholePath = window.document.location.href;

	// 获取主机地址之后的目录，如： /cnrvoice-fenzhang/login.html
	var suffixPath = window.document.location.pathname;

	var pos = wholePath.indexOf(suffixPath);
	// 获取主机地址，如： http://localhost:8080
	var hostPath = wholePath.substring(0, pos);

	// 获取带"/"的项目名，如：/cnrvoice-fenzhang
	var projectName = suffixPath.substring(0,
			suffixPath.substr(1).indexOf('/') + 1);

	return (hostPath + projectName);
}

function numFormat(arg) {
	var num = Math.round(arg * 100) / 100;
	if (!isNaN(num)) {
		return num;
	}
}
function loadUrl(url) {
	// $("#center").load(url);
	open1(url);
}

function open1(title, plugin) {
	if ($('#tt').tabs('exists', title)) {
		$('#tt').tabs('select', title);
	} else {
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+plugin+'" style="width:100%;height:100%;"></iframe>';
		$('#tt').tabs('add',{
			title:title,
			content:content,
			closable:true
		});
//		$('#tt').tabs('add', {
//			title : title,
//			href : plugin,
//			cache : false,
//			closable : true
//		});
	}
}
var date = null;
var remark = null;
function setdate(date) {
	this.date = date;
}
function getdate() {
	return date;
}
function setremark(remark) {
	this.remark = remark;
}
function getremark() {
	return remark;
}