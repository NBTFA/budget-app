package com.me.budgetbackend.messageQueue.headers;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class ProducerHeaders {
    public static void main(String[] args) throws IOException, TimeoutException {
        //创建链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //创建链接
        Connection connection = connectionFactory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();

        String queueName_1 = "testQueue_headers_1";
        String queueName_2 = "testQueue_topic_2";
        String queueName_3 = "testQueue_topic_3";
        String queueName_4 = "testQueue_topic_4";

        String key_1 = "key1.key2.key3.*";
        String key_2 = "key1.#";
        String key_3 = "*.key2.*.key4";
        String key_4 = "#.key3.key4";

        String exchangeName = "testExchange_headers";


        /**
         * 创建交换机
         * 1. 交换机名称
         * 2. 交换机类型
         * 3. 制定交换机是否持久化, 如果true, 那么交换机的元数据要持久化
         * 4. 指定交换机在没有消息队列的时候, 是否需要删除, false为不删除
         * 5. Map<String, Object>类型, 指定交换机其他的一些结构化参数
         */
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.HEADERS, true, false, null);

        /**
         * 生成队列
         * 1. 队列名
         * 2. 队列是否需要持久化, 不是队列消息的持久化, 是队列名称等元数据的持久化
         * 3. 队列是否私有化, 如果私有, 只有创建它的应用程序才能消费消息
         * 4. 队列没有消费者订阅的情况下, 是否自动删除
         * 5. 队列结构化信息, 比如声明死信队列, 磁盘队列会用到
         */
        channel.queueDeclare(queueName_1, true, false, false, null);

        /**
         * 绑定交换机和队列
         * 1. 队列名称
         * 2. 交换机名称
         * 3. routing key, 直连模式下是队列名称
         */
//        channel.queueBind(queueName_1, exchangeName, key_1);

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("x-match", "all");
        headerMap.put("name", "sb");
        headerMap.put("gender", "male");
        AMQP.BasicProperties.Builder properties = new AMQP.BasicProperties().builder().headers(headerMap);
//        发送消息
        String message = "key1_header_message";
        /**
         * 发送消息
         * 1. 交换机名称
         * 2. routing key
         * 3. 其他参数信息
         * 4. 发送消息的消息体(字节)
         */
        channel.basicPublish(exchangeName, "", properties.build(), message.getBytes());

        channel.close();
        connection.close();
    }
}
