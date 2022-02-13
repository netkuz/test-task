package org.netkuz.washing.service;

import org.netkuz.washing.dto.ProgramDTO;
import org.netkuz.washing.model.filter.FilterProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

/**
 * Program service operation
 */
public interface ProgramService {
    /**
     * Get page of program
     *
     * @param filter   Filter
     * @param id       program id
     * @param pageable Page param
     * @return {@link Page <ProgramDTO>}
     */
    Page<ProgramDTO> getPrograms(FilterProgram filter, UUID id, Pageable pageable);

    /**
     * Get program by id
     *
     * @param id program id
     * @return {@link ProgramDTO}
     */
    ProgramDTO getProgram(UUID id);

    /**
     * Create program
     *
     * @param program {@link ProgramDTO}
     * @return new value {@link ProgramDTO}
     */
    ProgramDTO create(@Valid ProgramDTO program);

    /**
     * Update program
     *
     * @param program {@link ProgramDTO}
     * @return updated value {@link ProgramDTO}
     */
    ProgramDTO update(@Valid ProgramDTO program);

    /**
     * Delete program
     *
     * @param ids {@link Collection <UUID>} Ids of program
     */
    void delete(Collection<UUID> ids);
}
