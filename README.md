# 🍃 Spring Clean Logging

---

### ⚽️ The goal of SCL

SCL is Spring Framework based module providing the ability to configure logs in `.properties` file 


### 🚀 Getting started

#### Setting up dependency in `pom.xml`

Currently, there is no artifact published to public repository,
instead script `mvn-install-locally.sh` can be used to install in local maven repository.

```xml
<dependency>
    <groupId>pl.k4mil</groupId>
    <artifactId>spring-clean-logging</artifactId>
    <version>0.0.1</version>
</dependency>
```

#### Defining logs

Log attributes:

`level` - Log level

```yaml
scl:
  logs:
    - reference: 'pl.k4mil.usageexample.TestBean.createAddress'
      level: warn
      type: before
      message: 'Creating new address - street: {}, city: {}'
      paths:
        - args[0]
        - args[1]

    - reference: 'pl.k4mil.usageexample.TestBean.createPersonWithAddress'
      level: error
      type: after
      message: 'New person created - city: {}, firstName: {}, lastName: {}, id: {}'
      paths:
        - args[2].city
        - args[0]
        - args[1]
        - retVal.id

    - reference: 'pl.k4mil.usageexample.TestBean.createPerson'
      level: info
      type: after
      message: 'New person created - toString(): {}'
      paths:
        - retVal
```

#### Java

Just place annotation `@EnableSCL` on main class or any class annotated with `@Configuration`

```java
@EnableSCL
@SpringBootApplication
public class UsageExampleApplication {
    
	public static void main(String[] args) {
		SpringApplication.run(UsageExampleApplication.class, args);
	}
}
```

#### Demo app

In `example` directory, there is a project showing few examples of SCL-based logs.

### 🗺 Roadmap

❌ Check compatibility with older versions of Spring Boot (currently tested on 2.6.1 only)

❌ Publish artifact to public repositories (Maven central etc.)

❌ More developer-friendly configuration validation 

### 💼 License

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

### 📧 Contact

#### k4mil-github@protonmail.com