# <p align="center">SQLava</p>
 <p align="center">A simple & easy to use Java MySQL Query Builder</p>
I've made this project a while ago in my free time while having exams  & I've decided to publish it to everyone!
The purpose of this plugin is to make the process of creating a mysql connection & creating quries fun and easy!

## Installation
[![](https://jitpack.io/v/Cobeine/SQLava.svg)](https://jitpack.io/#Cobeine/SQLava)
<p></p>
If you're using maven or gradle as a dependency manager then just follow the steps below.
Otherwise, just download the jar and add it as an artifact dependency.

### Adding the repository

###### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

###### Gradle
```groovy

repositories {
    mavenCentral()
    maven {
        url 'https://jitpack.io'
    }
}
```

### Implementing the dependency

###### Maven
```xml
<dependency>
    <groupId>com.github.cobeine</groupId>
    <artifactId>SQLava</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

###### Gradle
```groovy

dependencies {
    implementation 'com.github.cobeine:SQLava:1.0-SNAPSHOT'
    //Please note that my library is dependant on Hikrai
    //So, make sure to implement HikariCP in your project.
}
```

### Examples

For the examples, make sure to check the test source here.

### Contributing

Before contributing to this project, please note the following:

###### - Test your changes: Ensure that any changes you make to the project have been thoroughly tested before submitting them.

###### - Provide documentation: If you are adding new features or functionality, consider providing clear and concise documentation to help other contributors understand how to use them.

###### - Follow established standards: Adhere to established coding standards and best practices to ensure that your contributions are consistent with the rest of the project.

###### - Submit changes appropriately: Follow the project's preferred method of submitting changes, whether that is through pull requests, commits, or another method specified by the project owner.


#### <p align="center">Thanks for reading & reviewing my project!</p>





