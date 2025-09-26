package my_app.data;

import java.io.Serializable;

public record InputComponentData(
        String text,
        String placeholder,
        String font_weight,
        String font_size,
        String color,
        double x,
        double y,
        String identification,
        boolean in_canva,
        String canva_id) implements Serializable {
}