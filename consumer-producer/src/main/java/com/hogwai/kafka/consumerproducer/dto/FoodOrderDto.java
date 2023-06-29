package com.hogwai.kafka.consumerproducer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FoodOrderDto {
    String item;
    Double amount;
    Double vat;
}
