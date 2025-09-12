package my_app.data;

import javafx.scene.layout.Pane;

public interface ViewContract<T> {
    void appearance(Pane father);

    void settings(Pane father);

    T getData();

    void applyData(T data);
}
