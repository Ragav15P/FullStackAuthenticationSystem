package in.Ragav.SpringBootAthenticationSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse 
{
   private String email;
   private String token;
}
