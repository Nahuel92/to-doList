package org.nahuelrodriguez.serializers

import com.fasterxml.jackson.databind.ObjectMapper
import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest
import spock.lang.Specification

class NewTodoItemRequestDeserializerTest extends Specification {
    private TodoItemRequestDeserializer todoItemRequestDeserializer

    void setup() {
        todoItemRequestDeserializer = new TodoItemRequestDeserializer()
    }

    def "When invocked Deserialize method -> returning value OK"() {
        given:
        def dto = new NewTodoItemRequest()
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
