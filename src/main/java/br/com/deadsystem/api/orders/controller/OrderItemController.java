package br.com.deadsystem.api.orders.controller;

import br.com.deadsystem.api.orders.dto.OrderItemDTO;
import br.com.deadsystem.api.orders.service.OrderItemServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders-items")
public class OrderItemController {

    private final OrderItemServiceImpl orderItemService;

    public OrderItemController(OrderItemServiceImpl orderItemService) {
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public ResponseEntity<?> buscarTodos() {
        List<OrderItemDTO> orderItemDTOS = orderItemService.buscarTodos();
        return !orderItemDTOS.isEmpty() ? ResponseEntity.ok(orderItemDTOS) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarTodosPorOrder(@PathVariable("id") String id) {
        List<OrderItemDTO> orderItemDTOS = orderItemService.buscarTodosPorOrderId(id);
        return !orderItemDTOS.isEmpty() ? ResponseEntity.ok(orderItemDTOS) : ResponseEntity.noContent().build();
    }

}
