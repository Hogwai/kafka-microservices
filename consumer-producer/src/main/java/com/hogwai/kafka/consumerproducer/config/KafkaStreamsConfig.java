package com.hogwai.kafka.consumerproducer.config;

import com.hogwai.kafka.consumerproducer.service.FoodOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;

import java.util.Properties;

@Slf4j
@Configuration
@EnableKafkaStreams
public class KafkaStreamsConfig {

    @Value("${topic.name}")
    private String orderTopic;
    @Value("${topic.vat.name}")
    private String vatTopic;
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.streams.application-id}")
    private String applicationId;

    private final FoodOrderService foodOrderService;

    @Autowired
    public KafkaStreamsConfig(FoodOrderService vatService) {
        this.foodOrderService = vatService;
    }

    @Bean
    public KafkaStreams kafkaStreams(StreamsBuilder streamsBuilder) {
        Properties props = new Properties();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId);

        StreamsConfig streamsConfig = new StreamsConfig(props);

        KStream<String, String> stream = streamsBuilder.stream(orderTopic,
                Consumed.with(Serdes.String(), Serdes.String()));

        stream = stream.mapValues(foodOrderService::processFoodOrder);
        stream.to(vatTopic, Produced.with(Serdes.String(), Serdes.String()));

        KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), streamsConfig);
        kafkaStreams.start();

        return kafkaStreams;
    }
}
