package com.spo.core_app.Service;


import com.spo.core_app.Agents.AIReviewAgent;
import com.spo.core_app.Constants.AIConstants;
import com.spo.core_app.Constants.SystemConstants;
import com.spo.core_app.DtoRequests.ProcurementCompanyRegistrationDto;
import com.spo.core_app.DtoResponses.*;
import com.spo.core_app.DtoResponses.Gemini.GeminiResponse;
import com.spo.core_app.Enums.CompanyStatus;
import com.spo.core_app.Repository.AIReviewRepository;
import com.spo.core_app.Repository.ActivityRepository;
import com.spo.core_app.Repository.AttachmentRepository;
import com.spo.core_app.Repository.ProcurementCompanyRepository;
import com.spo.core_app.Stratergies.MultiMediaStrategy;
import com.spo.core_app.Transformers.CompanyAdapter;
import com.spo.core_app.Utilities.SystemUtilities;
import com.spo.core_app.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    private AIReviewAgent aiReviewAgent;
    private ActivityRepository activityRepository;
    private AIReviewRepository aiReviewRepository;

    @Autowired
    public ProcurementCompanyService(CompanyAdapter companyAdapter, ProcurementCompanyRepository procurementrepository, MultiMediaStrategy multimediastrategy, AttachmentRepository attachmentrepo, EmployeeService empservice, NotificationService Notificationservice, AIReviewAgent aiReviewAgent, ActivityRepository activityRepository,AIReviewRepository aiReviewRepository) {
        this.companyAdapter = companyAdapter;
        this.procurementrepository = procurementrepository;
        this.multimediastrategy = multimediastrategy;
        this.attachmentrepo = attachmentrepo;
        this.empservice = empservice;
        this.Notificationservice = Notificationservice;
        this.aiReviewAgent = aiReviewAgent;
        this.activityRepository = activityRepository;
        this.aiReviewRepository=aiReviewRepository;
        //stores the Repository object that Spring created into that variable so the Service can use it later.

    }

    public ProcurementCompany RegisterProcurementCompany(
            ProcurementCompanyRegistrationDto procurementcompanyregistrationdto,
            MultipartFile CompanyLogo,
            MultipartFile CompanyRegCertificate) {

        //1.Map this dto to procurementcompany object
        ProcurementCompany procurement = companyAdapter.mapProcurementCompanyDtoToModel(procurementcompanyregistrationdto);
        //call the repository layer and save it
        procurement = procurementrepository.save(procurement);
        MultiMediaService multimedia = multimediastrategy.getService(SystemConstants.IMAGEKIT_SERVICE_NAME);
        FileUploadResult companylogo = multimedia.UploadDocument(CompanyLogo, SystemConstants.procurement_company_basepath + "/" + procurement.getSysid(), "CompanyLogo");
        System.out.println(companylogo);
        Attachment CompanyLogoattachemt = Attachment.builder().AttachmentID(globalrecord.generate(SystemConstants.Attachment_entity_name)).attachmentType(companylogo.getFileType()).OriginalFileName(companylogo.getFileName()).AttachmentDesc("Company reg Docs").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).createdBy(SystemConstants.APPLICATION_USER_NAME).updatedBy(SystemConstants.APPLICATION_USER_NAME).AttachmentUrl(companylogo.getFilelink()).build();
        CompanyLogoattachemt = attachmentrepo.save(CompanyLogoattachemt);
        FileUploadResult Companyreg = multimedia.UploadDocument(CompanyRegCertificate, SystemConstants.procurement_company_basepath + "/" + procurement.getSysid(), "ComapnyRegistrationdetails");
        Attachment Companyregattachemt = Attachment.builder().AttachmentID(globalrecord.generate(SystemConstants.Attachment_entity_name)).attachmentType(Companyreg.getFileType()).OriginalFileName(Companyreg.getFileName()).AttachmentDesc("Company reg Docs").createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).createdBy(SystemConstants.APPLICATION_USER_NAME).updatedBy(SystemConstants.APPLICATION_USER_NAME).AttachmentUrl(Companyreg.getFilelink()).build();
        Companyregattachemt = attachmentrepo.save(Companyregattachemt);
        List<Attachment> attachments = new ArrayList<>();
        attachments.add(Companyregattachemt);
        attachments.add(CompanyLogoattachemt);
        //Attach this list of files to the procurement company object so that the company and its files are related."
        procurement.setAttachments(attachments);
        procurement = procurementrepository.save(procurement);

        //we call ai review from here
        ProcurementReviewResponse reviews = aiReviewAgent.VerifyProcurementDetails(procurement);
        AIReview aiReview = AIReview.builder()
                .confidenceScore(reviews.getConfidenceScore())
                .recommendation(reviews.getRecommendation())
                .summary(reviews.getSummary())
                .risks(reviews.getRisks())
                .company(procurement)
                .build();

        aiReviewRepository.save(aiReview);

        System.out.println("AI Recommendation : " + reviews.getRecommendation());
        System.out.println("AI Summary : " + reviews.getSummary());
        Activity activity = Activity.builder().comment("AI review completed")
                .ActivityId(globalrecord.generate("ACTIVITY"))
                .createdBy(AIConstants.AI_REVIEW_AGENT_NAME)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .updatedBy(AIConstants.AI_REVIEW_AGENT_NAME)
                .build();
        activityRepository.save(activity);
        if (procurement.getActivities() != null) {
            procurement.getActivities().add(activity);
        } else {
            List<Activity> activities = new ArrayList<>();
            activities.add(activity);
            procurement.setActivities(activities);
        }
        procurementrepository.save(procurement);

        return procurement;
    }

    public List<ProcurementCompanyResponse> getPendingCompanies() {

        List<ProcurementCompany> companies =
                procurementrepository.findByCompanyStatus(CompanyStatus.PENDING);

        List<ProcurementCompanyResponse> responses = new ArrayList<>();

        for (ProcurementCompany company : companies) {

            ProcurementCompanyResponse response =
                    ProcurementCompanyResponse.builder()
                            .sysid(company.getSysid())
                            .legalName(company.getLegalName())
                            .contactEmail(company.getContactEmail())
                            .primaryContactNumber(company.getPrimaryContactNumber())
                            .companyStatus(company.getCompanyStatus())
                            .addressLine1(company.getAddressLine1())
                            .build();

            responses.add(response);
        }

        return responses;
    }

    public ProcurementCompany getCompanyById(UUID sysid) {
        return procurementrepository.findBySysid(sysid)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

    public ProcurementCompany approveCompany(UUID sysid) {
        ProcurementCompany company =
                procurementrepository.findBySysid(sysid)
                        .orElseThrow(() ->
                                new RuntimeException("Company not found"));
        if (company.getCompanyStatus() != CompanyStatus.PENDING) {
            throw new RuntimeException("Only pending companies can be approved.");
        }
        company.setCompanyStatus(CompanyStatus.ACTIVE);
        company = procurementrepository.save(company);
        Employee admin = empservice.CreateSuperAdminForCompany(company);

        Notificationservice.SendProcurementCompanyRegistrationNotification(
                admin.getEmail(),
                "Temp@123",
                admin.getFirstName()

        );
        return company;
    }

    public ProcurementCompany rejectCompany(UUID sysid) {

        ProcurementCompany company = procurementrepository.findBySysid(sysid)
                .orElseThrow(() ->
                        new RuntimeException("Company not found"));

        if (company.getCompanyStatus() != CompanyStatus.PENDING) {
            throw new RuntimeException("Only pending companies can be rejected.");
        }

        company.setCompanyStatus(CompanyStatus.Rejected);

        company = procurementrepository.save(company);

        // We'll send the rejection email in the next step.

        return company;
    }

    public CompanyReviewResponse getCompanyReview(UUID sysid) {

        ProcurementCompany company =
                procurementrepository.findBySysid(sysid)
                        .orElseThrow(() ->
                                new RuntimeException("Company not found"));
//Give me the AI review belonging to this company.
        AIReview aiReview = aiReviewRepository.findByCompany_Sysid(sysid)
                .orElse(null);

        ProcurementCompanyResponse companyResponse =
                ProcurementCompanyResponse.builder()
                        .sysid(company.getSysid())
                        .legalName(company.getLegalName())
                        .contactEmail(company.getContactEmail())
                        .primaryContactNumber(company.getPrimaryContactNumber())
                        .companyStatus(company.getCompanyStatus())
                        .build();

        List<AttachementResponse> attachmentResponses =
                company.getAttachments().stream()
                        .map(attachment ->
                                AttachementResponse.builder()
                                        .attachmentID(attachment.getAttachmentID())
                                        .attachmentUrl(attachment.getAttachmentUrl())
                                        .attachmentType(attachment.getAttachmentType())
                                        .attachmentDesc(attachment.getAttachmentDesc())
                                        .originalFileName(attachment.getOriginalFileName())
                                        .build())
                        .collect(Collectors.toList());

        List<ActivityResponse> activityResponses =
                company.getActivities().stream()
                        .map(activity ->
                                ActivityResponse.builder()
                                        .activityId(activity.getActivityId())
                                        .comment(activity.getComment())
                                        .build())
                        .toList();
        ProcurementReviewResponse aiReviewResponse = null;

        if (aiReview != null) {
            aiReviewResponse = ProcurementReviewResponse.builder()
                    .confidenceScore(aiReview.getConfidenceScore())
                    .recommendation(aiReview.getRecommendation())
                    .summary(aiReview.getSummary())
                    .risks(aiReview.getRisks())
                    .build();
        }
        return CompanyReviewResponse.builder()
                .company(companyResponse)
                .attachments(attachmentResponses)
                .activities(activityResponses)
                .aiReview(aiReviewResponse)
                .build();
    }
    }

