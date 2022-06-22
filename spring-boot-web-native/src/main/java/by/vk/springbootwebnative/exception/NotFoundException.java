/*
 * INT Company. Do not reproduce without permission in writing.
 * Copyright (c) 2021 INT Company. All rights reserved.
 */

package by.vk.springbootwebnative.exception;

public class NotFoundException extends RuntimeException {

  public NotFoundException() {
    super("Resource not found");
  }

  public NotFoundException(String message) {
    super(message);
  }
}
