package com.p09.framework.context;

import java.util.HashMap;
import java.util.Map;

public final class ScenarioContext {

    private static final ThreadLocal<Map<String, Object>> context =
            ThreadLocal.withInitial(HashMap::new);

    private ScenarioContext() {
    }

    public static void set(String key, Object value) {
        context.get().put(key, value);
    }

    public static Object get(String key) {
        return context.get().get(key);
    }

    public static String getString(String key) {

        Object value = context.get().get(key);

        return value == null ? null : value.toString();

    }

    public static void clear() {
        context.remove();
    }
    public static void printAll() {
        context.get().forEach((key, value) ->
                System.out.println("Key: " + key + " | Value: " + value));
    }
    public static Map<String, Object> getAll() {
        return new HashMap<>(context.get());
    }

}