package com.spo.core_app.Controller;


import com.spo.core_app.DtoRequests.ProcurementCompanyRegistrationDto;
import com.spo.core_app.DtoResponses.CompanyReviewResponse;
import com.spo.core_app.DtoResponses.ProcurementCompanyResponse;
import com.spo.core_app.Service.ProcurementCompanyService;
import com.spo.core_app.models.ProcurementCompany;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/Company")
public class CompanyController {
    private ProcurementCompanyService procurementservice;
    @Autowired
   public CompanyController(ProcurementCompanyService procurementservice){
        this.procurementservice=procurementservice;
    }
    @PostMapping(value="/Procurement-Company/register",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    //Spring must know what type of request this API accepts. so use this consumes line
    public ResponseEntity RegisterProcurementCompany(@RequestPart String CompanyDetails,
                                                     @RequestPart MultipartFile CompanyLogo,
                                                     @RequestPart MultipartFile CompanyRegCertificate) {
        ObjectMapper objectmapper = new ObjectMapper();
        ProcurementCompanyRegistrationDto procurementcompanydto = objectmapper.readValue(CompanyDetails, ProcurementCompanyRegistrationDto.class);
        //ProcurementCompanyRegistrationDto.class:this tells the objectmapper which java class should be created
        ProcurementCompany procurement = procurementservice.RegisterProcurementCompany(procurementcompanydto, CompanyLogo, CompanyRegCertificate);
        return new ResponseEntity(procurement, HttpStatus.CREATED);
        //Create a new ResponseEntity object.
        //Put procurement inside its body.
        //Set status to 201 CREATED.
        //Return that response object to Spring.
    }
        @GetMapping("/pending")
        public ResponseEntity<List<ProcurementCompanyResponse>> getPendingCompanies() {
            List<ProcurementCompanyResponse> companies = procurementservice.getPendingCompanies();
            return ResponseEntity.ok(companies);
        }
    @GetMapping("/{sysid}")
    public ResponseEntity<ProcurementCompany> getCompanyById(
            @PathVariable UUID sysid) {

        ProcurementCompany company =
                procurementservice.getCompanyById(sysid);

        return ResponseEntity.ok(company);
    }
    @PostMapping("/{sysid}/approve")
    public ResponseEntity<ProcurementCompany> approveCompany(
            @PathVariable UUID sysid) {

        ProcurementCompany company = procurementservice.approveCompany(sysid);

        return ResponseEntity.ok(company);
    }
    @PostMapping("/{sysid}/reject")
    public ResponseEntity<ProcurementCompany> rejectCompany(
            @PathVariable UUID sysid) {

        ProcurementCompany company =
                procurementservice.rejectCompany(sysid);

        return ResponseEntity.ok(company);
    }
    @GetMapping("/{sysid}/review")
    public ResponseEntity<CompanyReviewResponse> getCompanyReview(
            @PathVariable UUID sysid) {

        return ResponseEntity.ok(
                procurementservice.getCompanyReview(sysid)
        );
    }


}
