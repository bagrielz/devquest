package devquest.application.exceptions.handlers;

import devquest.application.exceptions.ExceptionResponse;
import devquest.application.exceptions.InvalidJwtAuthenticationException;
import devquest.application.exceptions.RequiredObjectIsNullException;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Hidden
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(InvalidJwtAuthenticationException.class)
  public final ResponseEntity<ExceptionResponse> invalidJwtAuthenticationException(
          Exception ex, WebRequest request) {

    ExceptionResponse exceptionResponse = ExceptionResponse.builder()
            .timestamp(new Date())
            .message(ex.getMessage())
            .details(request.getDescription(false))
            .build();

    return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(RequiredObjectIsNullException.class)
  public final ResponseEntity<ExceptionResponse> requiredObjectIsNullException(
          Exception ex, WebRequest request) {

    ExceptionResponse exceptionResponse = ExceptionResponse.builder()
            .timestamp(new Date())
            .message(ex.getMessage())
            .details(request.getDescription(false))
            .build();

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

}
