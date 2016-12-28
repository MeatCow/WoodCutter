package EnumsScript;

import org.powerbot.script.Tile;

/**
 * Created by andy2great on 12/23/2016.
 */
public enum Banks {
    DRAYNOR(6943, new Tile(3092, 3243, 0)),
    GRAND_EXCHANGE(10060, new Tile(3164, 3486, 0)),
    FALADOR_EAST(24101, new Tile(3012, 3355, 0)),
    FALADOR_WEST(24101, new Tile(2946,3368,0)),
    VARROCK_WEST(7409, new Tile(3182,3440,0)),
    VARROCK_EST(7409, new Tile(3253,3422,0)),
    EDGEVILLE(6943 ,new Tile(3093,3494,0)),
    ALKHARID(6943, new Tile(3271,3167,0));

    private final int objectId;
    private final Tile bankPosition;

    private Banks(int objectId, Tile bankPosition) {
        this.objectId = objectId;
        this.bankPosition = bankPosition;
    }

    public int getObjectId() {
        return objectId;
    }

    public Tile getBankPosition() {
        return bankPosition;
    }
}
