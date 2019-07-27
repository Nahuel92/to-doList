package org.nahuelrodriguez.controllers

import org.nahuelrodriguez.services.TodoListService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

class TodoListControllerTest extends Specification {
    private MockMvc mockMvc
    private TodoListController controller
    private TodoListService service

    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup().build()

        service = Mock(TodoListService)
        controller = new TodoListController(service)
    }

}
