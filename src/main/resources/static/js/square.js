$(function () {

    var userName = getCookie("userName");

    initWebSocket("2", userName);

    //发送消息
    $("#send").click(function () {
        if (web_socket != null) {
            var to = {module: "2", type: "0", data: $("#msg").val()};
            web_socket.send(JSON.stringify(to));
        }
    });

    //创建MoSa房间
    $("#create_mosa").click(function () {
        $.ajax({
            url: pathHead + "mosa/room/create/" + userName,
            type: "get",
            success: function (data) {
                alert(data.msg);
                if (data.code === "0000") {
                    window.location.href = "vs/html/mosa.html";
                }
            }, error: function () {
                alert("发生错误");
            }
        });
    });

    //加入MoSa房间
    $("#join_mosa").click(function () {
        $.ajax({
            url: pathHead + "mosa/room/join/" + $("#room_id").val() + "/" + userName,
            type: "get",
            success: function (data) {
                alert(data.msg);
                if (data.code === "0000") {
                    window.location.href = "vs/html/mosa.html";
                }
            }, error: function () {
                alert("发生错误");
            }
        });
    });
});

//处理消息
function onMessage(message) {
    var vo = JSON.parse(message);
    var module = vo.module;
    var type = vo.type;
    var data = vo.data;
    if (module === 2 && type === 0) {
        var html = '<p>' + data + '</p>';
        $("#div_msg_list").append(html);
    }
}