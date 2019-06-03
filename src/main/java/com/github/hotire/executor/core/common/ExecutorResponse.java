package com.github.hotire.executor.core.common;

public class ExecutorResponse<T> {
  private T body;
  private int status;
  private Throwable error;

  public ExecutorResponse(T body) {
    this.body = body;
    this.status = 200;
  }

  public ExecutorResponse(T body, int status) {
    this.body = body;
    this.status = status;
  }

  public ExecutorResponse(Throwable error) {
    this.error = error;
    this.status = 500;
  }

  public ExecutorResponse(Throwable error, int status) {
    this.error = error;
    this.status = status;
  }

  public T getBody() {
    return body;
  }

  public void setBody(T body) {
    this.body = body;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public Throwable getError() {
    return error;
  }

  public void setError(Throwable error) {
    this.error = error;
  }
}
