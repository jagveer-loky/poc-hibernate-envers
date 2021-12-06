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
    public List<String> extractLabels() {

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

        return fields.stream().sorted(Comparator.comparing(Field::getIndex)).map(Field::getLabel).collect(Collectors.toList());
    }

    /**
     * @return Set<Integer>
     */
    public List<Integer> extractIndexes() {

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

        return fields.stream().sorted(Comparator.comparing(Field::getIndex)).map(Field::getIndex).collect(Collectors.toList());
    }

    /**
     * @return Set<Integer>
     */
    public List<Integer> extractIndexes(final Collection<String> filters) {

        final Set<Field> fields = new HashSet<>();

        final Set<String> attributes = Reflection.getAttributesFromClass(this.getClass());

        try {
            for (final String attribute : attributes) {
                final Parsed parsedAnnotation = this.getClass().getDeclaredField(attribute).getAnnotation(Parsed.class);
                final Index indexAnnotation = this.getClass().getDeclaredField(attribute).getAnnotation(Index.class);

                final Field field = new Field(parsedAnnotation.field()[0], Objects.isNull(indexAnnotation) ? attributes.size() : indexAnnotation.value());
                if (filters.stream().anyMatch(s -> s.equalsIgnoreCase(field.getLabel())))
                    fields.add(field);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return fields.stream().sorted(Comparator.comparing(Field::getIndex)).map(Field::getIndex).collect(Collectors.toList());
    }

    /**
     * @return Set<Field>
     */
    public List<Field> extractFields() {

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

        return fields.stream().sorted(Comparator.comparing(Field::getIndex)).collect(Collectors.toList());
    }

    /**
     * @param filters Collection<String>
     * @return Set<String>
     */
    public List<Object> extractValues(final Collection<String> filters) {

        final List<Field> fields = new ArrayList<>();

        try {
            final Collection<Integer> indexes = extractIndexes(filters);

            final Set<String> attributes = Reflection.getAttributesFromClass(this.getClass());

            for (final Integer index : indexes) {
                for (final String attribute : attributes) {
                    final Index indexAnnotation = this.getClass().getDeclaredField(attribute).getAnnotation(Index.class);
                    if (index == indexAnnotation.value()) {
                        final Parsed parsedAnnotation = this.getClass().getDeclaredField(attribute).getAnnotation(Parsed.class);

                        final Field field = new Field(parsedAnnotation.field()[0], indexAnnotation.value(), Reflection.getValueFromAttribute(this, attribute));
                        fields.add(field);

                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return fields.stream().sorted(Comparator.comparing(Field::getIndex)).map(Field::getValue).collect(Collectors.toList());
    }
}
