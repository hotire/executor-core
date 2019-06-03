package com.github.hotire.executor.core.sync;

import com.github.hotire.executor.core.common.ExecutorResponse;
import java.util.List;

public interface SyncExecutor<T> {
  List<ExecutorResponse<T>> execute();
}
