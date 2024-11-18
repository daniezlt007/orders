package br.com.deadsystem.api.orders.dto;

import br.com.deadsystem.api.orders.model.OrderItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemDTO implements Serializable {

    private String id;
    @NotNull(message = "O nome do produto é obrigatório")
    @Size(min = 2, max = 100, message = "O nome do produto deve ter no máximo 100 caracteres")
    private String productName;

    @NotNull(message = "O preço do produto é obrigatório")
    @Min(message = "O preço do produto deve ser maior que zero", value = 0)
    private double price;

    @NotNull(message = "A quantidade do produto é obrigatória")
    @Min(message = "A quantidade do produto deve ser maior que zero", value = 0)
    private int quantity;

    public OrderItemDTO(OrderItem orderItem) {
        this.id = orderItem.getId().toString();
        this.productName = orderItem.getProductName();
        this.price = orderItem.getPrice();
        this.quantity = orderItem.getQuantity();
    }

    public static List<OrderItemDTO> converter(List<OrderItem> orders){
        return orders.stream().map(OrderItemDTO::new).collect(Collectors.toList());
    }

}
