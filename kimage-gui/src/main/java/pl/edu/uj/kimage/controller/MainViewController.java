package pl.edu.uj.kimage.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import pl.edu.uj.kimage.controller.center.CenterViewController;
import pl.edu.uj.kimage.controller.left.LeftViewController;
import pl.edu.uj.kimage.controller.right.RightViewController;
import pl.edu.uj.kimage.controller.top.TopViewController;

import java.net.URL;
import java.util.ResourceBundle;

//This class allows controllers to communicate between them. All code that helps passing data from one controller
//to another should be inserted here.
public class MainViewController implements Initializable {

    @FXML
    TopViewController topViewController;
    @FXML
    LeftViewController leftViewController;
    @FXML
    CenterViewController centerViewController;
    @FXML
    RightViewController rightViewController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        topViewController.init(this);
        leftViewController.init(this);
        centerViewController.init(this);
        rightViewController.init(this);
    }

    public TopViewController getTopViewController() {
        return topViewController;
    }

    public LeftViewController getLeftViewController() {
        return leftViewController;
    }

    public CenterViewController getCenterViewController() {
        return centerViewController;
    }

    public RightViewController getRightViewController() {
        return rightViewController;
    }
}
