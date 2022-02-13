package org.netkuz.washing.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.netkuz.washing.dto.MachineDTO;
import org.netkuz.washing.dto.StateDTO;
import org.netkuz.washing.model.enumeration.Mode;
import org.netkuz.washing.model.enumeration.Status;
import org.netkuz.washing.model.enumeration.Type;
import org.netkuz.washing.model.filter.FilterProgram;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Check machine controller")
public class MachineControllerTest extends ControllerTest {

    @Test
    void getMachines() throws Exception {
        var machineId = machineRepository.findAll().stream().findFirst().orElseThrow().getId();
        mockMvc.perform(get("/machines/all?id=" + machineId))
                .andDo(document("{class-name}/{method-name}",
                        requestParameters(
                                parameterWithName("modelId").description("Filtering by model id").optional(),
                                parameterWithName("serialNumber").description("Filtering by serial number").optional(),
                                parameterWithName("status").description("Filtering by status").optional(),
                                parameterWithName("mode").description("Filtering by mode").optional(),
                                parameterWithName("type").description("Filtering by type").optional(),
                                parameterWithName("id").description("Machine id").optional(),
                                parameterWithName("page").description("Page, https://docs.spring.io/spring-data/rest/docs/current/reference/html/#paging-and-sorting").optional()
                        ),
                        responseFields(
                                fieldWithPath("content").type(JsonFieldType.ARRAY).description("Content with data"),
                                fieldWithPath("content.[].id").type(JsonFieldType.STRING).description("Machine id"),
                                fieldWithPath("content.[].serialNumber").type(JsonFieldType.STRING).description("Machine serial number"),
                                fieldWithPath("content.[].model").type(JsonFieldType.OBJECT).description("Model"),
                                fieldWithPath("content.[].model.id").type(JsonFieldType.STRING).description("Model id"),
                                fieldWithPath("content.[].model.name").type(JsonFieldType.STRING).description("Model name"),
                                fieldWithPath("content.[].model.description").type(JsonFieldType.STRING).description("Model description").optional(),
                                fieldWithPath("content.[].program").type(JsonFieldType.OBJECT).description("Program").optional(),
                                fieldWithPath("content.[].program.id").type(JsonFieldType.STRING).description("Program id"),
                                fieldWithPath("content.[].program.name").type(JsonFieldType.STRING).description("Program name"),
                                fieldWithPath("content.[].program.model").type(JsonFieldType.OBJECT).description("Model"),
                                fieldWithPath("content.[].program.model.id").type(JsonFieldType.STRING).description("Model id"),
                                fieldWithPath("content.[].program.model.name").type(JsonFieldType.STRING).description("Model name"),
                                fieldWithPath("content.[].program.model.description").type(JsonFieldType.STRING).description("Model description").optional(),
                                fieldWithPath("content.[].program.temperature").type(JsonFieldType.NUMBER).description("Temperature").optional(),
                                fieldWithPath("content.[].program.detergent").type(JsonFieldType.STRING).description("Amount of detergent").optional(),
                                fieldWithPath("content.[].program.weight").type(JsonFieldType.NUMBER).description("Weight (g)").optional(),
                                fieldWithPath("content.[].program.spin").type(JsonFieldType.NUMBER).description("Amount of rotation (rpm)").optional(),
                                fieldWithPath("content.[].program.description").type(JsonFieldType.STRING).description("Description of model").optional(),
                                fieldWithPath("content.[].status").type(JsonFieldType.STRING).description("Status").optional(),
                                fieldWithPath("content.[].mode").type(JsonFieldType.STRING).description("Mode").optional(),
                                fieldWithPath("content.[].type").type(JsonFieldType.STRING).description("Type").optional(),
                                fieldWithPath("pageable").type(JsonFieldType.OBJECT).description("Pageable"),
                                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("descending"),
                                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("descending"),
                                fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("descending"),
                                fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("Offset"),
                                fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("Page number"),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("Page size"),
                                fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("Flag of paged"),
                                fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("Flag of unpaged"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("Count all page"),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("Flag of last page"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("Count all elements"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("Size elements on page"),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("Number of page"),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("descending"),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("descending"),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("descending"),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("Count elements on page"),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("Flag of first page"),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("Flag of empty data")
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['numberOfElements']", is(15)))
                .andExpect(jsonPath("$['totalElements']", is(50)))
                .andDo(print());
    }

    @Test
    void getMachine() throws Exception {
        var machineId = machineRepository.findAll().stream().findFirst().orElseThrow().getId();
        mockMvc.perform(get("/machines?id=" + machineId))
                .andDo(document("{class-name}/{method-name}/{step}",
                        requestParameters(
                                parameterWithName("id").description("Machine id")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("Machine id"),
                                fieldWithPath("serialNumber").type(JsonFieldType.STRING).description("Machine serial number"),
                                fieldWithPath("model").type(JsonFieldType.OBJECT).description("Model"),
                                fieldWithPath("model.id").type(JsonFieldType.STRING).description("Model id"),
                                fieldWithPath("model.name").type(JsonFieldType.STRING).description("Model name"),
                                fieldWithPath("model.description").type(JsonFieldType.STRING).description("Model description").optional(),
                                fieldWithPath("program").type(JsonFieldType.OBJECT).description("Program").optional(),
                                fieldWithPath("program.id").type(JsonFieldType.STRING).description("Program id"),
                                fieldWithPath("program.name").type(JsonFieldType.STRING).description("Program name"),
                                fieldWithPath("program.model").type(JsonFieldType.OBJECT).description("Model"),
                                fieldWithPath("program.model.id").type(JsonFieldType.STRING).description("Model id"),
                                fieldWithPath("program.model.name").type(JsonFieldType.STRING).description("Model name"),
                                fieldWithPath("program.model.description").type(JsonFieldType.STRING).description("Model description").optional(),
                                fieldWithPath("program.temperature").type(JsonFieldType.NUMBER).description("Temperature").optional(),
                                fieldWithPath("program.detergent").type(JsonFieldType.STRING).description("Amount of detergent").optional(),
                                fieldWithPath("program.weight").type(JsonFieldType.NUMBER).description("Weight (g)").optional(),
                                fieldWithPath("program.spin").type(JsonFieldType.NUMBER).description("Amount of rotation (rpm)").optional(),
                                fieldWithPath("program.description").type(JsonFieldType.STRING).description("Description of model").optional(),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("Status").optional(),
                                fieldWithPath("mode").type(JsonFieldType.STRING).description("Mode").optional(),
                                fieldWithPath("type").type(JsonFieldType.STRING).description("Type").optional()
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(machineId.toString())))
                .andDo(print());
        mockMvc.perform(get("/machines?id=" + UUID.randomUUID()))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("error", is("Entity not found")));
    }

    @Test
    void create() throws Exception {
        var modelDTO = modelService.getModels(null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        var dto = new MachineDTO();
        dto.setSerialNumber("SERIAL_NUMBER_123456");
        dto.setModel(modelDTO);
        dto.setStatus(Status.STARTING);
        dto.setMode(Mode.WASHING);
        dto.setType(Type.SCHEDULED);

        var result = mockMvc.perform(post("/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(document("{class-name}/{method-name}/{step}",
                        requestFields(
                                fieldWithPath("serialNumber").type(JsonFieldType.STRING).description("Machine name").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model").type(JsonFieldType.OBJECT).description("Model").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model.id").type(JsonFieldType.STRING).description("Model id").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model.name").type(JsonFieldType.STRING).description("Model name").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model.description").type(JsonFieldType.STRING).description("Model description").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("program").type(JsonFieldType.OBJECT).description("Program").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("program.id").type(JsonFieldType.STRING).description("Program id").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("program.name").type(JsonFieldType.STRING).description("Program name").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("program.model").type(JsonFieldType.OBJECT).description("Model").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("program.model.id").type(JsonFieldType.STRING).description("Model id").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("program.model.name").type(JsonFieldType.STRING).description("Model name").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("program.model.description").type(JsonFieldType.STRING).description("Model description").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("program.temperature").type(JsonFieldType.NUMBER).description("Temperature").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("program.detergent").type(JsonFieldType.STRING).description("Amount of detergent").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("program.weight").type(JsonFieldType.NUMBER).description("Weight (g)").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("program.spin").type(JsonFieldType.NUMBER).description("Amount of rotation (rpm)").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("program.description").type(JsonFieldType.STRING).description("Program description").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("Status").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("mode").type(JsonFieldType.STRING).description("Mode").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("type").type(JsonFieldType.STRING).description("Type").attributes(key("constraints").value("")).optional()
                        )
                ))
                .andExpect(status().isOk()).andReturn();

        var actual = mapper.readValue(result.getResponse().getContentAsString(), MachineDTO.class);
        assertNotNull(actual.getId());
        assertEquals(actual.getSerialNumber(), "SERIAL_NUMBER_123456");

        dto.setSerialNumber(null);
        mockMvc.perform(post("/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        var modelDTO = modelService.getModels(null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        var programDTO = programService.getPrograms(new FilterProgram(), null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        var machine = machineRepository.findAll().stream().findFirst().orElseThrow();
        var dto = new MachineDTO();
        dto.setId(machine.getId());
        dto.setSerialNumber(machine.getSerialNumber());
        dto.setModel(modelDTO);
        dto.setProgram(programDTO);
        dto.setStatus(Status.RUNNING);
        dto.setMode(Mode.WASHING);
        dto.setType(Type.MANUAL);
        var result = mockMvc.perform(put("/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(document("{class-name}/{method-name}/{step}",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("Machine id").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("serialNumber").type(JsonFieldType.STRING).description("Machine name").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model").type(JsonFieldType.OBJECT).description("Model").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model.id").type(JsonFieldType.STRING).description("Model id").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model.name").type(JsonFieldType.STRING).description("Model name").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model.description").type(JsonFieldType.STRING).description("Model description").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("program").type(JsonFieldType.OBJECT).description("Program").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("program.id").type(JsonFieldType.STRING).description("Program id").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("program.name").type(JsonFieldType.STRING).description("Program name").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("program.model").type(JsonFieldType.OBJECT).description("Model").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("program.model.id").type(JsonFieldType.STRING).description("Model id").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("program.model.name").type(JsonFieldType.STRING).description("Model name").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("program.model.description").type(JsonFieldType.STRING).description("Model description").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("program.temperature").type(JsonFieldType.NUMBER).description("Temperature").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("program.detergent").type(JsonFieldType.STRING).description("Amount of detergent").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("program.weight").type(JsonFieldType.NUMBER).description("Weight (g)").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("program.spin").type(JsonFieldType.NUMBER).description("Amount of rotation (rpm)").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("program.description").type(JsonFieldType.STRING).description("Program description").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("Status").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("mode").type(JsonFieldType.STRING).description("Mode").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("type").type(JsonFieldType.STRING).description("Type").attributes(key("constraints").value("")).optional()
                        )
                ))
                .andExpect(status().isOk()).andReturn();
        var actual = mapper.readValue(result.getResponse().getContentAsString(), MachineDTO.class);
        assertEquals(actual.getId(), machine.getId());
        assertEquals(actual.getSerialNumber(), machine.getSerialNumber());

        dto.setSerialNumber(null);
        mockMvc.perform(put("/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void _delete() throws Exception {
        var machineId = machineRepository.findAll().stream().findFirst().orElseThrow().getId();
        mockMvc.perform(delete("/machines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(List.of(machineId))))
                .andDo(document("{class-name}/{method-name}/{step}",
                        requestFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("Machine ids").attributes(key("constraints").value("Must be not null"))
                        )
                ))
                .andExpect(status().isOk());
    }

    @Test
    void updateState() throws Exception {
        var machineId = machineRepository.findAll().stream().findFirst().orElseThrow().getId();
        var dto = new StateDTO();
        dto.setMachineId(machineId);
        dto.setStatus(Status.STOPPING);
        dto.setMode(Mode.RINSE);
        dto.setType(Type.SCHEDULED);
        var result = mockMvc.perform(put("/machines/state")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(document("{class-name}/{method-name}/{step}",
                        requestFields(
                                fieldWithPath("machineId").type(JsonFieldType.STRING).description("Machine id").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("Status").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("mode").type(JsonFieldType.STRING).description("Mode").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("type").type(JsonFieldType.STRING).description("Type").attributes(key("constraints").value("")).optional()
                        )
                ))
                .andExpect(status().isOk()).andReturn();
        var actual = mapper.readValue(result.getResponse().getContentAsString(), MachineDTO.class);
        assertEquals(actual.getId(), machineId);
        assertEquals(actual.getMode(), Mode.RINSE);
    }

    @Test
    void getStatus() throws Exception {
        mockMvc.perform(get("/machines/state/statuses"))
                .andDo(document("{class-name}/{method-name}/{step}",
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description(Arrays.stream(Status.values())
                                        .map(Status::toString).collect(Collectors.joining(", ")))
                        )))
                .andExpect(status().isOk());
    }

    @Test
    void getMode() throws Exception {
        mockMvc.perform(get("/machines/state/modes"))
                .andDo(document("{class-name}/{method-name}/{step}",
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description(Arrays.stream(Mode.values())
                                        .map(Mode::toString).collect(Collectors.joining(", ")))
                        )))
                .andExpect(status().isOk());
    }

    @Test
    void getType() throws Exception {
        mockMvc.perform(get("/machines/state/types"))
                .andDo(document("{class-name}/{method-name}/{step}",
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description(Arrays.stream(Type.values())
                                        .map(Type::toString).collect(Collectors.joining(", ")))
                        )))
                .andExpect(status().isOk());
    }
}
