package lg.webapidemo;

import com.google.common.collect.ImmutableMap;
import lg.webapidemo.position.*;
import lg.webapidemo.position.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Game {

    private static final Logger LOG = LoggerFactory.getLogger(Game.class);

    private Map map;
    private Point playerPosition;
    private Integer moveCount = 0;
    private Boolean playerIsBlind;
    private Boolean gameIsBroken;
    private java.util.Map<String, Door> doors = ImmutableMap.<String,Door>builder()
            .put("door1", new Door())
            .put("door2", new Door())
            .put("door3", new Door())
            .build();

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

    public Optional<Blocker> move(Direction direction) {
        if(isGameComplete()) {
            throw new GameCompleteException();
        }
        Point newPosition = playerPosition.translate(direction);
        Optional<Blocker> blocker = map.isIllegalPlayerPosition(newPosition, doors);
        if(!blocker.isPresent()) {
            playerPosition = newPosition;
            moveCount++;
        }
        return blocker;
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

    void openDoor(String doorId, UsernamePasswordAuthenticationToken credentials) {
        if(!map.canOpenDoor(doorId, credentials)) {
            throw new BadCredentialsException("Incorrect details specified for door '" + doorId + "'!");
        }
        Door door = doors.get(doorId);
        if(door == null) {
            throw new NoSuchObjectException(doorId);
        }
        door.open();
    }

    Set<String> getDoors() {
        return doors.keySet();
    }

    @Override
    public String toString() {
        return playerIsBlind ? "...oh and by the way, you are blind in this level!" : "";
    }
}
