package lg.webapidemo;

import lg.webapidemo.position.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;

public class Game {

    private static final Logger LOG = LoggerFactory.getLogger(Game.class);

    private Map map;
    private Point playerPosition;

    public Game(String gameId, Integer level) throws URISyntaxException, IOException {
        this.map = new Map(level);
        this.playerPosition = this.map.playerStartingPosition();
        LOG.info("Game '{}' started, level {}, player is at {}", gameId, level, this.playerPosition);
    }

    public Boolean isGameComplete() {
        return map.isAtGoal(playerPosition);
    }

    public Distance getDistanceToGoal() {
        return map.distanceToGoal(playerPosition);
    }

    public Surroundings getPlayerSurroundings() {
        return map.getPlayerSurroundings(playerPosition);
    }

    public Boolean move(Direction direction) {
        Point newPosition = playerPosition.translate(direction);
        if(map.isIllegalPlayerPosition(newPosition)) {
            return false;
        }
        playerPosition = newPosition;
        return true;
    }

}
