package final_exam.ezechiel_jolie.GalacTicket.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import final_exam.ezechiel_jolie.GalacTicket.model.AccessKey;
import final_exam.ezechiel_jolie.GalacTicket.model.Auth;
import final_exam.ezechiel_jolie.GalacTicket.model.User;
import final_exam.ezechiel_jolie.GalacTicket.repository.AccessKeyRepository;
import final_exam.ezechiel_jolie.GalacTicket.repository.UserRepository;

import java.util.List;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JsonWTService jwtService;
    private final AccessKeyRepository accessKeyRepository;
    private final AuthenticationManager authenticationManager;
    private final OTPService otpService;

    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JsonWTService jwtService,
                                 AccessKeyRepository accessKeyRepository,
                                 AuthenticationManager authenticationManager,
                                 OTPService otpService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.accessKeyRepository = accessKeyRepository;
        this.authenticationManager = authenticationManager;
        this.otpService = otpService;
    }

    public Auth register(User request) {
        if (repository.findByEmail(request.getUsername()).isPresent()) {
            return new Auth(null, "User already exists");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        

        // Generate OTP and send it to the user
        int otp = otpService.generateOTP(user.getUsername());
        if(!otpService.sendOTP(user.getEmail(), "Your OTP", "Your OTP is: " + otp)){
            user = repository.save(user);
            return new Auth(null, "Registed successful. OTP failed... Go to profile to verify.");    
        }
        user.setOtp(otp);
        user = repository.save(user);
        return new Auth(null, "User registration was successful. OTP has been sent to your email.");
    }

    public Auth authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Validate OTP
        boolean isOtpValid = otpService.validateOTP(request.getUsername(), request.getOtp());
        if (!isOtpValid) {
            return new Auth(null, "Invalid OTP");
        }
        User user = repository.findByUsername(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);
        user.setVerified(true);
        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        user = repository.save(user);

        return new Auth(jwt, "User login was successful");
    }

    private void revokeAllTokenByUser(User user) {
        List<AccessKey> validTokens = accessKeyRepository.findAllTokensByUser(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t -> t.setDeactivated(true));
        accessKeyRepository.saveAll(validTokens);
    }

    private void saveUserToken(String jwt, User user) {
        AccessKey accessKey = new AccessKey();
        accessKey.setKeyValue(jwt);
        accessKey.setDeactivated(false);
        accessKey.setUser(user);
        accessKeyRepository.save(accessKey);
    }
}
