package com.example;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaAvroConsumerV2 {
  public static void main(String[] args) {
    Properties config = new Properties();
    config.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    config.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "my-avro-consumer-group2");
    config.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
    config.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    config.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    config.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
    config.setProperty("schema.registry.url", "http://localhost:8081");
    config.setProperty("specific.avro.reader", "true");

    KafkaConsumer<String, Customer> consumer = new KafkaConsumer<String, Customer>(config);
    String topic = "customer-avro";

    consumer.subscribe(Collections.singleton(topic));

    System.out.println("Waiting for data!");
    Duration timeout = Duration.ofMillis(500);

    while (true) {
      ConsumerRecords<String, Customer> records = consumer.poll(timeout);
      records.forEach((record) -> {
        Customer customer = record.value();
        System.out.println(customer);
      });
      consumer.commitSync();
    }

//    consumer.close();
  }
}
