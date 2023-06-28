package com.hogwai.kafka.consumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hogwai.kafka.consumer.dto.FoodOrderDto;
import com.hogwai.kafka.consumer.model.FoodOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Slf4j
@Component
public class Consumer {

    private static final String ORDER_TOPIC = "${topic.name}";

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final FoodOrderService foodOrderService;

    @Autowired
    public Consumer(ObjectMapper objectMapper, ModelMapper modelMapper, FoodOrderService foodOrderService) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.foodOrderService = foodOrderService;
    }

    @KafkaListener(topics = ORDER_TOPIC)
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("Message consumed: {}", message);

        FoodOrderDto foodOrderDto = objectMapper.readValue(message, FoodOrderDto.class);
        FoodOrder foodOrder = modelMapper.map(foodOrderDto, FoodOrder.class);

        foodOrderService.persistFoodOrder(foodOrder);
    }

}
