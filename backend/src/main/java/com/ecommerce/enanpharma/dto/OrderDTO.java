package com.ecommerce.enanpharma.dto;

import com.ecommerce.enanpharma.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private Long userId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private AddressDTO address;
    private List<OrderItemDTO> items;
    private BigDecimal total;
    private Order.OrderStatus status;
    private Order.PaymentMethod paymentMethod;
    private Integer paymentInstallments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public OrderDTO() {}
}