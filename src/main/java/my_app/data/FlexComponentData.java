package my_app.data;

import java.io.Serializable;

public record FlexComponentData(
        String childType, // "text_component por exemplo"
        Object childData,
        String identification,
        String orientation// "row" || "column"
) implements Serializable {
}