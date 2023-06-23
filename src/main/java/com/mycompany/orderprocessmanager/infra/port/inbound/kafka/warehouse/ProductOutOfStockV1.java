package com.mycompany.orderprocessmanager.infra.port.inbound.kafka.warehouse;

import com.mycompany.application.event.DomainEvent;
import com.mycompany.application.json.JsonSubtype;
import lombok.NonNull;

import java.time.Instant;
import java.util.UUID;

import static com.mycompany.orderprocessmanager.infra.port.inbound.kafka.warehouse.ProductOutOfStockV1.ProductOutOfStockData;

@JsonSubtype
final class ProductOutOfStockV1 extends DomainEvent<ProductOutOfStockData> {

    ProductOutOfStockV1(@NonNull UUID eventId,
                        @NonNull Instant eventTime,
                        UUID correlationId,
                        @NonNull ProductOutOfStockData data) {
        super(eventId, eventTime, correlationId, data);
    }

    record ProductOutOfStockData(UUID waybillId, UUID productId) implements DomainEventData {
    }
}
