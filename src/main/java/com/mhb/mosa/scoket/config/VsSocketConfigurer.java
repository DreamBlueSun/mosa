package com.mhb.mosa.scoket.config;

import com.mhb.mosa.scoket.VsHandler;
import com.mhb.mosa.scoket.VsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @description: 配置
 * @auther: MaoHangBin
 * @date: 2019/11/27 15:14
 */
@Configuration
@EnableWebSocket
public class VsSocketConfigurer implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new VsHandler(), "mosa/**").addInterceptors(new VsInterceptor()).setAllowedOrigins("*");
    }
}
