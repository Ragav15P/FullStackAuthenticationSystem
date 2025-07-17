package in.Ragav.SpringBootAthenticationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;
    
    
    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendWelcomeEmail(String toEmail, String name) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Welcome to Our Platform");

        StringBuilder text = new StringBuilder();
        text.append("Hello ").append(name).append(",\n\n");
        text.append("Thank you for registering with us!\n");
        text.append("We’re excited to have you on board.\n\n");
        text.append("Regards,\n");
        text.append("vaultVerify-Ragav");

        message.setText(text.toString());

        javaMailSender.send(message);
    }
    
    
    public void sendResetOtp(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Password Reset OTP - VaultVerify");

        StringBuilder text = new StringBuilder();
        text.append("Hello,\n\n");
        text.append("You have requested to reset your password.\n");
        text.append("Please use the following OTP to proceed:\n\n");
        text.append("🔐 OTP: ").append(otp).append("\n\n");
        text.append("Note: This OTP is valid for the next 10 minutes only.\n\n");
        text.append("If you did not request a password reset, please ignore this email.\n\n");
        text.append("Regards,\n");
        text.append("VaultVerify - Ragav");

        message.setText(text.toString());

        javaMailSender.send(message);
    }
    
    public void sendOtp(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("Your OTP for Account Verification");

        StringBuilder text = new StringBuilder();
        text.append("Dear User,\n\n");
        text.append("Your One-Time Password (OTP) for account verification is: ").append(otp).append("\n");
        text.append("Please use this OTP to proceed. This code is valid for a limited time.\n\n");
        text.append("If you did not request this, please ignore this email.\n\n");
        text.append("Regards,\n");
        text.append("VaultVerify - Ragav");

        message.setText(text.toString());
        javaMailSender.send(message);
    }


}
