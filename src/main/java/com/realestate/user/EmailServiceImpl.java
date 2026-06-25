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


        String html = """
                <div style="background:#eef2f7;padding:40px 20px;font-family:Segoe UI,Arial,sans-serif;">
                
                    <table width="100%%" style="max-width:650px;margin:auto;background:#ffffff;
                           border-radius:20px;overflow:hidden;
                           box-shadow:0 10px 30px rgba(0,0,0,0.08);">
                
                        <tr>
                            <td style="background:linear-gradient(135deg,#1e3a8a,#2563eb);
                                       text-align:center;
                                       padding:40px;">
                
                                <h1 style="color:white;margin:0;">
                                    🏢 Real Estate Constructor
                                </h1>
                
                                <p style="color:#dbeafe;margin-top:10px;">
                                    Secure Account Verification
                                </p>
                
                            </td>
                        </tr>
                
                        <tr>
                            <td style="padding:40px;">
                
                                <h2 style="margin-top:0;color:#1e293b;">
                                    Welcome to Real Estate Constructor
                                </h2>
                
                                <p style="color:#64748b;
                                          font-size:15px;
                                          line-height:26px;">
                
                                    Thank you for creating an account with us.
                                    Please verify your email address using the code below.
                
                                </p>
                
                                <div style="text-align:center;
                                            margin:35px 0;">
                
                                    <div style="
                                        display:inline-block;
                                        background:#f8fafc;
                                        border:1px solid #cbd5e1;
                                        border-radius:16px;
                                        padding:25px 45px;">
                
                                        <div style="
                                            color:#2563eb;
                                            font-size:48px;
                                            font-weight:700;
                                            letter-spacing:10px;">
                
                                            %s
                
                                        </div>
                
                                    </div>
                
                                </div>
                
                                <div style="
                                    background:#fef2f2;
                                    border-left:4px solid #ef4444;
                                    padding:15px;
                                    border-radius:8px;">
                
                                    <strong style="color:#b91c1c;">
                                        Expires in 5 minutes
                                    </strong>
                
                                </div>
                
                                <br>
                
                                <div style="
                                    background:#f8fafc;
                                    border-radius:10px;
                                    padding:20px;">
                
                                    <h4 style="margin-top:0;color:#334155;">
                                        Security Tips
                                    </h4>
                
                                    <ul style="color:#64748b;line-height:24px;">
                
                                        <li>Never share your verification code.</li>
                
                                        <li>Our team will never ask for this code.</li>
                
                                        <li>If you didn't request this email, ignore it.</li>
                
                                    </ul>
                
                                </div>
                
                            </td>
                        </tr>
                
                        <tr>
                            <td style="
                                background:#0f172a;
                                color:#94a3b8;
                                text-align:center;
                                padding:25px;">
                
                                <div style="font-size:13px;">
                
                                    © 2026 Real Estate Constructor
                
                                    <br><br>
                
                                    Hyderabad, Telangana, India
                
                                    <br>
                
                                    support@realestate.com
                
                                    <br>
                
                                    +91 98765 43210
                
                                </div>
                
                            </td>
                        </tr>
                
                    </table>
                
                </div>
                """.formatted(pin);

        html = html
                .replace("\"", "\\\"")
                .replace("\r", "")
                .replace("\n", "");

        String body = String.format("""
                {
                  "sender": {
                    "name": "Real Estate Constructor",
                    "email": "pasunurisagar2001@gmail.com"
                  },
                  "to": [
                    {
                      "email": "%s"
                    }
                  ],
                  "subject": "Email Verification",
                  "htmlContent": "%s"
                }
                """, email, html);


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

    @Override
    public void sendPasswordResetOtp(String email, String otp) {

        System.out.println("===== EMAIL SERVICE: PASSWORD RESET STARTED =====");
        System.out.println("EMAIL = " + email);
        System.out.println("OTP = " + otp);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        String html = """
                <div style="background:#eef2f7;padding:40px 20px;font-family:Segoe UI,Arial,sans-serif;">
                
                    <table width="100%%" style="max-width:650px;margin:auto;background:#ffffff;
                           border-radius:20px;overflow:hidden;
                           box-shadow:0 10px 30px rgba(0,0,0,0.08);">
                 
                        <tr>
                            <td style="background:linear-gradient(135deg,#1e3a8a,#2563eb);
                                       text-align:center;
                                       padding:40px;">
                
                                <h1 style="color:white;margin:0;">
                                    🏢 Real Estate Constructor
                                </h1>
                
                                <p style="color:#dbeafe;margin-top:10px;">
                                    Password Reset Request
                                </p>
                
                            </td>
                        </tr>
                
                        <tr>
                            <td style="padding:40px;">
                
                                <h2 style="margin-top:0;color:#1e293b;">
                                    Reset Your Password
                                </h2>
                
                                <p style="color:#64748b;
                                          font-size:15px;
                                          line-height:26px;">
                
                                    We received a request to reset the password for your Real Estate Constructor account.
                                    Please use the 6-digit verification code below to proceed with setting a new password.
                
                                </p>
                
                                <div style="text-align:center;
                                            margin:35px 0;">
                
                                    <div style="
                                        display:inline-block;
                                        background:#f8fafc;
                                        border:1px solid #cbd5e1;
                                        border-radius:16px;
                                        padding:25px 45px;">
                
                                        <div style="
                                            color:#ef4444;
                                            font-size:48px;
                                            font-weight:700;
                                            letter-spacing:10px;">
                
                                            %s
                
                                        </div>
                
                                    </div>
                
                                </div>
                
                                <div style="
                                    background:#fef2f2;
                                    border-left:4px solid #ef4444;
                                    padding:15px;
                                    border-radius:8px;">
                
                                    <strong style="color:#b91c1c;">
                                        This verification code expires in 5 minutes
                                    </strong>
                
                                </div>
                
                                <br>
                
                                <div style="
                                    background:#f8fafc;
                                    border-radius:10px;
                                    padding:20px;">
                
                                    <h4 style="margin-top:0;color:#334155;">
                                        Security Tips
                                    </h4>
                
                                    <ul style="color:#64748b;line-height:24px;">
                
                                        <li>Never share this verification code with anyone.</li>
                
                                        <li>If you did not request a password reset, please ignore this email or contact support.</li>
                
                                    </ul>
                
                                </div>
                
                                <p style="color:#94a3b8;font-size:13px;margin-top:20px;">
                                    If the code above does not work, please request a new reset code.
                                </p>
                
                            </td>
                        </tr>
                
                        <tr>
                            <td style="
                                background:#0f172a;
                                color:#94a3b8;
                                text-align:center;
                                padding:25px;">
                
                                <div style="font-size:13px;">
                
                                    © 2026 Real Estate Constructor
                
                                    <br><br>
                
                                    Hyderabad, Telangana, India
                
                                    <br>
                
                                    support@realestate.com
                
                                    <br>
                
                                    +91 98765 43210
                
                                </div>
                
                            </td>
                        </tr>
                
                    </table>
                
                </div>
                """.formatted(otp);

        html = html
                .replace("\"", "\\\"")
                .replace("\r", "")
                .replace("\n", "");

        String body = String.format("""
                {
                  "sender": {
                    "name": "Real Estate Constructor",
                    "email": "pasunurisagar2001@gmail.com"
                  },
                  "to": [
                    {
                      "email": "%s"
                    }
                  ],
                  "subject": "Password Reset Request",
                  "htmlContent": "%s"
                }
                """, email, html);

        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        try {
            String response = restTemplate.exchange(
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

