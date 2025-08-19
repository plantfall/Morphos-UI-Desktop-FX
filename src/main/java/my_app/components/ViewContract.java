package my_app.components;

import javafx.scene.layout.Pane;

public interface ViewContract {
    void appearance(Pane father);

    void settings(Pane father);
}
