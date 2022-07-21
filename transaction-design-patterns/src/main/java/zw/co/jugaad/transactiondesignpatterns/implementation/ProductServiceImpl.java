package zw.co.jugaad.transactiondesignpatterns.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.jugaad.transactiondesignpatterns.dto.product.ProductRequestDto;
import zw.co.jugaad.transactiondesignpatterns.exception.BusinessValidationException;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Product;
import zw.co.jugaad.transactiondesignpatterns.repository.fudzo.ProductRepository;
import zw.co.jugaad.transactiondesignpatterns.service.ProductService;
import zw.co.jugaad.transactiondesignpatterns.service.ServiceStationService;


@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ServiceStationService serviceStationService;
    private final ProductRepository productRepository;

    public ProductServiceImpl(ServiceStationService serviceStationService, ProductRepository productRepository) {
        this.serviceStationService = serviceStationService;
        this.productRepository = productRepository;
    }


    @Override
    public Product save(ProductRequestDto requestDto) {
        var serviceStation = serviceStationService.findByStationName(requestDto.getStationName());
        Product product = Product.builder()
                .productName(requestDto.getProductName())
                .price(requestDto.getPrice())
                .serviceStation(serviceStation)
                .quantity(requestDto.getQuantity())
                .build();
        return productRepository.save(product);
    }

    @Override
    public void update(ProductRequestDto requestDto) {
        var serviceStation = serviceStationService.findByStationName(requestDto.getStationName());
        Product product = Product.builder()
                .productName(requestDto.getProductName())
                .price(requestDto.getPrice())
                .serviceStation(serviceStation)
                .build();
        productRepository.save(product);
    }

    @Override
    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product getProduct(Long id) {
        return getOne(id);
    }

    @Override
    public void deleteProduct(Long id) {
        var product = getProduct(id);

        if (product == null) throw new BusinessValidationException("Product with id " + id + " does not exist");

        productRepository.delete(product);
    }

    @Override
    public Product getOne(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new BusinessValidationException("Product with id " + id + " does not exist")
        );
    }

    @Override
    public Page<Product> findAllByServiceStation_Id(Long serviceStationId, Pageable pageable) {
        return productRepository.findAllByServiceStation_Id(serviceStationId, pageable);
    }

    @Override
    public Product findByProductName(String productName) {
        var product = productRepository.findByProductName(productName);

        if (product.isEmpty()) throw new BusinessValidationException("product: " + productName + " not found");

        return product.get();
    }
}