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
     * Xendit invoice 支付
     */
    XENDIT_INVOICE("xendit_invoice", "payChannel.name.xenditInvoice"),

    /**
     * xendit_eWallet_ovo 支付
     */
    XENDIT_E_WALLET_OVO("xendit_eWallet_ovo", "payChannel.name.xenditEWalletOvo"),

    /**
     * xendit_eWallet_dana 支付
     */
    XENDIT_E_WALLET_DANA("xendit_eWallet_dana", "payChannel.name.xenditEWalletDana"),

    /**
     * xendit_eWallet_linkaja 支付
     */
    XENDIT_E_WALLET_LINKAJA("xendit_eWallet_linkaja", "payChannel.name.xenditEWalletLinkAja"),

    /**
     * xendit_eWallet_astrapay 支付
     */
    XENDIT_E_WALLET_ASTRAPAY("xendit_eWallet_astrapay", "payChannel.name.xenditEWalletAstrapay"),

    /**
     * xendit_eWallet_jeniuspay 支付
     */
    XENDIT_E_WALLET_JENIUSPAY("xendit_eWallet_jeniuspay", "payChannel.name.xenditEWalletJeniuspay"),

    /**
     * xendit_eWallet_shopeepay 支付
     */
    XENDIT_E_WALLET_SHOPEEPAY("xendit_eWallet_shopeepay", "payChannel.name.xenditEWalletJeniuspay"),

    /**
     * xendit_card 支付，包含：credit_card / debit_card
     */
    XENDIT_CARD("xendit_card", "payChannel.name.xenditCard"),

    /**
     * xendit_va_bca 支付
     */
    XENDIT_VA_BCA("xendit_va_bca", "payChannel.name.xenditVABca"),

    /**
     * xendit_va_bsi 支付
     */
    XENDIT_VA_BSI("xendit_va_bsi", "payChannel.name.xenditVABsi"),

    /**
     * xendit_va_bjb 支付
     */
    XENDIT_VA_BJB("xendit_va_bjb", "payChannel.name.xenditVABjb"),

    /**
     * xendit_va_cimb 支付
     */
    XENDIT_VA_CIMB("xendit_va_cimb", "payChannel.name.xenditVACimb"),

    /**
     * xendit_va_sahabat_sampoerna 支付
     */
    XENDIT_VA_SAHABAT_SAMPOERNA("xendit_va_sahabat_sampoerna", "payChannel.name.xenditVASahabatSampoerna"),

    /**
     * xendit_va_artajasa 支付，该银行不支持转账了。
     */
    XENDIT_VA_ARTAJASA("xendit_va_artajasa", "payChannel.name.xenditVAArtajasa"),

    /**
     * xendit_va_bri 支付
     */
    XENDIT_VA_BRI("xendit_va_bri", "payChannel.name.xenditVABri"),

    /**
     * xendit_va_bni 支付
     */
    XENDIT_VA_BNI("xendit_va_bni", "payChannel.name.xenditVABni"),

    /**
     * xendit_va_mandiri 支付
     */
    XENDIT_VA_MANDIRI("xendit_va_mandiri", "payChannel.name.xenditVAMandiri"),

    /**
     * xendit_va_permata 支付
     */
    XENDIT_VA_PERMATA("xendit_va_permata", "payChannel.name.xenditVAPermata"),

    /**
     * xendit_qr_code 支付
     */
    XENDIT_QR_CODE("xendit_qr_code", "payChannel.name.xenditQrCode"),

    /**
     * ezeelink_eWallet_ovo 支付
     */
    EZEELINK_E_WALLET_OVO("ezeelink_eWallet_ovo", "payChannel.name.ezeelinkEWalletOvo"),

    /**
     * ezeelink_eWallet_dana 支付
     */
    EZEELINK_E_WALLET_DANA("ezeelink_eWallet_dana", "payChannel.name.ezeelinkEWalletDana"),

    /**
     * ezeelink_eWallet_gopay 支付
     */
    EZEELINK_E_WALLET_GOPAY("ezeelink_eWallet_gopay", "payChannel.name.ezeelinkEWalletGopay"),

    /**
     * ezeelink_va_bca 支付
     */
    EZEELINK_VA_BCA("ezeelink_va_bca", "payChannel.name.ezeelinkVABca"),

    /**
     * ezeelink_va_mandiri 支付
     */
    EZEELINK_VA_MANDIRI("ezeelink_va_mandiri", "payChannel.name.ezeelinkVAMandiri"),

    /**
     * ezeelink_qr_code 支付
     */
    EZEELINK_QR_CODE("ezeelink_qr_code", "payChannel.name.ezeelinkQrCode"),

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
