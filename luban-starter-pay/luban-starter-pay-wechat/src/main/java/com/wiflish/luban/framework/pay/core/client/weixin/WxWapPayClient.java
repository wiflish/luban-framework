package com.wiflish.luban.framework.pay.core.client.weixin;

import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderRespDTO;
import com.wiflish.luban.framework.pay.core.client.dto.order.PayOrderUnifiedReqDTO;
import com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum;
import com.wiflish.luban.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import com.github.binarywang.wxpay.bean.order.WxPayMwebOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信支付（H5 网页）
 * 文档：<a href="https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_3_1.shtml">H5下单API</>
 *
 * @author wiflish
 * @since 2024-07-22
 */
@Slf4j
public class WxWapPayClient extends AbstractWxPayClient {

    public WxWapPayClient(Long channelId, WxPayClientConfig config) {
        super(channelId, PayChannelEnum.WX_WAP.getCode(), config);
    }

    protected WxWapPayClient(Long channelId, String channelCode, WxPayClientConfig config) {
        super(channelId, channelCode, config);
    }

    @Override
    protected void doInit() {
        super.doInit(WxPayConstants.TradeType.MWEB);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrderV2(PayOrderUnifiedReqDTO reqDTO) throws WxPayException {
        // 构建 WxPayUnifiedOrderRequest 对象
        WxPayUnifiedOrderRequest request = buildPayUnifiedOrderRequestV2(reqDTO);
        // 执行请求
        WxPayMwebOrderResult response = client.createOrder(request);

        // 转换结果
        return PayOrderRespDTO.waitingOf(PayOrderDisplayModeEnum.URL.getMode(), response.getMwebUrl(),
                reqDTO.getOutTradeNo(), response);
    }

    @Override
    protected PayOrderRespDTO doUnifiedOrderV3(PayOrderUnifiedReqDTO reqDTO) throws WxPayException {
        // 构建 WxPayUnifiedOrderRequest 对象
        WxPayUnifiedOrderV3Request request = buildPayUnifiedOrderRequestV3(reqDTO);
        // 执行请求
        String response = client.createOrderV3(TradeTypeEnum.H5, request);

        // 转换结果
        return PayOrderRespDTO.waitingOf(PayOrderDisplayModeEnum.URL.getMode(), response,
                reqDTO.getOutTradeNo(), response);
    }

}
