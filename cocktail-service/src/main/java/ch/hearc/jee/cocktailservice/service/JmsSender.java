package ch.hearc.jee.cocktailservice.service;

import ch.hearc.jee.cocktailservice.resource.JmsMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsSender {

    @Autowired
    private ObjectMapper objectMapper;

    private final JmsTemplate jmsTemplate;

    public JmsSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String destination, JmsMessage jmsMessage) throws JsonProcessingException {
        jmsTemplate.convertAndSend(destination, objectMapper.writeValueAsString(jmsMessage));
    }
}