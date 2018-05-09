package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class DataSingleton {
    private static DataSingleton data = new DataSingleton();
    private static String fileName = "database.txt";
    private ObservableList<ToDoItem> list;
    private DateTimeFormatter df = DateTimeFormatter.ofPattern("dd MMMM, yyyy");

    private DataSingleton(){}

    public static DataSingleton getData(){
        return data;
    }

    public ObservableList<ToDoItem> getList() {
        return list;
    }

    public void setList(ObservableList<ToDoItem> list) {
        this.list = list;
    }

    public void addData(ToDoItem item){
        list.add(item);
    }

    public void loadData() throws IOException {
        list = FXCollections.observableArrayList();
        Path path = Paths.get(fileName);
        BufferedReader br = Files.newBufferedReader(path);

        String input;
        try {
            while ((input = br.readLine()) != null) {
                String[] items = input.split("\t");
                String shortDiscr = items[0];
                String longDiscr = items[1];
                LocalDate date = LocalDate.parse(items[2], df);

                list.add(new ToDoItem(shortDiscr, longDiscr, date));
            }
        }
        finally {
            br.close();
        }
    }

    public void saveData() throws IOException {

        Path path = Paths.get(fileName);
        BufferedWriter fw = Files.newBufferedWriter(path);
        try {
            for (ToDoItem item : list) {
                fw.write(item.getShortDisc() + "\t" + item.getLongDisc() + "\t" + item.getDate().format(df) + "\n");
            }
        }
        finally {
            fw.close();
        }
    }

    public void deleteItem(ToDoItem item) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Do you want to delete this task?");
        alert.setHeaderText(item + " to delete");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == (ButtonType.OK))
            list.remove(item);

    }
}
