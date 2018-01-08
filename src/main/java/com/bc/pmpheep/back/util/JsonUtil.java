package com.bc.pmpheep.back.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * <pre>
 * 功能描述：使用jackson包
 *         1.对象转json字符串 
 *         2.字符串转化为对象 
 *         3.字符串转化为ArrayList对象 
 *         4.字符串转化为ArrayList的HashMap对象 
 *         5.HashMap对象转对象
 * 使用示范：
 * 
 * 
 * &#64;author (作者) nyz
 * 
 * &#64;since (该版本支持的JDK版本) ：JDK 1.6或以上
 * &#64;version (版本) 1.0
 * &#64;date (开发日期) 2017-10-19
 * &#64;modify (最后修改时间) 
 * &#64;修改人 ：nyz 
 * &#64;审核人 ：
 * </pre>
 */
@SuppressWarnings("unused")
public class JsonUtil<T> {
    private static final Logger LOGGER              = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper objectMapper        = new ObjectMapper();
    private static final String TIME_DATE_FORMAT    = "yyyy-MM-dd HH:mm:ss";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private String              timeFormat;

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
        objectMapper.setDateFormat(new SimpleDateFormat(timeFormat));
    }

    public JsonUtil() {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT));
    }

    public JsonUtil(String timeFormat) {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setDateFormat(new SimpleDateFormat(timeFormat));
    }

    /**
     * 对象转json字符串
     * 
     * @param object
     * @return
     */
    public static String toJSon(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            LOGGER.error("对象转json字符串", e);
        }
        return "";
    }

    /**
     * 字符串转化为对象
     * 
     * @param v
     * @param json
     * @return
     */
    public T getObjectFromStr(Class<T> v, String json) {
        try {
            return objectMapper.readValue(json.getBytes(), objectMapper.constructType(v));
        } catch (IOException e) {
            LOGGER.error("字符串转化为对象异常", e);
        }
        return null;
    }

    /**
     * HashMap对象转对象
     * 
     * @param v
     * @param map
     * @return
     */
    public T getObjectFromMap(Class<T> v, HashMap<String, Object> map) {
        return objectMapper.convertValue(map, objectMapper.getTypeFactory().constructType(v));
    }

    /**
     * 将 POJO 对象转为 JSON 字符串
     */
    public static <T> String toJson(T pojo) {
        String json;
        try {
            json = objectMapper.writeValueAsString(pojo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将 JSON 字符串转为 POJO 对象
     */
    public static <T> T fromJson(String json, Class<T> type) {
        T pojo;
        try {
            pojo = objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pojo;
    }

    /**
     * 字符串转化为ArrayList对象
     * 
     * @param v
     * @param json
     * @return
     */
    public List<T> getArrayListObjectFromStr(Class<T> v, String json) {
        try {
            // JavaType javaType =
            // objectMapper.getTypeFactory().constructParametricType(List.class, Bean.class);
            // // Map类型
            // objectMapper.getTypeFactory().constructParametricType(HashMap.class,
            // String.class,
            // Bean.class);
            // return objectMapper.readValue(json,
            // new TypeReference<List<org.springframework.context.annotation.Bean>>() {
            // });
            return objectMapper.readValue(json.getBytes(),
                                          objectMapper.getTypeFactory()
                                                      .constructParametricType(ArrayList.class, v));
        } catch (IOException e) {
            LOGGER.error("字符串转化为ArrayList对象异常", e);
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        // String jsonDecPosition =
        // "[{'id':4,'chosenPosition':1,'rank':1,'isDigitalEditor':true},{'id':3,'chosenPosition':2,'rank':3,'isDigitalEditor':true},{'id':1,'chosenPosition':3,'rank':'','isDigitalEditor':false}]";

    }
    /**
     * 获取泛型的Collection Type
     * 
     * @param collectionClass 泛型的Collection
     * @param elementClasses 元素类
     * @return JavaType Java类型
     * @since 1.0
     */
    // public static JavaType getCollectionType(Class<?> collectionClass, Class<?>...
    // elementClasses) {
    // return objectMapper.getTypeFactory().constructParametricType(collectionClass,
    // elementClasses);
    // }

    /**
     * 字符串转化为ArrayList的HashMap对象
     * 
     * @param json
     * @return
     */
    // public List<T> getArrayListMapFromStr(String json) {
    // try {
    // return objectMapper.readValue(json.getBytes(),
    // objectMapper.getTypeFactory()
    // .constructParametricType(ArrayList.class,
    // HashMap.class));
    // } catch (IOException e) {
    // LOGGER.error("字符串转化为ArrayList的HashMap对象异常", e);
    // }
    // return null;
    // }

}
