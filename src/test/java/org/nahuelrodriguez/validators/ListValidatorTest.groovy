package org.nahuelrodriguez.validators

import org.nahuelrodriguez.requests.dtos.TodoItemRequest
import spock.lang.Specification

import javax.validation.Validation

class ListValidatorTest extends Specification {
    private ListValidator<TodoItemRequest> listValidator

    void setup() {
        def validator = Validation.buildDefaultValidatorFactory().getValidator()
        listValidator = new ListValidator<>(validator)
    }

    def "When invocking validate method with valid DTO collection -> returns empty map"() {
        when:
        def results = listValidator.validate(validDTOCollection())

        then:
        results.isEmpty()
    }

    def "When invocking validate method with invalid DTO collection -> returns map with errors"() {
        when:
        def results = listValidator.validate(invalidDTOCollection())

        then:
        !results.isEmpty()
        and:
        results.get(0).toList().contains("Id can not be null")
        and:
        results.get(1).toList().contains("Description can not be null or empty")
        and:
        results.get(2).toList().contains("Description can not be null or empty")
        and:
        results.get(3).toList().contains("Id can not be null")
        results.get(3).toList().contains("Description can not be null or empty")
    }

    def invalidDTOCollection() {
        [
                newDTO(null, "Valid dto 1"),
                newDTO(2, ""),
                newDTO(3, null),
                newDTO(null, null)
        ]
    }

    def validDTOCollection() {
        [
                newDTO(1, "Valid dto 1"),
                newDTO(2, "Valid dto 2"),
                newDTO(3, "Valid dto 3"),
                newDTO(4, "Valid dto 4")
        ]
    }

    def newDTO(Integer id, String description) {
        def dto = new TodoItemRequest()
        dto.setId(id)
        dto.setDescription(description)
        dto
    }
}
