package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Lobby;
import model.LobbyGateway;
import model.Message;
import model.MessageGateway;
import model.Role;
import model.RoleGateway;
import model.User;
import utility.AlertHelper;
import utility.ViewManager;

public class LobbyHostController extends Thread implements Initializable, GeneralController {

	@FXML private TextField chatTextField;
	@FXML private Button startGameButton;
	@FXML private ListView<Message> pregameChatListView;
	@FXML private ListView<User> playerListView;
	@FXML private Label playersLabel;
	@FXML private Label readyPlayerLabel;
	@FXML private ListView<Role> roleListView;
	
	private ObservableList<Message> chatLog;
	private ObservableList<User> users;
	private ObservableList<Role> roles;
	private MessageGateway messageGateway;
	private LobbyGateway lobbyGateway;
	private RoleGateway roleGateway;
	private Thread thread;
	private Lobby lobby;
	private int readyCount;
	private User user;
	private boolean starting;
	private int countdown = 3;
	
	public LobbyHostController(Object lobby, MessageGateway messageGate, LobbyGateway lobbyGate, RoleGateway roleGate){
		this.lobby = (Lobby) lobby;
		this.messageGateway = messageGate;
		this.lobbyGateway = lobbyGate;
		this.roleGateway = roleGate;
		chatLog = FXCollections.observableArrayList();
		users = FXCollections.observableArrayList();
		this.user = ViewManager.getInstance().getUser();
		starting = false;
		this.start();
	}

	public void run() {
		while(true){
			try {
				Thread.sleep(1000);
				chatLog = messageGateway.getMessagesForLobby(lobby.getId());
				users = lobbyGateway.getUsersByLobbyId(lobby.getId());
				readyCount = lobbyGateway.getReadyCount(lobby.getId());
			} 	catch (InterruptedException e) {
				break;
			}	catch (Exception e) {
				  e.printStackTrace();
			}
			Platform.runLater(new Runnable() {
				@Override public void run() {
					
					if(readyCount != lobby.getMaxPlayers()) {
						starting = false;
						countdown = 3;
					}
					if(starting == true && countdown != 0) {
						if(readyCount == lobby.getMaxPlayers()) {
							Message m = new Message();
							m.setMessage("Game starting in " + countdown + "...");
							m.setLobbyId(lobby.getId());
							m.setSendUser("Console:");
							try {
								messageGateway.insert(m);
								chatLog = messageGateway.getMessagesForLobby(lobby.getId());
								chatTextField.clear();
								pregameChatListView.setItems(chatLog);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							countdown--;
						}
					} else if (countdown == 0 && starting == true){
						assignRoles();
						try {
							messageGateway.deleteMessagesForLobby(lobby.getId());
						} catch (SQLException e) {
							e.printStackTrace();
						}
						ViewManager.getInstance().changeView(ViewManager.RUNNING_GAME, lobby);
						thread.interrupt();
					}
					pregameChatListView.setItems(chatLog);
					playerListView.setItems(users);
					playersLabel.setText("Players: " + users.size() + "/" + lobby.getMaxPlayers());
					readyPlayerLabel.setText("Ready Players: " + readyCount + "/" + lobby.getMaxPlayers());
				}
			});
		}
	}
	
	public void start () {
		 if (thread == null) {
			 thread = new Thread (this);
			 thread.setDaemon(true);
			 thread.start ();
		 }
	}
	
	@FXML private void startGameButtonClicked() {
		if(readyCount == lobby.getMaxPlayers()) {
			starting = true;
			
		} else {
			AlertHelper.showWarningMessage("Error", 
					"All players not ready", 
					"Please wait until the lobby is full and all players are ready");
			return;
		}
		
	}
	
	@FXML void onEnter(ActionEvent ae){
		if(!chatTextField.getText().equals("")) {
			Message m = new Message();
			m.setMessage(chatTextField.getText());
			m.setLobbyId(lobby.getId());
			m.setSendUser(ViewManager.getInstance().getUser().getUsername());
			try {
				messageGateway.insert(m);
				chatLog = messageGateway.getMessagesForLobby(lobby.getId());
				chatTextField.clear();
				pregameChatListView.setItems(chatLog);
			} catch (SQLException e) {
				System.out.println("Failed to update chatlog");
				e.printStackTrace();
			}
		}
	}
	
	private void assignRoles() {
		try {
			ObservableList<User> users = lobbyGateway.getUsersByLobbyId(lobby.getId());
			Random rand = new Random();
			for(int i = lobby.getMaxPlayers(); i > 0; i--) {
				int randNum = rand.nextInt(i);
				roleGateway.updateRoleForUser(roles.get(0).getId(), users.get(randNum).getId());
				roles.remove(0);
				users.remove(randNum);
			}
			Role role = roleGateway.getRoleByUserId(user.getId());
			user.setRole(role.getRole());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		pregameChatListView.setItems(chatLog);
		try {
			roles = roleGateway.getRoles(this.lobby.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		roleListView.setItems(roles);
	}
}
