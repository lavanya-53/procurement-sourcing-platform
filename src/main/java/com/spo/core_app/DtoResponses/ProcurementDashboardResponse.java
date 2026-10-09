package com.spo.core_app.DtoResponses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcurementDashboardResponse {

    private long totalSuppliers;

    private long pendingSuppliers;

    private long approvedSuppliers;

    private long rejectedSuppliers;

    private List<SupplierStatusCount> supplierStatusBreakdown;

    private List<MonthlyRegistrationCount> monthlyRegistrations;

    private List<RecentSupplier> recentSuppliers;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SupplierStatusCount {

        private String status;

        private long count;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthlyRegistrationCount {

        private String month;

        private long count;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentSupplier {

        private String supplierSysId;

        private String supplierCode;

        private String companyName;

        private String status;

        private String registeredOn;
    }
}