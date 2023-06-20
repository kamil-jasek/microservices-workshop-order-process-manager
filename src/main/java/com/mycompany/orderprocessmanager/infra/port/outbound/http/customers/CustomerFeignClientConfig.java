package com.mycompany.orderprocessmanager.infra.port.outbound.http.customers;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = CustomerRestApi.class)
class CustomerFeignClientConfig {
}
