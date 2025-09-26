package my_app.data;

import java.io.Serializable;

public record FlexComponentData(
                String childType, // "text_component por exemplo"
                String childId,
                String identification,
                String orientation, // "row" || "column"
                int x,
                int y) implements Serializable {
}