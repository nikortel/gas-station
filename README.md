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

### ProductBuilder

`ProductBuilder` demonstrates **fluent builder pattern** where returning interfaces dictate the operations that can be 
called for the builder. This allows strict control on how the builder is used and thus makes the setup 
easier for the user. When the setup is complete, we are returning a generic `Builder<T>` that defines only `T build()` 
method. This interface can then be passed for other builders with only the build method visible.

We are also able to define clear setup paths where only relevant options are available for the user. For example 
`ProductCostCurrencyBuilder` decides whether the setup is using metric or imperial volume unit system based on which 
currency is in use.

# pom.xml

The root pom.xml requires some special setup for surefire in order for it to work with JUnit 5.

# Usage

`./mvnw clean test`