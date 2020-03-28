package org.nahuelrodriguez.controllers

import org.nahuelrodriguez.requests.dtos.NewTodoItemRequest
import org.nahuelrodriguez.services.MessagingQueueProducer
import org.nahuelrodriguez.validators.ListValidator
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title
import spock.lang.Unroll

import static groovy.json.JsonOutput.toJson
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Unroll
@Subject(MessagingQueueController)
@Title("Unit test for MessagingQueueController class")
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

    def "Batch add with '#collection' as invalid collection = #status"() {
        given: "A collection validation data"
        listValidator.validate(data) >> dataValidation

        when: "the request is sent to the endpoint to massively add items"
        def results = mockMvc.perform(post('/v1/todo-list/batch/items')
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(data)))

        then: "data is sent to the add queue and the response is as expected"
        dataSent * producer.sendMessage(_ as Collection)
        results.andExpect(status().is(result.value()))

        where: "some data can be"
        data                                                   | dataValidation                                       | dataSent || result
        []                                                     | []                                                   | 0        || HttpStatus.BAD_REQUEST
        [new NewTodoItemRequest()]                             | [0: ["Description can not be null or empty"] as Set] | 0        || HttpStatus.BAD_REQUEST
        [new NewTodoItemRequest(description: "Valid request")] | [] as Map                                            | 1        || HttpStatus.CREATED
    }
}
