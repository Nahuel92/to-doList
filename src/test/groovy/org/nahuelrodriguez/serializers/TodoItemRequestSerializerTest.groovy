package org.nahuelrodriguez.serializers

import com.fasterxml.jackson.databind.ObjectMapper
import org.nahuelrodriguez.requests.dtos.TodoItemRequest
import spock.lang.Specification

class TodoItemRequestSerializerTest extends Specification {
    private TodoItemRequestSerializer todoItemRequestSerializer

    void setup() {
        todoItemRequestSerializer = new TodoItemRequestSerializer()
    }

    def "When invocked Serialize method with valid DTO -> returning value OK"() {
        given:
        def dto = new TodoItemRequest()
        dto.setId(1)
        dto.setDescription("Valid DTO")
        and:
        def expectedValue = new ObjectMapper().writeValueAsString(dto).getBytes()

        when:
        def serializedDTO = todoItemRequestSerializer.serialize("TopicName", dto)

        then:
        noExceptionThrown()
        serializedDTO == expectedValue
    }
}
