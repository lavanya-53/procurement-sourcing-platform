package com.spo.core_app.DtoResponses;

import com.spo.core_app.Enums.CompanyStatus;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SupplierSummaryResponseDto {

    private UUID supplierSysId;

    private String supplierCode;

    private String legalName;

    private String displayName;

    private CompanyStatus companyStatus;

    private String city;

    private String country;
}
