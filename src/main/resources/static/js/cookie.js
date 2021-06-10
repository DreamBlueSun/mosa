/**
 * 设置cookie
 * @param cname 名称
 * @param cvalue 值
 */
function setCookie(cname, cvalue) {
    document.cookie = cname + "=" + cvalue;
}

/**
 * 设置cookie
 * @param cname 名称
 * @param cvalue 值
 * @param exdays 过期时间（天）
 */
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}

/**
 * 设置cookie
 * @param cname 名称
 * @param cvalue 值
 * @param exdays 过期时间（天）
 * @param path cookie 路径
 */
function setCookie(cname, cvalue, exdays, path) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toGMTString();
    document.cookie = cname + "=" + cvalue + "; " + expires + "; path=" + path;
}

/**
 * 获取cookie值
 * @param cname 名称
 * @returns {string}
 */
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i].trim();
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}