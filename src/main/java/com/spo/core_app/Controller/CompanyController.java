package com.spo.core_app.Controller;


import com.spo.core_app.DtoRequests.ProcurementCompanyRegistrationDto;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.ObjectMapper;



@RestController
@RequestMapping("/api/v1/Company")
public class CompanyController {
    @PostMapping(value="/Procurement-Company/register",consumes=MediaType.MULTIPART_FORM_DATA_VALUE);
    //Spring must know what type of request this API accepts. so use this consumes line
    public ResponseEntity RegisterProcurementCompany(@RequestPart String CompanyDetails,
                                                     @RequestPart MultipartFile CompanyLogo,
                                                     @RequestPart MultipartFile CompanyRegCertificate){
        ObjectMapper objectmapper=new ObjectMapper();
        ProcurementCompanyRegistrationDto procurementcompanydto=objectmapper.readValue(CompanyDetails,ProcurementCompanyRegistrationDto.class);
        //ProcurementCompanyRegistrationDto.class:this tells the objectmapper which java class should be created

    }
}
