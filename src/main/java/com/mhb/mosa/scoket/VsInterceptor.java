package com.mhb.mosa.scoket;

import com.mhb.mosa.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.net.URLDecoder;
import java.util.Map;

/**
 * @description: 拦截器
 * @auther: MaoHangBin
 * @date: 2019/11/27 15:19
 */

@Slf4j
public class VsInterceptor extends HttpSessionHandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String requestUrl = request.getURI().toString();
        log.info("拦截器获取请求-URL：" + requestUrl);
        //截取请求Url最后参数上一个作为请求模块id
        String urlExcludeUserName = StringUtils.substring(requestUrl, 0, requestUrl.lastIndexOf("/"));
        String role = StringUtils.substring(urlExcludeUserName, urlExcludeUserName.lastIndexOf("/") + 1);
        attributes.put(Constants.SESSION_ROLE, role);
        //截取请求Url最后参数作为用户名
        String userName = StringUtils.substring(requestUrl, requestUrl.lastIndexOf("/") + 1);
        attributes.put(Constants.SESSION_USER_NAME, URLDecoder.decode(userName, "UTF-8"));
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
