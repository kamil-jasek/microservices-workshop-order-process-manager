package com.mycompany.orderprocessmanager.infra.port.inbound.kafka.orders;

import com.mycompany.orderprocessmanager.infra.port.outbound.http.customers.CustomerRestApi;
import com.mycompany.orderprocessmanager.infra.port.outbound.http.warehouse.WarehouseRestApi;
import com.mycompany.orderprocessmanager.infra.port.outbound.mail.MailPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
class KafkaOrdersConfig {

    @Bean
    KafkaOrdersListener kafkaOrdersListener(WarehouseRestApi warehouseRestApi,
                                            CustomerRestApi customerRestApi,
                                            MailPort mailPort) {
        return new KafkaOrdersListener(warehouseRestApi, customerRestApi, mailPort);
    }
}
