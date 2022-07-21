package zw.co.jugaad.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Owner;

import java.util.Optional;

@Repository
@Transactional(transactionManager = "mysqlTransactionManager")
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    Optional<Owner> findOwnerByCompanyName(@Param("companyName") String companyName);

    Optional<Owner> findByOwnerId(@Param("ownerId") String ownerId);
}