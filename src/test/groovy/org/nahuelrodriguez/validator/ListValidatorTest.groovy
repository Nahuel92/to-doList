package org.nahuelrodriguez.validator

import org.nahuelrodriguez.request.dto.NewTodoItemRequest
import spock.lang.Specification

import javax.validation.Validation

class ListValidatorTest extends Specification {
    private ListValidator<NewTodoItemRequest> listValidator

    void setup() {
        def validator = Validation.buildDefaultValidatorFactory().getValidator()
        listValidator = new ListValidator<>(validator)
    }

    def "When invoking validate method with valid DTO collection -> returns empty map"() {
        when:
        def results = listValidator.validate(validDTOCollection())

        then:
        results.isEmpty()
    }

    def "When invoking validate method with invalid DTO collection -> returns map with errors"() {
        when:
        def results = listValidator.validate(invalidDTOCollection())

        then:
        !results.isEmpty()
        and:
        results.get(0).toList().contains("Description can not be null or empty")
        and:
        results.get(1).toList().contains("Description can not be null or empty")
    }

    static def invalidDTOCollection() {
        [
                newDTO(""),
                newDTO(null),
        ]
    }

    static def validDTOCollection() {
        [
                newDTO("Valid dto 1"),
                newDTO("Valid dto 2"),
        ]
    }

    static def newDTO(String description) {
        def dto = new NewTodoItemRequest()
        dto.setDescription(description)
        dto
    }
}
