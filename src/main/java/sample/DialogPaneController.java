package sample;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sample.model.DataSingleton;
import sample.model.ToDoItem;

public class DialogPaneController {

    @FXML
    private TextField ShortDiscription;

    @FXML
    private javafx.scene.control.TextArea LongDiscription;

    @FXML
    private DatePicker DateDiscription;

    @FXML
    public void process(){
        DataSingleton.getData().addData(new ToDoItem(ShortDiscription.getText(), LongDiscription. getText(), DateDiscription.getValue()));
    }
}
