package com.wiflish.luban.framework.controller;

import com.wiflish.luban.framework.web.config.WebProperties;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wiflish
 * @since 2024-08-26
 */
@RestController
public class PingController {
    @Resource
    private WebProperties webProperties;

    /**
     * 心跳检查.
     *
     * @return 0=正常.
     */
    @RequestMapping("/ping")
    public String ping() {
        return webProperties.getPing();
    }
}
