package pl.edu.uj.kimage.controller.right;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.util.Callback;
import pl.edu.uj.kimage.controller.MainViewController;

//This controller should be used to control only elements in RightView.fxml
//It should provide getters for data that will be passed to or processed in another controllers.
//TODO: Implement current flow proceeding info, allow to control executing next flow step with buttons
public class RightViewController {
    @FXML
    private ListView<String> flowList;

    private MainViewController mainViewController;

    private final ObjectProperty<ListCell<String>> dragSource = new SimpleObjectProperty<>(); //dragged element
    private final ObservableList<String> observableList = FXCollections.observableArrayList();
    private final String addElemntInfo = "Add algorithm here.";         //last list element
    private final String separator = ": ";

    public void init(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
        initPluginnList();
    }

    private void initPluginnList() {
        flowList.setItems(observableList);
        observableList.add(addElemntInfo);

        flowList.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

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

                    //Last element is not accesible
                    String name = listCell.getItem();
                    if (name == addElemntInfo) {
                        return;
                    }

                    Dragboard db = listCell.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(listCell.getItem());
                    db.setContent(content);
                    dragSource.set(listCell);
                });

                listCell.setOnDragOver(event -> {
                    event.consume();

                    Dragboard db = event.getDragboard();
                    if (db.hasString()) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                });

                listCell.setOnDragDropped(event -> {
                    event.consume();
                    Dragboard db = event.getDragboard();
                    if (db.hasString() && dragSource.get() != null) {  //If dragged cell exist
                        ListCell<String> dragSourceCell = dragSource.get();
                        String fullName = dragSourceCell.getItem();

                        //Insert pluginCell
                        insertFlowCell(listCell, dragSourceCell);

                        //Delete cell if draged from flowList
                        deleteFlowCell(fullName);

                        //Update index in cell name
                        updateFlowListIndex();

                        event.setDropCompleted(true);
                        dragSource.set(null);
                    } else {
                        event.setDropCompleted(false);
                    }
                });

                return listCell;
            }
        });
    }

    private void insertFlowCell(ListCell<String> listCell, ListCell<String> draggedCell) {
        //TODO set view for Cell 'issue #60'
        String fullName = draggedCell.getItem();
        String[] splitName = fullName.split(separator);     //flowList plugin name have 'index separator name'
        boolean flowListElement = (splitName.length == 2);
        String pluginName = flowListElement ? splitName[1] : splitName[0];
        int newIndex = listCell.getIndex();
        int oldIndex = draggedCell.getIndex();

        if (listCell.getItem() != null) {                 // Dropped on element
            if (flowListElement && oldIndex < newIndex) { // Dragged from flowList
                newIndex++;    // After deleting it would go one position up, that why its need to be put further by one
            }
            observableList.add(newIndex, newIndex + separator + pluginName);
        } else {
            newIndex = observableList.size() - 1;         // Last element is just informaltion
            observableList.add(newIndex, newIndex + separator + pluginName);
        }
    }

    private void deleteFlowCell(String pluginFullName) {
        int index = observableList.indexOf(pluginFullName);
        if (index >= 0) {
            observableList.remove(index);
        }
    }

    private void updateFlowListIndex() {
        //TODO update graphic in 'issue #60'
        for (int i = 0; i < observableList.size() - 1; i++) {
            String pluginName = observableList.get(i).split(separator)[1];
            observableList.set(i, i + separator + pluginName);
        }

    }

    public void setDragSource(ListCell<String> dragSource) {
        this.dragSource.set(dragSource);
    }
}
