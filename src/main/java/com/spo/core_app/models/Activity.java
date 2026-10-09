




package com.spo.core_app.models;



import com.spo.core_app.models.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;


@Table(name="activities")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Activity extends globalrecord{
    private String ActivityId;
    private String comment;
    @ManyToOne //many comments can be made by one user
    private User user;
}

