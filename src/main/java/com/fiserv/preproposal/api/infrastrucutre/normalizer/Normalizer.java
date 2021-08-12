package com.fiserv.preproposal.api.infrastrucutre.normalizer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

public final class Normalizer<T> {

    /**
     * Normalize the words to write CEF return bulk
     *
     * @param str String
     * @return String
     */
    public static String normalize(final String str) {
        return java.text.Normalizer.normalize(str, java.text.Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").replaceAll("&", " ");
    }


    /**
     * @param object Object
     * @return HashMap<String, Object>
     */
    public T normalize(final T object) {

        final Stream<String> attributes = extractAttributesFromObject(object);

        attributes.forEach(attribute -> normalize(object, attribute));

        return object;
    }

    /**
     * @param object    Object
     * @param attribute String
     * @return HashMap<String, Object>
     */
    public T normalize(final T object, final String attribute) {

        try {
            final Method[] methodList = object.getClass().getDeclaredMethods();
            for (final Method getMethod : methodList)
                if (getMethod.getName().toUpperCase().equals("GET" + attribute.toUpperCase()))

                    if (getMethod.invoke(object) != null && getMethod.invoke(object) instanceof String) {
                        final String noNormalized = (String) getMethod.invoke(object);

                        final String normalized = normalize(noNormalized);
                        if (!noNormalized.equals(normalized)) {

                            for (final Method setMethod : methodList)
                                if (setMethod.getName().toUpperCase().equals("SET" + attribute.toUpperCase()))
                                    setMethod.invoke(object, normalized);

                        }
                    }
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return object;
    }

    /**
     * @param object Object
     * @return Set<String>
     */
    public static Stream<String> extractAttributesFromObject(final Object object) {

        return Arrays.stream(object.getClass().getDeclaredMethods())
                .map(Method::getName)
                .filter(method -> method.contains("get") || method.contains("set"))
                .map(method -> {
                    final String attribute = method.replace("get", "").replace("set", "");
                    return attribute.substring(0, 1).toLowerCase() + attribute.substring(1);
                });

    }

}
