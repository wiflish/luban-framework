package com.wiflish.luban.framework.pay.xendit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <a href="https://developers.xendit.co/api-reference/payments-api/#payment-method-object">payment-method-object</a>
 *
 * @author wiflish
 * @since 2024-07-29
 */
@Getter
@AllArgsConstructor
public enum PaymentTypeEnum {
    EWALLET("EWALLET"),
    DIRECT_DEBIT("DIRECT_DEBIT"),
    CARD("CARD"),
    VIRTUAL_ACCOUNT("VIRTUAL_ACCOUNT"),
    OVER_THE_COUNTER("OVER_THE_COUNTER"),
    QR_CODE("QR_CODE"),
    ;
    private final String name;
}
