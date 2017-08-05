package lg.webapidemo.game;

import org.springframework.util.StringUtils;

public class GameSummary {

    private String gameId;
    private Integer level;
    private String additionalInfo;

    public GameSummary(String gameId, Integer level, String additionalInfo) {
        this.gameId = gameId;
        this.level = level;
        this.additionalInfo = additionalInfo;
    }

    public String getGameId() {
        return gameId;
    }

    public Integer getLevel() {
        return level;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    @Override
    public String toString() {
        return "Started game : \"" + gameId + "\" on level " + level + additionalInfo == null ? "" : additionalInfo;
    }
}
