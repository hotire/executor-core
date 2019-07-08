package com.github.hotire.executor.core.common;

import java.util.function.Consumer;
import java.util.function.Function;

public class Caster {
  private Caster(){}
  public static <T, R> Consumer<T> castAccept(Function<T, R> castFunction, Consumer<R> consumer) {
    return t -> consumer.accept(castFunction.apply(t));
  }

  public static class CastConsumer<T, R> {
    private Function<T, R> castFunction;
    private CastConsumer(Function<T, R> castFunction) {
      this.castFunction = castFunction;
    }
    public Consumer<T> accept(Consumer<R> consumer) {
      return t -> consumer.accept(castFunction.apply(t));
    }
  }

  public static <T, R> CastConsumer<T, R> cast(Function<T, R> castFunction) {
    return new CastConsumer<>(castFunction);
  }
}
