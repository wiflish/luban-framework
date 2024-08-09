package com.wiflish.luban.framework.pay.ezeelink.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-08-08
 */
@Data
public class EzeelinkEMoneyReq extends EzeelinkBaseDTO {
    @JSONField(name = "bill_description")
    private String billDescription;
    @JSONField(name = "emoney_code")
    private String emoneyCode;
    @JSONField(name = "partner_return_link")
    private String partnerReturnLink;
    @JSONField(name = "expiration_duration")
    private Integer expirationDuration;
}
