package com.hogwai.kafka.consumerproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hogwai.kafka.consumerproducer.dto.FoodOrderDto;
import com.hogwai.kafka.consumerproducer.model.FoodOrder;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FoodOrderService {

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public FoodOrderService(ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    public FoodOrder applyVATOnFoodOrder(FoodOrder foodOrder) {
        log.info("VAT to be applied on food order: {}", foodOrder);
        Double vat = 1 + (foodOrder.getVat() / 100);
        foodOrder.setAmount(foodOrder.getAmount() * vat);
        log.info("VAT applied on food order: {}", foodOrder);
        return foodOrder;
    }

    public String processFoodOrder(String message) {
        try {
            FoodOrderDto foodOrderDto = objectMapper.readValue(message, FoodOrderDto.class);
            FoodOrder foodOrder = modelMapper.map(foodOrderDto, FoodOrder.class);
            FoodOrderDto orderDtoWithVAT = modelMapper.map(applyVATOnFoodOrder(foodOrder), FoodOrderDto.class);
            return objectMapper.writeValueAsString(orderDtoWithVAT);
        } catch (JsonProcessingException e) {
            log.error("An error occurred while consuming a stream: {}", e.getMessage());
        }
        return message;
    }
}