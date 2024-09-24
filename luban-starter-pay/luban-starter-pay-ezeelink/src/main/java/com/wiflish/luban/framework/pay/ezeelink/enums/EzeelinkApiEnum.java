package com.wiflish.luban.framework.pay.ezeelink.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wiflish
 * @since 2024-08-21
 */
@Getter
@AllArgsConstructor
public enum EzeelinkApiEnum {
    TRANSFER_API_EMONEY("/ezpaygateway/api/v1/emoney/disburse", "电子钱包转账API，可以退款"),
    PAYMENT_API_ACCOUNT_BIND("/ezpaygateway/api/v1/ovo/payment/bindaccount", "绑定账户"),
    PAYMENT_API_ACCOUNT_LOOKUP("/ezpaygateway/api/v1/ovo/payment/lookupaccount", "查询账户是否已绑定"),
    PAYMENT_API_MANUALTRANSFER("/ezpaygateway/api/v1/vapg/manualtransfer", "银行转账"),
    ;

    private final String api;
    private final String desc;
}
