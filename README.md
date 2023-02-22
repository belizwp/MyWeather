# My Weather App

This is a simple weather app that uses the WeatherStack API to get the current weather for a city.

## Pre-requisites

- Java SDK 11
- Android Studio

## Getting Started

- copy the `local.default.properties` file to `local.properties`
- replace your API key to the `local.properties` file
- build and run the app

## Run Lint Checks

`./gradlew ktlintCheck`

`./gradlew ktlintFormat`

## Run Tests with Coverage

`./gradlew testDebugUnitTest`

to run tests for a specific module:
`./gradlew :module:testDebugUnitTest` 
