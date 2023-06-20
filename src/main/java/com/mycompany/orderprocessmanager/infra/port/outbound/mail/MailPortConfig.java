package com.mycompany.orderprocessmanager.infra.port.outbound.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MailPortConfig {

    @Bean
    MailPort mailPort() {
        return new DummyMailPortAdapter();
    }
}
