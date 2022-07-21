package zw.co.jugaad.userservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zw.co.jugaad.userservice.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmailAddress(@Param("email") String email);

    User findUserByUserName(@Param("userName") String userName);

    User findByMobileNumber(@Param("mobileNumber") String mobileNumber);

    Optional<User> findByUserId(@Param("userId") String userId);
}