package my_app.contexts;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;

import java.util.Collections;

public class DataStore {

    // Classe interna para representar cada dado
    public static class DataItem {
        public String type, name;
        public SimpleStringProperty value;

        public DataItem(String type, String name, String value) {
            this.type = type;
            this.name = name;
            this.value = new SimpleStringProperty(value);
        }
    }

    // Singleton: garante que haja apenas uma instância acessível globalmente
    private static DataStore instance;

    // private List<DataItem> dataList = List.of(
    // new DataItem("boolean", "textIsSelected", "False"));

    private List<DataItem> dataList;

    private DataStore() {
        dataList = new ArrayList<>();
    }

    public static DataStore getInstance() {
        if (instance == null) {
            instance = new DataStore();
        }
        return instance;
    }

    // Adiciona um novo dado
    public void addData(DataItem item) {
        dataList.add(item);
    }

    // Remove um dado
    public void removeData(DataItem item) {
        dataList.remove(item);
    }

    // Retorna todos os dados (cópia imutável para segurança)
    public List<DataItem> getDataList() {
        return Collections.unmodifiableList(dataList);
    }

    // Limpar todos os dados
    public void clear() {
        dataList.clear();
    }

}
