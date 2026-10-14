package com.spo.core_app.DtoRequests;

import com.spo.core_app.Enums.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRegistrationDto {
    //Company information
    private String legalName;

    private String displayName;

    private String contactName;

    private String contactEmail;

    private String primaryContactNumber;

    private String taxId;

    private String taxRegNo;

    private String govtRegNo;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String city;

    private String country;

    private CompanyType companyType;

    //Administrator information
    private String adminFirstName;

    private String adminLastName;

    private String adminEmail;


}
