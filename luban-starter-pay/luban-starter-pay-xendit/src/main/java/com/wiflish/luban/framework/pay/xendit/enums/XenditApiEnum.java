package com.wiflish.luban.framework.pay.xendit.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author wiflish
 * @since 2024-09-23
 */
@Getter
@RequiredArgsConstructor
public enum XenditApiEnum {
    PAYOUT_API("/v2/payouts", "统一的转账API，不能原卡原退的转账；付佣金。"),
    PAYMENT_REQUEST_API("/payment_requests", "统一的付款API"),
    REFUND_API("/refunds", "统一的退款API，当不能原卡原退时，需要使用转账API"),
    SIMULATE_VA_TEMPLATE_API("/v2/payment_methods/%s/payments/simulate", "虚拟账户模拟支付"),
    ;

    private final String api;
    private final String desc;
}
