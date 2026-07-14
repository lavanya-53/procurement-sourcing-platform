package com.spo.core_app.Service;


import com.spo.core_app.Constants.SystemConstants;
import com.spo.core_app.DtoRequests.ProcurementCompanyRegistrationDto;
import com.spo.core_app.DtoResponses.FileUploadResult;
import com.spo.core_app.Repository.AttachmentRepository;
import com.spo.core_app.Repository.ProcurementCompanyRepository;
import com.spo.core_app.Stratergies.MultiMediaStrategy;
import com.spo.core_app.Transformers.CompanyAdapter;
import com.spo.core_app.Utilities.SystemUtilities;
import com.spo.core_app.models.Attachment;
import com.spo.core_app.models.Employee;
import com.spo.core_app.models.ProcurementCompany;
import com.spo.core_app.models.globalrecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ProcurementCompanyService {
    @Autowired
    private CompanyAdapter companyAdapter;
    private ProcurementCompanyRepository procurementrepository;
    private MultiMediaStrategy multimediastrategy;
    private AttachmentRepository attachmentrepo;
    private EmployeeService empservice;
    private NotificationService Notificationservice;

    public ProcurementCompanyService(CompanyAdapter companyAdapter,ProcurementCompanyRepository procurementrepository,MultiMediaStrategy multimediastrategy,AttachmentRepository attachmentrepo,EmployeeService empservice,NotificationService Notificationservice){
        this.companyAdapter=companyAdapter;
        this.procurementrepository=procurementrepository;
        this.multimediastrategy=multimediastrategy;
        this.attachmentrepo=attachmentrepo;
        this.empservice=empservice;
        this.Notificationservice=Notificationservice;
        //stores the Repository object that Spring created into that variable so the Service can use it later.

    }
    public ProcurementCompany RegisterProcurementCompany(
            ProcurementCompanyRegistrationDto procurementcompanyregistrationdto,
    MultipartFile CompanyLogo,
    MultipartFile CompanyRegCertificate){

        //1.Map this dto to procurementcompany object
       ProcurementCompany procurement= companyAdapter.mapProcurementCompanyDtoToModel(procurementcompanyregistrationdto);
       //call the repository layer and save it
        procurement=procurementrepository.save(procurement);
        MultiMediaService multimedia=multimediastrategy.getService(SystemConstants.IMAGEKIT_SERVICE_NAME);
        FileUploadResult companylogo=multimedia.UploadDocument(CompanyLogo,SystemConstants.procurement_company_basepath+"/"+ procurement.getSysid(),"CompanyLogo");
        Attachment CompanyLogoattachemt=Attachment.builder().AttachmentID(globalrecord.generate(SystemConstants.Attachment_entity_name)).attachmentType(companylogo.getFileType()).OriginalFileName(companylogo.getFileName()).AttachmentDesc("Company reg Docs").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).createdBy(SystemConstants.APPLICATION_USER_NAME).updatedBy(SystemConstants.APPLICATION_USER_NAME).AttachmentUrl(companylogo.getFilelink()).build();
        CompanyLogoattachemt=attachmentrepo.save(CompanyLogoattachemt);
        FileUploadResult Companyreg=multimedia.UploadDocument(CompanyRegCertificate,SystemConstants.procurement_company_basepath+"/"+ procurement.getSysid(),"ComapnyRegistrationdetails");
        Attachment Companyregattachemt=Attachment.builder().AttachmentID(globalrecord.generate(SystemConstants.Attachment_entity_name)).attachmentType(Companyreg.getFileType()).OriginalFileName(Companyreg.getFileName()).AttachmentDesc("Company reg Docs").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).createdBy(SystemConstants.APPLICATION_USER_NAME).updatedBy(SystemConstants.APPLICATION_USER_NAME).AttachmentUrl(Companyreg.getFilelink()).build();
        Companyregattachemt=attachmentrepo.save(Companyregattachemt);
        List<Attachment> attachments=List.of(CompanyLogoattachemt,Companyregattachemt);
        //Attach this list of files to the procurement company object so that the company and its files are related."
        procurement.setAttachments(attachments);
        procurement=procurementrepository.save(procurement);
        Employee admin=empservice.CreateSuperAdminForCompany((procurement));
        Notificationservice.SendProcurementCompanyRegistrationNotification(admin.getEmail(),admin.getFirstName(),"Temp@123");
        return procurement;
    }
}
