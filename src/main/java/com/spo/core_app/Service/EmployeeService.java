package com.spo.core_app.Service;


import com.spo.core_app.Constants.MessageConstants;
import com.spo.core_app.Constants.SystemConstants;
import com.spo.core_app.Enums.EmploymentType;
import com.spo.core_app.Enums.UserStatus;
import com.spo.core_app.Enums.UserType;
import com.spo.core_app.Exceptions.InvalidCredentialException;
import com.spo.core_app.Repository.EmployeeRepository;
import com.spo.core_app.models.Company;
import com.spo.core_app.models.Employee;
import com.spo.core_app.models.globalrecord;
import com.spo.core_app.models.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class EmployeeService {
    private RoleService roleservice;
    private EmployeeRepository emprepo;
    public EmployeeService(RoleService roleservice,EmployeeRepository emprepo){
        this.roleservice=roleservice;
        this.emprepo=emprepo;
    }
    //It creates a Super Admin employee for that company
    //We assign values to the Employee because the system wants to create a complete, usable default admin employee for the newly created company
    public Employee CreateSuperAdminForCompany(Company company){
        Roles roles=roleservice.CreateRole(company);
        Employee emp= Employee.builder().employeeId(globalrecord.generate(SystemConstants.Employee_Entity_Name))
                .firstName(company.getLegalName() + "_MAINT")
                .lastName("ADMIN")
                .email(company.getContactEmail())
                .phoneNumber(company.getPrimaryContactNumber())
                .userType(UserType.procurement_user).status(UserStatus.pending_activation)
                .company(company)
                .addressLine1(company.getAddressLine1())
                .addressLine2(company.getAddressLine2())
                .addressLine3(company.getAddressLine3())
                .joiningDate(LocalDate.now())
                .lastLoginDate(null)
                .emailVerified(false)
                .mfaEnabled(false)
                .role(List.of(roles))
                .businessUnit("ADMIN")
                .costCenter("ADMIN")
                .designation("SUPER_ADMIN")
                .department("ADMIN")
                .costCenter("ADMIN")
                .businessUnit("ADMIN")
                .employmentType(EmploymentType.FullTime)
                .manager(null)
                .approvalLimit(BigDecimal.ZERO)
                .procurementApprover(true)
                .financeApprover(true)
                .build();
        return emprepo.save(emp);
    }
    public Employee ValidateEmployeeCredentials(String email,String Password){
        Employee emp=emprepo.findByEmail(email);
        if(emp!=null && emp.getPassword().equals(Password)){
            return emp;
        }
        throw new InvalidCredentialException(MessageConstants.INVALID_CREDENTIALS_MESSAGE);
    }
}
