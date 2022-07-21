package zw.co.codehive.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.codehive.transactiondesignpatterns.projection.ProductResultDto;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Product;

import java.util.Optional;

@Repository
@Transactional(transactionManager = "mysqlTransactionManager")
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByServiceStation_Id(Long serviceStationId, Pageable pageable);


    @Query(value = "select product.id as id,\n" +
            "       product_name as productName,\n" +
            "       price as price,\n" +
            "       ss.id as serviceStationId,\n" +
            "       station_name as stationName\n" +
            "from product\n" +
            "left join service_station ss on ss.id = product.service_station_id\n" +
            "where product.id = ?1", nativeQuery = true)
    Optional<ProductResultDto> findProductById(Long id);

    Optional<Product> findByProductName(@Param("productName") String productName);
}