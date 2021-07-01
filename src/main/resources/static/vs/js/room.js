var selfIndex;

function createRoom(data) {
    selfIndex = data.index;
    $("#td_name_0").text(data.userName);
}

function joinRoom(data) {
    if (data.type === 0) {
        selfIndex = data.index;
        $("#td_name_0").text(data.userName);
        //填充已占有位置信息
        var players = data.players;
        for (var i = 0; i < players.length; i++) {
            $("#td_name_" + countOffset(selfIndex, players[i].index)).text(players[i].userName);
        }
    } else {
        $("#td_name_" + countOffset(selfIndex, data.index)).text(data.userName);
    }
}

function leaveRoom(data) {
    $("#td_name_" + countOffset(selfIndex, data.index)).text("");
}

//计算位置显示偏移
function countOffset(selfIndex, userIndex) {
    var index = userIndex - selfIndex;
    if (index < 0) {
        index = index + 4;
    }
    return index;
}
