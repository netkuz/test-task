package org.netkuz.washing.repository;

import org.netkuz.washing.model.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProgramRepository extends JpaRepository<Program, UUID>, JpaSpecificationExecutor<Program> {
}
