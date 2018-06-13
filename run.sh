#!/usr/bin/env bash

set -e

mvn clean compile assembly:single
java -jar target/jmeter-statistical-assertions-0.1.0-SNAPSHOT-jar-with-dependencies.jar report test-data/aggregate.csv
