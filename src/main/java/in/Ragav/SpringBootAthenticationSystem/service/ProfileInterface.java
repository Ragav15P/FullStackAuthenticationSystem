package in.Ragav.SpringBootAthenticationSystem.service;

import in.Ragav.SpringBootAthenticationSystem.dto.ProfileRequest;
import in.Ragav.SpringBootAthenticationSystem.dto.ProfileResponse;

public interface ProfileInterface 
{
    ProfileResponse createProfile(ProfileRequest req);
    
    ProfileResponse getProfile(String email);
    
    
    public void sendResetOtp(String email);
    
    public void resetPassword(String email,String otp,String newpassword);
    
    void sendOtp(String email);
    void verifyOtp(String email,String otp);
}
