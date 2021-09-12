package com.limengxiang.breeze.utils;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class HttpUtil {

    private static final RestTemplate restTemplate;
    private static final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
            HttpClientBuilder.create().build());

    static {
        restTemplate = new RestTemplate(clientHttpRequestFactory);
        List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
        int index = -1;
        for (HttpMessageConverter<?> converter : converters) {
            if (converter.getClass().equals(StringHttpMessageConverter.class)) {
                index = converters.indexOf(converter);
            }
        }
        if (index >= 0) {
            converters.remove(index);
            converters.add(index, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        }
    }

    public static <T> ResponseEntity<T> post(String url, Map<String, Object> params, Class<T> clazz) {
        return post(url, params, defaultHeaders(), clazz);
    }

    public static <T> ResponseEntity<T> post(String url, Map<String, Object> params, String token, Class<T> clazz) {
        HttpHeaders headers = bearerTokenHeader(token);
        return post(url, params, headers, clazz);
    }

    public static <T> ResponseEntity<T> post(String url, Map<String, Object> params, HttpHeaders headers, Class<T> clazz) {
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(params, headers);
        return restTemplate.postForEntity(url, httpEntity, clazz);
    }

    public static <T> ResponseEntity<T> get(String url, String token, Map<String, Object> params, Class<T> clazz) {
        return get(buildQuery(url, params), token, clazz);
    }

    public static <T> ResponseEntity<T> get(String url, Class<T> clazz) {
        return restTemplate.getForEntity(url, clazz);
    }

    public static <T> ResponseEntity<T> get(String url, String token, Class<T> clazz) {
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(bearerTokenHeader(token));
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, clazz);
    }

    public static <T> ResponseEntity<T> put(String url, Map<String, Object> params, String token, Class<T> tClass) {
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(params, bearerTokenHeader(token));
        return restTemplate.exchange(url, HttpMethod.PUT, httpEntity, tClass);
    }

    public static <T> ResponseEntity<T> delete(String url, Map<String, Object> params, String token, Class<T> tClass) {
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(params, bearerTokenHeader(token));
        return restTemplate.exchange(url, HttpMethod.DELETE, httpEntity, tClass);
    }

    public static String buildQuery(String url, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder(url);
        int qPos = url.lastIndexOf("?");
        if (qPos < 0) {
            sb.append("?");
        }
        else if (qPos != url.length() - 1) {
            if (!url.endsWith("&")) {
                sb.append("&");
            }
        }
        params.forEach((String k, Object v) -> {
            try {
                sb.append(k);
                sb.append("=");
                sb.append(v);
                sb.append("&");
            } catch (Throwable e) {
                throw new RuntimeException("Build query error:" + e.getMessage());
            }
        });
        return sb.toString();
    }

    private static HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        return headers;
    }

    private static HttpHeaders bearerTokenHeader(String token) {
        HttpHeaders headers = defaultHeaders();
        if (StrUtil.notEmpty(token)) {
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        }
        return headers;
    }
}
