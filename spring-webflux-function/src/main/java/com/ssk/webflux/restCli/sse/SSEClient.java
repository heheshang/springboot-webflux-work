package com.ssk.webflux.restCli.sse;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

/**
 * WebClient 还可以用同样的方式来访问 SSE 服务，如代码清单  所示。
 * 这里我们访问的是在之前的小节中创建的生成随机数的 SSE 服务。
 * 使用 WebClient 访问 SSE 在发送请求部分与访问 REST API 是相同的，
 * 所不同的地方在于对 HTTP 响应的处理。由于 SSE 服务的响应是一个消息流，
 * 我们需要使用 flatMapMany 把 Mono<ServerResponse>转换成一个 Flux<ServerSentEvent>对象，
 * 这是通过方法 BodyExtractors.toFlux 来完成的，
 * 其中的参数 new ParameterizedTypeReference<ServerSentEvent<String>>() {}表明了响应消息流中的内容是 ServerSentEvent 对象。由于 SSE 服务器会不断地发送消息，这里我们只是通过 buffer 方法来获取前 10 条消息并输出
 *
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-下午 3:52
 */
public class SSEClient {

    public static void main(String[] args) {

        WebClient client = WebClient.create();
        client.get().uri("http://localhost:8081/sse/randomNumbers")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(response ->
                        response.body(BodyExtractors.toFlux(new ParameterizedTypeReference<ServerSentEvent<String>>() {

                        })))
                .filter(sse -> Objects.nonNull(sse.data()))
                .map(ServerSentEvent::data)
                .buffer(10)
                .doOnNext(System.out::println)
                .blockFirst();
    }
}
