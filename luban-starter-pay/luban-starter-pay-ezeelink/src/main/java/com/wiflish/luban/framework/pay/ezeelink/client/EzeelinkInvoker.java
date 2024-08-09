package com.wiflish.luban.framework.pay.ezeelink.client;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.wiflish.luban.framework.common.util.date.LocalDateTimeUtils;
import com.wiflish.luban.framework.pay.ezeelink.util.SignatureUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static com.wiflish.luban.framework.common.exception.enums.GlobalErrorCodeConstants.INVOKER_ERROR;
import static com.wiflish.luban.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.wiflish.luban.framework.pay.ezeelink.enums.EzeelinkHeaderEnum.*;

/**
 * @author wiflish
 * @since 2024-08-08
 */
@Slf4j
@RequiredArgsConstructor
public class EzeelinkInvoker {
    private final RestTemplate restTemplate;

    /**
     * API 请求
     *
     * @param url    请求 url
     * @param apiKey 签名 key
     * @param req    对应请求的请求参数
     * @param <Req>  每个请求的请求结构 Req DTO
     * @param <Resp> 每个请求的响应结构 Resp DTO
     */
    public <Req, Resp> Resp request(String url, HttpMethod httpMethod, String apiKey, String apiSecret, Req req, TypeReference<Resp> respClz) {
        String requestBody = JSON.toJSONString(req);

        String timestamp = LocalDateTimeUtils.beijing2ISO8601(LocalDateTime.now());
        String signature = SignatureUtil.getSignature(httpMethod, url, apiSecret, timestamp, requestBody);

        // 请求头
        HttpHeaders headers = getHeaders(apiKey, signature, timestamp);

        // 发送请求
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        log.info("[ezeelinkRequest][请求参数({})]", requestEntity);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, httpMethod, requestEntity, String.class);
        log.info("[ezeelinkRequest][响应结果({})]", responseEntity);
        // 处理响应
        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw exception(INVOKER_ERROR);
        }
        return JSON.parseObject(responseEntity.getBody(), respClz);
    }

    private static HttpHeaders getHeaders(String apiKey, String signature, String timestamp) {
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(API_KEY.getHeader(), apiKey);
        headers.add(API_SIGNATURE.getHeader(), signature);
        headers.add(API_TIMESTAMP.getHeader(), timestamp);

        return headers;
    }
}
