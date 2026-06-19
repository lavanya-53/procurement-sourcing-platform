package com.spo.core_app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="Operations")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Operation extends globalrecord {
    private String id;//human readable id->OPR-12 OPR-13
    @Column(unique=true,nullable=false)
    private String operationName;
    private String operationCategory;

}
