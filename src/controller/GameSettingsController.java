package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.User;
import utility.AlertHelper;
import utility.ViewManager;

public class GameSettingsController implements Initializable, GeneralController {

	private User user;
	
	public GameSettingsController(User user) {
		this.user = user;
	}

	@FXML private Button saveSettingsButton;
	@FXML private TextField villagersTextField;
	@FXML private TextField detectiveTextField;
	@FXML private TextField doctorTextField;
	@FXML private TextField vampireTextField;
	@FXML private ChoiceBox<String> maxPlayers;
	private int totalPlayers =8;
	private int numVillagers, numDetectives, numDoctors, numVampires;
	private String error;

	
	@FXML private void saveButtonClicked(ActionEvent event) throws IOException {
		try {
			int sum = getSumRoles();
			if(sum > totalPlayers){
				AlertHelper.showWarningMessage("Error", 
						"Too many roles selected", 
						"Please select roles equal to max players");
				 return;
			}
			if(sum < totalPlayers){
				AlertHelper.showWarningMessage("Error", 
						"Too few roles selected", 
						"Please select roles equal to max players");
				 return;
			}
			if(numVampires < 1){
				AlertHelper.showWarningMessage("Error", 
						"Too few vampires selected", 
						"Please select at least one vampire role");
				 return;
			}
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
		maxPlayers.setItems(FXCollections.observableArrayList("None","3","4","5","6","7","8"));
		maxPlayers.getSelectionModel().selectLast();
		villagersTextField.setText("0");
		detectiveTextField.setText("0");
		doctorTextField.setText("0");
		vampireTextField.setText("0");
		
	}
	

	
}
