package com.spo.core_app.DtoResponses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AttachementResponse {


        private String attachmentID;
        private String attachmentUrl;
        private String attachmentType;
        private String attachmentDesc;
        private String originalFileName;

}
