package ro.codecrafters.BankingSystem.exception;

public class ApiConnectionException extends RuntimeException {

    private final String message;

    public ApiConnectionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
