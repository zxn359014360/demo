package com.controller;

import com.rabbitmq.producer.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class RabbitMQController {

    @Autowired
    public Sender sender;

    /**
     * rabbitmq Direct模式
     */
    @RequestMapping("queue")
    public void queue(){
        String msg = "hello world";
        sender.sendQueue(msg);
        sender.sendQueue(msg);
    }

    /**
     * rabbitmq Topic转发模式
     */
    @RequestMapping("topic")
    public void topic(){
        String msg = "hello world";
        sender.sendTopic1(msg);
        sender.sendTopic2(msg);
    }

    /**
     * rabbitmq Fanout广播模式
     */
    @RequestMapping("fanout")
    public void fanout(){
        String msg = "hello world";
        sender.sendFanout(msg);
    }

}
