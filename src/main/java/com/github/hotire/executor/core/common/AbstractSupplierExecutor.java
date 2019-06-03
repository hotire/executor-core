package com.github.hotire.executor.core.common;

import com.github.hotire.executor.core.async.AsyncExecutor;
import com.github.hotire.executor.core.sync.SyncExecutor;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AbstractSupplierExecutor<T, R> implements SyncExecutor<R>, AsyncExecutor {

  private Queue<Task<Supplier<T>, R>> tasks = new ConcurrentLinkedQueue<>();

  protected AbstractSupplierExecutor(Task<Supplier<T>, R> task) {
    this.tasks.add(task);
  }

  public AbstractSupplierExecutor<T, R> addTask(Supplier<T> task) {
    tasks.add(Task.of(task));
    return this;
  }

  public AbstractSupplierExecutor<T, R> addTask(Supplier<T> task, Consumer<Throwable> doOnError) {
    tasks.add(Task.of(task, doOnError));
    return this;
  }

  public AbstractSupplierExecutor<T, R> addTask(Supplier<T> task, Consumer<Throwable> doOnError, Consumer<R> doOnSuccess) {
    tasks.add(Task.of(task, doOnError, doOnSuccess));
    return this;
  }

  public Queue<Task<Supplier<T>, R>> getTasks() {
    return tasks;
  }

}
