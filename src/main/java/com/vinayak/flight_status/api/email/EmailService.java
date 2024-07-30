package com.vinayak.flight_status.api.email;

public interface EmailService {

    //to sending simple mail with text and body
    String sendSimpleMail(EmailDetails details);

    // wanted to send html file of ticket but due to time bound did not used this
    String sendMailWithAttachment(EmailDetails details);
}