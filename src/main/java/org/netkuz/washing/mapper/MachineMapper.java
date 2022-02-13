package org.netkuz.washing.mapper;

import org.netkuz.washing.dto.MachineDTO;
import org.netkuz.washing.model.Machine;

import javax.validation.Valid;
import java.util.Objects;

/**
 * Machine mapper
 */
public class MachineMapper {
    /**
     * Convert DTO to entity
     *
     * @param value {@link Machine}
     * @return {@link MachineDTO}
     */
    public static Machine toEntity(@Valid MachineDTO value) {
        var entity = new Machine();
        if (Objects.nonNull(value.getId())) {
            entity.setId(value.getId());
        }
        entity.setSerialNumber(value.getSerialNumber());
        entity.setModel(ModelMapper.toEntity(value.getModel()));
        if (Objects.nonNull(value.getProgram())) {
            entity.setProgram(ProgramMapper.toEntity(value.getProgram()));
        }
        entity.setStatus(value.getStatus());
        entity.setMode(value.getMode());
        entity.setType(value.getType());
        return entity;
    }

    /**
     * Convert entity to DTO
     *
     * @param entity {@link Machine}
     * @return {@link MachineDTO}
     */
    public static MachineDTO toDTO(Machine entity) {
        var value = new MachineDTO();
        value.setId(entity.getId());
        value.setSerialNumber(entity.getSerialNumber());
        value.setModel(ModelMapper.toDTO(entity.getModel()));
        if (Objects.nonNull(entity.getProgram())) {
            value.setProgram(ProgramMapper.toDTO(entity.getProgram()));
        }
        value.setStatus(entity.getStatus());
        value.setMode(entity.getMode());
        value.setType(entity.getType());
        return value;
    }
}
