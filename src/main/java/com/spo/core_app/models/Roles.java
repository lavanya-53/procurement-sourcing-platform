package com.spo.core_app.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
@Entity
@Table(name="Role")
public class Roles extends globalrecord{
    private String RoleId;
    @Column(unique=true)
    private String RoleName;//infosys_procuremntspeciliast,tcs_procurementSpecialist
    @ManyToMany
    private List<Operation> operations;
}
