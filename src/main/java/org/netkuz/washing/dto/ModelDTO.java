package org.netkuz.washing.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

/**
 * Model
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelDTO {
    /**
     * Id
     */
    private UUID id;
    /**
     * Model name
     */
    @NotBlank
    private String name;
    /**
     * Description
     */
    private String description;
}
