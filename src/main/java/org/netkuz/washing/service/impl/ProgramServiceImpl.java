package org.netkuz.washing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.netkuz.washing.dto.ProgramDTO;
import org.netkuz.washing.mapper.ProgramMapper;
import org.netkuz.washing.model.filter.FilterProgram;
import org.netkuz.washing.repository.ProgramRepository;
import org.netkuz.washing.service.ProgramService;
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

import static org.netkuz.washing.mapper.ProgramMapper.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProgramServiceImpl implements ProgramService {
    private static final String ENTITY_NOT_FOUND = "Entity not found";
    private static final String ENTITY_EXIST = "Entity exist";
    private final ProgramRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<ProgramDTO> getPrograms(FilterProgram filter, UUID id, Pageable pageable) {
        final var filterSpecification = filter.getSpecification();
        var page = repository.findAll(filterSpecification, pageable);
        if (id != null) {
            var find = page.getContent().stream().anyMatch(x -> x.getId().equals(id));
            while (!find) {
                var next = page.getPageable().next();
                page = repository.findAll(filterSpecification, next);
                find = page.getContent().stream().anyMatch(x -> x.getId().equals(id));
                if (!find) {
                    find = page.isLast();
                }
            }
        }
        var content = page.getContent().stream().map(ProgramMapper::toDTO).collect(Collectors.toList());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramDTO getProgram(UUID id) {
        return repository.findById(id).map(ProgramMapper::toDTO).orElseThrow(() -> {
            throw new EntityNotFoundException(ENTITY_NOT_FOUND);
        });
    }

    @Override
    @Transactional
    public ProgramDTO create(ProgramDTO dto) {
        if (dto.getId() != null) {
            throw new EntityExistsException(ENTITY_EXIST);
        }
        return toDTO(repository.save(toEntity(dto)));
    }

    @Override
    @Transactional
    public ProgramDTO update(ProgramDTO dto) {
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
