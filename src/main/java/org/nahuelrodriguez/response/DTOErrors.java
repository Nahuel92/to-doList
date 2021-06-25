package org.nahuelrodriguez.response;

import java.util.Set;

public class DTOErrors {
    private final Set<String> errorMessages;

    public DTOErrors(final Set<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public DTOErrors(final String errorMessage) {
        this.errorMessages = Set.of(errorMessage);
    }

    public Set<String> getErrorMessages() {
        return errorMessages;
    }
}
