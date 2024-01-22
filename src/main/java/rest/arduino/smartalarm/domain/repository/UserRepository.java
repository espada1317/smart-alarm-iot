package rest.arduino.smartalarm.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.arduino.smartalarm.domain.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> getUserByNickname(String nickname);

    Optional<User> getUserByEmail(String email);

}
