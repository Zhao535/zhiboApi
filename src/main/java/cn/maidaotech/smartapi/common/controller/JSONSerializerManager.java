package cn.maidaotech.smartapi.common.controller;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.sunnysuperman.commons.util.FormatUtil;
import com.sunnysuperman.commons.util.JSONUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JSONSerializerManager {
    public static class ISO8601DateSerializer implements ObjectSerializer {
        private static final ISO8601DateSerializer INSTANCE = new ISO8601DateSerializer();

        public static final ISO8601DateSerializer getInstance() {
            return INSTANCE;
        }

        private ISO8601DateSerializer() {

        }

        @Override
        public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
                throws IOException {
            if (object == null) {
                serializer.getWriter().writeNull();
            } else {
                serializer.write(FormatUtil.formatISO8601Date((Date) object));
            }
        }
    }

    private static Map<Type, ObjectSerializer> serializers = new HashMap<>();
    private static Map<Type, ObjectSerializer> defaultSerializers;
    static {
        serializers.put(Date.class, ISO8601DateSerializer.getInstance());
        serializers.put(java.sql.Timestamp.class, ISO8601DateSerializer.getInstance());
        serializers.put(java.sql.Date.class, ISO8601DateSerializer.getInstance());

        defaultSerializers = new HashMap<>(serializers);
    }

    public static void register(Type type, ObjectSerializer serializer) {
        synchronized (serializers) {
            serializers.put(type, serializer);
        }
    }

    public static String serialize(Object result) {
        return JSONUtil.toJSONString(result, serializers);
    }

    public static String serializeUseDefaultSerializers(Object result) {
        return JSONUtil.toJSONString(result, defaultSerializers);
    }

}
