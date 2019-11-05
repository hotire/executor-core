package com.github.hotire.executor.core.sync;

import com.github.hotire.executor.core.common.Task;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractFunctionExecutor<T, R> implements SyncExecutor<T> {

  private Task<Supplier<R>, T> firstTask;

  private Queue<Task<Function<T, R>, T>> tasks = new ConcurrentLinkedQueue<>();

  protected AbstractFunctionExecutor(Task<Supplier<R>, T> task) {
    this.firstTask = task;
  }

  public AbstractFunctionExecutor<T, R> addTask(Function<T, R> task) {
    tasks.add(Task.of(task));
    return this;
  }

  public AbstractFunctionExecutor<T, R> addTask(Function<T, R> task, Consumer<Throwable> doOnError) {
    tasks.add(Task.of(task, doOnError));
    return this;
  }

  public AbstractFunctionExecutor<T, R> addTask(Function<T, R> task, Consumer<Throwable> doOnError,
      Consumer<T> doOnSuccess) {
    tasks.add(Task.of(task, doOnError, doOnSuccess));
    return this;
  }

  public Task<Supplier<R>, T> getFirstTask() {
    return firstTask;
  }

  public Queue<Task<Function<T, R>, T>> getTasks() {
    return tasks;
  }

}

