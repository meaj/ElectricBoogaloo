package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utility.ViewManager;

public class MatchPageController implements Initializable, GeneralController {
	
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
		
	}

}
