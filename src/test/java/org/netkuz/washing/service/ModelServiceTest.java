package org.netkuz.washing.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.netkuz.washing.BaseTest;
import org.netkuz.washing.dto.ModelDTO;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Check model service")
public class ModelServiceTest extends BaseTest {

    @Test
    void getModels() {
        var models = modelService.getModels(null, PageRequest.of(0, 20));
        assertTrue(models.getTotalElements() > 0);
        assertEquals(1, models.getTotalPages());
    }

    @Test
    void getModel() {
        var model = modelRepository.findAll().stream().findFirst().orElseThrow();
        assertNotNull(modelService.getModel(model.getId()));
        assertThrows(EntityNotFoundException.class, () -> modelService.getModel(UUID.randomUUID()));
    }

    @Test
    void create() {
        var modelDTO = new ModelDTO();
        modelDTO.setName("model_123");
        modelDTO.setDescription("Create model");
        assertDoesNotThrow(() -> modelService.create(modelDTO));

        assertThrows(Exception.class, () -> modelService.create(modelDTO));

        var modelExist = modelService.getModels(null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        assertThrows(EntityExistsException.class, () -> modelService.create(modelExist));
    }

    @Test
    void update() {
        var modelDTO = modelService.getModels(null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        modelDTO.setName("99999");
        assertDoesNotThrow(() -> modelService.update(modelDTO));

        var modelNotExist = new ModelDTO();
        modelNotExist.setId(UUID.randomUUID());
        modelNotExist.setName("model_123");
        modelNotExist.setDescription("Create model");
        assertThrows(EntityNotFoundException.class, () -> modelService.update(modelNotExist));
    }

    @Test
    void delete() {
        var modelDTO = modelService.getModels(null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        assertDoesNotThrow(() -> modelService.delete(List.of(modelDTO.getId())));
    }
}
