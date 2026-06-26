package com.spo.core_app.Configurations;

import io.imagekit.sdk.ImageKit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//This class contains application configuration.
@Configuration
public class SystemConfigurations {
     @Value("${imageKit.public.key}")
     private String ImageKitpublickey;
     @Value("${imageKit.private.key}")
    private String ImageKitprivatekey;
     //application.properties
     //        ↓
     //@Value
     //        ↓
     //Java Variables
     @Value("${imageKit.endpoint}")
    private String Imagekitendpoint;
     //just an helper method was not supposed to be injected anywhere
   public io.imagekit.sdk.config.Configuration CreateConnectionConfiguration(){
       return new io.imagekit.sdk.config.Configuration(
               ImageKitpublickey,
               ImageKitprivatekey,
               Imagekitendpoint
       );
   }
   //This annotation is applied to a method.
    //The object returned by this method should be stored in the Spring Container as a Bean."
   @Bean
   public ImageKit CreateImageKit(){
       io.imagekit.sdk.config.Configuration config=this.CreateConnectionConfiguration();
       //this line craete the object
       //Hey ImageKit library, create an ImageKit object using this configuration and give that object back to me. Store it in the variable named imagekit.
       ImageKit imagekit=ImageKit.getInstance();
       //setconfig comes from image kit class
       //store this configuration object inside image kit objcet
       imagekit.setConfig(config);
       return imagekit;
       //spring recieves it and stores it has bean
   }

}
