package com.wiflish.luban.framework.pay.xendit.dto.payment;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-07-29
 */
@Data
public class DebitCardDTO {
    @JSONField(name = "mobile_number")
    private String mobileNumber;

    @JSONField(name = "card_last_four")
    private String cardLastFour;

    @JSONField(name = "card_expiry")
    private String cardExpiry;

    private String email;
}
