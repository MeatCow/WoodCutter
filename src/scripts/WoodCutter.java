package scripts;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import gui.InitialOptions;
import gui.ProgressPaint;
import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;

@Script.Manifest(
        name = "WoodCutterXtreme", properties = "author=Andrew & Matthieu; client=4;",
        description = "A neat little project"
)
public class WoodCutter extends PollingScript<ClientContext> implements PaintListener{

    private boolean bank = false;
    private ProgressPaint painter = new ProgressPaint(ctx); // If declared in constructor, repaint() throws IOException
    private List<Task> tasks;

    @Override
    public void start() {
        InitialOptions optionsWindow = new InitialOptions();
        optionsWindow.setVisible(true);

        waitForOptions(optionsWindow);

        tasks = new ArrayList();

        tasks.add(new Chop(ctx));
        tasks.add(new Return(ctx));
        if (bank) {
            tasks.add(new Bank(ctx));
        } else {
            tasks.add(new Drop(ctx));
        }

        tasks.add(new RandomEvent(ctx));
    }

    @Override
    public void poll() {
        boolean didNothing = false;
        for (Task t : tasks) {
            if (t.shouldActivate()) {
                t.execute();
            }
        }
    }

    @Override
    public void repaint(Graphics g) {
        painter.drawProgress(g);
    }

    private void waitForOptions(InitialOptions options) {
        while (!options.ready()) ;
    }

}
