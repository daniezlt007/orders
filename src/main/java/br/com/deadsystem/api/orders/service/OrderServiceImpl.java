package br.com.deadsystem.api.orders.service;

import br.com.deadsystem.api.orders.config.RabbitMQConfig;
import br.com.deadsystem.api.orders.dto.OrderDTO;
import br.com.deadsystem.api.orders.dto.OrderItemDTO;
import br.com.deadsystem.api.orders.exceptions.NegocioException;
import br.com.deadsystem.api.orders.model.Order;
import br.com.deadsystem.api.orders.model.OrderItem;
import br.com.deadsystem.api.orders.model.StatusEnum;
import br.com.deadsystem.api.orders.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    public static final String ORDER_NOT_FOUND = "Order not found";
    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    public OrderServiceImpl(OrderRepository orderRepository, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public OrderDTO salvar(@Validated OrderDTO orderDTO) {
        Order order = new Order();
        order.setTotalValue(orderDTO.getTotalValue());
        if(!StatusEnum.COMPLETED.equals(order.getStatus())){
            order.setStatus(orderDTO.getStatus() != null ? StatusEnum.valueOf(orderDTO.getStatus().toString()) : StatusEnum.PENDING);
        } else {
            order.setStatus(StatusEnum.COMPLETED);
        }

        for (OrderItemDTO itemDTO : orderDTO.getItems()) {
            OrderItem item = new OrderItem();
            item.setProductName(itemDTO.getProductName());
            item.setPrice(itemDTO.getPrice());
            item.setQuantity(itemDTO.getQuantity());
            order.addItem(item);
        }


        Order savedOrder = orderRepository.save(order);
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_QUEUE, savedOrder.getId().toString());
        return new OrderDTO(savedOrder);
    }

    @Override
    public List<OrderDTO> buscarTodos() {
        log.info("Buscando todos os pedidos");
        List<Order> all = orderRepository.findAll();
        return new OrderDTO().converter(all);
    }

    @Override
    public OrderDTO buscarPorId(String id) {
        log.info("Buscando pedido: {}", id);
        return orderRepository.findById(UUID.fromString(id))
                .map(OrderDTO::new)
                .orElseThrow(() -> new NegocioException(ORDER_NOT_FOUND));
    }

    @Override
    public OrderDTO atualizar(String id, OrderDTO orderDTO) {
        log.info("Atualizando pedido: {}", id);
        Optional<Order> orderOptional = orderRepository.findById(UUID.fromString(id));
        if(orderOptional.isPresent()){
            Order order = orderOptional.get();
            order.setStatus(orderDTO.getStatus());
            orderRepository.save(order);
            return new OrderDTO(order);
        }
        throw new NegocioException(ORDER_NOT_FOUND);
    }

    @Override
    public void deletar(String id) throws RuntimeException {
        log.info("Deletando pedido: {}", id);
        Optional<Order> orderOptional = orderRepository.findById(UUID.fromString(id));
        if(orderOptional.isPresent()){
            orderRepository.delete(orderOptional.get());
        } else {
            throw new NegocioException(ORDER_NOT_FOUND);
        }
    }
}
