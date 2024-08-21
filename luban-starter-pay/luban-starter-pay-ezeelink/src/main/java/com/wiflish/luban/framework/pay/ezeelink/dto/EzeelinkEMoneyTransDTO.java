package com.wiflish.luban.framework.pay.ezeelink.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author wiflish
 * @since 2024-08-21
 */
@Data
public class EzeelinkEMoneyTransDTO extends EzeelinkBaseDTO {
    @JSONField(name = "accounts")
    private List<EzeelinkAccountDTO> accounts;
}
