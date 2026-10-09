package com.spo.core_app.DtoResponses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProcurementReviewResponse {

    private Integer confidenceScore;
    private String recommendation;
    private String summary;
    private List<String> risks;
}
