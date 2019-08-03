package org.nahuelrodriguez.mappers

import org.nahuelrodriguez.requests.dtos.TodoItemRequest
import spock.lang.Specification

class TodoItemMapperTest extends Specification {
    private TodoItemMapper mapper

    void setup() {
        mapper = new TodoItemMapper()
    }

    def "When invocked from method with valid dto -> returns a valid entity"() {
        given:
        def dto = new TodoItemRequest()
        dto.setId(1)
        dto.setDescription("DTO description")

        when:
        def entity = mapper.from(dto)

        then:
        entity.getId() == dto.getId()
        and:
        entity.getDescription() == dto.getDescription()
    }
}
