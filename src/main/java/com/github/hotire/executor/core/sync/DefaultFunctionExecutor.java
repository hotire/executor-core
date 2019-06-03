package com.github.hotire.executor.core.sync;

import com.github.hotire.executor.core.common.ExecutorResponse;
import com.github.hotire.executor.core.common.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    try {
      T result = getFirstTask().getTask().get();
      getFirstTask().getDoOnSuccess().accept(result);
      atomicReference.set(result);
      executorResponses.add(new ExecutorResponse<>(result));
    } catch (Exception e) {
      getFirstTask().getDoOnError().accept(e);
      executorResponses.add(new ExecutorResponse<>(e));
    }

    for (Task<Function<T, T>, T> task : getTasks()) {
      try {
        if (Objects.isNull(atomicReference.get())) {
          break;
        }
        T result = task.getTask().apply(atomicReference.get());
        task.getDoOnSuccess().accept(result);
        atomicReference.set(result);
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
