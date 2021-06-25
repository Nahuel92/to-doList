package org.nahuelrodriguez.mapper


import org.nahuelrodriguez.request.dto.NewTodoItemRequest
import spock.lang.Specification

class TodoItemMapperTest extends Specification {
    private TodoItemMapper mapper

    void setup() {
        mapper = new TodoItemMapper()
    }

    def "When invoked from method with valid dto -> returns a valid entity"() {
        given:
        def dto = new NewTodoItemRequest()
        dto.setDescription("DTO description")

        when:
        def entity = mapper.from(dto)

        then:
        entity.getDescription() == dto.getDescription()
    }
}
