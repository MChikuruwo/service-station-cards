package zw.co.jugaad.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Attendant;
import zw.co.jugaad.transactiondesignpatterns.projection.AttendantResultDto;

import java.util.Optional;

@Repository
@Transactional(transactionManager = "mysqlTransactionManager")
public interface AttendantRepository extends JpaRepository<Attendant, Long> {

    Page<Attendant> findAllByServiceStation_Id(Long serviceStationId, Pageable pageable);

    @Query(value = "select attendant.id as id,\n" +
            "       first_name as firstName,\n" +
            "       last_name as lastName,\n" +
            "       email as email,\n" +
            "       mobile as mobile,\n" +
            "       role as role,\n" +
            "       status as status,\n" +
            "       ss.id as stationId,\n" +
            "       station_name as stationName\n" +
            "from attendant\n" +
            "left join service_station ss on ss.id = attendant.service_station_id\n" +
            "where attendant.id =?1", nativeQuery = true)
    Optional<AttendantResultDto> findAttendantById(Long id);

    Optional<Attendant> findByAttendantId(@Param("attendantId") String attendantId);
}