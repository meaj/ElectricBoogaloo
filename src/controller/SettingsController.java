package controller;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SettingsController extends Thread implements Initializable, GeneralController {
	
	@FXML private TextField nameTextField;
	protected boolean detective;
	protected boolean vampire;
	protected boolean villager;
	protected boolean priest;
	protected String matchName;
	
	@FXML private ChoiceBox<String> maxPlayers;
	
	
	//returns 0 if there is no max, returns the number of max players otherwise
	@FXML
	protected int getMaxPlayers() {
		if(maxPlayers.getValue().equals("None")) {
			return 0;
		}
		else {
			return Integer.parseInt(maxPlayers.getValue());
		}
			
	}
	
	@FXML private void setCheckBox(ActionEvent event) throws IOException {
		//Casts the object in getSource to a CheckBox then calls the getText method of Checkbox to get the case
		String s= ((CheckBox) event.getSource()).getText();
		switch(s) {
			case "Detective":
				detective=!detective;
				break;
			case "Mafia":
				vampire=!vampire;
				break;
			case "Priest":
				priest=!priest;
				break;
			case "Villager":
				villager=!villager;
				break;
		}
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		maxPlayers.setItems(FXCollections.observableArrayList("None","3","4","5","6","7","8"));
		maxPlayers.getSelectionModel().selectFirst();
		matchName="";
		detective=false;
		vampire=false;
		villager=false;
		priest=false;
		
		nameTextField.setOnKeyPressed(new EventHandler<KeyEvent>(){
		    @Override
		    public void handle(KeyEvent ke){
		      if (ke.getCode().equals(KeyCode.ENTER)) {
		    	  matchName=nameTextField.getText();
		    	  nameTextField.clear();
		         }
		      }
		    });
		
	}

}
