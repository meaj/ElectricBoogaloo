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
	
	private boolean detective;
	private boolean mafia;
	private boolean villager;
	private boolean police;
	
	@FXML private ChoiceBox<Integer> maxPlayers;
	
	@FXML private void CreateMatchClicked(ActionEvent event) throws IOException {
		try {
			ViewManager.getInstance().changeView(ViewManager.LOBBY_HOST, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@FXML private void MatchSearchClicked(ActionEvent event) throws IOException {
		//Should check database for available matches and search based on filters
	}
	
	@FXML private void setDetective(ActionEvent event) throws IOException {
		detective=!detective;
		System.out.println(event.getTarget());
	}
	
	@FXML private void setMafia(ActionEvent event) throws IOException {
		mafia=!mafia;
		System.out.println(event.getTarget());
	}
	
	@FXML private void setVillager(ActionEvent event) throws IOException {
		villager=!villager;
	}
	
	@FXML private void setPolice(ActionEvent event) throws IOException {
		police=!police;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		maxPlayers.setItems(FXCollections.observableArrayList(3,4,5,6,7,8));
		detective=false;
		mafia=false;
		villager=false;
		police=true;
	}

}
