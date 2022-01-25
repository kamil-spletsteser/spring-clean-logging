# 🍃 Spring Clean Logging

---

### ⚽️ The goal of SCL

### 🚀 Getting started

Currently, there is no artifact published to public repository, 
instead script `mvn-install-locally.sh` can be used to install in local maven repository.

#### `pom.xmn`

```xml
<dependency>
    <groupId>pl.k4mil</groupId>
    <artifactId>spring-clean-logging</artifactId>
    <version>0.0.1</version>
</dependency>
```

#### `application.properties`
```yaml
scl:
  logs:
    - id: 1
      reference: 'pl.k4mil.usageexample.TestBean.createAddress'
      level: warn
      type: before
      message: 'Creating new address - street: {}, city: {}'
      paths:
        - args[0]
        - args[1]

    - id: 2
      reference: 'pl.k4mil.usageexample.TestBean.createPersonWithAddress'
      level: error
      type: after
      message: 'New person created - city: {}, firstName: {}, lastName: {}, id: {}'
      paths:
        - args[2].city
        - args[0]
        - args[1]
        - retVal.id

    - id: 3
      reference: 'pl.k4mil.usageexample.TestBean.createPerson'
      level: info
      type: after
      message: 'New person created - toString(): {}'
      paths:
        - retVal.
```

#### Code

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

### 🗺 Roadmap

### 💼 License

### 📧 Contact