package com.wiflish.luban.framework.pay.core.client.xendit;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.wiflish.luban.framework.common.exception.enums.GlobalErrorCodeConstants.INVOKER_ERROR;
import static com.wiflish.luban.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * @author wiflish
 * @since 2024-07-31
 */
@Slf4j
@RequiredArgsConstructor
public class XenditInvoker {
    private final RestTemplate restTemplate;

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
    public <Req, Resp> Resp request(String url, HttpMethod httpMethod, String apiKey, Req req, Class<Resp> respClass) {
        // 请求头
        HttpHeaders headers = getHeaders(apiKey, req);
        String requestBody = JSON.toJSONString(req);

        // 发送请求
        log.info("[xenditRequest][请求参数({})]", requestBody);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, String.class);
        log.info("[xenditRequest][响应结果({})]", responseEntity);
        // 处理响应
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw exception(INVOKER_ERROR);
        }
        return JSON.parseObject(responseEntity.getBody(), respClass);
    }

    private static <Req> HttpHeaders getHeaders(String apiKey, Req req) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setBasicAuth(apiKey, "");

        Object referenceId = ReflectUtil.getFieldValue(req, "referenceId");
        if (referenceId != null) {
            headers.add("idempotency-key", referenceId.toString());
        }

        return headers;
    }
}
