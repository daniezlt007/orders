package br.com.deadsystem.api.orders.service;

import br.com.deadsystem.api.orders.dto.OrderItemDTO;

import java.util.List;

public interface OrderItemService {

    public OrderItemDTO salvar(OrderItemDTO orderItemDTO);
    public List<OrderItemDTO> buscarTodos();
    public OrderItemDTO buscarPorId(String id);
    public OrderItemDTO atualizar(String id, OrderItemDTO orderItemDTO);
    public void deletar(String id) throws RuntimeException;
    public List<OrderItemDTO> buscarTodosPorOrderId(String orderId);

}
