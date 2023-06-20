package com.mycompany.orderprocessmanager.infra.port.inbound.kafka.orders;

import com.mycompany.application.event.DomainEvent;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

record OrderDataV1(
    @NonNull UUID id,
    @NonNull UUID customerId,
    @NonNull Instant createTime,
    @NonNull String status,
    @NonNull String currency,
    @NonNull List<OrderMadeItem> orderItems,
    @NonNull BigDecimal discount,
    @NonNull BigDecimal deliveryCost,
    Instant sentTime,
    Instant cancelTime,
    String cancelReason
) implements DomainEvent.DomainEventData {

    public record OrderMadeItem(
        @NonNull UUID productId,
        @NonNull BigDecimal originalPrice,
        @NonNull String originalCurrency,
        @NonNull BigDecimal exchangedPrice,
        double weight,
        @NonNull String weightUnit,
        int quantity
    ) {
    }
}
