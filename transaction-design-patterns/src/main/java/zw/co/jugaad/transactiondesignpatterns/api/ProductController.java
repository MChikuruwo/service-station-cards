package zw.co.jugaad.transactiondesignpatterns.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.jugaad.transactiondesignpatterns.dto.product.ProductRequestDto;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Product;
import zw.co.jugaad.transactiondesignpatterns.response.GenericResponse;
import zw.co.jugaad.transactiondesignpatterns.service.ProductService;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/product")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> registerProduct(@RequestBody ProductRequestDto requestDto) throws IllegalAccessException, JsonProcessingException {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri)
                .body(productService.save(requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @GetMapping("/name")
    public Product getProductByName(@RequestParam("productName") String productName) {
        return productService.findByProductName(productName);
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<Product>> getProducts(@PathVariable("page") int page,
                                                     @PathVariable("size") int size) {
        return ResponseEntity.ok(productService.getAllProducts(PageRequest.of(page, size)));
    }


    @PutMapping("/edit")
    public ResponseEntity<GenericResponse> editProduct(@RequestParam("productId") Long productId,
            @RequestBody ProductRequestDto productRequestDto) throws IllegalAccessException {
        productService.getOne(productId);
        productService.update(productRequestDto);
        return ResponseEntity.ok(new GenericResponse("Record updated successfully"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new GenericResponse("Record deleted successfully"));
    }

    @GetMapping("/{service-station-id}/{page}/{size}")
    public ResponseEntity<Page<Product>> getProductsByServiceStationId(@PathVariable("service-station-id") Long serviceStationId,
                                                                           @PathVariable("page") int page,
                                                                           @PathVariable("size") int size)  {

        return ResponseEntity.ok(productService.findAllByServiceStation_Id(serviceStationId,PageRequest.of(page, size)));
    }
}