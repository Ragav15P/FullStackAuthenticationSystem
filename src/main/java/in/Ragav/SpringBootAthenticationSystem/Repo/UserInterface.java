package in.Ragav.SpringBootAthenticationSystem.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.Ragav.SpringBootAthenticationSystem.model.User;

public interface UserInterface extends JpaRepository<User,Long>
{
      Optional<User>findByEmail(String email);
      Boolean existsByEmail(String email);
}
