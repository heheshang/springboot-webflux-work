package com.ssk.webflux.restCli.websocket;

import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.time.Duration;

/**
 * 问 WebSocket 不能使用 WebClient，而应该使用专门的 WebSocketClient 客户端。
 * Spring Boot 的 WebFlux 模板中默认使用的是 Reactor Netty 库。
 * Reactor Netty 库提供了 WebSocketClient 的实现。
 * 在代码清单 中，我们访问的是上面小节中创建的 WebSocket 服务。WebSocketClient 的 execute 方法与 WebSocket 服务器建立连接，
 * 并执行给定的 WebSocketHandler 对象。该 WebSocketHandler 对象与代码清单 6 中的作用是一样的，只不过它是工作于客户端，
 * 而不是服务器端。在 WebSocketHandler 的实现中，首先通过 WebSocketSession 的 send 方法来发送字符串 Hello 到服务器端，
 * 然后通过 receive 方法来等待服务器端的响应并输出。方法 take(1)的作用是表明客户端只获取服务器端发送的第一条消息
 *
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-下午 3:59
 */
public class WsClient {

    public static void main(String[] args) {

        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(URI.create("ws://localhost:8081/echo"), session ->
                session.send(Flux.just(session.textMessage("Hello")))
                        .thenMany(session.receive().take(1).map(webSocketMessage -> {
                            System.out.println(webSocketMessage.getPayloadAsText());
                            return webSocketMessage.getPayloadAsText();
                        })).then()

        ).block(Duration.ofMillis(50000));

    }
}
