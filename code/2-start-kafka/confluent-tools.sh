#!/bin/bash

# Two options to get access to the confluent tools
# HomeBrew doesn't work for this one. Installs, but errors.

# Download the confluent binaries at:
# https://www.confluent.io/download/
# Option 1:
# Put them on your system and put the confluent/bin directory in your path
kafka-avro-console-consumer.sh

# Option 2:
# Use a docker image to have access to all the binaries right away:

# docker run -it --rm --net=host confluentinc/cp-schema-registry:3.3.1 bash
docker run -it --rm --net=host confluentinc/cp-schema-registry:5.3.1 bash
# Then you can do
kafka-avro-console-consumer
