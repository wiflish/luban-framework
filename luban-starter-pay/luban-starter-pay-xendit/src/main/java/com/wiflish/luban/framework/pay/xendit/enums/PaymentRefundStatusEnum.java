package com.wiflish.luban.framework.pay.xendit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <a href="https://developers.xendit.co/api-reference/payments-api/#refund-failed">Payment Refund</a>
 *
 * @author wiflish
 * @since 2024-07-17
 */
@Getter
@AllArgsConstructor
public enum PaymentRefundStatusEnum {
    SUCCEEDED("SUCCEEDED"),
    FAILED("FAILED"),
    ;

    private final String name;

    public static boolean isSuccess(String statusName) {
        return SUCCEEDED.getName().equals(statusName);
    }
}
