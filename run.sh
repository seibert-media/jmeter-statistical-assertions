#!/usr/bin/env bash

set -e

atlas-mvn clean compile assembly:single

jar_path=$(ls target/jmeter-statistical-assertions-*-jar-with-dependencies.jar)

java -jar ${jar_path} report test-data/aggregate.csv
