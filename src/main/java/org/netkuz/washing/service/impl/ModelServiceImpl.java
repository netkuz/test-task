package org.netkuz.washing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.netkuz.washing.dto.ModelDTO;
import org.netkuz.washing.mapper.ModelMapper;
import org.netkuz.washing.repository.ModelRepository;
import org.netkuz.washing.service.ModelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.netkuz.washing.mapper.ModelMapper.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class ModelServiceImpl implements ModelService {
    private static final String ENTITY_NOT_FOUND = "Entity not found";
    private static final String ENTITY_EXIST = "Entity exist";
    private final ModelRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<ModelDTO> getModels(UUID id, Pageable pageable) {
        var page = repository.findAll(pageable);
        if (id != null) {
            var find = page.getContent().stream().anyMatch(x -> x.getId().equals(id));
            while (!find) {
                var next = page.getPageable().next();
                page = repository.findAll(next);
                find = page.getContent().stream().anyMatch(x -> x.getId().equals(id));
                if (!find) {
                    find = page.isLast();
                }
            }
        }
        var content = page.getContent().stream().map(ModelMapper::toDTO).collect(Collectors.toList());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public ModelDTO getModel(UUID id) {
        return repository.findById(id).map(ModelMapper::toDTO).orElseThrow(() -> {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND);
        });
    }

    @Override
    @Transactional
    public ModelDTO create(ModelDTO dto) {
        if (dto.getId() != null) {
            throw new EntityExistsException(ENTITY_EXIST);
        }
        return toDTO(repository.save(toEntity(dto)));
    }

    @Override
    @Transactional
    public ModelDTO update(ModelDTO dto) {
        repository.findById(dto.getId()).orElseThrow(() -> {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND);
        });
        return toDTO(repository.save(toEntity(dto)));
    }

    @Override
    @Transactional
    public void delete(Collection<UUID> ids) {
        repository.deleteAll(repository.findAllById(ids));
    }
}
