# TrumpBot

## Prerequisites
* SBT
* IntelliJ
* IntelliJ Scala plugin (via IntelliJ plugin installation interface)
* JDK 8

## Installation

Before running please run the scripts:

    > ./docker/install_sentiment_docker.sh

## Running

    > cd ./docker
    > docker-compose up -d
    > cd ..
    > sbt run

## Loading project in IntelliJ for the first time

1. Click file -> new project from existing sources
2. Check SBT
3. Choose the TrumpBot repository root project directory
4. Check all boxes and click continue
5. Make sure it is using Java 8 and finish