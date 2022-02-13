package org.netkuz.washing.repository;

import org.netkuz.washing.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MachineRepository extends JpaRepository<Machine, UUID>, JpaSpecificationExecutor<Machine> {
}
