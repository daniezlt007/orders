package br.com.deadsystem.api.orders.service;

import br.com.deadsystem.api.orders.dto.OrderDTO;
import br.com.deadsystem.api.orders.dto.OrderItemDTO;
import br.com.deadsystem.api.orders.exceptions.NegocioException;
import br.com.deadsystem.api.orders.model.Order;
import br.com.deadsystem.api.orders.model.OrderItem;
import br.com.deadsystem.api.orders.repository.OrderItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {

    public static final String ORDER_ITEM_NOT_FOUND = "OrderItem not found";
    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItemDTO salvar(OrderItemDTO orderItemDTO) {
        log.info("Salvando item do pedido: {}", orderItemDTO);
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(orderItemDTO.getPrice());
        orderItem.setProductName(orderItemDTO.getProductName());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        orderItemRepository.save(orderItem);
        return new OrderItemDTO(orderItem);
    }

    @Override
    public List<OrderItemDTO> buscarTodos() {
        log.info("Buscando todos os itens");
        List<OrderItem> items = orderItemRepository.findAll();
        return new OrderItemDTO().converter(items);
    }

    @Override
    public OrderItemDTO buscarPorId(String id) {
        log.info("Buscando item do pedido: {}", id);
        return orderItemRepository.findById(UUID.fromString(id))
                .map(OrderItemDTO::new)
                .orElseThrow(() -> new NegocioException(ORDER_ITEM_NOT_FOUND));
    }

    @Override
    public OrderItemDTO atualizar(String id, OrderItemDTO orderItemDTO) {
        log.info("Atualizando pedido: " + id);
        OrderItemDTO buscarOrderItem = buscarPorId(id);
        if(buscarOrderItem != null){
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(orderItemDTO.getPrice());
            orderItem.setProductName(orderItemDTO.getProductName());
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItemRepository.save(orderItem);
            return new OrderItemDTO(orderItem);
        } else {
            throw new NegocioException(ORDER_ITEM_NOT_FOUND);
        }
    }

    @Override
    public void deletar(String id) throws RuntimeException {
        log.info("Deletando item do pedido: {}", id);
        Optional<OrderItem> orderOptional = orderItemRepository.findById(UUID.fromString(id));
        if(orderOptional.isPresent()){
            orderItemRepository.delete(orderOptional.get());
        } else {
            throw new NegocioException(ORDER_ITEM_NOT_FOUND);
        }
    }

    @Override
    public List<OrderItemDTO> buscarTodosPorOrderId(String orderId) {
        List<OrderItem> orderItemList = orderItemRepository.findByOrderId(UUID.fromString(orderId));
        return OrderItemDTO.converter(orderItemList);
    }
}
