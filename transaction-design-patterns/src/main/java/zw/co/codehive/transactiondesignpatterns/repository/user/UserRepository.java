package zw.co.codehive.transactiondesignpatterns.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.codehive.transactiondesignpatterns.model.user.User;


@Repository
@Transactional(transactionManager = "userTransactionManager")
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmailAddress(@Param("email") String email);

    User findUserByUserName(@Param("userName") String userName);

    User findByMobileNumber(@Param("mobileNumber") String mobileNumber);
}