package com.wiflish.luban.framework.jackson.core.databind;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.wiflish.luban.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;

/**
 * LocalDateTime反序列化规则
 * <p>
 * 会将毫秒级时间戳反序列化为LocalDateTime
 */
@Slf4j
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    public static final LocalDateTimeDeserializer INSTANCE = new LocalDateTimeDeserializer();

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        //时间戳优先级最高
        long valueAsLong = p.getValueAsLong();
        if (valueAsLong > 0) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(valueAsLong), ZoneId.systemDefault());
        }
        try {
            Object currentValue = p.getCurrentValue();
            String currentName = p.getCurrentName();
            Field field = ReflectUtil.getField(currentValue.getClass(), currentName);
            JsonFormat annotation = field.getAnnotation(JsonFormat.class);
            String pattern = FORMAT_YEAR_MONTH_DAY;
            if (annotation != null && StrUtil.isNotEmpty(annotation.pattern())) {
                pattern = annotation.pattern();
            }
            return LocalDateTimeUtil.parse(p.getValueAsString(), pattern);
        } catch (Exception e) {
            log.warn("deserialize LocalDateTime error. {}", e.getMessage());
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(valueAsLong), ZoneId.systemDefault());
    }
}
