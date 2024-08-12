package com.wiflish.luban.framework.pay.ezeelink.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import org.springframework.http.HttpMethod;

import java.nio.charset.StandardCharsets;

/**
 * @author wiflish
 * @since 2024-08-09
 */
public class EzeelinkUtil {
    public static String getSignature(HttpMethod httpMethod, String url, String apiSecret, String timestamp, String requestBody) {
        String strToSign = httpMethod.name() + ":" + getRelativeUrl(url) +
                ":" + DigestUtil.sha256Hex(StrUtil.removeAll(requestBody, " ")).toLowerCase() +
                ":" + timestamp;

        HMac hmac = DigestUtil.hmac(HmacAlgorithm.HmacSHA256, apiSecret.getBytes(StandardCharsets.UTF_8));

        return hmac.digestHex(strToSign);
    }

    /**
     * 长度限制为10，否则会超出。
     *
     * @param subject 订单描述.
     * @return 订单描述.
     */
    public static String buildBillDescription(String subject) {
        if (subject == null || subject.length() <= 10) {
            return subject;
        }
        return subject.substring(0, 10);
    }

    public static String getRelativeUrl(String url) {
        if (!url.contains("//")) {
            return url;
        }
        String relativeUrl = url.substring(url.indexOf("//") + 2);
        return relativeUrl.substring(relativeUrl.indexOf("/"));
    }
}
