package org.nahuelrodriguez.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.nahuelrodriguez.controller.advice.ControllerAdvice
import org.nahuelrodriguez.dao.Repository
import org.nahuelrodriguez.entity.TodoItem
import org.nahuelrodriguez.request.dto.NewTodoItemRequest
import org.nahuelrodriguez.request.dto.UpdateTodoItemRequest
import org.nahuelrodriguez.service.TodoListService
import org.nahuelrodriguez.service.implementation.CassandraDBService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title
import spock.lang.Unroll

import java.time.Instant

import static org.hamcrest.Matchers.containsInAnyOrder
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Unroll
@Subject(TodoListController)
@Title("Unit test for TodoListController class")
class TodoListControllerTest extends Specification {
    private ObjectMapper objectMapper
    private MockMvc mockMvc
    private TodoListController controller
    private TodoListService service
    private Repository repository

    void setup() {
        objectMapper = new ObjectMapper()
        repository = Stub(Repository)
        service = new CassandraDBService(repository)
        controller = new TodoListController(service)
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerAdvice())
                .build()
    }

    def "Add request with '#description' as description = #status"() {
        given: "a request"
        def dto = new NewTodoItemRequest()
        dto.setDescription(description)

        and: "an entity created with request data"
        def createdEntity = new TodoItem()
        createdEntity.with {
            setId UUID.fromString("a056fb54-317e-4982-bd83-ccb0b8b97d74")
            setDescription dto.getDescription()
        }
        repository.save(_ as TodoItem) >> Mono.just(createdEntity)

        when: "the request is sent to the endpoint to add item"
        def results = mockMvc.perform(post('/v1/todo-list/items')
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))

        then: "the response status is as expected"
        results.andExpect(status().is(status.value()))

        where: "some request descriptions are"
        description || status
        "Valid dto" || HttpStatus.CREATED
        ""          || HttpStatus.BAD_REQUEST
    }

    def "Delete request with '#id' as id = #expectedResult"() {
        when: "the request is sent to the endpoint to delete item"
        def results = mockMvc.perform(delete('/v1/todo-list/items/{id}', id))

        then: "the response status is as expected"
        results.andExpect(status().is(expectedResult.value()))

        where: "some ids are"
        id                                     || expectedResult
        "a056fb54-317e-4982-bd83-ccb0b8b97d74" || HttpStatus.NO_CONTENT
        "a056fb54-317e-4982-bd83-ccb0b8b97d7z" || HttpStatus.BAD_REQUEST
        //""                                     || HttpStatus.BAD_REQUEST
    }

    def "Delete all request = 204 NO CONTENT"() {
        when: "the request is sent to the endpoint to delete all items"
        def results = mockMvc.perform(delete('/v1/todo-list/items'))

        then: "the response status is as expected"
        results.andExpect(status().isNoContent())
    }

    def "Get request with '#id' as id = #expectedStatus"() {
        when: "the request is sent to the endpoint to get an item"
        def results = mockMvc.perform(get('/v1/todo-list/items/{id}', id))

        then: "the response status is as expected"
        results.andExpect(status().is(expectedStatus.value()))

        where: "some values are"
        id                                     | description   || expectedStatus
        "a056fb54-317e-4982-bd83-ccb0b8b97d74" | "valid dto"   || HttpStatus.OK
        "a056fb54-317e-4982-bd83-ccb0b8b97d7z" | "invalid dto" || HttpStatus.BAD_REQUEST
    }

    def "Get all request = 200 OK and items"() {
        given: "a collection of saved items"
        def data = [
                new TodoItem(id: UUID.fromString("a056fb54-317e-4982-bd83-ccb0b8b97d71"),
                        description: "entity 1", createdDatetime: Instant.now(), status: "Created"
                ),
                new TodoItem(id: UUID.fromString("a056fb54-317e-4982-bd83-ccb0b8b97d72"),
                        description: "entity 2", createdDatetime: Instant.now(), status: "Done"
                ),
                new TodoItem(id: UUID.fromString("a056fb54-317e-4982-bd83-ccb0b8b97d73"),
                        description: "entity 3", createdDatetime: Instant.now(), status: "Created"),
                new TodoItem(id: UUID.fromString("a056fb54-317e-4982-bd83-ccb0b8b97d74"),
                        description: "entity 4", createdDatetime: Instant.now(), status: "Created"
                )
        ]

        repository.findAll() >> Flux.fromIterable(data)

        when: "the request is sent to the endpoint to get items"
        def results = mockMvc.perform(get('/v1/todo-list/items')
                .contentType(MediaType.APPLICATION_JSON))

        then: "the response status is ok"
        results.andExpect(status().isOk())

        and: "data id and description are as expected"
        results.andExpect(jsonPath('$.content[*].id').value(containsInAnyOrder(data.collect { d -> d.id.toString() }.toArray())))
        results.andExpect(jsonPath('$.content[*].description').value(containsInAnyOrder(data.collect { d -> d.description }.toArray())))
    }

    def "Update request with '#id' as id and '#description' as description = #expectedStatus"() {
        given: "an update request object"
        def dto = new UpdateTodoItemRequest()
        dto.with {
            setId id
            setDescription description
            setStatus status
        }

        and: "a previously saved entity"
        repository.findById(_ as UUID) >> Mono.just(
                new TodoItem(description: description)
        )

        when: "the request is sent to the endpoint to perform an update"
        def results = mockMvc.perform(patch('/v1/todo-list/items')
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))

        then: "the response status is as expected"
        results.andExpect(status().is(expectedStatus.value()))

        where: "some cases are"
        id                                     | description   | status    || expectedStatus
        "a056fb54-317e-4982-bd83-ccb0b8b97d74" | "valid dto"   | "Created" || HttpStatus.NO_CONTENT
        ""                                     | "invalid dto" | "Created" || HttpStatus.BAD_REQUEST
    }
}
