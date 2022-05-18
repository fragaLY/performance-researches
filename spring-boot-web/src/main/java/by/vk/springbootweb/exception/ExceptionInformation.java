package by.vk.springbootweb.exception;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.http.HttpStatus;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record ExceptionInformation(HttpStatus status, Integer code, String message) {
}