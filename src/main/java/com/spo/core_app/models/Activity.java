package com.spo.core_app.models;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Table(name="activities")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Activity {
    private String ActivityId;
    private String comment;
    @ManyToOne //many comments can be made by one user
    private User user;
}
