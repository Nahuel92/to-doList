package org.nahuelrodriguez.serializers

import com.fasterxml.jackson.databind.ObjectMapper
import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest
import spock.lang.Specification

class NewTodoItemRequestSerializerTest extends Specification {
    private TodoItemRequestSerializer todoItemRequestSerializer

    void setup() {
        todoItemRequestSerializer = new TodoItemRequestSerializer()
    }

    def "When invoked Serialize method with valid DTO -> returning value OK"() {
        given:
        def dto = new NewTodoItemRequest()
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
