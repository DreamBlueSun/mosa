$(function () {

    listRoom();

    //创建MoSa房间
    $("#create_mosa_room").click(function () {
        $.ajax({
            url: pathHead + "mosa/room/create/" + userName,
            type: "get",
            success: function (data) {
                alert(data.msg);
                if (data.code === "0000") {
                    window.location.href = "vs/html/mosa.html?0";
                }
            }, error: function () {
                alert("发生错误");
            }
        });
    });

    //加入MoSa房间
    $("[name='joinMosaRoom']").click(function () {
        var mosa = $(this).attr("mosa");
        var roomId = $("#td-mosa-room-id-" + mosa).html();
        $.ajax({
            url: pathHead + "mosa/room/join/" + roomId + "/" + userName,
            type: "get",
            success: function (data) {
                alert(data.msg);
                if (data.code === "0000") {
                    window.location.href = "vs/html/mosa.html?1";
                }
            }, error: function () {
                alert("发生错误");
            }
        });
    });

    //刷新MoSa房间列表
    $("#refresh_mosa_room").click(function () {
        listRoom();
    });
});

//获取mosa房间列表
function listRoom() {
    $.ajax({
        url: pathHead + "mosa/room/list?pageSize=5&pageNum=1",
        type: "get",
        dataType: "json",
        success: function (data) {
            if (data == null) {
                return;
            }
            var list = data.rows;
            if (list == null) {
                return;
            }
            for (var i = 0; i < list.length; i++) {
                $("#td-mosa-room-id-" + i).html(list[i].roomId);
                $("#td-mosa-players-num-" + i).html(list[i].playersNum);
                $("#tr-mosa-list-" + i).show();
            }
            for (var j = list.length; j < 5; j++) {
                $("#tr-mosa-list-" + i).hide();
            }
        }
    });
}