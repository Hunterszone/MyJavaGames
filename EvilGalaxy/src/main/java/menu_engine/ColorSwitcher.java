package menu_engine;

import java.util.ArrayList;
import java.awt.*;

public class ColorSwitcher {
    private ArrayList<Color> colorsList;
    private int currentColor;

    public ColorSwitcher() {
        currentColor = 0;
        this.colorsList = new ArrayList<>();
        init();
    }

    private void init() {
        colorsList.add(new Color(0, 0, 255, 175));
        colorsList.add(new Color(255, 0, 0, 175));
        colorsList.add(new Color(255, 0, 255, 175));
        colorsList.add(new Color(0, 255, 255, 175));
        colorsList.add(new Color(255, 255, 0, 175));
        colorsList.add(new Color(0, 255, 0, 175));
        colorsList.add(new Color(255, 255, 255, 175));
        colorsList.add(new Color(0, 0, 0, 175));
        colorsList.add(new Color(76, 0, 255, 145));
        colorsList.add(new Color(48, 159, 175, 175));
    }

    public Color nextColor(Color otherColor) {
        currentColor++;
        if(currentColor >= colorsList.size()) {
            currentColor = 0;
        }
        if(equalColors(otherColor)) {
            currentColor++;
            if(currentColor >= colorsList.size()) {
                currentColor = 0;
            }
            return colorsList.get(currentColor);
        } else {
            return colorsList.get(currentColor);
        }

    }

    private boolean equalColors(Color otherColor) {
        double distance = (otherColor.getRed() - this.colorsList.get(currentColor).getRed()) * (otherColor.getRed() - this.colorsList.get(currentColor).getRed()) +
                (otherColor.getGreen() - this.colorsList.get(currentColor).getGreen())*(otherColor.getGreen() - this.colorsList.get(currentColor).getGreen()) +
                (otherColor.getBlue() - this.colorsList.get(currentColor).getBlue())*(otherColor.getBlue() - this.colorsList.get(currentColor).getBlue()) +
                (otherColor.getAlpha() - this.colorsList.get(currentColor).getAlpha())*(otherColor.getAlpha() - this.colorsList.get(currentColor).getAlpha());
        if(distance == 0){
            return true;
        }else{
            return false;
        }
    }

    public Color getColor() {
        return colorsList.get(currentColor);
    }
}
