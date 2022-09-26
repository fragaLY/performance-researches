package by.vk.springbootreactive.exception;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String className, Long id) {
    this("The " + className + " with id [ = " + id + " ] was not found.");
  }
}
