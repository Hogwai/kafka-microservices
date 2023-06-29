# kafka-microservices

The project is composed of:
-  A docker-compose running: 
    - Apache Zookeeper
    - Apache Kafka
    - [Kafdrop](https://github.com/obsidiandynamics/kafdrop)

- 3 microservices:
    - producer
    - consumer-producer
    - consumer

## Prerequisites
- Docker
- JDK 17 and above
- Maven

## Description
### Producer
The entrypoint is the producer. It has an endpoint available for orders creation.

#### Payload
Here is an example of the required payload:
```
curl --location 'http://localhost:8080/order' \
--header 'Content-Type: application/json' \
--data '{
    "item": "pizza",
    "amount": "15",
    "vat": 19.6
}'
```

### Consumer-producer
This microservice listens to events emitted on the producer topic.
Its purpose is to apply VAT on food orders, then to emit the updated order on the consumer-producer topic.

### Consumer
This microservice listens to events emitted on the consumer-producer topic.
Its purpose is to save food orders in database.


## Installation
Clone the project on your local machine.

Launch Zookeeper, Kafka and Kafdrop:
```
docker compose up
```

Launch the microservices:
```
mvn spring-boot:run
```