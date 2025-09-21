package my_app.contexts;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

import my_app.data.CanvaComponentJson;

public class ComponentsContext {

    private static ComponentsContext instance;
    public CanvaComponentJson data;

    public void loadJsonState(File file) {

        try {
            ObjectMapper om = new ObjectMapper();
            this.data = om.readValue(file, CanvaComponentJson.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //

    public static ComponentsContext getInstance() {
        if (instance == null) {
            instance = new ComponentsContext();
        }
        return instance;
    }
}
