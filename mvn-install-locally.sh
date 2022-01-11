#!/usr/bin/env bash

JAR_PATH="target/spring-clean-logging-0.0.1.jar"
ARTIFACT="spring-clean-logging"
VERSION="0.0.1"

mvn versions:set -DnewVersion=$VERSION
mvn clean
mvn package
mvn install:install-file \
              -Dfile=$JAR_PATH \
              -DgroupId=pl.k4mil \
              -DartifactId=$ARTIFACT \
              -Dversion=$VERSION \
              -Dpackaging=jar
mvn versions:revert

printf "\n\nDone."