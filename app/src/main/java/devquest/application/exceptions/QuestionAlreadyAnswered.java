package devquest.application.exceptions;

public class QuestionAlreadyAnswered extends RuntimeException {

  public QuestionAlreadyAnswered(String message) {
    super(message);
  }

}
