package com.davidlday.udemy.kafka.avro.generic;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.*;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

import java.io.File;
import java.io.IOException;

public class GenericRecordExample {

  public static void main(String[] args) {
    // 0: Define Avro schema
    Schema.Parser parser = new Schema.Parser();
    Schema schema = parser.parse("{\n" +
      "     \"type\": \"record\",\n" +
      "     \"namespace\": \"com.example\",\n" +
      "     \"name\": \"Customer\",\n" +
      "     \"doc\": \"Avro Schema for our Customer\",     \n" +
      "     \"fields\": [\n" +
      "       { \"name\": \"first_name\", \"type\": \"string\", \"doc\": \"First Name of Customer\" },\n" +
      "       { \"name\": \"last_name\", \"type\": \"string\", \"doc\": \"Last Name of Customer\" },\n" +
      "       { \"name\": \"age\", \"type\": \"int\", \"doc\": \"Age at the time of registration\" },\n" +
      "       { \"name\": \"height\", \"type\": \"float\", \"doc\": \"Height at the time of registration in cm\" },\n" +
      "       { \"name\": \"weight\", \"type\": \"float\", \"doc\": \"Weight at the time of registration in kg\" },\n" +
      "       { \"name\": \"automated_email\", \"type\": \"boolean\", \"default\": true, \"doc\": \"Field indicating if the user is enrolled in marketing emails\" }\n" +
      "     ]\n" +
      "}");

    // 1: Create a generic record
    GenericRecordBuilder customerBuilder = new GenericRecordBuilder(schema);
    customerBuilder.set("first_name", "David");
    customerBuilder.set("last_name", "Day");
    customerBuilder.set("age", 25);
    customerBuilder.set("height", 170f);
    customerBuilder.set("weight", 80.5f);
    customerBuilder.set("automated_email", false);
    GenericData.Record customer = customerBuilder.build();

    System.out.println(customer);

    GenericRecordBuilder customerBuilderWithDefault = new GenericRecordBuilder(schema);
    customerBuilderWithDefault.set("first_name", "David");
    customerBuilderWithDefault.set("last_name", "Day");
    customerBuilderWithDefault.set("age", 25);
    customerBuilderWithDefault.set("height", 170f);
    customerBuilderWithDefault.set("weight", 80.5f);
    GenericData.Record customerWithDefault = customerBuilderWithDefault.build();

    System.out.println(customerWithDefault);

    // 2: Write the generic record to a file
    final DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
    try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter)) {
      dataFileWriter.create(customer.getSchema(), new File("customer-generic.avro"));
      dataFileWriter.append(customer);
      dataFileWriter.append(customerWithDefault);
      System.out.println("Written customer-generic.avro");
    } catch (IOException e) {
      System.out.println("Couldn't write file");
      e.printStackTrace();
    }

    // 3: Read a generic record from a file
    final File file = new File("customer-generic.avro");
    final DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();
    try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader)){
      dataFileReader.forEach((customerRead) -> {
        System.out.println("Successfully read avro file");
        System.out.println(customerRead.toString());

        // get the data from the generic record
        System.out.println("First name: " + customerRead.get("first_name"));

        // read a non existent field
        System.out.println("Non existent field: " + customerRead.get("not_here"));
      });
    }
    catch(IOException e) {
      e.printStackTrace();
    }

    // 4: Interpret as a generic record
  }

}
