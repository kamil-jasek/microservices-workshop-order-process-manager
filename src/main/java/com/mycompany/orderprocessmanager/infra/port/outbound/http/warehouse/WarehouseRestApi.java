package com.mycompany.orderprocessmanager.infra.port.outbound.http.warehouse;

import lombok.NonNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "warehouse", configuration = WarehouseRestApiConfig.class)
public interface WarehouseRestApi {

    @PostMapping("/v1/stock-release-orders")
    void createStockReleaseOrder(@RequestBody StockReleaseOrder stockReleaseOrder);

    record StockReleaseOrder(@NonNull UUID waybillId,
                             @NonNull UUID orderId,
                             @NonNull DeliveryAddress deliveryAddress,
                             @NonNull List<StockReleaseOrderItem> items) {
    }

    record DeliveryAddress(
        @NonNull String name,
        @NonNull String address) {
    }

    record StockReleaseOrderItem(
        @NonNull UUID productId,
        int quantity) {
    }
}
