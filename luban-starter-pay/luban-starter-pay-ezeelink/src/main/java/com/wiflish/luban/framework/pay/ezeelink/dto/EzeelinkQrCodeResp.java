package com.wiflish.luban.framework.pay.ezeelink.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-08-08
 */
@Data
public class EzeelinkQrCodeResp extends EzeelinkBaseDTO {
    /**
     * 2 = Success/Paid
     * 6 = Payment Expired
     * 5 = Waiting for payment
     */
    @JSONField(name = "status_id")
    private Integer statusId;
    @JSONField(name = "status_name")
    private String statusName;
    @JSONField(name = "qr_content")
    private String qrContent;
    @JSONField(name = "qr_url")
    private String qrUrl;
    @JSONField(name = "store_name")
    private String storeName;
    @JSONField(name = "store_ext_id")
    private String storeExtId;
    @JSONField(name = "terminal_id")
    private String terminalId;
}
