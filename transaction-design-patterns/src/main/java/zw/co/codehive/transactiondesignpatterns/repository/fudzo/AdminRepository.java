package zw.co.codehive.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Admin;

import java.util.Optional;

@Repository
@Transactional(transactionManager = "userTransactionManager")
public interface AdminRepository  extends JpaRepository<Admin,Long> {

    Optional<Admin> findByUserName(String userName);
}