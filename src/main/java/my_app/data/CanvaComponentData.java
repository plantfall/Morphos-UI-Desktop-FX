package my_app.data;

import java.io.Serializable;

public record CanvaComponentData(
        int padding_top,
        int padding_right,
        int padding_bottom,
        int paddingL_left,
        double width,
        double height,
        String bg_type,
        String bgContent) implements Serializable {
}