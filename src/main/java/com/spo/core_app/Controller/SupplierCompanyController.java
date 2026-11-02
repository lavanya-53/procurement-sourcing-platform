package com.spo.core_app.Controller;

import com.spo.core_app.DtoRequests.SupplierRegistrationDto;
import com.spo.core_app.DtoResponses.SupplierResponseDto;
import com.spo.core_app.DtoResponses.SupplierSummaryResponseDto;
import com.spo.core_app.Enums.CompanyStatus;
import com.spo.core_app.Service.AuthService;
import com.spo.core_app.Service.SupplierCompanyService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Supplier")

public class SupplierCompanyController {
    private SupplierCompanyService Supplyservice;
    private AuthService authservice;
    public SupplierCompanyController(SupplierCompanyService Supplyservice,AuthService authservice){
        this.Supplyservice=Supplyservice;
        this.authservice=authservice;
    }
    @PostMapping("/register")
    public ResponseEntity registerSupplier(@RequestBody SupplierRegistrationDto supplierRequest){
        SupplierResponseDto response=Supplyservice.registerSupplier(supplierRequest);
        return new ResponseEntity(response, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<SupplierSummaryResponseDto>> getSuppliers(

            @RequestHeader("Authorization") String token,

            @RequestParam(required = false)
            CompanyStatus status) {

        authservice.IsUserAllowedToPerformOperation(
                token,
                "VIEW_SUPPLIERS");

        List<SupplierSummaryResponseDto> suppliers =
                Supplyservice.getSuppliers(status);
        return ResponseEntity.ok(suppliers);
    }
}
