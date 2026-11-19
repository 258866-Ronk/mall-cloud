package com.mallcloud.seckill.config;
import com.fasterxml.jackson.databind.ObjectMapper; import com.mallcloud.api.message.MqNames; import org.springframework.amqp.core.*; import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter; import org.springframework.context.annotation.*; import org.springframework.data.redis.core.script.DefaultRedisScript;
@Configuration public class SeckillConfig {
 public static final String DLX="mall.dead.exchange",DEAD_QUEUE="mall.seckill.order.dead.queue";
 @Bean DirectExchange businessExchange(){return new DirectExchange(MqNames.BUSINESS_EXCHANGE,true,false);}@Bean DirectExchange deadExchange(){return new DirectExchange(DLX,true,false);}
 @Bean Queue seckillQueue(){return QueueBuilder.durable(MqNames.SECKILL_ORDER_QUEUE).deadLetterExchange(DLX).deadLetterRoutingKey("seckill.dead").build();}
 @Bean Binding seckillBinding(Queue seckillQueue,DirectExchange businessExchange){return BindingBuilder.bind(seckillQueue).to(businessExchange).with(MqNames.SECKILL_ORDER_KEY);}
 @Bean Queue deadQueue(){return QueueBuilder.durable(DEAD_QUEUE).build();}@Bean Binding deadBinding(Queue deadQueue,DirectExchange deadExchange){return BindingBuilder.bind(deadQueue).to(deadExchange).with("seckill.dead");}
 @Bean Jackson2JsonMessageConverter jsonMessageConverter(ObjectMapper mapper){return new Jackson2JsonMessageConverter(mapper);}
 @Bean DefaultRedisScript<Long> seckillScript(){return new DefaultRedisScript<>("local now=tonumber(ARGV[2]); local start=tonumber(ARGV[3]); local finish=tonumber(ARGV[4]); if now<start then return -4 end; if now>finish then return -5 end; if redis.call('SISMEMBER',KEYS[2],ARGV[1])==1 then return -3 end; local stock=tonumber(redis.call('GET',KEYS[1]) or '-1'); if stock<=0 then return -1 end; redis.call('DECR',KEYS[1]); redis.call('SADD',KEYS[2],ARGV[1]); redis.call('EXPIRE',KEYS[2],86400); return stock-1",Long.class);}
}
