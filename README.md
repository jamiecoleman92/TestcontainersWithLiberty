# TestcontainersWithLiberty
This repo is to show how you can take a simple Liberty application and use Testcontainers to run all your tests in a container.

## Pre-reqs
- Java 11
- Docker Desktop
- Maven

## Before you do anything!
First you will need to manually build your docker image. I have provided you with a Dockerfile in the /start directory you can use for creating your image. Firstly you need to package your application with the command `mvn package` from the /start directory.

Once the build is finished you need to build your docker image with the following command `docker build -t testcontainers:test .`

You can see if your image has been created by running docker images and look for the testcontainers:test image

Now that has bee created you can start testing your application

## To run the tests
You can do this either by running Liberty in devmode and pressing enter once it is up and running by using the following command `mvn liberty:dev`. Then simply pres the return key and your tests will run.

Alternativly you can use the maven failsafe plugin to run your tests by running the following command `mvn verify`

Now you should see some output in your terminal in regards to the container being started and your tests all running again the endpoint in the container.