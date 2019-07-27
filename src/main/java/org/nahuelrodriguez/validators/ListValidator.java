package org.nahuelrodriguez.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Validator;
import java.util.*;
import java.util.stream.IntStream;

@Component
public final class ListValidator<T> {
    private final Validator validator;

    @Autowired
    public ListValidator(final Validator validator) {
        this.validator = validator;
    }

    public Map<Integer, Set<String>> validate(final List<T> dtos) {
        final Map<Integer, Set<String>> errors = new HashMap<>();

        IntStream.range(0, dtos.size())
                .forEach(i -> {
                    Set<String> errorMessages = new HashSet<>();

                    validator.validate(dtos.get(i))
                            .forEach(e -> errorMessages.add(e.getMessage()));

                    if (!errorMessages.isEmpty())
                        errors.put(i, errorMessages);
                });
        return errors;
    }
}
