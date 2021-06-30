var selfIndex;

function createRoom(data) {
    selfIndex = data.index;
    $("#td_name_0").text(data.userName);
}

function joinRoom(data) {
    if (data.type === 0) {
        selfIndex = data.index;
        $("#td_name_0").text(data.userName);
    } else {
        var index = countOffset(selfIndex, data.index);
        $("#td_name_" + index).text(data.userName);
    }
}

function leaveRoom(data) {
    var index = countOffset(selfIndex, data.index);
    $("#td_name_" + index).text("离线");
}

//计算位置显示偏移
function countOffset(selfIndex, userIndex) {
    var index = userIndex - selfIndex;
    if (index < 0) {
        index = index + 4;
    }
    return index;
}
