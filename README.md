# Executor Core

![delivery](/doc/delivery.png)

> Probably the *best* Java library for execute sync / async task


## Installation

### Maven

```
<dependency>
    <groupId>com.github.hotire</groupId>
    <artifactId>executor-core</artifactId>
    <version>0.0.2</version>
</dependency>

```

## How to use

### Async 

- Supplier

```
DefaultAsyncExecutor
      .ofTask(executor, testService::service)
      .addTask(testService::get, testService::rollback, testService::save)
      .execute();
```

```
DefaultAsyncExecutor
      .ofTask(executor, testService::service)
      .addTask(testService::get, testService::rollback, testService::save)
      .executeByAsync();
```

### Sync

- Supplier

```
DefaultExecutor
      .ofTask(testService::service)
      .addTask(testService::get)
      .execute();
```

```
DefaultExecutor
      .ofTask(testService::service)
      .addTask(() -> { throw new RuntimeException();})
      .addTask(testService::get)
      .execute();
```

- Function

```
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
  
  