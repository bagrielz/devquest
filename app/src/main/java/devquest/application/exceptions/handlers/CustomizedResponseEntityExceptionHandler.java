package devquest.application.exceptions.handlers;

import devquest.application.exceptions.*;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    ExceptionResponse exceptionResponse = getExceptionResponse(ex, request);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(RequiredObjectIsNullException.class)
  public final ResponseEntity<ExceptionResponse> requiredObjectIsNullException(
          Exception ex, WebRequest request) {

    ExceptionResponse exceptionResponse = getExceptionResponse(ex, request);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public final ResponseEntity<ExceptionResponse> resourceNotFoundException(
          Exception ex, WebRequest request) {

    ExceptionResponse exceptionResponse = getExceptionResponse(ex, request);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(QuestionAlreadyAnsweredException.class)
  public final ResponseEntity<ExceptionResponse> questionAlreadyAnsweredException(
          Exception ex, WebRequest request) {

    ExceptionResponse exceptionResponse = getExceptionResponse(ex, request);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(ExerciseAlreadyAnsweredException.class)
  public final ResponseEntity<ExceptionResponse> exerciseAlreadyAnsweredException(
          Exception ex, WebRequest request) {

    ExceptionResponse exceptionResponse = getExceptionResponse(ex, request);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public final ResponseEntity<ExceptionResponse> badCredentialsException(
          Exception ex, WebRequest request) {

    ExceptionResponse exceptionResponse = getExceptionResponse(ex, request);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public final ResponseEntity<ExceptionResponse> usernameNotFoundException(
          Exception ex, WebRequest request) {

    ExceptionResponse exceptionResponse = getExceptionResponse(ex, request);
    return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
  }

  private static ExceptionResponse getExceptionResponse(Exception ex, WebRequest request) {
    ExceptionResponse exceptionResponse = ExceptionResponse.builder()
            .timestamp(new Date())
            .message(ex.getMessage())
            .details(request.getDescription(false))
            .build();
    return exceptionResponse;
  }

}
