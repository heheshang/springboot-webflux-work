package com.ssk.webflux.dao;

import com.ssk.webflux.domain.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-上午 9:23
 */
@Repository
public class CityRepository {

    private ConcurrentHashMap<Long, City> repository = new ConcurrentHashMap<>();

    private static final AtomicLong idGenerator = new AtomicLong();

    private final Logger logger = LoggerFactory.getLogger(CityRepository.class);

    public Long save(City city) {

        Long id = idGenerator.incrementAndGet();
        city.setId(id);
        this.repository.put(id, city);
        this.logger.info("根据【{}】保存数据", city);
        return id;
    }


    public Collection<City> findAll() {

        return this.repository.values();
    }

    public City findCityById(Long id) {

        return this.repository.get(id);
    }

    public Long updateCity(City city) {

        this.repository.put(city.getId(), city);
        return city.getId();
    }

    public Long deleteCity(Long id) {

        this.repository.remove(id);
        return id;
    }
}
