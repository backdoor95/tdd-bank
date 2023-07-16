package shop.mtcoding.tddbank.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(@Param("username") String username);// name query
    // 이거 말고 jpql를 사용해도됨.
    // select * from user where username("username") = ?(username)
}
