package com.ssk.webflux.web;

import com.ssk.webflux.domain.City;
import com.ssk.webflux.handler.CityHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-上午 9:38
 */
@RestController
@RequestMapping(value = "/city")
public class CityWebFluxController {


    private final Logger logger = LoggerFactory.getLogger(CityWebFluxController.class);

    @Autowired
    private CityHandler cityHandler;

    @GetMapping(value = "/{id}")
    public Mono<City> findCityCyId(@PathVariable("id") Long id) {

        this.logger.info("根据【{}】请求", id);
        Mono<City> mono = this.cityHandler.findCityById(id);
        this.logger.info("根据【{}】请求返回", mono);
        return mono;
    }

    @GetMapping
    public Flux<City> findAllCity() {

        return this.cityHandler.findAllCity();
    }

    @PostMapping
    public Mono<Long> saveCity(@RequestBody City city) {

        this.logger.info("根据【{}】保存数据", city);
        Mono<Long> mono = this.cityHandler.save(city);
        this.logger.info("根据【{}】请求返回", mono);
        return mono;
    }

    @PutMapping
    public Mono<Long> modifyCity(@RequestBody City city) {

        return this.cityHandler.modifyCity(city);
    }


    @DeleteMapping(value = "/{id}")
    public Mono<Long> deleteCity(@PathVariable("id") Long id) {

        this.logger.info("根据【{}】删除", id);
        return this.cityHandler.deleteCity(id);
    }
}
