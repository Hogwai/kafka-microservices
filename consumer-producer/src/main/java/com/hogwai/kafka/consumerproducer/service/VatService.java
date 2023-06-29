package com.hogwai.kafka.consumerproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hogwai.kafka.consumerproducer.model.FoodOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class VatService {
    public FoodOrder applyVATOnFoodOrder(FoodOrder foodOrder) {
        Double vat = 1 + (foodOrder.getVat() / 100);
        foodOrder.setAmount(foodOrder.getAmount() * vat);
        return foodOrder;
    }
}