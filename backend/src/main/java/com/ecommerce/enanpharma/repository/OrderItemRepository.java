package com.ecommerce.enanpharma.repository;

import com.ecommerce.enanpharma.entity.OrderItem;
import com.ecommerce.enanpharma.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
    List<OrderItem> findByOrderId(Long orderId);
    
    List<OrderItem> findByProductId(Long productId);
    
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.user.id = :userId")
    List<OrderItem> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT SUM(oi.quantity) FROM OrderItem oi WHERE oi.product.id = :productId AND oi.order.status = 'DELIVERED'")
    Long getTotalQuantitySoldByProductId(@Param("productId") Long productId);
    
    @Query("SELECT oi.product, SUM(oi.quantity) as totalSold FROM OrderItem oi WHERE oi.order.status = 'DELIVERED' GROUP BY oi.product ORDER BY totalSold DESC")
    List<Object[]> findBestSellingProducts();
}