package org.netkuz.washing.mapper;

import org.netkuz.washing.dto.ProgramDTO;
import org.netkuz.washing.model.Program;

import javax.validation.Valid;
import java.util.Objects;

/**
 * Program mapper
 */
public class ProgramMapper {
    /**
     * Convert DTO to entity
     *
     * @param value {@link Program}
     * @return {@link ProgramDTO}
     */
    public static Program toEntity(@Valid ProgramDTO value) {
        var entity = new Program();
        if (Objects.nonNull(value.getId())) {
            entity.setId(value.getId());
        }
        entity.setName(value.getName());
        entity.setModel(ModelMapper.toEntity(value.getModel()));
        entity.setTemperature(value.getTemperature());
        entity.setDetergent(value.getDetergent());
        entity.setWeight(value.getWeight());
        entity.setSpin(value.getSpin());
        entity.setDescription(value.getDescription());
        return entity;
    }

    /**
     * Convert entity to DTO
     *
     * @param entity {@link Program}
     * @return {@link ProgramDTO}
     */
    public static ProgramDTO toDTO(Program entity) {
        var value = new ProgramDTO();
        value.setId(entity.getId());
        value.setName(entity.getName());
        value.setModel(ModelMapper.toDTO(entity.getModel()));
        value.setTemperature(entity.getTemperature());
        value.setDetergent(entity.getDetergent());
        value.setWeight(entity.getWeight());
        value.setSpin(entity.getSpin());
        value.setDescription(entity.getDescription());
        return value;
    }
}
