package com.spo.core_app.models;

import com.spo.core_app.Enums.CompanyStatus;
import com.spo.core_app.Enums.CompanyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;

import java.util.List;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
@Table(name="Companies")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Company extends globalrecord{
      private String CompanyId;
      private String LegalName;
      private String DisplayName;
      @Enumerated
      private CompanyType companyType;
      @Enumerated
      private CompanyStatus companyStatus;
      private String MainLogoUrl;
      private String taxId;
      private String taxRegno;
      private String govtRegNo;
      private String PrimaryContactNumber;
      private String ContactName;
      private String ContactEmail;
      private String AddressLine1;
      private String AddressLine2;
      private String AddressLine3;
      private String City;
      private String Country;
      @OneToMany
      private List<Activity> activities;
      @OneToMany
      private List<Attachment> attachments;
}
