package org.nahuelrodriguez.responses;

import java.util.Collection;

public class DTOErrors<T> {
    private final Collection<T> errorMessages;

    public DTOErrors(final Collection<T> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Collection<T> getErrorMessages() {
        return errorMessages;
    }
}
