package com.mycompany.orderprocessmanager.infra.port.outbound.mail;

import java.util.List;

final class DummyMailPortAdapter implements MailPort {
    @Override
    public void send(String subject, String body, List<String> recipients) {
        // send email via mail-microservice
    }
}
