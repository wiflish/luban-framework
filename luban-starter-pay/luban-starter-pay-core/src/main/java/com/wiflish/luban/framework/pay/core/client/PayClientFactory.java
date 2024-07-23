package com.wiflish.luban.framework.pay.core.client;

/**
 * 支付客户端的工厂接口
 *
 * @author 芋道源码
 */
public interface PayClientFactory {
    /**
     * 获得支付渠道配置
     *
     * @param code 渠道编码.
     * @return 渠道配置
     */
    PayChannelConfig getPayChannelConfig(String code);

    /**
     * 获得支付客户端
     *
     * @param channelId 渠道编号
     * @return 支付客户端
     */
     PayClient getPayClient(Long channelId);

    /**
     * 创建支付客户端
     *
     * @param channelId 渠道编号
     * @param channelCode 渠道编码
     * @param config 支付配置
     */
    <Config extends PayClientConfig> void createOrUpdatePayClient(Long channelId, String channelCode,
                                                                  Config config);

    /**
     * 注册支付客户端 Class，用于模块中实现的 PayClient
     *
     * @param channelCode 支付渠道的编码
     * @param payClientClass 支付客户端 class
     */
    void registerPayClientClass(String channelCode, Class<?> payClientClass);

}
