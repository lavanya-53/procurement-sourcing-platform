package com.spo.core_app.DtoResponses;

import com.spo.core_app.DtoRequests.ProcurementCompanyRegistrationDto;
import com.spo.core_app.DtoResponses.Gemini.GeminiResponse;
import com.spo.core_app.Enums.CompanyStatus;
import com.spo.core_app.models.Activity;
import com.spo.core_app.models.Attachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyReviewResponse {
    private ProcurementCompanyResponse company;
    private List<AttachementResponse> attachments;
    private ProcurementReviewResponse aiReview;
    private List<ActivityResponse> activities;
}
