package com.spo.core_app.models;


import com.spo.core_app.Enums.EmploymentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee extends User {
    @Column(unique = true, nullable = false)
    private String employeeId;

    private String designation;

    private String department;

    private String costCenter;

    private String businessUnit;


    @Enumerated
    private EmploymentType employmentType;

    @ManyToOne
    private Employee manager;//"manager is a variable whose data type is Employee, so it can refer to one complete Employee objec

    private BigDecimal approvalLimit;//How much money this employee is allowed to approve.

    private Boolean procurementApprover;//Can this employee approve procurement requests?

    private Boolean financeApprover;//Can this employee approve procurement requests?
}
