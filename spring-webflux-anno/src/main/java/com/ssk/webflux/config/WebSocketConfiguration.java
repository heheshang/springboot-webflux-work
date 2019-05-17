package com.ssk.webflux.config;

import com.ssk.webflux.websocket.EchoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-下午 2:40
 */
@Configuration
public class WebSocketConfiguration {

    @Autowired
    @Bean
    public HandlerMapping webSocketMapping(EchoHandler echoHandler) {

        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/echo", echoHandler);
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        mapping.setUrlMap(map);
        return mapping;

    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {

        return new WebSocketHandlerAdapter();
    }
}
