package com.wiflish.luban.framework.pay.ezeelink.client.payout;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.wiflish.luban.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import com.wiflish.luban.framework.pay.ezeelink.client.EzeelinkAbstractPayClient;
import com.wiflish.luban.framework.pay.ezeelink.client.EzeelinkPayClientConfig;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkBankDTO;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkBankTransferDTO;
import com.wiflish.luban.framework.pay.ezeelink.dto.EzeelinkResp;
import com.wiflish.luban.framework.pay.ezeelink.enums.EzeelinkApiEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Ezeelink 通过银行付款client
 *
 * @author wiflish
 * @since 2024-10-04
 */
@Slf4j
public class EzeelinkPayoutBankPayClient extends EzeelinkAbstractPayClient {
    public EzeelinkPayoutBankPayClient(Long channelId, String channelCode, EzeelinkPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    protected PayTransferRespDTO doUnifiedTransfer(PayTransferUnifiedReqDTO reqDTO) throws Throwable {
        EzeelinkBankDTO bankDTO = new EzeelinkBankDTO();
        bankDTO.setBankCode(config.getPayoutChannelCode())

                .setAccountName(reqDTO.getUserName()).setAccountNumber(reqDTO.getBankAccount())
                .setTransferAmount(String.valueOf(reqDTO.getPrice()))
                .setPartnerTransId(reqDTO.getOutTransferNo());

        EzeelinkBankTransferDTO transReq = new EzeelinkBankTransferDTO();
        transReq.setAccounts(List.of(bankDTO)).setPartnerId(config.getPartnerId()).setSubPartnerId(config.getSubPartnerId());

        // 发起转账
        try {
            EzeelinkResp<EzeelinkBankTransferDTO> resp = ezeelinkInvoker.request(config.getBaseUrl() + EzeelinkApiEnum.PAYMENT_API_MANUALTRANSFER.getApi(), HttpMethod.POST, config.getApiKey(), config.getApiSecret(), transReq, new TypeReference<>() {
            });

            // 处理返回结果
            log.info("发起转账调用成功, 渠道: ezeelink , resp: {} ", JSON.toJSONString(resp));
            EzeelinkBankDTO result = resp.getResult().getAccounts().getFirst();
            if (result.getStatus().equals("Success")) {
                return PayTransferRespDTO.successOf(result.getTransactionCode(), LocalDateTime.now(), result.getPartnerTransId(), resp);
            } else if (result.getStatus().equals("Failed")) {
                return PayTransferRespDTO.closedOf(result.getStatus(), result.getMessage(), result.getPartnerTransId(), resp);
            } else {
                return PayTransferRespDTO.dealingOf(result.getTransactionCode(), result.getPartnerTransId(), resp);
            }
        } catch (HttpClientErrorException e) {
            log.error("发起转账调用失败, 渠道: ezeelink , req: {} ", JSON.toJSONString(transReq), e);
            String responseBodyAsString = e.getResponseBodyAsString();
            JSONObject jsonObject = JSON.parseObject(responseBodyAsString);

            return PayTransferRespDTO.closedOf(jsonObject.getString("error_code"), jsonObject.getString("error_message"),
                    reqDTO.getOutTransferNo(), jsonObject);
        }
    }
}
