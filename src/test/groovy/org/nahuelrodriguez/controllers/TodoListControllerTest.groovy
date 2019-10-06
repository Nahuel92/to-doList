package org.nahuelrodriguez.controllers

import org.nahuelrodriguez.controllers.advices.ControllerAdvice
import org.nahuelrodriguez.daos.Repository
import org.nahuelrodriguez.entities.TodoItem
import org.nahuelrodriguez.requests.dtos.TodoItemRequest
import org.nahuelrodriguez.services.TodoListService
import org.nahuelrodriguez.services.implementation.CassandraDBService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.Instant

import static groovy.json.JsonOutput.toJson
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class TodoListControllerTest extends Specification {
    private MockMvc mockMvc
    private TodoListController controller
    private TodoListService service
    private Repository repository

    void setup() {
        repository = Mock(Repository)
        service = new CassandraDBService(repository)
        controller = new TodoListController(service)
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerAdvice())
                .build()
    }

    def "When invocked addNewTodoItem method with valid DTO -> returns 201 created"() {
        given:
        def dto = new TodoItemRequest()
        dto.setId(1)
        dto.setDescription("valid dto")
        dto.setStatus("Created")

        when:
        def results = mockMvc.perform(post('/todo-list/item')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dto)))

        then:
        results.andExpect(status().isCreated())
    }

    def "When invocked addNewTodoItem method with invalid DTO -> returns 400 bad request"() {
        given:
        def dto = new TodoItemRequest()

        when:
        def results = mockMvc.perform(post('/todo-list/item')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dto)))

        then:
        0 * service.addNewTodoItem(_ as TodoItemRequest)
        and:
        results.andExpect(status().isBadRequest())
    }

    def "When invocked deleteTodoItem method with valid id and there's a saved item having that id -> returns 204 no content"() {
        given:
        def id = 1
        and:
        repository.findById(id) >> Optional.of(newEntity(id, "valid entity"))

        when:
        def results = mockMvc.perform(delete('/todo-list/item/{id}', id))

        then:
        results.andExpect(status().isNoContent())
    }

    def "When invocked deleteTodoItem method with valid id and there isn't a saved item having that id -> returns 404 not found"() {
        given:
        def id = 1
        and:
        repository.findById(id) >> Optional.empty()

        when:
        def results = mockMvc.perform(delete('/todo-list/item/{id}', id))

        then:
        0 * service.deleteTodoItem(id)
        and:
        results.andExpect(status().isNotFound())
        results.andExpect(jsonPath('$.errorMessages').value('Entity not found.'))
    }

    def "When invocked deleteTodoItem method with invalid id -> returns 400 bad request"() {
        given:
        def id = "invalid id"

        when:
        def results = mockMvc.perform(delete('/todo-list/item/{id}', id))

        then:
        0 * service.deleteTodoItem(id)
        and:
        results.andExpect(status().isBadRequest())
    }

    def "When invocked deleteAllTodoItems method  -> returns 204 no content"() {
        when:
        def results = mockMvc.perform(delete('/todo-list/items'))

        then:
        results.andExpect(status().isNoContent())
    }

    def "When invocked getAllTodoItems method -> returns 200 OK and an entity collection"() {
        given:
        repository.findAll() >> List.of(newEntity(1, "entity 1"),
                newEntity(2, "entity 2"),
                newEntity(3, "entity 3"),
                newEntity(4, "entity 4"))

        when:
        def results = mockMvc.perform(get('/todo-list/items')
                .contentType(MediaType.APPLICATION_JSON))

        then:
        results.andExpect(status().isOk())
        and:
        results.andExpect(jsonPath('$[0].id').value('1'))
        results.andExpect(jsonPath('$[0].description').value("entity 1"))
        and:
        results.andExpect(jsonPath('$[1].id').value('2'))
        results.andExpect(jsonPath('$[1].description').value("entity 2"))
        and:
        results.andExpect(jsonPath('$[2].id').value('3'))
        results.andExpect(jsonPath('$[2].description').value("entity 3"))
        and:
        results.andExpect(jsonPath('$[3].id').value('4'))
        results.andExpect(jsonPath('$[3].description').value("entity 4"))
    }

    def "When invocked updateTodoItem method with valid dto -> returns 204 no content"() {
        given:
        def id = 1
        and:
        def dto = new TodoItemRequest()
        dto.setId(id)
        dto.setDescription("valid dto")
        dto.setStatus("Created")
        and:
        repository.findById(id) >> Optional.of(newEntity(id, "valid entity"))

        when:
        def results = mockMvc.perform(patch('/todo-list/item')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dto)))

        then:
        results.andExpect(status().isNoContent())
    }

    def "When invocked updateTodoItem method with valid dto but there isn't an entity saved -> returns 404 not found"() {
        given:
        def id = 1
        and:
        def dto = new TodoItemRequest()
        dto.setId(id)
        dto.setDescription("valid dto")
        dto.setStatus("Created")
        and:
        repository.findById(id) >> Optional.empty()

        when:
        def results = mockMvc.perform(patch('/todo-list/item')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dto)))

        then:
        0 * service.updateTodoItem(dto)
        and:
        results.andExpect(status().isNotFound())
        results.andExpect(jsonPath('$.errorMessages').value('Entity not found.'))
    }

    def newEntity(int id, String description) {
        def entity = new TodoItem()
        entity.setId(id)
        entity.setDescription(description)
        entity.setCreatedDatetime(Instant.now())
        entity
    }
}
