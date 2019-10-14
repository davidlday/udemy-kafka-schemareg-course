#!/bin/bash
# use HomeBrew instead

# put this in any directory you like
# wget http://central.maven.org/maven2/org/apache/avro/avro-tools/1.8.2/avro-tools-1.8.2.jar
brew install avro-tools

# run this from our project folder. Make sure ~/avro-tools-1.8.2.jar is your actual avro tools location
# getting the schema
# java -jar ~/avro-tools-1.8.2.jar tojson --pretty customer-generic.avro
avro-tools tojson --pretty customer-generic.avro

# java -jar ~/avro-tools-1.8.2.jar tojson --pretty customer-specific.avro
avro-tools tojson --pretty customer-specific.avro

# java -jar ~/avro-tools-1.8.2.jar getschema customer-specific.avro
avro-tools getschema customer-specific.avro
