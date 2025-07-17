package in.Ragav.SpringBootAthenticationSystem.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileResponse {

    private String userId; // Changed to String to match UUID format

    private String name;
    private String email;
    private Boolean isAccountVerified;
}
