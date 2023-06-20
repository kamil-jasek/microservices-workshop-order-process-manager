package com.mycompany.orderprocessmanager.infra.port.inbound.kafka.warehouse;

import com.mycompany.orderprocessmanager.infra.port.outbound.http.orders.OrdersRestApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
class KafkaWarehouseConfig {

    @Bean
    KafkaWarehouseListener kafkaWarehouseListener(OrdersRestApi ordersRestApi) {
        return new KafkaWarehouseListener(ordersRestApi);
    }
}
