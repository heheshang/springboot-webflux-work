package com.ssk.webflux.service;

import com.ssk.webflux.domain.User;
import com.ssk.webflux.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 该 REST API 用来对用户数据进行基本的 CRUD 操作。作为领域对象的 User 类中包含了 id、name 和 email 等三个基本的属性。
 * 为了对 User 类进行操作，我们需要提供服务类 UserService，如代码清单 2 所示。
 * 类 UserService 使用一个 Map 来保存所有用户的信息，并不是一个持久化的实现。这对于示例应用来说已经足够了。
 * 类 UserService 中的方法都以 Flux 或 Mono 对象作为返回值，这也是 WebFlux 应用的特征。在方法 getById()中，
 * 如果找不到 ID 对应的 User 对象，会返回一个包含了 ResourceNotFoundException 异常通知的 Mono 对象。
 * 方法 getById()和 createOrUpdate()都可以接受 String 或 Flux 类型的参数。
 * Flux 类型的参数表示的是有多个对象需要处理。这里使用 doOnNext()来对其中的每个对象进行处理
 *
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-下午 1:53
 */
@Service
public class UserService {

    private final Map<String, User> data = new ConcurrentHashMap<>();

    public Flux<User> list() {

        return Flux.fromIterable(this.data.values());
    }

    public Flux<User> getById(Flux<String> ids) {

        return ids.flatMap(id -> Mono.justOrEmpty(this.data.get(id)));
    }

    public Mono<User> getById(String id) {

        return Mono.justOrEmpty(this.data.get(id)).switchIfEmpty(Mono.error(new ResourceNotFoundException()));
    }

    public Mono<User> createOrUpdate(User user) {

        this.data.put(user.getId(), user);
        return Mono.just(user);
    }

    public Mono<User> delete(String id) {

        return Mono.justOrEmpty(this.data.remove(id));
    }
}
