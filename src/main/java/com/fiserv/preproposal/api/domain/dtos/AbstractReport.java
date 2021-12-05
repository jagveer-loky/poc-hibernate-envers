package com.fiserv.preproposal.api.domain.dtos;

import com.fiserv.preproposal.api.application.annotation.Index;
import com.fiserv.preproposal.api.infrastrucutre.aid.reflection.Reflection;
import com.univocity.parsers.annotations.Parsed;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractReport {

    /**
     * @return Set<String>
     */
    public Set<String> extractLabels() {

        final Set<Field> fields = new HashSet<>();

        final Set<String> attributes = Reflection.getAttributesFromClass(this.getClass());

        try {
            for (final String attribute : attributes) {
                final Parsed parsedAnnotation = this.getClass().getDeclaredField(attribute).getAnnotation(Parsed.class);
                final Index indexAnnotation = this.getClass().getDeclaredField(attribute).getAnnotation(Index.class);

                final Field field = new Field(parsedAnnotation.field()[0], Objects.isNull(indexAnnotation) ? attributes.size() : indexAnnotation.value());
                fields.add(field);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return fields.stream().sorted(Comparator.comparing(Field::getIndex)).collect(Collectors.toCollection(LinkedHashSet::new)).stream().map(Field::getLabel).collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * @return Set<Integer>
     */
    public Set<Integer> extractIndexes() {

        final Set<Field> fields = new HashSet<>();

        final Set<String> attributes = Reflection.getAttributesFromClass(this.getClass());

        try {
            for (final String attribute : attributes) {
                final Parsed parsedAnnotation = this.getClass().getDeclaredField(attribute).getAnnotation(Parsed.class);
                final Index indexAnnotation = this.getClass().getDeclaredField(attribute).getAnnotation(Index.class);

                final Field field = new Field(parsedAnnotation.field()[0], Objects.isNull(indexAnnotation) ? attributes.size() : indexAnnotation.value());
                fields.add(field);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return fields.stream().sorted(Comparator.comparing(Field::getIndex)).collect(Collectors.toCollection(LinkedHashSet::new)).stream().map(Field::getIndex).collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * @return Set<Integer>
     */
    public Set<Integer> extractIndexes(final Collection<String> filters) {

        final Set<Field> fields = new HashSet<>();

        final Set<String> attributes = Reflection.getAttributesFromClass(this.getClass());

        try {
            for (final String attribute : attributes) {
                final Parsed parsedAnnotation = this.getClass().getDeclaredField(attribute).getAnnotation(Parsed.class);
                final Index indexAnnotation = this.getClass().getDeclaredField(attribute).getAnnotation(Index.class);

                final Field field = new Field(parsedAnnotation.field()[0], Objects.isNull(indexAnnotation) ? attributes.size() : indexAnnotation.value());
                if (filters.stream().anyMatch(s -> s.equals(field.getLabel())))
                    fields.add(field);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return fields.stream().sorted(Comparator.comparing(Field::getIndex)).collect(Collectors.toCollection(LinkedHashSet::new)).stream().map(Field::getIndex).collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * @return Set<Field>
     */
    public Set<Field> extractFields() {

        final Set<Field> fields = new HashSet<>();

        final Set<String> attributes = Reflection.getAttributesFromClass(this.getClass());

        try {
            for (final String attribute : attributes) {
                final Parsed parsedAnnotation = this.getClass().getDeclaredField(attribute).getAnnotation(Parsed.class);
                final Index indexAnnotation = this.getClass().getDeclaredField(attribute).getAnnotation(Index.class);

                final Field field = new Field(parsedAnnotation.field()[0], Objects.isNull(indexAnnotation) ? attributes.size() : indexAnnotation.value());
                fields.add(field);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return fields;
    }
}
