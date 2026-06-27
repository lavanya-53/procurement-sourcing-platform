package com.spo.core_app.Service;

import com.spo.core_app.Constants.SystemConstants;
import com.spo.core_app.DtoResponses.FileUploadResult;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.models.results.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import io.imagekit.sdk.models.FileCreateRequest;

@Service
@Slf4j
public class ImageKitService implements MultiMediaService{
    private ImageKit Imagekit;
    @Autowired
    public ImageKitService(ImageKit imagekit){
        this.Imagekit=imagekit;
    }
    @Override
    //MultipartFile is a datatype (actually an interface type) provided by Spring.
    //. It provides methods to access the file name, size, content type, and file data.
//    @RequestPart is used to extract individual parts from a multipart/form-data request.
    public FileUploadResult UploadDocument(MultipartFile File, String path, String FileName) {
        FileUploadResult result=null;
        for (int i = 0; i < SystemConstants.File_Upload_retry; i++) {
            try {
                //Give me the actual contents of the uploaded file as bytes."
                //Because ImageKit's upload API expects: bytes
                byte[] file = File.getBytes();
                //filecreaterequest is a class from image sdk
                //mageKit upload method does not want random values.
                //
                //It wants a proper request object.
                FileCreateRequest filecreaterequest = new FileCreateRequest(file, FileName);
                filecreaterequest.setFolder(path);
                Result imagekitresult=Imagekit.upload(filecreaterequest);
                //Call the getName() method of the Result object.
                result=FileUploadResult.builder().FileName(imagekitresult.getName()).fileId(imagekitresult.getFileId()).FileType(imagekitresult.getFileType()).FileSize(imagekitresult.getSize()).Filelink(imagekitresult.getFilePath()).build();
                return result;
            } catch (Exception e) {
                log.error(String.format("The error occured due to following resosn:%s", e.getMessage()));

            }
        }
        return result;
    }

    @Override
    public void getImage() {

    }

    @Override
    public void deleteImage() {

    }
}
