package final_exam.ezechiel_jolie.GalacTicket.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import final_exam.ezechiel_jolie.GalacTicket.model.AccessKey;
import final_exam.ezechiel_jolie.GalacTicket.repository.AccessKeyRepository;

@Configuration
public class CustomLogoutHandler implements LogoutHandler {

    private final AccessKeyRepository accessKeyRepository;

    public CustomLogoutHandler(AccessKeyRepository accessKeyRepository) {
        this.accessKeyRepository = accessKeyRepository;
    }

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String accessKey = authHeader.substring(7);
        AccessKey storedAccessKey = accessKeyRepository.findByToken(accessKey).orElse(null);

        if(storedAccessKey != null) {
            storedAccessKey.setDeactivated(true);
            accessKeyRepository.save(storedAccessKey);
        }
    }
}
