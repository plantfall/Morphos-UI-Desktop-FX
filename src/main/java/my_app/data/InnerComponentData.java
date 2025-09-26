package my_app.data;

import java.io.Serializable;
import java.util.List;

public record InnerComponentData(
        int padding_top,
        int padding_right,
        int padding_bottom,
        int padding_left,
        double width,
        double height,
        String bg_type,
        String bgContent,
        String identification,
        int x, int y,
        boolean in_canva,
        String canva_id,
        List<TextComponentData> text_componentes,
        List<ButtonComponentData> button_componentes,
        List<ImageComponentData> image_components,
        List<InputComponentData> input_components,
        List<FlexComponentData> flex_componentes,
        List<StateJson_v2> custom_components) implements Serializable {

}
