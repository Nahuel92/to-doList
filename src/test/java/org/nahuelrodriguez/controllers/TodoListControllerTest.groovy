package org.nahuelrodriguez.controllers

import org.nahuelrodriguez.requests.dtos.TodoItemRequest
import org.nahuelrodriguez.services.TodoListService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.HttpStatus.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class TodoListControllerTest extends Specification {
    private MockMvc mockMvc
    private TodoListController controller
    private TodoListService service

    void setup() {
        service = Mock(TodoListService)
        controller = new TodoListController(service)

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "When invocked addNewTodoItem method with valid DTO -> returns 201 created"() {
        given:
        def dto = new TodoItemRequest()
        dto.setId(1)
        dto.setDescription("valid dto")

        when:
        def results = mockMvc
                .perform(post('/todo-list/item')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(dto)))
                .andReturn()
                .response

        then:
        results.status == CREATED.value()
    }

    def "When invocked addNewTodoItem method with invalid DTO -> returns 400 bad request"() {
        given:
        def dto = new TodoItemRequest()

        when:
        def results = mockMvc
                .perform(post('/todo-list/item')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(dto)))
                .andReturn()
                .response

        then:
        results.status == BAD_REQUEST.value()
    }

    def "When invocked deleteTodoItem method with valid id -> returns 204 no content"() {
        given:
        def id = 1

        when:
        def results = mockMvc
                .perform(delete('/todo-list/item/{id}', id))
                .andReturn()
                .response

        then:
        results.status == NO_CONTENT.value()
    }

    def "When invocked deleteTodoItem method with invalid id -> returns 400 bad request"() {
        given:
        def id = "invalid id"

        when:
        def results = mockMvc
                .perform(delete('/todo-list/item/{id}', id))
                .andReturn()
                .response

        then:
        results.status == BAD_REQUEST.value()
    }
}
