# schwan_kalah

Implementation of the game Kalah ( https://en.wikipedia.org/wiki/Kalah )

## Project Setup

The project was developed on Java 1.8.0_65 with Gradle 3.4.1.  It should work with other, similar versions of both, but is untested.

Once the project is downloaded, build and test from the command line by running:
    `gradle clean build test`
    
Then run the project with
    `gradle bootRun`

It will then start running a local server, accessible at:
    `http://localhost:8080/`
        

## Code Layout

The initial Spring boot project / directory layout based on: https://www.mkyong.com/spring-boot/spring-boot-hello-world-example-jsp/

The architecture of the project is a basic MVC (Model-View-Controller):

    - The model is found within the com.schwan.kalah.model package
    - The view is within webapp/WEB-INF for dynamic content, and resources/static for static content
    - The controller is within the com.schwan.kalah.controller package
    

## Testing

All the unit tests are written in JUnit.  There are currently no integration or E2E tests.

Can be run locally either in the IDE, or on command line by running `gradle test`

Tests are automatically run upon commit at: https://circleci.com/gh/PSchwan/schwan_kalah