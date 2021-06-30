$(function () {

    //登入
    $("#login").click(function () {
        var userName = $("#user_name").val();
        if (userName.length = 0 || userName.length > 8) {
            alert("昵称输入有误");
            return;
        }
        $.ajax({
            url: pathHead + "link/login/" + userName,
            type: "get",
            success: function (data) {
                alert(data.msg);
                if (data.code === "0000") {
                    sessionStorage.setItem("userName", userName);
                    window.location.href = "square.html";
                }
            }, error: function () {
                alert("发生错误");
            }
        });
    });

});