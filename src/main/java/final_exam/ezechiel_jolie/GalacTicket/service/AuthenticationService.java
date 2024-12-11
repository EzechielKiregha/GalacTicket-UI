package final_exam.ezechiel_jolie.GalacTicket.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import final_exam.ezechiel_jolie.GalacTicket.model.AccessKey;
import final_exam.ezechiel_jolie.GalacTicket.model.Authentication;
import final_exam.ezechiel_jolie.GalacTicket.model.User;
import final_exam.ezechiel_jolie.GalacTicket.repository.AccessKeyRepository;
import final_exam.ezechiel_jolie.GalacTicket.repository.UserRepository;

import java.util.List;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JsonWebTokenService jwtService;

    private final AccessKeyRepository accessKeyRepository;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JsonWebTokenService jwtService,
                                 AccessKeyRepository accessKeyRepository,
                                 AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.accessKeyRepository = accessKeyRepository;
        this.authenticationManager = authenticationManager;
    }

    public Authentication register(User request) {

        // check if user already exist. if exist than authenticate the user
        if(repository.findByEmail(request.getUsername()).isPresent()) {
            return new Authentication(null, "User already exist");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        user.setRole(request.getRole());

        user = repository.save(user);

        String jwt = jwtService.generateToken(user);

        saveUserToken(jwt, user);

        return new Authentication(jwt, "User registration was successful");

    }

    public Authentication authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = repository.findByUsername(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        return new Authentication(jwt, "User login was successful");

    }
    private void revokeAllTokenByUser(User user) {
        List<AccessKey> validTokens = accessKeyRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setDeactivated(true);
        });

        accessKeyRepository.saveAll(validTokens);
    }
    private void saveUserToken(String jwt, User user) {
        AccessKey accessKey = new AccessKey();
        accessKey.setKeyValue(jwt);;
        accessKey.setDeactivated(false);
        accessKey.setUser(user);
        accessKeyRepository.save(accessKey);
    }
}
