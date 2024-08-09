package com.wiflish.luban.framework.pay.ezeelink.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-08-08
 */
@Data
public class EzeelinkEMoneyResp extends EzeelinkBaseDTO {
    @JSONField(name = "emoney_name")
    private String emoney_name;
    @JSONField(name = "emoney_code")
    private String emoneyCode;
    @JSONField(name = "payment_link")
    private String paymentLink;
}
