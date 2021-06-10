$(function () {

    initWebSocket(getCookie("userName"));

    //发送消息
    $("#send").click(function () {
        if (web_socket != null) {
            var to = {type: "2", msg: $("#msg").val()};
            web_socket.send(JSON.stringify(to));
        }
    });
});

//处理消息
function onMessage(message) {
    var vo = JSON.parse(message);
    var type = vo.type;
    var msg = vo.msg;
    if (type === 2) {
        var html = '<p>' + msg + '</p>';
        $("#div_msg_list").append(html);
    }
}