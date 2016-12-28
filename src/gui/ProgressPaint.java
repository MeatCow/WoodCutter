package gui;

import org.powerbot.script.rt4.ClientContext;
import org.powerbot.script.PaintListener;
import org.powerbot.script.rt4.Constants;
import org.powerbot.script.rt4.Skills.*;

import java.awt.*;

/**
 * Created by Matt on 22/12/2016.
 */
public class ProgressPaint {

    ClientContext ctx;
    private long startTime;
    private Color backgroundColour;

    private final int RECTANGLE_X = 0;
    private final int RECTANGLE_Y = 25;
    private final int RECTANGLE_WIDTH = 210;
    private final int RECTANGLE_HEIGHT = 105;
    private final int INITIAL_XP;

    public ProgressPaint(ClientContext ctx) {
        INITIAL_XP = ctx.skills.experience(Constants.SKILLS_WOODCUTTING);
        startTime = System.currentTimeMillis();
        this.ctx = ctx;
        backgroundColour = new Color(192, 192, 192, 120); // RGBA for transparent gray
    }

    public void drawProgress(Graphics g) {

        int currentLevel = ctx.skills.level(Constants.SKILLS_WOODCUTTING);

        g.drawRect(RECTANGLE_X,RECTANGLE_Y,RECTANGLE_WIDTH,RECTANGLE_HEIGHT); // Draws the border

        g.setColor(backgroundColour);
        g.fillRect(RECTANGLE_X,RECTANGLE_Y,RECTANGLE_WIDTH,RECTANGLE_HEIGHT); // Draws the background

        g.setColor(Color.white);
        g.drawString("Time since start: " + getFormattedTime(),RECTANGLE_X + 2,RECTANGLE_Y + 20);
        g.drawString("Current WoodCutting level: "
                + currentLevel,RECTANGLE_X + 2,RECTANGLE_Y + 40);

        int remainingXp =
                ctx.skills.experienceAt(currentLevel + 1) - ctx.skills.experience(Constants.SKILLS_WOODCUTTING);

        g.drawString("Remaining XP to levelup: " + remainingXp,RECTANGLE_X + 2,RECTANGLE_Y + 60);

        int hourlyXp = calculateXpHour();
        g.drawString(hourlyXp + " xp/hour",RECTANGLE_X + 2, RECTANGLE_Y + 80);
    }

    private int calculateXpHour() {
        long timeTaken = System.currentTimeMillis() - startTime;
        int xpGained = ctx.skills.experience(Constants.SKILLS_WOODCUTTING) - INITIAL_XP;

        double xpMillis = 1.0 * xpGained/timeTaken;

        return (int) (xpMillis * 3600 * 1000);
    }

    private String getFormattedTime() {
        long timeDifference = System.currentTimeMillis() - startTime;

        int seconds = (int) (timeDifference / 1000);
        int minutes = seconds / 60;
        int hours = minutes / 60;

        seconds %= 60;
        minutes %= 60;
        hours %= 24;

        return (hours < 10 ? "0" : "") + hours + ":" +
                (minutes < 10 ? "0" : "") + minutes + ":" +
                (seconds < 10 ? "0" : "") + seconds;
    }

}
