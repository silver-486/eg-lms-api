#!/bin/sh
mvn clean install -DskipTests=true
java -jar ./target/eg-lms-api-0.0.1-SNAPSHOT.jar
