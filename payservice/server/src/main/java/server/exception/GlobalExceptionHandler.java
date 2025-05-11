package server.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleIllegalArgument(ClientNotFoundException ex) {
        var errorResponse = new ErrorResponse(404,
                String.format("Клиент(ы) c clientId=%s не найден(-ы)", ex.getMessage()));
        return Mono.just(ResponseEntity.status(HttpStatusCode.valueOf(404)).body(errorResponse));
    }

    @ExceptionHandler(TypeMismatchException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleIllegalArgument(TypeMismatchException ex) {
        var errorResponse = new ErrorResponse(400,
                String.format("Некорректный тип клиентского id=" + ex.getValue()));
        return Mono.just(ResponseEntity.badRequest().body(errorResponse));
    }

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleIllegalArgument(ServerWebInputException ex) {
        var errorResponse = new ErrorResponse(400, "Некорректное тело запроса");
        return Mono.just(ResponseEntity.badRequest().body(errorResponse));
    }

    @ExceptionHandler(BalanceNotEnough.class)
    public Mono<ResponseEntity<ErrorResponse>> handleIllegalArgument(BalanceNotEnough ex) {
        String message = String.format("Объем средств недостаточен для выполнения операции id=%s", ex.getMessage());
        var errorResponse = new ErrorResponse(422, message);
        return Mono.just(ResponseEntity.status(HttpStatusCode.valueOf(422)).body(errorResponse));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleIllegalArgument(DataIntegrityViolationException ex) {
        String message = "Ошибка при сохранении в базу данных вследствие нарушений ограничений (вероятно, нехватка средств)";
        var errorResponse = new ErrorResponse(422, message);
        return Mono.just(ResponseEntity.status(HttpStatusCode.valueOf(422)).body(errorResponse));
    }

    @ExceptionHandler(IncorrectResultSizeDataAccessException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleIllegalArgument(IncorrectResultSizeDataAccessException ex) {
        String message = "Ошибка при сохранении в базу данных";
        var errorResponse = new ErrorResponse(500, message);
        return Mono.just(ResponseEntity.internalServerError().body(errorResponse));
    }

}
