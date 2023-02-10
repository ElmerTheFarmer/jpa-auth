package ajc.jpa.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ajc.jpa.models.CustomUser;

public interface UserRepository extends JpaRepository<CustomUser, Long>{
	Optional<CustomUser> findByUsername(String username);

}
