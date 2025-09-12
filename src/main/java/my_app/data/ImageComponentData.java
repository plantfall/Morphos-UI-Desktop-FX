package my_app.data;

import java.io.Serializable;

public record ImageComponentData(
        String url,
        double width,
        double height,
        double x,
        double y,
        boolean preserve_ratio,
        String identification) implements Serializable {
}