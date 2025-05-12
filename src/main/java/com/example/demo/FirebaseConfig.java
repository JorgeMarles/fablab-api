package com.example.demo;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Configuration
public class FirebaseConfig {

    @Value("${FIREBASE_CREDENTIALS_BASE64}")
    private String firebaseCredentialsBase64;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        // You need to have firebase-service-account.json in your resources folder
        byte[] decodedCredentials = Base64.getDecoder().decode(firebaseCredentialsBase64);
        InputStream serviceAccount = new ByteArrayInputStream(decodedCredentials);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(credentials)
                .build();
        
        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        }
        return FirebaseApp.getInstance();
    }
}