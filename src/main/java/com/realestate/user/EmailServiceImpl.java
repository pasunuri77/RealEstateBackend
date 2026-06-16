package com.realestate.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class EmailServiceImpl implements EmailService {

    @Value("${brevo.api.key}")
    private String apiKey;

    @Override
    public void sendPin(String email, String pin) {

        System.out.println("===== EMAIL SERVICE STARTED =====");
        System.out.println("EMAIL = " + email);
        System.out.println("PIN = " + pin);


        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_JSON);

        headers.set(
                "api-key",
                apiKey);

        String body = String.format("""
                {
                  "sender": {
                    "name": "Real Estate",
                    "email": "pasunurisagar2001@gmail.com"
                  },
                  "to": [
                    {
                      "email": "%s"
                    }
                  ],
                  "subject": "Email Verification",
                  "htmlContent": "<h2>Email Verification</h2><p>Your PIN is:</p><h1>%s</h1><p>Valid for 5 minutes</p>"
                }
                """, email, pin);


        HttpEntity<String> entity =
                new HttpEntity<>(
                        body,
                        headers);

        System.out.println("BODY = ");
        System.out.println(body);

        try {

            String response =
                    restTemplate.exchange(
                            "https://api.brevo.com/v3/smtp/email",
                            HttpMethod.POST,
                            entity,
                            String.class
                    ).getBody();

            System.out.println("BREVO RESPONSE:");
            System.out.println(response);

        } catch (Exception e) {

            System.out.println("BREVO ERROR:");
            e.printStackTrace();
        }

    }
}
