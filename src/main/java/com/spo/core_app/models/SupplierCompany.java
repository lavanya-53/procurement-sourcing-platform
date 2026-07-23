package com.spo.core_app.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "supplier_companies")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class SupplierCompany extends Company {
    @Column(unique = true, nullable = false)
    private String supplierCode;

    private String supplierCategory;

    private String paymentTerms;

    private Integer leadTimeInDays;

    private Boolean preferredSupplier;

    private Double supplierRating;

    private Double performanceScore;

    private String bankName;

    private String bankAccountNumber;

    private String ifscCode;

    private String swiftCode;

    private LocalDate onboardingDate;
}
