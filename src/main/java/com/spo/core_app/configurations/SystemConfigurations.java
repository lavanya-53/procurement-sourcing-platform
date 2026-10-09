package com.spo.core_app.Configurations;

import io.imagekit.sdk.ImageKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Properties;

//This class contains application configuration.
@Configuration
public class SystemConfigurations {
     @Value("${imageKit.Public.key}")
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
    @Value("${spring.mail.username}")
    private String apiEmailAddress;
    @Value("${spring.mail.password}")
    private String apiEmailPassword;
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
   @Bean
   //javamailsender->connect to gmail->user recieves mail
   public JavaMailSender CreateJavaMailSender(){
       JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
       Properties mailProperties = new Properties();
       mailProperties.put("mail.smtp.auth", true);
       mailProperties.put("mail.smtp.starttls.enable", true);
       javaMailSender.setJavaMailProperties(mailProperties);
       javaMailSender.setHost("smtp.gmail.com");
       javaMailSender.setPort(587);
       javaMailSender.setUsername(apiEmailAddress);
       javaMailSender.setPassword(apiEmailPassword);
       return  javaMailSender;
   }
   @Bean
   //it is used to fill the values of those placeholders
    public TemplateEngine CreateTemplateEngine(){
       ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
       templateResolver.setPrefix("templates/"); // Make sure this folder exists in resources
       templateResolver.setSuffix(".html");
       templateResolver.setTemplateMode("HTML");
       templateResolver.setCharacterEncoding("UTF-8");
       TemplateEngine templateEngine = new TemplateEngine();
       templateEngine.setTemplateResolver(templateResolver);
       return templateEngine;
   }
   @Bean
    public RestTemplate CreateRestTemplate(){
       return new RestTemplate();
   }

}
