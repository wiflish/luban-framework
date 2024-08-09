package com.wiflish.luban.framework.pay.ezeelink.dto.event;

import com.alibaba.fastjson2.annotation.JSONField;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkBaseDTO;
import com.wiflish.luban.framework.pay.ezeelink.enums.EzeelinkPaymentStatusEnum;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-08-09
 */
@Data
public class VirtualAccountEventDTO extends EzeelinkBaseDTO {
    @JSONField(name = "bank_code")
    private String bankCode;
    @JSONField(name = "channel_name")
    private String channelName;
    @JSONField(name = "va_number")
    private String vaNumber;
    @JSONField(name = "bill_description")
    private String billDescription;
    /**
     * @see EzeelinkPaymentStatusEnum
     */
    @JSONField(name = "status_id")
    private String statusId;
    @JSONField(name = "payment_status")
    private String paymentStatus;
}
