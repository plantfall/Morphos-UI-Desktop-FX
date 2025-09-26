package my_app.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CanvaData implements Serializable {
        public CanvaProps self;
        public List<TextComponentData> text_componentes = new ArrayList<>();
        public List<ButtonComponentData> button_componentes = new ArrayList<>();
        public List<ImageComponentData> image_components = new ArrayList<>();
        public List<InputComponentData> input_components = new ArrayList<>();
        public List<FlexComponentData> flex_componentes = new ArrayList<>();

        public List<CanvaData> custom_components = new ArrayList<>();
}
