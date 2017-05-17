package com.zz.util.shengyuan;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    private JsonUtils() {
    }

    public static Map<String, Object> fromMap(String json) throws Exception {
        return (Map)mapper.readValue(json, Map.class);
    }

    public static String toJson(Object value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static <T> T toObject(String json, Class<T> valueType) {
        Assert.hasText(json);
        Assert.notNull(valueType);

        try {
            return mapper.readValue(json, valueType);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static <T> T toObject(String json, TypeReference<?> typeReference) {
        Assert.hasText(json);
        Assert.notNull(typeReference);

        try {
            return mapper.readValue(json, typeReference);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static <T> T toObject(String json, JavaType javaType) {
        Assert.hasText(json);
        Assert.notNull(javaType);

        try {
            return mapper.readValue(json, javaType);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static void writeValue(Writer writer, Object value) {
        try {
            mapper.writeValue(writer, value);
        } catch (JsonGenerationException var3) {
            var3.printStackTrace();
        } catch (JsonMappingException var4) {
            var4.printStackTrace();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    public static <T> List<T> toList(File jsonFile, Class<T> clazz) {
        if(jsonFile == null) {
            return null;
        } else if(clazz == null) {
            return null;
        } else {
            try {
                JavaType javaType = getCollectionType(ArrayList.class, new Class[]{clazz});
                return (List)mapper.readValue(jsonFile, javaType);
            } catch (JsonParseException var3) {
                var3.printStackTrace();
            } catch (JsonMappingException var4) {
                var4.printStackTrace();
            } catch (IOException var5) {
                var5.printStackTrace();
            }

            return null;
        }
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    static {
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            public void serialize(Object value, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
                jg.writeString("");
            }
        });
    }
}
