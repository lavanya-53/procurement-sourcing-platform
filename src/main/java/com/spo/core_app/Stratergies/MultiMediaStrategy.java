package com.spo.core_app.Stratergies;

import com.spo.core_app.Constants.SystemConstants;
import com.spo.core_app.Service.AwsService;
import com.spo.core_app.Service.AzureService;
import com.spo.core_app.Service.ImageKitService;
import com.spo.core_app.Service.MultiMediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultiMediaStrategy {
    private AwsService awsservice;
    private AzureService azureservice;
    private ImageKitService imagekitservice;
    public MultiMediaStrategy(AwsService awsservice,AzureService azureservice,ImageKitService imagekitservice){
        this.awsservice=awsservice;
        this.azureservice=azureservice;
        this.imagekitservice=imagekitservice;
    }
    public MultiMediaService getService(String CloudServiceName) {
        switch (CloudServiceName) {
            case SystemConstants.AWS_SERVICE_NAME:
                return awsservice;
            case SystemConstants.AZURE_SERVICE_NAME:
                return azureservice;
            case SystemConstants.IMAGEKIT_SERVICE_NAME:
                return imagekitservice;
            default:
                throw new IllegalArgumentException("Incorrect ServiceName Passed");

        }
    }

}
