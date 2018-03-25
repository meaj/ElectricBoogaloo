package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import utility.ViewManager;

public class LobbyHostController implements Initializable {

	@FXML private TextField chatTextField;
	@FXML private Button startGameButton;
	@FXML private Button signupPassword;
	
	
	public LobbyHostController(){
	}
	
	@FXML void startGameButtonClicked(){
		try {
			ViewManager.getInstance().changeView(ViewManager.MATCH_PLAYER, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML void settingsButtonClicked(){
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
