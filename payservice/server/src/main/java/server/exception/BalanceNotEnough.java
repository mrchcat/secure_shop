package server.exception;

public class BalanceNotEnough extends RuntimeException {
    public BalanceNotEnough(String message) {
        super(message);
    }
}
