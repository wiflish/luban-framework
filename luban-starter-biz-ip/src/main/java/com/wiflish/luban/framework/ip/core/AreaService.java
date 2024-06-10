package com.wiflish.luban.framework.ip.core;

import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * @author wiflish
 * @since 2024-06-10
 */
public interface AreaService extends InitializingBean {
    /**
     * 加载所有区域
     *
     * @return 区域列表
     */
    List<Area> loadAllAreas();
}
