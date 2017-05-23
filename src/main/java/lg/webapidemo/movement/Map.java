package lg.webapidemo.movement;

import lg.webapidemo.movement.Point;

import javax.el.MapELResolver;
import javax.swing.text.Position;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Map {

    private List<List<MapElement>> map;

    Map(Integer levelNumber) throws URISyntaxException, IOException {
        Path path = Paths.get(ClassLoader.getSystemResource("/maps/map_" + levelNumber + ".map").toURI());

        map = Files.lines(path).map(
                row -> Arrays.stream(row.split("\\s")).map(MapElement::fromMapNotationElement).collect(toList())
            ).collect(toList());
    }

    Point playerStartingPosition() {
        return getPositionOfElement(MapElement.PLAYER);
    }

    public Distance distanceToGoal(Point playerPosition) {
        return Distance.between(playerPosition, getPositionOfElement(MapElement.GOAL));
    }

    public Boolean isAtGoal(Point playerPosition) {
        return getMapElementAtPoint(playerPosition).equals(MapElement.GOAL);
    }

    public Boolean isIllegalPlayerPosition(Point playerPosition) {
        return !getMapElementAtPoint(playerPosition).equals(MapElement.WALL);
    }

    private MapElement getMapElementAtPoint(Point point) {
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

    private enum MapElement {
        EMPTY("."), WALL("x"), GOAL("g"), PLAYER("p");

        private String mapNotation;

        MapElement(String mapNotation) {
            this.mapNotation = mapNotation;
        }

        static MapElement fromMapNotationElement(String element) {
            for(MapElement mapElement : MapElement.values()) {
                if(mapElement.mapNotation.equals(element)) {
                    return mapElement;
                }
            }
            return null;
        }
    }

}
