package com.mycompany.application.config;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.mycompany.application.event.DomainEvent;
import com.mycompany.application.json.JsonSubtype;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;

import java.util.Set;

@Configuration
@EnableKafka
@Slf4j
class DomainEventKafkaContainerConfig {

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, DomainEvent<?>> domainEventKafkaContainerFactory(
        KafkaProperties properties,
        KafkaTemplate<String, DomainEvent<?>> kafkaTemplate) {
        final var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, DomainEvent<?>>();
        final var deserializer = new KafkaJsonSchemaDeserializer<>();
        final var consumerFactory = new DefaultKafkaConsumerFactory<>(
            properties.buildConsumerProperties(),
            new StringDeserializer(),
            deserializer);
        registerObjectMapperSubtypes(deserializer);
        containerFactory.setCommonErrorHandler(
            new DefaultErrorHandler(new DeadLetterPublishingRecoverer(kafkaTemplate)));
        containerFactory.setConsumerFactory(consumerFactory);
        return containerFactory;
    }

    void registerObjectMapperSubtypes(KafkaJsonSchemaDeserializer<?> deserializer) {
        final var reflections = new Reflections(new ConfigurationBuilder()
            .forPackages("com.mycompany"));
        final var subtypes = reflections.getTypesAnnotatedWith(JsonSubtype.class);
        if (subtypes.isEmpty()) {
            throw new IllegalStateException("no subtypes found");
        }
        subtypes
            .stream()
            .map(clazz -> new NamedType(clazz, clazz.getSimpleName()))
            .forEach(deserializer.objectMapper()::registerSubtypes);
    }
}
