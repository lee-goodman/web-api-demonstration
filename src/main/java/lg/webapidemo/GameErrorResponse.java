package lg.webapidemo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GameErrorResponse {

    private Integer status;
    private String statusText;
    private String error;

    public GameErrorResponse(Integer status, String statusText, String error) {
        this.status = status;
        this.error = error;
    }

    public static ResponseEntity<GameErrorResponse> entity(HttpStatus status, String error) {
        return new ResponseEntity<GameErrorResponse>(new GameErrorResponse(status.value(), status.getReasonPhrase(), error), status);
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
