package com.spo.core_app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.spo.core_app.models.Operation;

public interface OperationRepository extends JpaRepository<Operation, UUID> {
    public List<Operation> findByOperationNameIn(List<String> operationName);
    public Optional<Operation> findByOperationName(String operationName);
}
