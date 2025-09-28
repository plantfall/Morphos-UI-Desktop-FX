package my_app.data;

public record ColumnComponentData(
                String type, // NOVO: "column",
                String identification,
                String childType,
                String childId,
                // Alteramos para a interface base
                ComponentData child,
                ComponentData alternative_child,
                int x,
                int y,
                boolean in_canva,
                String canva_id,
                int pref_child_amount_for_preview) implements ComponentData {

        public ColumnComponentData {
                if (type == null)
                        type = "column items";
        }

}