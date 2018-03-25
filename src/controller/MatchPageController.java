package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import utility.ViewManager;

public class MatchPageController implements Initializable, GeneralController {
	
	@FXML private ChoiceBox<Integer> maxPlayers;
	
	@FXML private void CreateMatchClicked(ActionEvent event) throws IOException {
		try {
			ViewManager.getInstance().changeView(ViewManager.LOBBY_HOST, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
//more stuff later
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		maxPlayers.setItems(FXCollections.observableArrayList(3,4,5,6,7,8));
	}

}
