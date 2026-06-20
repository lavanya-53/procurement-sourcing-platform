package com.spo.core_app.models;

import com.spo.core_app.Enums.Currency;
import jakarta.persistence.Enumerated;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@SuperBuilder
public class ProcurementCompany extends Company{
    private String ProcurementCompanyId;
    // Procurement Organization Details
    private String ProcurementHead;
    private String ProcurementEmail;
    private String ProcurementPhone;

    @Enumerated
    //Financial constraints
    private Currency BaseCurrency;
    private BigDecimal AnnualProcurementBudget;//Imagine Infosys says This year we can spend ₹50 Crore That amount is theAnnual Procurement Budget.
    private BigDecimal availableBudget;//purchased chairs,purchased servers now this s the remaining amiunt

    //Approval Configuration
    private boolean ApprovalRequired;//true or false
    private Integer ApprovalLevels;//if approval required then how many levels 3 2 or null so for null use Integer

    // Purchasing Controls
       private BigDecimal autoApprovalThreshold;//Up to what amount can the system automatically approve a purchase?
        private BigDecimal rfqRequiredThreshold;
        private BigDecimal rfpRequiredThreshold;
    //Sourcing = the overall process of finding and selecting a supplier.
    //RFQ = a sourcing method used when you already know exactly what you want; suppliers mainly compete on price.
    //RFP = another sourcing method used when you need suppliers to propose the solution itself, so they compete on solution, technology, experience, timeline, and price.
    // Procurement Policies
    private Boolean contractRequired;//Does this procurement company require a legal contract before purchasing?


    // ERP Integration
    private String erpSystem;//Which ERP software does this company use?like orcle,sap hwhich erp software should i send daata to
    private String erpCompanyCode;
    private String costCenterPrefix;//Which department spent the money?SAP records the expense against the correct department.

    // Procurement Metrics
    //Usually these values are shown on the company's dashboard.
    //Imagine you login as the Procurement Head.
    private Integer activeSuppliers;
    private Integer activeContracts;
    private Integer activePurchaseOrders;
    private Integer activePurchaseRequests;

    //Should every supplier or procurement request go through a compliance team before approval?
    // Compliance
    private Boolean complianceReviewRequired;
    private Boolean legalReviewRequired;//before signing contact infosys checks like is this contcat legal no issues any risk penalty

    // Dates
    private LocalDate goLiveDate;//The date from which the company starts using the system.
    private LocalDate lastAuditDate;//checking whether everything is correct.









}
