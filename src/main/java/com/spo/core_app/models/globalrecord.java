package com.spo.core_app.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.Random;
import java.time.LocalDateTime;
import java.util.UUID;


import static org.hibernate.annotations.UuidGenerator.Style.RANDOM;

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
    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final Random RANDOM = new Random();
    public static String generate(String entityName) {

        String prefix = entityName
                .substring(0, Math.min(3, entityName.length()))
                .toUpperCase();

        StringBuilder random = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            random.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }

        return prefix + "-" + random;
    }
}
