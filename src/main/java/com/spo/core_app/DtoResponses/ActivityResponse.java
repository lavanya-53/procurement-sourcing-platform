package com.spo.core_app.DtoResponses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public class ActivityResponse {

        private String activityId;
        private String comment;
        private LocalDateTime createdAt;

}
