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
    private Integer moveCount = 0;
    private Boolean playerIsBlind;
    private Boolean gameIsBroken;

    public Game(String gameId, Integer level) throws URISyntaxException, IOException {
        this.map = new Map(level);
        this.playerPosition = this.map.playerStartingPosition();
        this.playerIsBlind = level.equals(2);
        this.gameIsBroken = level.equals(3);
        LOG.info("Game '{}' started, level {}, player is at {}", gameId, level, this.playerPosition);
    }

    public Boolean isGameComplete() {
        return map.isAtGoal(playerPosition);
    }

    public Distance getDistanceToGoal() {
        if(isGameComplete()) {
            throw new GameCompleteException();
        }
        if(playerIsBlind) {
            throw new PlayerBlindException();
        }
        return map.distanceToGoal(playerPosition);
    }

    public Surroundings getPlayerSurroundings() {
        if(isGameComplete()) {
            throw new GameCompleteException();
        }
        if(playerIsBlind) {
            throw new PlayerBlindException();
        }
        return map.getPlayerSurroundings(playerPosition);
    }

    public Boolean move(Direction direction) {
        if(isGameComplete()) {
            throw new GameCompleteException();
        }
        Point newPosition = playerPosition.translate(direction);
        if(map.isIllegalPlayerPosition(newPosition)) {
            return false;
        }
        playerPosition = newPosition;
        moveCount++;
        return true;
    }

    Boolean isPlayerBlind() {
        return playerIsBlind;
    }

    Boolean isGameBroken() {
        return gameIsBroken && moveCount > 2;
    }

    Integer getMoveCount() {
        return moveCount;
    }

    @Override
    public String toString() {
        return playerIsBlind ? "...oh and by the way, you are blind in this level!" : "";
    }
}
