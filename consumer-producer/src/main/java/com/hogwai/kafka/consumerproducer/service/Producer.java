package com.hogwai.kafka.consumerproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hogwai.kafka.consumerproducer.dto.FoodOrderDto;
import com.hogwai.kafka.consumerproducer.model.FoodOrder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Producer {

    @Value("${topic.vat.name}")
    private String vatTopic;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public Producer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(FoodOrderDto foodOrderDto) throws JsonProcessingException {
        String orderAsMessage = objectMapper.writeValueAsString(foodOrderDto);
        kafkaTemplate.send(vatTopic, orderAsMessage);

        log.info("The food order with VAT has been produced: {}", orderAsMessage);
    }
}