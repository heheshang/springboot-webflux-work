package com.ssk.webflux.web;

import com.ssk.webflux.domain.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

/**
 * 在 spring-test 模块中也添加了对 WebFlux 的支持。
 * 通过类 org.springframework.test.web.reactive.server.WebTestClient 可以测试 WebFlux 服务器。
 * 进行测试时既可以通过 mock 的方式来进行，也可以对实际运行的服务器进行集成测试。
 * 代码清单  通过一个集成测试来测试 UserController 中的创建用户的功能。
 * 方法 WebTestClient.bindToServer 绑定到一个运行的服务器并设置了基础 URL。
 * 发送 HTTP 请求的方式与代码清单 10 相同，不同的是 exchange 方法的返回值是 ResponseSpec 对象，
 * 其中包含了 expectStatus 和 expectBody 等方法来验证 HTTP 响应的状态码和内容。
 * 方法 jsonPath 可以根据 JSON 对象中的路径来进行验证
 *
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-下午 4:08
 */
public class UserControllerTest {

    private final WebTestClient client = WebTestClient.bindToServer().baseUrl("http://localhost:8081").build();

    @Test
    public void testCreateUser() throws Exception {

        final User user = new User();
        user.setName("Test");
        user.setEmail("test@example.org");
        this.client.post().uri("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(user), User.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody().jsonPath("name").isEqualTo("Test");
    }
}
