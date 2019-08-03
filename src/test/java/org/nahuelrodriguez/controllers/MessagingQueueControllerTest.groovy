package org.nahuelrodriguez.controllers

import org.nahuelrodriguez.requests.dtos.TodoItemRequest
import org.nahuelrodriguez.services.MessagingQueueProducer
import org.nahuelrodriguez.validators.ListValidator
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static groovy.json.JsonOutput.toJson
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class MessagingQueueControllerTest extends Specification {
    private MockMvc mockMvc
    private ListValidator<TodoItemRequest> listValidator
    private MessagingQueueProducer producer
    private MessagingQueueController controller

    void setup() {
        listValidator = Mock(ListValidator)
        producer = Mock(MessagingQueueProducer)
        controller = new MessagingQueueController(listValidator, producer)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "When invocked AddNewTodoItems method with empty collection -> returns 400 bad request"() {
        given:
        def emptyCollection = new ArrayList<TodoItemRequest>()

        when:
        def results = mockMvc.perform(post('/todo-list/items')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(emptyCollection)))

        then:
        results.andExpect(status().isBadRequest())
        and:
        results.andExpect(jsonPath('$.errorMessages').value('Empty request'))
    }

    def "When invocked AddNewTodoItems method with invalid data collection -> returns 400 bad request"() {
        given:
        def invalidDataCollection = List.of(new TodoItemRequest(), new TodoItemRequest())
        and:
        listValidator.validate(invalidDataCollection) >> errorsMap()

        when:
        def results = mockMvc.perform(post('/todo-list/items')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(invalidDataCollection)))

        then:
        results.andExpect(status().isBadRequest())
        and:
        results.andExpect(jsonPath('$.errorMessages.0[0]').value('Id can not be null'))
        results.andExpect(jsonPath('$.errorMessages.0[1]').value('Description can not be null or empty'))
        and:
        results.andExpect(jsonPath('$.errorMessages.1[0]').value('Id can not be null'))
    }

    def "When invocked AddNewTodoItems method with valid data collection -> producer.send() invocked and returns 201 created"() {
        given:
        def validDataCollection = List.of(newRequest(1, "Valid request"),
                newRequest(2, "Valid request 2"),
                newRequest(3, "Valid request 3"))
        and:
        listValidator.validate(validDataCollection) >> Map.of()

        when:
        def results = mockMvc.perform(post('/todo-list/items')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(validDataCollection)))

        then:
        1 * producer.sendMessage(validDataCollection)
        and:
        results.andExpect(status().isCreated())
    }

    def newRequest(Integer id, String description) {
        def request = new TodoItemRequest()
        request.setId(id)
        request.setDescription(description)
        request
    }

    def errorsMap() {
        def errors = new HashMap<Integer, Set<String>>()
        errors.put(0, Set.of("Id can not be null", "Description can not be null or empty"))
        errors.put(1, Set.of("Id can not be null"))
        errors
    }
}
