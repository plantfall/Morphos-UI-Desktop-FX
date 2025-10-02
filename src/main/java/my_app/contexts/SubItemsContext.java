package my_app.contexts;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class SubItemsContext {

    private static SubItemsContext instance;
    private ObservableMap<String, ObservableList<SimpleStringProperty>> dataMap;

    public static SimpleBooleanProperty leftItemsStateRefreshed = new SimpleBooleanProperty(false);

    public static void refreshSubItems() {
        leftItemsStateRefreshed.set(!leftItemsStateRefreshed.get());
    }

    private SubItemsContext() {
        dataMap = FXCollections.observableHashMap();
    }

    public void addItem(String type, String identification) {
        dataMap.computeIfAbsent(type, k -> FXCollections.observableArrayList())
                .add(new SimpleStringProperty(identification));
    }

    public ObservableList<SimpleStringProperty> getItemsByType(String type) {
        return dataMap.computeIfAbsent(type, k -> FXCollections.observableArrayList());
    }

    // public ObservableList<SimpleStringProperty> getItemsByType(String type) {
    // return dataMap.getOrDefault(type, FXCollections.observableArrayList());
    // }
    public ObservableMap<String, ObservableList<SimpleStringProperty>> getAllData() {
        return dataMap;
    }

    public void clearAllItems() {
        dataMap.clear();
    }

    public static SubItemsContext getInstance() {
        if (instance == null) {
            instance = new SubItemsContext();
        }
        return instance;
    }
}
