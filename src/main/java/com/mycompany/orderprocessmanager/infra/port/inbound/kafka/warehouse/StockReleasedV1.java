package com.mycompany.orderprocessmanager.infra.port.inbound.kafka.warehouse;

import com.mycompany.application.event.DomainEvent;
import com.mycompany.application.json.JsonSubtype;
import lombok.NonNull;

import java.time.Instant;
import java.util.UUID;

@JsonSubtype
final class StockReleasedV1 extends DomainEvent<StockReleasedV1.StockReleased> {

    StockReleasedV1(@NonNull UUID eventId,
                    @NonNull Instant eventTime,
                    UUID correlationId,
                    @NonNull StockReleased data) {
        super(eventId, eventTime, correlationId, data);
    }

    record StockReleased(UUID waybillId, UUID orderId) implements DomainEventData {
    }
}
