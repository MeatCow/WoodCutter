package scripts;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import EnumsScript.Logs;
import EnumsScript.Trees;
import gui.InitialOptions;
import gui.ProgressPaint;
import org.powerbot.script.*;
import org.powerbot.script.rt4.ClientContext;

@Script.Manifest(
        name = "WoodCutterXtreme", properties = "author=Andrew & Matthieu; client=4;",
        description = "A neat little project"
)
public class WoodCutter extends PollingScript<ClientContext> implements PaintListener{

    private ProgressPaint painter = new ProgressPaint(ctx); // If initialised in constructor, repaint() throws IOException
    private InitialOptions optionsWindow;
    private List<Task> tasks;

    @Override
    public void start() {
        optionsWindow = new InitialOptions();
        optionsWindow.setVisible(true);

        waitForOptions();

        int[] treeIds = getCutableTreeIds();
        int[] logIds = getDropableLogIds();
        boolean bank = getBankChoice();

        tasks = new ArrayList();

        tasks.add(new Chop(ctx, treeIds));
        tasks.add(new Return(ctx));
        if (bank) {
            tasks.add(new Bank(ctx, logIds));
        } else {
            tasks.add(new Drop(ctx, logIds));
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

    private void waitForOptions() {
        while (!optionsWindow.ready()) ;
    }

    private int[] getCutableTreeIds() {
        List<Integer> cutableIds = new LinkedList();
        if (optionsWindow.getNormalTrees()) {
            for (int i:Trees.NORMAL.getTreeIds()){
                cutableIds.add(i);
            }
        }
        if (optionsWindow.getOaks()) {
            for (int i:Trees.OAK.getTreeIds()) {
                cutableIds.add(i);
            }
        }
        if (optionsWindow.getWillows()) {
            for(int i:Trees.WILLOW.getTreeIds()) {
                cutableIds.add(i);
            }
        }
        if (optionsWindow.getYews()) {
            for(int i:Trees.YEW.getTreeIds()) {
                cutableIds.add(i);
            }
        }
        return cutableIds.stream().mapToInt(i->i).toArray();
    }

    private int[] getDropableLogIds() {
        int[] dropableLogs = new int[Logs.AMOUNT_TYPES];
        if (optionsWindow.getNormalTrees()) {
            dropableLogs[0] = Logs.Normal.getId();
        }
        if (optionsWindow.getOaks()) {
            dropableLogs[1] = Logs.Oak.getId();
        }
        if (optionsWindow.getWillows()) {
            dropableLogs[2] = Logs.Willow.getId();
        }
        if (optionsWindow.getYews()) {
            dropableLogs[3] = Logs.Yew.getId();
        }

        return dropableLogs;
    }

    private boolean getBankChoice() {
        InitialOptions.Methods bankChoice = optionsWindow.getBankChoice();

        return bankChoice == InitialOptions.Methods.BANK;
    }

}
