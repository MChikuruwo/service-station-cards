package zw.co.jugaad.transactiondesignpatterns.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.jugaad.transactiondesignpatterns.dto.product.ProductRequestDto;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Product;

public interface ProductService {

    Product save(ProductRequestDto requestDto) ;

    void update(ProductRequestDto requestDto);

    Page<Product> getAllProducts(Pageable pageable);

    Product getProduct(Long id);

    void deleteProduct(Long id);

    Product getOne(Long id);

    Page<Product> findAllByServiceStation_Id(Long serviceStationId, Pageable pageable);

    Product findByProductName(String productName);
}