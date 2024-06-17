package com.wiflish.luban.framework.ip.core;

import com.wiflish.luban.framework.ip.core.enums.AreaTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 区域节点，包括国家、省份、城市、地区等信息
 *
 * 数据可见 resources/area.csv 文件
 *
 * @author wiflish
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Area {

    /**
     * 编号 - 全球，即根目录
     */
    public static final Integer ID_GLOBAL = 0;
    /**
     * 编号 - 中国
     */
    public static final Integer ID_CHINA = 1;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 父级id
     */
    private List<Integer> parentPath;

    /**
     * 名字
     */
    private String name;

    /**
     * 类型
     * 枚举 {@link AreaTypeEnum}
     */
    private Integer type;

    /**
     * 编码
     */
    private String code;

    /**
     * 子节点
     */
    private List<Area> children;
}
