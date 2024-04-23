package com.me.budgetbackend.messageQueue;

import com.me.budgetbackend.entity.ChatMessage;
import com.me.budgetbackend.entity.ChatMessageDAO;
import com.me.budgetbackend.entity.DBInstructor;
import com.me.budgetbackend.mapper.ChatMessageMapper;
import com.me.budgetbackend.utils.SerializationUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {
    @Autowired
    private ChatMessageMapper chatMessageMapper;
    @RabbitListener(queues = "queue_db")
    public void processDBMessage(byte[] dbInstructor) {
        DBInstructor<ChatMessage> dbInstructor1 = SerializationUtils.deserialize(dbInstructor);
        String tableName = dbInstructor1.getDbName();
        String operation = dbInstructor1.getOperation();
        ChatMessageDAO chatMessageDAO = new ChatMessageDAO(dbInstructor1.getContent());
        if (operation.equals("insert")) {
            chatMessageMapper.insert(chatMessageDAO);
        }
    }
}
