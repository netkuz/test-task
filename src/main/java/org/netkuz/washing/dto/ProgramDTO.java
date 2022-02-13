package org.netkuz.washing.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.netkuz.washing.model.Model;
import org.netkuz.washing.model.enumeration.Detergent;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Program
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProgramDTO {
    /**
     * Id
     */
    private UUID id;
    /**
     * Name
     */
    @NotBlank
    private String name;
    /**
     * {@link ModelDTO}
     */
    @NotNull
    private ModelDTO model;
    /**
     * Temperature
     */
    private int temperature;
    /**
     * Detergent
     */
    private Detergent detergent = Detergent.NONE;
    /**
     * Weight
     */
    private int weight;
    /**
     * Spin
     */
    private int spin;
    /**
     * Description
     */
    private String description;
}
