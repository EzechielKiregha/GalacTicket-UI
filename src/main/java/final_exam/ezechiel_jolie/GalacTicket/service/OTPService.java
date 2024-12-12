package final_exam.ezechiel_jolie.GalacTicket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPService {
    @Autowired private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    private final Map<String, Integer> otpData = new HashMap<>();

    public int generateOTP(String key) {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        otpData.put(key, otp);
        // Add expiration logic, if needed, using a scheduled executor or a library like Redis
        return otp;
    }

    public boolean validateOTP(String key, int otp) {
        Integer storedOtp = otpData.get(key);
        if (storedOtp != null && storedOtp == otp) {
            otpData.remove(key); // Remove after use
            return true;
        }
        return false;
    }
    public void sendOTP(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    public void clearOTP(String key) {
        otpData.remove(key);
    }
}

