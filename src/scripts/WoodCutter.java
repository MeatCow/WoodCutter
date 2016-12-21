package scripts;

import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

@Script.Manifest(
        name = "Hello, RSBot!", properties = "author=Coma; topic=1296203; client=4;",
        description = "A 'Hello, World' example for RSBot"
)
public class WoodCutter extends PollingScript<ClientContext> {

    @Override
    public void start() {
        log.info("Hello, RSBot!");
    }

    @Override
    public void poll() {

    }
}