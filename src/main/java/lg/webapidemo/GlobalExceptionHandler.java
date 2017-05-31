package lg.webapidemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        if(exception.getRequiredType().isEnum()) {
            return ResponseEntity.badRequest().body("The value '" + exception.getValue() + "' is not a valid " + exception.getName() + ", remember it should be upper case");
        }

        return ResponseEntity.ok("The value '" + exception.getValue() + "' is not a valid " + exception.getName());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception) {
        return new ResponseEntity<String>("This endpoint doesn't support " + exception.getMethod() + " requests!", HttpStatus.METHOD_NOT_ALLOWED);
    }
}
