package com.wiflish.luban.framework.pay.ezeelink.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-08-08
 */
@Data
public class EzeelinkVAResp extends EzeelinkBaseDTO {
    @JSONField(name = "bank_code")
    private String bankCode;
    @JSONField(name = "channel_name")
    private String channelName;
    @JSONField(name = "va_number")
    private String vaNumber;
    @JSONField(name = "bill_description")
    private String billDescription;
    @JSONField(name = "paymentStatus")
    private String paymentStatus;
    @JSONField(name = "status_id")
    private String statusId;
}
