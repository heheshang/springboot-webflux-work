package com.ssk.webflux.web;

import com.ssk.webflux.domain.User;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 务器推送事件（Server-Sent Events，SSE）允许服务器端不断地推送数据到客户端。
 * 相对于 WebSocket 而言，服务器推送事件只支持服务器端到客户端的单向数据传递。
 * 虽然功能较弱，但优势在于 SSE 在已有的 HTTP 协议上使用简单易懂的文本格式来表示传输的数据。
 * 作为 W3C 的推荐规范，SSE 在浏览器端的支持也比较广泛，除了 IE 之外的其他浏览器都提供了支持。
 * 在 IE 上也可以使用 polyfill 库来提供支持。在服务器端来说，SSE 是一个不断产生新数据的流，非常适合于用反应式流来表示。
 * 在 WebFlux 中创建 SSE 的服务器端是非常简单的。
 * 只需要返回的对象的类型是 Flux<ServerSentEvent>，就会被自动按照 SSE 规范要求的格式来发送响应。
 * <p>
 * 代码清单 4 中的 SseController 是一个使用 SSE 的控制器的示例。
 * 其中的方法 randomNumbers()表示的是每隔一秒产生一个随机数的 SSE 端点。
 * 我们可以使用类 ServerSentEvent.Builder 来创建 ServerSentEvent 对象。
 * 这里我们指定了事件名称 random，以及每个事件的标识符和数据。事件的标识符是一个递增的整数，而数据则是产生的随机数。
 * 在测试 SSE 时，我们只需要使用 curl 来访问即可。代码清单 5 给出了调用 curl http://localhost:8080/sse/randomNumbers 的结果
 *
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-下午 2:09
 */
@RestController
@RequestMapping("/sse")
public class SseController {

    @GetMapping("/randomNumbers")
    public Flux<ServerSentEvent<User>> randomNumbers() {

        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> {
                    User user = new User();
                    user.setId(ThreadLocalRandom.current().toString());
                    return Tuples.of(seq, user);
                }).map(data -> ServerSentEvent.<User>builder()
                        .event("random")
                        .id(data.getT1().toString())
                        .data(data.getT2())
                        .build()
                );

    }
}
