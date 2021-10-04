package com.limengxiang.breeze.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.limengxiang.breeze.validation.Validation;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author LI Mengxiang <limengxiang876@gmail.com>
 */
public class JSONUtil {

    private static final ObjectMapper mapper;

    public static class MapTypeReference extends TypeReference<Map<String, Object>> {
        public MapTypeReference() {}
    }

    static {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDateFormat(new SimpleDateFormat(Validation.DATE_FORMAT_SEC));
//        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    }

    public static <T> T parse(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Parse json failed, input:" + json + ", err:" + e.getMessage());
        }
    }

    public static Map<String, Object> parse(String json) {
        try {
            return mapper.readValue(json, new MapTypeReference());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Parse json failed, input:" + json + ", err:" + e.getMessage());
        }
    }

    public static String stringify(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Stringify json failed, input:" + obj);
        }
    }

}
