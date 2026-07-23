package com.spo.core_app.Service;


import com.spo.core_app.Constants.MessageConstants;
import com.spo.core_app.Constants.SystemConstants;
import com.spo.core_app.DtoRequests.SupplierRegistrationDto;
import com.spo.core_app.DtoResponses.SupplierResponseDto;
import com.spo.core_app.DtoResponses.SupplierSummaryResponseDto;
import com.spo.core_app.models.Employee;
import org.springframework.stereotype.Service;

import com.spo.core_app.Enums.CompanyStatus;
import com.spo.core_app.models.SupplierCompany;
import com.spo.core_app.Repository.SupplierCompanyRepository;
import com.spo.core_app.models.globalrecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierCompanyService {

    private final SupplierCompanyRepository supplierCompanyRepository;
    private final EmployeeService employeeService;

    private final globalrecord globalRecord;
    private final NotificationService notificationService;
    public SupplierResponseDto registerSupplier(
            SupplierRegistrationDto request) {

        // Step 1 : Validate Request
        validateSupplierRequest(request);

        // Step 2 : Create Supplier Entity
        SupplierCompany supplier = SupplierCompany.builder()

                .CompanyId(globalRecord.generate(SystemConstants.Company_Entity_Name))

                .supplierCode(globalRecord.generate(SystemConstants.SUPPLIER_ENTITY_NAME))

                .LegalName(request.getLegalName())

                .DisplayName(request.getDisplayName())

                .ContactName(request.getContactName())

                .ContactEmail(request.getContactEmail())

                .PrimaryContactNumber(request.getPrimaryContactNumber())

                .taxId(request.getTaxId())

                .taxRegno(request.getTaxRegNo())

                .govtRegNo(request.getGovtRegNo())

                .AddressLine1(request.getAddressLine1())

                .AddressLine2(request.getAddressLine2())

                .AddressLine3(request.getAddressLine3())

                .City(request.getCity())

                .Country(request.getCountry())

                .companyType(request.getCompanyType())

                .companyStatus(CompanyStatus.PENDING)


                .preferredSupplier(false)

                .supplierRating(0.0)

                .performanceScore(0.0)

                .onboardingDate(LocalDate.now())

                .build();

        // Step 3 : Save Supplier
        SupplierCompany savedSupplier =
                supplierCompanyRepository.save(supplier);

       Employee emp= employeeService.createSupplierAdmin(
                savedSupplier,
                request);
       notificationService.SendSupplierRegistrationNotification(emp.getEmail(),"Temp@123",emp.getFirstName(),savedSupplier.getLegalName(), savedSupplier.getSupplierCode());


        // Step 4 : Prepare Response
        return SupplierResponseDto.builder()

                .supplierSysId(savedSupplier.getSysid())

                .supplierCode(savedSupplier.getSupplierCode())

                .legalName(savedSupplier.getLegalName())

                .displayName(savedSupplier.getDisplayName())

                .companyStatus(savedSupplier.getCompanyStatus())

                .message(MessageConstants.SUPPLIER_REGISTERED_SUCCESSFULLY)

                .build();
    }

    private void validateSupplierRequest(
            SupplierRegistrationDto request) {

        if (request.getLegalName() == null ||
                request.getLegalName().isBlank()) {

            throw new IllegalArgumentException(
                    "Legal Name is mandatory");
        }

        if (request.getContactEmail() == null ||
                request.getContactEmail().isBlank()) {

            throw new IllegalArgumentException(
                    "Contact Email is mandatory");
        }

        if (request.getPrimaryContactNumber() == null ||
                request.getPrimaryContactNumber().isBlank()) {

            throw new IllegalArgumentException(
                    "Primary Contact Number is mandatory");
        }
    }
    public List<SupplierSummaryResponseDto> getSuppliers(
            CompanyStatus companyStatus) {

        List<SupplierCompany> suppliers;

        if (companyStatus == null) {

            suppliers = supplierCompanyRepository.findAll();

        } else {

            suppliers = supplierCompanyRepository
                    .findByCompanyStatus(companyStatus);
        }

        List<SupplierSummaryResponseDto> response =
                new ArrayList<>();

        for (SupplierCompany supplier : suppliers) {

            SupplierSummaryResponseDto dto =
                    SupplierSummaryResponseDto.builder()

                            .supplierSysId(supplier.getSysid())

                            .supplierCode(supplier.getSupplierCode())

                            .legalName(supplier.getLegalName())

                            .displayName(supplier.getDisplayName())

                            .companyStatus(supplier.getCompanyStatus())

                            .city(supplier.getCity())

                            .country(supplier.getCountry())

                            .build();

            response.add(dto);
        }

        return response;
    }
}
