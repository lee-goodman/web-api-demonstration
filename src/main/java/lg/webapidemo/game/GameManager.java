package lg.webapidemo.game;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

@Component
public class GameManager {

    private Cache<String, Game> games = CacheBuilder.newBuilder().expireAfterAccess(4, TimeUnit.HOURS).build();

    public GameSummary newGame(String gameId, Integer level) throws URISyntaxException, IOException {
        Game game = new Game(gameId, level);
        games.put(gameId, game);
        return new GameSummary(gameId, level, game.toString());
    }

    public Game getGame(String gameId) {
        Game game = games.getIfPresent(gameId);
        if(game == null) {
            throw new GameNotFoundException("Game " + gameId + " does not exist!");
        }
        if(game.isGameBroken()) {
            throw new GameBrokenException("Request to backend failed: MySQL syntax error at 'SELECT * FREM'");
        }
        return game;
    }
}
