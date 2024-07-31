package com.wiflish.luban.framework.pay.core.client.dto.order;

import lombok.Data;

/**
 * 模拟支付 Response DTO
 *
 * @author wiflish
 * @since 2024-07-31
 */
@Data
public class SimulatePayRespDTO {
    /**
     * The status of the request. If it successfully being processed then the status will be PENDING.
     */
    private String status;

    /**
     * Additional information regarding the payment simulation process
     */
    private String message;
}
