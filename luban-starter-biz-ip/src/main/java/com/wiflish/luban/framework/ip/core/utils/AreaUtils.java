package com.wiflish.luban.framework.ip.core.utils;

import com.wiflish.luban.framework.ip.core.Area;
import com.wiflish.luban.framework.ip.core.AreaService;
import com.wiflish.luban.framework.ip.core.enums.AreaTypeEnum;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import static com.wiflish.luban.framework.common.util.collection.CollectionUtils.convertList;

/**
 * 区域工具类
 *
 * @author wiflish
 */
@Slf4j
public class AreaUtils {
    /**
     * Area 内存缓存，提升访问速度
     */
    private static Map<Integer, Area> areas;

    private AreaUtils() {
    }

    public static void init(AreaService areaService) {
        if (areas != null && !areas.isEmpty()) {
            log.info("[getArea][已初始化]");
            return;
        }
        // TODO 后续需要根据国家不同做处理，否则存在性能问题
        areas = new ConcurrentHashMap<>();
        if (areaService != null) {
            List<Area> areaList = areaService.loadAllAreas();
            for (Area area : areaList) {
                if (!area.getType().equals(AreaTypeEnum.DISTRICT.getType())) {
                    area.setChildren(new ArrayList<>());
                }
                areas.put(area.getId(), area);
                Integer parentId = area.getParentId();
                Area parentArea = areas.get(parentId);
                if (parentArea != null) {
                    parentArea.getChildren().add(area);
                }
            }
        }
        log.info("[getArea][初始化 Area 数量为 {}]", areas.size());
    }

    /**
     * 获得指定编号对应的区域
     *
     * @param id 区域编号
     * @return 区域
     */
    public static Area getArea(Integer id) {
        return areas.get(id);
    }

    /**
     * 获得区域树
     *
     * @param stateCode 国家编码
     * @return 区域
     */
    public static List<Area> getAreaTree(String stateCode) {
        return areas.values().stream().filter(area -> Objects.equals(area.getCode(), stateCode)).findFirst().orElse(new Area()).getChildren();
    }

    /**
     * 格式化区域
     *
     * @param id 区域编号
     * @return 格式化后的区域
     */
    public static String format(Integer id) {
        return format(id, " ");
    }

    /**
     * 格式化区域
     *
     * 例如说：
     * 1. id = “静安区”时：上海 上海市 静安区
     * 2. id = “上海市”时：上海 上海市
     * 3. id = “上海”时：上海
     * 4. id = “美国”时：美国
     * 当区域在中国时，默认不显示中国
     *
     * @param id        区域编号
     * @param separator 分隔符
     * @return 格式化后的区域
     */
    public static String format(Integer id, String separator) {
        // 获得区域
        Area area = areas.get(id);
        if (area == null) {
            return null;
        }

        // 格式化
        StringBuilder sb = new StringBuilder();
        for (Integer parentId : area.getParentPath()) {
            sb.append(areas.get(parentId).getName()).append(separator);
        }
        sb.append(area.getName());

        return sb.toString();
    }

    /**
     * 获取指定类型的区域列表
     *
     * @param type 区域类型
     * @param func 转换函数
     * @param <T>  结果类型
     * @return 区域列表
     */
    public static <T> List<T> getByType(AreaTypeEnum type, Function<Area, T> func) {
        return convertList(areas.values(), func, area -> type.getType().equals(area.getType()));
    }

    /**
     * 根据区域编号、上级区域类型，获取上级区域编号
     *
     * @param id   区域编号
     * @param type 区域类型
     * @return 上级区域编号
     */
    public static Integer getParentIdByType(Integer id, @NonNull AreaTypeEnum type) {
        Area area = areas.get(id);
        if (area == null) {
            return null;
        }
        if (Objects.equals(area.getType(), type.getType())) {
            return area.getId();
        }
        List<Integer> parentPath = area.getParentPath();
        for (Integer parentId : parentPath) {
            Area parent = areas.get(parentId);
            if (Objects.equals(parent.getType(), type.getType())) {
                return parent.getId();
            }
        }
        return null;
    }

}
