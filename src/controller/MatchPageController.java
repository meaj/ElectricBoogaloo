package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utility.ViewManager;

public class MatchPageController extends SettingsController implements Initializable, GeneralController {

	@FXML private void CreateMatchClicked(ActionEvent event) throws IOException {
		try {
			ViewManager.getInstance().changeView(ViewManager.GAME_SETTINGS, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	@FXML private void MatchSearchClicked(ActionEvent event) throws IOException {
		//Should check database for available matches and search based on filters
	}
	

}
