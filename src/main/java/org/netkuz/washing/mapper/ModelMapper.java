package org.netkuz.washing.mapper;

import org.netkuz.washing.dto.ModelDTO;
import org.netkuz.washing.model.Model;

import javax.validation.Valid;
import java.util.Objects;

/**
 * Model mapper
 */
public class ModelMapper {
    /**
     * Convert DTO to entity
     *
     * @param value {@link Model}
     * @return {@link ModelDTO}
     */
    public static Model toEntity(@Valid ModelDTO value) {
        var entity = new Model();
        if (Objects.nonNull(value.getId())) {
            entity.setId(value.getId());
        }
        entity.setName(value.getName());
        entity.setDescription(value.getDescription());
        return entity;
    }

    /**
     * Convert entity to DTO
     *
     * @param entity {@link Model}
     * @return {@link ModelDTO}
     */
    public static ModelDTO toDTO(Model entity) {
        var value = new ModelDTO();
        value.setId(entity.getId());
        value.setName(entity.getName());
        value.setDescription(entity.getDescription());
        return value;
    }
}
