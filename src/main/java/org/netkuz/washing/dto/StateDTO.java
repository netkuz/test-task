package org.netkuz.washing.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.netkuz.washing.model.enumeration.Mode;
import org.netkuz.washing.model.enumeration.Status;
import org.netkuz.washing.model.enumeration.Type;

import java.util.UUID;

/**
 * Machine state
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateDTO {
    /**
     * Machine id
     */
    private UUID machineId;
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
