package lg.webapidemo.position;

import com.google.common.io.CharStreams;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Map {

    private List<List<MapElement>> map;

    public Map(Integer levelNumber) throws URISyntaxException, IOException {
        ClassPathResource resource = new ClassPathResource("maps/map_" + levelNumber + ".map");

        map = CharStreams.readLines(new InputStreamReader(resource.getInputStream())).stream().map(
                row -> Arrays.stream(row.split("\\s")).map(MapElement::fromMapNotationElement).collect(toList())
            ).collect(toList());
    }

    public Point playerStartingPosition() {
        return getPositionOfElement(MapElement.PLAYER);
    }

    public Distance distanceToGoal(Point playerPosition) {
        return Distance.between(playerPosition, getPositionOfElement(MapElement.GOAL));
    }

    public Boolean isAtGoal(Point playerPosition) {
        return getMapElementAtPoint(playerPosition).equals(MapElement.GOAL);
    }

    public Boolean isIllegalPlayerPosition(Point playerPosition) {
        return getMapElementAtPoint(playerPosition).equals(MapElement.WALL);
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

    private Point getPositionOfElement(MapElement element) {
        for (int yIndex = 0; yIndex < map.size(); yIndex++) {
            Integer xIndex = map.get(yIndex).indexOf(element);
            if(xIndex > -1) {
                return new Point(xIndex, yIndex);
            }
        }
        return null;
    }

}
