package org.netkuz.washing.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.netkuz.washing.dto.ModelDTO;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Check model controller")
public class ModelControllerTest extends ControllerTest {

    @Test
    void getModels() throws Exception {
        var modelId = modelRepository.findAll().stream().findFirst().orElseThrow().getId();
        mockMvc.perform(get("/models/all?id=" + modelId))
                .andDo(document("{class-name}/{method-name}",
                        requestParameters(
                                parameterWithName("id").description("Model id").optional(),
                                parameterWithName("page").description("Page, https://docs.spring.io/spring-data/rest/docs/current/reference/html/#paging-and-sorting").optional()
                        ),
                        responseFields(
                                fieldWithPath("content").type(JsonFieldType.ARRAY).description("Content with data"),
                                fieldWithPath("content.[].id").type(JsonFieldType.STRING).description("Model id"),
                                fieldWithPath("content.[].name").type(JsonFieldType.STRING).description("Model name"),
                                fieldWithPath("content.[].description").type(JsonFieldType.STRING).description("Model description").optional(),
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
                .andExpect(jsonPath("$['numberOfElements']", is(10)))
                .andExpect(jsonPath("$['totalElements']", is(10)))
                .andDo(print());
    }

    @Test
    void getModel() throws Exception {
        var modelId = modelRepository.findAll().stream().findFirst().orElseThrow().getId();
        mockMvc.perform(get("/models?id=" + modelId))
                .andDo(document("{class-name}/{method-name}/{step}",
                        requestParameters(
                                parameterWithName("id").description("Model id")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("Model id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Model name"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("Model description").optional()
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(modelId.toString())))
                .andDo(print());
        mockMvc.perform(get("/models?id=" + UUID.randomUUID()))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("error", is("Entity not found")));
    }

    @Test
    void create() throws Exception {
        var dto = new ModelDTO();
        dto.setName("NAME");
        dto.setDescription("DESCRIPTION");

        var result = mockMvc.perform(post("/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(document("{class-name}/{method-name}/{step}",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Model name").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("Model description").attributes(key("constraints").value("")).optional()
                        )
                ))
                .andExpect(status().isOk()).andReturn();

        var actual = mapper.readValue(result.getResponse().getContentAsString(), ModelDTO.class);
        assertNotNull(actual.getId());
        assertEquals(actual.getName(), "NAME");
        assertEquals(actual.getDescription(), "DESCRIPTION");

        dto.setName(null);
        mockMvc.perform(post("/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update() throws Exception {
        var modelId = modelRepository.findAll().stream().findFirst().orElseThrow().getId();
        var dto = new ModelDTO();
        dto.setId(modelId);
        dto.setName("NAME");
        dto.setDescription("DESCRIPTION");
        var result = mockMvc.perform(put("/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(document("{class-name}/{method-name}/{step}",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.STRING).description("Model id").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("Model name").attributes(key("constraints").value("Must be not null")),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("Model description").attributes(key("constraints").value("")).optional()
                        )
                ))
                .andExpect(status().isOk()).andReturn();
        var actual = mapper.readValue(result.getResponse().getContentAsString(), ModelDTO.class);
        assertEquals(actual.getId(), modelId);
        assertEquals(actual.getName(), "NAME");
        assertEquals(actual.getDescription(), "DESCRIPTION");

        dto.setName(null);
        mockMvc.perform(put("/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    void _delete() throws Exception {
        var modelId = modelRepository.findAll().stream().findFirst().orElseThrow().getId();
        mockMvc.perform(delete("/models")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(List.of(modelId))))
                .andDo(document("{class-name}/{method-name}/{step}",
                        requestFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("Model ids").attributes(key("constraints").value("Must be not null"))
                        )
                ))
                .andExpect(status().isOk());
    }
}
