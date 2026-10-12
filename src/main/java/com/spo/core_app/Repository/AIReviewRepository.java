package com.spo.core_app.Repository;

import com.spo.core_app.models.AIReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

    public interface AIReviewRepository extends JpaRepository<AIReview, UUID> {
        Optional<AIReview> findByCompany_Sysid(UUID sysid);
    }

