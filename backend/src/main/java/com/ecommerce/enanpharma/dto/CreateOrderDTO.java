package com.ecommerce.enanpharma.dto;

import com.ecommerce.enanpharma.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderDTO {
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private AddressDTO address;
    private List<OrderItemRequestDTO> items;
    private Order.PaymentMethod paymentMethod;
    private Integer paymentInstallments;
    
    public CreateOrderDTO() {}
    
    @Getter
    @Setter
    public static class OrderItemRequestDTO {
        private Long productId;
        private Integer quantity;
        
        public OrderItemRequestDTO() {}
        
        public OrderItemRequestDTO(Long productId, Integer quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }
    }
}