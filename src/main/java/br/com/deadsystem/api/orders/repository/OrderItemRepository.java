package br.com.deadsystem.api.orders.repository;

import br.com.deadsystem.api.orders.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    public List<OrderItem> findByOrderId(UUID orderId);

}
