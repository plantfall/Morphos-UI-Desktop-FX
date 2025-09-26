package my_app.data;

import java.io.Serializable;

public record FlexComponentData(
        String identification,
        String childType, // "text_component por exemplo"
        String childId,
        InnerComponentData child,
        String orientation, // "row" || "column"
        int x,
        int y,
        int pref_length_of_wrap,
        boolean in_canva,
        String canva_id) implements Serializable {
}