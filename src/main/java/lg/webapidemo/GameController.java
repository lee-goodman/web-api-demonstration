package lg.webapidemo;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lg.webapidemo.position.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
public class GameController {

    private Cache<String, Game> games = CacheBuilder.newBuilder().expireAfterAccess(4, TimeUnit.HOURS).build();

    @PostMapping("/newGame")
    public String startNewGame(@RequestParam String gameId, @RequestParam(defaultValue = "1") Integer level) throws Exception {
        Game game = new Game(gameId, level);
        games.put(gameId, game);
        return "Started game : \"" + gameId + "\" on level " + level + "" + game.toString();
    }

    @PostMapping("/move")
    public ResponseEntity<String> movePlayer(@RequestParam String gameId, @RequestParam Direction direction) {
        Game game = getGame(gameId);
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
        return getGame(gameId).getDistanceToGoal().toString();
    }

    @GetMapping("/surroundings")
    public String getSurroundings(@RequestParam String gameId) {
        return getGame(gameId).getPlayerSurroundings().toString();
    }

    @GetMapping("/doors/")
    public DoorsResponse getDoors(@RequestParam String gameId) {
        return new DoorsResponse(getGame(gameId).getDoors());
    }

    @PostMapping("/doors/{doorId}/open")
    public String openDoor(@PathVariable String doorId, @RequestParam String gameId) {
        getGame(gameId).openDoor(doorId);
        return "The door is now open";
    }

    private Game getGame(String gameId) {
        Game game = games.getIfPresent(gameId);
        if(game == null) {
            throw new GameNotFoundException("Game " + gameId + " does not exist!");
        }
        if(game.isGameBroken()) {
            throw new GameBrokenException("Request to backend failed: MySQL syntax error at 'SELECT * FREM'");
        }
        return game;
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

}
