package com.ssk.webflux.domain;

import lombok.Data;

/**
 * @author ssk www.8win.com Inc.All rights reserved
 * @version v1.0
 * @date 2019-05-17-上午 9:20
 */
@Data
public class City {

    /**
     * 城市id
     */
    private Long id;

    /**
     * 省份Id
      */
    private Long provinceId;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 城市描述
     */
    private String description;
}
