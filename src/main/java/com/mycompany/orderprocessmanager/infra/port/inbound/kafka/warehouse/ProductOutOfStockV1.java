package com.mycompany.orderprocessmanager.infra.port.inbound.kafka.warehouse;

import com.mycompany.application.event.DomainEvent;
import com.mycompany.application.json.JsonSubtype;
import lombok.NonNull;

import java.time.Instant;
import java.util.UUID;

import static com.mycompany.orderprocessmanager.infra.port.inbound.kafka.warehouse.ProductOutOfStockV1.*;
import static java.time.Instant.now;
import static java.util.UUID.randomUUID;

@JsonSubtype
final class ProductOutOfStockV1 extends DomainEvent<ProductOutOfStockData> {

    ProductOutOfStockV1(@NonNull UUID eventId,
                        @NonNull Instant eventTime,
                        UUID correlationId,
                        @NonNull ProductOutOfStockData data) {
        super(eventId, eventTime, correlationId, data);
    }

    static ProductOutOfStockV1 of(ProductOutOfStockData data) {
        return new ProductOutOfStockV1(
            randomUUID(),
            now(),
            null,
            data);
    }

    record ProductOutOfStockData(UUID orderId, UUID productId) implements DomainEventData {
    }
}
