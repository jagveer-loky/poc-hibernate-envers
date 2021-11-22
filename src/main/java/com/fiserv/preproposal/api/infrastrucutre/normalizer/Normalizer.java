package com.fiserv.preproposal.api.infrastrucutre.normalizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public final class Normalizer<T> {

    /**
     *
     */
    private static final Logger LOGGER = LogManager.getLogger();

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

        final Set<String> attributes = extractAttributesFromObject(object);
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
        } catch (final Exception e) {
            e.printStackTrace();
            LOGGER.error("");
            LOGGER.error("Erro durante a normalização do atributo: '" + attribute + "' com o valor: '" + object + "'");
            System.err.println("Erro durante a normalização do atributo: '" + attribute + "' com o valor: '" + object + "'");
        }

        return object;
    }

    /**
     * @param object Object
     * @return Set<String>
     */
    public static Set<String> extractAttributesFromObject(final Object object) {

        final Set<String> attributes = new HashSet<>();

        try {
            for (final Method declaredMethod : object.getClass().getDeclaredMethods()) {
                try {
                    if (declaredMethod.getName().contains("get") || declaredMethod.getName().contains("set")) {
                        final String attribute = declaredMethod.getName().replace("get", "").replace("set", "");
                        attributes.add(attribute.substring(0, 1).toLowerCase() + attribute.substring(1));
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    LOGGER.error("");
                    LOGGER.error("Erro durante a extração dos atributos dos objetos");
                    System.err.println("Erro durante a extração dos atributos dos objetos");
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
            LOGGER.error("");
            LOGGER.error("Erro durante a extração dos atributos dos objetos");
            System.err.println("Erro durante a extração dos atributos dos objetos");
        }
        return attributes;
    }

}
