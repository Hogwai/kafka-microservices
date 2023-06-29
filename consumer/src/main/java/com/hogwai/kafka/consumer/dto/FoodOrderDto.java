package com.hogwai.kafka.consumer.dto;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class FoodOrderDto {
    String item;
    Double amount;
    Double vat;

    public FoodOrderDto(String item, Double amount, Double vat) {
        this.item = item;
        this.amount = amount;
        this.vat = vat;
    }
}
