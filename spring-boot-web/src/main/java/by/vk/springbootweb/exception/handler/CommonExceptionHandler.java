package by.vk.springbootweb.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import by.vk.springbootweb.exception.BadRequestException;
import by.vk.springbootweb.exception.ExceptionInformation;
import by.vk.springbootweb.exception.NotFoundException;
import by.vk.springbootweb.exception.util.MessageBeautifier;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

  @ExceptionHandler(ServletException.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ExceptionInformation handleServletException(ServletException exception) {
    var info = new ExceptionInformation(
        INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.value(), "Huston, we have a problem");
    log.error("[EXCEPTION] {}", info);
    return info;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(BAD_REQUEST)
  public ExceptionInformation handleIllegalArgumentException(IllegalArgumentException exception) {
    var info = new ExceptionInformation(BAD_REQUEST, BAD_REQUEST.value(), exception.toString());
    log.error("[EXCEPTION] {}", info);
    return info;
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public ExceptionInformation handleNotFoundException(RuntimeException exception) {
    var message =
        exception.getMessage() != null
            ? MessageBeautifier.beautify(exception.getMessage(), "[", "]")
            : "Not Found";
    var info = new ExceptionInformation(NOT_FOUND, NOT_FOUND.value(), message);
    log.error("[EXCEPTION] {}", info);
    return info;
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(BAD_REQUEST)
  public ExceptionInformation handleBadRequestException(BadRequestException exception) {
    var message =
        exception.getMessage() != null
            ? MessageBeautifier.beautify(exception.getMessage(), "[", "]")
            : "Bad Request";
    var info = new ExceptionInformation(BAD_REQUEST, BAD_REQUEST.value(), message);
    log.error("[EXCEPTION] {}", info);
    return info;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ExceptionInformation handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    var message =
        exception.getBindingResult().getAllErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.joining(", "));
    var info = new ExceptionInformation(BAD_REQUEST, BAD_REQUEST.value(), message);
    log.error("[EXCEPTION] {}", info);
    return info;
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(BAD_REQUEST)
  public ExceptionInformation handleHttpMessageNotReadableException(
      HttpMessageNotReadableException exception) {
    var message =
        exception.getLocalizedMessage() != null
            ? MessageBeautifier.beautify(exception.getLocalizedMessage(), "[", "]")
            : "Bad Request";
    var info = new ExceptionInformation(BAD_REQUEST, BAD_REQUEST.value(), message);
    log.error("[EXCEPTION] {}", info);
    return info;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(BAD_REQUEST)
  public ExceptionInformation handleConstraintViolationException(
      ConstraintViolationException exception) {
    var message =
        exception.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(", "));
    var info = new ExceptionInformation(BAD_REQUEST, BAD_REQUEST.value(), message);
    log.error("[EXCEPTION] {}", info);
    return info;
  }
}
