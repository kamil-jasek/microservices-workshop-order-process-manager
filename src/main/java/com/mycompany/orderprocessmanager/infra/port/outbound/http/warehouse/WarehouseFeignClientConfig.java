package com.mycompany.orderprocessmanager.infra.port.outbound.http.warehouse;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(clients = WarehouseRestApi.class)
class WarehouseFeignClientConfig {
}
