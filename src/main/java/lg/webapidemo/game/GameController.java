package lg.webapidemo.game;

import lg.webapidemo.DoorsResponse;
import lg.webapidemo.NoSuchObjectException;
import lg.webapidemo.player.PlayerBlindException;
import lg.webapidemo.objects.Blocker;
import lg.webapidemo.position.Direction;
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

@RestController
public class GameController {

    @Autowired
    private GameManager gameManager;

    @PostMapping("/newGame")
    public String startNewGame(@RequestParam String gameId, @RequestParam(defaultValue = "1") Integer level) throws Exception {
        GameSummary summary = gameManager.newGame(gameId, level);
        return summary.toString();
    }

    @PostMapping("/move")
    public ResponseEntity<String> movePlayer(@RequestParam String gameId, @RequestParam Direction direction) {
        Game game = gameManager.getGame(gameId);
        Optional<Blocker> movementBlocker = game.move(direction);

        ResponseEntity<String> response = ResponseEntity.ok(game.isPlayerBlind() ? "" : "Player has moved!");
        if(movementBlocker.isPresent()) {
            response = ResponseEntity.badRequest().body(game.isPlayerBlind() ? "" : "Cannot move in that direction; " + movementBlocker.get());
        }
        if(game.isGameComplete()) {
            response = ResponseEntity.ok("Well done, level completed in " + game.getMoveCount() + " moves!");
        }
        return response;
    }

    @GetMapping("/distance")
    public String getDistanceToObjective(@RequestParam String gameId) {
        return gameManager.getGame(gameId).getDistanceToGoal().toString();
    }

    @GetMapping("/surroundings")
    public String getSurroundings(@RequestParam String gameId) {
        return gameManager.getGame(gameId).getPlayerSurroundings().toString();
    }

    @GetMapping("/doors")
    public DoorsResponse getDoors(@RequestParam String gameId) {
        return new DoorsResponse(gameManager.getGame(gameId).getDoors());
    }

    @PostMapping("/doors/{doorId}/open")
    public String openDoor(@PathVariable String doorId, @RequestParam String gameId, HttpServletRequest request) {
        Game game = gameManager.getGame(gameId);
        game.openDoor(doorId, getAuth(request));
        return "The door is now open";
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
    public ResponseEntity<String> handleGameNotFoundException(GameNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlayerBlindException.class)
    public ResponseEntity<String> handlePlayerBlindException() {
        return ResponseEntity.badRequest().body("You are blind, you can't do that!");
    }

    @ExceptionHandler(GameCompleteException.class)
    public ResponseEntity<String> handleGameCompleteException() {
        return new ResponseEntity<>("Level is complete, you need to start a new game!", HttpStatus.GONE);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handleFileNotFoundException() {
        return new ResponseEntity<>("Level does not exist!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchObjectException.class)
    public ResponseEntity<String> handleNoSuchObjectException(NoSuchObjectException exception) {
        return new ResponseEntity<>("No object called '" + exception.getObjectId() + "' exists!", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
