package lg.webapidemo;

import lg.webapidemo.objects.Blocker;
import org.springframework.http.ResponseEntity;

public class MoveResponse {

    private Boolean moveSuccess;
    private Boolean gameComplete;
    private BlockerResponse blockedBy;

    private MoveResponse(Boolean moveSuccess, Boolean gameComplete, BlockerResponse blockedBy) {
        this.moveSuccess = moveSuccess;
        this.gameComplete = gameComplete;
        this.blockedBy = blockedBy;
    }

    public static ResponseEntity<MoveResponse> successfulMove() {
        return ResponseEntity.ok(new MoveResponse(true, false, null));
    }

    public static ResponseEntity<MoveResponse> unsuccessfulMove(Blocker blocker) {
        return ResponseEntity.badRequest().body(new MoveResponse(false, false, blocker.asResponse()));
    }

    public static ResponseEntity<MoveResponse> gameComplete() {
        return ResponseEntity.ok(new MoveResponse(true, true, null));
    }

    public Boolean getMoveSuccess() {
        return moveSuccess;
    }

    public Boolean getGameComplete() {
        return gameComplete;
    }

    public BlockerResponse getBlockedBy() {
        return blockedBy;
    }
}
