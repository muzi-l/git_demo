$(function() {
	$('#answererPanel').panel({width : 300, height : 350, noheader : true});
	$('#answerPanel').panel({width : 500, height : 350, noheader : true});
	$('#answerCasePanel').panel({width : 400, height : 350, noheader : true});
//	$('#chatRoom').panel({width : 1208, height : 250, noheader : true});
	$('#answerCountdown').progressbar({width:392, text:'0秒'});
	$('#answerA_case').progressbar({width:392, text:''});
	$('#answerB_case').progressbar({width:392, text:''});
	$('#answerC_case').progressbar({width:392, text:''});
	$('#answerD_case').progressbar({width:392, text:''});
	$('#selectBtn').click(seleteAnswerer);
	$('#pushQuestion').click(pushQuestion);
	$('#answer_A').click(function(){selectAnswer('answer_A');});
	$('#answer_B').click(function(){selectAnswer('answer_B');});
	$('#answer_C').click(function(){selectAnswer('answer_C');});
	$('#answer_D').click(function(){selectAnswer('answer_D');});
	$('#caseRefresh').click(refreshAnswerCase);
//	$('#chats').scroll(function(event)
//	{
//		if (event.target.scrollTop == event.target.scrollTopMax)
//		{
//			$('#chats').data('isAutoScroll', true);
//		}
//		else
//		{
//			$('#chats').data('isAutoScroll', false);
//		}
//	});
	// 获取当前节目
	$.getJSON(getRootPath() + '/host/currProgram',
			{
				"random" : Math.random()
			}, function(result)
			{
				resultHandler(result, function process(data)
				{
					if(data){
						initProgram(data);
					}else{
						$.messager.alert("提示", "当前节目不存在！", "warning", function(){
							
						});
					}
				});
			});
});
// 初始化当前节目
function initProgram(data){
	$('#progamePanel').data('program', data);
	$("#answerer").empty();
	var answer_1 = null, answer_2 = null, answer_3 = null;
	if(data.answer_1_Uuid && data.answer_1_Name){
		answer_1 = {
				uuid : data.answer_1_Uuid,
				userName : data.answer_1_Name
		};
	}
	if(data.answer_2_Uuid && data.answer_2_Name){
		answer_2 = {
				uuid : data.answer_2_Uuid,
				userName : data.answer_2_Name
		};
	}
	if(data.answer_3_Uuid && data.answer_3_Name){
		answer_3 = {
				uuid : data.answer_3_Uuid,
				userName : data.answer_3_Name
		};
	}
	// 答题者不能为空
	if(answer_1 && answer_2 && answer_3){
		//  答题者不能重复
		if(answer_1.uuid == answer_2.uuid || answer_1.uuid == answer_3.uuid || answer_3.uuid == answer_2.uuid){
			$.messager.alert("错误", "答题者重复！", "error", function(){});
			return;
		}
		// 设置答题者
		$("#answerer").append('<div class="answerer-item" id="'+answer_1.uuid+'" value="'+answer_1.uuid+'">'+answer_1.userName+'</div>');
		$("#"+answer_1.uuid).data('answer', answer_1);
		addEventForAnswererItem(answer_1.uuid);
		$("#answerer").append('<div class="answerer-item" id="'+answer_2.uuid+'" value="'+answer_2.uuid+'">'+answer_2.userName+'</div>');
		$("#"+answer_2.uuid).data('answer', answer_2);
		addEventForAnswererItem(answer_2.uuid);
		$("#answerer").append('<div class="answerer-item" id="'+answer_3.uuid+'" value="'+answer_3.uuid+'">'+answer_3.userName+'</div>');
		$("#"+answer_3.uuid).data('answer', answer_3);
		addEventForAnswererItem(answer_3.uuid);
		addClickEventForAnswererItem([answer_1.uuid, answer_2.uuid, answer_3.uuid]);
		// 赋值
		$('#programName').text(data.programName);
		$('#programPeriods').text(data.programPeriods);
		$('#compereName').text(data.compereName);
//		$('#answerTime').text(data.answerTime + '（秒）');
		$('#completeNo').text(0);
//		$('#currRespondents').data('currCount', {
//			currBarrier : 0,
//			barrierQuestion : 3,
//			currQuestion : 0
//		});
		$('#selectBtn').removeAttr('disabled');
//		var listenMark = Math.random();
//		$("#chats").data('listenMark', listenMark);
		//获取聊天记录
//		listen('', '', listenMark);
	}else{
		$.messager.alert("错误", "缺少答题者！", "error", function(){});
	}
}
// 为答题者增加条纹的事件
function addEventForAnswererItem(id){
	$("#"+id).mouseover(function(){$("#"+id).addClass("answerer-item-hover");});
	$("#"+id).mouseout(function(){$("#"+id).removeClass("answerer-item-hover");});
}
// 增加答题者点击条纹类
function addClickEventForAnswererItem(ids){
	$.each(ids, function(i, id){
		$("#"+id).click(function(){
			$.each(ids, function(j, id2){
				if(id == id2){
					$("#"+id2).addClass("answerer-item-selected");
				}else{
					$("#"+id2).removeClass("answerer-item-selected");
				}
			});
		});
	});
}
// 选择答题者
function seleteAnswerer(){
	var answer = null;
	// 获取用户选择的答题者
	var option = $("#answerer > .answerer-item-selected");
	if(option.length > 0){
		answer = $("#"+option[0].id).data('answer');
	}
	if(answer != null){
		answerReady(answer);
	}else{
		$.messager.alert("提示", "请选择一个答题者！", "warning");
	}
}
// 答题准备
function answerReady(answer){
	nextQuestion();
	$('#currRespondents').text(answer.userName);
	var program = $('#progamePanel').data('program');
	var bonus = program['pass_1_bonus'];
	$('#currBonus').text(bonus + '（元）');
	$('#currRespondents').data('currAnswererCount', {
		currAnswerer : answer,
		currQuestion : 0,
		barrierCount : 3,
		barrierQuestionCount : 3
	});
	$("#"+answer.uuid).remove();
	$('#selectBtn').attr('disabled', 'disabled');
}
// 下一道题
function nextQuestion(collback){
	var program = $('#progamePanel').data('program');
	$.getJSON(getRootPath() + '/host/nextq', {
		programUuid : program.uuid,
		random : Math.random()
	}, function(result)
			{
				resultHandler(result, function process(data)
				{
					if(data){
						$('#answerPanel').data('question', data);
						$('#currQuestionTitle').text(data.questionTitle);
						$('#currQuestionContent').text(data.questionContent);
						$('#answer_A').text('A：' + data.answer_A);
						$('#answer_B').text('B：' + data.answer_B);
						$('#answer_C').text('C：' + data.answer_C);
						$('#answer_D').text('D：' + data.answer_D);
						$('#answerA').text(data.answer_A);
						$('#answerB').text(data.answer_B);
						$('#answerC').text(data.answer_C);
						$('#answerD').text(data.answer_D);
						$('#answerA_case').progressbar({text:'', value:0});
						$('#answerB_case').progressbar({text:'', value:0});
						$('#answerC_case').progressbar({text:'', value:0});
						$('#answerD_case').progressbar({text:'', value:0});
						// $('#currBonus').text(data.questionBonus + '（元）');
						$('#completeNo').text(parseInt(data.sortNo) - 1);
						$('#pushQuestion').removeAttr('disabled');
					}else{
						if(collback){collback();}else{
							$.messager.alert("错误", "获取当前节目题目失败！", "error", function(){});
						}
					}
				});
			});
}
// 推送题目
function pushQuestion(){
//	var program = $('#progamePanel').data('program');
	var question = $('#answerPanel').data('question');
	var currAnswererCount = $('#currRespondents').data('currAnswererCount');
	var answerer = currAnswererCount.currAnswerer;
	$.getJSON(getRootPath() + '/host/pushq', {
		programUuid : question.programUuid,
		questionUuid : question.uuid,
		userUuid : answerer.uuid
	}, function(result)
		{
			resultHandler(result, function process(data)
			{
				if(data){
					$('#pushQuestion').attr('disabled', 'disabled');
					$('#caseRefresh').removeAttr('disabled');
					startCountdown();
//					$('#answer_A').removeAttr('disabled');
//					$('#answer_B').removeAttr('disabled');
//					$('#answer_C').removeAttr('disabled');
//					$('#answer_D').removeAttr('disabled');
				}else{
					$.messager.alert("错误", "出题失败！", "error", function(){});
				}
			});
		});
}
function startCountdown(){
	var program = $('#progamePanel').data('program');
	var time = program.answerTime + 4;
	$('#answerCountdown').data('time', {
		total : time,
		curr : time
	});
	var countdownMark = Math.random();
	$('#answerCountdown').data('countdownMark', countdownMark);
	var countdown = setInterval('countdownHandler('+countdownMark+')', 1000);
	$('#answerCountdown').data('countdown', countdown);
}
function countdownHandler(countdownMark){
	if(countdownMark == $('#answerCountdown').data('countdownMark')){
		var time = $('#answerCountdown').data('time');
		var percentage = (time.curr*100) / (time.total*100) * 100;
		$('#answerCountdown').progressbar({width:392, text:time.curr+'秒', value : percentage});
		if(time.curr == 0){
			stopCountdown();
		}
		time.curr--;
	}
}
// 关闭定时器，刷新答题情况
function stopCountdown(){
	var countdown = $('#answerCountdown').data('countdown');
	if(countdown){
		window.clearInterval(countdown);
//		$('#caseRefresh').attr('disabled', 'disabled');
		refreshAnswerCase(function(data){
			getCurrAnswer(data, function(correctAnswer){
				// 设置正确答案 打开按钮
				recodeAnswerCase(data, correctAnswer, function(){
					$('#' + correctAnswer).css('color', 'red');
					$('#answer_A').removeAttr('disabled');
					$('#answer_B').removeAttr('disabled');
					$('#answer_C').removeAttr('disabled');
					$('#answer_D').removeAttr('disabled');
				});
			});
		});
	}
}
//提交答案
function recodeAnswerCase(data, correctAnswer, callback){
	$.getJSON(getRootPath() + '/host/case/answer/record',
	{
		programUuid : data.programUuid,
		questionUuid : data.questionUuid,
		correctAnswer : correctAnswer,
		answer_A_count : data.answer_A_case,
		answer_B_count : data.answer_B_case,
		answer_C_count : data.answer_C_case,
		answer_D_count : data.answer_D_case
	}, function(result)
	{
		resultHandler(result, function process(data)
		{
			if (data == true)
			{
				if (callback)
				{
					callback();
				}
			}else{
				$.messager.alert("错误", "提交正确答案失败！", "error", function(){});
			}
		});
	});
}
function getCurrAnswer(data, callback){
	var currAnswer = 'answer_A', currCase = data['answer_A_case'];
	var arra = [['A', currAnswer, currCase]];
	$.each(['B','C','D'], function(i, d){
		if(data['answer_'+d+'_case'] > currCase){
			currCase = data['answer_'+d+'_case'];
			currAnswer = 'answer_' + d;
			arra = [[d, currAnswer, currCase]];
		}else if(data['answer_'+d+'_case'] == currCase){
			arra.push([d, 'answer_' + d, data['answer_'+d+'_case']]);
		}
	});
	if(arra.length > 1 && arra[0][2] >= currCase){
		var win = $('<div id="selectAnswer"></div>');
		win.appendTo("body");
		var content = '请选择一个正确答案？'+'<br/>';
		$.each(arra, function(i, d){
			content += '<input id="option_'+d[1]+'" name="answer_option" value="'+d[1]+'" type="radio" />'+d[0]+'：'+d[2]+'人<br/>';
		});
		win.dialog({   
			title:'提示',
			width:300,
			height:180,
			collapsible:false,
			minimizable:false,
			maximizable:false,
			draggable:false,
			closable:false,
			resizable:false,
			content : content,
			inline:true,
			modal:true,
			buttons : [{
	   			id : 'selectAnswerSubmit',
	   			text : '确定',
	   			iconCls : 'icon-ok',
	   			handler:function(){
	   				var allAnswer = $('input:checked');
	   				if(allAnswer.length < 1){
	   					$.messager.alert("提示", "请选择一个正确的答案！", "info", function(){});
	   					return;
	   				}
	   				currAnswer = allAnswer[0].value;
	   				var question = $('#answerPanel').data('question');
	   				question.correctAnswer = currAnswer;
	   				$('#answerPanel').data('question', question);
	   				callback(currAnswer);
	   				win.dialog('destroy');
	               }
	   		}]
//	   		onClose:function(){
//	   			$('#selectAnswer').dialog('destroy');
//			}
		}); 
	}else{
		var question = $('#answerPanel').data('question');
		question.correctAnswer = currAnswer;
		$('#answerPanel').data('question', question);
		callback(currAnswer);
	}
//	currAnswer = 'answer_B';
//	var question = $('#answerPanel').data('question');
//	question.correctAnswer = currAnswer;
//	$('#answerPanel').data('question', question);
//	return currAnswer;
}
// 刷新答题情况
function refreshAnswerCase(callback){
	$('#caseRefresh').attr('disabled', 'disabled');
	var question = $('#answerPanel').data('question');
	$.getJSON(getRootPath() + '/host/case/answer',
			{
				programUuid : question.programUuid,
				questionUuid : question.uuid,
			}, function(result)
			{
				resultHandler(result, function process(data)
				{
					if (data)
					{
						// 设置答题情况
						setCaseProgressBar(data);
						if (callback && $.isFunction(callback))
						{
							callback(data);
						}
						else
						{
							$('#caseRefresh').removeAttr('disabled');
						}
					}else{
						$.messager.alert("错误", "刷新失败，请重试！", "error", function(){});
					}
				});
			});
}
function setCaseProgressBar(data){
	var a = parseInt(data.answer_A_case);
	var b = parseInt(data.answer_B_case);
	var c = parseInt(data.answer_C_case);
	var d = parseInt(data.answer_D_case);
	var max = 0;
	$.each([a, b, c, d], function(i, d){
		max = d > max ? d : max;
	});
	if(max && max > 0){
		max = max * 1.3;
		a = (a > 0 ? a / max : 0) * 100;
		b = (b > 0 ? b / max : 0) * 100;
		c = (c > 0 ? c / max : 0) * 100;
		d = (d > 0 ? d / max : 0) * 100;
	}
//	alert(2 / 0);infinity
//	alert(0 / 2);0
//	alert(0 / 0);nan
	$('#answerA_case').progressbar({text:data.answer_A_case+'人', value:a});
	$('#answerB_case').progressbar({text:data.answer_B_case+'人', value:b});
	$('#answerC_case').progressbar({text:data.answer_C_case+'人', value:c});
	$('#answerD_case').progressbar({text:data.answer_D_case+'人', value:d});
}
// 选择答案
function selectAnswer(id){
	var program = $('#progamePanel').data('program');
	var currAnswererCount = $('#currRespondents').data('currAnswererCount');
	var answerer = currAnswererCount.currAnswerer;
	var question = $('#answerPanel').data('question');
	// 回答正确
	if(question && id == question.correctAnswer){
		$.messager.confirm('提示', '回答正确，是否提交答案？', function(flag){
			if(flag){
				$('#' + question.correctAnswer).css('color', '');
				// 当前答题人题号
				currAnswererCount.currQuestion ++;
				$('#currRespondents').data('currAnswererCount', currAnswererCount);
				invalidQuestion(program.uuid, answerer.uuid, question.uuid, id, true, function(){
					$('#answer_A').attr('disabled', 'disabled');
					$('#answer_B').attr('disabled', 'disabled');
					$('#answer_C').attr('disabled', 'disabled');
					$('#answer_D').attr('disabled', 'disabled');
					// 如果是最后一题，则换人
					if(currAnswererCount.currQuestion%(currAnswererCount.barrierCount*currAnswererCount.barrierQuestionCount) == 0){
						var options = $("#answerer > .answerer-item");
						if(options.length == 0){
							$.messager.alert("提示", "本期答题结束！", "info", function(){});
						}else{
							$('#selectBtn').removeAttr('disabled');
							var questionBonus = parseInt($('#currBonus').text());
							reward(program, answerer, question, questionBonus, program.pass_3_bonusAllotPopulation, true, function(){
								$.messager.alert("提示", "当前答题者答题结束，请换人！", "info", function(){});
							});
						}
						$('#currRespondents').text('');
						$('#currBonus').text('');
					}
					// 下一小关
					else if(currAnswererCount.currQuestion%currAnswererCount.barrierQuestionCount == 0){
						var barrier = currAnswererCount.currQuestion/currAnswererCount.barrierQuestionCount;
//						$.messager.confirm("提示", "第"+barrier+"关结束，是否进入第"+(++barrier)+"关？如果不进入，答题者将获得者当前所有奖金！", function(flag){
//							if(flag){
//								// 调整奖金
//								var bonus = parseInt(program['pass_'+barrier+'_bonus']) + parseInt($('#currBonus').text());
//								$('#currBonus').text(bonus + '（元）');
//								nextQuestion();
//							}else{
//								var questionBonus = parseInt($('#currBonus').text());
//								var awardNum = program['pass_'+barrier+'_bonusAllotPopulation'];
//								reward(program, answerer, question, questionBonus, awardNum, true, function(){
//									$('#selectBtn').removeAttr('disabled');
//									$.messager.alert("提示", "当前答题者答题结束，请换人！", "info", function(){});
//								});
//							}
//						});
						var win = $('<div id="selectBarrier"></div>');
						win.appendTo("body");
						var content = "<div style='margin:5px 10px;'><div class=\"messager-icon messager-info\"></div>第"+barrier+"关结束，是否进入第"+(++barrier)+"关？如果不进入，答题者将获得者当前所有奖金！"+'<div style=\"clear:both;\"/></div>';
						win.dialog({   
							title:'提示',
							width:300,
							height:120,
							collapsible:false,
							minimizable:false,
							maximizable:false,
							draggable:false,
							closable:false,
							resizable:false,
							content : content,
							inline:true,
							modal:true,
							buttons : [{
					   			id : 'selectBarrierSubmit',
					   			text : '进入下一关',
					   			iconCls : 'icon-ok',
					   			handler:function(){
					   				// 调整奖金
									var bonus = parseInt(program['pass_'+barrier+'_bonus']) + parseInt($('#currBonus').text());
									$('#currBonus').text(bonus + '（元）');
									nextQuestion();
									win.dialog('destroy');
					   			}
					   		},{
					   			id : 'selectBarrierCancel',
					   			text : '结束答题',
					   			iconCls : 'icon-cancel',
					   			handler:function(){
									var questionBonus = parseInt($('#currBonus').text());
									var awardNum = program['pass_'+barrier+'_bonusAllotPopulation'];
									reward(program, answerer, question, questionBonus, awardNum, true, function(){
										$('#selectBtn').removeAttr('disabled');
										$.messager.alert("提示", "当前答题者答题结束，请换人！", "info", function(){});
									});
					   				win.dialog('destroy');
					               }
					   		}]
//					   		onClose:function(){
//					   			win.dialog('destroy');
//							}
						}); 
					}else{
						nextQuestion();
					}
				});
			}else{
				// 重选
			}
		});
	}else{
		$.messager.confirm('提示', '回答错误，分配奖励给观众？', function(flag){
			if(flag){
				$('#' + question.correctAnswer).css('color', '');
				// 当前答题人题号
				currAnswererCount.currQuestion ++;
				$('#currRespondents').data('currAnswererCount', currAnswererCount);
				invalidQuestion(program.uuid, answerer.uuid, question.uuid, id, false, function(){
					$('#answer_A').attr('disabled', 'disabled');
					$('#answer_B').attr('disabled', 'disabled');
					$('#answer_C').attr('disabled', 'disabled');
					$('#answer_D').attr('disabled', 'disabled');
					// 换人 或 结束
					var questionBonus = parseInt($('#currBonus').text());
					var barrier = currAnswererCount.currQuestion/currAnswererCount.barrierQuestionCount;
					barrier = Math.floor(barrier);
					if(currAnswererCount.currQuestion%currAnswererCount.barrierQuestionCount != 0){
						barrier ++;
					}
					var awardNum = program['pass_'+barrier+'_bonusAllotPopulation'];
					reward(program, answerer, question, questionBonus, awardNum, false, function(){
						$('#currRespondents').text('');
						$('#currBonus').text('');
						$('#selectBtn').removeAttr('disabled');
						var options = $("#answerer > .answerer-item");
						if(options.length == 0){
							$.messager.alert("提示", "本期答题结束！", "info", function(){});
						}else{
							$.messager.alert("提示", "当前答题者答题结束，请换人！", "info", function(){});
						}
					});
				});
			}else{
				// 重选
			}
		});
	}
}
// 提交答案
function invalidQuestion(programUuid, userUuid, questionUuid, answer, correct, callback){
	$.getJSON(getRootPath() + '/host/select',
	{
		programUuid : programUuid,
		userUuid : userUuid,
		questionUuid : questionUuid,
		answer : answer,
		correct : correct
	}, function(result)
	{
		resultHandler(result, function process(data)
		{
			if (data)
			{
				if (callback)
				{
					callback();
				}
			}else{
				$.messager.alert("错误", "提交答案失败！", "error", function(){});
			}
		});
	});
}
// 分钱
function reward(program, answerer, question, questionBonus, awardNum, isAllCorrect, callback){
	$.getJSON(getRootPath() + '/host/reward', {
		programUuid : program.uuid,
		userUuid : answerer.uuid,
		questionUuid : question.uuid,
		questionBonus : questionBonus,
		awardNum : awardNum,
		isAllCorrect : isAllCorrect
	}, function(result)
	{
		resultHandler(result, function process(data)
		{
			if(callback){
				callback();
			}
		});
	});
}
/*function listen(last_modified, etag, listenMark)
{
	if($("#chats") == null || $("#chats").data('listenMark') != listenMark){
		return;
	}
//	var wholePath = window.document.location.href;
//	var url = wholePath.substring(0, wholePath.indexOf('/', 7));
	var url = $('#chatUrl').val();
	$.ajax(
	{
		'beforeSend' : function(xhr)
		{
			xhr.setRequestHeader("If-None-Match", etag);
			xhr.setRequestHeader("If-Modified-Since", last_modified);
		},
//		url : 'http://192.168.0.243:90/chat/subscribe?id=1000',
//		url : url + '/chat/subscribe?id=1000',
		url : url,
		dataType : 'json',
		type : 'GET',
		cache : false,
		success : function(data, textStatus, xhr)
		{
			if($("#chats") != null && $("#chats").data('listenMark') == listenMark){
				
				var chats = $("#chats")[0];
				var isScroll = chats.scrollTopMax == 0;
				$("#chats").append('<div class="info"><div class="desc">'+data.userId+' ['+data.time+']：</div><div class="msg">'+data.msg+'</div></div>');
				isScroll = isScroll && chats.scrollTopMax > 0;
				if($('#chats').data('isAutoScroll') || isScroll){
					chats.scrollTop = chats.scrollTopMax;
				}
				etag = xhr.getResponseHeader('Etag');
				last_modified = xhr.getResponseHeader('Last-Modified');
				listen(last_modified, etag, listenMark);
			}
		},
		error : function(xhr, textStatus, errorThrown)
		{			
			if($("#chats") != null && $("#chats").data('listenMark') == listenMark){
				listen(last_modified, etag, listenMark);
			}
		}
	});
};*/
