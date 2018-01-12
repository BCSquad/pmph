package com.bc.pmpheep.back.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bc.pmpheep.back.vo.SurveyQuestionListVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
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

    static {
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
        objectMapper.setDateFormat(new SimpleDateFormat(timeFormat));
    }

    public JsonUtil() {
    }

    public JsonUtil(String timeFormat) {
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
    @SuppressWarnings("deprecation")
    public List<T> getArrayListObjectFromStr(Class<T> v, String json) {
        try {
            return objectMapper.readValue(json,
                                          objectMapper.getTypeFactory()
                                                      .constructParametricType(ArrayList.class, v));
        } catch (IOException e) {
            LOGGER.error("字符串转化为ArrayList对象异常", e);
        }
        return null;
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
    public static void main(String[] args) throws Exception {
        // String jsonDecPosition =
        // "[{'id':4,'chosenPosition':1,'rank':1,'isDigitalEditor':true},{'id':3,'chosenPosition':2,'rank':3,'isDigitalEditor':true},{'id':1,'chosenPosition':3,'rank':'','isDigitalEditor':false}]";
        String jsonString =
        "[{'title':'你会网上冲浪吗？','type':1,'direction':null,'sort':1,'surveyQuestionOptionList':[{'optionContent':'不会'},{'optionContent':'会'}]},{'title':'你最喜欢的运动是什么','type':2,'direction':'','sort':'2','surveyQuestionOptionList':[{'optionContent':'篮球'},{'optionContent':'足球'},{'optionContent':'乒乓球'},{'optionContent':'羽毛球'},{'optionContent':'棒球'},{'optionContent':'网球'}]}]";
        // JavaType javaType = getCollectionType(ArrayList.class, SurveyQuestionListVO.class);
        // List<SurveyQuestionListVO> lst =
        // (List<SurveyQuestionListVO>) objectMapper.readValue(jsonString, javaType);
        // System.out.println(lst.toString());
        // System.out.println(decode(jsonString, new TypeReference<SurveyQuestionListVO>() {
        // }).toString());
        JavaType javaType =
        objectMapper.getTypeFactory().constructParametricType(List.class,
                                                              SurveyQuestionListVO.class);
        List<SurveyQuestionListVO> lst =
        (List<SurveyQuestionListVO>) objectMapper.readValue(jsonString, javaType);
        // List<SurveyQuestionListVO> beanList =
        // objectMapper.readValue(jsonString, new TypeReference<List<SurveyQuestionListVO>>() {
        // });
        System.out.println(lst.toString());
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
