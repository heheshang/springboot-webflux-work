package com.ssk.webflux.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * WebSocket 支持客户端与服务器端的双向通讯。当客户端与服务器端之间的交互方式比较复杂时，
 * 可以使用 WebSocket。WebSocket 在主流的浏览器上都得到了支持。
 * WebFlux 也对创建 WebSocket 服务器端提供了支持。
 * 在服务器端，我们需要实现接口 org.springframework.web.reactive.socket.WebSocketHandler 来处理 WebSocket 通讯。
 * 接口 WebSocketHandler 的方法 handle 的参数是接口 WebSocketSession 的对象，
 * 可以用来获取客户端信息、接送消息和发送消息。
 * 代码清单 中的 EchoHandler 对于每个接收的消息，会发送一个添加了"ECHO -> "前缀的响应消息。
 * WebSocketSession 的 receive 方法的返回值是一个 Flux<WebSocketMessage>对象，
 * 表示的是接收到的消息流。而 send 方法的参数是一个 Publisher<WebSocketMessage>对象，表示要发送的消息流。
 * <p>
 * 在 handle 方法，使用 map 操作对 receive 方法得到的 Flux<WebSocketMessage>中包含的消息继续处理，
 * 然后直接由 send 方法来发送。
 * 在创建了 WebSocket 的处理器 EchoHandler 之后，下一步需要把它注册到 WebFlux 中。
 * 我们首先需要创建一个类 WebSocketHandlerAdapter 的对象，该对象负责把 WebSocketHandler 关联到 WebFlux 中。
 * 代码清单 {@link com.ssk.webflux.config.WebSocketConfiguration} 中给出了相应的 Spring 配置。
 * 其中的 HandlerMapping 类型的 bean 把 EchoHandler 映射到路径 /echo
 *
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-下午 2:27
 */
@Component
public class EchoHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {

        return webSocketSession.send(
                webSocketSession.receive()
                        .map(msg -> webSocketSession.textMessage("ECHO->" + msg.getPayloadAsText())));
    }
    /**
     * 运行应用之后，可以使用工具来测试该 WebSocket 服务。打开工具页面 https://www.websocket.org/echo.html，
     * 然后连接到 ws://localhost:8081/echo，可以发送消息并查看服务器端返回的结果。
     */
}
