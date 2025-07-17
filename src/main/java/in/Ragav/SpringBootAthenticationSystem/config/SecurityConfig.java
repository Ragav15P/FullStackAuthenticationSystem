package in.Ragav.SpringBootAthenticationSystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import in.Ragav.SpringBootAthenticationSystem.Filter.JwtRequestFilter;
import in.Ragav.SpringBootAthenticationSystem.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	//@Autowired
	private final  AppUserService userservice;
	
	private final JwtRequestFilter jwtRequestFilter;
	
	private final CustomAuthenticationEntryPoint authEntry;

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/send-reset-otp", "/reset-password").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .logout(AbstractHttpConfigurer::disable)
            .addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(ex->ex.authenticationEntryPoint(authEntry));

        return http.build();
    }

    @Bean
    public PasswordEncoder pwdEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*")); // Use allowedOrigins(List.of("http://localhost:3000")) for specific origins
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
    
//    @Bean
//	public DaoAuthenticationProvider authProvider()
//	{
//		DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
//	    dao.setPasswordEncoder(pwdEncoder());
//	    dao.setUserDetailsService(userservice);
//	    
//	    return dao;
//	}
//    
    
@Bean
	
	public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception
	{
		//return config.getAuthenticationManager();
	DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
	dao.setPasswordEncoder(pwdEncoder());
    dao.setUserDetailsService(userservice);
    return new ProviderManager(dao);
	}
}
