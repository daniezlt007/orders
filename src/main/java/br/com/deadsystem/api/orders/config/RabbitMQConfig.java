package br.com.deadsystem.api.orders.config;

import br.com.deadsystem.api.orders.service.OrderConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_QUEUE = "order.queue";

    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_QUEUE, true);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        // Criar e configurar o ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Adiciona o módulo para tratar UUID
        objectMapper.registerModule(new com.fasterxml.jackson.module.paramnames.ParameterNamesModule());
        objectMapper.enableDefaultTyping();

        return new Jackson2JsonMessageConverter(objectMapper);
    }

}
