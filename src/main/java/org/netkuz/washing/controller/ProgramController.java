package org.netkuz.washing.controller;

import lombok.RequiredArgsConstructor;
import org.netkuz.washing.dto.ProgramDTO;
import org.netkuz.washing.model.filter.FilterProgram;
import org.netkuz.washing.service.ProgramService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("programs")
public class ProgramController {
    private final ProgramService service;

    /**
     * Get filtered page of programs
     *
     * @param modelId     Model for filtering
     * @param name        Name for filtering
     * @param description Description for filtering
     * @param id          Program id
     * @param pageable    {@link Pageable}
     * @return {@link Page < ProgramDTO >}
     */
    @GetMapping("all")
    public Page<ProgramDTO> getPrograms(
            @RequestParam(required = false) UUID modelId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) UUID id,
            @PageableDefault(value = 15, sort = {"name"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        var filter = new FilterProgram(modelId, name, description);
        return service.getPrograms(filter, id, pageable);
    }

    /**
     * Get program by id
     *
     * @param id Program id
     * @return {@link ProgramDTO}
     */
    @GetMapping
    public ProgramDTO getProgram(@RequestParam UUID id) {
        return service.getProgram(id);
    }

    /**
     * Create program
     *
     * @param dto {@link ProgramDTO}
     * @return {@link ProgramDTO}
     */
    @PostMapping
    public ProgramDTO create(@Valid @RequestBody ProgramDTO dto) {
        return service.create(dto);
    }

    /**
     * Update program
     *
     * @param dto {@link ProgramDTO}
     * @return {@link ProgramDTO}
     */
    @PutMapping
    public ProgramDTO update(@Valid @RequestBody ProgramDTO dto) {
        return service.update(dto);
    }

    /**
     * Remove programs
     *
     * @param ids {@link Collection <UUID>}
     */
    @DeleteMapping
    public void delete(@RequestBody Collection<UUID> ids) {
        service.delete(ids);
    }
}
