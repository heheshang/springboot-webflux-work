package com.ssk.webflux.restCli.http;

import com.ssk.webflux.domain.User;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 除了服务器端实现之外，WebFlux 也提供了反应式客户端，可以访问 HTTP、SSE 和 WebSocket 服务器端
 * 如下是客户端实现
 *
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-下午 3:47
 */
public class RESTClient {

    /**
     * 对于 HTTP 和 SSE，可以使用 WebFlux 模块中的类 org.springframework.web.reactive.function.client.WebClient。
     * 代码清单  中的 RESTClient 用来访问前面小节中创建的 REST API。
     * 首先使用 WebClient.create 方法来创建一个新的 WebClient 对象，
     * 然后使用方法 post 来创建一个 POST 请求，并使用方法 body 来设置 POST 请求的内容。
     * 方法 exchange 的作用是发送请求并得到以 Mono<ServerResponse>表示的 HTTP 响应。
     * 最后对得到的响应进行处理并输出结果。ServerResponse 的 bodyToMono 方法把响应内容转换成类 User 的对象，
     * 最终得到的结果是 Mono<User>对象。调用 createdUser.block 方法的作用是等待请求完成并得到所产生的类 User 的对象
     *
     * @param args
     */
    public static void main(String[] args) {

        final User user = new User();
        user.setId("111");
        user.setName("Test");
        user.setEmail("test@example.org");
        // 访问 spring-webflux-anno 项目的 user
        final WebClient client = WebClient.create("http://localhost:8081/user");
        final Mono<User> createdUser = client.post()
                .uri("")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .exchange()
                .flatMap(response -> response.bodyToMono(User.class));
        System.out.println(createdUser.block());
    }
}
