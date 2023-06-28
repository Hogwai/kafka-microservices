package com.hogwai.kafka.consumer.service;

import com.hogwai.kafka.consumer.model.FoodOrder;
import com.hogwai.kafka.consumer.repository.FoodOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FoodOrderService {

    private final FoodOrderRepository foodOrderRepository;

    @Autowired
    public FoodOrderService(FoodOrderRepository foodOrderRepository) {
        this.foodOrderRepository = foodOrderRepository;
    }

    public void persistFoodOrder(FoodOrder foodOrder) {
        FoodOrder persistedFoodOrder = foodOrderRepository.save(foodOrder);
        log.info("The food order has been persisted: {}", persistedFoodOrder);
    }

}
