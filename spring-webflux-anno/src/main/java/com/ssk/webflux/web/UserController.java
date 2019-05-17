package com.ssk.webflux.web;

import com.ssk.webflux.domain.User;
import com.ssk.webflux.exception.ResourceNotFoundException;
import com.ssk.webflux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * UserController 是具体的 Spring MVC 控制器类。它使用类 UserService 来完成具体的功能。
 * 类 UserController 中使用了注解@ExceptionHandler 来添加了 ResourceNotFoundException 异常的处理方法，
 * 并返回 404 错误。类 UserController 中的方法都很简单，只是简单地代理给 UserService 中的对应方法。
 *
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-下午 2:02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
    @ExceptionHandler(ResourceNotFoundException.class)
    public void notFound() {

    }

    @GetMapping
    public Flux<User> list() {

        return this.userService.list();
    }

    @GetMapping("/{id}")
    public Mono<User> getById(@PathVariable("id") final String id) {

        return this.userService.getById(id);
    }

    @PostMapping("")
    public Mono<User> create(@RequestBody final User user) {

        return this.userService.createOrUpdate(user);
    }

    @PutMapping("/{id}")
    public Mono<User> update(@PathVariable("id") final String id, @RequestBody final User user) {

        Objects.requireNonNull(user);
        user.setId(id);
        return this.userService.createOrUpdate(user);
    }

    @DeleteMapping("/{id}")
    public Mono<User> delete(@PathVariable("id") final String id) {

        return this.userService.delete(id);
    }
}
