package com.spo.core_app.Service;

import com.spo.core_app.DtoResponses.FileUploadResult;
import org.springframework.web.multipart.MultipartFile;

public interface MultiMediaService {
    public FileUploadResult UploadDocument(MultipartFile File, String path, String FileName);
    public void getImage();
    public void deleteImage();
}
