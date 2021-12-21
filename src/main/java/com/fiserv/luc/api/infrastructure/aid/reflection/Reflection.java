package com.fiserv.luc.api.infrastructure.aid.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Reflection {

    /**
     * Extract the attributes from class
     *
     * @return Set<String>
     */
    public static Set<String> getAttributesFromClass(final Class<?> clazz) {

        return Arrays.stream(clazz.getDeclaredMethods())
                .map(Method::getName)
                .filter(method -> method.contains("get") || method.contains("set"))
                .map(method -> {
                    final String attribute = method.replace("get", "").replace("set", "");
                    return attribute.substring(0, 1).toLowerCase() + attribute.substring(1);
                }).collect(Collectors.toSet());
    }

    /**
     * Extract the attributes from class
     *
     * @return Set<String>
     */
    public static Object getValueFromAttribute(final Object object, final String attribute) {

        for (Method method : object.getClass().getMethods()) {
            if (method.getName().toLowerCase().replace("get", "").equals(attribute.toLowerCase())) {
                try {
                    return method.invoke(object);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
