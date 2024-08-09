package com.wiflish.luban.framework.pay.ezeelink.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-08-08
 */
@Data
public class EzeelinkOVOResp {
    @JSONField(name = "webview_link")
    private String webviewLink;

    @JSONField(name = "transaction_code")
    private String transactionCode;
}
