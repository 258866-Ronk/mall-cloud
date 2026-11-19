package com.mallcloud.product.config;

import com.fasterxml.jackson.databind.ObjectMapper; import com.mallcloud.api.message.MqNames;
import org.springframework.amqp.core.*; import org.springframework.amqp.rabbit.connection.ConnectionFactory; import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter; import org.springframework.context.annotation.*;

@Configuration
public class RabbitConfig {
    @Bean DirectExchange businessExchange() { return new DirectExchange(MqNames.BUSINESS_EXCHANGE, true, false); }
    @Bean Queue productChangedQueue() { return QueueBuilder.durable(MqNames.PRODUCT_CHANGED_QUEUE).build(); }
    @Bean Binding productBinding(Queue productChangedQueue, DirectExchange businessExchange) { return BindingBuilder.bind(productChangedQueue).to(businessExchange).with(MqNames.PRODUCT_CHANGED_KEY); }
    @Bean Jackson2JsonMessageConverter jsonMessageConverter(ObjectMapper mapper) { return new Jackson2JsonMessageConverter(mapper); }
    @Bean RabbitTemplate rabbitTemplate(ConnectionFactory factory, Jackson2JsonMessageConverter converter) { RabbitTemplate template = new RabbitTemplate(factory); template.setMessageConverter(converter); template.setMandatory(true); return template; }
}
