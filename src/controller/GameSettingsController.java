package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.Lobby;
import model.LobbyGateway;
import model.Role;
import model.RoleGateway;
import model.User;
import model.UserGateway;
import utility.AlertHelper;
import utility.ViewManager;

public class GameSettingsController implements Initializable, GeneralController {

	private User user;
	private LobbyGateway lobbyGate;
	private UserGateway userGate;
	private RoleGateway roleGate;
	
	public GameSettingsController(User user, LobbyGateway lobbyGate, UserGateway userGate, RoleGateway roleGate) {
		this.user = user;
		this.lobbyGate = lobbyGate;
		this.userGate = userGate;
		this.roleGate = roleGate;
	}

	@FXML private Button saveSettingsButton;
	@FXML private TextField villagersTextField;
	@FXML private TextField detectiveTextField;
	@FXML private TextField doctorTextField;
	@FXML private TextField vampireTextField;
	@FXML private ChoiceBox<Integer> maxPlayers;
	@FXML private TextField matchNameVal;
	
	private int totalPlayers = 8;
	private int numVillagers, numDetectives, numPriests, numVampires;

	
	@FXML private void saveButtonClicked(ActionEvent event) {
		int sum;
		try {
			sum = getSumRoles();
		} catch (Exception e) {
			AlertHelper.showWarningMessage("Error", 
					"Invalid Role Number Value", 
					"Please ensure that entered values are numbers");
			return;
		}
		totalPlayers = maxPlayers.getValue();
		
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
		if(numVampires > totalPlayers / 2) {
			AlertHelper.showWarningMessage("Error", 
					"Too many vampires selected", 
					"Please select less than half of total players as vampires");
			 return;
		}
		if(matchNameVal.getText().length() == 0) {
			AlertHelper.showWarningMessage("Error", 
					"No lobby name given", 
					"Please enter a lobby name");
			 return;
		}
		if(matchNameVal.getText().length() > 12) {
			AlertHelper.showWarningMessage("Error", 
					"Lobby name too long", 
					"Please enter a lobby name less than 12 characters");
			 return;
		}
		Lobby lobby = new Lobby(matchNameVal.getText(), maxPlayers.getValue(), 
				numDetectives, numVampires, numVillagers, numPriests);
		try {
			this.lobbyGate.insertLobby(lobby);
			this.userGate.updateUserLobby(user, lobby.getId());
			user.setLobbyId(lobby.getId());
			ObservableList<Role> roles = createRoles(lobby);
			if(roles.size() != maxPlayers.getValue()) {
				throw new Exception();
			}
			ViewManager.getInstance().changeView(ViewManager.LOBBY_HOST, lobby);
		} catch (Exception e) {
			AlertHelper.showWarningMessage("Error", 
					"Failed to initialize lobby", 
					"This isn't good :thumbsup:");
			return;
		}
	}
	
	private ObservableList<Role> createRoles(Lobby lobby) {
		ObservableList<Role> roles = FXCollections.observableArrayList();
		try {
			for(int i = 0; i < numVillagers; i++) {
				Role role = new Role("Villager", lobby.getId());
				roles.add(role);
				roleGate.insertRole(role);
			}
			for(int i = 0; i < numDetectives; i++) {
				Role role = new Role("Detective", lobby.getId());
				roles.add(role);
				roleGate.insertRole(role);
			}
			for(int i = 0; i < numPriests; i++) {
				Role role = new Role("Priest", lobby.getId());
				roles.add(role);
				roleGate.insertRole(role);
			}
			for(int i = 0; i < numVampires; i++) {
				Role role = new Role("Vampire", lobby.getId());
				roles.add(role);
				roleGate.insertRole(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roles;
	}
	
	private int getSumRoles(){
		numVillagers = Integer.parseInt(villagersTextField.getText());
		numDetectives = Integer.parseInt(detectiveTextField.getText());
		numPriests = Integer.parseInt(doctorTextField.getText());
		numVampires = Integer.parseInt(vampireTextField.getText());
		return numVillagers + numDetectives + numPriests + numVampires;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		maxPlayers.setItems(FXCollections.observableArrayList(3, 4, 5, 6, 7, 8));
		maxPlayers.getSelectionModel().selectLast();
		villagersTextField.setText("0");
		detectiveTextField.setText("0");
		doctorTextField.setText("0");
		vampireTextField.setText("0");
	}
	

	
}
