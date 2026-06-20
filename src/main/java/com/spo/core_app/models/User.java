package com.spo.core_app.models;

import com.spo.core_app.Enums.UserStatus;
import com.spo.core_app.Enums.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name="users")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data

public class User extends globalrecord {
    private String userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;
    private String Password;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToOne
    private Company company;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private LocalDate joiningDate;

    private LocalDateTime lastLoginDate;

    private Boolean emailVerified;

    private Boolean mfaEnabled;
}

}
