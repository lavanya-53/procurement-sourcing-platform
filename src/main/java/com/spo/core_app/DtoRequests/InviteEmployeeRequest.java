package com.spo.core_app.DtoRequests;

import com.spo.core_app.Enums.EmploymentType;
import com.spo.core_app.Enums.UserStatus;
import com.spo.core_app.Enums.UserType;
import com.spo.core_app.models.Company;
import com.spo.core_app.models.Employee;
import com.spo.core_app.models.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class InviteEmployeeRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private LocalDate joiningDate;
    private String employeeId;
    private String designation;
    private String department;
    private String costCenter;
    private String businessUnit;
    private EmploymentType employmentType;
    private UUID managerSysId;
    private BigDecimal approvalLimit;
    private Boolean procurementApprover;
    private Boolean financeApprover;
    private List<UUID> roles;
}