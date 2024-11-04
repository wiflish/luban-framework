package com.wiflish.luban.framework.i18n.aop;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import com.wiflish.luban.framework.common.pojo.PageResult;
import com.wiflish.luban.framework.i18n.annotation.I18nDynamicPoint;
import com.wiflish.luban.framework.i18n.spring.I18nMessageSourceFacade;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

@Aspect
@RequiredArgsConstructor
public class I18nAspect {

    private final I18nMessageSourceFacade i18nMessageSourceFacade;

    @Around("@annotation(com.wiflish.luban.framework.i18n.annotation.I18nDynamicPoint)")
    public Object handleI18n(ProceedingJoinPoint joinPoint) throws Throwable {

        // 获取方法参数
        Object[] args = joinPoint.getArgs();
        // 继续执行目标方法
        Object result = joinPoint.proceed(args);

        if (result == null) return null;

        // 获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        // 获取方法上的注解
        I18nDynamicPoint annotation = method.getAnnotation(I18nDynamicPoint.class);
        // 获取注解属性
        boolean cacheable = annotation.cacheable();
        String code = annotation.code();

        if (result instanceof PageResult) {
            for (Object item : ((PageResult<?>) result).getList()) {
                getDynamicMessage(item, code, cacheable);
            }
        } else if (result instanceof List) {
            for (Object item : (List<?>) result) {
                getDynamicMessage(item, code, cacheable);
            }
        } else {
            getDynamicMessage(result, code, cacheable);
        }

        return result;
    }

    private void getDynamicMessage(Object result, String code, boolean cacheable) {
        Object id = ReflectUtil.getFieldValue(result, "id");
        if (id == null) return;

        Map<String, Object> dynamicMap = i18nMessageSourceFacade.getDynamicMessage(code, id, cacheable);
        BeanUtil.copyProperties(dynamicMap, result);
    }

}
