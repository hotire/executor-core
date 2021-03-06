# Executor Core

![delivery](/doc/delivery.png)

> Probably the *best* Java library for execute sync / async task


## Installation

### Maven

```xml
<repository>
  <id>hotire</id>
  <url>http://dl.bintray.com/hotire/utils</url>
</repository>

<dependency>
    <groupId>com.github.hotire</groupId>
    <artifactId>executor-core</artifactId>
    <version>1.1.3</version>
</dependency>

```

## How to use

### Async 

- Supplier

```java
DefaultAsyncExecutor
      .ofTask(executor, testService::service)
      .addTask(testService::get, testService::rollback, testService::save)
      .execute();
```

```java
DefaultAsyncExecutor
      .ofTask(executor, testService::service)
      .addTask(testService::get, testService::rollback, testService::save)
      .executeByAsync();
```

### Sync

- Supplier

```java
DefaultExecutor
      .ofTask(testService::service)
      .addTask(testService::get)
      .execute();
```

```java
DefaultExecutor
      .ofTask(testService::service)
      .addTask(() -> { throw new RuntimeException();})
      .addTask(testService::get)
      .execute();
```

- Function

```java
DefaultExecutor
      .ofFunctionTask(() -> "s")
      .addTask(s -> s + s)
      .addTask(s -> s + s)
      .execute()
      .stream()
      .map(ExecutorResponse::getBody)
      .forEach(System.out::println);
```
<b>output</b> <br/>

s <br/>
ss <br/>
ssss <br/>
  
  
## blog

### Core (Task, AbstractSupplierExecutor, AbstractFunctionExecutor)

- https://blog.naver.com/gngh0101/221553733927

### DefaultExecutor (SupplierExecutor, FunctionExecutor), DefaultAsyncExecutor

- https://blog.naver.com/gngh0101/221556093114

### Getting Started Executor Core

- https://blog.naver.com/gngh0101/221562124236
  
  