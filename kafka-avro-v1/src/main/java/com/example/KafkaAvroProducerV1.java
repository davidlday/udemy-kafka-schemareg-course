package com.example;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaAvroProducerV1 {

  public static void main(String[] args) {
    Properties config = new Properties();
    config.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    config.setProperty(ProducerConfig.ACKS_CONFIG, "1");
    config.setProperty(ProducerConfig.RETRIES_CONFIG, "10");

    config.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    config.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class.getName());
    config.setProperty("schema.registry.url", "http://localhost:8081");

    KafkaProducer<String, Customer> kafkaProducer = new KafkaProducer<>(config);
    String topic = "customer-avro";

    Customer customer = Customer.newBuilder()
      .setFirstName("David")
      .setLastName("Day")
      .setAge(26)
      .setHeight(185.5f)
      .setWeight(85.6f)
      .setAutomatedEmail(false)
      .build();

    ProducerRecord<String, Customer> producerRecord =
      new ProducerRecord<>(topic, customer);

    System.out.println(customer.getSchema().toString(true));

    // Getting this error:
    //  Exception in thread "main" org.apache.kafka.common.errors.SerializationException: Error registering Avro schema: {"type":"record","name":"Customer","namespace":"com.example","fields":[{"name":"first_name","type":{"type":"string","avro.java.string":"String"},"doc":"First Name of Customer"},{"name":"last_name","type":{"type":"string","avro.java.string":"String"},"doc":"Last Name of Customer"},{"name":"age","type":"int","doc":"Age at the time of registration"},{"name":"height","type":"float","doc":"Height at the time of registration in cm"},{"name":"weight","type":"float","doc":"Weight at the time of registration in kg"},{"name":"automated_email","type":"boolean","doc":"Field indicating if the user is enrolled in marketing emails","default":true}],"version":"1"}
    //  Caused by: io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException: Unrecognized token 'Error': was expecting ('true', 'false' or 'null')

    kafkaProducer.send(producerRecord, (recordMetadata, e) -> {
      if (e == null) {
        System.out.println("Success!");
        System.out.println(recordMetadata.toString());
      } else {
        e.printStackTrace();
      }
    });

    kafkaProducer.flush();
    kafkaProducer.close();

  }

}
