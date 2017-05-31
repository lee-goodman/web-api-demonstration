package lg.webapidemo;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lg.webapidemo.position.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
public class GameController {

    private Cache<String, Game> games = CacheBuilder.newBuilder().expireAfterAccess(4, TimeUnit.HOURS).build();

    @PostMapping("/newGame")
    public String startNewGame(@RequestParam String gameId) throws Exception {
        games.put(gameId, new Game(gameId,1));
        return "Started game : \"" + gameId + "\"";
    }

    @PostMapping("/move")
    public ResponseEntity<String> movePlayer(@RequestParam String gameId, @RequestParam Direction direction) {
        Boolean moveSuccess = getGame(gameId).move(direction);

        ResponseEntity<String> response = ResponseEntity.ok("Player has moved!");
        if(!moveSuccess) {
            response = ResponseEntity.badRequest().body("Cannot move in that direction!");
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

    @GetMapping("/status")
    public String getGameStatus(@RequestParam String gameId) {
        return "Game is " + (getGame(gameId).isGameComplete() ? "complete" : "incomplete");
    }

    private Game getGame(String gameId) {
        Game game = games.getIfPresent(gameId);
        if(game == null) {
            throw new GameNotFoundException("Game " + gameId + " does not exist!");
        }
        return game;
    }

    @ExceptionHandler(GameNotFoundException.class)
    public String handleGameNotFoundException(GameNotFoundException exception) {
        return exception.getMessage();
    }
}
