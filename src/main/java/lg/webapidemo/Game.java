package lg.webapidemo;

import lg.webapidemo.movement.Direction;
import lg.webapidemo.movement.Distance;
import lg.webapidemo.movement.Map;
import lg.webapidemo.movement.Point;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.List;

public class Game {

    private Map map;
    private Point playerPosition;

    public Game(Integer level) throws URISyntaxException {
    }

    public Boolean isGameComplete() {
        return map.isAtGoal(playerPosition);
    }

    public Distance getDistanceToGoal() {
        return map.distanceToGoal(playerPosition);
    }

    public String getPlayerSurroundings() {
        return null;
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
