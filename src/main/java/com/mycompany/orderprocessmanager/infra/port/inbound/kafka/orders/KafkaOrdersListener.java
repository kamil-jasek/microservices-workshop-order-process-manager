package com.mycompany.orderprocessmanager.infra.port.inbound.kafka.orders;

import com.mycompany.orderprocessmanager.infra.port.outbound.http.customers.CustomerRestApi;
import com.mycompany.orderprocessmanager.infra.port.outbound.http.warehouse.WarehouseRestApi;
import com.mycompany.orderprocessmanager.infra.port.outbound.http.warehouse.WarehouseRestApi.StockReleaseOrder;
import com.mycompany.orderprocessmanager.infra.port.outbound.mail.MailPort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@RequiredArgsConstructor
class KafkaOrdersListener {

    private final @NonNull WarehouseRestApi warehouseRestApi;
    private final @NonNull CustomerRestApi customerRestApi;
    private final @NonNull MailPort mailPort;

    @SneakyThrows
    @KafkaListener(
        id = "order-process-manager-order-made-listener",
        topics = "orders-order-made-v1",
        containerFactory = "domainEventKafkaContainerFactory"
    )
    @Transactional
    void handleOrderMade(OrderMadeV1 event) {
        warehouseRestApi.createStockReleaseOrder(createStockReleaseOrder(event.getData()));
    }

    private StockReleaseOrder createStockReleaseOrder(OrderDataV1 order) {
        final var deliveryAddress = customerRestApi.findById(order.customerId()).deliveryAddress();
        return new StockReleaseOrder(
            order.id(),
            new WarehouseRestApi.DeliveryAddress(deliveryAddress.name(), deliveryAddress.address()),
            order.orderItems()
                .stream()
                .map(item -> new WarehouseRestApi.StockReleaseOrderItem(item.productId(), item.quantity()))
                .collect(toList())
        );
    }

    @SneakyThrows
    @KafkaListener(
        id = "order-process-manager-order-sent-listener",
        topics = "orders-order-sent-v1",
        containerFactory = "domainEventKafkaContainerFactory"
    )
    public void handleOrderSent(OrderSentV1 event) {
        log.info("sending email order sent id: {}", event.getData().id());
        mailPort.send("order sent", "body", List.of("recipient@test.pl"));
    }

    @SneakyThrows
    @KafkaListener(
        id = "order-process-manager-order-canceled-listener",
        topics = "orders-order-canceled-v1",
        containerFactory = "domainEventKafkaContainerFactory"
    )
    public void handleOrderCanceled(OrderCanceledV1 event) {
        log.info("sending email order canceled id: {}", event.getData().id());
        mailPort.send("order canceled", "body", List.of("recipient@test.pl"));
    }
}
