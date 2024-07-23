package com.wiflish.luban.framework.pay.core.client.xendit;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.refund.PayRefundUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.dto.transfer.PayTransferRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.transfer.PayTransferUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.client.impl.AbstractPayClient;
import com.wiflish.luban.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import com.wiflish.luban.framework.pay.core.enums.order.PayOrderStatusRespEnum;
import com.wiflish.luban.framework.pay.core.enums.transfer.PayTransferTypeEnum;
import com.wiflish.luban.framework.pay.xendit.dto.PaymentRefundDTO;
import com.xendit.XenditClient;
import com.xendit.model.Invoice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_FORMATTER;
import static com.wiflish.luban.framework.common.exception.enums.GlobalErrorCodeConstants.INVOKER_ERROR;
import static com.wiflish.luban.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum.XENDIT_INVOICE;

/**
 * Xendit支付Client
 *
 * @author wiflish
 * @since 2024-07-17
 */
@Slf4j
public class XenditInvoicePayClient extends AbstractPayClient<XenditPayClientConfig> {
    private static final String XENDIT_PAYOUT_URL = "https://api.xendit.co/v2/payouts";
    private static final String XENDIT_REFUND_URL = "https://api.xendit.co/refunds";
    private XenditClient xenditClient;
    private RestTemplate restTemplate;

    public XenditInvoicePayClient(Long channelId, XenditPayClientConfig config) {
        super(channelId, XENDIT_INVOICE.getCode(), config);
    }

