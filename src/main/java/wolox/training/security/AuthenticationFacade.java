package wolox.training.security;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {
    @Override
        public Authentication getAuthentication() {
            return SecurityContextHolder.getContext().getAuthentication();
        }
}
