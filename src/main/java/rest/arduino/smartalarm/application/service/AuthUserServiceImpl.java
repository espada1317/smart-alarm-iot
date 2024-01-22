package rest.arduino.smartalarm.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import rest.arduino.smartalarm.domain.entity.User;
import rest.arduino.smartalarm.domain.repository.UserRepository;
import rest.arduino.smartalarm.application.security.AuthUserDetails;

import java.util.Optional;

@Component
public class AuthUserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = userRepository.getUserByNickname(username);

        return userInfo.map(AuthUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
}
