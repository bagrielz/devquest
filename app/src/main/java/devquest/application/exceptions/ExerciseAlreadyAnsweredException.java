package devquest.application.exceptions;

public class ExerciseAlreadyAnsweredException extends RuntimeException {
  public ExerciseAlreadyAnsweredException(String message) {
    super(message);
  }
}
