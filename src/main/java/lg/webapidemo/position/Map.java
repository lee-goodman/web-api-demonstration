package lg.webapidemo.position;

import com.google.common.io.CharStreams;
import lg.webapidemo.Blocker;
import lg.webapidemo.Door;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class Map {

    private List<List<MapElement>> map;

    public Map(Integer levelNumber) throws URISyntaxException, IOException {
        ClassPathResource resource = new ClassPathResource("maps/map_" + levelNumber + ".map");

        map = CharStreams.readLines(new InputStreamReader(resource.getInputStream())).stream().map(
                row -> Arrays.stream(row.split("\\s+")).map(MapElement::new).collect(toList())
            ).collect(toList());
    }

    public Point playerStartingPosition() {
        return getPositionOfElement(MapElementType.PLAYER);
    }

    public Distance distanceToGoal(Point playerPosition) {
        return Distance.between(playerPosition, getPositionOfElement(MapElementType.GOAL));
    }

    public Boolean isAtGoal(Point playerPosition) {
        return getMapElementAtPoint(playerPosition).isA(MapElementType.GOAL);
    }

    public Optional<Blocker> isIllegalPlayerPosition(Point playerPosition, java.util.Map<String, Door> doors) {
        MapElement mapElement = getMapElementAtPoint(playerPosition);
        Optional<MapElementType> blockingObject = mapElement.getBlockingObject();
        if(blockingObject.isPresent()) {
            if(blockingObject.get() == MapElementType.DOOR) {
                if(doors.get(mapElement.getId().get()).isClosed()) {
                    return Optional.of(new Blocker(blockingObject.get().getFriendlyName()));
                }
            }
            return Optional.of(new Blocker(blockingObject.get().getFriendlyName()));
        }
        return Optional.empty();
    }

    public Surroundings getPlayerSurroundings(Point playerPosition) {
        return new Surroundings(
                getMapElementAtPoint(playerPosition.translate(Direction.UP)),
                getMapElementAtPoint(playerPosition.translate(Direction.RIGHT)),
                getMapElementAtPoint(playerPosition.translate(Direction.DOWN)),
                getMapElementAtPoint(playerPosition.translate(Direction.LEFT))
        );
    }

    private MapElement getMapElementAtPoint(Point point) {
        if(point.outOfBounds(map.get(0).size(), map.size())) {
            return null;
        }
        return map.get(point.getY()).get(point.getX());
    }

    private Point getPositionOfElement(MapElementType element) {
        for (int yIndex = 0; yIndex < map.size(); yIndex++) {
            List<MapElement> row = map.get(yIndex);
            for (int xIndex = 0; xIndex < row.size(); xIndex++) {
                if(row.get(xIndex).isA(element)) {
                    return new Point(xIndex, yIndex);
                }
            }
        }
        return null;
    }

}
