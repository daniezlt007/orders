package br.com.deadsystem.api.orders.service;


import br.com.deadsystem.api.orders.config.RabbitMQConfig;
import br.com.deadsystem.api.orders.dto.OrderDTO;
import br.com.deadsystem.api.orders.model.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class OrderConsumer {

    private final OrderServiceImpl orderService;

    public OrderConsumer(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void processOrder(String orderId) {
        try {
            UUID id = UUID.fromString(orderId);

            OrderDTO order = orderService.buscarPorId(id.toString());
            if (order == null) {
                log.warn("Pedido não encontrado para o ID: {}", orderId);
                return;
            }

            if (StatusEnum.COMPLETED.equals(order.getStatus())) {
                log.info("Pedido {} já foi completado. Ignorando.", orderId);
                return;
            }

            // Atualizar o status do pedido
            log.info("Processando pedido: {}", order.getId());
            order.setId(orderId);
            order.setStatus(StatusEnum.COMPLETED);
            orderService.atualizar(orderId, order);

        } catch (Exception e) {
            log.error("Erro ao processar o pedido {}: {}", orderId, e.getMessage());
        }
    }

}
