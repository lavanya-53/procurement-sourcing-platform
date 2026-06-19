package com.spo.core_app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Inheritance(strategy=InheritanceType.JOINED)
@Entity
@Table(name="global_record")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class globalrecord {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID sysid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
