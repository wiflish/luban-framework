package com.wiflish.luban.framework.pay.ezeelink.dto;

import lombok.Data;

/**
 * @author wiflish
 * @since 2024-08-08
 */
@Data
public class EzeelinkResp<T> {
    private String message;
    private T result;
}
