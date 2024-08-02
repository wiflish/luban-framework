package com.wiflish.luban.framework.pay.xendit.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * <a href="https://dashboard.xendit.co/settings/developers#webhooks">详见控制台</a>
 *
 * @author wiflish
 * @since 2024-07-19
 */
@Data
public class XenditEventDTO<T> {
    private String event;
    @JSONField(name = "business_id")
    private String businessId;
    private String created;
    private T data;
}
