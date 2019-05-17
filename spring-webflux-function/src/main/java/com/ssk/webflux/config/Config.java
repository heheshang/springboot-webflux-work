package com.ssk.webflux.config;

import com.ssk.webflux.handler.CalculatorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * 方法 RouterFunctions.route 用来根据 Predicate 是否匹配来确定 HandlerFunction 是否被应用。
 * RequestPredicates 中包含了很多静态方法来创建常用的基于不同匹配规则的 Predicate。
 * 如 RequestPredicates.path 用来根据 HTTP 请求的路径来进行匹配。此处我们检查请求的路径是/calculator。
 * 在清单  中，我们首先使用 ServerRequest 的 queryParam 方法来获取到查询参数 operator 的值，
 * 然后通过反射 API 在类 CalculatorHandler 中找到与查询参数 operator 的值名称相同的方法来确定要调用的 HandlerFunction 的实现，
 * 最后调用查找到的方法来处理该请求。如果找不到查询参数 operator 或是 operator 的值不在识别的列表中，
 * 服务器端返回 400 错误；如果反射 API 的方法调用中出现错误，服务器端返回 500 错误。
 *
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-下午 3:29
 */
@Configuration
public class Config {

    @Bean
    @Autowired
    public RouterFunction<ServerResponse> routerFunction(CalculatorHandler calculatorHandler) {

        return RouterFunctions.route(RequestPredicates.path("/calculator"), request ->
                request.queryParam("operator").map(operator ->
                        Mono.justOrEmpty(ReflectionUtils.findMethod(CalculatorHandler.class, operator, ServerRequest.class))
                                .flatMap(method -> (Mono<ServerResponse>) ReflectionUtils.invokeMethod(method, calculatorHandler, request))
                                .switchIfEmpty(ServerResponse.badRequest().build())
                                .onErrorResume(ex -> ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build())
                ).orElse(ServerResponse.badRequest().build())

        );
    }
}
