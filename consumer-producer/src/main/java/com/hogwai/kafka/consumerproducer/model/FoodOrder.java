package com.hogwai.kafka.consumerproducer.model;

import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class FoodOrder {
    String item;
    Double amount;
    Double vat;
}
