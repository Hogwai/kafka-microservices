package com.hogwai.kafka.consumerproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hogwai.kafka.consumerproducer.dto.FoodOrderDto;
import com.hogwai.kafka.consumerproducer.model.FoodOrder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    private static final String ORDER_TOPIC = "${topic.name}";

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final VatService vatService;
    private final Producer producer;

    @Autowired
    public Consumer(ObjectMapper objectMapper, ModelMapper modelMapper, VatService vatService, Producer producer) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.vatService = vatService;
        this.producer = producer;
    }

    @KafkaListener(topics = ORDER_TOPIC)
    public void consumeAndProcess(String message) throws JsonProcessingException {
        FoodOrderDto foodOrderDto = objectMapper.readValue(message, FoodOrderDto.class);
        FoodOrder foodOrder = modelMapper.map(foodOrderDto, FoodOrder.class);

        log.info("VAT to be applied on the order: {}", foodOrder);
        FoodOrder orderWithVAT = vatService.applyVATOnFoodOrder(foodOrder);
        log.info("VAT applied on the order: {}", orderWithVAT);

        FoodOrderDto orderDtoWithVAT = modelMapper.map(orderWithVAT, FoodOrderDto.class);
        producer.sendMessage(orderDtoWithVAT);
    }
}
