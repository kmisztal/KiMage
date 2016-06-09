package pl.edu.uj.kimage.controller.center;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;
import pl.edu.uj.kimage.client.ImageProcessorClientFactory;
import pl.edu.uj.kimage.controller.MainViewController;
import pl.edu.uj.kimage.controller.right.RightViewController;

import java.util.Set;

//This controller should be used to control only elements in CenterView.fxml
//It should provide getters for data that will be passed to or processed in another controllers.
//TODO: Implement possibility to manage algorithms and flow templates, allow to add own ones
public class CenterViewController {

    @FXML
    private ListView<String> pluginList;

    private MainViewController mainViewController;

    public void init(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
        initPluginList();
    }

    private void initPluginList(){
        Set<String> pluginsNames = new ImageProcessorClientFactory().create().getListOfPluginName();
        pluginList.getItems().addAll(pluginsNames);

        pluginList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

            @Override
            public ListCell<String> call(ListView<String> param) {
                ListCell<String> listCell = new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                    }
                };

                listCell.setOnDragDetected((MouseEvent event) -> {
                    event.consume();

                    Dragboard db = listCell.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(listCell.getItem());
                    db.setContent(content);
                    mainViewController.rightViewController.setDragSource(listCell);
                });

                return listCell;
            }
        });
    }
}
