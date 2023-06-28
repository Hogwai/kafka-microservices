package com.hogwai.kafka.producer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hogwai.kafka.producer.model.FoodOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FoodOrderService {

    private final Producer producer;

    @Autowired
    public FoodOrderService(Producer producer) {
        this.producer = producer;
    }

    public String createFoodOrder(FoodOrder foodOrder) throws JsonProcessingException {
        return producer.sendMessage(foodOrder);
    }
}