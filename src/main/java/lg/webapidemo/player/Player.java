package lg.webapidemo.player;

import lg.webapidemo.objects.Item;
import lg.webapidemo.position.Point;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private Point position;
    private List<Item> inventory = new ArrayList<>();
    private Boolean isBlind;

    public Player(Point position, Boolean isBlind) {
        this.position = position;
        this.isBlind = isBlind;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Boolean isBlind() {
        return isBlind;
    }
}
