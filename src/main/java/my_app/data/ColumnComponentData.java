package my_app.data;

import java.io.Serializable;

public record ColumnComponentData(
                String identification,
                String childType, // Ex: "text_component", "custom_component"
                String childId, // ID do tipo de componente filho a ser replicado
                InnerComponentData child, // Estado do componente filho (para recriação)
                InnerComponentData alternative_child,
                int x,
                int y,
                boolean in_canva,
                String canva_id,
                int pref_child_amount_for_preview) implements Serializable {
}