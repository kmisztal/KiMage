package pl.edu.uj.kimage.controller.top;

import pl.edu.uj.kimage.controller.MainViewController;

//This controller should me used to control only elements in TopView.fxml
//It should provide getters for data that will be passed to or processed in another controllers.
//TODO: Implement menu bar functions
public class TopViewController {

    private MainViewController mainViewController;

    public void init(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

}

