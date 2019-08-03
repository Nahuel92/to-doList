package org.nahuelrodriguez.serializers

import com.fasterxml.jackson.databind.ObjectMapper
import org.nahuelrodriguez.requests.dtos.TodoItemRequest
import spock.lang.Specification

class TodoItemRequestDeserializerTest extends Specification {
    private TodoItemRequestDeserializer todoItemRequestDeserializer

    void setup() {
        todoItemRequestDeserializer = new TodoItemRequestDeserializer()
    }

    def "When invocked Deserialize method -> returning value OK"() {
        given:
        def dto = new TodoItemRequest()
        dto.setId(1)
        dto.setDescription("Valid DTO")
        and:
        def serializedDto = new ObjectMapper().writeValueAsString(dto).getBytes()

        when:
        def dtoDeserialized = todoItemRequestDeserializer.deserialize("TopicName", serializedDto)

        then:
        noExceptionThrown()
        dtoDeserialized == dto
    }
}
