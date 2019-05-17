package com.ssk.webflux.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-下午 1:43
 */
@RestController
public class BasicController {

    /**
     * 基于 Java 注解的编程模型，对于使用过 Spring MVC 的开发人员来说是再熟悉不过的。
     * 在 WebFlux 应用中使用同样的模式，容易理解和上手。我们先从最经典的 Hello World 的示例开始说明。
     * 代码清单 中的 BasicController 是 REST API 的控制器，通过@RestController 注解来声明。
     * 在 BasicController 中声明了一个 URI 为/hello_world 的映射。
     * 其对应的方法 sayHelloWorld()的返回值是 Mono<String>类型，
     * 其中包含的字符串"Hello World"会作为 HTTP 的响应内容。
     *
     * @return
     */
    @GetMapping("/hello-world")
    public Mono<String> syaHelloWorld() {

        return Mono.just("Hello World");
    }
}
