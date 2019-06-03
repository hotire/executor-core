package com.github.hotire.executor.core.async;

import static java.util.stream.Collectors.toList;

import com.github.hotire.executor.core.common.ExecutorResponse;
import com.github.hotire.executor.core.common.AbstractSupplierExecutor;
import com.github.hotire.executor.core.common.Task;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DefaultAsyncExecutor<T> extends AbstractSupplierExecutor<T, T> {

  private Executor executor;

  protected DefaultAsyncExecutor(
    Executor executor,
    Task<Supplier<T>, T> task) {
    super(task);
    this.executor = executor;
  }
  protected DefaultAsyncExecutor(
    Task<Supplier<T>, T> task) {
    super(task);
  }

  public static<T> DefaultAsyncExecutor<T> ofTask(
    Supplier<T> task) {
    return new DefaultAsyncExecutor<>(Task.of(task));
  }

  public static<T> DefaultAsyncExecutor<T> ofTask(
    Supplier<T> task, Consumer<Throwable> doOnError) {
    return new DefaultAsyncExecutor<>(Task.of(task, doOnError));
  }

  public static<T> DefaultAsyncExecutor<T> ofTask(
   Supplier<T> task, Consumer<Throwable> doOnError, Consumer<T> doOnSuccess) {
    return new DefaultAsyncExecutor<>(Task.of(task, doOnError, doOnSuccess));
  }

  public static<T> DefaultAsyncExecutor<T> ofTask(
    Executor executor, Supplier<T> task) {
    return new DefaultAsyncExecutor<>(executor, Task.of(task));
  }

  public static<T> DefaultAsyncExecutor<T> ofTask(
    Executor executor, Supplier<T> task, Consumer<Throwable> doOnError) {
    return new DefaultAsyncExecutor<>(executor, Task.of(task, doOnError));
  }

  public static<T> DefaultAsyncExecutor<T> ofTask(
    Executor executor, Supplier<T> task, Consumer<Throwable> doOnError, Consumer<T> doOnSuccess) {
    return new DefaultAsyncExecutor<>(executor, Task.of(task, doOnError, doOnSuccess));
  }

  private CompletableFuture<T> supplyAsync(Supplier<T> supplier) {
    if (Objects.isNull(executor)) {
      return CompletableFuture
        .supplyAsync(supplier);
    }
    return CompletableFuture.supplyAsync(supplier, executor);
  }

  @Override
  public void executeByAsync() {
    getTasks()
      .forEach(task ->
        supplyAsync(task.getTask())
        .thenAccept(task.getDoOnSuccess())
        .exceptionally(throwable -> {
          task.getDoOnError().accept(throwable);
          return null;
        })
      );
  }

  @Override
  public List<ExecutorResponse<T>> execute() {
    Map<CompletableFuture, Task<Supplier<T>, T>> futureTaskMap = new HashMap<>();
    return getTasks()
      .stream()
      .map(task -> {
        CompletableFuture<T> completableFuture = supplyAsync(task.getTask());
        futureTaskMap.put(completableFuture, task);
        return completableFuture;
      })
      .collect(toList())
      .stream()
      .map(future -> {
        try {
          T result = future.get();
          futureTaskMap.get(future).getDoOnSuccess().accept(result);
          return new ExecutorResponse<>(result);
        } catch (Exception e) {
          futureTaskMap.get(future).getDoOnError().accept(e);
          return new ExecutorResponse<T>(e);
        }
      })
      .collect(toList());
  }
}
