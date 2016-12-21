package scripts;

import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;

import java.util.concurrent.ThreadLocalRandom;

@Script.Manifest(
        name = "Hello, RSBot!", properties = "author=Coma; topic=1296203; client=4;",
        description = "A 'Hello, World' example for RSBot"
)
public class WoodCutter extends PollingScript<ClientContext> {

    @Override
    public void start() {

        this.stop();
    }

    @Override
    public void poll() {

    }

    /**
     * Methode qui permet tourne la camera dans une position aleatoire
     */
    private void turnCameraRandomPosition(){
        int x = ThreadLocalRandom.current().nextInt(-10000, 10000);
        int y = ThreadLocalRandom.current().nextInt(-10000, 10000);
        int z = ThreadLocalRandom.current().nextInt(-10000, 10000);
        ctx.camera.turnTo(new Locatable() {
            @Override
            public Tile tile() {
                return new Tile(x,y,z);
            }
        });
    }
}