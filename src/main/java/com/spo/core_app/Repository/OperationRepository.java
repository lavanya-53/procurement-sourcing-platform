package com.spo.core_app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;
import com.spo.core_app.models.Operation;

public interface OperationRepository extends JpaRepository<Operation, UUID> {
    public Optional<Operation> findByOperationName(String operationName);
}
