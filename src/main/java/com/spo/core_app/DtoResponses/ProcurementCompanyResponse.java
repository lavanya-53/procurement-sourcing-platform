package com.spo.core_app.DtoResponses;

import com.spo.core_app.Enums.CompanyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class ProcurementCompanyResponse {

        private UUID sysid;
        private String legalName;
        private String contactEmail;
        private String primaryContactNumber;
        private CompanyStatus companyStatus;
        private String addressLine1;
    }

