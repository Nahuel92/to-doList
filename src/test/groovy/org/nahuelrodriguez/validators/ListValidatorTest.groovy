package org.nahuelrodriguez.validators

import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest
import spock.lang.Specification

import javax.validation.Validation

class ListValidatorTest extends Specification {
    private ListValidator<NewTodoItemRequest> listValidator

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
        results.get(0).toList().contains("Description can not be null or empty")
        and:
        results.get(1).toList().contains("Description can not be null or empty")
        and:
        results.get(2).toList().contains("Description can not be null or empty")
        and:
        results.get(3).toList().contains("Description can not be null or empty")
    }

    def invalidDTOCollection() {
        [
                newDTO("a056fb54-317e-4982-bd83-ccb0b8b97d74", "", ""),
                newDTO("a056fb54-317e-4982-bd83-ccb0b8b97d73", null, null),
                newDTO(null, null, ""),
                newDTO("x56a4180-h5aa-42ec-a945-5fd21dec0538", null, "")
        ]
    }

    def validDTOCollection() {
        [
                newDTO("a056fb54-317e-4982-bd83-ccb0b8b97d74", "Valid dto 1", "Created"),
                newDTO("a056fb54-317e-4982-bd83-ccb0b8b97d73", "Valid dto 2", "In Progress"),
                newDTO("a056fb54-317e-4982-bd83-ccb0b8b97d72", "Valid dto 3", "Done"),
                newDTO("a056fb54-317e-4982-bd83-ccb0b8b97d71", "Valid dto 4", "Done")
        ]
    }

    def newDTO(String id, String description, String status) {
        def dto = new NewTodoItemRequest()
        dto.setId(id)
        dto.setDescription(description)
        dto.setStatus(status)
        dto
    }
}
