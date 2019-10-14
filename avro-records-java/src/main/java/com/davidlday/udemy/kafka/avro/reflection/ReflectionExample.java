package com.davidlday.udemy.kafka.avro.reflection;

// Reflection is the LEAST recommended way of creating Avro objects.

import org.apache.avro.Schema;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

import java.io.File;
import java.io.IOException;

public class ReflectionExample {

  public static void main(String[] args) {
    // Use reflection to determine the schema
    Schema schema = ReflectData.get().getSchema(ReflectedCustomer.class);
    System.out.println("schema = " + schema.toString(true));

    // write to a file
    try {
      System.out.println("Writing customer-reflected.avro");
      File file = new File("customer-reflected.avro");
      DatumWriter<ReflectedCustomer> writer = new ReflectDatumWriter<>(ReflectedCustomer.class);
      DataFileWriter<ReflectedCustomer> out = new DataFileWriter<>(writer)
        .setCodec(CodecFactory.deflateCodec(9))
        .create(schema, file);

      out.append(new ReflectedCustomer("David", "Day", "D-Day"));
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // read from a file
    try {
      System.out.println("Reading customer-reflected.avro");
      File file = new File("customer-reflected.avro");
      DatumReader<ReflectedCustomer> reader = new ReflectDatumReader<>(ReflectedCustomer.class);
      DataFileReader<ReflectedCustomer> in = new DataFileReader<>(file, reader);

      // read ReflectedCustomers from the file & print them as JSON
      for (ReflectedCustomer reflectedCustomer : in) {
        System.out.println(reflectedCustomer.fullName());
      }
      // close the input file
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

}
