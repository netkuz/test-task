package org.netkuz.washing.service;

import org.netkuz.washing.dto.ModelDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

/**
 * Model service operation
 */
public interface ModelService {
    /**
     * Get page of model
     *
     * @param id       Model id
     * @param pageable Page param
     * @return {@link Page <ModelDTO>}
     */
    Page<ModelDTO> getModels(UUID id, Pageable pageable);

    /**
     * Get model by id
     *
     * @param id Model id
     * @return {@link ModelDTO}
     */
    ModelDTO getModel(UUID id);

    /**
     * Create model
     *
     * @param model {@link ModelDTO}
     * @return new value {@link ModelDTO}
     */
    ModelDTO create(@Valid ModelDTO model);

    /**
     * Update model
     *
     * @param model {@link ModelDTO}
     * @return updated value {@link ModelDTO}
     */
    ModelDTO update(@Valid ModelDTO model);


    /**
     * Delete models
     *
     * @param ids {@link Collection <UUID>} Ids of models
     */
    void delete(Collection<UUID> ids);
}
