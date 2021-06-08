package com.mhb.mosa.scoket;

import com.mhb.mosa.constant.ConstantVsScoket;
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
        //截取请求Url最后参数作为用户名
        String requestUrl = request.getURI().toString();
        log.info("拦截器获取请求-URL：" + requestUrl);
        String userName = StringUtils.substring(requestUrl, requestUrl.lastIndexOf("/") + 1);
        //放入attributes
        String userNameDecode = URLDecoder.decode(userName, ConstantVsScoket.SESSION_CODING);
        attributes.put(ConstantVsScoket.SESSION_USER_NAME, userNameDecode);
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
