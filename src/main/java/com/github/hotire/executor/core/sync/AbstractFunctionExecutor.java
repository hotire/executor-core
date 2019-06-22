package com.github.hotire.executor.core.sync;

import com.github.hotire.executor.core.common.Task;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractFunctionExecutor<T> implements SyncExecutor<T> {

  private Task<Supplier<T>, T> firstTask;

  private Queue<Task<Function<T, T>, T>> tasks = new ConcurrentLinkedQueue<>();

  protected AbstractFunctionExecutor(Task<Supplier<T>, T> task) {
    this.firstTask = task;
  }

  public AbstractFunctionExecutor<T> addTask(Function<T, T> task) {
    tasks.add(Task.of(task));
    return this;
  }

  public AbstractFunctionExecutor<T> addTask(Function<T, T> task, Consumer<Throwable> doOnError) {
    tasks.add(Task.of(task, doOnError));
    return this;
  }

  public AbstractFunctionExecutor<T> addTask(Function<T, T> task, Consumer<Throwable> doOnError, Consumer<T> doOnSuccess) {
    tasks.add(Task.of(task, doOnError, doOnSuccess));
    return this;
  }

  public Task<Supplier<T>, T> getFirstTask() {
    return firstTask;
  }

  public Queue<Task<Function<T, T>, T>> getTasks() {
    return tasks;
  }

}

