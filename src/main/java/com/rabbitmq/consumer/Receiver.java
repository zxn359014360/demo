package com.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.sound.midi.Soundbank;

@Component
public class Receiver {
    /** Direct模式**/

    @RabbitListener(queues = "queue")
    public void process1(String msg){
        System.out.println("Receiver1:"+msg);
    }

    @RabbitListener(queues = "queue")
    public void process2(String msg){
        System.out.println("Receiver2:"+msg);
    }



    /** topic 模式**/

    @RabbitListener(queues = "topic.message")
    public void topProcess1(String msg){
        System.out.println("TopicReceiver1:"+msg);
    }

    @RabbitListener(queues = "topic.messages")
    public void topicProcess2(String msg){
        System.out.println("TopicReceiver2:"+msg);
    }

    /** fanout 广播模式**/
    @RabbitListener(queues = "fanout.A")
    public void processA(String msg){
        System.out.println("FanoutReceiverA:"+msg);
    }

    @RabbitListener(queues = "fanout.B")
    public void processB(String msg){
        System.out.println("FanoutReceiverB:"+msg);
    }

    @RabbitListener(queues = "fanout.C")
    public void processC(String msg){
        System.out.println("FanoutReceiverC:"+msg);
    }

}
