package com.spo.core_app.Service;


import com.spo.core_app.Constants.EmailConstants;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
public class NotificationService {
    private TemplateEngine templateengine;
    private JavaMailSender javamailsender;
    @Autowired
    public NotificationService(TemplateEngine templateengine,JavaMailSender javamailsender){
        this.templateengine=templateengine;
        this.javamailsender=javamailsender;
    }

    public void SendProcurementCompanyRegistrationNotification(String ToEmail,
                                                               String Password,
                                                               String Username){
        //context is just a placeholder just stores the values later send the context to thymeleaf
        //Get values
        //      ↓
        //Put values in Context
        //      ↓
        //Pass Context to templateEngine.process(...)
        //      ↓
        //Thymeleaf replaces placeholders
        //      ↓
        //Generate final HTML
        //      ↓
        //Send email

//
       // ConIf you want me to fill values into a template, please give me the data inside a Context object."

       // It is simply the API that Thymeleaf provides.
        Context context=new Context();
        context.setVariable("maintUserName",Username);//maintusername="TCS_MAINT_USER
        context.setVariable("email",ToEmail);
        context.setVariable("password",Password);
        //Dear Thymeleaf, open this HTML template and fill all the blanks using the values inside this Context."
        String HtmlEmail=templateengine.process(EmailConstants.Procurement_Company_Reg_Email_Template_name,context);
        try{
            //MimeMessage represents the entire email.
            //Creating the actual email message
            MimeMessage mimemessage=javamailsender.createMimeMessage();
            MimeMessageHelper mimemessagehelper=new MimeMessageHelper(mimemessage);
            mimemessagehelper.setTo(ToEmail);
            mimemessagehelper.setText(HtmlEmail,true);
            mimemessagehelper.setSubject(EmailConstants.procurement_Company_email_Subject_Line);
            javamailsender.send(mimemessage);
        }
        catch(Exception e){
            log.error(e.getMessage());
        }

    }
}
