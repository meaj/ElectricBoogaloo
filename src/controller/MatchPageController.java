package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import utility.ViewManager;

public class MatchPageController implements Initializable, GeneralController {
	
	private boolean detective;
	private boolean mafia;
	private boolean villager;
	private boolean police;
	
	@FXML private ChoiceBox<String> maxPlayers;
	
	@FXML private void CreateMatchClicked(ActionEvent event) throws IOException {
		try {
			ViewManager.getInstance().changeView(ViewManager.LOBBY_HOST, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	//returns 0 if there is no max, returns the number of max players otherwise
	@FXML private int getMaxPlayers() {
		if(maxPlayers.getValue().equals("None")) {
			return 0;
		}
		else {
			return Integer.parseInt(maxPlayers.getValue());
		}
			
	}
	
	@FXML private void MatchSearchClicked(ActionEvent event) throws IOException {
		//Should check database for available matches and search based on filters
	}
	
	@FXML private void setCheckBox(ActionEvent event) throws IOException {
		//Casts the object in getSource to a CheckBox then calls the getText method of Checkbox to get the case
		String s= ((CheckBox) event.getSource()).getText();
		switch(s) {
			case "Detective":
				detective=!detective;
				break;
			case "Mafia":
				mafia=!mafia;
				break;
			case "Police":
				police=!police;
				break;
			case "Villager":
				villager=!villager;
				break;
		}
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		maxPlayers.setItems(FXCollections.observableArrayList("None","3","4","5","6","7","8"));
		maxPlayers.getSelectionModel().selectFirst();
		detective=false;
		mafia=false;
		villager=false;
		police=false;
	}

}
