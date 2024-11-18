package br.com.deadsystem.api.orders.controller;

import br.com.deadsystem.api.orders.dto.OrderDTO;
import br.com.deadsystem.api.orders.service.OrderServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrderController(@Valid @RequestBody OrderDTO order) {
        OrderDTO savedOrder = orderService.salvar(order);
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping
    public ResponseEntity<?> getAllOrdersController() {
        List<OrderDTO> orderDTOS = orderService.buscarTodos();
        return !orderDTOS.isEmpty() ? ResponseEntity.ok(orderDTOS) : ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderByIdController(@PathVariable UUID id) {
        OrderDTO orderDTO = orderService.buscarPorId(id.toString());
        return orderDTO != null ? ResponseEntity.ok(orderDTO) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrderController(@PathVariable UUID id, @RequestBody OrderDTO order) {
        return ResponseEntity.ok(orderService.atualizar(id.toString(), order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderController(@PathVariable UUID id) {
        orderService.deletar(id.toString());
        return ResponseEntity.noContent().build();
    }

}
