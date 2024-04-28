package com.wiflish.luban.framework.idempotent.config;

import com.wiflish.luban.framework.idempotent.core.aop.IdempotentAspect;
import com.wiflish.luban.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import com.wiflish.luban.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import com.wiflish.luban.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import com.wiflish.luban.framework.idempotent.core.redis.IdempotentRedisDAO;
import com.wiflish.luban.framework.redis.config.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = RedisAutoConfiguration.class)
public class IdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
