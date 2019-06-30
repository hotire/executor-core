package com.github.hotire.executor.core.common;

import java.util.function.Consumer;

public class ErrorHelper {
  private ErrorHelper(){}
  public static Consumer<Throwable> throwError() {
    return throwable -> {
      throw new RuntimeException(throwable);
    };
  }
  public static Consumer<Throwable> acceptAndThrowError(Consumer<Throwable> doOnError) {
    return throwable -> {
      doOnError.accept(throwable);
      throw new RuntimeException(throwable);
    };
  }
}
