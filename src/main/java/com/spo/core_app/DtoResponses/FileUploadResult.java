package com.spo.core_app.DtoResponses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
//We don't usually expose third-party library objects (Result) directly to the frontend. We extract only the required data and place it inside our own DTO (FileUploadResult).
public class FileUploadResult {
    private String fileId;
    private String Filelink;
    private Long FileSize;
    private String FileName;
    private String FileType;
}
