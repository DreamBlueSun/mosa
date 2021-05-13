$(function() {

	//加入房间
	$("#join_room").click(function() {
		var roomName = $("#room_name").val();
		var userName = $("#user_name").val();
		if (roomName.length = 0 || roomName.length > 3) {
			alert("房间号输入有误");
			return;
		}
		if (userName.length = 0 || userName.length > 8) {
			alert("用户名输入有误");
			return;
		}
		$("#div_vs_joining").hide();
		$("#div_vs_playing").show();
		initWebSocket(roomName, userName);
	});

	//离开房间
	$("#leave_room").click(function() {
		close();
		window.location.reload();
	});

});

function joinRoom(action) {
	var isJoined = action.isJoined;
	//加入成功
	if (isJoined == 1) {
		var selfName = $("#user_name").val();
		var userName = action.userName;
		var thisUserIndex = action.index;
		//判断当前加入的玩家位置
		if (selfName == userName) {
			$("#user_sequence").val(thisUserIndex);
		}
		//刷新所有玩家信息
		showPlayersInfo(action.listPlayer);
	} else {
		alert("加入失败");
	}
}

function leaveRoom(action) {
	//刷新所有玩家信息
	showPlayersInfo(action.listPlayer);
	//清除离开的玩家信息
	clearPlayersInfoByLeave(action.index);
}

//刷新所有玩家信息
function showPlayersInfo(listPlayer) {
	//获取自己的位置
	var selfIndex = $("#user_sequence").val();
	//显示信息
	for (var i = 0; i < listPlayer.length; i++) {
		var index = countOffset(selfIndex, listPlayer[i].index);
		$("#td_name_" + index).text(listPlayer[i].userName);
	}
}

//清除离开的玩家信息
function clearPlayersInfoByLeave(leaveIndex) {
	//获取自己的位置
	var selfIndex = $("#user_sequence").val();
	//清除信息
	var index = countOffset(selfIndex, leaveIndex);
	$("#td_name_" + index).text("玩家已离开");
}

//计算显示偏移
function countOffset(selfIndex, userIndex) {
	var index = userIndex - selfIndex;
	if (index < 0) {
		index = index + 4;
	}
	return index;
}
