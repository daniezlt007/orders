package br.com.deadsystem.api.orders.service;

import br.com.deadsystem.api.orders.dto.OrderDTO;
import br.com.deadsystem.api.orders.dto.OrderItemDTO;
import br.com.deadsystem.api.orders.model.Order;
import br.com.deadsystem.api.orders.model.StatusEnum;
import br.com.deadsystem.api.orders.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository; // Mock do repositório

    @Mock
    private RabbitTemplate rabbitTemplate; // Mock do RabbitTemplate

    @InjectMocks
    private OrderServiceImpl orderService; // A classe que estamos testando

    private OrderDTO orderDTO; // DTO de exemplo para o teste

    @BeforeEach
    void setUp() {
        // Inicializa os mocks do Mockito
        MockitoAnnotations.openMocks(this);

        orderService = new OrderServiceImpl(orderRepository, rabbitTemplate);

        // Criando um DTO de exemplo
        orderDTO = new OrderDTO();
        //orderDTO.setId("123e4567-e89b-12d3-a456-426614174000");
        orderDTO.setTotalValue(100.0);
        orderDTO.setStatus(StatusEnum.PENDING);

        // Criando o item do pedido
        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setId("123e4567-e89b-12d3-a456-426614174001");
        itemDTO.setProductName("Produto Teste");
        itemDTO.setPrice(10.0);
        itemDTO.setQuantity(10);
        orderDTO.setItems(List.of(itemDTO));
    }

    @Test
    @DisplayName("Testar o comportamento do método salvar order.")
    void testSalvar() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductName("Produto 1");
        orderItemDTO.setPrice(100.0);
        orderItemDTO.setQuantity(2);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setTotalValue(200.0);
        orderDTO.setItems(Arrays.asList(orderItemDTO));

        Order mockOrder = new Order();
        mockOrder.setId(UUID.randomUUID());  // Gerando um ID fictício
        mockOrder.setTotalValue(200.0);

        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        OrderDTO savedOrderDTO = orderService.salvar(orderDTO);

        assertNotNull(savedOrderDTO);
        assertNotNull(savedOrderDTO.getId());
        assertEquals(mockOrder.getId().toString(), savedOrderDTO.getId());
    }

    @Test
    @DisplayName("Testar o comportamento do método salvar order com status COMPLETED.")
    void testSalvarComStatusCompleted() {
        // Configurando o status do pedido como COMPLETED
        orderDTO.setStatus(StatusEnum.COMPLETED);

        Order mockOrder = new Order();
        mockOrder.setId(UUID.randomUUID());  // Gerando um ID fictício
        mockOrder.setTotalValue(100.0);
        mockOrder.setStatus(StatusEnum.COMPLETED);

        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        OrderDTO savedOrderDTO = orderService.salvar(orderDTO);

        assertNotNull(savedOrderDTO);
        assertNotNull(savedOrderDTO.getId());
        assertEquals(mockOrder.getId().toString(), savedOrderDTO.getId());
    }

    @Test
    @DisplayName("Testar o comportamento do método salvar obtendo a falha.")
    void testSalvarQuandoOrderRepositoryFalha() {
        when(orderRepository.save(any(Order.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        assertThrows(RuntimeException.class, () -> orderService.salvar(orderDTO));
    }

    @Test
    @DisplayName("Testar o comportamento do método buscarTodos.")
    void testBuscarTodos(){
        Order mockOrder = new Order();
        mockOrder.setId(UUID.randomUUID());
        mockOrder.setTotalValue(100.0);
        mockOrder.setStatus(StatusEnum.PENDING);

        when(orderRepository.findAll()).thenReturn(List.of(mockOrder));

        List<OrderDTO> orders = orderService.buscarTodos();

        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(mockOrder.getId().toString(), orders.get(0).getId());
    }

    @Test
    @DisplayName("Testar o comportamento do método buscarPorId.")
    void testBuscarPorId(){
        Order mockOrder = new Order();
        mockOrder.setId(UUID.randomUUID());
        mockOrder.setTotalValue(100.0);
        mockOrder.setStatus(StatusEnum.PENDING);

        when(orderRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(mockOrder));

        OrderDTO order = orderService.buscarPorId(mockOrder.getId().toString());

        assertNotNull(order);
        assertEquals(mockOrder.getId().toString(), order.getId());
    }

    @Test
    @DisplayName("Testar o comportamento do método buscarPorId com falha.")
    void testBuscarPorIdComFalha(){
        when(orderRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.buscarPorId(UUID.randomUUID().toString()));
    }

    @Test
    @DisplayName("Testar o comportamento do método atualizar.")
    void testAtualizar(){
        Order mockOrder = new Order();
        mockOrder.setId(UUID.randomUUID());
        mockOrder.setTotalValue(100.0);
        mockOrder.setStatus(StatusEnum.PENDING);

        when(orderRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(mockOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        OrderDTO order = orderService.atualizar(mockOrder.getId().toString(), orderDTO);

        assertNotNull(order);
        assertEquals(mockOrder.getId().toString(), order.getId());
    }

    @Test
    @DisplayName("Testar o comportamento do método atualizar com falha.")
    void testAtualizarComFalha(){
        when(orderRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.atualizar(UUID.randomUUID().toString(), orderDTO));
    }

    @Test
    @DisplayName("Testar o comportamento do método excluir.")
    void testExcluir(){
        Order mockOrder = new Order();
        mockOrder.setId(UUID.randomUUID());
        mockOrder.setTotalValue(100.0);
        mockOrder.setStatus(StatusEnum.PENDING);

        when(orderRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.of(mockOrder));

        orderService.deletar(mockOrder.getId().toString());

        verify(orderRepository, times(1)).delete(mockOrder);
    }

    @Test
    @DisplayName("Testar o comportamento do método excluir com falha.")
    void testExcluirComFalha(){
        when(orderRepository.findById(any(UUID.class))).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.deletar(UUID.randomUUID().toString()));
    }

}



