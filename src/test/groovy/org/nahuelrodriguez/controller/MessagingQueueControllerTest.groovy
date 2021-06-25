package org.nahuelrodriguez.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.nahuelrodriguez.request.dto.NewTodoItemRequest
import org.nahuelrodriguez.service.MessagingQueueProducer
import org.nahuelrodriguez.validator.ListValidator
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Unroll
@Subject(MessagingQueueController)
@Title("Unit test for MessagingQueueController class")
class MessagingQueueControllerTest extends Specification {
    private ObjectMapper objectMapper
    private MockMvc mockMvc
    private ListValidator<NewTodoItemRequest> listValidator
    private MessagingQueueProducer producer
    private MessagingQueueController controller

    def setup() {
        objectMapper = new ObjectMapper()
        listValidator = Mock(ListValidator)
        producer = Mock(MessagingQueueProducer)
        controller = new MessagingQueueController(listValidator, producer)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "Batch add with '#data' as a collection = #result"() {
        given: "A collection validation data"
        listValidator.validate(data) >> dataValidation

        when: "the request is sent to the endpoint to massively add items"
        def results = mockMvc.perform(post('/v1/todo-list/batch/items')
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data)))

        then: "data is sent to the add queue and the response is as expected"
        dataSent * producer.sendMessage(_ as Collection)
        results.andExpect(status().is(result.value()))

        where: "some data can be"
        data                                                   | dataValidation                                       | dataSent || result
        []                                                     | []                                                   | 0        || HttpStatus.BAD_REQUEST
        [new NewTodoItemRequest()]                             | [0: ["Description can not be null or empty"] as Set] | 0        || HttpStatus.BAD_REQUEST
        [new NewTodoItemRequest(description: "Valid request")] | [] as HashMap                                        | 1        || HttpStatus.CREATED
    }
}
