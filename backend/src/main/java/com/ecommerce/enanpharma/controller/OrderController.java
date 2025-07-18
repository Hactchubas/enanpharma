package com.ecommerce.enanpharma.controller;

import com.ecommerce.enanpharma.dto.CreateOrderDTO;
import com.ecommerce.enanpharma.dto.OrderDTO;
import com.ecommerce.enanpharma.entity.Order;
import com.ecommerce.enanpharma.entity.User;
import com.ecommerce.enanpharma.repository.UserRepository;
import com.ecommerce.enanpharma.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final OrderService orderService;
    private final UserRepository userRepository;
    
    public OrderController(OrderService orderService, UserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }
    
    // Create new order
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        Long userId = getCurrentUserId();
        OrderDTO order = orderService.createOrder(createOrderDTO, userId);
        return ResponseEntity.ok(order);
    }
    
    // Get all orders for current user
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getUserOrders() {
        Long userId = getCurrentUserId();
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
    
    // Get specific order by ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        OrderDTO order = orderService.getOrderById(id, userId);
        return ResponseEntity.ok(order);
    }
    
    // Cancel order
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        orderService.cancelOrder(id, userId);
        return ResponseEntity.ok().build();
    }
    
    // Admin endpoints
    
    // Get all orders (admin only)
    @GetMapping("/admin/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    
    // Update order status (admin only)
    @PutMapping("/admin/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id, @RequestBody UpdateOrderStatusRequest request) {
        OrderDTO order = orderService.updateOrderStatus(id, request.getStatus());
        return ResponseEntity.ok(order);
    }
    
    // Helper method to get current user ID
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        // Find user by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
        
        return user.getId();
    }
    
    // Request DTOs
    public static class UpdateOrderStatusRequest {
        private Order.OrderStatus status;
        
        public UpdateOrderStatusRequest() {}
        
        public Order.OrderStatus getStatus() {
            return status;
        }
        
        public void setStatus(Order.OrderStatus status) {
            this.status = status;
        }
    }
}