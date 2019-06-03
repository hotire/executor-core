package com.github.hotire.executor.core.sync;

import com.github.hotire.executor.core.common.Task;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DefaultExecutor {
  private DefaultExecutor(){}

  public static<T> DefaultSupplierExecutor<T> ofTask(
    Supplier<T> task) {
    return new DefaultSupplierExecutor<>(Task.of(task));
  }

  public static<T> DefaultSupplierExecutor<T> ofTask(
    Supplier<T> task, Consumer<Throwable> doOnError) {
    return new DefaultSupplierExecutor<>(Task.of(task, doOnError));
  }

  public static<T> DefaultSupplierExecutor<T> ofTask(
    Supplier<T> task, Consumer<Throwable> doOnError, Consumer<T> doOnSuccess) {
    return new DefaultSupplierExecutor<>(Task.of(task, doOnError, doOnSuccess));
  }

  public static<T> DefaultFunctionExecutor<T> ofFunctionTask(
    Supplier<T> task) {
    return new DefaultFunctionExecutor<>(Task.of(task));
  }

  public static<T> DefaultFunctionExecutor<T> ofFunctionTask(
    Supplier<T> task, Consumer<Throwable> doOnError) {
    return new DefaultFunctionExecutor<>(Task.of(task, doOnError));
  }

  public static<T> DefaultFunctionExecutor<T> ofFunctionTask(
    Supplier<T> task, Consumer<Throwable> doOnError, Consumer<T> doOnSuccess) {
    return new DefaultFunctionExecutor<>(Task.of(task, doOnError, doOnSuccess));
  }

}
