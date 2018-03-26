package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import model.User;
import utility.ViewManager;

public class GameSettingsController implements Initializable, GeneralController {

	private User user;
	
	public GameSettingsController(User user) {
		this.user = user;
	}

	@FXML private Button saveSettingsButton;
	
	
	@FXML void saveButtonClicked(){
		try {
			ViewManager.getInstance().changeView(ViewManager.LOBBY_HOST, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
