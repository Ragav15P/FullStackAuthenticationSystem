package in.Ragav.SpringBootAthenticationSystem.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import in.Ragav.SpringBootAthenticationSystem.dto.LoginRequest;
import in.Ragav.SpringBootAthenticationSystem.dto.LoginResponse;
import in.Ragav.SpringBootAthenticationSystem.dto.PasswordResetRequest;
import in.Ragav.SpringBootAthenticationSystem.jwt.JwtService;
import in.Ragav.SpringBootAthenticationSystem.service.AppUserService;
import in.Ragav.SpringBootAthenticationSystem.service.ProfileServiceImplementation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController 
{
	
	private final AuthenticationManager authManager;
	private final AppUserService userservice;
	private final JwtService jwtService;
	private final ProfileServiceImplementation pimp;
	@PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequest req)
    {
    	try
    	{
    		authenticate(req.getEmail(),req.getPassword());
    		UserDetails userDet=userservice.loadUserByUsername(req.getEmail());
    		String jwtToken=jwtService.generateToken(userDet);
    		ResponseCookie cookie=ResponseCookie.from("jwt",jwtToken)
    				.httpOnly(true)
    				.path("/")
    				.maxAge(Duration.ofDays(1))
    				.sameSite("Strict")
    				.build();
    		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString())
    				.body(new LoginResponse(req.getEmail(),jwtToken));
    	}
    	catch(BadCredentialsException ex)
    	{
    		Map<String,Object>error=new HashMap<>();
    		error.put("error", true);
    		error.put("message", "Email Or Password Is Incorrect");
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    	}
    	catch(DisabledException ex)
    	{
    		Map<String,Object>error=new HashMap<>();
    		error.put("error", true);
    		error.put("message", "Account Is Disabled");
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    	}
    	catch(Exception ex)
    	{
    		Map<String,Object>error=new HashMap<>();
    		error.put("error", true);
    		error.put("message", "Authentication Failed");
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    	}
    }
    
    public void authenticate(String email,String password)
    {
    	authManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
    }
    @GetMapping("/is-authenticated")
    public ResponseEntity<Boolean>isAuthenticated(@CurrentSecurityContext(expression="authentication?.name")String email)
    {
    	return ResponseEntity.ok(email!=null);
    }
    @PostMapping("/send-reset-otp")
    public void sendResetOtp(@RequestParam String email)
    {
    	try
    	{
    	   pimp.sendResetOtp(email);
    	}
    	catch(Exception e)
    	{
    		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
    	}
    }
    @PostMapping("/reset-password")
    public void resetPassword(@Valid @RequestBody PasswordResetRequest preq)
    {
    	try
    	{
    	   pimp.resetPassword(preq.getEmail(), preq.getOtp(), preq.getNewPassword());
    	}
    	catch(Exception e)
    	{
    		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
    	}
    }
    @PostMapping("/send-otp")
    public void sendVerifyOtp(@CurrentSecurityContext(expression="authentication?.name")String email)
    {
    	try
    	{
    		pimp.sendOtp(email);
    	}
    	catch(Exception e)
    	{
    		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
    	}
    }
    
    @PostMapping("/account-verify")
    public void verifyAccount(@RequestBody Map<String,Object> request,@CurrentSecurityContext(expression="authentication?.name")String email)
    {
    	
    	if(request.get("otp").toString()==null )
    	{
    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Missing Details");
    	}
    	try
    	{
    		pimp.verifyOtp(email,request.get("otp") .toString());
    	}
    	catch(Exception e)
    	{
    		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
    	}
    }
}
