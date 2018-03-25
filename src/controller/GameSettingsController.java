package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utility.ViewManager;

public class GameSettingsController extends SettingsController implements Initializable, GeneralController {
	@FXML private void saveButtonClicked(ActionEvent event) throws IOException {
		try {
			ViewManager.getInstance().changeView(ViewManager.LOBBY_HOST, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
