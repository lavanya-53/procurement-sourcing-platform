package com.spo.core_app.models;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

    @Entity
    @Table(name = "ai_reviews")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public class AIReview extends globalrecord {

        @Column(nullable = false)
        private Integer confidenceScore;

        @Column(nullable = false)
        private String recommendation;

        //Make this database column a TEXT column
        @Column(columnDefinition = "TEXT")
        private String summary;
//This entity contains a collection of simple values, and JPA should store those values in a separate table.
        @ElementCollection
        private List<String> risks;

        @OneToOne
        @JoinColumn(name = "company_sysid", nullable = false, unique = true)
        private ProcurementCompany company;
    }

