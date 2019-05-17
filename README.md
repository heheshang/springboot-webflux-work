# springboot-webflux-work
## spring-webflux-curd 
- 基础增删删改查

## spring-webflux-anno
- 注解编程模型 
- 服务器推送
  - SseController  服务器向浏览器推送demo
- WebSocket
  - EchoHandler  WebSocket-demo
  - ![image](https://github.com/heheshang/springboot-webflux-work/blob/master/spring-webflux-anno/src/main/resources/QQ%E6%88%AA%E5%9B%BE20190517144904.jpg)
## spring-webflux-function
- 函数式编程模型

## 结语：
反应式编程范式为开发高性能 Web 应用带来了新的机会和挑战。Spring 5 中的 WebFlux 模块可以作为开发反应式 Web 应用的基础。由于 Spring 框架的流行，WebFlux 会成为开发 Web 应用的重要趋势之一。本文对 Spring 5 中的 WebFlux 模块进行了详细的介绍，包括如何用 WebFlux 开发 HTTP、SSE 和 WebSocket 服务器端应用，以及作为客户端来访问 HTTP、SSE 和 WebSocket 服务。对于 WebFlux 的基于 Java 注解和函数式编程等两种模型都进行了介绍。最后介绍了如何测试 WebFlux 应用。


* 删除 github 上 .idea文件
```bash
$ git --help # 帮助命令


$ git pull origin master  # 将远程仓库里面的项目拉下来

$ dir # 查看有哪些文件夹

$ git rm -r --cached .idea  # 删除.idea文件夹
$ git commit -m '删除.idea' # 提交,添加操作说明

```



* [WebFlux Restful CRUD 实践（三）](https://mp.weixin.qq.com/s/93lBuIQhPM-XWyBaynx6Hg)
* [使用 Spring 5 的 WebFlux 开发反应式 Web 应用](https://www.ibm.com/developerworks/cn/java/spring5-webflux-reactive/index.html)
* [6. WebFlux framework](https://docs.spring.io/spring/docs/5.0.0.RC2/spring-framework-reference/web.html#web-reactive)
* [使用 Reactor 进行反应式编程](https://www.ibm.com/developerworks/cn/java/j-cn-with-reactor-response-encode/index.html)