package com.ecommerce.enanpharma.service;

import com.ecommerce.enanpharma.dto.*;
import com.ecommerce.enanpharma.entity.*;
import com.ecommerce.enanpharma.exception.BadRequestException;
import com.ecommerce.enanpharma.exception.ResourceNotFoundException;
import com.ecommerce.enanpharma.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    
    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, 
                       AddressRepository addressRepository, ProductRepository productRepository, 
                       UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.addressRepository = addressRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
    
    public OrderDTO createOrder(CreateOrderDTO createOrderDTO, Long userId) {
        // Find user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        // Create address
        Address address = new Address();
        address.setStreet(createOrderDTO.getAddress().getStreet());
        address.setNumber(createOrderDTO.getAddress().getNumber());
        address.setComplement(createOrderDTO.getAddress().getComplement());
        address.setNeighborhood(createOrderDTO.getAddress().getNeighborhood());
        address.setCity(createOrderDTO.getAddress().getCity());
        address.setState(createOrderDTO.getAddress().getState());
        address.setZipCode(createOrderDTO.getAddress().getZipCode());
        address = addressRepository.save(address);
        
        // Create order
        Order order = new Order();
        order.setUser(user);
        order.setCustomerName(createOrderDTO.getCustomerName());
        order.setCustomerEmail(createOrderDTO.getCustomerEmail());
        order.setCustomerPhone(createOrderDTO.getCustomerPhone());
        order.setAddress(address);
        order.setPaymentMethod(createOrderDTO.getPaymentMethod());
        order.setPaymentInstallments(createOrderDTO.getPaymentInstallments());
        order.setStatus(Order.OrderStatus.PENDING);
        order.setTotal(BigDecimal.ZERO); // Initialize total to avoid null constraint violation
        
        // Calculate total and create order items
        BigDecimal total = BigDecimal.ZERO;
        order = orderRepository.save(order);
        
        for (CreateOrderDTO.OrderItemRequestDTO itemRequest : createOrderDTO.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemRequest.getProductId()));
            
            // Check stock
            if (product.getQuantity() < itemRequest.getQuantity()) {
                throw new BadRequestException("Insufficient stock for product: " + product.getName());
            }
            
            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setUnitPrice(new BigDecimal(product.getPrice()));
            orderItem.setTotal(new BigDecimal(product.getPrice()).multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
            
            orderItemRepository.save(orderItem);
            
            // Update product stock
            product.setQuantity(product.getQuantity() - itemRequest.getQuantity());
            productRepository.save(product);
            
            total = total.add(orderItem.getTotal());
        }
        
        // Update order total
        order.setTotal(total);
        order = orderRepository.save(order);
        
        return mapToDTO(order);
    }
    
    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return orders.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    
    public OrderDTO getOrderById(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        
        // Check if order belongs to user
        if (!order.getUser().getId().equals(userId)) {
            throw new BadRequestException("Order does not belong to user");
        }
        
        return mapToDTO(order);
    }
    
    public OrderDTO updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        
        order.setStatus(status);
        order = orderRepository.save(order);
        
        return mapToDTO(order);
    }
    
    public void cancelOrder(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        
        // Check if order belongs to user
        if (!order.getUser().getId().equals(userId)) {
            throw new BadRequestException("Order does not belong to user");
        }
        
        // Can only cancel pending or confirmed orders
        if (order.getStatus() != Order.OrderStatus.PENDING && order.getStatus() != Order.OrderStatus.CONFIRMED) {
            throw new BadRequestException("Cannot cancel order with status: " + order.getStatus());
        }
        
        // Restore product stock
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        for (OrderItem item : orderItems) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() + item.getQuantity());
            productRepository.save(product);
        }
        
        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
    
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    
    private OrderDTO mapToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setCustomerName(order.getCustomerName());
        dto.setCustomerEmail(order.getCustomerEmail());
        dto.setCustomerPhone(order.getCustomerPhone());
        dto.setAddress(mapAddressToDTO(order.getAddress()));
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setPaymentInstallments(order.getPaymentInstallments());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        
        // Load order items
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
        dto.setItems(orderItems.stream().map(this::mapOrderItemToDTO).collect(Collectors.toList()));
        
        return dto;
    }
    
    private AddressDTO mapAddressToDTO(Address address) {
        AddressDTO dto = new AddressDTO();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setNumber(address.getNumber());
        dto.setComplement(address.getComplement());
        dto.setNeighborhood(address.getNeighborhood());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZipCode(address.getZipCode());
        return dto;
    }
    
    private OrderItemDTO mapOrderItemToDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(orderItem.getId());
        dto.setProductId(orderItem.getProduct().getId());
        dto.setProductName(orderItem.getProduct().getName());
        dto.setProductImageUrl(orderItem.getProduct().getImageUrl());
        dto.setQuantity(orderItem.getQuantity());
        dto.setUnitPrice(orderItem.getUnitPrice());
        dto.setTotal(orderItem.getTotal());
        return dto;
    }
}