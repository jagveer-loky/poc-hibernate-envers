package com.fiserv.preproposal.api.domain.dtos;

import com.univocity.parsers.annotations.Parsed;

import java.util.HashSet;
import java.util.Set;

import static com.fiserv.preproposal.api.infrastrucutre.aid.reflection.Reflection.getAttributesFromClass;

public abstract class AbstractReport {

    /**
     * @return Set<String>
     */
    public Set<String> extractFields() {
        final Set<String> fields = new HashSet<>();
        try {
            for (final String attribute : getAttributesFromClass(this.getClass())) {
                final Parsed parsedAnnotation = this.getClass().getDeclaredField(attribute).getAnnotation(Parsed.class);
                fields.add(parsedAnnotation.field()[0]);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return fields;
    }
}
