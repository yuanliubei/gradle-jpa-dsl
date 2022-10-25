package com.example.demo;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @author yuanlb
 * @since 2022/10/25
 */
public class JacksonUtil {

    private static ObjectMapper sharedObjectMapper = new ObjectMapper();



    public JacksonUtil() {
    }

    public static ObjectMapper objectMapper() {
        if (Objects.isNull(sharedObjectMapper)) {
            throw new IllegalArgumentException("");
        } else {
            return sharedObjectMapper;
        }
    }


    public static String toJson(Object obj) {
        if (Objects.isNull(obj)) {
            return null;
        } else {
            try {
                return objectMapper().writeValueAsString(obj);
            } catch (Exception var2) {
                throw new RuntimeException("json 转换失败。", var2);
            }
        }
    }

    public static <T> T fromJson(String jsonString, Class<T> type) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        } else {
            try {
                return objectMapper().readValue(jsonString, type);
            } catch (Exception var3) {
                throw new RuntimeException("json 转化异常。", var3);
            }
        }
    }

    public static <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        } else {
            try {
                return objectMapper().readValue(jsonString, javaType);
            } catch (Exception var3) {
                throw new RuntimeException("json 转化异常。", var3);
            }
        }
    }

    public static <T> T fromJson(String jsonString, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        } else {
            try {
                return objectMapper().readValue(jsonString, typeReference);
            } catch (Exception var3) {
                throw new RuntimeException("json 转化异常。", var3);
            }
        }
    }

    public static <T> T fromJson(byte[] src, Class<T> type) {
        if (!Objects.isNull(src) && src.length != 0) {
            try {
                return objectMapper().readValue(src, type);
            } catch (Exception var3) {
                throw new RuntimeException("json 转化异常。", var3);
            }
        } else {
            return null;
        }
    }

    public static JsonNode fromJson(String content) {
        if (StringUtils.isBlank(content)) {
            return null;
        } else {
            try {
                return objectMapper().readTree(content);
            } catch (Exception var2) {
                throw new RuntimeException("json 转化异常。", var2);
            }
        }
    }

    public static <T> T fromNode(TreeNode node, Class<T> type) {
        if (Objects.isNull(node)) {
            return null;
        } else {
            try {
                return objectMapper().treeToValue(node, type);
            } catch (Exception var3) {
                throw new RuntimeException("json 转化异常", var3);
            }
        }
    }

    public static <T> T fromNode(JsonNode node, TypeReference<T> typeReference) {
        if (Objects.isNull(node)) {
            return null;
        } else {
            try {
                return objectMapper().readerFor(typeReference).readValue(node);
            } catch (Exception var3) {
                throw new RuntimeException("json 转化异常。", var3);
            }
        }
    }

    public static JsonNode toNode(Object value) {
        return objectMapper().valueToTree(value);
    }


    public static void setSharedObjectMapper(final ObjectMapper sharedObjectMapper) {
        JacksonUtil.sharedObjectMapper = sharedObjectMapper;
    }
}
