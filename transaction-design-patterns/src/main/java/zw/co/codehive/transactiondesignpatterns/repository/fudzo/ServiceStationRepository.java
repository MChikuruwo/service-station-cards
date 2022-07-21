package zw.co.codehive.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.ServiceStation;

import java.util.Optional;

@Repository
@Transactional(transactionManager = "mysqlTransactionManager")
public interface ServiceStationRepository extends JpaRepository<ServiceStation, Long> {

//    Page<ServiceStation> findAllByCompanyName(Long id, Pageable pageable);

//    @Query(value = "select service_station.id as id,\n" +
//            "       station_name as stationName,\n" +
//            "       address as address,\n" +
//            "       contact_number as contactNumber,\n" +
//            "       o.id as organisationId,\n" +
//            "       company_name as organisation\n" +
//            "from service_station\n" +
//            "left join owner o on o.id = service_station.owner_id\n" +
//            "where service_station.id =?1", nativeQuery = true)

    Optional<ServiceStation> findByStationName(@Param("serviceStationName") String serviceStationName);
}