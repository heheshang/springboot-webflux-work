package com.ssk.webflux.handler;

import com.ssk.webflux.dao.CityRepository;
import com.ssk.webflux.domain.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-上午 9:30
 */
@Component
public class CityHandler {

    private final CityRepository cityRepository;

    private final Logger logger = LoggerFactory.getLogger(CityHandler.class);

    @Autowired
    public CityHandler(CityRepository cityRepository) {

        this.cityRepository = cityRepository;
    }

    public Mono<Long> save(City city) {

        this.logger.info("根据【{}】保存数据", city);
        return Mono.create(cityMonSink -> cityMonSink.success(this.cityRepository.save(city)));
    }

    public Mono<City> findCityById(Long id) {

        return Mono.justOrEmpty(this.cityRepository.findCityById(id));
    }

    public Flux<City> findAllCity() {

        return Flux.fromIterable(this.cityRepository.findAll());
    }

    public Mono<Long> modifyCity(City city) {

        return Mono.create(cityMonoSink -> cityMonoSink.success(this.cityRepository.updateCity(city)));
    }

    public Mono<Long> deleteCity(Long id) {

        return Mono.create(cityMonoSink -> cityMonoSink.success(this.cityRepository.deleteCity(id)));
    }

}
