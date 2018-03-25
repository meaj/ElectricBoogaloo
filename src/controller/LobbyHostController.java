package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import utility.ViewManager;

public class LobbyHostController implements Initializable, GeneralController {

	@FXML private TextField chatTextField;
	@FXML private Button startGameButton;
	@FXML private Button signupPassword;
	
	
	public LobbyHostController(){
	}
	
	@FXML private void startGameButtonClicked(){
		try {
			ViewManager.getInstance().changeView(ViewManager.RUNNING_GAME, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML private void settingsButtonClicked(){
		//
	}
	
	@FXML private void handleMouseClicked() {
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
