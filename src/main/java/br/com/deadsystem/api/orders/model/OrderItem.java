package br.com.deadsystem.api.orders.model;

import br.com.deadsystem.api.orders.dto.OrderDTO;
import br.com.deadsystem.api.orders.dto.OrderItemDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "product")
    private String productName;

    private double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;

    public OrderItem(OrderItemDTO orderItemDTO) {
        this.id = UUID.fromString(orderItemDTO.getId());
        this.productName = orderItemDTO.getProductName();
        this.price = orderItemDTO.getPrice();
        this.quantity = orderItemDTO.getQuantity();
    }

    public static List<OrderItem> converter(List<OrderItemDTO> ordersItems){
        return ordersItems.stream().map(OrderItem::new).collect(Collectors.toList());
    }

}
