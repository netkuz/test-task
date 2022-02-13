package org.netkuz.washing.controller;

import lombok.RequiredArgsConstructor;
import org.netkuz.washing.dto.ModelDTO;
import org.netkuz.washing.service.ModelService;
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
@RequestMapping("models")
public class ModelController {
    private final ModelService service;

    /**
     * Get page of models
     *
     * @param id       ID of dashboard
     * @param pageable {@link Pageable}
     * @return {@link Page < ModelDTO >}
     */
    @GetMapping("all")
    public Page<ModelDTO> getModels(
            @RequestParam(required = false) UUID id,
            @PageableDefault(value = 15, sort = {"name"}, direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return service.getModels(id, pageable);
    }

    /**
     * Get model by id
     *
     * @param id model id
     * @return {@link ModelDTO}
     */
    @GetMapping
    public ModelDTO getModel(@RequestParam UUID id) {
        return service.getModel(id);
    }

    /**
     * Create model
     *
     * @param dto {@link ModelDTO}
     * @return {@link ModelDTO}
     */
    @PostMapping
    public ModelDTO create(@Valid @RequestBody ModelDTO dto) {
        return service.create(dto);
    }

    /**
     * Update model
     *
     * @param dto {@link ModelDTO}
     * @return {@link ModelDTO}
     */
    @PutMapping
    public ModelDTO update(@Valid @RequestBody ModelDTO dto) {
        return service.update(dto);
    }

    /**
     * Remove models
     *
     * @param ids {@link Collection <UUID>}
     */
    @DeleteMapping
    public void delete(@RequestBody Collection<UUID> ids) {
        service.delete(ids);
    }
}
