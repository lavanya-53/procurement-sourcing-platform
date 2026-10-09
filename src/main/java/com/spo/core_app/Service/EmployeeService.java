package com.spo.core_app.Service;


import com.spo.core_app.Constants.MessageConstants;
import com.spo.core_app.Constants.SystemConstants;
import com.spo.core_app.DtoRequests.InviteEmployeeRequest;
import com.spo.core_app.DtoRequests.SupplierRegistrationDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
@Slf4j
public class EmployeeService {
    private RoleService roleservice;
    private EmployeeRepository emprepo;

    @Autowired
    public EmployeeService(RoleService roleservice, EmployeeRepository emprepo) {
        this.roleservice = roleservice;
        this.emprepo = emprepo;
    }

    //It creates a Super Admin employee for that company
    //We assign values to the Employee because the system wants to create a complete, usable default admin employee for the newly created company
    public Employee CreateSuperAdminForCompany(Company company) {
        Roles roles = roleservice.CreateMAintRole(company);
        Employee emp = Employee.builder().employeeId(globalrecord.generate(SystemConstants.Employee_Entity_Name))
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
                .Password("Temp@123")
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

    public Employee ValidateEmployeeCredentials(String email, String Password) {
        Employee emp = emprepo.findByEmail(email);
        if (emp != null && emp.getPassword().equals(Password)) {
            return emp;
        }
        throw new InvalidCredentialException(MessageConstants.INVALID_CREDENTIALS_MESSAGE);
    }

    public Employee getEmployeebyemail(String Email) {
        //Use the repository that belongs to THIS EmployeeService object.
        return this.emprepo.findByEmail(Email);
    }

    public Employee inviteEmployee(
            InviteEmployeeRequest inviteEmployeeRequest,
            Employee invitor
    ) {
        Employee manager = emprepo.findById(inviteEmployeeRequest.getManagerSysId()).orElse(null);
        List<Roles> roles = roleservice.fetchRolesById(inviteEmployeeRequest.getRoles());
        Employee invitee = Employee.builder()
                .manager(manager)
                .addressLine1(inviteEmployeeRequest.getAddressLine1())
                .addressLine2(inviteEmployeeRequest.getAddressLine2())
                .addressLine3(inviteEmployeeRequest.getAddressLine3())
                .businessUnit(inviteEmployeeRequest.getBusinessUnit())
                .company(invitor.getCompany())
                .employeeId(inviteEmployeeRequest.getEmployeeId())
                .role(roles)
                .build();
        emprepo.save(invitee);
         return invitee;

    }
    //supplier
    public Employee createSupplierAdmin(
            Company company,
            SupplierRegistrationDto request) {

        Roles supplierAdminRole =
                roleservice.createSupplierAdminRole(company);

        Employee supplierAdmin = Employee.builder()

                .employeeId(globalrecord.generate(SystemConstants.Employee_Entity_Name))

                .firstName(request.getAdminFirstName())

                .lastName(request.getAdminLastName())

                .email(request.getAdminEmail())

                .Password(request.getAdminPassword())

                .phoneNumber(request.getPrimaryContactNumber())

                .userType(UserType.Supplier_user)

                .status(UserStatus.pending_activation)

                .company(company)

                .addressLine1(request.getAddressLine1())

                .addressLine2(request.getAddressLine2())

                .addressLine3(request.getAddressLine3())

                .joiningDate(LocalDate.now())

                .lastLoginDate(null)

                .emailVerified(false)

                .mfaEnabled(false)

                .role(List.of(supplierAdminRole))

                .businessUnit("SUPPLIER")

                .costCenter("SUPPLIER")

                .designation("SUPPLIER_ADMIN")

                .department("SUPPLIER")

                .employmentType(EmploymentType.FullTime)

                .manager(null)

                .approvalLimit(BigDecimal.ZERO)

                .procurementApprover(false)

                .financeApprover(false)

                .build();

        return emprepo.save(supplierAdmin);
    }
}