    public XenditInvoicePayClient(Long channelId, String channelCode, XenditPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    protected void doInit() {
        xenditClient = new XenditClient.Builder().setApikey(config.getApiKey()).build();
        restTemplate = SpringUtil.getBean(RestTemplate.class);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrder(PayOrderUnifiedReqDTO reqDTO) throws Throwable {
        Map<String, Object> params = new HashMap<>();
        params.put("external_id", reqDTO.getOutTradeNo());
        params.put("amount", reqDTO.getPrice() / 100);
        // FIXME 先写死, 后续改为读系统配置.
        params.put("currency", "IDR");
        params.put("description", reqDTO.getSubject());

        Invoice invoice = xenditClient.invoice.create(params);
        log.info("发起支付, 渠道: xendit , req: {}, resp: {}", JSON.toJSONString(params), JSON.toJSONString(invoice));

        PayOrderRespDTO respDTO = new PayOrderRespDTO();

        respDTO.setChannelOrderNo(invoice.getId());
        respDTO.setDisplayMode(PayOrderDisplayModeEnum.IFRAME.getMode());
        respDTO.setDisplayContent(invoice.getInvoiceUrl());

        return respDTO;
    }


    /**
     * 2. Handle the Callback for Successful Payment
     * https://docs.xendit.co/create-a-checkout-page-via-api
     * https://developers.xendit.co/api-reference/#invoice-callback
     * Once the Invoice is paid by the customer, Xendit will send you a callback notifying successful payment. In this step, you will define a function to handle this callback sent from Xendit.
     * In the script below, a simple express server serves a POST endpoint /receive_callback. This is the endpoint Xendit will send the callback to.
     *
     * @param params
     * @param body
     * @return
     * @throws Throwable
     */
    @Override
    protected PayOrderRespDTO doParseOrderNotify(Map<String, String> params, String body) throws Throwable {
        // 1.校验请求合法性
        // 2.幂等处理
        // 3.处理boby
        Map<String, String> bodyObj = HttpUtil.decodeParamMap(body, StandardCharsets.UTF_8);
        Integer status = parseStatus(bodyObj.get("status"));
        Assert.notNull(status, (Supplier<Throwable>) () -> {
            throw new IllegalArgumentException(StrUtil.format("body({}) 的 trade_status 不正确", body));
        });
        return PayOrderRespDTO.of(status, bodyObj.get("external_id"), bodyObj.get("user_id"), parseTime(params.get("paid_at")),
                bodyObj.get("out_trade_no"), body);
    }

    private static Integer parseStatus(String tradeStatus) {
        return Objects.equals("PAID", tradeStatus) ? PayOrderStatusRespEnum.SUCCESS.getStatus()
                : Objects.equals("EXPIRED", tradeStatus) ? PayOrderStatusRespEnum.CLOSED.getStatus() : null;
    }

    protected LocalDateTime parseTime(String str) {
        return LocalDateTimeUtil.parse(str, NORM_DATETIME_FORMATTER);
    }

    @Override
    protected PayOrderRespDTO doGetOrder(String outTradeNo) throws Throwable {
        return null;
    }

    @Override
    protected PayRefundRespDTO doUnifiedRefund(PayRefundUnifiedReqDTO reqDTO) {
//        Payout payout = xenditClient.payout.createPayout(reqDTO.getOutTradeNo(), reqDTO.getRefundPrice() / 100);
//        payout.setResponseHeaders(null); //不需要打印请求头.

        PaymentRefundDTO paymentRefundDTO = httpRequest(XENDIT_REFUND_URL, HttpMethod.POST, config.getApiKey(), reqDTO, PaymentRefundDTO.class);
//        PayoutDTO payoutDTO = httpRequest(XENDIT_PAYOUT_URL, HttpMethod.POST, config.getApiKey(), reqDTO, PayoutDTO.class);

        return PayRefundRespDTO.waitingOf(paymentRefundDTO.getId(), reqDTO.getOutRefundNo(), paymentRefundDTO);
    }

    @Override
    protected PayRefundRespDTO doParseRefundNotify(Map<String, String> params, String body) throws Throwable {
        return null;
    }

    @Override
    protected PayRefundRespDTO doGetRefund(String outTradeNo, String outRefundNo) throws Throwable {
        return null;
    }

    @Override
    protected PayTransferRespDTO doUnifiedTransfer(PayTransferUnifiedReqDTO reqDTO) throws Throwable {
        return null;
    }

    @Override
    protected PayTransferRespDTO doGetTransfer(String outTradeNo, PayTransferTypeEnum type) throws Throwable {
        return null;
    }

    /**
     * API 请求
     *
     * @param url       请求 url
     * @param apiKey    签名 key
     * @param req       对应请求的请求参数
     * @param respClass 对应请求的响应 class
     * @param <Req>     每个请求的请求结构 Req DTO
     * @param <Resp>    每个请求的响应结构 Resp DTO
     */
    private <Req, Resp> Resp httpRequest(String url, HttpMethod httpMethod, String apiKey, Req req, Class<Resp> respClass) {
        // 请求头
        HttpHeaders headers = getHeaders(apiKey);
        Object requestObj = null;
        if (req instanceof PayRefundUnifiedReqDTO xenditReq) {
            headers.add("idempotency-key", UUID.fastUUID().toString());

            Map<String, Object> params = new HashMap<>();
            params.put("reference_id", xenditReq.getOutTradeNo());
            params.put("invoice_id", xenditReq.getChannelOrderNo());
            params.put("amount", xenditReq.getRefundPrice() / 100);
            params.put("currency", "IDR"); //FIXME 先写死, 后续改为读系统配置.
            requestObj = JSON.toJSONString(params);
        }

        // 发送请求
        log.debug("[xenditRequest][请求参数({})]", requestObj);
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestObj, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, String.class);
        log.debug("[xenditRequest][响应结果({})]", responseEntity);
        // 处理响应
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw exception(INVOKER_ERROR);
        }
        return JSON.parseObject(responseEntity.getBody(), respClass);
    }

    private static HttpHeaders getHeaders(String apiKey) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        // Override apiKey with Authorization key
        // Can be used as workaround for https://github.com/xendit/xendit-java/issues/30
//        String base64Key = encodeBase64(apiKey + ":");
//        String authorization = customHeaders.getOrDefault("Authorization", "Basic " + base64Key);
        headers.setBasicAuth(apiKey, "");

        return headers;
    }

    private static String encodeBase64(String key) {
        return Base64.encode(key.getBytes(StandardCharsets.UTF_8));
    }

}