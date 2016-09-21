# TrumpBot
Ryan De La O
Tyler Lynch
Christopher Salinas

## Testers

### Database Files

1. `bayesData.csv` used for training naive bayes classifier.

2. `responses.csv` contains responses used for populating the database

### Running the chatbot

1. Graders of CS427: An email has been sent to your cs.unm.edu account. Please accespt
the slack chat group invite.

2. Once you are in the group, click on the "channels" to search channels and search
the "trumptower" channel.

3. Join "trumptower" channel

4. Begin talking with Donald Trump

5. To restart the conversation, simply type `$restart`


## Specificities

Direct Matching:

 There are two options for direct matching in The Trump bot. You can either get direct matching
 with the state of the conversation or with key phrases that will trigger "Trump" to go off on a
 topic with a certain response.

Example output from Trump:

    <UserName> [9:33 PM]
    You're a sexist pig
    trumpBOT [9:33 PM]
    I only said that about Rosie O'Donnell. Let me tell you, she's an exception.

Partial Matching:

 Along with Direct Matching, key phrases contained within a response will trigger the same output.
 Examples of this include "how old are you." Another one is "hi" once the conversation begins.
 Along with key phrases certain patterns in your sentiment towards the bot will prompt a swift exit
 by the bot. The bot is a politician and refuses to be under attack.

Stochasticity:

 There are multiple points in the code that produce random events. The user input is analyzed
 through a full Natural Language processor as well as a Naive Bayes classification that is trained
 on polices published by Trump himself. Along with NLP and Bayes, we also use a sentiment classier
 to classy the sentiment of each incoming message. Based on all these factors responses will be
 dynamically selected depending on the user's input. If the user enters the same response in
 separate conversation, the output to the bot will be the same, unless the bot has already used
 the response. Certain patterns in your sentiment towards the bot will trigger the bot to say
 certain phrases that will include a random issue to talk about. This is a tactic to redirect
 the conversation.


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
