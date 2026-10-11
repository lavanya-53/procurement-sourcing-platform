package com.spo.core_app.DtoResponses;

import com.spo.core_app.Enums.CompanyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierResponseDto {

    private UUID supplierSysId;

    private String supplierCode;

    private String legalName;

    private String displayName;

    private CompanyStatus companyStatus;

    private String message;
}
