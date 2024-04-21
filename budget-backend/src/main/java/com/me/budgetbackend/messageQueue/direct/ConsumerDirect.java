package com.me.budgetbackend.messageQueue.direct;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConsumerDirect {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //创建链接
        Connection connection = connectionFactory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();

        String queueName = "testQueue_4";

        DeliverCallback deliverCallback = (consumerTage, message) -> {
            System.out.println("接收到消息"+new String(message.getBody()));
        };

        CancelCallback cancelCallback = consumerTage -> {
            System.out.println("消费被中断");
        };

        /**
         * 消费消息
         * 1. 消费队列
         * 2. 消费成功后是否自动应答, true: 自动应答
         * 3. 接收消息的回调函数
         * 4. 取消消息的回调
         */
        channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
    }
}
