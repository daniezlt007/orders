package br.com.deadsystem.api.orders.dto;

import br.com.deadsystem.api.orders.model.Order;
import br.com.deadsystem.api.orders.model.StatusEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDTO implements Serializable {

    private String id;
    @NotNull(message = "O valor total do pedido é obrigatório", groups = {OrderDTO.class})
    @Min(value = 0, message = "O valor total do pedido deve ser maior que zero")
    private double totalValue;

    private StatusEnum status;
    List<OrderItemDTO> items;

    public OrderDTO(Order order) {
        this.id = order.getId().toString();
        this.totalValue = order.getTotalValue();
        this.status = order.getStatus();
        this.items = order.getItems().stream()
                .map(OrderItemDTO::new)
                .collect(Collectors.toList());
    }

    public static List<OrderDTO> converter(List<Order> orders){
        return orders.stream().map(OrderDTO::new).collect(Collectors.toList());
    }


}
