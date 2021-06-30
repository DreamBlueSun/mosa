var selfName;

var alive = true;

$(function () {
    selfName = sessionStorage.getItem("userName");
    initWebSocket("3", selfName);

    //离开房间
    $("#leave_room").click(function () {
        window.location.href = "../square.html";
    });

});

//socket开启之后执行
function afterOpenWebSocket() {
    //初始化信息（create、join）
    var type = location.search.substr(1);
    var to = {module: "3", type: type};
    web_socket.send(JSON.stringify(to));
}

//socket关闭之前执行
function beforeCloseWebSocket() {
    if (alive && web_socket != null) {
        //初始化信息（leave）
        var to = {module: "3", type: "2"};
        web_socket.send(JSON.stringify(to));
        alive = false;
    }
}

//处理服务器消息
function onMessage(message) {
    var vo = JSON.parse(message);
    var module = vo.module;
    var type = vo.type;
    var data = vo.data;
    if (module === 3) {
        if (type === 0) {
            createRoom(data);
        } else if (type === 1) {
            //加入房间
            joinRoom(data);
        } else if (type === 2) {
            //离开房间
            leaveRoom(data);
        } else if (type === 3) {
            //准备就绪
        } else if (type === 4) {
            //打出卡牌
        } else if (type === 5) {
            //抽取卡牌
        }
    }


    //===============================================旧逻辑
    // if (vo.type == 0) {
    //     //离开房间
    //     leaveRoom(data);
    // } else if (vo.type == 1) {
    //     //加入房间
    //     joinRoom(data);
    // } else if (vo.type == 3) {
    //     //---------------牌局初始化---------------
    //     //刷新场次信息
    //     refreshRoundInfo(data);
    //     //初始化玩家手牌
    //     refreshHandCards(data.cardMap);
    //     //显示玩家手牌数量
    //     refreshHandNum(data.numMap);
    //     //当前场次设为已开局
    //     $("#round_is_start").val("1");
    //     var selfIndex = $("#user_sequence").val();
    //     if (data.next == selfIndex) {
    //         //判断强制抽牌
    //         if (data.mustDraw == 1) {
    //             alert("无可用卡牌!");
    //             mustDrawCard();
    //         }
    //     }
    // } else if (vo.type == 4) {
    //     //---------------打出卡牌---------------
    //     //刷新场次信息
    //     refreshRoundInfo(data);
    //     var selfIndex = $("#user_sequence").val();
    //     if (data.index == selfIndex) {
    //         //初始化玩家手牌
    //         refreshHandCards(data.cardMap);
    //     } else {
    //         //显示其他玩家手牌数量
    //         refreshHandNum(data.numMap);
    //     }
    //     //判断胜负
    //     if (data.win == 1) {
    //         alert(data.winIndex + "获胜!");
    //         //进入下一场次
    //         $("#round_is_start").val("0");
    //         $("#card_library").click();
    //         return;
    //     }
    //     //下一位行动位置玩家
    //     if (data.next == selfIndex) {
    //         //判断是否为棱镜
    //         if (data.rule == 2) {
    //             cardSkillPrismPopUpWindows();
    //         }
    //         //判断强制抽牌
    //         if (data.mustDraw == 1) {
    //             alert("无可用卡牌!");
    //             mustDrawCard();
    //         }
    //     }
    // } else if (vo.type == 5) {
    //     //---------------抽取卡牌---------------
    //     var selfIndex = $("#user_sequence").val();
    //     if (data.index == selfIndex) {
    //         //初始化玩家手牌
    //         refreshHandCards(data.cardMap);
    //         //判断强制抽牌
    //         if (data.mustDraw == 1) {
    //             alert("无可用卡牌!");
    //             mustDrawCard();
    //         }
    //     } else {
    //         //显示其他玩家手牌数量
    //         refreshHandNum(data.numMap);
    //     }
    //     //判断胜负
    //     if (data.win == 1) {
    //         alert(data.winIndex + "爆牌结束!");
    //         //进入下一场次
    //         $("#round_is_start").val("0");
    //         $("#card_library").click();
    //         return;
    //     }
    // } else if (vo.type == 10) {
    //     //---------------卡牌技能(棱镜)---------------
    //     //刷新场次信息
    //     refreshRoundInfo(data);
    //     var selfIndex = $("#user_sequence").val();
    //     if (data.next == selfIndex) {
    //         //判断强制抽牌
    //         if (data.mustDraw == 1) {
    //             alert("无可用卡牌!");
    //             mustDrawCard();
    //         }
    //     }
    // } else if (vo.type == 20) {
    //     //---------------对局提示信息---------------
    //     alert(data);
    // }
}
