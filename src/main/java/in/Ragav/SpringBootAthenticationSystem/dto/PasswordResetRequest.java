package in.Ragav.SpringBootAthenticationSystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequest 
{
	
	
	
	@Email(message="Enter Valid Email")
	private String email;
	
	@NotBlank(message="Valid otp Is Required")
	   private String otp;
	
	@NotBlank(message="New Password is Required")
	   private String newPassword;
}
