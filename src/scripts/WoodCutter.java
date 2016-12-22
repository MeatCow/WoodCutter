package scripts;

import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Script.Manifest(
        name = "WoodCutterXtreme", properties = "author=Andrew & Matthieu; client=4;",
        description = "A neat little project"
)
public class WoodCutter extends PollingScript<ClientContext> {

    List<GameObject> trees;
    private int[] cutable = {1278,1276};

    @Override
    public void start() {
    }

    @Override
    public void poll() {
        if (shouldCut()) {
            GameObject tree = ctx.objects.select().id(cutable).nearest().poll();
            ctx.camera.turnTo(tree);
            ctx.movement.step(tree);
            tree.interact("Chop");
        }
        else if (inventoryIsFull()) {
            dropAllLogs();
        }
    }

    private void dropAllLogs() {
        for (Item inventoryItem: ctx.inventory.items()) {
            if (inventoryItem.id() == 1511) {
                inventoryItem.interact("Drop");
            }
        }
    }

    private boolean shouldCut() {
        if (!inventoryIsFull()
                && ctx.players.local().animation() == -1
                && !ctx.players.local().inMotion()) {
            return true;
        }
        return false;
    }

    private boolean inventoryIsFull() {
        return ctx.inventory.size() == 28;
    }

}