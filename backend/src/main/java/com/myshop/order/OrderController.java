package com.myshop.order;

import com.myshop.product.Product;
import com.myshop.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        List<Product> products = productRepository.findAllById(
            order.getProducts().stream().map(Product::getId).toList()
        );

        if (products.isEmpty()) {
            throw new RuntimeException("No valid products selected");
        }

        for (Product product : products) {
            if (product.getStock() <= 0) {
                throw new RuntimeException("Product out of stock: " + product.getName());
            }
            product.setStock(product.getStock() - 1);
        }
        productRepository.saveAll(products);

        order.setProducts(products);
        return orderRepository.save(order);
    }

    @PatchMapping("/{id}")
    public Order updateShippingStatus(@PathVariable Long id, @RequestParam String status) {
        return orderRepository.findById(id).map(order -> {
            order.setShippingStatus(status);
            return orderRepository.save(order);
        }).orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
