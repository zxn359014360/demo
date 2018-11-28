package com.rabbitmq.bean;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FantoutBeanConfig {

    @Bean(name = "AMessage")
    public Queue Amessage(){
        return new Queue("fanout.A");
    }

    @Bean(name = "BMessage")
    public Queue Bmessage(){
        return new Queue("fanout.B");
    }

    @Bean(name="CMessage")
    public Queue CMessage() {
        return new Queue("fanout.C");
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchangeA(@Qualifier("AMessage")Queue amessage, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(amessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(@Qualifier("BMessage")Queue bmessage, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(bmessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(@Qualifier("CMessage")Queue cmessage, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(cmessage).to(fanoutExchange);
    }
}
