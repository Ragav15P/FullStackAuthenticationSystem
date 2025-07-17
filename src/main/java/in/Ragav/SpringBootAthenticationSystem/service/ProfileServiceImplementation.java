package in.Ragav.SpringBootAthenticationSystem.service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import in.Ragav.SpringBootAthenticationSystem.Repo.UserInterface;
import in.Ragav.SpringBootAthenticationSystem.dto.ProfileRequest;
import in.Ragav.SpringBootAthenticationSystem.dto.ProfileResponse;
import in.Ragav.SpringBootAthenticationSystem.model.User;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfileServiceImplementation implements ProfileInterface {

    //@Autowired
    private final UserInterface userInterface;
    
   // @Autowired
    private final PasswordEncoder pwd;
    
    @Autowired
    private JavaMailSender mail;
    
    private final EmailService emailservice;

    @Override
    public ProfileResponse createProfile(ProfileRequest req) {
    	
        User newProfile = convertToUser(req);
        if(!userInterface.existsByEmail(req.getEmail()))
        {
          newProfile = userInterface.save(newProfile); // save and get generated fields
           return convertToUserResponse(newProfile); // return the DTO
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT,"Email Already Registered with us");
    }

    public User convertToUser(ProfileRequest req) {
        return User.builder()
                .userId(UUID.randomUUID().toString())
                .name(req.getName())
                .email(req.getEmail())
                .password(pwd.encode(req.getPassword()))
                .isAccountVerified(false)
                .resetOtp(null)
                .resetOtpExpiredAt(0L)
                .verifyOtp(null)
                .verifyOtpExpiredAt(0L)
                .build();
    }

    public ProfileResponse convertToUserResponse(User newProfile) {
        return ProfileResponse.builder()
                .userId(newProfile.getUserId())
                .name(newProfile.getName())
                .email(newProfile.getEmail())
                .isAccountVerified(newProfile.getIsAccountVerified())
                .build(); 
    }

    @Override
    public ProfileResponse getProfile(String email) {
        User user = userInterface.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return convertToUserResponse(user);
    }

	@Override
	public void sendResetOtp(String email) 
	{
		 User user = userInterface.findByEmail(email)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
		 
		 
		 String otp=String.valueOf(ThreadLocalRandom.current().nextInt(100000,1000000));
		 
		 long expiryTime=System.currentTimeMillis()+(15*60*10000);
		 
		 user.setResetOtp(otp);
		 user.setResetOtpExpiredAt(expiryTime);
		 
		 userInterface.save(user);
		 
		 try
		 {
			emailservice.sendResetOtp(user.getEmail(), otp); 
		 }
		 catch(Exception ex)
		 {
			 throw new RuntimeException("unable to send email");
		 }
		 
		 
		 
		
	}

	@Override
	public void resetPassword(String email, String otp, String newpassword) {
		User user = userInterface.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
		
		
		if(user.getResetOtp()==null || !user.getResetOtp().equals(otp))
		{
			throw new RuntimeException("Invalid Otp");
		}
		if(user.getResetOtpExpiredAt()<System.currentTimeMillis())
		{
			throw new RuntimeException("Otp Expired");
		}
		user.setPassword(pwd.encode(newpassword));
		user.setResetOtp(null);
		user.setResetOtpExpiredAt(0L);
		
		userInterface.save(user);
		
	}

	@Override
	public void sendOtp(String email) 
	{
		User user = userInterface.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
		
		if(user.getIsAccountVerified()!=null  && user.getIsAccountVerified())
		{
			return;
		}
		
		 
		 String otp=String.valueOf(ThreadLocalRandom.current().nextInt(100000,1000000));
		 
		 long expiryTime=System.currentTimeMillis()+(24*60*60*1000);
		 user.setVerifyOtp(otp);
		 user.setVerifyOtpExpiredAt(expiryTime);
		    
		 
		 userInterface.save(user);
		 try
		 {
			emailservice.sendOtp(user.getEmail(), otp); 
		 }
		 catch(Exception ex)
		 {
			 throw new RuntimeException("unable to send email");
		 }
		 
		 
		 
		 
		
	 
		
	}

	@Override
	public void verifyOtp(String email, String otp) {
		
		User user = userInterface.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
		
		if(user.getVerifyOtp()==null || user.getVerifyOtp().equals("otp"))
		{
			throw new RuntimeException("Invalid Otp");
		}
		if(user.getVerifyOtpExpiredAt()<System.currentTimeMillis())
		{
			throw new RuntimeException("Otp Expired");
		}
		user.setIsAccountVerified(true);
		user.setVerifyOtp(null);
		user.setVerifyOtpExpiredAt(0L);
		userInterface.save(user);
		
	}

}
