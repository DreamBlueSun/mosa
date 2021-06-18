var web_socket = null;

// 初始化web_socket
function initWebSocket(role, userName) {
    //判断浏览器是否支持web_socket
    if ('WebSocket' in window) {
        var url = "ws://" + document.location.host + pathHead + "mosa/" + role + "/" + userName;
        console.log(url);
        web_socket = new WebSocket(url);
        web_socket.onopen = function () {
            console.log("open")
        };
        web_socket.onclose = function () {
            console.log("close");
            window.location.href = document.location.host + pathHead;
        };
        web_socket.onerro = function () {
            console.log("erro");
            window.location.href = document.location.host + pathHead;
        };
        web_socket.onmessage = function (message) {
            onMessage(message.data);
        }
    } else {
        alert("该换手机了。。。");
    }
}

//关闭连接
function close() {
    if (web_socket != null) {
        web_socket.close();
    }
}

//页面关闭之前
window.onbeforeunload = function () {
    close();
};
