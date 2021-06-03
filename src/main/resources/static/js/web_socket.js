var web_socket = null;

// 初始化web_socket
function initWebSocket(roomName, userName) {
    //判断浏览器是否支持web_socket
    if ('WebSocket' in window) {
        var url = "ws://" + document.location.host + pathHead + "mosa/" + roomName + "/" + userName;
        console.log(url);
        web_socket = new WebSocket(url);
        web_socket.onopen = function () {
            console.log("open")
        };
        web_socket.onclose = function () {
            console.log("close");
            window.location.reload();
        };
        web_socket.onerro = function () {
            console.log("erro");
            window.location.reload();
        };
        web_socket.onmessage = function (message) {
            //处理服务器消息
            var html = "<p>'+msg+'</p>";
            $("#div_msg_list").append();
        }
    } else {
        alert("该换手机了。。。");
    }
}

//关闭连接
function close() {
    if (web_socket != null) {
        web_socket.close()
    }
}

//页面关闭之前
window.onbeforeunload = function () {
    close();
};

$(function () {

    //加入房间
    $("#join").click(function () {
        var userName = $("#user_name").val();
        if (userName.length = 0 || userName.length > 8) {
            alert("昵称输入有误");
            return;
        }
        $("#div_joining").hide();
        $("#div_index").show();
        initWebSocket(roomName, userName);
    });

    //发送消息
    $("#send").click(function () {
        if (web_socket != null) {
            var msg = $("#msg").val();
            web_socket.send(msg);
        }
    });

    //离开房间
    // $("#leave").click(function() {
    // 	close();
    // 	window.location.reload();
    // });

});
