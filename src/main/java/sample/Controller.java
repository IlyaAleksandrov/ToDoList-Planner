package sample;

import javafx.application.Platform;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import sample.model.DataSingleton;
import sample.model.ToDoItem;
import sample.model.Weather;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;

public class Controller {

    @FXML
    private Label weather;

    @FXML
    private ListView<ToDoItem> listView;

    @FXML
    private TextArea details;

    @FXML
    private Label datadetails;

    @FXML
    private BorderPane mainPane;

    @FXML
    private ContextMenu contextMenu;

    public void initialize() throws IOException {

        weather.setText(Weather.getWeather());

        SortedList<ToDoItem> sortedList = new SortedList<ToDoItem>(DataSingleton.getData().getList(), new Comparator<ToDoItem>() {
            @Override
            public int compare(ToDoItem o1, ToDoItem o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        DataSingleton.getData().setList(DataSingleton.getData().getList());
        listView.setItems(sortedList);

        contextMenu = new ContextMenu();
        MenuItem delete = new MenuItem("Delete task");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteTask();
            }
        });
        contextMenu.getItems().setAll(delete);

        listView.setCellFactory(new Callback<ListView<ToDoItem>, ListCell<ToDoItem>>() {
            @Override
            public ListCell<ToDoItem> call(ListView<ToDoItem> param) {
                ListCell<ToDoItem> cell = new ListCell<ToDoItem>(){
                    @Override
                    protected void updateItem(ToDoItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty)
                            setText(null);
                        else {
                            setText(item.getShortDisc());
                            if (item.getDate().isBefore(LocalDate.now().plusDays(1))){
                            setTextFill(Color.RED);
                        }
                        }
                    }
                };
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if(isNowEmpty)
                    cell.setContextMenu(null);
                else
                    cell.setContextMenu(contextMenu);
            } );

            return cell;
            }
        });
    }

    @FXML
    public void showDialogPaneForNewTask(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainPane.getScene().getWindow());
        dialog.setTitle("Adding new task");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/DialogFile.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }
        catch (IOException e){
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get()== ButtonType.OK) {
            DialogPaneController controller = fxmlLoader.getController();
            controller.process();
        }
        else System.out.println();

    }

    @FXML
    public void ListViewClicked(){
        ToDoItem selected = listView.getSelectionModel().getSelectedItem();

        StringBuilder description = new StringBuilder(selected.getLongDisc());
        description.append("\n\n\n");
        details.setText(description.toString());

        StringBuilder date = new StringBuilder("");
        date.append(" Deadline:\t");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd MMMM, yyyy");
        date.append(df.format(selected.getDate()));
        datadetails.setText(date.toString());
    }

    @FXML
    public void deleteTask() {
        ToDoItem toDelete = listView.getSelectionModel().getSelectedItem();
        if (toDelete != null)
            DataSingleton.getData().deleteItem(toDelete);
    }

    @FXML
    public void exitApp(){
        Platform.exit();
    }

}
