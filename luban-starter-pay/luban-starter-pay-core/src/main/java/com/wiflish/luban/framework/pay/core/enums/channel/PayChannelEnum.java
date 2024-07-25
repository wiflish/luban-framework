package com.wiflish.luban.framework.pay.core.enums.channel;

import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付渠道的编码的枚举
 *
 * @author wiflish
 * @since 2024-07-22
 */
@Getter
@AllArgsConstructor
public enum PayChannelEnum {
    /**
     * 微信 JSAPI 支付
     */
    WX_PUB("wx_pub", "payChannel.name.wx_pub"),
    /**
     * 微信小程序支付
     */
    WX_LITE("wx_lite", "payChannel.name.wx_lite"),
    /**
     * 微信 App 支付
     */
    WX_APP("wx_app", "payChannel.name.wx_app"),
    /**
     * 微信 Native 支付
     */
    WX_NATIVE("wx_native", "payChannel.name.wx_native"),
    /**
     * 微信 Wap 网站支付
     */
    WX_WAP("wx_wap", "payChannel.name.wx_wap"),
    /**
     * 微信付款码支付
     */
    WX_BAR("wx_bar", "payChannel.name."),

    /**
     * 支付宝 PC 网站支付
     */
    ALIPAY_PC("alipay_pc", "payChannel.name.alipay_pc"),
    /**
     * 支付宝 Wap 网站支付
     */
    ALIPAY_WAP("alipay_wap", "payChannel.name.alipay_wap"),
    /**
     * 支付宝 App 支付
     */
    ALIPAY_APP("alipay_app", "payChannel.name.alipay_app"),
    /**
     * 支付宝 扫码支付
     */
    ALIPAY_QR("alipay_qr", "payChannel.name.alipay_qr"),
    /**
     * 支付宝 条码支付
     */
    ALIPAY_BAR("alipay_bar", "payChannel.name.alipay_bar"),

    /**
     * Xendit invoice支付
     */
    XENDIT_INVOICE("xendit_invoice", "payChannel.name.xenditInvoice"),

    /**
     * xendit_eWallet_ovo支付
     */
    XENDIT_E_WALLET_OVO("xendit_eWallet_ovo", "payChannel.name.xenditEWalletOvo"),

    /**
     * 模拟支付
     */
    MOCK("mock", "payChannel.name.mock"),

    /**
     * 钱包支付
     */
    WALLET("wallet", "payChannel.name.wallet");

    /**
     * 编码
     *
     * 参考 <a href="https://www.pingxx.com/api/支付渠道属性值.html">支付渠道属性值</a>
     */
    private final String code;
    /**
     * 名字
     */
    private final String name;

    /**
     * 微信支付
     */
    public static final String WECHAT = "WECHAT";

    /**
     * 支付宝支付
     */
    public static final String ALIPAY = "ALIPAY";

    public static PayChannelEnum getByCode(String code) {
        return ArrayUtil.firstMatch(o -> o.getCode().equals(code), values());
    }

    public static boolean isAlipay(String channelCode) {
        return channelCode != null && channelCode.startsWith("alipay");
    }
}
