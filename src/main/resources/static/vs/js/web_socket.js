var web_socket = null;

// 初始化web_socket
function initWebSocket(roomName, userName) {
	//判断浏览器是否支持web_socket
	if ('WebSocket' in window) {
		var url = "ws://" + document.location.host + pathHead + "mosa/" + roomName + "/" + userName;
		console.log(url)
		web_socket = new WebSocket(url);
		web_socket.onopen = function() {
			console.log("open")
		}
		web_socket.onclose = function() {
			console.log("close")
			window.location.reload();
		}
		web_socket.onerro = function() {
			console.log("erro")
			window.location.reload();
		}
		web_socket.onmessage = function(message) {
			doMessage(message.data);
		}
	} else {
		alert("该换手机了。。。")
	}
}

//关闭连接
function close() {
	if (web_socket != null) {
		web_socket.close()
	}
}

//页面关闭之前
window.onbeforeunload = function() {
	close();
}

//处理服务器消息
function doMessage(messageVO) {
	var vo = JSON.parse(messageVO);
	var data = vo.data;
	if (vo.type == 0) {
		//离开房间
		leaveRoom(data);
	} else if (vo.type == 1) {
		//加入房间
		joinRoom(data);
	} else if (vo.type == 3) {
		//---------------牌局初始化---------------
		//刷新场次信息
		refreshRoundInfo(data);
		//初始化玩家手牌
		refreshHandCards(data.cardMap);
		//显示玩家手牌数量
		refreshHandNum(data.numMap);
		//当前场次设为已开局
		$("#round_is_start").val("1");
		var selfIndex = $("#user_sequence").val();
		if (data.next == selfIndex) {
			//判断强制抽牌
			if (data.mustDraw == 1) {
				alert("无可用卡牌!");
				mustDrawCard();
			}
		}
	} else if (vo.type == 4) {
		//---------------打出卡牌---------------
		//刷新场次信息
		refreshRoundInfo(data);
		var selfIndex = $("#user_sequence").val();
		if (data.index == selfIndex) {
			//初始化玩家手牌
			refreshHandCards(data.cardMap);
		} else {
			//显示其他玩家手牌数量
			refreshHandNum(data.numMap);
		}
		//判断胜负
		if (data.win == 1) {
			alert(data.winIndex + "获胜!");
			//进入下一场次
			$("#round_is_start").val("0");
			$("#card_library").click();
			return;
		}
		//下一位行动位置玩家
		if (data.next == selfIndex) {
			//判断是否为棱镜
			if (data.rule == 2) {
				cardSkillPrismPopUpWindows();
			}
			//判断强制抽牌
			if (data.mustDraw == 1) {
				alert("无可用卡牌!");
				mustDrawCard();
			}
		}
	} else if (vo.type == 5) {
		//---------------抽取卡牌---------------
		var selfIndex = $("#user_sequence").val();
		if (data.index == selfIndex) {
			//初始化玩家手牌
			refreshHandCards(data.cardMap);
			//判断强制抽牌
			if (data.mustDraw == 1) {
				alert("无可用卡牌!");
				mustDrawCard();
			}
		} else {
			//显示其他玩家手牌数量
			refreshHandNum(data.numMap);
		}
		//判断胜负
		if (data.win == 1) {
			alert(data.winIndex + "爆牌结束!");
			//进入下一场次
			$("#round_is_start").val("0");
			$("#card_library").click();
			return;
		}
	} else if (vo.type == 10) {
		//---------------卡牌技能(棱镜)---------------
		//刷新场次信息
		refreshRoundInfo(data);
		var selfIndex = $("#user_sequence").val();
		if (data.next == selfIndex) {
			//判断强制抽牌
			if (data.mustDraw == 1) {
				alert("无可用卡牌!");
				mustDrawCard();
			}
		}
	} else if (vo.type == 20) {
		//---------------对局提示信息---------------
		alert(data);
	}
}
