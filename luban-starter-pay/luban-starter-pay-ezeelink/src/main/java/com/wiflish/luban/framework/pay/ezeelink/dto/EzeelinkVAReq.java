package com.wiflish.luban.framework.pay.ezeelink.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-08-08
 */
@Data
public class EzeelinkVAReq extends EzeelinkBaseDTO {
    @JSONField(name = "bank_code")
    private String bankCode;
    @JSONField(name = "bill_description")
    private String billDescription;
    @JSONField(name = "expiration_duration")
    private String expirationDuration;
}
