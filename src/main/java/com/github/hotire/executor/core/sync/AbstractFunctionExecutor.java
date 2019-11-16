package com.github.hotire.executor.core.sync;

import com.github.hotire.executor.core.common.Task;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class AbstractFunctionExecutor<T, R> implements SyncExecutor<R> {

  private Task<Supplier<T>, R> firstTask;

  private Queue<Task<Function<R, T>, R>> tasks = new ConcurrentLinkedQueue<>();

  protected AbstractFunctionExecutor(Task<Supplier<T>, R> task) {
    this.firstTask = task;
  }

  public AbstractFunctionExecutor<T, R> addTask(Function<R, T> task) {
    tasks.add(Task.of(task));
    return this;
  }

  public AbstractFunctionExecutor<T, R> addTask(Function<R, T> task, Consumer<Throwable> doOnError) {
    tasks.add(Task.of(task, doOnError));
    return this;
  }

  public AbstractFunctionExecutor<T, R> addTask(Function<R, T> task, Consumer<Throwable> doOnError,
      Consumer<R> doOnSuccess) {
    tasks.add(Task.of(task, doOnError, doOnSuccess));
    return this;
  }

  public Task<Supplier<T>, R> getFirstTask() {
    return firstTask;
  }

  public Queue<Task<Function<R, T>, R>> getTasks() {
    return tasks;
  }

}

