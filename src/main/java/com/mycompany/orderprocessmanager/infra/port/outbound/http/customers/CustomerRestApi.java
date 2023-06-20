package com.mycompany.orderprocessmanager.infra.port.outbound.http.customers;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "customers", configuration = CustomerRestApiConfig.class)
public interface CustomerRestApi {

    record CustomerDto(UUID id, DeliveryAddress deliveryAddress) {
    }

    record DeliveryAddress(String name, String address) {
    }

    @GetMapping(value = "/api/customers/{id}", headers = {"version=1.0.0"})
    CustomerDto findById(@PathVariable UUID id);
}
