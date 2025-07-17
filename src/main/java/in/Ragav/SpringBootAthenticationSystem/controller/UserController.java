package in.Ragav.SpringBootAthenticationSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import in.Ragav.SpringBootAthenticationSystem.dto.ProfileRequest;
import in.Ragav.SpringBootAthenticationSystem.dto.ProfileResponse;
import in.Ragav.SpringBootAthenticationSystem.service.EmailService;
import in.Ragav.SpringBootAthenticationSystem.service.ProfileServiceImplementation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {

    //@Autowired
    private final ProfileServiceImplementation pro;
    @Autowired
    private final  PasswordEncoder pwd;
    
    private final EmailService mail;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProfileResponse> createPro(@Valid @RequestBody ProfileRequest req) {
        ProfileResponse response = pro.createProfile(req);
        mail.sendWelcomeEmail(response.getEmail(), response.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/test")
    public String test()
    {
    	return "Auth is Working";
    }
    
    @GetMapping("/getProfile")
    public ProfileResponse getProfile(@CurrentSecurityContext(expression="authentication?.name") String email)
    {
    	return pro.getProfile(email);
    }
}
