package com.wiflish.luban.framework.pay.ezeelink.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.wiflish.luban.framework.pay.ezeelink.enums.EzeelinkAccountStatusEnum;
import lombok.Data;

/**
 * @author wiflish
 * @since 2024-08-21
 */
@Data
public class EzeelinkAccountDTO {
    @JSONField(name = "customerphonenumber")
    private String customerPhoneNumber;
    @JSONField(name = "ewallet_code")
    private String ewalletCode;
    @JSONField(name = "transfer_amount")
    private String transferAmount;
    @JSONField(name = "partner_trans_id")
    private String partnerTransId;
    /**
     * {@link EzeelinkAccountStatusEnum }
     */
    @JSONField(name = "status")
    private String status;

}
