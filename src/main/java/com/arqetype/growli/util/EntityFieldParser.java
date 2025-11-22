package com.arqetype.growli.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityFieldParser {

    public static Map<String, Object> parseEntityFields(Object entity) {
        Map<String, Object> fieldValues = new HashMap<>();
        Class<?> clazz = entity.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                fieldValues.put(field.getName(), field.get(entity));
            } catch (IllegalAccessException e) {
                fieldValues.put(field.getName(), "ACCESS_ERROR");
            }
        }

        return fieldValues;
    }
}
