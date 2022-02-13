package org.netkuz.washing.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import org.netkuz.washing.model.Model;
import org.netkuz.washing.model.enumeration.Mode;
import org.netkuz.washing.model.enumeration.Status;
import org.netkuz.washing.model.enumeration.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Machine
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MachineDTO {
    /**
     * Id
     */
    private UUID id;
    /**
     * Serial number
     */
    @NotBlank
    private String serialNumber;
    /**
     * {@link ModelDTO}
     */
    @NotNull
    private ModelDTO model;
    /**
     * {@link ProgramDTO}
     */
    private ProgramDTO program;
    /**
     * {@link Status}
     */
    private Status status = Status.IDLE;
    /**
     * {@link Mode}
     */
    private Mode mode = Mode.NONE;
    /**
     * {@link Type}
     */
    private Type type = Type.MANUAL;
}
