package com.mallcloud.order.config;
import com.fasterxml.jackson.databind.ObjectMapper; import com.mallcloud.api.message.MqNames; import com.mallcloud.common.id.SnowflakeIdGenerator;
import org.springframework.amqp.core.*; import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter; import org.springframework.context.annotation.*;
@Configuration public class OrderConfig {
 @Bean SnowflakeIdGenerator orderIdGenerator(){return new SnowflakeIdGenerator(6);}
 @Bean DirectExchange businessExchange(){return new DirectExchange(MqNames.BUSINESS_EXCHANGE,true,false);}
 @Bean Queue paymentSucceededQueue(){return QueueBuilder.durable(MqNames.PAYMENT_SUCCEEDED_QUEUE).build();}
 @Bean Binding paymentBinding(Queue paymentSucceededQueue,DirectExchange businessExchange){return BindingBuilder.bind(paymentSucceededQueue).to(businessExchange).with(MqNames.PAYMENT_SUCCEEDED_KEY);}
 @Bean Jackson2JsonMessageConverter jsonMessageConverter(ObjectMapper mapper){return new Jackson2JsonMessageConverter(mapper);}
}
