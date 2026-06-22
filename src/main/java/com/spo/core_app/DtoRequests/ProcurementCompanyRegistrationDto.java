package com.spo.core_app.DtoRequests;

import com.spo.core_app.Enums.CompanyStatus;
import com.spo.core_app.Enums.CompanyType;
import com.spo.core_app.Enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcurementCompanyRegistrationDto {
    // Company Information
    private String legalName;
    private String displayName;
    private CompanyType companyType;
    private CompanyStatus companyStatus;

    // Registration & Tax Information
    private String taxId;
    private String taxRegNumber;
    private String govtRegNumber;

    // Contact Information
    private String primaryContactNumber;
    private String contactName;
    private String contactEmail;

    // Address Information
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String city;
    private String country;

    // Financial Controls
    private Currency baseCurrency;
    private BigDecimal annualProcurementBudget;
    private BigDecimal availableBudget;

    // Approval Configuration
    private Boolean approvalRequired;
    private Integer approvalLevels;

    // Purchasing Controls
    private BigDecimal autoApprovalThreshold;
    private BigDecimal rfqRequiredThreshold;
    private BigDecimal rfpRequiredThreshold;

    // Procurement Policies
    private Boolean contractRequired;

    // ERP Integration
    private String erpSystem;
    private String erpCompanyCode;
    private String costCenterPrefix;
}
