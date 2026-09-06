package com.spo.core_app.models;

import com.spo.core_app.Enums.EmploymentType;
import com.spo.core_app.Enums.UserStatus;
import com.spo.core_app.Enums.UserType;
import com.spo.core_app.Repository.EmployeeRepository;
import com.spo.core_app.Repository.RoleRepository;
import com.spo.core_app.Repository.OperationRepository;
import com.spo.core_app.models.Employee;
import com.spo.core_app.models.Roles;
import com.spo.core_app.models.Operation;
import com.spo.core_app.models.globalrecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
//CommandLineRunner is a Spring Boot feature that lets you run some Java code automatically when your application starts.
public class DataInitializer implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final OperationRepository operationRepository;

    public DataInitializer(
            EmployeeRepository employeeRepository,
            RoleRepository roleRepository,
            OperationRepository operationRepository) {

        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.operationRepository = operationRepository;
    }

    @Override
    //The ... is called varargs (variable arguments)
//    "This method can receive zero or more String values."
    public void run(String... args) {

        String adminEmail = "admin@procurex.com";

        // Check whether the Internal Super Admin already exists
        Employee existingAdmin =
                employeeRepository.findByEmail(adminEmail);

        if (existingAdmin != null) {
            System.out.println("Internal Super Admin already exists.");
            return;
        }

        // Fetch operations required by the Internal Super Admin
        List<Operation> internalOperations =
                operationRepository.findByOperationNameIn(List.of(
                        "INVITE_USER",
                        "CREATE_ROLE",
                        "VIEW_SUPPLIERS",
                        "APPROVE_COMPANY",
                        "REJECT_COMPANY"
                ));

        // Create the Internal Super Admin role
        Roles internalSuperAdminRole = Roles.builder()
                .RoleId(globalrecord.generate("ROLE"))
                .RoleName("INTERNAL_SUPER_ADMIN")
                .operations(internalOperations)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("SYSTEM")
                .updatedBy("SYSTEM")
                .build();

        roleRepository.save(internalSuperAdminRole);

        // Create the Internal Super Admin employee
        Employee admin = Employee.builder()
                .employeeId(globalrecord.generate("EMP"))
                .firstName("ProcuraX")
                .lastName("Super Admin")
                .email(adminEmail)
                .Password("Temp@123")
                .userType(UserType.Internal_user)
                .status(UserStatus.Active)
                .designation("SUPER_ADMIN")
                .department("ADMIN")
                .businessUnit("INTERNAL")
                .costCenter("ADMIN")
                .employmentType(EmploymentType.FullTime)
                .joiningDate(LocalDate.now())
                .lastLoginDate(null)
                .emailVerified(true)
                .mfaEnabled(false)
                .manager(null)
                .approvalLimit(BigDecimal.ZERO)
                .procurementApprover(true)
                .financeApprover(true)
                .company(null)
                .role(List.of(internalSuperAdminRole))
                .build();

        employeeRepository.save(admin);

        System.out.println("==========================================");
        System.out.println("Internal Super Admin created successfully");
        System.out.println("Email    : " + adminEmail);
        System.out.println("Password : Temp@123");
        System.out.println("Role     : INTERNAL_SUPER_ADMIN");
        System.out.println("==========================================");
    }
}