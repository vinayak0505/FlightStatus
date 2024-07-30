package com.vinayak.flight_status.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "gcp.firebase")
public class FirebaseProperties {

    final private Resource serviceAccount;

    // seting firepage key path variable
    public FirebaseProperties() {
        serviceAccount = new ClassPathResource("static/firebase-spring-key.json");
    }

    public Resource getServiceAccount() {
        return serviceAccount;
    }

}
