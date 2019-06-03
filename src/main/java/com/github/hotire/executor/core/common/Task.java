package com.github.hotire.executor.core.common;

import java.util.function.Consumer;


public class Task<T, R> {
  private T task;
  private Consumer<Throwable> doOnError;
  private Consumer<R> doOnSuccess;

  private Task(T task, Consumer<Throwable> doOnError, Consumer<R> doOnSuccess) {
    this.task = task;
    this.doOnError = doOnError;
    this.doOnSuccess = doOnSuccess;
  }

  public static <T, R> Task<T, R> of(T task) {
    return new Task<>(task, ex -> {}, r -> {});
  }
  public static <T, R> Task<T, R> of(T task, Consumer<Throwable> doOnError) {
    return new Task<>(task, doOnError, r -> {});
  }
  public static <T, R> Task<T, R> of(T task, Consumer<Throwable> doOnError, Consumer<R> doOnSuccess) {
    return new Task<>(task, doOnError, doOnSuccess);
  }

  public T getTask() {
    return task;
  }

  public void setTask(T task) {
    this.task = task;
  }

  public Consumer<Throwable> getDoOnError() {
    return doOnError;
  }

  public void setDoOnError(Consumer<Throwable> doOnError) {
    this.doOnError = doOnError;
  }

  public Consumer<R> getDoOnSuccess() {
    return doOnSuccess;
  }

  public void setDoOnSuccess(Consumer<R> doOnSuccess) {
    this.doOnSuccess = doOnSuccess;
  }
}
