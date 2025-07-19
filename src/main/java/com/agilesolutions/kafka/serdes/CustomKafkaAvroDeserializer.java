package com.agilesolutions.kafka.serdes;

import com.agilesolutions.kafka.model.Share;
import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;

@Slf4j
public class CustomKafkaAvroDeserializer extends KafkaAvroDeserializer {
    @Override
    public Object deserialize(String topic, byte[] bytes) {
        this.schemaRegistry = getMockClient(Share.SCHEMA$);

        try {
            return super.deserialize(topic, bytes);
        } catch (Exception e) {
            log.error("Error deserializing message from topic {} - message content is: '{}'. " , topic, new String(bytes));
            return null;

        }
    }

    private static SchemaRegistryClient getMockClient(final Schema schema$) {
        return new MockSchemaRegistryClient() {
            @Override
            public synchronized Schema getById(int id) {
                return schema$;
            }
        };
    }
}