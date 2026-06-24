
package com.spo.core_app.Transformers;

import com.spo.core_app.DtoRequests.ProcurementCompanyRegistrationDto;
import com.spo.core_app.Enums.CompanyStatus;
import com.spo.core_app.models.ProcurementCompany;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

    @Component
    public class CompanyAdapter {


        public ProcurementCompany mapProcurementCompanyDtoToModel(ProcurementCompanyRegistrationDto dto){
            //This is a public method. Give me a ProcurementCompanyRegistrationDto, and I will convert it into a ProcurementCompany entity and return that entity to you."
            return ProcurementCompany.builder()
                    // Company Fields
                    .LegalName(dto.getLegalName())
                    .DisplayName(dto.getDisplayName())
                    .companyType(dto.getCompanyType())
                    .taxId(dto.getTaxId())
                    .taxRegno(dto.getTaxRegNumber())
                    .govtRegNo(dto.getGovtRegNumber())
                    .PrimaryContactNumber(dto.getPrimaryContactNumber())
                    .ContactName(dto.getContactName())
                    .ContactEmail(dto.getContactEmail())
                    .AddressLine1(dto.getAddressLine1())
                    .AddressLine2(dto.getAddressLine2())
                    .AddressLine3(dto.getAddressLine3())
                    .City(dto.getCity())
                    .Country(dto.getCountry())
                    //Procurement Fields
                    .BaseCurrency(dto.getBaseCurrency())
                    .AnnualProcurementBudget(dto.getAnnualProcurementBudget())
                    .availableBudget(dto.getAvailableBudget())
                    .ApprovalRequired(dto.getApprovalRequired())
                    .ApprovalLevels(dto.getApprovalLevels())
                    .autoApprovalThreshold(dto.getAutoApprovalThreshold())
                    .rfqRequiredThreshold(dto.getRfqRequiredThreshold())
                    .rfpRequiredThreshold(dto.getRfpRequiredThreshold())
                    .contractRequired(dto.getContractRequired())
                    .erpSystem(dto.getErpSystem())
                    .erpCompanyCode(dto.getErpCompanyCode())
                    .costCenterPrefix(dto.getCostCenterPrefix())
                    // Default Values
                    .activeSuppliers(0)
                    .activeContracts(0)
                    .activePurchaseOrders(0)
                    .activePurchaseRequests(0)
                    .complianceReviewRequired(true)
                    .legalReviewRequired(true)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .createdBy(dto.getContactName())
                    .updatedBy(dto.getContactEmail())
                    .build();
        }

    }

