$(function() {

	//点击牌库进行初始发牌
	$("#card_library").click(function() {
		if (web_socket != null) {
			var roundIsStart = $("#round_is_start").val();
			if (roundIsStart == 1) {
				//场次已开局,当前玩家抽牌
				var actionBO = {
					type: "5",
					data: {
						index: $("#user_sequence").val()
					}
				};
				web_socket.send(JSON.stringify(actionBO));
			} else if (roundIsStart == 0) {
				//场次未开局,所有玩家发牌
				var actionBO = {
					type: "3"
				};
				web_socket.send(JSON.stringify(actionBO));
			}
		}
	});

	//手牌点击
	$("img[name='handCard']").click(function() {
		var last = $("#div_hand_cards").find("img[will-play='will']");
		var there = $(this);
		var lastId = last.attr("id");
		var thereId = there.attr("id");
		if (lastId != undefined) {
			last.css("margin-top", "1.6%");
			last.attr("will-play", "none");
		}
		if (lastId != thereId) {
			$(this).css("margin-top", "-2%");
			$(this).attr("will-play", "will");
		} else {
			var actionBO = {
				type: "4",
				data: {
					playerIndex: $("#user_sequence").val(),
					handIndex: there.attr("cardIndex")
				}
			}
			web_socket.send(JSON.stringify(actionBO));
		}
	});

});

//刷新场次信息
function refreshRoundInfo(data) {
	var round = data.round;
	var direction = data.direction;
	var properties = data.properties;
	var index = data.next;
	var rule = data.rule;
	var code = data.code;
	//显示当前场上卡牌
	if (code != undefined && code.length == 2) {
		$("#card_out").prop("src", "../img/card/" + code + ".png");
		$("#card_out").show();
	}
	//显示当前场上属性
	if (properties != undefined) {
		$("#round_properties").prop("src", "../img/card/A" + (properties + 1) + ".png");
		$("#round_properties").show();
	}
	//显示当前出牌方向
	if (direction != undefined) {
		$("#vs_direction").prop("src", "../img/direction-" + direction + ".png");
		$("#vs_direction").show();
	}
	//刷新当前出牌位置
	if (index != undefined) {
		$("#index").val(index);
		//显示提示红点
		var selfIndex = $("#user_sequence").val();
		var offset = countOffset(selfIndex, index);
		for (var i = 0; i < 4; i++) {
			if (i != offset) {
				$("#vs_now_player_" + i).hide();
			} else {
				$("#vs_now_player_" + i).show();
			}
		}
	}
	//刷新当前出牌规则
	if (rule != undefined) {
		$("#rule").val(rule);
	}
	//刷新当前场次
	if (round != undefined) {
		$("#round").val(round);
	}
}

//显示手牌
function refreshHandCards(cardMap) {
	//获取自己的位置
	var selfIndex = $("#user_sequence").val();
	var cardList = cardMap[selfIndex];
	var cards = cardList.length;
	//计算卡牌显示偏移值
	var offset = (80 / (cards - 1)) - 12;
	if (offset > 0) {
		offset = 0;
	}
	if (cards > 0) {
		//显示手牌
		for (var i = 0; i < cards; i++) {
			if (i > 0) {
				$("#hand_card_" + i).css("margin-left", offset + "%");
			}
			$("#hand_card_" + i).prop("src", "../img/card/" + cardList[i] + ".png");
			$("#hand_card_" + i).show();
		}
	}
	//隐藏没有手牌的div
	for (var i = cards; i < 15; i++) {
		$("#hand_card_" + i).hide();
	}
	//显示自己的手牌数量
	$("#td_cards_0").text(cards);
}

//显示玩家手牌数量
function refreshHandNum(numMap) {
	var selfIndex = $("#user_sequence").val();
	for (var key in numMap) {
		var index = countOffset(selfIndex, key);
		$("#td_cards_" + index).text(numMap[key]);
	}
}

//强制玩家抽牌
function mustDrawCard() {
	var actionBO = {
		type: "6"
	};
	web_socket.send(JSON.stringify(actionBO));
}

//提示信息弹窗
function messageModalWindows(message) {
	$("#message_modal").text(message);
	$("#modal-936584").click();
}

//棱镜弹窗
function cardSkillPrismPopUpWindows() {
	$("#modal-966556").click();
}

//棱镜执行
function cardSkillPrism(properties) {
	var actionBO = {
		type: "10",
		data: {
			properties: properties
		}
	};
	web_socket.send(JSON.stringify(actionBO));
}
