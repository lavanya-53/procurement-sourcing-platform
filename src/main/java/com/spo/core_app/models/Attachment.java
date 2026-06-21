package com.spo.core_app.models;


import com.spo.core_app.Enums.AttachmentType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="attachments")
@Entity
public class Attachment {
    private String AttachmentID;
    private String AttachmentUrl;
    @Enumerated
    //this keyword says this field is java enum
    private AttachmentType attachmentType;
    private String AttachmentDesc;
    private String OriginalFileName;
    private String FileSystemName;
    //User uploads GST.pdf System saves it as d7e5ac18-9f52-4f3a-a52c.pdf This is called the system filename.
}
