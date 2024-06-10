package com.wiflish.luban.framework.excel.core.convert;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * Excel 数据地区转换器
 *
 * @author HUIHUI
 */
@Slf4j
public class AreaConvert implements Converter<Object> {

    @Override
    public Class<?> supportJavaTypeKey() {
        throw new UnsupportedOperationException("暂不支持，也不需要");
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        throw new UnsupportedOperationException("暂不支持，也不需要");
    }
}
