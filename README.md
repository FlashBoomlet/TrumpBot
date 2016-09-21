# TrumpBot

## Testers

### Running the chatbot

1. Graders of CS427: An email has been sent to your cs.unm.edu account. Please accespt
the slack chat group invite.

2. Once you are in the group, click on the "channels" to search channels and search
the "trumptower" channel.

3. Join "trumptower" channel

4. Begin talking with Donald Trump

5. To restart the conversation, simply type `$restart`

## Developers

### Prerequisites
* SBT
* JDK 8
* IntelliJ (developers)
* IntelliJ Scala plugin (developers) (via IntelliJ plugin installation interface)

### Installation

Before running please run the scripts:

    > ./docker/install_sentiment_docker.sh
    > docker pull mongo:latest

### Running

    > cd ./docker
    > docker-compose up -d
    > cd ..
    > sbt "project trumpBotCore" "run"

### Loading project in IntelliJ for the first time

1. Click file -> new project from existing sources
2. Check SBT
3. Choose the TrumpBot repository root project directory
4. Check all boxes and click continue
5. Make sure it is using Java 8 and finish
