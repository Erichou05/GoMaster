package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class PerformanceCurve extends JPanel {
    private ArrayList<Integer> ratings;
    private static final int BORDER_WIDTH = 50;
    private int minRating;
    private int maxRating;

    /*
     * EFFECTS: sets the list of ratings to the given rating history
     */
    public PerformanceCurve(ArrayList<Integer> ratings) {
        this.ratings = ratings;
    }

    /*
     * EFFECTS: generates the user interface of viewing the performance curve
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPerformanceCurve(g);
    }

    /*
     * EFFECTS: initializes the parameters for drawing the performance curve
     */
    private void drawPerformanceCurve(Graphics g) {
        int width = getWidth() - 2 * BORDER_WIDTH;
        int height = getHeight() - 2 * BORDER_WIDTH;
        int startX = BORDER_WIDTH;
        int startY = BORDER_WIDTH;
        int endX = startX + width;
        int endY = startY + height;
        drawAxes(g, startX, startY, endX, endY);
        drawMinMaxRatings(g, startX, startY, endY);
        drawCurve(g, startX, startY, width, height, endY);
    }

    /*
     * EFFECTS: draws the axes
     */
    private void drawAxes(Graphics g, int startX, int startY, int endX, int endY) {
        g.setColor(Color.BLUE);
        g.drawLine(startX, endY, endX, endY);
        g.drawLine(startX, startY, startX, endY);
    }

    /*
     * EFFECTS: draws the endpoints of the y axis (rating)
     */
    private void drawMinMaxRatings(Graphics g, int startX, int startY, int endY) {
        if (!ratings.isEmpty()) {
            maxRating = Collections.max(ratings);
            minRating = Collections.min(ratings);
        }
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(minRating), startX - 30, endY);
        g.drawString(Integer.toString(maxRating), startX - 30, startY);
    }

    /*
     * EFFECTS: draws the performance curve
     */
    private void drawCurve(Graphics g, int startX, int startY, int width, int height, int endY) {
        g.setColor(Color.BLUE);
        int numPoints = ratings.size();
        if (numPoints > 1) {
            for (int i = 1; i < numPoints; i++) {
                int x1 = startX + (width * (i - 1)) / (numPoints - 1);
                int y1 = endY - ((ratings.get(i - 1) - minRating) * height / (maxRating - minRating));
                int x2 = startX + (width * i) / (numPoints - 1);
                int y2 = endY - ((ratings.get(i) - minRating) * height / (maxRating - minRating));
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }

}
