package com.wiflish.luban.framework.pay.core.client.xendit.payout;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPayClientConfig;
import com.wiflish.luban.framework.pay.core.client.xendit.XenditPaymentAbstractPayClient;
import com.wiflish.luban.framework.pay.xendit.dto.PayoutDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.ChannelPropertiesDTO;
import com.wiflish.luban.framework.pay.xendit.dto.payment.PaymentMethodDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpClientErrorException;

import static com.wiflish.luban.framework.pay.xendit.enums.XenditApiEnum.PAYOUT_API;

/**
 * Xendit支付Client
 *
 * @author wiflish
 * @since 2024-07-17
 */
@Slf4j
public class XenditPayoutBankPayClient extends XenditPaymentAbstractPayClient {
    public XenditPayoutBankPayClient(Long channelId, String channelCode, XenditPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    protected PayTransferRespDTO doUnifiedTransfer(PayTransferUnifiedReqDTO reqDTO) throws Throwable {
        PayoutDTO payoutRespDTO = new PayoutDTO();
        PayoutDTO payoutReqDTO = new PayoutDTO();
        ChannelPropertiesDTO channelProperties = new ChannelPropertiesDTO();
        channelProperties.setAccountHolderName(reqDTO.getUserName()).setAccountNumber(reqDTO.getBankAccount());

        payoutReqDTO.setReferenceId(reqDTO.getOutTransferNo()).setChannelCode(config.getPayoutChannelCode()).setChannelProperties(channelProperties)
                .setAmount(getActualPayAmount(reqDTO.getPrice())).setCurrency(reqDTO.getCurrency());
        try {
            payoutRespDTO = xenditInvoker.request(config.getBaseUrl() + PAYOUT_API.getApi(), HttpMethod.POST, config.getApiKey(), payoutReqDTO, PayoutDTO.class);
        } catch (HttpClientErrorException e) {
            log.error("发起转账调用失败, 渠道: xenditInvoice.payout , req: {}, resp: {}", JSON.toJSONString(payoutReqDTO), JSON.toJSONString(payoutRespDTO), e);
            String responseBodyAsString = e.getResponseBodyAsString();
            JSONObject jsonObject = JSON.parseObject(responseBodyAsString);
            PayTransferRespDTO payTransferRespDTO = PayTransferRespDTO.waitingOf(null, reqDTO.getOutTransferNo(), responseBodyAsString);
            payTransferRespDTO.setChannelErrorCode(jsonObject.getString("error_code")).setChannelErrorMsg(jsonObject.getString("message"));
            return payTransferRespDTO;
        }

        return PayTransferRespDTO.dealingOf(payoutRespDTO.getId(), reqDTO.getOutTransferNo(), payoutRespDTO);
    }

    @Override
    protected PaymentMethodDTO getPaymentMethod(PayOrderUnifiedReqDTO reqDTO) {
        return null;
    }

    @Override
    protected ChannelPropertiesDTO channelProperties(PayOrderUnifiedReqDTO reqDTO) {
        return null;
    }
}
