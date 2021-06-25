package org.nahuelrodriguez.response;

import java.util.Map;
import java.util.Set;

public class ErrorList {
    private final Map<Integer, Set<String>> errorMessages;

    public ErrorList(final Map<Integer, Set<String>> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Map<Integer, Set<String>> getErrorMessages() {
        return errorMessages;
    }
}
