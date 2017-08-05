package lg.webapidemo;

import lg.webapidemo.game.*;
import lg.webapidemo.objects.Blocker;
import lg.webapidemo.player.PlayerBlindException;
import lg.webapidemo.position.Direction;
import lg.webapidemo.position.Distance;
import lg.webapidemo.position.Surroundings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.util.Optional;

@RequestMapping("/advanced/")
@RestController
public class AdvancedGameController {

    @Autowired
    private GameManager gameManager;

    @PostMapping("/newGame")
    public GameSummary startNewGame(@RequestParam String gameId, @RequestParam(defaultValue = "1") Integer level) throws Exception {
        return gameManager.newGame(gameId, level);
    }

    @PostMapping("/move")
    public ResponseEntity<MoveResponse> movePlayer(@RequestParam String gameId, @RequestParam Direction direction) {
        Game game = gameManager.getGame(gameId);
        Optional<Blocker> movementBlocker = game.move(direction);

        ResponseEntity<MoveResponse> response = MoveResponse.successfulMove();
        if(movementBlocker.isPresent()) {
            response = MoveResponse.unsuccessfulMove(movementBlocker.get());
        }
        if(game.isGameComplete()) {
            response = MoveResponse.gameComplete();
        }
        return response;
    }

    @GetMapping("/distance")
    public Distance getDistanceToObjective(@RequestParam String gameId) {
        return gameManager.getGame(gameId).getDistanceToGoal();
    }

    @GetMapping("/surroundings")
    public Surroundings getSurroundings(@RequestParam String gameId) {
        return gameManager.getGame(gameId).getPlayerSurroundings();
    }

    @GetMapping("/doors/")
    public DoorsResponse getDoors(@RequestParam String gameId) {
        return new DoorsResponse(gameManager.getGame(gameId).getDoors());
    }

    @PostMapping("/doors/{doorId}/open")
    public DoorStatus openDoor(@PathVariable String doorId, @RequestParam String gameId, HttpServletRequest request) {
        gameManager.getGame(gameId).openDoor(doorId, getAuth(request));
        return new DoorStatus(true);
    }

    private UsernamePasswordAuthenticationToken getAuth(HttpServletRequest request) {
        String auth = request.getHeader("Authorization");
        if(auth != null && auth.startsWith("Basic ")) {
            String[] creds = new String(Base64.decode(auth.replace("Basic ", "").getBytes())).split(":");
            return new UsernamePasswordAuthenticationToken(creds[0], creds[1]);
        }
        throw new AuthenticationCredentialsNotFoundException("The door is locked, you need to provide credentials to get in!");
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<GameErrorResponse> handleGameNotFoundException(GameNotFoundException exception) {
        return GameErrorResponse.entity(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(PlayerBlindException.class)
    public ResponseEntity<GameErrorResponse> handlePlayerBlindException() {
        return GameErrorResponse.entity(HttpStatus.BAD_REQUEST, "You are blind, you can't do that!");
    }

    @ExceptionHandler(GameCompleteException.class)
    public ResponseEntity<GameErrorResponse> handleGameCompleteException() {
        return GameErrorResponse.entity(HttpStatus.GONE, "Level is complete, you need to start a new game!");
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<GameErrorResponse> handleFileNotFoundException() {
        return GameErrorResponse.entity(HttpStatus.NOT_FOUND, "Level does not exist!");
    }

    @ExceptionHandler(NoSuchObjectException.class)
    public ResponseEntity<GameErrorResponse> handleNoSuchObjectException(NoSuchObjectException exception) {
        return GameErrorResponse.entity(HttpStatus.NOT_FOUND, "No object called '" + exception.getObjectId() + "' exists!");
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<GameErrorResponse> handleAuthenticationException(AuthenticationException exception) {
        return GameErrorResponse.entity(HttpStatus.UNAUTHORIZED, exception.getMessage());
    }

}
