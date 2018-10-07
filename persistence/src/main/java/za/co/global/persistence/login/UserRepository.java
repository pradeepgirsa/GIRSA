package za.co.global.persistence.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.login.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
