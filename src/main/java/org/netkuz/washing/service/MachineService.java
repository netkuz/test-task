package org.netkuz.washing.service;

import org.netkuz.washing.dto.MachineDTO;
import org.netkuz.washing.dto.StateDTO;
import org.netkuz.washing.model.filter.FilterMachine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

/**
 * Machine service operation
 */
public interface MachineService {
    /**
     * Get page of machine
     *
     * @param filter   Filter
     * @param id       machine id
     * @param pageable Page param
     * @return {@link Page <MachineDTO>}
     */
    Page<MachineDTO> getMachines(FilterMachine filter, UUID id, Pageable pageable);

    /**
     * Get machine by id
     *
     * @param id machine id
     * @return {@link MachineDTO}
     */
    MachineDTO getMachine(UUID id);

    /**
     * Create machine
     *
     * @param machine {@link MachineDTO}
     * @return new value {@link MachineDTO}
     */
    MachineDTO create(@Valid MachineDTO machine);

    /**
     * Update machine
     *
     * @param machine {@link MachineDTO}
     * @return updated value {@link MachineDTO}
     */
    MachineDTO update(@Valid MachineDTO machine);

    /**
     * Delete machine
     *
     * @param ids {@link Collection <UUID>} Ids of machine
     */
    void delete(Collection<UUID> ids);

    /**
     * Update machine state
     *
     * @param state {@link StateDTO}
     * @return updated value {@link MachineDTO}
     */
    MachineDTO update(@Valid StateDTO state);
}
