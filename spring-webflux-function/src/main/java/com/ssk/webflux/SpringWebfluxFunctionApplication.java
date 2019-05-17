package com.ssk.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * webFlux 还支持基于 lambda 表达式的函数式编程模型。
 * 与基于 Java 注解的编程模型相比，函数式编程模型的抽象层次更低，
 * 代码编写更灵活，可以满足一些对动态性要求更高的场景。不过在编写时的代码复杂度也较高，学习曲线也较陡。
 * 开发人员可以根据实际的需要来选择合适的编程模型。
 * 目前 Spring Boot 不支持在一个应用中同时使用两种不同的编程模式。
 */
@SpringBootApplication
public class SpringWebfluxFunctionApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringWebfluxFunctionApplication.class, args);
    }

}
