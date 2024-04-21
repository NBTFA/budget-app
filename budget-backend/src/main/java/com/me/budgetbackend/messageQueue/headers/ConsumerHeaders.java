package com.me.budgetbackend.messageQueue.headers;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

public class ConsumerHeaders {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //创建链接
        Connection connection = connectionFactory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();

        String queueName = "testQueue_fanout_1";

        DeliverCallback deliverCallback = (consumerTage, message) -> {
            System.out.println("接收到消息"+new String(message.getBody()));
        };

        CancelCallback cancelCallback = consumerTage -> {
            System.out.println("消费被中断");
        };

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("x-match", "all");
        headerMap.put("name", "sb");
        headerMap.put("gender", "male");


        /**
         * 绑定交换机和队列
         * 1. 队列名称
         * 2. 交换机名称
         * 3. routing key, 直连模式下是队列名称
         */
        channel.queueBind("testQueue_headers_1", "testExchange_headers", "", headerMap);

        /**
         * 消费消息
         * 1. 消费队列
         * 2. 消费成功后是否自动应答, true: 自动应答
         * 3. 接收消息的回调函数
         * 4. 取消消息的回调
         */
        channel.basicConsume("testQueue_headers_1", true, deliverCallback, cancelCallback);
    }
}
