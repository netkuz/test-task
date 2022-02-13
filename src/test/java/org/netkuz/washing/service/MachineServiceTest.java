package org.netkuz.washing.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.netkuz.washing.BaseTest;
import org.netkuz.washing.dto.MachineDTO;
import org.netkuz.washing.dto.StateDTO;
import org.netkuz.washing.model.enumeration.Mode;
import org.netkuz.washing.model.enumeration.Status;
import org.netkuz.washing.model.enumeration.Type;
import org.netkuz.washing.model.filter.FilterMachine;
import org.netkuz.washing.model.filter.FilterProgram;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Check machine service")
public class MachineServiceTest extends BaseTest {

    @Test
    void getMachines() {
        var machines = machineService.getMachines(new FilterMachine(), null, PageRequest.of(0, 20));
        assertTrue(machines.getTotalElements() > 0);
        assertEquals(3, machines.getTotalPages());
    }

    @Test
    void getMachine() {
        var machine = machineRepository.findAll().stream().findFirst().orElseThrow();
        assertNotNull(machineService.getMachine(machine.getId()));
        assertThrows(EntityNotFoundException.class, () -> machineService.getMachine(UUID.randomUUID()));
    }

    @Test
    void create() {
        var modelDTO = modelService.getModels(null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        var machineDTO = new MachineDTO();
        machineDTO.setSerialNumber("serial_number_123");
        machineDTO.setModel(modelDTO);
        assertDoesNotThrow(() -> machineService.create(machineDTO));

        assertThrows(Exception.class, () -> machineService.create(machineDTO));

        var machineExist = machineService.getMachines(new FilterMachine(), null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        assertThrows(EntityExistsException.class, () -> machineService.create(machineExist));
    }

    @Test
    void update() {
        var programDTO = programService.getPrograms(new FilterProgram(), null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        var machineDTO = machineService.getMachines(new FilterMachine(), null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        machineDTO.setProgram(programDTO);
        machineDTO.setStatus(Status.STARTING);
        machineDTO.setMode(Mode.WASHING);
        machineDTO.setType(Type.MANUAL);
        assertDoesNotThrow(() -> machineService.update(machineDTO));

        var modelDTO = modelService.getModels(null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        var machineNotExist = new MachineDTO();
        machineNotExist.setId(UUID.randomUUID());
        machineNotExist.setSerialNumber("000-000");
        machineNotExist.setModel(modelDTO);
        assertThrows(EntityNotFoundException.class, () -> machineService.update(machineNotExist));
    }

    @Test
    void delete() {
        var machineDTO = machineService.getMachines(new FilterMachine(), null, PageRequest.of(0, 20)).stream().findFirst().orElseThrow();
        assertDoesNotThrow(() -> machineService.delete(List.of(machineDTO.getId())));
    }

    @Test
    void updateState() {
        var machineId = machineRepository.findAll().stream().findFirst().orElseThrow().getId();
        var stateDTO = new StateDTO();
        stateDTO.setMachineId(machineId);
        stateDTO.setStatus(Status.STOPPING);
        stateDTO.setMode(Mode.DRYING);
        stateDTO.setType(Type.SCHEDULED);
        assertDoesNotThrow(() -> machineService.update(stateDTO));
    }
}
