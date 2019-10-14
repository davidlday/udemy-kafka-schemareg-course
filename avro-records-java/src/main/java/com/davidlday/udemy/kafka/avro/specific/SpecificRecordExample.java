package com.davidlday.udemy.kafka.avro.specific;

import com.example.Customer;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;
import java.io.IOException;

public class SpecificRecordExample {

  public static void main(String[] args) {

    // 1: create specific record
    Customer.Builder customerBuilder = Customer.newBuilder();
    customerBuilder.setAge(25);
    customerBuilder.setFirstName("David");
    customerBuilder.setLastName("Day");
    customerBuilder.setHeight(170f);
    customerBuilder.setWeight(80.5f);
    customerBuilder.setAutomatedEmail(false);
    Customer customer = customerBuilder.build();

    System.out.println(customer);

    Customer.Builder customerBuilderWithDefault = Customer.newBuilder();
    customerBuilderWithDefault.setAge(25);
    customerBuilderWithDefault.setFirstName("David");
    customerBuilderWithDefault.setLastName("Day");
    customerBuilderWithDefault.setHeight(170f);
    customerBuilderWithDefault.setWeight(80.5f);
    Customer customerWithDefault = customerBuilder.build();

    System.out.println(customerWithDefault);

    // 2: write to file
    final DatumWriter<Customer> datumWriter = new SpecificDatumWriter<>(Customer.class);
    try (DataFileWriter<Customer> dataFileWriter = new DataFileWriter<>(datumWriter)) {
      dataFileWriter.create(customer.getSchema(), new File("customer-specific.avro"));
      dataFileWriter.append(customer);
      dataFileWriter.append(customerWithDefault);
      System.out.println("Written customer-specific.avro");
    } catch (IOException e) {
      System.out.println("Couldn't write file");
      e.printStackTrace();
    }

    // 3: read from file
    final File file = new File("customer-generic.avro");
    final DatumReader<Customer> datumReader = new SpecificDatumReader<>(Customer.class);
    try (DataFileReader<Customer> dataFileReader = new DataFileReader<>(file, datumReader)){
      dataFileReader.forEach((customerRead) -> {
        System.out.println("Successfully read avro file");
        System.out.println(customerRead.toString());

        // get the data from the generic record
        System.out.println("First name: " + customerRead.getFirstName());
        System.out.println("Automated Email: " + customerRead.getAutomatedEmail());
      });
    }
    catch(IOException e) {
      e.printStackTrace();
    }

    // 4: interpret
  }

}
