package org.netkuz.washing.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.netkuz.washing.dto.ProgramDTO;
import org.netkuz.washing.model.enumeration.Detergent;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;
import java.util.UUID;

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

@DisplayName("Check program controller")
public class ProgramControllerTest extends ControllerTest {

    @Test
    void getPrograms() throws Exception {
        var programId = programRepository.findAll().stream().findFirst().orElseThrow().getId();
        mockMvc.perform(get("/programs/all?id=" + programId))
                .andDo(document("{class-name}/{method-name}",
                        requestParameters(
                                parameterWithName("modelId").description("Filtering by model id").optional(),
                                parameterWithName("name").description("Filtering by name").optional(),
                                parameterWithName("description").description("Filtering by description").optional(),
                                parameterWithName("id").description("Program id").optional(),
                                parameterWithName("page").description("Page, https://docs.spring.io/spring-data/rest/docs/current/reference/html/#paging-and-sorting").optional()
                        ),
                        responseFields(
                                fieldWithPath("content").type(JsonFieldType.ARRAY).description("Content with data"),
                                fieldWithPath("content.[].id").type(JsonFieldType.STRING).description("Program id"),
                                fieldWithPath("content.[].name").type(JsonFieldType.STRING).description("Program name"),
                                fieldWithPath("content.[].model").type(JsonFieldType.OBJECT).description("Model"),
                                fieldWithPath("content.[].model.id").type(JsonFieldType.STRING).description("Model id"),
                                fieldWithPath("content.[].model.name").type(JsonFieldType.STRING).description("Model name"),
                                fieldWithPath("content.[].model.description").type(JsonFieldType.STRING).description("Model description").optional(),
                                fieldWithPath("content.[].temperature").type(JsonFieldType.NUMBER).description("Temperature").optional(),
                                fieldWithPath("content.[].detergent").type(JsonFieldType.STRING).description("Amount of detergent").optional(),
                                fieldWithPath("content.[].weight").type(JsonFieldType.NUMBER).description("Weight (g)").optional(),
                                fieldWithPath("content.[].spin").type(JsonFieldType.NUMBER).description("Amount of rotation (rpm)").optional(),
                                fieldWithPath("content.[].description").type(JsonFieldType.STRING).description("Description of model").optional(),
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
    void getProgram() throws Exception {
        var programId = programRepository.findAll().stream().findFirst().orElseThrow().getId();
        mockMvc.perform(get("/programs?id=" + programId))
                .andDo(document("{class-name}/{method-name}/{step}",
                        requestParameters(
                                parameterWithName("id").description("Program id")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("Program id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Program name"),
                                fieldWithPath("model").type(JsonFieldType.OBJECT).description("Model"),
                                fieldWithPath("model.id").type(JsonFieldType.STRING).description("Model id"),
                                fieldWithPath("model.name").type(JsonFieldType.STRING).description("Model name"),
                                fieldWithPath("model.description").type(JsonFieldType.STRING).description("Model description").optional(),
                                fieldWithPath("temperature").type(JsonFieldType.NUMBER).description("Temperature").optional(),
                                fieldWithPath("detergent").type(JsonFieldType.STRING).description("Amount of detergent").optional(),
                                fieldWithPath("weight").type(JsonFieldType.NUMBER).description("Weight (g)").optional(),
                                fieldWithPath("spin").type(JsonFieldType.NUMBER).description("Amount of rotation (rpm)").optional(),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("Description of model").optional()
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(programId.toString())))
                .andDo(print());
        mockMvc.perform(get("/programs?id=" + UUID.randomUUID()))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("error", is("Entity not found")));
    }

    @Test
    void create() throws Exception {
        var modelDTO = modelService.getModels(null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        var dto = new ProgramDTO();
        dto.setName("NAME");
        dto.setModel(modelDTO);
        dto.setTemperature(50);
        dto.setDetergent(Detergent.MAX);
        dto.setWeight(5000);
        dto.setSpin(2500);
        dto.setDescription("DESCRIPTION");

        var result = mockMvc.perform(post("/programs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(document("{class-name}/{method-name}/{step}",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Program name").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model").type(JsonFieldType.OBJECT).description("Model").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model.id").type(JsonFieldType.STRING).description("Model id").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model.name").type(JsonFieldType.STRING).description("Model name").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model.description").type(JsonFieldType.STRING).description("Model description").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("temperature").type(JsonFieldType.NUMBER).description("Temperature").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("detergent").type(JsonFieldType.STRING).description("Amount of detergent").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("weight").type(JsonFieldType.NUMBER).description("Weight (g)").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("spin").type(JsonFieldType.NUMBER).description("Amount of rotation (rpm)").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("Program description").attributes(key("constraints").value("")).optional()
                        )
                ))
                .andExpect(status().isOk()).andReturn();

        var actual = mapper.readValue(result.getResponse().getContentAsString(), ProgramDTO.class);
        assertNotNull(actual.getId());
        assertEquals(actual.getName(), "NAME");
        assertEquals(actual.getDescription(), "DESCRIPTION");

        dto.setName(null);
        mockMvc.perform(post("/programs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        var modelDTO = modelService.getModels(null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        var programId = programRepository.findAll().stream().findFirst().orElseThrow().getId();
        var dto = new ProgramDTO();
        dto.setId(programId);
        dto.setName("NAME");
        dto.setModel(modelDTO);
        dto.setTemperature(40);
        dto.setDetergent(Detergent.AVG);
        dto.setWeight(4000);
        dto.setSpin(1500);
        dto.setDescription("DESCRIPTION");
        var result = mockMvc.perform(put("/programs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(document("{class-name}/{method-name}/{step}",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("Program id").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Program name").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model").type(JsonFieldType.OBJECT).description("Model").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model.id").type(JsonFieldType.STRING).description("Model id").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model.name").type(JsonFieldType.STRING).description("Model name").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("model.description").type(JsonFieldType.STRING).description("Model description").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("temperature").type(JsonFieldType.NUMBER).description("Temperature").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("detergent").type(JsonFieldType.STRING).description("Amount of detergent").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("weight").type(JsonFieldType.NUMBER).description("Weight (g)").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("spin").type(JsonFieldType.NUMBER).description("Amount of rotation (rpm)").attributes(key("constraints").value("")).optional(),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("Program description").attributes(key("constraints").value("")).optional()
                        )
                ))
                .andExpect(status().isOk()).andReturn();
        var actual = mapper.readValue(result.getResponse().getContentAsString(), ProgramDTO.class);
        assertEquals(actual.getId(), programId);
        assertEquals(actual.getName(), "NAME");
        assertEquals(actual.getDescription(), "DESCRIPTION");

        dto.setName(null);
        mockMvc.perform(put("/programs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void _delete() throws Exception {
        var programId = programRepository.findAll().stream().findFirst().orElseThrow().getId();
        mockMvc.perform(delete("/programs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(List.of(programId))))
                .andDo(document("{class-name}/{method-name}/{step}",
                        requestFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("Program ids").attributes(key("constraints").value("Must be not null"))
                        )
                ))
                .andExpect(status().isOk());
    }
}
