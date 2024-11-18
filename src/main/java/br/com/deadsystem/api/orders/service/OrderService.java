package br.com.deadsystem.api.orders.service;

import br.com.deadsystem.api.orders.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    public OrderDTO salvar(OrderDTO orderDTO);
    public List<OrderDTO> buscarTodos();
    public OrderDTO buscarPorId(String id);
    public OrderDTO atualizar(String id, OrderDTO orderDTO);
    public void deletar(String id) throws RuntimeException;

}
