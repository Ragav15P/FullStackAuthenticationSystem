package in.Ragav.SpringBootAthenticationSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.Ragav.SpringBootAthenticationSystem.Repo.UserInterface;
import in.Ragav.SpringBootAthenticationSystem.model.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    //@Autowired
    private final  UserInterface us;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = us.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER") // Adjust based on your role setup
                .build();
    }
    

}
