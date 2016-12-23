package scripts;

import EnumsScript.RandomActions;
import org.powerbot.script.Random;
import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.rt4.Game;

import java.awt.*;

/**
 * Created by andy2great on 12/22/2016.
 */
public class RandomEvent extends Task {

    private final int MISS_CLICK = 0;
    public RandomEvent(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean shouldActivate() {
        return Random.nextInt(0, 50) == 0;
    }

    @Override
    public void execute() {
        startRandomEvent(Random.nextInt(1, 2));
    }

    private void startRandomEvent(int actionValue) {
        if (RandomActions.MISS_CLICK.getActionValue() == actionValue) {
            missClick();
        } else if (RandomActions.TURNING_CAMERA.getActionValue() == actionValue) {
            turningCamera();
        }
    }

    private void missClick(){
        int mouseCurrentPositionX = ctx.input.getLocation().x;
        int mouseCurrentPositionY = ctx.input.getLocation().y;
        Point currentMousePosition = new Point(mouseCurrentPositionX, mouseCurrentPositionY);
        ctx.input.click(currentMousePosition, 0);
    }

    private void turningCamera(){
        ctx.camera.pitch(Random.nextInt(0,100));
        ctx.camera.angle(Random.nextInt(0,360));
    }
}
