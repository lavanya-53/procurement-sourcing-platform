package com.spo.core_app.Repository;

import com.spo.core_app.DtoResponses.ProcurementCompanyResponse;
import com.spo.core_app.Enums.CompanyStatus;
import com.spo.core_app.models.ProcurementCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProcurementCompanyRepository extends JpaRepository<ProcurementCompany, UUID> {
    public List<ProcurementCompany> findByCompanyStatus(CompanyStatus companyStatus);
   public Optional<ProcurementCompany> findBySysid(UUID sysid);

}
