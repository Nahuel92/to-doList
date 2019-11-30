package org.nahuelrodriguez.controllers

import org.hamcrest.Matchers
import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest
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
    private ListValidator<NewTodoItemRequest> listValidator
    private MessagingQueueProducer producer
    private MessagingQueueController controller

    void setup() {
        listValidator = Mock(ListValidator)
        producer = Mock(MessagingQueueProducer)
        controller = new MessagingQueueController(listValidator, producer)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "When invoked AddNewTodoItems method with empty collection -> returns 400 bad request"() {
        given:
        def emptyCollection = new ArrayList<NewTodoItemRequest>()

        when:
        def results = mockMvc.perform(post('/v1/todo-list/batch/items')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(emptyCollection)))

        then:
        0 * producer.sendMessage(_ as List)
        and:
        results.andExpect(status().isBadRequest())
        and:
        results.andExpect(jsonPath('$.errorMessages').value('Empty request'))
    }

    def "When invoked AddNewTodoItems method with invalid data collection -> returns 400 bad request"() {
        given:
        def invalidDataCollection = List.of(new NewTodoItemRequest(), new NewTodoItemRequest())
        and:
        listValidator.validate(invalidDataCollection) >> errorsMap()

        when:
        def results = mockMvc.perform(post('/v1/todo-list/batch/items')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(invalidDataCollection)))

        then:
        0 * producer.sendMessage(_ as List)
        and:
        results.andExpect(status().isBadRequest())
        and:
        results.andExpect(jsonPath('$.errorMessages.0', Matchers.containsInAnyOrder("Description can not be null or empty")))
        and:
        results.andExpect(jsonPath('$.errorMessages.1[0]').value("Description can not be null or empty"))
    }

    def "When invoked AddNewTodoItems method with valid data collection -> producer.send() invoked and returns 201 created"() {
        given:
        def validDataCollection = List.of(newRequest("Valid request"),
                newRequest("Valid request 2"),
                newRequest("Valid request 3"))
        and:
        listValidator.validate(validDataCollection) >> Map.of()

        when:
        def results = mockMvc.perform(post('/v1/todo-list/batch/items')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(validDataCollection)))

        then:
        1 * producer.sendMessage(validDataCollection)
        and:
        results.andExpect(status().isCreated())
    }

    def newRequest(String description) {
        def request = new NewTodoItemRequest()
        request.setDescription(description)
        request
    }

    def errorsMap() {
        def errors = new HashMap<Integer, Set<String>>()
        errors.put(0, Set.of("Description can not be null or empty"))
        errors.put(1, Set.of("Description can not be null or empty"))
        errors
    }
}
