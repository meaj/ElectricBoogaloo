package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
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
import model.Role;
import model.RoleGateway;
import model.User;
import model.UserGateway;
import utility.AlertHelper;
import utility.ViewManager;

public class MatchPageController extends SettingsController implements Initializable, GeneralController {

	@FXML private ListView<Lobby> matchNameList;
	private ObservableList<Lobby> lobbies;
	private LobbyGateway lobbyGate;
	private UserGateway userGate;
	private RoleGateway rGate;
	private User user;
	private Thread thread;
	private boolean detectiveFilter;
	private boolean villagerFilter;
	private boolean priestFilter;
	private int playersFilter;
	
	public MatchPageController(User user, LobbyGateway lobbyGate, UserGateway userGate, RoleGateway roleGate) {
		this.lobbyGate = lobbyGate;
		this.userGate = userGate;
		this.user = user;
		rGate=roleGate;
		detectiveFilter=detective;
		villagerFilter=villager;
		priestFilter=priest;
		playersFilter=0;
		getLobbies();
		this.start();
	}
	
	@FXML private void CreateMatchClicked(ActionEvent event) throws IOException {
		ViewManager.getInstance().changeView(ViewManager.GAME_SETTINGS, null);
	}
	

	@FXML private void MatchSearchClicked(ActionEvent event) throws IOException {
		//Sets the filters
		detectiveFilter=detective;
		villagerFilter=villager;
		priestFilter=priest;
		playersFilter=super.getMaxPlayers();
	}
	
	public void run() {
		while(true){
			try {
				Thread.sleep(1000);
				Platform.runLater(new Runnable() {
				@Override public void run() {
					getLobbies();
					updateListView();
				}
			});
			} catch (Exception e) {
				  e.printStackTrace();
			}
			
		}
	}
	
	public void start () {
		 if (thread == null) {
			 thread = new Thread (this);
			 thread.setDaemon(true);
			 thread.start ();
		 }
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
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
		    		} catch(NullPointerException e) {
		    			
		    		}
		        }
		    }
		});
	}

	public void updateListView() {
		ObservableList<Lobby> filteredList = FXCollections.observableArrayList();
		for (Lobby l : lobbies) {
			ObservableList<Role> roleList = FXCollections.observableArrayList();
			try {
				roleList=rGate.getRoles(l.getId());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if((l.getMaxPlayers()==playersFilter||playersFilter==0)) {
				boolean noDetective=true;
				boolean noPriest=true;
				boolean noVillager=true;
				for(Role r: roleList) {
					switch(r.getRole()) {
					case "Detective":
						noDetective=false;
						break;
					case "Priest":
						noPriest=false;
						break;
					case "Villager":
						noVillager=false;
						break;
					}
				}
				if(((noDetective&&detectiveFilter)||!detectiveFilter)&&((noPriest&&priestFilter)||!priestFilter)&&((noVillager&&villagerFilter)||!villagerFilter)) {
					filteredList.add(l);
				}
			}
		}
		matchNameList.setItems(filteredList);
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
