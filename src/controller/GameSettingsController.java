package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import utility.ViewManager;

public class GameSettingsController implements Initializable, GeneralController {

	@FXML private TextField villagersTextField;
	@FXML private TextField detectiveTextField;
	@FXML private TextField doctorTextField;
	@FXML private TextField vampireTextField;
	@FXML private ChoiceBox<Integer> maxPlayers;
	private int totalPlayers =8;
	private int numVillagers, numDetectives, numDoctors, numVampires;
	
	@FXML private void saveButtonClicked(ActionEvent event) throws IOException {
		try {
			ViewManager.getInstance().changeView(ViewManager.LOBBY_HOST, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private int getSumRoles(){
		numVillagers = Integer.parseInt(villagersTextField.getText());
		numDetectives +=Integer.parseInt(detectiveTextField.getText());
		numDoctors+=Integer.parseInt(doctorTextField.getText());
		numVampires +=Integer.parseInt(vampireTextField.getText());
		return numVillagers + numDetectives +numDoctors + numVampires;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		maxPlayers.setValue(totalPlayers);
		
	}

}
