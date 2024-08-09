package com.wiflish.luban.framework.pay.ezeelink.dto.event;

import com.alibaba.fastjson2.annotation.JSONField;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkBaseDTO;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-08-08
 */
@Data
public class PaymentEventDTO extends EzeelinkBaseDTO {
    @JSONField(name = "emoney_code")
    private String emoneyCode;
    @JSONField(name = "emoney_name")
    private String emoneyName;
}
