# Gas station

This is a Maven multi-module project with Java modules. Demonstrates the use of Java 11, Java Modules, Value Objects and Maven wrapper.

# Modules
## example.value.objects

Exports package `example.valueobject`

* DeciliterVolume - Volume in deciliters
* Money - Represents money 
* UnitPrice - Price of a unit of something in a currency
* VolumeUnit - Enum describing units of volume

## gasstation

Does not export anything but requires `example.value.objects` module.

* Product - A sellable product with type and unit price
* ProductType - Type of products supported such as E10, E98 and DIESEL
* Pump - Gas pump
* Receipt - A receipt from purchase
* Tank - Product container with limited capacity

Contains also builders for setting up the gas station.

Usage examples can be found from tests

# pom.xml

The root pom.xml requires some special setup for surefire in order for it to work with JUnit 5.

# Usage

`./mvnw clean test`