package ru.app.Jms_func;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class SampleJmsMessageSender {
    private JmsTemplate jmsTemplate;
    private Queue queue;
    @Value("${queue}")
    private String queueName;

    @Autowired
    ActiveMQConnectionFactory connectionFactory;

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setQueue(Queue queue){
        this.queue = queue;
    }

    public void simpleSend(String msg) {
        setQueue(new ActiveMQQueue(queueName));
        jmsTemplate.send(queue, s -> s.createTextMessage(msg));
    }
}
