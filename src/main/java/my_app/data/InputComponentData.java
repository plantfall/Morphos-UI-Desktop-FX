package my_app.data;

public record InputComponentData(
                String type,
                String text,
                String placeholder,
                String font_weight,
                String font_size,
                String color,
                double x,
                double y,
                String identification,
                boolean in_canva,
                String canva_id) implements ComponentData {
        public InputComponentData {
                if (type == null)
                        type = "input";
        }
}