package pl.edu.uj.kimage.controller.left;

import pl.edu.uj.kimage.controller.MainViewController;

//This controller should me used to control only elements in LeftView.fxml
//It should provide getters for data that will be passed to or processed in another controllers.
//TODO: Implement listing of loaded images with possibility to show their miniatures, implement "ADD" button functionality
public class LeftViewController {

    private MainViewController mainViewController;

    public void init(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }
}
