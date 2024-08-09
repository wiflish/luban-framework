package com.wiflish.luban.framework.pay.core.client.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ReflectUtil;
import com.wiflish.luban.framework.pay.core.client.PayChannelConfig;
import com.wiflish.luban.framework.pay.core.client.PayClient;
import com.wiflish.luban.framework.pay.core.client.PayClientConfig;
import com.wiflish.luban.framework.pay.core.client.PayClientFactory;
import com.wiflish.luban.framework.pay.core.client.impl.mock.MockPayClient;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.wiflish.luban.framework.pay.core.enums.channel.PayChannelEnum.MOCK;

/**
 * 支付客户端的工厂实现类
 *
 * @author 芋道源码
 */
@Slf4j
public class PayClientFactoryImpl implements PayClientFactory {
    private final List<PayChannelConfig> payChannelConfigs;

    /**
     * 支付客户端 Map
     * <p>
     * key：渠道编号
     */
    private final ConcurrentMap<Long, AbstractPayClient<?>> clients = new ConcurrentHashMap<>();

    /**
     * 支付客户端 Class Map
     */
    private final Map<String, Class<?>> clientClass = new ConcurrentHashMap<>();

    public PayClientFactoryImpl(List<PayChannelConfig> payChannelConfigs) {
        this.payChannelConfigs = payChannelConfigs;
        // Mock 支付客户端
        clientClass.put(MOCK.getCode(), MockPayClient.class);
        payChannelConfigs.forEach(config -> registerPayClientClass(config.getChannelCode(), config.getPayClient()));
    }

    @Override
    public void registerPayClientClass(String channelCode, Class<?> payClientClass) {
        clientClass.put(channelCode, payClientClass);
    }

    @Override
    public PayChannelConfig getPayChannelConfig(String code) {
        return payChannelConfigs.stream().filter(config -> config.getChannelCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public PayClient getPayClient(Long channelId) {
        AbstractPayClient<?> client = clients.get(channelId);
        if (client == null) {
            log.error("[getPayClient][渠道编号({}) 找不到客户端]", channelId);
        }
        return client;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <Config extends PayClientConfig> void createOrUpdatePayClient(Long channelId, String channelCode,
                                                                         Config config) {
        AbstractPayClient<Config> client = (AbstractPayClient<Config>) clients.get(channelId);
        if (client == null) {
            client = this.createPayClient(channelId, channelCode, config);
            client.init();
            clients.put(client.getId(), client);
        } else {
            client.refresh(config);
        }
    }

    @SuppressWarnings("unchecked")
    private <Config extends PayClientConfig> AbstractPayClient<Config> createPayClient(Long channelId, String channelCode,
                                                                                       Config config) {
        Class<?> payClientClass = clientClass.get(channelCode);
        Assert.notNull(payClientClass, String.format("支付渠道(%s) Class 为空", channelCode));
        return (AbstractPayClient<Config>) ReflectUtil.newInstance(payClientClass, channelId, channelCode, config);
    }
}
