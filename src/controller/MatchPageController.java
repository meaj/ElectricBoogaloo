package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import model.Lobby;
import model.LobbyGateway;
import model.User;
import model.UserGateway;
import utility.AlertHelper;
import utility.ViewManager;

public class MatchPageController extends SettingsController implements Initializable, GeneralController {

	@FXML private ListView<Lobby> matchNameList;
	private ObservableList<Lobby> lobbies;
	private LobbyGateway lobbyGate;
	private UserGateway userGate;
	private User user;
	
	public MatchPageController(User user, LobbyGateway lobbyGate, UserGateway userGate) {
		this.lobbyGate = lobbyGate;
		this.userGate = userGate;
		this.user = user;
		getLobbies();
	}
	
	@FXML private void CreateMatchClicked(ActionEvent event) throws IOException {
		ViewManager.getInstance().changeView(ViewManager.GAME_SETTINGS, null);
	}
	

	@FXML private void MatchSearchClicked(ActionEvent event) throws IOException {
		//Should check database for available matches and search based on filters
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Bind the GUI list view to the list data
		updateListView();
		//Set the listener for the double click on the lobby
		matchNameList.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent click) {
		        if (click.getClickCount() == 2) {
		        	Lobby currentItemSelected = matchNameList.getSelectionModel().getSelectedItem();
		        	try {
		    			userGate.updateUserLobby(user, currentItemSelected.getId());
		    			user.setLobbyId(currentItemSelected.getId());
		    			ViewManager.getInstance().changeView(ViewManager.LOBBY_PLAYER, currentItemSelected);
		    		} catch (SQLException e) {
		    			AlertHelper.showWarningMessage("Error", 
		    					"Failed to initialize lobby", 
		    					"This isn't good :thumbsup:");
		    			return;
		    		}
		        }
		    }
		});
	}

	public void updateListView() {
		matchNameList.setItems(lobbies);
		matchNameList.setCellFactory(lv -> new ListCell<Lobby>() {
			@Override
			public void updateItem(Lobby item, boolean empty) {
				super.updateItem(item, empty);
				if(empty) {
					setText(null);
				} else {
					String text = item.getName();
					setText(text);
				}
			}
		});
	}
	
	public void getLobbies() {
		try {
			lobbies = this.lobbyGate.getLobbies();
		} catch (Exception e) {
			e.printStackTrace();
			Platform.exit();
		}
	}
	
}
