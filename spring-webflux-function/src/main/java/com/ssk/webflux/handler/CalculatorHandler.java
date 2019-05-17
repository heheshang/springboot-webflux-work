package com.ssk.webflux.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.function.BiFunction;

/**
 * 在函数式编程模型中，每个请求是由一个函数来处理的，
 * 通过接口 org.springframework.web.reactive.function.server.HandlerFunction
 * 来表示。HandlerFunction 是一个函数式接口，其中只有一个方法
 * Mono<T extends ServerResponse> handle(ServerRequest request)，因此可以用 labmda 表达式来实现该接口。
 * 接口 ServerRequest 表示的是一个 HTTP 请求。通过该接口可以获取到请求的相关信息，
 * 如请求路径、HTTP 头、查询参数和请求内容等。方法 handle 的返回值是一个 Mono<T extends ServerResponse>对象。
 * 接口 ServerResponse 用来表示 HTTP 响应。ServerResponse 中包含了很多静态方法来创建不同 HTTP 状态码的响应对象。
 * 本节中通过一个简单的计算器来展示函数式编程模型的用法。
 * 代码清单  中给出了处理不同请求的类 CalculatorHandler，其中包含的方法 add、subtract、multiply 和 divide
 * 都是接口 HandlerFunction 的实现。
 * 这些方法分别对应加、减、乘、除四种运算。每种运算都是从 HTTP 请求中获取到两个作为操作数的整数，再把运算的结果返回
 *
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-下午 3:06
 */
@Component
public class CalculatorHandler {

    /**
     * http://localhost:8082/calculator?operator=add&v1=1&v2=2
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> add(final ServerRequest request) {

        return this.calculate(request, (v1, v2) -> v1 + v2);
    }

    /**
     * http://localhost:8082/calculator?operator=subtract&v1=1&v2=2
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> subtract(final ServerRequest request) {

        return this.calculate(request, (v1, v2) -> v1 - v2);
    }

    /**
     * http://localhost:8082/calculator?operator=multiply&v1=1&v2=2
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> multiply(final ServerRequest request) {

        return this.calculate(request, (v1, v2) -> v1 * v2);
    }

    /**
     * http://localhost:8082/calculator?operator=divide&v1=1&v2=2
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> divide(final ServerRequest request) {

        return this.calculate(request, (v1, v2) -> v1 / v2);
    }


    private Mono<ServerResponse> calculate(ServerRequest request,
                                           BiFunction<Integer, Integer, Integer> calculateFunc) {

        Tuple2<Integer, Integer> operands = this.extractOperands(request);
        return ServerResponse.ok()
                .body(Mono.just(calculateFunc.apply(operands.getT1(), operands.getT2())), Integer.class);
    }

    private Tuple2<Integer, Integer> extractOperands(ServerRequest request) {

        return Tuples.of(this.parseOperand(request, "v1"), this.parseOperand(request, "v2"));
    }

    private int parseOperand(final ServerRequest request, final String param) {

        try {
            return Integer.parseInt(request.queryParam(param).orElse("0"));
        } catch (final NumberFormatException e) {
            return 0;
        }
    }

    /**
     * 在创建了处理请求的 HandlerFunction 之后，下一步是为这些 HandlerFunction 提供路由信息，
     * 也就是这些 HandlerFunction 被调用的条件。
     * 这是通过函数式接口 org.springframework.web.reactive.function.server.RouterFunction 来完成的。
     * 接口 RouterFunction 的方法 Mono<HandlerFunction<T extends ServerResponse>> route(ServerRequest request)对每个 ServerRequest，
     * 都返回对应的 0 个或 1 个 HandlerFunction 对象，以 Mono<HandlerFunction>来表示。当找到对应的 HandlerFunction 时，
     * 该 HandlerFunction 被调用来处理该 ServerRequest，并把得到的 ServerResponse 返回。在使用 WebFlux 的 Spring Boot 应用中，
     * 只需要创建 RouterFunction 类型的 bean，就会被自动注册来处理请求并调用相应的 HandlerFunction
     * 配置信息 @see {@link com.ssk.webflux.config.Config}
     */
}
