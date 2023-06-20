package com.mycompany.orderprocessmanager.infra.port.outbound.http.orders;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "orders", configuration = OrdersRestApiConfig.class)
public interface OrdersRestApi {

    @PutMapping("/v1/orders/{id}/send")
    void sendOrder(@PathVariable UUID id);

    @PutMapping("/v1/orders/{id}/cancel")
    void cancelOrder(@PathVariable UUID id, @RequestBody OrderCancelReason reason);

    record OrderCancelReason(String reason) {
    }
}
