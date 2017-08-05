package lg.webapidemo;

import lg.webapidemo.objects.Blocker;
import lg.webapidemo.objects.Door;
import lg.webapidemo.position.*;
import lg.webapidemo.position.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class Game {

    private static final Logger LOG = LoggerFactory.getLogger(Game.class);

    private Map map;
    private Player player;

    private Integer moveCount = 0;
    private Boolean gameIsBroken;
    private java.util.Map<String, Door> doors;

    public Game(String gameId, Integer level) throws URISyntaxException, IOException {
        this.map = new Map(level);
        this.player = new Player(this.map.playerStartingPosition(), level.equals(2));

        this.gameIsBroken = level.equals(3);
        this.doors = this.map.createDoorInstances();
        LOG.info("Game '{}' started, level {}, player is at {}", gameId, level, this.player.getPosition());
    }

    public Boolean isGameComplete() {
        return map.isAtGoal(player.getPosition());
    }

    public Distance getDistanceToGoal() {
        if(isGameComplete()) {
            throw new GameCompleteException();
        }
        if(player.isBlind()) {
            throw new PlayerBlindException();
        }
        return map.distanceToGoal(player.getPosition());
    }

    public Surroundings getPlayerSurroundings() {
        if(isGameComplete()) {
            throw new GameCompleteException();
        }
        if(player.isBlind()) {
            throw new PlayerBlindException();
        }
        return map.getPlayerSurroundings(player.getPosition());
    }

    public Optional<Blocker> move(Direction direction) {
        if(isGameComplete()) {
            throw new GameCompleteException();
        }
        Point newPosition = player.getPosition().translate(direction);
        Optional<Blocker> blocker = map.isIllegalPlayerPosition(newPosition, doors);
        if(!blocker.isPresent()) {
            player.setPosition(newPosition);
            moveCount++;
        }
        return blocker;
    }

    Boolean isPlayerBlind() {
        return player.isBlind();
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
        return player.isBlind() ? "...oh and by the way, you are blind in this level!" : null;
    }
}
