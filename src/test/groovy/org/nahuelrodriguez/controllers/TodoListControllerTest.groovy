package org.nahuelrodriguez.controllers

import org.nahuelrodriguez.controllers.advices.ControllerAdvice
import org.nahuelrodriguez.daos.Repository
import org.nahuelrodriguez.entities.TodoItem
import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest
import org.nahuelrodriguez.requests.dtos.UpdateTodoItemRequest
import org.nahuelrodriguez.services.TodoListService
import org.nahuelrodriguez.services.implementation.CassandraDBService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import java.time.Instant

import static groovy.json.JsonOutput.toJson
import static org.hamcrest.Matchers.containsInAnyOrder
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
        def dto = new NewTodoItemRequest()
        dto.setDescription("valid dto")
        and:
        def entitySaved = newEntity("a056fb54-317e-4982-bd83-ccb0b8b97d74", dto.getDescription())
        and:
        repository.save(_ as TodoItem) >> entitySaved

        when:
        def results = mockMvc.perform(post('/v1/todo-list/items')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dto)))

        then:
        results.andExpect(status().isCreated())
    }

    def "When invocked addNewTodoItem method with invalid DTO -> returns 400 bad request"() {
        given:
        def dto = new NewTodoItemRequest()

        when:
        def results = mockMvc.perform(post('/v1/todo-list/items')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dto)))

        then:
        0 * service.addNewTodoItem(_ as NewTodoItemRequest)
        and:
        results.andExpect(status().isBadRequest())
    }

    def "When invocked deleteTodoItem method with valid id and there's a saved item having that id -> returns 204 no content"() {
        given:
        def id = "a056fb54-317e-4982-bd83-ccb0b8b97d74"
        and:
        repository.findById(UUID.fromString(id)) >> Optional.of(newEntity(id, "valid entity"))

        when:
        def results = mockMvc.perform(delete('/v1/todo-list/items/{id}', id))

        then:
        results.andExpect(status().isNoContent())
    }

    def "When invocked deleteTodoItem method with valid id and there isn't a saved item having that id -> returns 404 not found"() {
        given:
        def id = "a056fb54-317e-4982-bd83-ccb0b8b97d74"
        and:
        repository.findById(UUID.fromString(id)) >> Optional.empty()

        when:
        def results = mockMvc.perform(delete('/v1/todo-list/items/{id}', id))

        then:
        0 * service.deleteTodoItem(id)
        and:
        results.andExpect(status().isNotFound())
        results.andExpect(jsonPath('$.errorMessages').value('Entity not found.'))
    }

    def "When invocked deleteTodoItem method with invalid id -> returns 400 bad request"() {
        given:
        def id = "a056fb54-317e-4982-bd83-ccb0b8b97d7x"

        when:
        def results = mockMvc.perform(delete('/v1/todo-list/items/{id}', id))

        then:
        0 * service.deleteTodoItem(id)
        and:
        results.andExpect(status().isBadRequest())
    }

    def "When invocked deleteAllTodoItems method  -> returns 204 no content"() {
        when:
        def results = mockMvc.perform(delete('/v1/todo-list/items'))

        then:
        results.andExpect(status().isNoContent())
    }

    def "When invocked getAllTodoItems method -> returns 200 OK and an entity collection"() {
        given:
        def idCollection = [
                "a056fb54-317e-4982-bd83-ccb0b8b97d74",
                "a056fb54-317e-4982-bd83-ccb0b8b97d73",
                "a056fb54-317e-4982-bd83-ccb0b8b97d72",
                "a056fb54-317e-4982-bd83-ccb0b8b97d71"
        ]
        and:
        def descriptions = ["entity 1",
                            "entity 2",
                            "entity 3",
                            "entity 4"]
        and:
        repository.findAll() >> List.of(newEntity(idCollection[0], descriptions[0]),
                newEntity(idCollection[1], descriptions[1]),
                newEntity(idCollection[2], descriptions[2]),
                newEntity(idCollection[3], descriptions[3]))

        when:
        def results = mockMvc.perform(get('/v1/todo-list/items')
                .contentType(MediaType.APPLICATION_JSON))

        then:
        results.andExpect(status().isOk())
        and:
        results.andExpect(jsonPath('$[*].id').value(containsInAnyOrder(idCollection.toArray())))
        and:
        results.andExpect(jsonPath('$[*].description').value(containsInAnyOrder(descriptions.toArray())))
    }

    def "When invocked updateTodoItem method with valid dto -> returns 204 no content"() {
        given:
        def id = "a056fb54-317e-4982-bd83-ccb0b8b97d74"
        and:
        def dto = new UpdateTodoItemRequest()
        dto.setId(id)
        dto.setDescription("valid dto")
        dto.setStatus("Created")
        and:
        repository.findById(UUID.fromString(id)) >> Optional.of(newEntity(id, "valid entity"))

        when:
        def results = mockMvc.perform(patch('/v1/todo-list/items')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dto)))

        then:
        results.andExpect(status().isNoContent())
    }

    def "When invocked updateTodoItem method with valid dto but there isn't an entity saved -> returns 404 not found"() {
        given:
        def id = "a056fb54-317e-4982-bd83-ccb0b8b97d74"
        and:
        def dto = new UpdateTodoItemRequest()
        dto.setId(id)
        dto.setDescription("valid dto")
        dto.setStatus("Created")
        and:
        repository.findById(UUID.fromString(id)) >> Optional.empty()

        when:
        def results = mockMvc.perform(patch('/v1/todo-list/items')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(dto)))

        then:
        0 * service.updateTodoItem(dto)
        and:
        results.andExpect(status().isNotFound())
        results.andExpect(jsonPath('$.errorMessages').value('Entity not found.'))
    }

    def newEntity(String id, String description) {
        def entity = new TodoItem()
        entity.setId(UUID.fromString(id))
        entity.setDescription(description)
        entity.setCreatedDatetime(Instant.now())
        entity.setStatus("Created")
        entity
    }
}
