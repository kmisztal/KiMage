package pl.edu.uj.kimage.controller.right;

import pl.edu.uj.kimage.controller.MainViewController;

//This controller should be used to control only elements in RightView.fxml
//It should provide getters for data that will be passed to or processed in another controllers.
//TODO: Implement current flow proceeding info, allow to control executing next flow step with buttons
public class RightViewController {

    private MainViewController mainViewController;

    public void init(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }
}
