package com.spo.core_app.DtoRequests;

import com.spo.core_app.Enums.CompanyStatus;
import com.spo.core_app.Enums.CompanyType;
import com.spo.core_app.Enums.Currency;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcurementCompanyRegistrationDto {

    // Company Information

    @NotBlank(message = "Legal Name is required")
    private String legalName;

    @NotBlank(message = "Display Name is required")
    private String displayName;

    @NotNull(message = "Company Type is required")
    private CompanyType companyType;

    @NotNull(message = "Company Status is required")
    private CompanyStatus companyStatus;

    // Registration & Tax Information

    @NotBlank(message = "Tax ID is required")
    private String taxId;

    @NotBlank(message = "Tax Registration Number is required")
    private String taxRegNumber;

    @NotBlank(message = "Government Registration Number is required")
    private String govtRegNumber;

    // Contact Information

    @NotBlank(message = "Primary Contact Number is required")
    private String primaryContactNumber;

    @NotBlank(message = "Contact Name is required")
    private String contactName;

    @NotBlank(message = "Contact Email is required")
    @Email(message = "Enter a valid email address")
    private String contactEmail;

    // Address Information

    @NotBlank(message = "Address Line 1 is required")
    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Country is required")
    private String country;

    // Financial Controls

    @NotNull(message = "Base Currency is required")
    private Currency baseCurrency;

    @NotNull(message = "Annual Procurement Budget is required")
    @PositiveOrZero(message = "Annual Procurement Budget cannot be negative")
    private BigDecimal annualProcurementBudget;

    @NotNull(message = "Available Budget is required")
    @PositiveOrZero(message = "Available Budget cannot be negative")
    private BigDecimal availableBudget;

    // Approval Configuration

    @NotNull(message = "Approval Required is required")
    private Boolean approvalRequired;

    @NotNull(message = "Approval Levels are required")
    private Integer approvalLevels;

    // Purchasing Controls

    @NotNull(message = "Auto Approval Threshold is required")
    @PositiveOrZero(message = "Auto Approval Threshold cannot be negative")
    private BigDecimal autoApprovalThreshold;

    @NotNull(message = "RFQ Required Threshold is required")
    @PositiveOrZero(message = "RFQ Required Threshold cannot be negative")
    private BigDecimal rfqRequiredThreshold;

    @NotNull(message = "RFP Required Threshold is required")
    @PositiveOrZero(message = "RFP Required Threshold cannot be negative")
    private BigDecimal rfpRequiredThreshold;

    // Procurement Policies

    @NotNull(message = "Contract Required is required")
    private Boolean contractRequired;

    // ERP Integration

    @NotBlank(message = "ERP System is required")
    private String erpSystem;

    @NotBlank(message = "ERP Company Code is required")
    private String erpCompanyCode;

    @NotBlank(message = "Cost Center Prefix is required")
    private String costCenterPrefix;
}