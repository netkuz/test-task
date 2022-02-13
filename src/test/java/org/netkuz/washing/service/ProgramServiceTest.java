package org.netkuz.washing.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.netkuz.washing.BaseTest;
import org.netkuz.washing.dto.ProgramDTO;
import org.netkuz.washing.model.enumeration.Detergent;
import org.netkuz.washing.model.filter.FilterProgram;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Check program service")
public class ProgramServiceTest extends BaseTest {

    @Test
    void getPrograms() {
        var programs = programService.getPrograms(new FilterProgram(), null, PageRequest.of(0, 20));
        assertTrue(programs.getTotalElements() > 0);
        assertEquals(3, programs.getTotalPages());
    }

    @Test
    void getProgram() {
        var program = programRepository.findAll().stream().findFirst().orElseThrow();
        assertNotNull(programService.getProgram(program.getId()));
        assertThrows(EntityNotFoundException.class, () -> programService.getProgram(UUID.randomUUID()));
    }

    @Test
    void create() {
        var modelDTO = modelService.getModels(null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        var programDTO = new ProgramDTO();
        programDTO.setName("program_123");
        programDTO.setModel(modelDTO);
        programDTO.setTemperature(50);
        programDTO.setSpin(2500);
        programDTO.setWeight(2500);
        programDTO.setDetergent(Detergent.MAX);
        programDTO.setDescription("Description create");
        assertDoesNotThrow(() -> programService.create(programDTO));

        var machineExist = programService.getPrograms(new FilterProgram(), null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        assertThrows(EntityExistsException.class, () -> programService.create(machineExist));
    }

    @Test
    void update() {
        var programDTO = programService.getPrograms(new FilterProgram(), null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        programDTO.setName("program_999");
        programDTO.setTemperature(50);
        programDTO.setSpin(3000);
        programDTO.setWeight(5000);
        programDTO.setDetergent(Detergent.NONE);
        programDTO.setDescription("Description update");
        assertDoesNotThrow(() -> programService.update(programDTO));

        var modelDTO = modelService.getModels(null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        var programNotExist = new ProgramDTO();
        programNotExist.setId(UUID.randomUUID());
        programDTO.setName("program_111");
        programDTO.setModel(modelDTO);
        programDTO.setTemperature(60);
        programDTO.setSpin(1500);
        programDTO.setWeight(4000);
        programDTO.setDetergent(Detergent.MIN);
        programDTO.setDescription("Description update");
        assertThrows(EntityNotFoundException.class, () -> programService.update(programNotExist));
    }

    @Test
    void delete() {
        var programDTO = programService.getPrograms(new FilterProgram(), null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        assertDoesNotThrow(() -> programService.delete(List.of(programDTO.getId())));
    }
}
