package com.mallcloud.payment.config;
import com.fasterxml.jackson.databind.ObjectMapper; import com.mallcloud.api.message.MqNames; import com.mallcloud.common.id.SnowflakeIdGenerator;
import org.springframework.amqp.core.DirectExchange; import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter; import org.springframework.context.annotation.*;
@Configuration public class PaymentConfig {@Bean SnowflakeIdGenerator paymentIdGenerator(){return new SnowflakeIdGenerator(7);}@Bean DirectExchange businessExchange(){return new DirectExchange(MqNames.BUSINESS_EXCHANGE,true,false);}@Bean Jackson2JsonMessageConverter jsonMessageConverter(ObjectMapper mapper){return new Jackson2JsonMessageConverter(mapper);}}
