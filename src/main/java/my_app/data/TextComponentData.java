package my_app.data;

import java.io.Serializable;

public record TextComponentData(
                String text,
                double layout_x,
                double layout_y,
                String fontSize,
                String color,
                String font_weight,
                String identification,
                boolean in_canva,
                String canva_id) implements Serializable {

}