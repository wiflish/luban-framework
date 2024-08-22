package com.wiflish.luban.framework.pay.xendit.dto.payment;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author wiflish
 * @since 2024-08-22
 */
@Data
public class PaymentGetResponseDTO {
    private List<PaymentResponseDTO> data;
    @JSONField(name = "has_more")
    private Boolean hasMore;
}