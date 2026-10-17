package com.spo.core_app.Repository;

import com.spo.core_app.Enums.CompanyStatus;
import com.spo.core_app.models.SupplierCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface SupplierCompanyRepository extends JpaRepository<SupplierCompany, UUID> {
        List<SupplierCompany> findByCompanyStatus(CompanyStatus companyStatus);
        long countByCompanyStatus(CompanyStatus companyStatus);
}
