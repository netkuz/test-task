package org.netkuz.washing.controller;

import lombok.RequiredArgsConstructor;
import org.netkuz.washing.dto.MachineDTO;
import org.netkuz.washing.dto.StateDTO;
import org.netkuz.washing.model.enumeration.Mode;
import org.netkuz.washing.model.enumeration.Status;
import org.netkuz.washing.model.enumeration.Type;
import org.netkuz.washing.model.filter.FilterMachine;
import org.netkuz.washing.service.MachineService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("machines")
public class MachineController {
    private final MachineService service;

    /**
     * Get filtered page of machines
     *
     * @param modelId      Model for filtering
     * @param serialNumber Serial number for filtering
     * @param status       Set of statuses for filtering
     * @param mode         Set of modes for filtering
     * @param type         Set of types for filtering
     * @param id           Machine id
     * @param pageable     {@link Pageable}
     * @return {@link Page<MachineDTO>}
     */
    @GetMapping("all")
    public Page<MachineDTO> getMachines(
            @RequestParam(required = false) UUID modelId,
            @RequestParam(required = false) String serialNumber,
            @RequestParam(required = false) Set<Status> status,
            @RequestParam(required = false) Set<Mode> mode,
            @RequestParam(required = false) Set<Type> type,
            @RequestParam(required = false) UUID id,
            @PageableDefault(value = 15, sort = {"serialNumber"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        var filter = new FilterMachine(modelId, serialNumber, status, mode, type);
        return service.getMachines(filter, id, pageable);
    }

    /**
     * Get machine by id
     *
     * @param id Machine id
     * @return {@link MachineDTO}
     */
    @GetMapping
    public MachineDTO getMachine(@RequestParam UUID id) {
        return service.getMachine(id);
    }

    /**
     * Create machine
     *
     * @param dto {@link MachineDTO}
     * @return {@link MachineDTO}
     */
    @PostMapping
    public MachineDTO create(@Valid @RequestBody MachineDTO dto) {
        return service.create(dto);
    }

    /**
     * Update machine
     *
     * @param dto {@link MachineDTO}
     * @return {@link MachineDTO}
     */
    @PutMapping
    public MachineDTO update(@Valid @RequestBody MachineDTO dto) {
        return service.update(dto);
    }

    /**
     * Remove machines
     *
     * @param ids {@link Collection<UUID>}
     */
    @DeleteMapping
    public void delete(@RequestBody Collection<UUID> ids) {
        service.delete(ids);
    }

    /**
     * Update machine state
     *
     * @param dto {@link MachineDTO}
     * @return {@link MachineDTO}
     */
    @PutMapping("state")
    public MachineDTO update(@Valid @RequestBody StateDTO dto) {
        return service.update(dto);
    }

    /**
     * Get all statuses
     *
     * @return List of {@link Status}
     */
    @GetMapping("state/statuses")
    @ResponseBody
    public Collection<Status> getStatus() {
        return List.of(Status.values());
    }

    /**
     * Get all modes
     *
     * @return List of {@link Mode}
     */
    @GetMapping("state/modes")
    @ResponseBody
    public Collection<Mode> getMode() {
        return List.of(Mode.values());
    }

    /**
     * Get all types
     *
     * @return List of {@link Type}
     */
    @GetMapping("state/types")
    @ResponseBody
    public Collection<Type> getType() {
        return List.of(Type.values());
    }
}
