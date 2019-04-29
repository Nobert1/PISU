package dk.dtu.compute.se.pisd.monopoly.mini.model.properties;

import dk.dtu.compute.se.pisd.monopoly.mini.model.Property;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public enum Colors {
    GREY(142, 142, 147),
    RED(255, 59, 48),
    GREEN(51, 255, 51),
    PURPLE(88, 86, 214),
    LIGHTBLUE(52, 170, 220),
    MAGENTA(255,51,255),
    LIGHTRED(255,102,102),
    WHITE(255,255,255),
    YELLOW(255,255,0),
    DARKGREEN(0, 102, 0),
    SHIPPINGWHITE(224,224,224);

    private final int r;
    private final int g;
    private final int b;


    Colors(int i, int i1, int i2) {
        this.r = i;
        this.g = i1;
        this.b = i2;
    }



    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }


    public int getB() {
        return b;
    }

    public static Color getcolor(Colors colors) {
        Color color = new Color(colors.getR(), colors.getG(), colors.getB());
        return color;
    }
}

