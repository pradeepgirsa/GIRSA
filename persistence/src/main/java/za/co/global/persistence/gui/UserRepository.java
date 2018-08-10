package za.co.global.persistence.gui;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.global.domain.gui.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
