package pl.edu.uj.kimage.controller.center;

import pl.edu.uj.kimage.controller.MainViewController;

//This controller should me used to control only elements in CenterView.fxml
//It should provide getters for data that will be passed to or processed in another controllers.
//TODO: Implement possibility to manage algorithms and flow templates, allow to add own ones
public class CenterViewController {

    private MainViewController mainViewController;

    public void init(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }
}
