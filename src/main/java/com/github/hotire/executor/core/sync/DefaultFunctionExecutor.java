package com.github.hotire.executor.core.sync;

import com.github.hotire.executor.core.common.ExecutorResponse;
import com.github.hotire.executor.core.common.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class DefaultFunctionExecutor<T> extends AbstractFunctionExecutor<T> {

  protected DefaultFunctionExecutor(
    Task<Supplier<T>, T> task) {
    super(task);
  }

  public static<T> DefaultFunctionExecutor<T> ofTask(
    Supplier<T> task) {
    return new DefaultFunctionExecutor<>(Task.of(task));
  }

  public static<T> DefaultFunctionExecutor<T> ofTask(
    Supplier<T> task, Consumer<Throwable> doOnError) {
    return new DefaultFunctionExecutor<>(Task.of(task, doOnError));
  }

  public static<T> DefaultFunctionExecutor<T> ofTask(
    Supplier<T> task, Consumer<Throwable> doOnError, Consumer<T> doOnSuccess) {
    return new DefaultFunctionExecutor<>(Task.of(task, doOnError, doOnSuccess));
  }

  @Override
  public List<ExecutorResponse<T>> execute() {
    final List<ExecutorResponse<T>> executorResponses = new ArrayList<>();

    // execute fistTask
    T result;

    try {
      result = getFirstTask().getTask().get();
    } catch (Exception e) {
      getFirstTask().getDoOnError().accept(e);
      executorResponses.add(new ExecutorResponse<>(e));
      return executorResponses;
    }

    getFirstTask().getDoOnSuccess().accept(result);
    executorResponses.add(new ExecutorResponse<>(result));

    // execute tasks
    for (Task<Function<T, T>, T> task : getTasks()) {
      try {
        result = task.getTask().apply(result);
      } catch (Throwable e) {
        task.getDoOnError().accept(e);
        executorResponses.add(new ExecutorResponse<>(e));
        break;
      }
      task.getDoOnSuccess().accept(result);
      executorResponses.add(new ExecutorResponse<>(result));
    }

    return executorResponses;
  }

}
