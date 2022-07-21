package zw.co.codehive.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.codehive.transactiondesignpatterns.projection.TerminalResultDto;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Terminal;

import java.util.Optional;

@Repository
@Transactional(transactionManager = "mysqlTransactionManager")
public interface TerminalRepository extends JpaRepository<Terminal, Long> {

    Page<Terminal> findAllByServiceStation_Id(Long serviceStationId, Pageable pageable);


    @Query(value = "select terminal.id as id,\n" +
            "       terminal_id as terminalId,\n" +
            "       imei as imei,\n" +
            "       status as status,\n" +
            "       terminal_type as terminalType,\n" +
            "       ss.id as stationId,\n" +
            "       station_name as serviceStation\n" +
            "from terminal\n" +
            "left join service_station ss on ss.id = terminal.service_station_id\n" +
            "where terminal.id = ?1", nativeQuery = true)
    Optional<TerminalResultDto> findTerminalById(Long terminalId);

    Optional<Terminal> findByImei(@Param("imei") String imei);

    Optional<Terminal> findByTerminalId(@Param("terminalId") String terminalId);
}
