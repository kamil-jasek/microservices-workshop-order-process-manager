package com.mycompany.orderprocessmanager.infra.port.inbound.kafka.warehouse;

import com.mycompany.orderprocessmanager.infra.port.outbound.http.orders.OrdersRestApi;
import com.mycompany.orderprocessmanager.infra.port.outbound.http.orders.OrdersRestApi.OrderCancelReason;
import com.mycompany.orderprocessmanager.infra.port.outbound.http.orders.OrdersRestApi.OrderShipmentId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;

@RequiredArgsConstructor
final class KafkaWarehouseListener {

    private final @NonNull OrdersRestApi ordersRestApi;

    @SneakyThrows
    @KafkaListener(
        id = "order-process-manager-stock-released-listener",
        topics = "warehouse-stock-released-v1",
        containerFactory = "domainEventKafkaContainerFactory"
    )
    void handleStockReleased(StockReleasedV1 event) {
        ordersRestApi.sendOrder(event.getData().waybillId(),
            new OrderShipmentId(event.getData().shipmentId()));
    }

    @SneakyThrows
    @KafkaListener(
        id = "order-process-manager-product-out-of-stock-listener",
        topics = "warehouse-product-out-of-stock-v1",
        containerFactory = "domainEventKafkaContainerFactory"
    )
    void handleProductOutOfStock(ProductOutOfStockV1 event) {
        ordersRestApi.cancelOrder(
            event.getData().waybillId(),
            new OrderCancelReason("product out of stock: " + event.getData().productId()));
    }
}
