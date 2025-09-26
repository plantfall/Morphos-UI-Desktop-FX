package my_app.data;

import java.io.Serializable;
import java.util.List;

public class InnerComponentData implements Serializable {

        public int padding_top;
        public int padding_right;
        public int padding_bottom;
        public int padding_left;
        public double width;
        public double height;
        public String bg_type;
        public String bgContent;
        public String identification;
        public int x;
        public int y;
        public boolean in_canva;
        public String canva_id;
        public List<TextComponentData> text_componentes;
        public List<ButtonComponentData> button_componentes;
        public List<ImageComponentData> image_components;
        public List<InputComponentData> input_components;
        public List<FlexComponentData> flex_componentes;
        public List<StateJson_v2> custom_components;

        // Construtor vazio
        public InnerComponentData() {
        }

        // Construtor com todos os argumentos
        public InnerComponentData(
                        int padding_top,
                        int padding_right,
                        int padding_bottom,
                        int padding_left,
                        double width,
                        double height,
                        String bg_type,
                        String bgContent,
                        String identification,
                        int x,
                        int y,
                        boolean in_canva,
                        String canva_id,
                        List<TextComponentData> text_componentes,
                        List<ButtonComponentData> button_componentes,
                        List<ImageComponentData> image_components,
                        List<InputComponentData> input_components,
                        List<FlexComponentData> flex_componentes,
                        List<StateJson_v2> custom_components) {

                this.padding_top = padding_top;
                this.padding_right = padding_right;
                this.padding_bottom = padding_bottom;
                this.padding_left = padding_left;
                this.width = width;
                this.height = height;
                this.bg_type = bg_type;
                this.bgContent = bgContent;
                this.identification = identification;
                this.x = x;
                this.y = y;
                this.in_canva = in_canva;
                this.canva_id = canva_id;
                this.text_componentes = text_componentes;
                this.button_componentes = button_componentes;
                this.image_components = image_components;
                this.input_components = input_components;
                this.flex_componentes = flex_componentes;
                this.custom_components = custom_components;
        }
}
