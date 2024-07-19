package com.wiflish.luban.framework.pay.xendit.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * <a href="https://docs.xendit.co/payment-link/statuses">Payment Link Statuses</a>
 *
 * @author wiflish
 * @since 2024-07-17
 */
@Getter
@AllArgsConstructor
public enum PaymentRefundErrorCodeEnum {
    ACCOUNT_ACCESS_BLOCKED("ACCOUNT_ACCESS_BLOCKED", "End customer account has been blocked, end user should contact the bank for resolution"),
    ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND", "Destination account for refund was not found"),
    DUPLICATE_ERROR("DUPLICATE_ERROR", "There's an existing record of refund"),
    INSUFFICIENT_BALANCE("INSUFFICIENT_BALANCE", "Error because there is no sufficient balance in your Xendit balance to perform the refund"),
    REFUND_FAILED("REFUND_FAILED", "Refund rejected by the partner channel"),

    UNKNOWN_ERROR("UNKNOWN_ERROR", "Unknown error"),
    ;

    private final String code;
    private final String description;

    public static PaymentRefundErrorCodeEnum findByCode(String errorCode) {
        return Arrays.stream(values())
                .filter(item -> item.getCode().equals(errorCode))
                .findFirst()
                .orElse(UNKNOWN_ERROR);
    }
}
