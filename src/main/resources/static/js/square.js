$(function () {

    initWebSocket(getCookie("userName"));

    //发送消息
    $("#send").click(function () {
        if (web_socket != null) {
            var to = {module: "2", type: "0", data: $("#msg").val()};
            web_socket.send(JSON.stringify(to));
        }
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