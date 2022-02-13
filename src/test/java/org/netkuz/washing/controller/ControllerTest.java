package org.netkuz.washing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.netkuz.washing.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class ControllerTest extends BaseTest {
    protected final ObjectMapper mapper = new ObjectMapper();
    protected MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
                    chain.doFilter(request, response);
                })
                .build();
    }
}
