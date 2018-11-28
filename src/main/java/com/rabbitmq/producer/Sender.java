package com.rabbitmq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;


    public void sendQueue(String msg){
        System.out.println("QueueSender1:"+msg);
        rabbitTemplate.convertAndSend("queue",msg);
    }

    public void sendTopic1(String msg){
        System.out.println("TopicSender1:"+msg);
        rabbitTemplate.convertAndSend("exchange","topic.message",msg);
    }

    public void sendTopic2(String msg){
        System.out.println("TopicSender2:"+msg);
        rabbitTemplate.convertAndSend("exchange","topic.messages",msg);
    }

    public void sendFanout(String msg){
        System.out.println("FanoutSender:"+msg);
        rabbitTemplate.convertAndSend("fanoutExchange","",msg);
    }



}
