package wolox.training.services;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import wolox.training.constants.ErrorConstants;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username).orElseThrow(() ->
                new UserNotFoundException(ErrorConstants.NOT_EXIST_USER_NAME));

        /*return new org.springframework.security.core.userdetails.User(username,
                user.getPassword(),
                null);*/

        return new org.springframework.security.core.userdetails.User(username,
                user.getPassword(),
                new ArrayList<>());
    }
}
