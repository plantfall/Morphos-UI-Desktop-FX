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

    public void removeItemByIdentification(String identification) {
        // Itera sobre todos os ObservableList de SimpleStringProperty no dataMap
        for (ObservableList<SimpleStringProperty> itemsList : dataMap.values()) {
            // Usa um Iterator para permitir a remoção segura durante a iteração
            itemsList.removeIf(item -> item.get().equals(identification));

            // A forma mais direta e garantida é:
            SimpleStringProperty itemToRemove = null;
            for (SimpleStringProperty item : itemsList) {
                if (item.get().equals(identification)) {
                    itemToRemove = item;
                    break; // Encontrou o item, pode parar a busca nesta lista
                }
            }

            if (itemToRemove != null) {
                // Remove o item e retorna true
                itemsList.remove(itemToRemove);
                refreshSubItems();
            }
        }

    }

    public ObservableList<SimpleStringProperty> getItemsByType(String type) {
        return dataMap.computeIfAbsent(type, _ -> FXCollections.observableArrayList());
    }

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
