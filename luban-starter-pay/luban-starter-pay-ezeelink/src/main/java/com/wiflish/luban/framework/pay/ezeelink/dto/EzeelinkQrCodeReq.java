package com.wiflish.luban.framework.pay.ezeelink.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-08-08
 */
@Data
public class EzeelinkQrCodeReq extends EzeelinkBaseDTO {
    @JSONField(name = "currency")
    private String currency;
    @JSONField(name = "store_ext_id")
    private String storeExtId;
    @JSONField(name = "terminal_id")
    private String terminalId;
    @JSONField(name = "bill_description")
    private String billDescription;
    @JSONField(name = "expiry_time")
    private Integer expiryTime;

    /**
     * 01 - User inputs free amount
     * 02 - Convenience fee is a fixed value, to be indicated in convenience_fee_fixed
     * 03 - Convenience fee is based on percentage of payment, to be indicated in convenience_fee_percentage
     */
    @JSONField(name = "convenience_fee_indicator")
    private String convenienceFeeIndicator;

    /**
     * convenienceFeeIndicator = 02时，必填
     */
    @JSONField(name = "convenience_fee_fixed")
    private Long convenienceFeeFixed;

    /**
     * convenienceFeeIndicator = 03时，必填
     * Format: “00.00” example “01.00”
     */
    @JSONField(name = "convenience_fee_percentage")
    private String convenienceFeePercentage;
}
