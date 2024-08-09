package com.wiflish.luban.framework.pay.ezeelink.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-08-08
 */
@Data
public class EzeelinkOVOReq extends EzeelinkBaseDTO {
    @JSONField(name = "phone_number")
    private String phoneNumber;
    @JSONField(name = "redirect_url")
    private String redirectUrl;
    @JSONField(name = "cash_amount")
    private String cashAmount;
}
