package my_app.data;

import java.io.Serializable;

public record ButtonComponentData(
        String text,
        String fontSize,
        String fontWeight,
        String color,
        String borderWidth,
        String borderRadius,
        String bgColor,
        double x,
        double y,
        int padding_top,
        int padding_right,
        int padding_bottom,
        int padding_left,
        String identification) implements Serializable {
}