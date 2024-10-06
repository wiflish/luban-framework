package com.wiflish.luban.framework.pay.ezeelink.dto.event;

import com.alibaba.fastjson2.annotation.JSONField;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkQrCodeResp;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-08-08
 */
@Data
public class QrCodeEventDTO extends EzeelinkQrCodeResp {
    @JSONField(name = "initialization_qris")
    private Integer initializationQris;

    @JSONField(name = "reference_number")
    private String referenceNumber;
}
