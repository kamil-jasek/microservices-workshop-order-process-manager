package com.mycompany.orderprocessmanager.infra.port.outbound.http.orders;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = OrdersRestApi.class)
class OrdersFeignClientConfig {
}
