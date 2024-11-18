package br.com.deadsystem.api.orders.model;

import br.com.deadsystem.api.orders.dto.OrderDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();

    private double totalValue;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public Order(OrderDTO orderDTO) {
        if(orderDTO.getId() != null){
            this.id = UUID.fromString(orderDTO.getId());
        }
        this.totalValue = orderDTO.getTotalValue();
        this.status = (orderDTO.getStatus() != null) ? StatusEnum.valueOf(orderDTO.getStatus().toString()) : StatusEnum.PENDING;
        this.items = OrderItem.converter(orderDTO.getItems());
    }

    public static List<Order> converter(List<OrderDTO> orders){
        return orders.stream().map(Order::new).collect(Collectors.toList());
    }

    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this); // Estabelece o relacionamento bidirecional
    }

    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }

}
