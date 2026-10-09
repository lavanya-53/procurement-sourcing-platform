package com.spo.core_app.Service;


import com.spo.core_app.Constants.SystemConstants;
import com.spo.core_app.DtoRequests.CreateRoleRequest;
import com.spo.core_app.Repository.RoleRepository;
import com.spo.core_app.models.Company;
import com.spo.core_app.models.Employee;
import com.spo.core_app.models.Roles;
import com.spo.core_app.models.globalrecord;
import com.spo.core_app.models.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class RoleService extends globalrecord {
    private OperationService oprservice;
    private RoleRepository rolerepo;
    @Autowired
    public RoleService(OperationService opse,RoleRepository rolerepo){
        this.oprservice=opse;
        this.rolerepo=rolerepo;
    }
    public Roles CreateMAintRole(Company company){
        List<Operation>operations= oprservice.fetchAllTheProcurementMainOperations();
        Roles role= Roles.builder().RoleId(globalrecord.generate(SystemConstants.Role_Entity_name)).RoleName(company.getLegalName()+"_"+"MAINT").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).createdBy(SystemConstants.APPLICATION_USER_NAME).updatedBy(SystemConstants.APPLICATION_USER_NAME).
                operations(operations).build();
        return rolerepo.save(role);
    }
    public Roles CreateRole(CreateRoleRequest createrolerequest, Employee emp){
        List<String>opnames=createrolerequest.getOperations();
        List<Operation>op=oprservice.fetchOperationsByName(opnames);
        Roles role = Roles.builder()
                .RoleId(globalrecord.generate("ROLE"))
                .RoleName(emp.getCompany().getLegalName() + "_" + createrolerequest.getRolename())
                .operations(op)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(emp.getEmail())
                .updatedBy(emp.getEmail())
                .build();
        rolerepo.save(role);
        return role;
    }
    public List<Roles>fetchRolesById(List<UUID> role){
            List<Roles> roles = new ArrayList<>();
            for(UUID id : role){
                roles.add(rolerepo.findById(id).orElse(null));
            }
            return roles;

    }
    public Roles createSupplierAdminRole(Company company) {

        List<Operation> Operations =
                oprservice.fetchAllTheSupplierAdminOperations();

        Roles role = Roles.builder()
                .RoleId(globalrecord.generate(SystemConstants.Role_Entity_name))
                .RoleName(company.getLegalName() + "_SUPPLIER_ADMIN")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy(SystemConstants.APPLICATION_USER_NAME)
                .updatedBy(SystemConstants.APPLICATION_USER_NAME)
                .operations(Operations)
                .build();

        return rolerepo.save(role);
    }

}
