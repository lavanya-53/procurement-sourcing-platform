package com.spo.core_app.Service;


import com.spo.core_app.Constants.SystemConstants;
import com.spo.core_app.Repository.RoleRepository;
import com.spo.core_app.models.Company;
import com.spo.core_app.models.Roles;
import com.spo.core_app.models.globalrecord;
import jdk.dynalink.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoleService extends globalrecord {
    private OperationService oprservice;
    private RoleRepository rolerepo;
    @Autowired
    public RoleService(OperationService opse,RoleRepository rolerepo){
        this.oprservice=opse;
        this.rolerepo=rolerepo;
    }
    public Roles CreateRole(Company company){
        List<Operation>operations= oprservice.fetchAllTheProcurementMainOperations();
        Roles role= Roles.builder().RoleId(globalrecord.generate(SystemConstants.Role_Entity_name)).RoleName(company.getLegalName()+"_"+"MAINT").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).createdBy(SystemConstants.APPLICATION_USER_NAME).createdBy(SystemConstants.APPLICATION_USER_NAME).
                operations(operations).build();
        return rolerepo.save(role);
    }
}
