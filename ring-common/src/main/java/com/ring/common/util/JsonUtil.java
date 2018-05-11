package com.ring.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author CHAO on 2017/7/10
 *         常用注解
 * @see: @JsonIgnoreProperties(ignoreUnknown = true)
 * @see: @JsonIgnore 此注解用于属性上，作用是进行JSON操作时忽略该属性。
 * @see: @JsonFormat 此注解用于属性上，作用是把Date类型直接转化为想要的格式，如@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")。
 * @see: @JsonProperty 此注解用于属性上，作用是把该属性的名称序列化为另外一个名称，如把trueName属性序列化为name，@JsonProperty("name")。
 */
public final class JsonUtil {
    private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        //设置NULL值不进行序列化
        //OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //空对象不抛异常 e.g. handler{ }
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //解析JSON时忽略未知属性
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    private JsonUtil() {
    }

    /**
     * @param obj POJO
     * @param <T> 泛型
     * @return JSON
     */
    public static <T> String toJSON(T obj) {
        String jsonStr;
        try {
            jsonStr = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            LOG.error("Convert POJO to JSON failure", e);
            throw new RuntimeException(e);
        }
        return jsonStr;
    }

    /**
     * @param text JSON
     * @param clzz Class<?>
     * @param <T>  泛型
     * @return POJO
     */
    public static <T> T fromJSON(String text, Class<T> clzz) {
        T obj;
        try {
            obj = OBJECT_MAPPER.readValue(text, clzz);
        } catch (IOException e) {
            LOG.error("Convert JSON to POJO failure", e);
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static JsonNode fromJSON(String text) {
        JsonNode obj;
        try {
            obj = OBJECT_MAPPER.readTree(text);
        } catch (IOException e) {
            LOG.error("Convert JSON to JsonNode failure", e);
            throw new RuntimeException(e);
        }
        return obj;
    }

    public static <T> T fromJSON(String text, TypeReference<T> valueTypeRef) {
        T obj;
        try {
            obj = OBJECT_MAPPER.readValue(text, valueTypeRef);
        } catch (IOException e) {
            LOG.error("Convert JSON to JavaBean failure by TypeReference", e);
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * @param src  JSON字节
     * @param clzz Class<?>
     * @param <T>  泛型
     * @return POJO
     */
    public static <T> T fromByte(byte[] src, Class<T> clzz) {
        T obj;
        try {
            obj = OBJECT_MAPPER.readValue(src, OBJECT_MAPPER.constructType(clzz));
        } catch (IOException e) {
            LOG.error("Convert BYTE to POJO failure", e);
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * @param text JSON
     * @param clzz Class<?>
     * @param <T>  泛型
     * @return POJO
     */
    public static <T> T parseObject(String text, Class<T> clzz) {
        T obj;
        try {
            obj = OBJECT_MAPPER.readValue(text, clzz);
        } catch (IOException e) {
            LOG.error("Convert JSON to POJO failure", e);
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * @param map  Map
     * @param clzz Class<?>
     * @param <T>  泛型
     * @return POJO
     */
    public static <T> T fromMap(Map<String, Object> map, Class<T> clzz) {
        T obj;
        try {
            obj = OBJECT_MAPPER.convertValue(map, OBJECT_MAPPER.getTypeFactory().constructType(clzz));
        } catch (IllegalArgumentException e) {
            LOG.error("Convert MAP to POJO failure", e);
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * @param text JSON
     * @param clzz Class<?>
     * @param <T>  T
     * @return List
     */
    public static <T> List<T> fromJsonToList(String text, Class<T> clzz) {
        List<T> objList;
        try {
            objList = OBJECT_MAPPER.readValue(text, OBJECT_MAPPER.getTypeFactory().constructParametricType(ArrayList.class, clzz));
        } catch (IOException e) {
            LOG.error("Convert JSON TO List<POJO> failure", e);
            throw new RuntimeException(e);
        }
        return objList;
    }
}
