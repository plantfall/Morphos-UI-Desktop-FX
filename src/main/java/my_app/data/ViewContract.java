package my_app.data;

import javafx.scene.layout.Pane;

public interface ViewContract {
    void appearance(Pane father);

    void settings(Pane father);
}
