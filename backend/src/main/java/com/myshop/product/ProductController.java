package com.myshop.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping
    public ResponseEntity<?> getProducts(@RequestParam(required = false) Long id) {
        if (id != null) {
            Optional<Product> product = productRepository.findById(id);
            return product.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.ok(productRepository.findAll());
    }
    
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productRepository.findById(id)
            .map(product -> {
                if (updatedProduct.getName() != null) {
                    product.setName(updatedProduct.getName());
                }
                if (updatedProduct.getDescription() != null) {
                    product.setDescription(updatedProduct.getDescription());
                }

                if (updatedProduct.getPrice() > 0) {
                    product.setPrice(updatedProduct.getPrice());
                }

                if (updatedProduct.getStock() >= 0) {
                    product.setStock(updatedProduct.getStock());
                }

                if (updatedProduct.getImg() != null) {
                    product.setImg(updatedProduct.getImg());
                }

                if (updatedProduct.getCategory() != null) {
                    product.setCategory(updatedProduct.getCategory());
                }

                Product savedProduct = productRepository.save(product);
                return ResponseEntity.ok(savedProduct);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}