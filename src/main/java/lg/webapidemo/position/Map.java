package lg.webapidemo.position;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import lg.webapidemo.Blocker;
import lg.webapidemo.Door;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Map {

    private static final Logger LOG = LoggerFactory.getLogger(Map.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private List<List<MapElement>> map;
    private MapMetadata metadata = MapMetadata.empty();

    public Map(Integer levelNumber) throws URISyntaxException, IOException {
        ClassPathResource resource = new ClassPathResource("maps/map_" + levelNumber + ".map");

        List<String> lines = CharStreams.readLines(new InputStreamReader(resource.getInputStream()));
        if(lines.contains("---")) {
            Integer splitPoint = lines.indexOf("---");
            List<String> mapMetadataLines = lines.subList(splitPoint+1, lines.size());
            lines = lines.subList(0, splitPoint);

            metadata = OBJECT_MAPPER.readValue(String.join("\n", mapMetadataLines), MapMetadata.class);
        }

        map = lines.stream().map(
                row -> Arrays.stream(row.split("\\s+")).map(MapElement::new).collect(toList())
            ).collect(toList());

        LOG.debug("Map {} parsed, result:\n{}", levelNumber, this);
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
                    return Optional.of(new Blocker(blockingObject.get().getFriendlyName(), mapElement.getId()));
                }
            } else {
                return Optional.of(new Blocker(blockingObject.get().getFriendlyName()));
            }
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

    @Override
    public String toString() {
        return "Map{" +
                "map=" + String.join("\n", map.stream().map(List::toString).collect(Collectors.toList())) +
                "\n\nmetadata=" + metadata +
                '}';
    }
}
