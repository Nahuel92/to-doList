package org.nahuelrodriguez.mappers

import org.nahuelrodriguez.entities.TodoItem
import spock.lang.Specification

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class TodoItemDTOMapperTest extends Specification {
    private TodoItemDTOMapper mapper
    private DateTimeFormatter dtf = DateTimeFormatter
            .ofPattern("yyyy mm dd HH:MM:SS")
            .withZone(ZoneId.systemDefault())

    void setup() {
        mapper = new TodoItemDTOMapper()
    }

    def "When invocked from method with valid entity -> returns a valid dto"() {
        given:
        def entity = new TodoItem()
        entity.setId(UUID.fromString("a056fb54-317e-4982-bd83-ccb0b8b97d74"))
        entity.setDescription("Entity description")
        entity.setCreatedDatetime(Instant.now())

        when:
        def dto = mapper.from(entity)

        then:
        UUID.fromString(dto.getId()) == entity.getId()
        dto.getDescription() == entity.getDescription()
        dto.getCreatedDatetime() == dtf.format(entity.getCreatedDatetime())
    }
}
