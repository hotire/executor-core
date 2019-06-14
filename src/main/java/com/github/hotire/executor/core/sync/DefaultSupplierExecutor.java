package com.github.hotire.executor.core.sync;

import com.github.hotire.executor.core.common.ExecutorResponse;
import com.github.hotire.executor.core.common.AbstractSupplierExecutor;
import com.github.hotire.executor.core.common.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DefaultSupplierExecutor<T> extends AbstractSupplierExecutor<T, T> {

  protected DefaultSupplierExecutor(
    Task<Supplier<T>, T> task) {
    super(task);
  }

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

  @Override
  public void executeByAsync() {
    throw new UnsupportedOperationException("UnsupportedAsyncException");
  }

  @Override
  public List<ExecutorResponse<T>> execute() {
    final List<ExecutorResponse<T>> executorResponses = new ArrayList<>();

    for (Task<Supplier<T>, T> task : getTasks()) {
      try {
        T result = task.getTask().get();
        task.getDoOnSuccess().accept(result);
        executorResponses.add(new ExecutorResponse<>(result));
      } catch (Exception e) {
        task.getDoOnError().accept(e);
        executorResponses.add(new ExecutorResponse<>(e));
        break;
      }
    }

    return executorResponses;
  }
}
